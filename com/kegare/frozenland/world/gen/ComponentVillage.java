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

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public abstract class ComponentVillage extends StructureComponent
{
	protected int averageGroundLevel = -1;

	private int villagersSpawned;

	public ComponentVillage() {}

	protected ComponentVillage(ComponentVillageStartPiece villageStartPiece, int type)
	{
		super(type);
	}

	@Override
	protected void func_143012_a(NBTTagCompound nbtTagCompound)
	{
		nbtTagCompound.setInteger("HPos", averageGroundLevel);
		nbtTagCompound.setInteger("VCount", villagersSpawned);
	}

	@Override
	protected void func_143011_b(NBTTagCompound nbtTagCompound)
	{
		averageGroundLevel = nbtTagCompound.getInteger("HPos");
		villagersSpawned = nbtTagCompound.getInteger("VCount");
	}

	protected StructureComponent getNextComponentNN(ComponentVillageStartPiece villageStartPiece, List list, Random random, int x, int z)
	{
		switch (coordBaseMode)
		{
			case 0:
				return StructureVillagePieces.getNextValidComponent(villageStartPiece, list, random, boundingBox.minX - 1, boundingBox.minY + x, boundingBox.minZ + z, 1, getComponentType());
			case 1:
				return StructureVillagePieces.getNextValidComponent(villageStartPiece, list, random, boundingBox.minX + z, boundingBox.minY + x, boundingBox.minZ - 1, 2, getComponentType());
			case 2:
				return StructureVillagePieces.getNextValidComponent(villageStartPiece, list, random, boundingBox.minX - 1, boundingBox.minY + x, boundingBox.minZ + z, 1, getComponentType());
			case 3:
				return StructureVillagePieces.getNextValidComponent(villageStartPiece, list, random, boundingBox.minX + z, boundingBox.minY + x, boundingBox.minZ - 1, 2, getComponentType());
			default:
				return null;
		}
	}

	protected StructureComponent getNextComponentPP(ComponentVillageStartPiece villageStartPiece, List list, Random random, int x, int z)
	{
		switch (coordBaseMode)
		{
			case 0:
				return StructureVillagePieces.getNextValidComponent(villageStartPiece, list, random, boundingBox.maxX + 1, boundingBox.minY + x, boundingBox.minZ + z, 3, getComponentType());
			case 1:
				return StructureVillagePieces.getNextValidComponent(villageStartPiece, list, random, boundingBox.minX + z, boundingBox.minY + x, boundingBox.maxZ + 1, 0, getComponentType());
			case 2:
				return StructureVillagePieces.getNextValidComponent(villageStartPiece, list, random, boundingBox.maxX + 1, boundingBox.minY + x, boundingBox.minZ + z, 3, getComponentType());
			case 3:
				return StructureVillagePieces.getNextValidComponent(villageStartPiece, list, random, boundingBox.minX + z, boundingBox.minY + x, boundingBox.maxZ + 1, 0, getComponentType());
			default:
				return null;
		}
	}

	protected int getAverageGroundLevel(World world, StructureBoundingBox structureBoundingBox)
	{
		int var1 = 0;
		int var2 = 0;

		for (int x = boundingBox.minX; x <= boundingBox.maxX; ++x)
		{
			for (int z = boundingBox.minZ; z <= boundingBox.maxZ; ++z)
			{
				if (structureBoundingBox.isVecInside(x, 64, z))
				{
					var1 += Math.max(world.getTopSolidOrLiquidBlock(x, z), world.provider.getAverageGroundLevel());

					++var2;
				}
			}
		}

		if (var2 == 0)
		{
			return -1;
		}

		return var1 / var2;
	}

	protected static boolean canVillageGoDeeper(StructureBoundingBox structureBoundingBox)
	{
		return structureBoundingBox != null && structureBoundingBox.minY > 10;
	}

	protected void spawnVillagers(World world, StructureBoundingBox structureBoundingBox, int x, int y, int z, int villagers)
	{
		if (villagersSpawned < villagers)
		{
			for (int type = villagersSpawned; type < villagers; ++type)
			{
				int posX = getXWithOffset(x + type, z);
				int posY = getYWithOffset(y);
				int posZ = getZWithOffset(x + type, z);

				if (!structureBoundingBox.isVecInside(posX, posY, posZ))
				{
					break;
				}

				++villagersSpawned;

				EntityVillager entityVillager = new EntityVillager(world, getVillagerType(type));
				entityVillager.setLocationAndAngles(posX + 0.5D, posY, posZ + 0.5D, 0.0F, 0.0F);

				world.spawnEntityInWorld(entityVillager);
			}
		}
	}

	protected int getVillagerType(int type)
	{
		return 0;
	}
}