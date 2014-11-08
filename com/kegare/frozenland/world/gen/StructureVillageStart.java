/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.world.gen;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public class StructureVillageStart extends StructureStart
{
	private boolean hasMoreThanTwoComponents;

	public StructureVillageStart() {}

	protected StructureVillageStart(World world, Random random, int chunkX, int chunkZ, int size)
	{
		super(chunkX, chunkZ);
		List list = StructureVillagePieces.getStructureVillageWeightedPieceList(random, size);
		ComponentVillageStartPiece villageStartPiece = new ComponentVillageStartPiece(world.getWorldChunkManager(), random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, size);
		this.components.add(villageStartPiece);
		villageStartPiece.buildComponent(villageStartPiece, components, random);
		List componentList = villageStartPiece.villageComponentList;
		List structureList = villageStartPiece.villageStructureList;
		int index;

		while (!componentList.isEmpty() || !structureList.isEmpty())
		{
			StructureComponent structureComponent;

			if (componentList.isEmpty())
			{
				index = random.nextInt(structureList.size());
				structureComponent = (StructureComponent)structureList.remove(index);
				structureComponent.buildComponent(villageStartPiece, components, random);
			}
			else
			{
				index = random.nextInt(componentList.size());
				structureComponent = (StructureComponent)componentList.remove(index);
				structureComponent.buildComponent(villageStartPiece, components, random);
			}
		}

		updateBoundingBox();
		index = 0;
		Iterator iterator = components.iterator();

		while (iterator.hasNext())
		{
			StructureComponent structureComponent = (StructureComponent)iterator.next();

			if (!(structureComponent instanceof ComponentVillageRoadPiece))
			{
				++index;
			}
		}

		this.hasMoreThanTwoComponents = index > 2;
	}

	@Override
	public void func_143022_a(NBTTagCompound nbtTagCompound)
	{
		super.func_143022_a(nbtTagCompound);
		nbtTagCompound.setBoolean("Valid", hasMoreThanTwoComponents);
	}

	@Override
	public void func_143017_b(NBTTagCompound nbtTagCompound)
	{
		super.func_143017_b(nbtTagCompound);
		hasMoreThanTwoComponents = nbtTagCompound.getBoolean("Valid");
	}

	@Override
	public boolean isSizeableStructure()
	{
		return hasMoreThanTwoComponents;
	}
}