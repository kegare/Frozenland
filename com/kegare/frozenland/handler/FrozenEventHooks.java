/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.handler;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import shift.sextiarysector.api.SextiarySectorAPI;

import com.kegare.frozenland.api.FrozenlandAPI;
import com.kegare.frozenland.core.Config;
import com.kegare.frozenland.core.Frozenland;
import com.kegare.frozenland.item.ItemIcePickaxe;
import com.kegare.frozenland.network.DimSyncMessage;
import com.kegare.frozenland.plugin.sextiarysector.SextiarySectorPlugin;
import com.kegare.frozenland.util.Version;
import com.kegare.frozenland.util.Version.Status;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerConnectionFromClientEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FrozenEventHooks
{
	public static final FrozenEventHooks instance = new FrozenEventHooks();

	protected int updateLCG = new Random().nextInt();

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent event)
	{
		if (event.modID.equals(Frozenland.MODID))
		{
			if (Config.config.hasChanged())
			{
				Config.config.save();
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onClientConnected(ClientConnectedToServerEvent event)
	{
		if (Version.getStatus() == Status.PENDING || Version.getStatus() == Status.FAILED)
		{
			Version.versionCheck();
		}
		else if (Version.DEV_DEBUG || Config.versionNotify && Version.isOutdated())
		{
			IChatComponent component = new ChatComponentTranslation("frozenland.version.message", EnumChatFormatting.AQUA + "Frozenland" + EnumChatFormatting.RESET);
			component.appendText(" : " + EnumChatFormatting.YELLOW + Version.getLatest());
			component.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Frozenland.metadata.url));

			event.handler.handleChat(new S02PacketChat(component));
		}
	}

	@SubscribeEvent
	public void onServerConnected(ServerConnectionFromClientEvent event)
	{
		event.manager.scheduleOutboundPacket(Frozenland.network.getPacketFrom(new DimSyncMessage(FrozenlandAPI.getDimension(), FrozenlandAPI.getBiome().biomeID)));
	}

	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event)
	{
		if (event.phase != Phase.END || event.world.isRemote)
		{
			return;
		}

		if (event.world instanceof WorldServer)
		{
			WorldServer world = (WorldServer)event.world;

			if (world.provider.dimensionId == FrozenlandAPI.getDimension())
			{
				Set activeChunkSet = ObfuscationReflectionHelper.getPrivateValue(World.class, world, "activeChunkSet", "field_72993_I");

				if (activeChunkSet != null && !activeChunkSet.isEmpty())
				{
					Iterator iterator = activeChunkSet.iterator();

					while (iterator.hasNext())
					{
						ChunkCoordIntPair coord = (ChunkCoordIntPair)iterator.next();
						int x = coord.chunkXPos * 16;
						int z = coord.chunkZPos * 16;

						if (world.rand.nextInt(10) == 0)
						{
							updateLCG = updateLCG * 3 + 1013904223;
							int i = updateLCG >> 2;
							int j = i & 15;
							int k = i >> 8 & 15;
							int y = world.getPrecipitationHeight(j + x, k + z);
							int blockX = j + x;
							int blockY = y - 1;
							int blockZ = k + z;

							if (world.isBlockFreezableNaturally(blockX, blockY, blockZ))
							{
								world.setBlock(blockX, blockY, blockZ, Blocks.ice);
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entityLiving;

			if (!player.capabilities.isCreativeMode && FrozenlandAPI.isEntityInFrozenland(player))
			{
				if (player.inventory.armorItemInSlot(0) == null)
				{
					player.motionX *= 0.9D;
					player.motionZ *= 0.9D;
				}

				int i = 200;

				if (player.isSprinting())
				{
					i /= 3;
				}

				if (player.ticksExisted % i == 0)
				{
					if (player.getArmorVisibility() <= 0.0F)
					{
						player.addExhaustion(0.05F);

						if (SextiarySectorPlugin.enabled())
						{
							SextiarySectorAPI.addStaminaExhaustion(player, 2.0F);
						}

						player.attackEntityFrom(DamageSource.generic, 1.0F);
					}
					else if (player.getArmorVisibility() < 0.5F)
					{
						player.addExhaustion(0.035F);

						if (SextiarySectorPlugin.enabled())
						{
							SextiarySectorAPI.addStaminaExhaustion(player, 1.0F);
						}

						player.attackEntityFrom(DamageSource.generic, 0.5F);
					}
					else if (player.getArmorVisibility() < 0.75F)
					{
						player.addExhaustion(0.015F);

						if (SextiarySectorPlugin.enabled())
						{
							SextiarySectorAPI.addStaminaExhaustion(player, 0.5F);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerBreak(BreakEvent event)
	{
		if (FrozenlandAPI.isEntityInFrozenland(event.getPlayer()) && event.getPlayer() instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP)event.getPlayer();
			WorldServer world = player.getServerForPlayer();
			int x = event.x;
			int y = event.y;
			int z = event.z;
			ItemStack itemstack = player.getCurrentEquippedItem();

			if (itemstack != null && itemstack.getItem() != null && (event.block == Blocks.ice || event.block == Blocks.packed_ice))
			{
				Item item = itemstack.getItem();

				if (item instanceof ItemIcePickaxe)
				{
					return;
				}

				if (item instanceof ItemPickaxe || item.getToolClasses(itemstack).contains("pickaxe"))
				{
					int rate = 10;

					world.playAuxSFXAtEntity(player, 2001, x, y, z, Block.getIdFromBlock(event.block) + (world.getBlockMetadata(x, y, z) << 12));
					world.setBlockToAir(x, y, z);

					if (EnchantmentHelper.getSilkTouchModifier(player))
					{
						rate = 1;
					}
					else if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equalsIgnoreCase("ice"))
					{
						rate = 5;
					}

					if (rate == 1 || rate > 1 && world.rand.nextInt(rate) == 0)
					{
						EntityItem entity = new EntityItem(world, x + 0.5D, y + 0.5D, z + 0.5D, new ItemStack(event.block));
						entity.delayBeforeCanPickup = 10;

						world.spawnEntityInWorld(entity);
					}

					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public void onChunkPopulate(PopulateChunkEvent.Populate event)
	{
		if (event.world.provider.dimensionId == FrozenlandAPI.getDimension())
		{
			if (event.type == Populate.EventType.LAVA || event.type == Populate.EventType.NETHER_LAVA)
			{
				event.setResult(Result.DENY);
			}
		}
	}
}