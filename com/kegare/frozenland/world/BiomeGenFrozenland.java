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

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenFrozenland extends BiomeGenBase
{
	public static BiomeGenBase frozenland;

	public static final Height height_Frozenland = new Height(0.3F, 0.85F);

	public BiomeGenFrozenland(int biomeID, boolean register)
	{
		super(biomeID, register);
		this.setBiomeName("Frozenland");
		this.setColor(10526880);
		this.topBlock = Blocks.ice;
		this.fillerBlock = Blocks.ice;
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.setHeight(height_Frozenland);
		this.setTemperatureRainfall(-2.0F, 1.0F);
		this.setEnableSnow();
	}

	@Override
	public void addDefaultFlowers() {}

	@Override
	public void plantFlower(World world, Random rand, int x, int y, int z) {}
}