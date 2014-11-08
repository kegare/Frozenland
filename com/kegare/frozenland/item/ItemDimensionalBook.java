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

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
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
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.dimensionId = dim;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
	{
		if (!world.isRemote && player.onGround)
		{
			NBTTagCompound nbt = itemstack.getTagCompound();
			EntityPlayerMP thePlayer = (EntityPlayerMP)player;
			int dim = thePlayer.dimension == dimensionId ? 0 : dimensionId;

			if (nbt != null && nbt.getString("Owner").equals(thePlayer.getGameProfile().getId().toString()))
			{
				if (thePlayer.capabilities.isCreativeMode || nbt.getLong("LastUseTime") + (world.difficultySetting.getDifficultyId() > 2 ? 6000L : 3600L) < world.getTotalWorldTime())
				{
					if (thePlayer.dimension == dimensionId && nbt.hasKey("LastDim"))
					{
						dim = nbt.getInteger("LastDim");

						if (!DimensionManager.isDimensionRegistered(dim) || dim == 1)
						{
							dim = 0;
						}
					}

					NBTTagCompound pos = new NBTTagCompound();
					pos.setDouble("PosX", thePlayer.posX);
					pos.setDouble("PosY", thePlayer.posY);
					pos.setDouble("PosZ", thePlayer.posZ);
					pos.setFloat("Yaw", thePlayer.rotationYaw);
					pos.setFloat("Pitch", thePlayer.rotationPitch);
					nbt.setTag("LastUsePos." + world.provider.dimensionId, pos);

					world.playSoundToNearExcept(thePlayer, "frozenland:dimensional_teleport", 0.5F, 1.0F);

					if (nbt.hasKey("LastUsePos." + dim))
					{
						pos = nbt.getCompoundTag("LastUsePos." + dim);
						double posX = pos.getDouble("PosX");
						double posY = pos.getDouble("PosY");
						double posZ = pos.getDouble("PosZ");
						float yaw = pos.getFloat("Yaw");
						float pitch = pos.getFloat("Pitch");

						thePlayer = FrozenUtils.forceTeleport(thePlayer, dim, true, posX, posY, posZ, yaw, pitch);
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
							thePlayer = FrozenUtils.forceTeleport(thePlayer, dim, true, x + 0.5D, y, z + 0.5D, thePlayer.rotationYaw, thePlayer.rotationPitch);
						}
						else
						{
							thePlayer = FrozenUtils.forceTeleport(thePlayer, dim, true);
						}
					}

					thePlayer.worldObj.playSoundAtEntity(thePlayer, "frozenland:dimensional_teleport", 0.75F, 1.0F);

					if (!thePlayer.capabilities.isCreativeMode)
					{
						nbt.setLong("LastUseTime", world.getTotalWorldTime());
					}

					nbt.setInteger("LastDim", world.provider.dimensionId);
				}
			}
		}

		return itemstack;
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean current)
	{
		super.onUpdate(itemstack, world, entity, slot, current);

		if (!world.isRemote && entity instanceof EntityPlayer)
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
				nbt.setString("OwnerName", player.getGameProfile().getName());
			}
		}
	}

	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player)
	{
		super.onCreated(itemstack, world, player);

		if (!world.isRemote)
		{
			NBTTagCompound nbt = itemstack.getTagCompound();

			if (nbt == null)
			{
				nbt = new NBTTagCompound();

				itemstack.setTagCompound(nbt);
			}

			nbt.setString("Owner", player.getGameProfile().getId().toString());
			nbt.setString("OwnerName", player.getGameProfile().getName());
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean advanced)
	{
		NBTTagCompound nbt = itemstack.getTagCompound();

		if (nbt != null && nbt.hasKey("OwnerName"))
		{
			list.add(EnumChatFormatting.ITALIC + I18n.format("item.dimensionalBook.owner") + ": " + EnumChatFormatting.RESET + nbt.getString("OwnerName"));
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