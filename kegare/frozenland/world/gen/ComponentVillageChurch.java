package kegare.frozenland.world.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentVillageChurch extends ComponentVillage
{
	public ComponentVillageChurch() {}

	protected ComponentVillageChurch(ComponentVillageStartPiece villageStartPiece, int type, Random random, StructureBoundingBox structureBoundingBox, int coordBaseMode)
	{
		super(villageStartPiece, type);
		this.coordBaseMode = coordBaseMode;
		this.boundingBox = structureBoundingBox;
	}

	protected static ComponentVillageChurch findValidPlacement(ComponentVillageStartPiece villageStartPiece, List list, Random random, int x, int y, int z, int coordBaseMode, int type)
	{
		StructureBoundingBox structureBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 5, 12, 9, coordBaseMode);

		return canVillageGoDeeper(structureBoundingBox) && StructureComponent.findIntersecting(list, structureBoundingBox) == null ? new ComponentVillageChurch(villageStartPiece, type, random, structureBoundingBox, coordBaseMode) : null;
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

			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 12 - 1, 0);
		}

		fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 3, 3, 7, 0, 0, false);
		fillWithBlocks(world, structureBoundingBox, 1, 5, 1, 3, 9, 3, 0, 0, false);
		fillWithBlocks(world, structureBoundingBox, 1, 0, 0, 3, 0, 8, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 3, 10, 0, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 10, 3, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 4, 1, 1, 4, 10, 3, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 0, 0, 4, 0, 4, 7, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 4, 0, 4, 4, 4, 7, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 1, 1, 8, 3, 4, 8, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 1, 5, 4, 3, 10, 4, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 1, 5, 5, 3, 5, 7, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 0, 9, 0, 4, 9, 4, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 0, 4, 0, 4, 4, 4, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 0, 11, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 4, 11, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 2, 11, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 2, 11, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 1, 1, 6, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 1, 1, 7, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 2, 1, 7, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 3, 1, 6, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 3, 1, 7, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stairsStoneBrick.blockID, getMetadataWithOffset(Block.stairsStoneBrick.blockID, 3), 1, 1, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stairsStoneBrick.blockID, getMetadataWithOffset(Block.stairsStoneBrick.blockID, 3), 2, 1, 6, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stairsStoneBrick.blockID, getMetadataWithOffset(Block.stairsStoneBrick.blockID, 3), 3, 1, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stairsStoneBrick.blockID, getMetadataWithOffset(Block.stairsStoneBrick.blockID, 1), 1, 2, 7, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stairsStoneBrick.blockID, getMetadataWithOffset(Block.stairsStoneBrick.blockID, 0), 3, 2, 7, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 0, 2, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 0, 3, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 4, 2, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 4, 3, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 0, 6, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 0, 7, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 4, 6, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 4, 7, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 2, 6, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 2, 7, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 2, 6, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 2, 7, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 0, 3, 6, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 4, 3, 6, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 2, 3, 8, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.torchWood.blockID, 0, 2, 4, 7, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.torchWood.blockID, 0, 1, 4, 6, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.torchWood.blockID, 0, 3, 4, 6, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.torchWood.blockID, 0, 2, 4, 5, structureBoundingBox);
		int i = getMetadataWithOffset(Block.ladder.blockID, 4);
		int j;

		for (j = 1; j <= 9; ++j)
		{
			placeBlockAtCurrentPosition(world, Block.ladder.blockID, i, 3, j, 3, structureBoundingBox);
		}

		placeBlockAtCurrentPosition(world, 0, 0, 2, 1, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, 0, 0, 2, 2, 0, structureBoundingBox);
		placeDoorAtCurrentPosition(world, structureBoundingBox, random, 2, 1, 0, getMetadataWithOffset(Block.doorWood.blockID, 1));

		if (getBlockIdAtCurrentPosition(world, 2, 0, -1, structureBoundingBox) == 0 && getBlockIdAtCurrentPosition(world, 2, -1, -1, structureBoundingBox) != 0)
		{
			placeBlockAtCurrentPosition(world, Block.stairsStoneBrick.blockID, getMetadataWithOffset(Block.stairsStoneBrick.blockID, 3), 2, 0, -1, structureBoundingBox);
		}

		for (i = 0; i < 5; ++i)
		{
			for (j = 0; j < 9; ++j)
			{
				clearCurrentPositionBlocksUpwards(world, j, 12, i, structureBoundingBox);
				fillCurrentPositionBlocksDownwards(world, Block.stoneBrick.blockID, 0, j, -1, i, structureBoundingBox);
			}
		}

		spawnVillagers(world, structureBoundingBox, 2, 1, 2, 2);

		return true;
	}

	@Override
	protected int getVillagerType(int type)
	{
		return 2;
	}
}