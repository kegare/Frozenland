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

public class ComponentVillageTorch extends ComponentVillage
{
	public ComponentVillageTorch() {}

	protected ComponentVillageTorch(ComponentVillageStartPiece villageStartPiece, int type, Random random, StructureBoundingBox structureBoundingBox, int coordBaseMode)
	{
		super(villageStartPiece, type);
		this.coordBaseMode = coordBaseMode;
		this.boundingBox = structureBoundingBox;
	}

	protected static StructureBoundingBox findValidPlacement(ComponentVillageStartPiece villageStartPiece, List list, Random random, int x, int y, int z, int coordBaseMode)
	{
		StructureBoundingBox structureBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 3, 4, 2, coordBaseMode);

		return StructureComponent.findIntersecting(list, structureBoundingBox) != null ? null : structureBoundingBox;
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

			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 4 - 1, 0);
		}

		fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 2, 3, 1, Blocks.air, Blocks.air, false);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 1, 0, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 1, 1, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 1, 2, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.wool, 15, 1, 3, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 0, 3, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 1, 3, 1, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 2, 3, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 1, 3, -1, structureBoundingBox);

		return true;
	}
}