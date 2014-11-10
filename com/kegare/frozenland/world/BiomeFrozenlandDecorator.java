/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.world;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BiomeFrozenlandDecorator extends BiomeDecorator
{
	public WorldGenerator waterGen;

	public BiomeFrozenlandDecorator()
	{
		this.dirtGen = new WorldGenMinable(Blocks.dirt, 32);
		this.gravelGen = new WorldGenMinable(Blocks.gravel, 32);
		this.coalGen = new WorldGenMinable(Blocks.coal_ore, 16);
		this.ironGen = new WorldGenMinable(Blocks.iron_ore, 10);
		this.goldGen = new WorldGenMinable(Blocks.gold_ore, 8);
		this.redstoneGen = new WorldGenMinable(Blocks.redstone_ore, 7);
		this.diamondGen = new WorldGenMinable(Blocks.diamond_ore, 7);
		this.lapisGen = new WorldGenMinable(Blocks.lapis_ore, 8);
		this.reedGen = new WorldGenReed();
		this.waterlilyGen = new WorldGenWaterlily();
		this.waterGen = new WorldGenLiquids(Blocks.flowing_water);
		this.generateLakes = true;
		this.treesPerChunk = -999;
		this.grassPerChunk = -999;
		this.flowersPerChunk = -999;
		this.cactiPerChunk = -999;
		this.bigMushroomsPerChunk = -999;
		this.deadBushPerChunk = -999;
	}

	@Override
	protected void genDecorations(BiomeGenBase biome)
	{
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(currentWorld, randomGenerator, chunk_X, chunk_Z));

		generateOres();

		int i;
		int x;
		int y;
		int z;

		boolean doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, Decorate.EventType.LILYPAD);
		for (i = 0; doGen && i < waterlilyPerChunk; ++i)
		{
			x = chunk_X + randomGenerator.nextInt(16) + 8;
			z = chunk_Z + randomGenerator.nextInt(16) + 8;

			for (y = randomGenerator.nextInt(currentWorld.getHeightValue(x, z) * 2); y > 0 && currentWorld.isAirBlock(x, y - 1, z); --y)
			{
				;
			}

			waterlilyGen.generate(currentWorld, randomGenerator, x, y, z);
		}

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, Decorate.EventType.SHROOM);
		for (i = 0; doGen && i < mushroomsPerChunk; ++i)
		{
			if (randomGenerator.nextInt(4) == 0)
			{
				x = chunk_X + randomGenerator.nextInt(16) + 8;
				z = chunk_Z + randomGenerator.nextInt(16) + 8;
				y = currentWorld.getHeightValue(x, z);

				mushroomBrownGen.generate(currentWorld, randomGenerator, x, y, z);
			}

			if (randomGenerator.nextInt(8) == 0)
			{
				x = chunk_X + randomGenerator.nextInt(16) + 8;
				z = chunk_Z + randomGenerator.nextInt(16) + 8;
				y = randomGenerator.nextInt(currentWorld.getHeightValue(x, z) * 2);

				mushroomRedGen.generate(currentWorld, randomGenerator, x, y, z);
			}
		}

		if (doGen && randomGenerator.nextInt(4) == 0)
		{
			x = chunk_X + randomGenerator.nextInt(16) + 8;
			z = chunk_Z + randomGenerator.nextInt(16) + 8;
			y = randomGenerator.nextInt(currentWorld.getHeightValue(x, z) * 2);

			mushroomBrownGen.generate(currentWorld, randomGenerator, x, y, z);
		}

		if (doGen && randomGenerator.nextInt(8) == 0)
		{
			x = chunk_X + randomGenerator.nextInt(16) + 8;
			z = chunk_Z + randomGenerator.nextInt(16) + 8;
			y = randomGenerator.nextInt(currentWorld.getHeightValue(x, z) * 2);

			mushroomRedGen.generate(currentWorld, randomGenerator, x, y, z);
		}

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, Decorate.EventType.REED);
		for (i = 0; doGen && i < reedsPerChunk; ++i)
		{
			x = chunk_X + randomGenerator.nextInt(16) + 8;
			z = chunk_Z + randomGenerator.nextInt(16) + 8;
			y = randomGenerator.nextInt(currentWorld.getHeightValue(x, z) * 2);

			reedGen.generate(currentWorld, randomGenerator, x, y, z);
		}

		for (i = 0; doGen && i < 10; ++i)
		{
			x = chunk_X + randomGenerator.nextInt(16) + 8;
			z = chunk_Z + randomGenerator.nextInt(16) + 8;
			y = randomGenerator.nextInt(currentWorld.getHeightValue(x, z) * 2);

			reedGen.generate(currentWorld, randomGenerator, x, y, z);
		}

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, Decorate.EventType.LAKE);
		if (doGen && generateLakes)
		{
			for (i = 0; i < 45; ++i)
			{
				x = chunk_X + randomGenerator.nextInt(16) + 8;
				y = randomGenerator.nextInt(randomGenerator.nextInt(248) + 8);
				z = chunk_Z + randomGenerator.nextInt(16) + 8;

				waterGen.generate(currentWorld, randomGenerator, x, y, z);
			}
		}

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(currentWorld, randomGenerator, chunk_X, chunk_Z));
	}
}