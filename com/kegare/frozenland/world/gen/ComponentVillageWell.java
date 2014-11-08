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

public class ComponentVillageWell extends ComponentVillage
{
	public ComponentVillageWell() {}

	protected ComponentVillageWell(ComponentVillageStartPiece villageStartPiece, int type, Random random, int x, int z)
	{
		super(villageStartPiece, type);
		this.coordBaseMode = random.nextInt(4);

		switch (coordBaseMode)
		{
			case 0:
			case 2:
				this.boundingBox = new StructureBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
				break;
			default:
				this.boundingBox = new StructureBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
		}
	}

	@Override
	public void buildComponent(StructureComponent structureComponent, List list, Random random)
	{
		StructureVillagePieces.getNextComponentVillagePath((ComponentVillageStartPiece)structureComponent, list, random, boundingBox.minX - 1, boundingBox.maxY - 4, boundingBox.minZ + 1, 1, getComponentType());
		StructureVillagePieces.getNextComponentVillagePath((ComponentVillageStartPiece)structureComponent, list, random, boundingBox.maxX + 1, boundingBox.maxY - 4, boundingBox.minZ + 1, 3, getComponentType());
		StructureVillagePieces.getNextComponentVillagePath((ComponentVillageStartPiece)structureComponent, list, random, boundingBox.minX + 1, boundingBox.maxY - 4, boundingBox.minZ - 1, 2, getComponentType());
		StructureVillagePieces.getNextComponentVillagePath((ComponentVillageStartPiece)structureComponent, list, random, boundingBox.minX + 1, boundingBox.maxY - 4, boundingBox.maxZ + 1, 0, getComponentType());
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

			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 3, 0);
		}

		fillWithBlocks(world, structureBoundingBox, 1, 0, 1, 4, 12, 4, Blocks.stonebrick, Blocks.flowing_water, false);
		placeBlockAtCurrentPosition(world, Blocks.air, 0, 2, 12, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.air, 0, 3, 12, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.air, 0, 2, 12, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.air, 0, 3, 12, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 1, 13, 1, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 1, 14, 1, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4, 13, 1, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4, 14, 1, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 1, 13, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 1, 14, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4, 13, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4, 14, 4, structureBoundingBox);
		fillWithBlocks(world, structureBoundingBox, 1, 15, 1, 4, 15, 4, Blocks.stonebrick, Blocks.stonebrick, false);

		for (int i = 0; i <= 5; ++i)
		{
			for (int j = 0; j <= 5; ++j)
			{
				if (j == 0 || j == 5 || i == 0 || i == 5)
				{
					placeBlockAtCurrentPosition(world, Blocks.gravel, 0, j, 11, i, structureBoundingBox);
					clearCurrentPositionBlocksUpwards(world, j, 12, i, structureBoundingBox);
				}
			}
		}

		return true;
	}
}