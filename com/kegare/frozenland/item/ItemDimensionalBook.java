/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.item;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import com.kegare.frozenland.core.Frozenland;
import com.kegare.frozenland.util.FrozenUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDimensionalBook extends ItemBook
{
	private final int dimensionId;

	public ItemDimensionalBook(String name, String texture, int dim)
	{
		this.setUnlocalizedName(name);
		this.setTextureName("frozenland:" + texture);
		this.setCreativeTab(Frozenland.tabFrozenland);
		this.dimensionId = dim;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP && player.onGround)
		{
			EntityPlayerMP thePlayer = (EntityPlayerMP)player;
			int dim = thePlayer.dimension == dimensionId ? 0 : dimensionId;
			NBTTagCompound nbt = itemstack.getTagCompound();

			if (nbt != null && nbt.getString("Owner").equals(thePlayer.getGameProfile().getId().toString()))
			{
				if (thePlayer.capabilities.isCreativeMode || !nbt.hasKey("LastUseTime") || nbt.getLong("LastUseTime") + 6000L < world.getTotalWorldTime())
				{
					NBTTagCompound pos = nbt.getCompoundTag("LastUsePos." + thePlayer.dimension);

					if (pos == null)
					{
						pos = new NBTTagCompound();
					}

					pos.setDouble("PosX", thePlayer.posX);
					pos.setDouble("PosY", thePlayer.posY);
					pos.setDouble("PosZ", thePlayer.posZ);
					pos.setFloat("Yaw", thePlayer.rotationYaw);
					pos.setFloat("Pitch", thePlayer.rotationPitch);
					nbt.setTag("LastUsePos." + thePlayer.dimension, pos);

					if (!thePlayer.capabilities.isCreativeMode)
					{
						nbt.setLong("LastUseTime", world.getTotalWorldTime());
					}

					if (thePlayer.dimension == dimensionId)
					{
						if (nbt.hasKey("LastDim"))
						{
							dim = nbt.getInteger("LastDim");

							if (!DimensionManager.isDimensionRegistered(dim))
							{
								dim = 0;
							}
						}
					}
					else
					{
						nbt.setInteger("LastDim", thePlayer.dimension);
					}

					world.playSoundToNearExcept(thePlayer, "frozenland:dimensional_teleport", 0.5F, 1.0F);

					boolean result;

					if (nbt.hasKey("LastUsePos." + dim))
					{
						pos = nbt.getCompoundTag("LastUsePos." + dim);
						result = FrozenUtils.teleportPlayer(thePlayer, dim, pos.getDouble("PosX"), pos.getDouble("PosY"), pos.getDouble("PosZ"), pos.getFloat("Yaw"), pos.getFloat("Pitch"));
					}
					else
					{
						int x = MathHelper.floor_double(thePlayer.posX);
						int y = MathHelper.floor_double(thePlayer.posY);
						int z = MathHelper.floor_double(thePlayer.posZ);
						ChunkPosition village = world.findClosestStructure("Village", x, y, z);
						boolean flag = false;

						if (village != null)
						{
							x = village.chunkPosX;
							z = village.chunkPosZ + 3;
							y = world.getTopSolidOrLiquidBlock(x, z);

							if (y > 0)
							{
								flag = true;
							}
						}

						if (flag)
						{
							result = FrozenUtils.teleportPlayer(thePlayer, dim, x + 0.5D, y, z + 0.5D, thePlayer.rotationYaw, thePlayer.rotationPitch);
						}
						else
						{
							result = FrozenUtils.teleportPlayer(thePlayer, dim);
						}
					}

					if (result)
					{
						thePlayer.worldObj.playSoundAtEntity(thePlayer, "frozenland:dimensional_teleport", 0.75F, 1.0F);
					}
				}
			}
		}

		return itemstack;
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean current)
	{
		super.onUpdate(itemstack, world, entity, slot, current);

		if (entity instanceof EntityPlayer)
		{
			NBTTagCompound nbt = itemstack.getTagCompound();
			EntityPlayer player = (EntityPlayer)entity;

			if (nbt == null)
			{
				nbt = new NBTTagCompound();

				itemstack.setTagCompound(nbt);
			}

			if (!nbt.hasKey("Owner"))
			{
				nbt.setString("Owner", player.getGameProfile().getId().toString());
			}
		}
	}

	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player)
	{
		super.onCreated(itemstack, world, player);

		NBTTagCompound nbt = itemstack.getTagCompound();

		if (nbt == null)
		{
			nbt = new NBTTagCompound();

			itemstack.setTagCompound(nbt);
		}

		nbt.setString("Owner", player.getGameProfile().getId().toString());
	}

	@Override
	public int getItemEnchantability()
	{
		return 0;
	}

	@Override
	public boolean isBookEnchantable(ItemStack itemstack, ItemStack book)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean advanced)
	{
		NBTTagCompound nbt = itemstack.getTagCompound();

		if (nbt != null && nbt.hasKey("Owner"))
		{
			EntityPlayer owner = player.worldObj.func_152378_a(UUID.fromString(nbt.getString("Owner")));
			String name;

			if (owner == null)
			{
				name = "Unknown";
			}
			else
			{
				name = owner.getDisplayName();

				if (!name.equals(owner.getGameProfile().getName()))
				{
					name += " (" + owner.getGameProfile().getName() + ")";
				}
			}

			list.add(EnumChatFormatting.ITALIC + I18n.format("item.dimensionalBook.owner") + ": " + EnumChatFormatting.RESET + name);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public EnumRarity getRarity(ItemStack itemstack)
	{
		return EnumRarity.rare;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack itemstack, int pass)
	{
		NBTTagCompound nbt = itemstack.getTagCompound();

		return nbt != null && nbt.hasKey("Owner");
	}
}