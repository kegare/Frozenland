/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;

import com.kegare.frozenland.core.Frozenland;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;

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

	public static EntityPlayerMP respawnPlayer(EntityPlayerMP player, int dim)
	{
		player.isDead = false;
		player.forceSpawn = true;
		player.timeUntilPortal = player.getPortalCooldown();
		player.playerNetServerHandler.playerEntity = player.mcServer.getConfigurationManager().respawnPlayer(player, dim, true);

		return player.playerNetServerHandler.playerEntity;
	}

	public static EntityPlayerMP forceTeleport(EntityPlayerMP player, int dim, boolean changed)
	{
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		int dimOld = player.dimension;
		final WorldServer world = server.worldServerForDimension(dim);

		if (dim != player.dimension)
		{
			player = respawnPlayer(player, dim);

			if (changed)
			{
				FMLCommonHandler.instance().bus().post(new PlayerChangedDimensionEvent(player, dimOld, dim));
			}
		}

		ChunkCoordinates spawn = world.getSpawnPoint();
		int var1 = 64;

		for (int x = spawn.posX - var1; x < spawn.posX + var1; ++x)
		{
			for (int z = spawn.posZ - var1; z < spawn.posZ + var1; ++z)
			{
				for (int y = world.getActualHeight() - 3; y > world.provider.getAverageGroundLevel(); --y)
				{
					if (world.isAirBlock(x, y, z) && world.isAirBlock(x, y + 1, z) &&
						world.isAirBlock(x - 1, y, z) && world.isAirBlock(x - 1, y + 1, z) &&
						world.isAirBlock(x + 1, y, z) && world.isAirBlock(x + 1, y + 1, z) &&
						world.isAirBlock(x, y, z - 1) && world.isAirBlock(x, y + 1, z - 1) &&
						world.isAirBlock(x, y, z + 1) && world.isAirBlock(x, y + 1, z + 1) &&
						!world.getBlock(x, y - 1, z).getMaterial().isLiquid())
					{
						while (world.isAirBlock(x, y - 1, z))
						{
							--y;
						}

						if (!world.getBlock(x, y - 1, z).getMaterial().isLiquid())
						{
							player.playerNetServerHandler.setPlayerLocation(x + 0.5D, y + 0.8D, z + 0.5D, player.rotationYaw, player.rotationPitch);
							player.addExperienceLevel(0);

							return player;
						}
					}
				}
			}
		}

		return player;
	}

	public static EntityPlayerMP forceTeleport(EntityPlayerMP player, int dim, boolean changed, double posX, double posY, double posZ, float yaw, float pitch)
	{
		int dimOld = player.dimension;

		if (dim != player.dimension)
		{
			player = respawnPlayer(player, dim);

			if (changed)
			{
				FMLCommonHandler.instance().bus().post(new PlayerChangedDimensionEvent(player, dimOld, dim));
			}
		}

		player.playerNetServerHandler.setPlayerLocation(posX, posY + 0.5D, posZ, yaw, pitch);
		player.addExperienceLevel(0);

		return player;
	}
}