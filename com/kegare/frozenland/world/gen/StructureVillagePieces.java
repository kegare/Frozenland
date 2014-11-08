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

import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;

import com.google.common.collect.Lists;

public class StructureVillagePieces
{
	public static void registerPieces()
	{
		MapGenStructureIO.func_143031_a(ComponentVillageChurch.class, "ViFlChurch");
		MapGenStructureIO.func_143031_a(ComponentVillageField.class, "ViFlField1");
		MapGenStructureIO.func_143031_a(ComponentVillageField2.class, "ViFlField2");
		MapGenStructureIO.func_143031_a(ComponentVillageHall.class, "ViFlHall");
		MapGenStructureIO.func_143031_a(ComponentVillageHouse1.class, "ViFlHouse1");
		MapGenStructureIO.func_143031_a(ComponentVillageHouse2.class, "ViFlHouse2");
		MapGenStructureIO.func_143031_a(ComponentVillageHouse3.class, "ViFlHouse3");
		MapGenStructureIO.func_143031_a(ComponentVillageHouse4_Garden.class, "ViFlHouse4");
		MapGenStructureIO.func_143031_a(ComponentVillagePathGen.class, "ViFlPath");
		MapGenStructureIO.func_143031_a(ComponentVillageStartPiece.class, "ViFlStart");
		MapGenStructureIO.func_143031_a(ComponentVillageTorch.class, "ViFlTorch");
		MapGenStructureIO.func_143031_a(ComponentVillageWell.class, "ViFlWell");
		MapGenStructureIO.func_143031_a(ComponentVillageWoodHut.class, "ViFlWoodHut");
	}

	public StructureVillagePieces() {}

	protected static List getStructureVillageWeightedPieceList(Random random, int size)
	{
		List list = Lists.newArrayList();
		list.add(new PieceWeight(ComponentVillageChurch.class, 20, 1));
		list.add(new PieceWeight(ComponentVillageField.class, 3, MathHelper.getRandomIntegerInRange(random, 1 + size, 4 + size)));
		list.add(new PieceWeight(ComponentVillageField2.class, 3, MathHelper.getRandomIntegerInRange(random, 2 + size, 4 + size * 2)));
		list.add(new PieceWeight(ComponentVillageHall.class, 15, MathHelper.getRandomIntegerInRange(random, 0 + size, 2 + size)));
		list.add(new PieceWeight(ComponentVillageHouse1.class, 20, MathHelper.getRandomIntegerInRange(random, 0 + size, 2 + size)));
		list.add(new PieceWeight(ComponentVillageHouse2.class, 15, 1));
		list.add(new PieceWeight(ComponentVillageHouse3.class, 8, MathHelper.getRandomIntegerInRange(random, 0 + size, 3 + size * 2)));
		list.add(new PieceWeight(ComponentVillageHouse4_Garden.class, 4, MathHelper.getRandomIntegerInRange(random, 2 + size, 4 + size * 2)));
		list.add(new PieceWeight(ComponentVillageWoodHut.class, 3, MathHelper.getRandomIntegerInRange(random, 2 + size, 5 + size * 3)));

		Iterator iterator = list.iterator();

		while (iterator.hasNext())
		{
			if (((PieceWeight)iterator.next()).villagePiecesLimit == 0)
			{
				iterator.remove();
			}
		}

		return list;
	}

	private static int getNextPieceWeight(List list)
	{
		boolean flag = false;
		int i = 0;
		PieceWeight villagePieceWeight;

		for (Iterator iterator = list.iterator(); iterator.hasNext(); i += villagePieceWeight.villagePieceWeight)
		{
			villagePieceWeight = (PieceWeight)iterator.next();

			if (villagePieceWeight.villagePiecesLimit > 0 && villagePieceWeight.villagePiecesSpawned < villagePieceWeight.villagePiecesLimit)
			{
				flag = true;
			}
		}

		return flag ? i : -1;
	}

	private static ComponentVillage getNextComponentFromPieceWeight(ComponentVillageStartPiece villageStartPiece, PieceWeight villagePieceWeight, List list, Random random, int x, int y, int z, int coordBaseMode, int type)
	{
		Class clazz = villagePieceWeight.villagePieceClass;
		Object object = null;

		if (clazz == ComponentVillageHouse4_Garden.class)
		{
			object = ComponentVillageHouse4_Garden.findValidPlacement(villageStartPiece, list, random, x, y, z, coordBaseMode, type);
		}
		else if (clazz == ComponentVillageChurch.class)
		{
			object = ComponentVillageChurch.findValidPlacement(villageStartPiece, list, random, x, y, z, coordBaseMode, type);
		}
		else if (clazz == ComponentVillageHouse1.class)
		{
			object = ComponentVillageHouse1.findValidPlacement(villageStartPiece, list, random, x, y, z, coordBaseMode, type);
		}
		else if (clazz == ComponentVillageWoodHut.class)
		{
			object = ComponentVillageWoodHut.findValidPlacement(villageStartPiece, list, random, x, y, z, coordBaseMode, type);
		}
		else if (clazz == ComponentVillageHall.class)
		{
			object = ComponentVillageHall.findValidPlacement(villageStartPiece, list, random, x, y, z, coordBaseMode, type);
		}
		else if (clazz == ComponentVillageField.class)
		{
			object = ComponentVillageField.findValidPlacement(villageStartPiece, list, random, x, y, z, coordBaseMode, type);
		}
		else if (clazz == ComponentVillageField2.class)
		{
			object = ComponentVillageField2.findValidPlacement(villageStartPiece, list, random, x, y, z, coordBaseMode, type);
		}
		else if (clazz == ComponentVillageHouse2.class)
		{
			object = ComponentVillageHouse2.findValidPlacement(villageStartPiece, list, random, x, y, z, coordBaseMode, type);
		}
		else if (clazz == ComponentVillageHouse3.class)
		{
			object = ComponentVillageHouse3.findValidPlacement(villageStartPiece, list, random, x, y, z, coordBaseMode, type);
		}

		return (ComponentVillage)object;
	}

	private static ComponentVillage getNextComponent(ComponentVillageStartPiece villageStartPiece, List list, Random random, int x, int y, int z, int coordBaseMode, int type)
	{
		int var1 = getNextPieceWeight(villageStartPiece.villageWeightedPieceList);

		if (var1 <= 0)
		{
			return null;
		}

		int var2 = 0;

		while (var2 < 5)
		{
			++var2;

			int var3 = random.nextInt(var1);
			Iterator iterator = villageStartPiece.villageWeightedPieceList.iterator();

			while (iterator.hasNext())
			{
				PieceWeight villagePieceWeight = (PieceWeight)iterator.next();
				var3 -= villagePieceWeight.villagePieceWeight;

				if (var3 < 0)
				{
					if (!villagePieceWeight.canSpawnMoreVillagePiecesOfType(type) || villagePieceWeight == villageStartPiece.villagePieceWeight && villageStartPiece.villageWeightedPieceList.size() > 1)
					{
						break;
					}

					ComponentVillage componentVillage = getNextComponentFromPieceWeight(villageStartPiece, villagePieceWeight, list, random, x, y, z, coordBaseMode, type);

					if (componentVillage != null)
					{
						++villagePieceWeight.villagePiecesSpawned;
						villageStartPiece.villagePieceWeight = villagePieceWeight;

						if (!villagePieceWeight.canSpawnMoreVillagePieces())
						{
							villageStartPiece.villageWeightedPieceList.remove(villagePieceWeight);
						}

						return componentVillage;
					}
				}
			}
		}

		StructureBoundingBox structureBoundingBox = ComponentVillageTorch.findValidPlacement(villageStartPiece, list, random, x, y, z, coordBaseMode);

		if (structureBoundingBox != null)
		{
			return new ComponentVillageTorch(villageStartPiece, type, random, structureBoundingBox, coordBaseMode);
		}

		return null;
	}

	protected static StructureComponent getNextValidComponent(ComponentVillageStartPiece villageStartPiece, List list, Random random, int x, int y, int z, int coordBaseMode, int type)
	{
		if (type > 50)
		{
			return null;
		}
		else if (Math.abs(x - villageStartPiece.getBoundingBox().minX) <= 112 && Math.abs(z - villageStartPiece.getBoundingBox().minZ) <= 112)
		{
			ComponentVillage componentVillage = getNextComponent(villageStartPiece, list, random, x, y, z, coordBaseMode, type + 1);

			if (componentVillage != null)
			{
				list.add(componentVillage);
				villageStartPiece.villageStructureList.add(componentVillage);

				return componentVillage;
			}

			return null;
		}
		else
		{
			return null;
		}
	}

	protected static StructureComponent getNextComponentVillagePath(ComponentVillageStartPiece villageStartPiece, List list, Random random, int x, int y, int z, int coordBaseMode, int type)
	{
		if (type > 3 + villageStartPiece.size)
		{
			return null;
		}
		else if (Math.abs(x - villageStartPiece.getBoundingBox().minX) <= 112 && Math.abs(z - villageStartPiece.getBoundingBox().minZ) <= 112)
		{
			StructureBoundingBox structureBoundingBox = ComponentVillagePathGen.getNextStructureBoundingBox(villageStartPiece, list, random, x, y, z, coordBaseMode);

			if (structureBoundingBox != null && structureBoundingBox.minY > 10)
			{
				ComponentVillagePathGen villagePathGen = new ComponentVillagePathGen(villageStartPiece, type, random, structureBoundingBox, coordBaseMode);

				list.add(villagePathGen);
				villageStartPiece.villageComponentList.add(villagePathGen);

				return villagePathGen;
			}

			return null;
		}
		else
		{
			return null;
		}
	}
}