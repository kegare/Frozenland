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
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentVillageHouse1 extends ComponentVillage
{
	public ComponentVillageHouse1() {}

	protected ComponentVillageHouse1(ComponentVillageStartPiece villageStartPiece, int type, Random random, StructureBoundingBox structureBoundingBox, int coordBaseMode)
	{
		super(villageStartPiece, type);
		this.coordBaseMode = coordBaseMode;
		this.boundingBox = structureBoundingBox;
	}

	protected static ComponentVillageHouse1 findValidPlacement(ComponentVillageStartPiece villageStartPiece, List list, Random random, int x, int y, int z, int coordBaseMode, int type)
	{
		StructureBoundingBox structureBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 9, 9, 6, coordBaseMode);

		return canVillageGoDeeper(structureBoundingBox) && StructureComponent.findIntersecting(list, structureBoundingBox) == null ? new ComponentVillageHouse1(villageStartPiece, type, random, structureBoundingBox, coordBaseMode) : null;
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

			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 9 - 1, 0);
		}

		fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 7, 5, 4, Blocks.air, Blocks.air, false);
		fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 8, 0, 5, Blocks.stonebrick, Blocks.stonebrick, false);
		fillWithBlocks(world, structureBoundingBox, 0, 5, 0, 8, 5, 5, Blocks.stonebrick, Blocks.stonebrick, false);
		fillWithBlocks(world, structureBoundingBox, 0, 6, 1, 8, 6, 4, Blocks.stonebrick, Blocks.stonebrick, false);
		fillWithBlocks(world, structureBoundingBox, 0, 7, 2, 8, 7, 3, Blocks.stonebrick, Blocks.stonebrick, false);
		int i = getMetadataWithOffset(Blocks.oak_stairs, 3);
		int j = getMetadataWithOffset(Blocks.oak_stairs, 2);
		int k;
		int l;

		for (k = -1; k <= 2; ++k)
		{
			for (l = 0; l <= 8; ++l)
			{
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, i, l, 6 + k, k, structureBoundingBox);
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, j, l, 6 + k, 5 - k, structureBoundingBox);
			}
		}

		fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 1, 5, Blocks.stonebrick, Blocks.stonebrick, false);
		fillWithBlocks(world, structureBoundingBox, 1, 1, 5, 8, 1, 5, Blocks.stonebrick, Blocks.stonebrick, false);
		fillWithBlocks(world, structureBoundingBox, 8, 1, 0, 8, 1, 4, Blocks.stonebrick, Blocks.stonebrick, false);
		fillWithBlocks(world, structureBoundingBox, 2, 1, 0, 7, 1, 0, Blocks.stonebrick, Blocks.stonebrick, false);
		fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 0, 4, 0, Blocks.stonebrick, Blocks.stonebrick, false);
		fillWithBlocks(world, structureBoundingBox, 0, 2, 5, 0, 4, 5, Blocks.stonebrick, Blocks.stonebrick, false);
		fillWithBlocks(world, structureBoundingBox, 8, 2, 5, 8, 4, 5, Blocks.stonebrick, Blocks.stonebrick, false);
		fillWithBlocks(world, structureBoundingBox, 8, 2, 0, 8, 4, 0, Blocks.stonebrick, Blocks.stonebrick, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 2, 1, 0, 4, 4, Blocks.planks, 1, Blocks.planks, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 2, 5, 7, 4, 5, Blocks.planks, 1, Blocks.planks, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 8, 2, 1, 8, 4, 4, Blocks.planks, 1, Blocks.planks, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 2, 0, 7, 4, 0, Blocks.planks, 1, Blocks.planks, 1, false);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 4, 2, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 5, 2, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 6, 2, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 4, 3, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 5, 3, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 6, 3, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 0, 2, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 0, 2, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 0, 3, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 0, 3, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 8, 2, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 8, 2, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 8, 3, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 8, 3, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 2, 2, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 3, 2, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 5, 2, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.glass, 0, 6, 2, 5, structureBoundingBox);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 4, 1, 7, 4, 1, Blocks.planks, 1, Blocks.planks, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 4, 4, 7, 4, 4, Blocks.planks, 1, Blocks.planks, 1, false);
		fillWithBlocks(world, structureBoundingBox, 1, 3, 4, 7, 3, 4, Blocks.bookshelf, Blocks.bookshelf, false);
		placeBlockAtCurrentPosition(world, Blocks.planks, 1, 7, 1, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getMetadataWithOffset(Blocks.oak_stairs, 0), 7, 1, 3, structureBoundingBox);
		k = getMetadataWithOffset(Blocks.oak_stairs, 3);
		placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, k, 6, 1, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, k, 5, 1, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, k, 4, 1, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, k, 3, 1, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 6, 1, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.wooden_pressure_plate, 0, 6, 2, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4, 1, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.wooden_pressure_plate, 0, 4, 2, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.crafting_table, 0, 7, 1, 1, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.air, 0, 1, 1, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.air, 0, 1, 2, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 1, 3, 1, structureBoundingBox);
		placeDoorAtCurrentPosition(world, structureBoundingBox, random, 1, 1, 0, getMetadataWithOffset(Blocks.wooden_door, 1));

		if (getBlockAtCurrentPosition(world, 1, 0, -1, structureBoundingBox) == Blocks.air && getBlockAtCurrentPosition(world, 1, -1, -1, structureBoundingBox) != Blocks.air)
		{
			placeBlockAtCurrentPosition(world, Blocks.stone_brick_stairs, getMetadataWithOffset(Blocks.stone_brick_stairs, 3), 1, 0, -1, structureBoundingBox);
		}

		for (k = 0; k < 6; ++k)
		{
			for (l = 0; l < 9; ++l)
			{
				clearCurrentPositionBlocksUpwards(world, l, 9, k, structureBoundingBox);
				func_151554_b(world, Blocks.stonebrick, 0, l, -1, k, structureBoundingBox);
			}
		}

		spawnVillagers(world, structureBoundingBox, 2, 1, 2, 2);

		return true;
	}

	@Override
	protected int getVillagerType(int type)
	{
		return 1;
	}
}