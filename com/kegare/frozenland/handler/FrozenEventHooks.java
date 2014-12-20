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
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingPackSizeEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import shift.sextiarysector.api.SextiarySectorAPI;

import com.kegare.frozenland.api.FrozenlandAPI;
import com.kegare.frozenland.core.Config;
import com.kegare.frozenland.core.Frozenland;
import com.kegare.frozenland.item.FrozenItems;
import com.kegare.frozenland.item.ItemIcePickaxe;
import com.kegare.frozenland.plugin.sextiarysector.SextiarySectorPlugin;
import com.kegare.frozenland.util.FrozenUtils;
import com.kegare.frozenland.util.Version;
import com.kegare.frozenland.util.Version.Status;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerConnectionFromClientEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FrozenEventHooks
{
	public static final FrozenEventHooks instance = new FrozenEventHooks();

	protected int updateLCG = new Random().nextInt();

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event)
	{
		if (event.modID.equals(Frozenland.MODID))
		{
			Config.syncConfig();
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

			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(component);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onClientDisconnected(ClientDisconnectionFromServerEvent event)
	{
		Frozenland.tabFrozenland.tabIconItem = null;

		Config.syncConfig();
	}

	@SubscribeEvent
	public void onServerConnected(ServerConnectionFromClientEvent event)
	{
		event.manager.scheduleOutboundPacket(Frozenland.network.getPacketFrom(new Config()));
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
		EntityLivingBase entity = event.entityLiving;

		if (FrozenlandAPI.isEntityInFrozenland(entity))
		{
			if (entity.ticksExisted % 20 == 0 && entity.isBurning())
			{
				entity.extinguish();
			}
		}
		else return;

		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;

			if (!player.capabilities.isCreativeMode && player.worldObj.difficultySetting.getDifficultyId() > 0)
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
					float armor = player.getArmorVisibility();
					boolean daytime = player.worldObj.isDaytime();

					if (armor <= 0.0F)
					{
						player.addExhaustion(0.05F);

						if (SextiarySectorPlugin.enabled())
						{
							SextiarySectorAPI.addStaminaExhaustion(player, 2.0F);
						}

						if (daytime)
						{
							player.attackEntityFrom(DamageSource.generic, 1.0F);
						}
						else
						{
							player.attackEntityFrom(DamageSource.generic, 1.5F);
						}
					}
					else if (armor < 0.5F)
					{
						player.addExhaustion(0.035F);

						if (SextiarySectorPlugin.enabled())
						{
							SextiarySectorAPI.addStaminaExhaustion(player, 1.0F);
						}

						if (daytime)
						{
							player.attackEntityFrom(DamageSource.generic, 0.5F);
						}
						else
						{
							player.attackEntityFrom(DamageSource.generic, 1.0F);
						}
					}
					else if (armor < 0.75F)
					{
						player.addExhaustion(0.015F);

						if (SextiarySectorPlugin.enabled())
						{
							SextiarySectorAPI.addStaminaExhaustion(player, 0.5F);
						}

						if (daytime)
						{
							player.attackEntityFrom(DamageSource.generic, 0.5F);
						}
					}
				}
			}
		}
		else if (entity instanceof EntityLiving)
		{
			EntityLiving living = (EntityLiving)entity;

			if (living instanceof EntityVillager || living instanceof EntityGolem || living instanceof EntitySquid)
			{
				return;
			}

			int i = 0;
			boolean boots = false;

			if (living.func_130225_q(3) != null)
			{
				++i;
			}

			if (living.func_130225_q(2) != null)
			{
				++i;
			}

			if (living.func_130225_q(1) != null)
			{
				++i;
			}

			if (living.func_130225_q(0) != null)
			{
				boots = true;
				++i;
			}

			if (!boots)
			{
				living.motionX *= 0.9D;
				living.motionZ *= 0.9D;
			}

			int j = 200;

			if (living.isSprinting())
			{
				j /= 3;
			}

			if (living.ticksExisted % j == 0)
			{
				float armor = i / 4.0F;
				boolean daytime = living.worldObj.isDaytime();

				if (armor <= 0.0F)
				{
					if (daytime)
					{
						living.attackEntityFrom(DamageSource.generic, 2.0F);
					}
					else
					{
						living.attackEntityFrom(DamageSource.generic, 2.5F);
					}
				}
				else if (armor < 0.5F)
				{
					if (daytime)
					{
						living.attackEntityFrom(DamageSource.generic, 1.0F);
					}
					else
					{
						living.attackEntityFrom(DamageSource.generic, 1.5F);
					}
				}
				else if (armor < 0.75F)
				{
					if (daytime)
					{
						living.attackEntityFrom(DamageSource.generic, 0.5F);
					}
					else
					{
						living.attackEntityFrom(DamageSource.generic, 1.0F);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerBreak(BreakEvent event)
	{
		if (FrozenlandAPI.isEntityInFrozenland(event.getPlayer()))
		{
			Block block = event.block;
			int metadata = event.blockMetadata;
			EntityPlayer player = event.getPlayer();
			World world = player.worldObj;
			int x = event.x;
			int y = event.y;
			int z = event.z;
			ItemStack itemstack = player.getCurrentEquippedItem();

			if (itemstack != null && itemstack.getItem() != null && FrozenUtils.isIceBlock(block))
			{
				Item item = itemstack.getItem();

				if (item instanceof ItemIcePickaxe)
				{
					return;
				}

				if (item instanceof ItemPickaxe || item.getToolClasses(itemstack).contains("pickaxe"))
				{
					world.playAuxSFXAtEntity(player, 2001, x, y, z, Block.getIdFromBlock(block) + (metadata << 12));
					world.setBlockToAir(x, y, z);

					if (!world.isRemote)
					{
						int rate = 5;

						if (EnchantmentHelper.getSilkTouchModifier(player))
						{
							rate = 1;
						}
						else if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equalsIgnoreCase("ice"))
						{
							rate = 2;
						}

						if (rate == 1 || rate > 1 && world.rand.nextInt(rate) == 0)
						{
							EntityItem entity = new EntityItem(world, x + 0.5D, y + 0.5D, z + 0.5D, new ItemStack(block, 1, metadata));
							entity.delayBeforeCanPickup = 10;

							world.spawnEntityInWorld(entity);
						}
					}

					event.setCanceled(true);
				}
				else if (Config.iceball && item == Items.snowball)
				{
					world.playAuxSFXAtEntity(player, 2001, x, y, z, Block.getIdFromBlock(block) + (metadata << 12));
					world.setBlockToAir(x, y, z);

					int stack = itemstack.stackSize;

					itemstack.stackSize = 0;
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

					if (!world.isRemote)
					{
						stack = Math.max(stack / 2, 1);
						EntityItem entity = new EntityItem(world, x + 0.5D, y + 0.5D, z + 0.5D, new ItemStack(FrozenItems.iceball, player.getRNG().nextInt(stack) + stack));
						entity.delayBeforeCanPickup = 10;

						world.spawnEntityInWorld(entity);
					}

					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public void onLivingCheckSpawn(LivingSpawnEvent.CheckSpawn event)
	{
		if (FrozenlandAPI.isEntityInFrozenland(event.entityLiving) && event.entityLiving instanceof IMob)
		{
			event.setResult(Result.DENY);
		}
	}

	@SubscribeEvent
	public void onLivingSpecialSpawn(LivingSpawnEvent.SpecialSpawn event)
	{
		if (FrozenlandAPI.isEntityInFrozenland(event.entityLiving) && event.entityLiving instanceof IMob)
		{
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onLivingDespawn(LivingSpawnEvent.AllowDespawn event)
	{
		if (FrozenlandAPI.isEntityInFrozenland(event.entityLiving) && event.entityLiving instanceof IMob)
		{
			event.setResult(Result.ALLOW);
		}
	}

	@SubscribeEvent
	public void onLivingPackSize(LivingPackSizeEvent event)
	{
		if (FrozenlandAPI.isEntityInFrozenland(event.entityLiving) && event.entityLiving instanceof IMob)
		{
			event.maxPackSize = 0;
			event.setResult(Result.ALLOW);
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

	@SubscribeEvent
	public void onBiomeDecorate(DecorateBiomeEvent.Decorate event)
	{
		if (event.world.provider.dimensionId == FrozenlandAPI.getDimension())
		{
			if (event.type == Decorate.EventType.TREE)
			{
				event.setResult(Result.DENY);
			}
		}
	}
}