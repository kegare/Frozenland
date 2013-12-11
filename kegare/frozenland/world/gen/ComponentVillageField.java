package kegare.frozenland.world.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentVillageField extends ComponentVillage
{
	private int cropTypeA;
	private int cropTypeB;
	private int cropTypeC;
	private int cropTypeD;

	public ComponentVillageField() {}

	protected ComponentVillageField(ComponentVillageStartPiece villageStartPiece, int type, Random random, StructureBoundingBox structureBoundingBox, int coordBaseMode)
	{
		super(villageStartPiece, type);
		this.coordBaseMode = coordBaseMode;
		this.boundingBox = structureBoundingBox;
		this.cropTypeA = getRandomCrop(random);
		this.cropTypeB = getRandomCrop(random);
		this.cropTypeC = getRandomCrop(random);
		this.cropTypeD = getRandomCrop(random);
	}

	@Override
	protected void func_143012_a(NBTTagCompound nbtTagCompound)
	{
		super.func_143012_a(nbtTagCompound);
		nbtTagCompound.setInteger("CA", cropTypeA);
		nbtTagCompound.setInteger("CB", cropTypeB);
		nbtTagCompound.setInteger("CC", cropTypeC);
		nbtTagCompound.setInteger("CD", cropTypeD);
	}

	@Override
	protected void func_143011_b(NBTTagCompound nbtTagCompound)
	{
		super.func_143011_b(nbtTagCompound);
		cropTypeA = nbtTagCompound.getInteger("CA");
		cropTypeB = nbtTagCompound.getInteger("CB");
		cropTypeC = nbtTagCompound.getInteger("CC");
		cropTypeD = nbtTagCompound.getInteger("CD");
	}

	private int getRandomCrop(Random random)
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

	protected static ComponentVillageField findValidPlacement(ComponentVillageStartPiece villageStartPiece, List list, Random random, int x, int y, int z, int coordBaseMode, int type)
	{
		StructureBoundingBox structureBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 13, 4, 9, coordBaseMode);

		return canVillageGoDeeper(structureBoundingBox) && StructureComponent.findIntersecting(list, structureBoundingBox) == null ? new ComponentVillageField(villageStartPiece, type, random, structureBoundingBox, coordBaseMode) : null;
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

		fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 12, 4, 8, 0, 0, false);
		fillWithBlocks(world, structureBoundingBox, 1, 0, 1, 2, 0, 7, Block.tilledField.blockID, Block.tilledField.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 4, 0, 1, 5, 0, 7, Block.tilledField.blockID, Block.tilledField.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 7, 0, 1, 8, 0, 7, Block.tilledField.blockID, Block.tilledField.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 10, 0, 1, 11, 0, 7, Block.tilledField.blockID, Block.tilledField.blockID, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 0, 0, 0, 0, 0, 8, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 6, 0, 0, 6, 0, 8, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 12, 0, 0, 12, 0, 8, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 0, 0, 11, 0, 0, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		fillWithMetadataBlocks(world, structureBoundingBox, 1, 0, 8, 11, 0, 8, Block.wood.blockID, 1, Block.wood.blockID, 1, false);
		fillWithBlocks(world, structureBoundingBox, 3, 0, 1, 3, 0, 7, Block.waterMoving.blockID, Block.waterMoving.blockID, false);
		fillWithBlocks(world, structureBoundingBox, 9, 0, 1, 9, 0, 7, Block.waterMoving.blockID, Block.waterMoving.blockID, false);
		int i;

		for (i = 1; i <= 7; ++i)
		{
			placeBlockAtCurrentPosition(world, cropTypeA, MathHelper.getRandomIntegerInRange(random, 2, 7), 1, 1, i, structureBoundingBox);
			placeBlockAtCurrentPosition(world, cropTypeA, MathHelper.getRandomIntegerInRange(random, 2, 7), 2, 1, i, structureBoundingBox);
			placeBlockAtCurrentPosition(world, cropTypeB, MathHelper.getRandomIntegerInRange(random, 2, 7), 4, 1, i, structureBoundingBox);
			placeBlockAtCurrentPosition(world, cropTypeB, MathHelper.getRandomIntegerInRange(random, 2, 7), 5, 1, i, structureBoundingBox);
			placeBlockAtCurrentPosition(world, cropTypeC, MathHelper.getRandomIntegerInRange(random, 2, 7), 7, 1, i, structureBoundingBox);
			placeBlockAtCurrentPosition(world, cropTypeC, MathHelper.getRandomIntegerInRange(random, 2, 7), 8, 1, i, structureBoundingBox);
			placeBlockAtCurrentPosition(world, cropTypeD, MathHelper.getRandomIntegerInRange(random, 2, 7), 10, 1, i, structureBoundingBox);
			placeBlockAtCurrentPosition(world, cropTypeD, MathHelper.getRandomIntegerInRange(random, 2, 7), 11, 1, i, structureBoundingBox);
		}

		for (i = 0; i < 9; ++i)
		{
			for (int j = 0; j < 13; ++j)
			{
				clearCurrentPositionBlocksUpwards(world, j, 4, i, structureBoundingBox);
				fillCurrentPositionBlocksDownwards(world, Block.dirt.blockID, 0, j, -1, i, structureBoundingBox);
			}
		}

		return true;
	}
}