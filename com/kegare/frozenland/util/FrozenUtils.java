/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import com.kegare.frozenland.block.BlockSlabPackedIce;
import com.kegare.frozenland.block.BlockSlabSlipperyIce;
import com.kegare.frozenland.block.BlockSlipperyIce;
import com.kegare.frozenland.block.BlockStairsPackedIce;
import com.kegare.frozenland.block.BlockStairsSlipperyIce;
import com.kegare.frozenland.core.Frozenland;
import com.kegare.frozenland.world.TeleporterDummy;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;

public class FrozenUtils
{
	public static ModContainer getModContainer()
	{
		ModContainer mod = Loader.instance().getIndexedModList().get(Frozenland.MODID);

		if (mod == null)
		{
			mod = Loader.instance().activeModContainer();

			if (mod == null || mod.getModId() != Frozenland.MODID)
			{
				return null;
			}
		}

		return mod;
	}

	public static boolean isIceBlock(Block block)
	{
		return block != null && (block instanceof BlockIce || block instanceof BlockPackedIce || block instanceof BlockSlipperyIce ||
			block instanceof BlockSlabPackedIce || block instanceof BlockStairsPackedIce ||
			block instanceof BlockSlabSlipperyIce || block instanceof BlockStairsSlipperyIce);
	}

	public static boolean teleportPlayer(EntityPlayerMP player, int dim)
	{
		if (!DimensionManager.isDimensionRegistered(dim))
		{
			return false;
		}

		if (dim != player.dimension)
		{
			player.isDead = false;
			player.forceSpawn = true;
			player.timeUntilPortal = player.getPortalCooldown();
			player.mcServer.getConfigurationManager().transferPlayerToDimension(player, dim, new TeleporterDummy(player.mcServer.worldServerForDimension(dim)));
			player.addExperienceLevel(0);
		}

		WorldServer world = player.getServerForPlayer();
		ChunkCoordinates spawn;
		String key = "Caveworld:LastForceTeleport." + dim;
		int x, y, z;

		if (player.getEntityData().hasKey(key))
		{
			NBTTagCompound data = player.getEntityData().getCompoundTag(key);
			x = data.getInteger("PosX");
			y = data.getInteger("PosY");
			z = data.getInteger("PosZ");
			spawn = new ChunkCoordinates(x, y, z);
		}
		else
		{
			spawn = world.getSpawnPoint();
		}

		x = spawn.posX;
		y = spawn.posY;
		z = spawn.posZ;

		if (world.isAirBlock(x, y, z) && world.isAirBlock(x, y + 1, z))
		{
			while (world.isAirBlock(x, y - 1, z))
			{
				--y;
			}

			if (!world.isAirBlock(x, y - 1, z) && !world.getBlock(x, y - 1, z).getMaterial().isLiquid())
			{
				player.playerNetServerHandler.setPlayerLocation(x + 0.5D, y + 0.8D, z + 0.5D, player.rotationYaw, player.rotationPitch);

				NBTTagCompound data = new NBTTagCompound();
				data.setInteger("PosX", x);
				data.setInteger("PosY", y);
				data.setInteger("PosZ", z);
				player.getEntityData().setTag(key, data);
			}
		}
		else
		{
			int range = 16;

			for (x = spawn.posX - range; x < spawn.posX + range; ++x)
			{
				for (z = spawn.posZ - range; z < spawn.posZ + range; ++z)
				{
					for (y = world.getActualHeight() - 3; y > world.provider.getAverageGroundLevel(); --y)
					{
						if (world.isAirBlock(x, y, z) && world.isAirBlock(x, y + 1, z) &&
							world.isAirBlock(x - 1, y, z) && world.isAirBlock(x - 1, y + 1, z) &&
							world.isAirBlock(x + 1, y, z) && world.isAirBlock(x + 1, y + 1, z) &&
							world.isAirBlock(x, y, z - 1) && world.isAirBlock(x, y + 1, z - 1) &&
							world.isAirBlock(x, y, z + 1) && world.isAirBlock(x, y + 1, z + 1))
						{
							while (world.isAirBlock(x, y - 1, z))
							{
								--y;
							}

							if (!world.isAirBlock(x, y - 1, z) && !world.getBlock(x, y - 1, z).getMaterial().isLiquid())
							{
								player.playerNetServerHandler.setPlayerLocation(x + 0.5D, y + 0.8D, z + 0.5D, player.rotationYaw, player.rotationPitch);

								NBTTagCompound data = new NBTTagCompound();
								data.setInteger("PosX", x);
								data.setInteger("PosY", y);
								data.setInteger("PosZ", z);
								player.getEntityData().setTag(key, data);

								return true;
							}
						}
					}
				}
			}

			x = 0;
			y = 30;
			z = 0;
			player.playerNetServerHandler.setPlayerLocation(x + 0.5D, y + 0.8D, z + 0.5D, player.rotationYaw, player.rotationPitch);
			world.setBlockToAir(x, y, z);
			world.setBlockToAir(x, y + 1, z);
			world.setBlock(x, y - 1, z, Blocks.stone);
		}

		return false;
	}

	public static boolean teleportPlayer(EntityPlayerMP player, int dim, double posX, double posY, double posZ, float yaw, float pitch)
	{
		if (!DimensionManager.isDimensionRegistered(dim))
		{
			return false;
		}

		if (dim != player.dimension)
		{
			player.isDead = false;
			player.forceSpawn = true;
			player.timeUntilPortal = player.getPortalCooldown();
			player.mcServer.getConfigurationManager().transferPlayerToDimension(player, dim, new TeleporterDummy(player.mcServer.worldServerForDimension(dim)));
			player.addExperienceLevel(0);
		}

		WorldServer world = player.getServerForPlayer();
		int x = MathHelper.floor_double(posX);
		int y = MathHelper.floor_double(posY);
		int z = MathHelper.floor_double(posZ);

		if (world.isAirBlock(x, y, z) && world.isAirBlock(x, y + 1, z))
		{
			while (world.isAirBlock(x, y - 1, z))
			{
				--y;
			}

			if (!world.isAirBlock(x, y - 1, z) && !world.getBlock(x, y - 1, z).getMaterial().isLiquid())
			{
				player.playerNetServerHandler.setPlayerLocation(posX, posY + 0.5D, posZ, yaw, pitch);

				NBTTagCompound data = new NBTTagCompound();
				data.setInteger("PosX", x);
				data.setInteger("PosY", y);
				data.setInteger("PosZ", z);
				player.getEntityData().setTag("Caveworld:LastForceTeleport." + dim, data);

				return true;
			}
		}

		return teleportPlayer(player, dim);
	}
}