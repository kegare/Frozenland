/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.block;

import java.util.Random;

import net.minecraft.block.BlockStairs;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import com.kegare.frozenland.core.Frozenland;

public class BlockStairsPackedIce extends BlockStairs
{
	public BlockStairsPackedIce(String name)
	{
		super(Blocks.packed_ice, 0);
		this.setBlockName(name);
		this.setCreativeTab(Frozenland.tabFrozenland);
		this.slipperiness = Blocks.packed_ice.slipperiness;
		this.useNeighborBrightness = true;
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		return Blocks.packed_ice.getBlockHardness(world, x, y, z);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	protected boolean canSilkHarvest()
	{
		return true;
	}
}