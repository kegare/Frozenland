package kegare.frozenland.world.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentVillageHouse4_Garden extends ComponentVillage
{
	private boolean isRoofAccessible;

	public ComponentVillageHouse4_Garden() {}

	protected ComponentVillageHouse4_Garden(ComponentVillageStartPiece villageStartPiece, int type, Random random, StructureBoundingBox structureBoundingBox, int coordBaseMode)
	{
		super(villageStartPiece, type);
		this.coordBaseMode = coordBaseMode;
		this.boundingBox = structureBoundingBox;
		this.isRoofAccessible = random.nextBoolean();
	}

	protected static ComponentVillageHouse4_Garden findValidPlacement(ComponentVillageStartPiece villageStartPiece, List list, Random random, int x, int y, int z, int coordBaseMode, int type)
	{
		StructureBoundingBox structureBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 5, 6, 5, coordBaseMode);

		return StructureComponent.findIntersecting(list, structureBoundingBox) != null ? null : new ComponentVillageHouse4_Garden(villageStartPiece, type, random, structureBoundingBox, coordBaseMode);
	}

	@Override
	protected void func_143012_a(NBTTagCompound nbtTagCompound)
	{
		super.func_143012_a(nbtTagCompound);
		nbtTagCompound.setBoolean("Terrace", isRoofAccessible);
	}

	@Override
	protected void func_143011_b(NBTTagCompound nbtTagCompound)
	{
		super.func_143011_b(nbtTagCompound);
		isRoofAccessible = nbtTagCompound.getBoolean("Terrace");
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

		fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 4, 0, 4, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 4, 0, 4, 4, 4, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 4, 1, 3, 4, 3, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 0, 1, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 0, 2, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 0, 3, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 4, 1, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 4, 2, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 4, 3, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 0, 1, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 0, 2, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 0, 3, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 4, 1, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 4, 2, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.stoneBrick.blockID, 0, 4, 3, 4, structureBoundingBox);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 1, 1, 0, 3, 3, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 4, 1, 1, 4, 3, 3, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 1, 4, 3, 3, 4, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 0, 2, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 2, 2, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 4, 2, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 1, 1, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 1, 2, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 1, 3, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 2, 3, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 3, 3, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 3, 2, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.planks.blockID, 1, 3, 1, 0, structureBoundingBox);

		if (getBlockIdAtCurrentPosition(world, 2, 0, -1, structureBoundingBox) == 0 && getBlockIdAtCurrentPosition(world, 2, -1, -1, structureBoundingBox) != 0)
		{
			placeBlockAtCurrentPosition(world, Block.stairsStoneBrick.blockID, getMetadataWithOffset(Block.stairsStoneBrick.blockID, 3), 2, 0, -1, structureBoundingBox);
		}

		fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 3, 3, 3, 0, 0, false);

		if (isRoofAccessible)
		{
			placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 0, 5, 0, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 1, 5, 0, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 2, 5, 0, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 3, 5, 0, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 4, 5, 0, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 0, 5, 4, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 1, 5, 4, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 2, 5, 4, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 3, 5, 4, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 4, 5, 4, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 4, 5, 1, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 4, 5, 2, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 4, 5, 3, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 0, 5, 1, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 0, 5, 2, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 0, 5, 3, structureBoundingBox);
		}

		int i;

		if (isRoofAccessible)
		{
			i = getMetadataWithOffset(Block.ladder.blockID, 3);
			placeBlockAtCurrentPosition(world, Block.ladder.blockID, i, 3, 1, 3, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.ladder.blockID, i, 3, 2, 3, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.ladder.blockID, i, 3, 3, 3, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.ladder.blockID, i, 3, 4, 3, structureBoundingBox);
		}

		placeBlockAtCurrentPosition(world, Block.torchWood.blockID, 0, 2, 3, 1, structureBoundingBox);

		for (i = 0; i < 5; ++i)
		{
			for (int j = 0; j < 5; ++j)
			{
				clearCurrentPositionBlocksUpwards(world, j, 6, i, structureBoundingBox);
				fillCurrentPositionBlocksDownwards(world, Block.stoneBrick.blockID, 0, j, -1, i, structureBoundingBox);
			}
		}

		spawnVillagers(world, structureBoundingBox, 1, 1, 2, 1);

		return true;
	}
}