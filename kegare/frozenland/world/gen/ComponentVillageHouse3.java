package kegare.frozenland.world.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentVillageHouse3 extends ComponentVillage
{
	public ComponentVillageHouse3() {}

	protected ComponentVillageHouse3(ComponentVillageStartPiece villageStartPiece, int type, Random random, StructureBoundingBox structureBoundingBox, int coordBaseMode)
	{
		super(villageStartPiece, type);
		this.coordBaseMode = coordBaseMode;
		this.boundingBox = structureBoundingBox;
	}

	protected static ComponentVillageHouse3 findValidPlacement(ComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
	{
		StructureBoundingBox structureBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 9, 7, 12, par6);

		return canVillageGoDeeper(structureBoundingBox) && StructureComponent.findIntersecting(par1List, structureBoundingBox) == null ? new ComponentVillageHouse3(par0ComponentVillageStartPiece, par7, par2Random, structureBoundingBox, par6) : null;
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
		fillWithMetadataBlocks(world, structureBoundingBox, 2, 0, 5, 8, 0, 10, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 0, 1, 7, 0, 4, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 0, 3, 5, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 8, 0, 0, 8, 3, 10, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 1, 0, 0, 7, 2, 0, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 1, 0, 5, 2, 1, 5, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 2, 0, 6, 2, 3, 10, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 3, 0, 10, 7, 3, 10, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 2, 0, 7, 3, 0, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 2, 5, 2, 3, 5, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 4, 1, 8, 4, 1, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 4, 4, 3, 4, 4, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 5, 2, 8, 5, 3, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 0, 4, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 0, 4, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 8, 4, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 8, 4, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 8, 4, 4, structureBoundingBox);
		int i = getMetadataWithOffset(Block.stairsWoodOak.blockID, 3);
		int j = getMetadataWithOffset(Block.stairsWoodOak.blockID, 2);
		int k;
		int l;

		for (k = -1; k <= 2; ++k)
		{
			for (l = 0; l <= 8; ++l)
			{
				placeBlockAtCurrentPosition(world, Block.stairsWoodSpruce.blockID, i, l, 4 + k, k, structureBoundingBox);

				if ((k > -1 || l <= 1) && (k > 0 || l <= 3) && (k > 1 || l <= 4 || l >= 6))
				{
					placeBlockAtCurrentPosition(world, Block.stairsWoodSpruce.blockID, j, l, 4 + k, 5 - k, structureBoundingBox);
				}
			}
		}

		fillWithMetadataBlocks(world, structureBoundingBox, 3, 4, 5, 3, 4, 10, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 7, 4, 2, 7, 4, 10, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 4, 5, 4, 4, 5, 10, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 6, 5, 4, 6, 5, 10, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 5, 6, 3, 5, 6, 10, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		k = getMetadataWithOffset(Block.stairsWoodOak.blockID, 0);
		int m;

		for (l = 4; l >= 1; --l)
		{
			placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, l, 2 + l, 7 - l, structureBoundingBox);

			for (m = 8 - l; m <= 10; ++m)
			{
				placeBlockAtCurrentPosition(world, Block.stairsWoodSpruce.blockID, k, l, 2 + l, m, structureBoundingBox);
			}
		}

		l = getMetadataWithOffset(Block.stairsWoodOak.blockID, 1);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 6, 6, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 7, 5, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stairsWoodSpruce.blockID, l, 6, 6, 4, structureBoundingBox);
		int n;

		for (m = 6; m <= 8; ++m)
		{
			for (n = 5; n <= 10; ++n)
			{
				placeBlockAtCurrentPosition(world, Block.stairsWoodSpruce.blockID, l, m, 12 - m, n, structureBoundingBox);
			}
		}

		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 0, 2, 1, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 0, 2, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 0, 2, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 0, 2, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 4, 2, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 5, 2, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 6, 2, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 8, 2, 1, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 8, 2, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 8, 2, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 8, 2, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 8, 2, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 8, 2, 6, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 8, 2, 7, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 8, 2, 8, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 8, 2, 9, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 2, 2, 6, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 2, 2, 7, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 2, 2, 8, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 2, 2, 9, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 4, 4, 10, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 5, 4, 10, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 6, 4, 10, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 5, 5, 10, structureBoundingBox);
		placeBlockAtCurrentPosition(world, 0, 0, 2, 1, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, 0, 0, 2, 2, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.torchWood.blockID, 0, 2, 3, 1, structureBoundingBox);
		placeDoorAtCurrentPosition(world, structureBoundingBox, random, 2, 1, 0, getMetadataWithOffset(Block.doorWood.blockID, 1));
		fillWithBlocks(world, structureBoundingBox, 1, 0, -1, 3, 2, -1, 0, 0, false);

		if (getBlockIdAtCurrentPosition(world, 2, 0, -1, structureBoundingBox) == 0 && getBlockIdAtCurrentPosition(world, 2, -1, -1, structureBoundingBox) != 0)
		{
			placeBlockAtCurrentPosition(world, Block.stairsStoneBrick.blockID, getMetadataWithOffset(Block.stairsStoneBrick.blockID, 3), 2, 0, -1, structureBoundingBox);
		}

		for (m = 0; m < 5; ++m)
		{
			for (n = 0; n < 9; ++n)
			{
				clearCurrentPositionBlocksUpwards(world, n, 7, m, structureBoundingBox);
				fillCurrentPositionBlocksDownwards(world, Block.stoneBrick.blockID, 0, n, -1, m, structureBoundingBox);
			}
		}

		for (m = 5; m < 11; ++m)
		{
			for (n = 2; n < 9; ++n)
			{
				clearCurrentPositionBlocksUpwards(world, n, 7, m, structureBoundingBox);
				fillCurrentPositionBlocksDownwards(world, Block.stoneBrick.blockID, 0, n, -1, m, structureBoundingBox);
			}
		}

		spawnVillagers(world, structureBoundingBox, 4, 1, 2, 2);

		return true;
	}
}