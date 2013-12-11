package kegare.frozenland.world.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
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

		fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 9, 4, 6, 0, 0, false);
		fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 9, 0, 6, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 0, 4, 0, 9, 4, 6, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 0, 5, 0, 9, 5, 6, Block.stoneSingleSlab.blockID, Block.stoneSingleSlab.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 1, 5, 1, 8, 5, 5, 0, 0, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 1, 0, 2, 3, 0, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 1, 0, 0, 4, 0, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 3, 1, 0, 3, 4, 0, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 1, 6, 0, 4, 6, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 3, 3, 1, structureBoundingBox);
		fillWithMetadataBlocks(world, structureBoundingBox, 3, 1, 2, 3, 3, 2, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 4, 1, 3, 5, 3, 3, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 1, 1, 0, 3, 5, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 1, 6, 5, 3, 6, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithBlocks(world, structureBoundingBox, 5, 1, 0, 5, 3, 0, Block.fence.blockID, Block.fence.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 9, 1, 0, 9, 3, 0, Block.fence.blockID, Block.fence.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 6, 1, 4, 9, 4, 6, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		placeBlockAtCurrentPosition(world, Block.lavaMoving.blockID, 0, 7, 1, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.lavaMoving.blockID, 0, 8, 1, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.fenceIron.blockID, 0, 9, 2, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.fenceIron.blockID, 0, 9, 2, 4, structureBoundingBox);
		fillWithBlocks(world, structureBoundingBox, 7, 2, 4, 8, 2, 5, 0, 0, false);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 6, 1, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.furnaceIdle.blockID, 0, 6, 2, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.furnaceIdle.blockID, 0, 6, 3, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneDoubleSlab.blockID, 0, 8, 1, 1, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 0, 2, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 0, 2, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 2, 2, 6, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 4, 2, 6, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 2, 1, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.pressurePlatePlanks.blockID, 0, 2, 2, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 1, 1, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stairsWoodSpruce.blockID, getMetadataWithOffset(Block.stairsWoodOak.blockID, 3), 2, 1, 5, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stairsWoodSpruce.blockID, getMetadataWithOffset(Block.stairsWoodOak.blockID, 1), 1, 1, 4, structureBoundingBox);
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
			if (getBlockIdAtCurrentPosition(world, i, 0, -1, structureBoundingBox) == 0 && getBlockIdAtCurrentPosition(world, i, -1, -1, structureBoundingBox) != 0)
			{
				placeBlockAtCurrentPosition(world, Block.stairsStoneBrick.blockID, getMetadataWithOffset(Block.stairsStoneBrick.blockID, 3), i, 0, -1, structureBoundingBox);
			}
		}

		for (i = 0; i < 7; ++i)
		{
			for (j = 0; j < 10; ++j)
			{
				clearCurrentPositionBlocksUpwards(world, j, 6, i, structureBoundingBox);
				fillCurrentPositionBlocksDownwards(world, Block.stoneBrick.blockID, 0, j, -1, i, structureBoundingBox);
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