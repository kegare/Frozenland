/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.world.gen;

import static net.minecraftforge.common.ChestGenHooks.*;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DungeonHooks;

public class WorldGenDungeonsFrozenland extends WorldGenDungeons
{
	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		byte b = 3;
		int i = random.nextInt(2) + 2;
		int j = random.nextInt(2) + 2;
		int k = 0;
		int l;
		int m;
		int n;

		for (l = x - i - 1; l <= x + i + 1; ++l)
		{
			for (m = y - 1; m <= y + b + 1; ++m)
			{
				for (n = z - j - 1; n <= z + j + 1; ++n)
				{
					Material material = world.getBlock(l, m, n).getMaterial();

					if (m == y - 1 && !material.isSolid())
					{
						return false;
					}

					if (m == y + b + 1 && !material.isSolid())
					{
						return false;
					}

					if ((l == x - i - 1 || l == x + i + 1 || n == z - j - 1 || n == z + j + 1) && m == y && world.isAirBlock(l, m, n) && world.isAirBlock(l, m + 1, n))
					{
						++k;
					}
				}
			}
		}

		if (k >= 1 && k <= 5)
		{
			for (l = x - i - 1; l <= x + i + 1; ++l)
			{
				for (m = y + b; m >= y - 1; --m)
				{
					for (n = z - j - 1; n <= z + j + 1; ++n)
					{
						if (l != x - i - 1 && m != y - 1 && n != z - j - 1 && l != x + i + 1 && m != y + b + 1 && n != z + j + 1)
						{
							world.setBlockToAir(l, m, n);
						}
						else if (m >= 0 && !world.getBlock(l, m - 1, n).getMaterial().isSolid())
						{
							world.setBlockToAir(l, m, n);
						}
						else if (world.getBlock(l, m, n).getMaterial().isSolid())
						{
							if (m == y - 1 && random.nextInt(4) != 0)
							{
								world.setBlock(l, m, n, Blocks.stonebrick, 1, 2);
							}
							else
							{
								world.setBlock(l, m, n, Blocks.stonebrick, 0, 2);
							}
						}
					}
				}
			}

			l = 0;

			while (l < 2)
			{
				m = 0;

				while (true)
				{
					if (m < 3)
					{
						outside:
						{
							n = x + random.nextInt(i * 2 + 1) - i;
							int o = z + random.nextInt(j * 2 + 1) - j;

							if (world.isAirBlock(n, y, o))
							{
								int count = 0;

								if (world.getBlock(n - 1, y, o).getMaterial().isSolid())
								{
									++count;
								}

								if (world.getBlock(n + 1, y, o).getMaterial().isSolid())
								{
									++count;
								}

								if (world.getBlock(n, y, o - 1).getMaterial().isSolid())
								{
									++count;
								}

								if (world.getBlock(n, y, o + 1).getMaterial().isSolid())
								{
									++count;
								}

								if (count == 1)
								{
									world.setBlock(n, y, o, Blocks.chest, 0, 2);
									TileEntityChest chest = (TileEntityChest)world.getTileEntity(n, y, o);

									if (chest != null)
									{
										WeightedRandomChestContent.generateChestContents(random, ChestGenHooks.getItems(DUNGEON_CHEST, random), chest, ChestGenHooks.getCount(DUNGEON_CHEST, random));
									}

									break outside;
								}
							}

							++m;
							continue;
						}
					}

					++l;
					break;
				}
			}

			world.setBlock(x, y, z, Blocks.mob_spawner, 0, 2);
			TileEntityMobSpawner mobSpawner = (TileEntityMobSpawner)world.getTileEntity(x, y, z);

			if (mobSpawner != null)
			{
				mobSpawner.func_145881_a().setEntityName(DungeonHooks.getRandomDungeonMob(random));
			}
			else
			{
				System.err.println("Failed to fetch mob spawner entity at (" + x + ", " + y + ", " + z + ")");
			}

			return true;
		}

		return false;
	}
}