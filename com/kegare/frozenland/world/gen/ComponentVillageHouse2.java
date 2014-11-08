/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.world.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraftforge.common.ChestGenHooks;

public class ComponentVillageHouse2 extends ComponentVillage
{
	private boolean hasMadeChest;

	public ComponentVillageHouse2() {}

	protected ComponentVillageHouse2(ComponentVillageStartPiece villageStartPiece, int type, Random random, StructureBoundingBox structureBoundingBox, int coordBaseMode)
	{
		super(villageStartPiece, type);
		this.coordBaseMode = coordBaseMode;
		this.boundingBox = structureBoundingBox;
	}

	protected static ComponentVillageHouse2 findValidPlacement(ComponentVillageStartPiece villageStartPiece, List list, Random random, int x, int y, int z, int coordBaseMode, int type)
	{
		StructureBoundingBox structureBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 10, 6, 7, coordBaseMode);

		return canVillageGoDeeper(structureBoundingBox) && StructureComponent.findIntersecting(list, structureBoundingBox) == null ? new ComponentVillageHouse2(villageStartPiece, type, random, structureBoundingBox, coordBaseMode) : null;
	}

	@Override
	protected void func_143012_a(NBTTagCompound nbtTagCompound)
	{
		super.func_143012_a(nbtTagCompound);
		nbtTagCompound.setBoolean("Chest", hasMadeChest);
	}

	@Override
	protected void func_143011_b(NBTTagCompound nbtTagCompound)
	{
		super.func_143011_b(nbtTagCompound);
		hasMadeChest = nbtTagCompound.getBoolean("Chest");
	}

	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox)
	{
		if (averageGroundLevel < 0)
		{
			averageGroundLevel = getAverageGroundLevel(world, structureBoundingBox);

			if (averageGroundLevel < 0)
			{
				return true;
			}

			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 6 - 1, 0);
		}

		fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 9, 4, 6, Blocks.air, Blocks.air, false);
		fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 9, 0, 6, Blocks.stonebrick, Blocks.stonebrick, false);
		fillWithBlocks(world, structureBoundingBox, 0, 4, 0, 9, 4, 6, Blocks.stonebrick, Blocks.stonebrick, false);
		fillWithBlocks(world, structureBoundingBox, 0, 5, 0, 9, 5, 6, Blocks.stone_slab, Blocks.stone_slab, false);
		fillWithBlocks(world, structureBoundingBox, 1, 5, 1, 8, 5, 5, Blocks.air, Blocks.air, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 1, 0, 2, 3, 0, Blocks.planks, 1, Blocks.planks, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 1, 0, 0, 4, 0, Blocks.log, 1, Blocks.log, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 3, 1, 0, 3, 4, 0, Blocks.log, 1, Blocks.log, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 1, 6, 0, 4, 6, Blocks.log, 1, Blocks.log, 1, false);
		placeBlockAtCurrentPosition(world, Blocks.planks, 1, 3, 3, 1, structureBoundingBox);
		fillWithMetadataBlocks(world, structureBoundingBox, 3, 1, 2, 3, 3, 2, Blocks.planks, 1, Blocks.planks, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 4, 1, 3, 5, 3, 3, Blocks.planks, 1, Blocks.planks, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 1, 1, 0, 3, 5, Blocks.planks, 1, Blocks.planks, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 1, 6, 5, 3, 6, Blocks.planks, 1, Blocks.planks, 1, false);
		fillWithBlocks(world, structureBoundingBox, 5, 1, 0, 5, 3, 0, Blocks.fence, Blocks.fence, false);
		fillWithBlocks(world, structureBoundingBox, 9, 1, 0, 9, 3, 0, Blocks.fence, Blocks.fence, false);
		fillWithBlocks(world, structureBoundingBox, 6, 1, 4, 9, 4, 6, Blocks.stonebrick, Blocks.stonebrick, false);
		placeBlockAtCurrentPosition(world, Blocks.flowing_lava, 0, 7, 1, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.flowing_lava, 0, 8, 1, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.iron_bars, 0, 9, 2, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.iron_bars, 0, 9, 2, 4, structureBoundingBox);
		fillWithBlocks(world, structureBoundingBox, 7, 2, 4, 8, 2, 5, Blocks.air, Blocks.air, false);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, 6, 1, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.furnace, 0, 6, 2, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.furnace, 0, 6, 3, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 8, 1, 1, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 0, 2, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 0, 2, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 2, 2, 6, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 4, 2, 6, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 2, 1, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.wooden_pressure_plate, 0, 2, 2, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.planks, 1, 1, 1, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getMetadataWithOffset(Blocks.oak_stairs, 3), 2, 1, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getMetadataWithOffset(Blocks.oak_stairs, 1), 1, 1, 4, structureBoundingBox);
		int i;
		int j;

		if (!hasMadeChest)
		{
			i = getYWithOffset(1);
			j = getXWithOffset(5, 5);
			int k = getZWithOffset(5, 5);

			if (structureBoundingBox.isVecInside(j, i, k))
			{
				hasMadeChest = true;
				generateStructureChestContents(world, structureBoundingBox, random, 5, 1, 5, ChestGenHooks.getItems(ChestGenHooks.VILLAGE_BLACKSMITH, random), ChestGenHooks.getCount(ChestGenHooks.VILLAGE_BLACKSMITH, random));
			}
		}

		for (i = 6; i <= 8; ++i)
		{
			if (getBlockAtCurrentPosition(world, i, 0, -1, structureBoundingBox) == Blocks.air && getBlockAtCurrentPosition(world, i, -1, -1, structureBoundingBox) != Blocks.air)
			{
				placeBlockAtCurrentPosition(world, Blocks.stone_brick_stairs, getMetadataWithOffset(Blocks.stone_brick_stairs, 3), i, 0, -1, structureBoundingBox);
			}
		}

		for (i = 0; i < 7; ++i)
		{
			for (j = 0; j < 10; ++j)
			{
				clearCurrentPositionBlocksUpwards(world, j, 6, i, structureBoundingBox);
				func_151554_b(world, Blocks.stonebrick, 0, j, -1, i, structureBoundingBox);
			}
		}

		spawnVillagers(world, structureBoundingBox, 7, 1, 1, 1);

		return true;
	}

	@Override
	protected int getVillagerType(int type)
	{
		return 3;
	}
}