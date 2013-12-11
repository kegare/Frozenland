package kegare.frozenland.world.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentVillageField2 extends ComponentVillage
{
	private int cropTypeA;
	private int cropTypeB;

	public ComponentVillageField2() {}

	protected ComponentVillageField2(ComponentVillageStartPiece villageStartPiece, int type, Random random, StructureBoundingBox structureBoundingBox, int coordBaseMode)
	{
		super(villageStartPiece, type);
		this.coordBaseMode = coordBaseMode;
		this.boundingBox = structureBoundingBox;
		this.cropTypeA = pickRandomCrop(random);
		this.cropTypeB = pickRandomCrop(random);
	}

	@Override
	protected void func_143012_a(NBTTagCompound nbtTagCompound)
	{
		super.func_143012_a(nbtTagCompound);
		nbtTagCompound.setInteger("CA", cropTypeA);
		nbtTagCompound.setInteger("CB", cropTypeB);
	}

	@Override
	protected void func_143011_b(NBTTagCompound nbtTagCompound)
	{
		super.func_143011_b(nbtTagCompound);
		cropTypeA = nbtTagCompound.getInteger("CA");
		cropTypeB = nbtTagCompound.getInteger("CB");
	}

	private int pickRandomCrop(Random random)
	{
		switch (random.nextInt(5))
		{
			case 0:
				return Block.crops.blockID;
			case 1:
			case 2:
				return Block.carrot.blockID;
			default:
				return Block.potato.blockID;
		}
	}

	protected static ComponentVillageField2 findValidPlacement(ComponentVillageStartPiece villageStartPiece, List list, Random random, int x, int y, int z, int coordBaseMode, int type)
	{
		StructureBoundingBox structureBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 7, 4, 9, coordBaseMode);

		return canVillageGoDeeper(structureBoundingBox) && StructureComponent.findIntersecting(list, structureBoundingBox) == null ? new ComponentVillageField2(villageStartPiece, type, random, structureBoundingBox, coordBaseMode) : null;
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

		fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 6, 4, 8, 0, 0, false);
		fillWithBlocks(world, structureBoundingBox, 1, 0, 1, 2, 0, 7, Block.tilledField.blockID, Block.tilledField.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 4, 0, 1, 5, 0, 7, Block.tilledField.blockID, Block.tilledField.blockID, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 0, 0, 0, 0, 8, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 6, 0, 0, 6, 0, 8, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 0, 0, 5, 0, 0, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 0, 8, 5, 0, 8, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		fillWithBlocks(world, structureBoundingBox, 3, 0, 1, 3, 0, 7, Block.waterMoving.blockID, Block.waterMoving.blockID, false);
		int i;

		for (i = 1; i <= 7; ++i)
		{
			placeBlockAtCurrentPosition(world, cropTypeA, MathHelper.getRandomIntegerInRange(random, 2, 7), 1, 1, i, structureBoundingBox);
			placeBlockAtCurrentPosition(world, cropTypeA, MathHelper.getRandomIntegerInRange(random, 2, 7), 2, 1, i, structureBoundingBox);
			placeBlockAtCurrentPosition(world, cropTypeB, MathHelper.getRandomIntegerInRange(random, 2, 7), 4, 1, i, structureBoundingBox);
			placeBlockAtCurrentPosition(world, cropTypeB, MathHelper.getRandomIntegerInRange(random, 2, 7), 5, 1, i, structureBoundingBox);
		}

		for (i = 0; i < 9; ++i)
		{
			for (int j = 0; j < 7; ++j)
			{
				clearCurrentPositionBlocksUpwards(world, j, 4, i, structureBoundingBox);
				fillCurrentPositionBlocksDownwards(world, Block.dirt.blockID, 0, j, -1, i, structureBoundingBox);
			}
		}

		return true;
	}
}