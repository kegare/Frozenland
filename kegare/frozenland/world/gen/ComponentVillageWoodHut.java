package kegare.frozenland.world.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentVillageWoodHut extends ComponentVillage
{
	private boolean isTallHouse;
	private int tablePosition;

	public ComponentVillageWoodHut() {}

	protected ComponentVillageWoodHut(ComponentVillageStartPiece villageStartPiece, int type, Random random, StructureBoundingBox structureBoundingBox, int coordBaseMode)
	{
		super(villageStartPiece, type);
		this.coordBaseMode = coordBaseMode;
		this.boundingBox = structureBoundingBox;
		this.isTallHouse = random.nextBoolean();
		this.tablePosition = random.nextInt(3);
	}

	protected static ComponentVillageWoodHut findValidPlacement(ComponentVillageStartPiece villageStartPiece, List list, Random random, int x, int y, int z, int coordBaseMode, int type)
	{
		StructureBoundingBox structureBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 4, 6, 5, coordBaseMode);

		return canVillageGoDeeper(structureBoundingBox) && StructureComponent.findIntersecting(list, structureBoundingBox) == null ? new ComponentVillageWoodHut(villageStartPiece, type, random, structureBoundingBox, coordBaseMode) : null;
	}

	@Override
	protected void func_143012_a(NBTTagCompound nbtTagCompound)
	{
		super.func_143012_a(nbtTagCompound);
		nbtTagCompound.setInteger("T", tablePosition);
		nbtTagCompound.setBoolean("C", isTallHouse);
	}

	@Override
	protected void func_143011_b(NBTTagCompound nbtTagCompound)
	{
		super.func_143011_b(nbtTagCompound);
		tablePosition = nbtTagCompound.getInteger("T");
		isTallHouse = nbtTagCompound.getBoolean("C");
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

		fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 3, 5, 4, 0, 0, false);
		fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 3, 0, 4, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 1, 0, 1, 2, 0, 3, Block.dirt.blockID, Block.dirt.blockID, false);

		if (isTallHouse)
		{
			fillWithMetadataBlocks(world, structureBoundingBox, 1, 4, 1, 2, 4, 3, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		}
		else
		{
			fillWithMetadataBlocks(world, structureBoundingBox, 1, 5, 1, 2, 5, 3, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		}

		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 1, 4, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 2, 4, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 1, 4, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 2, 4, 4, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 0, 4, 1, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 0, 4, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 0, 4, 3, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 3, 4, 1, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 3, 4, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.wood.blockID, 1, 3, 4, 3, structureBoundingBox);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 1, 0, 0, 3, 0, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 3, 1, 0, 3, 3, 0, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 1, 4, 0, 3, 4, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 3, 1, 4, 3, 3, 4, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 1, 1, 0, 3, 3, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 3, 1, 1, 3, 3, 3, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 1, 0, 2, 3, 0, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 1, 4, 2, 3, 4, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 0, 2, 2, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.thinGlass.blockID, 0, 3, 2, 2, structureBoundingBox);

		if (tablePosition > 0)
		{
			placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, tablePosition, 1, 3, structureBoundingBox);
			placeBlockAtCurrentPosition(world, Block.pressurePlatePlanks.blockID, 0, tablePosition, 2, 3, structureBoundingBox);
		}

		placeBlockAtCurrentPosition(world, 0, 0, 1, 1, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, 0, 0, 1, 2, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.torchWood.blockID, 0, 1, 3, 1, structureBoundingBox);
		placeDoorAtCurrentPosition(world, structureBoundingBox, random, 1, 1, 0, getMetadataWithOffset(Block.doorWood.blockID, 1));

		if (getBlockIdAtCurrentPosition(world, 1, 0, -1, structureBoundingBox) == 0 && getBlockIdAtCurrentPosition(world, 1, -1, -1, structureBoundingBox) != 0)
		{
			placeBlockAtCurrentPosition(world, Block.stairsStoneBrick.blockID, getMetadataWithOffset(Block.stairsStoneBrick.blockID, 3), 1, 0, -1, structureBoundingBox);
		}

		for (int x = 0; x < 4; ++x)
		{
			for (int z = 0; z < 5; ++z)
			{
				clearCurrentPositionBlocksUpwards(world, x, 6, z, structureBoundingBox);
				fillCurrentPositionBlocksDownwards(world, Block.stoneBrick.blockID, 0, x, -1, z, structureBoundingBox);
			}
		}

		spawnVillagers(world, structureBoundingBox, 1, 1, 2, 1);

		return true;
	}
}