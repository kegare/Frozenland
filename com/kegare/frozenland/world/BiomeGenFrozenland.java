/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenFrozenland extends BiomeGenBase
{
	public static BiomeGenBase frozenland;

	public static final Height height_Frozenland = new Height(0.3F, 0.85F);

	public BiomeGenFrozenland(int biomeID)
	{
		super(biomeID);
		this.setBiomeName("Frozenland");
		this.setColor(0xD4F0FF);
		this.topBlock = Blocks.ice;
		this.fillerBlock = Blocks.ice;
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.setHeight(height_Frozenland);
		this.setTemperatureRainfall(-2.0F, 1.0F);
		this.setEnableSnow();
	}

	@Override
	public BiomeDecorator createBiomeDecorator()
	{
		return new BiomeFrozenlandDecorator();
	}

	@Override
	public BiomeGenBase createMutation()
	{
		return this;
	}

	@Override
	public void genTerrainBlocks(World world, Random random, Block[] blocks, byte[] metadata, int chunkX, int chunkZ, double noise)
	{
		Block top = topBlock;
		byte meta = (byte)(field_150604_aj & 255);
		Block filler = fillerBlock;
		int i = -1;
		int j = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
		int x = chunkX & 15;
		int z = chunkZ & 15;
		int height = blocks.length / 256;

		for (int y = 255; y >= 0; --y)
		{
			int index = (z * 16 + x) * height + y;

			if (y <= 0 + random.nextInt(3))
			{
				blocks[index] = Blocks.bedrock;
			}
			else
			{
				Block block = blocks[index];

				if (block != null && block.getMaterial() != Material.air)
				{
					if (block == Blocks.stone)
					{
						if (i == -1)
						{
							if (j <= 0)
							{
								top = null;
								meta = 0;
								filler = Blocks.stone;
							}
							else if (y >= 59 && y <= 64)
							{
								top = topBlock;
								meta = (byte)(field_150604_aj & 255);
								filler = fillerBlock;
							}

							if (y < 63 && (top == null || top.getMaterial() == Material.air))
							{
								if (getFloatTemperature(chunkX, y, chunkZ) < 0.15F)
								{
									top = Blocks.ice;
									meta = 0;
								}
								else
								{
									top = Blocks.water;
									meta = 0;
								}
							}

							i = j;

							if (y >= 62)
							{
								blocks[index] = top;
								metadata[index] = meta;
							}
							else if (y < 56 - j)
							{
								top = null;
								filler = Blocks.stone;
								blocks[index] = Blocks.gravel;
							}
							else
							{
								blocks[index] = filler;
							}
						}
						else if (i > 0)
						{
							--i;
							blocks[index] = filler;
						}
					}
				}
				else
				{
					i = -1;
				}
			}
		}
	}

	@Override
	public float getSpawningChance()
	{
		return 0.01F;
	}

	@Override
	public void addDefaultFlowers() {}

	@Override
	public void plantFlower(World world, Random rand, int x, int y, int z) {}
}