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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentVillagePathGen extends ComponentVillageRoadPiece
{
	private int averageGroundLevel;

	public ComponentVillagePathGen() {}

	protected ComponentVillagePathGen(ComponentVillageStartPiece villageStartPiece, int type, Random random, StructureBoundingBox structureBoundingBox, int coordBaseMode)
	{
		super(villageStartPiece, type);
		this.coordBaseMode = coordBaseMode;
		this.boundingBox = structureBoundingBox;
		this.averageGroundLevel = Math.max(structureBoundingBox.getXSize(), structureBoundingBox.getZSize());
	}

	@Override
	protected void func_143012_a(NBTTagCompound nbtTagCompound)
	{
		super.func_143012_a(nbtTagCompound);
		nbtTagCompound.setInteger("Length", averageGroundLevel);
	}

	@Override
	protected void func_143011_b(NBTTagCompound nbtTagCompound)
	{
		super.func_143011_b(nbtTagCompound);
		averageGroundLevel = nbtTagCompound.getInteger("Length");
	}

	@Override
	public void buildComponent(StructureComponent structureComponent, List list, Random random)
	{
		boolean flag = false;
		int i;
		StructureComponent structurecomponent;

		for (i = random.nextInt(5); i < averageGroundLevel - 8; i += 2 + random.nextInt(5))
		{
			structurecomponent = getNextComponentNN((ComponentVillageStartPiece)structureComponent, list, random, 0, i);

			if (structurecomponent != null)
			{
				i += Math.max(structurecomponent.getBoundingBox().getXSize(), structurecomponent.getBoundingBox().getZSize());
				flag = true;
			}
		}

		for (i = random.nextInt(5); i < averageGroundLevel - 8; i += 2 + random.nextInt(5))
		{
			structurecomponent = getNextComponentPP((ComponentVillageStartPiece)structureComponent, list, random, 0, i);

			if (structurecomponent != null)
			{
				i += Math.max(structurecomponent.getBoundingBox().getXSize(), structurecomponent.getBoundingBox().getZSize());
				flag = true;
			}
		}

		if (flag && random.nextInt(3) > 0)
		{
			switch (coordBaseMode)
			{
				case 0:
					StructureVillagePieces.getNextComponentVillagePath((ComponentVillageStartPiece)structureComponent, list, random, getBoundingBox().minX - 1, getBoundingBox().minY, getBoundingBox().maxZ - 2, 1, getComponentType());
					break;
				case 1:
					StructureVillagePieces.getNextComponentVillagePath((ComponentVillageStartPiece)structureComponent, list, random, getBoundingBox().minX, getBoundingBox().minY, getBoundingBox().minZ - 1, 2, getComponentType());
					break;
				case 2:
					StructureVillagePieces.getNextComponentVillagePath((ComponentVillageStartPiece)structureComponent, list, random, getBoundingBox().minX - 1, getBoundingBox().minY, getBoundingBox().minZ, 1, getComponentType());
					break;
				case 3:
					StructureVillagePieces.getNextComponentVillagePath((ComponentVillageStartPiece)structureComponent, list, random, getBoundingBox().maxX - 2, getBoundingBox().minY, getBoundingBox().minZ - 1, 2, getComponentType());
			}
		}

		if (flag && random.nextInt(3) > 0)
		{
			switch (coordBaseMode)
			{
				case 0:
					StructureVillagePieces.getNextComponentVillagePath((ComponentVillageStartPiece)structureComponent, list, random, getBoundingBox().maxX + 1, getBoundingBox().minY, getBoundingBox().maxZ - 2, 3, getComponentType());
					break;
				case 1:
					StructureVillagePieces.getNextComponentVillagePath((ComponentVillageStartPiece)structureComponent, list, random, getBoundingBox().minX, getBoundingBox().minY, getBoundingBox().maxZ + 1, 0, getComponentType());
					break;
				case 2:
					StructureVillagePieces.getNextComponentVillagePath((ComponentVillageStartPiece)structureComponent, list, random, getBoundingBox().maxX + 1, getBoundingBox().minY, getBoundingBox().minZ, 3, getComponentType());
					break;
				case 3:
					StructureVillagePieces.getNextComponentVillagePath((ComponentVillageStartPiece)structureComponent, list, random, getBoundingBox().maxX - 2, getBoundingBox().minY, getBoundingBox().maxZ + 1, 0, getComponentType());
			}
		}
	}

	protected static StructureBoundingBox getNextStructureBoundingBox(ComponentVillageStartPiece villageStartPiece, List list, Random random, int x, int y, int z, int coordBaseMode)
	{
		for (int i1 = 7 * MathHelper.getRandomIntegerInRange(random, 3, 5); i1 >= 7; i1 -= 7)
		{
			StructureBoundingBox structureBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 3, 3, i1, coordBaseMode);

			if (StructureComponent.findIntersecting(list, structureBoundingBox) == null)
			{
				return structureBoundingBox;
			}
		}

		return null;
	}

	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox)
	{
		for (int x = getBoundingBox().minX; x <= getBoundingBox().maxX; ++x)
		{
			for (int z = getBoundingBox().minZ; z <= getBoundingBox().maxZ; ++z)
			{
				if (structureBoundingBox.isVecInside(x, 64, z))
				{
					world.setBlock(x, world.getTopSolidOrLiquidBlock(x, z) - 1, z, Blocks.gravel, 0, 2);
				}
			}
		}

		return true;
	}
}