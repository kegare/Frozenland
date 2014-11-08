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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenVillageFrozenland extends MapGenVillage
{
	private int size;
	private int distance;
	private int minimumDistance;

	public MapGenVillageFrozenland()
	{
		this.size = 5;
		this.distance = 40;
		this.minimumDistance = 8;
	}

	public MapGenVillageFrozenland(Map map)
	{
		this();
		Iterator iterator = map.entrySet().iterator();

		while (iterator.hasNext())
		{
			Entry entry = (Entry)iterator.next();

			if (((String)entry.getKey()).equals("size"))
			{
				this.size = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), size, 4);
			}
			else if (((String)entry.getKey()).equals("distance"))
			{
				this.distance = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), distance, minimumDistance + 1);
			}
		}
	}

	@Override
	public String func_143025_a()
	{
		return "Village.Frozenland";
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
	{
		int var1 = chunkX;
		int var2 = chunkZ;

		if (chunkX < 0)
		{
			chunkX -= distance - 1;
		}

		if (chunkZ < 0)
		{
			chunkZ -= distance - 1;
		}

		int var3 = chunkX / distance;
		int var4 = chunkZ / distance;
		Random random = worldObj.setRandomSeed(var3, var4, 10387312);
		var3 *= distance;
		var4 *= distance;
		var3 += random.nextInt(distance - minimumDistance);
		var4 += random.nextInt(distance - minimumDistance);

		if (var1 == var3 && var2 == var4)
		{
			return true;
		}

		return false;
	}

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ)
	{
		return new StructureVillageStart(worldObj, rand, chunkX, chunkZ, size);
	}
}