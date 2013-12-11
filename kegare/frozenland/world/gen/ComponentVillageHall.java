package kegare.frozenland.world.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentVillageHall extends ComponentVillage
{
	public ComponentVillageHall() {}

	protected ComponentVillageHall(ComponentVillageStartPiece villageStartPiece, int type, Random random, StructureBoundingBox structureBoundingBox, int coordBaseMode)
	{
		super(villageStartPiece, type);
		this.coordBaseMode = coordBaseMode;
		this.boundingBox = structureBoundingBox;
	}

	protected static ComponentVillageHall findValidPlacement(ComponentVillageStartPiece villageStartPiece, List list, Random random, int x, int y, int z, int coordBaseMode, int type)
	{
		StructureBoundingBox structureBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 9, 7, 11, coordBaseMode);

		return canVillageGoDeeper(structureBoundingBox) && StructureComponent.findIntersecting(list, structureBoundingBox) == null ? new ComponentVillageHall(villageStartPiece, type, random, structureBoundingBox, coordBaseMode) : null;
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

			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 7 - 1, 0);
		}

		fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 7, 4, 4, 0, 0, false);
		fillWithBlocks(world, structureBoundingBox, 2, 1, 6, 8, 4, 10, 0, 0, false);
		fillWithBlocks(world, structureBoundingBox, 2, 0, 6, 8, 0, 10, Block.dirt.blockID, Block.dirt.blockID, false);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 6, 0, 6, structureBoundingBox);
		fillWithBlocks(world, structureBoundingBox, 2, 1, 6, 2, 1, 10, Block.fence.blockID, Block.fence.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 8, 1, 6, 8, 1, 10, Block.fence.blockID, Block.fence.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 3, 1, 10, 7, 1, 10, Block.fence.blockID, Block.fence.blockID, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 0, 1, 7, 0, 4, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 0, 3, 5, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 8, 0, 0, 8, 3, 5, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 1, 0, 0, 7, 1, 0, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 1, 0, 5, 7, 1, 5, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 2, 0, 7, 3, 0, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 2, 5, 7, 3, 5, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 4, 1, 8, 4, 1, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 4, 4, 8, 4, 4, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 5, 2, 8, 5, 3, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 0, 4, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 0, 4, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 8, 4, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 8, 4, 3, structureBoundingBox);
		int i = getMetadataWithOffset(Block.stairsWoodOak.blockID, 3);
		int j = getMetadataWithOffset(Block.stairsWoodOak.blockID, 2);
		int k;
		int l;

		for (k = -1; k <= 2; ++k)
		{
			for (l = 0; l <= 8; ++l)
			{
				placeBlockAtCurrentPosition(world, Block.stairsWoodSpruce.blockID, i, l, 4 + k, k, structureBoundingBox);
				placeBlockAtCurrentPosition(world, Block.stairsWoodSpruce.blockID, j, l, 4 + k, 5 - k, structureBoundingBox);
			}
		}

		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 0, 2, 1, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 0, 2, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 8, 2, 1, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 8, 2, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 0, 2, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 0, 2, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 8, 2, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 8, 2, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 2, 2, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 3, 2, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 5, 2, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 6, 2, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 2, 1, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.pressurePlatePlanks.blockID, 0, 2, 2, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 1, 1, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stairsWoodSpruce.blockID, getMetadataWithOffset(Block.stairsWoodOak.blockID, 3), 2, 1, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stairsWoodSpruce.blockID, getMetadataWithOffset(Block.stairsWoodOak.blockID, 1), 1, 1, 3, structureBoundingBox);
		fillWithBlocks(world, structureBoundingBox, 5, 0, 1, 7, 0, 3, Block.stoneDoubleSlab.blockID, Block.stoneDoubleSlab.blockID, false);
		placeBlockAtCurrentPosition(world, Block.stoneDoubleSlab.blockID, 0, 6, 1, 1, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneDoubleSlab.blockID, 0, 6, 1, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, 0, 0, 2, 1, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, 0, 0, 2, 2, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.torchWood.blockID, 0, 2, 3, 1, structureBoundingBox);
		placeDoorAtCurrentPosition(world, structureBoundingBox, random, 2, 1, 0, getMetadataWithOffset(Block.doorWood.blockID, 1));

		if (getBlockIdAtCurrentPosition(world, 2, 0, -1, structureBoundingBox) == 0 && getBlockIdAtCurrentPosition(world, 2, -1, -1, structureBoundingBox) != 0)
		{
			placeBlockAtCurrentPosition(world, Block.stairsStoneBrick.blockID, getMetadataWithOffset(Block.stairsStoneBrick.blockID, 3), 2, 0, -1, structureBoundingBox);
		}

		placeBlockAtCurrentPosition(world, 0, 0, 6, 1, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, 0, 0, 6, 2, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.torchWood.blockID, 0, 6, 3, 4, structureBoundingBox);
		placeDoorAtCurrentPosition(world, structureBoundingBox, random, 6, 1, 5, getMetadataWithOffset(Block.doorWood.blockID, 1));

		for (k = 0; k < 5; ++k)
		{
			for (l = 0; l < 9; ++l)
			{
				clearCurrentPositionBlocksUpwards(world, l, 7, k, structureBoundingBox);
				fillCurrentPositionBlocksDownwards(world, Block.stoneBrick.blockID, 0, l, -1, k, structureBoundingBox);
			}
		}

		spawnVillagers(world, structureBoundingBox, 4, 1, 2, 2);

		return true;
	}

	@Override
	protected int getVillagerType(int type)
	{
		return type == 0 ? 4 : 0;
	}
}