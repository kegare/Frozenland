package kegare.frozenland.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DungeonHooks;

public class WorldGenDungeonsFrozenland extends WorldGenerator
{
	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		byte var1 = 3;
		int var2 = random.nextInt(2) + 2;
		int var3 = random.nextInt(2) + 2;
		int var4 = 0;
		int var5;
		int var6;
		int var7;

		for (var5 = x - var2 - 1; var5 <= x + var2 + 1; ++var5)
		{
			for (var6 = y - 1; var6 <= y + var1 + 1; ++var6)
			{
				for (var7 = z - var3 - 1; var7 <= z + var3 + 1; ++var7)
				{
					Material material = world.getBlockMaterial(var5, var6, var7);

					if (var6 == y - 1 && !material.isSolid())
					{
						return false;
					}

					if (var6 == y + var1 + 1 && !material.isSolid())
					{
						return false;
					}

					if ((var5 == x - var2 - 1 || var5 == x + var2 + 1 || var7 == z - var3 - 1 || var7 == z + var3 + 1) && var6 == y && world.isAirBlock(var5, var6, var7) && world.isAirBlock(var5, var6 + 1, var7))
					{
						++var4;
					}
				}
			}
		}

		if (var4 >= 1 && var4 <= 5)
		{
			for (var5 = x - var2 - 1; var5 <= x + var2 + 1; ++var5)
			{
				for (var6 = y + var1; var6 >= y - 1; --var6)
				{
					for (var7 = z - var3 - 1; var7 <= z + var3 + 1; ++var7)
					{
						if (var5 != x - var2 - 1 && var6 != y - 1 && var7 != z - var3 - 1 && var5 != x + var2 + 1 && var6 != y + var1 + 1 && var7 != z + var3 + 1)
						{
							world.setBlockToAir(var5, var6, var7);
						}
						else if (var6 >= 0 && !world.getBlockMaterial(var5, var6 - 1, var7).isSolid())
						{
							world.setBlockToAir(var5, var6, var7);
						}
						else if (world.getBlockMaterial(var5, var6, var7).isSolid())
						{
							if (var6 == y - 1 && random.nextInt(4) != 0)
							{
								world.setBlock(var5, var6, var7, Block.stoneBrick.blockID, 1, 2);
							}
							else
							{
								world.setBlock(var5, var6, var7, Block.stoneBrick.blockID, 0, 2);
							}
						}
					}
				}
			}

			var5 = 0;

			while (var5 < 2)
			{
				var6 = 0;

				while (true)
				{
					if (var6 < 3)
					{
						label1:
						{
							var7 = x + random.nextInt(var2 * 2 + 1) - var2;
							int var8 = z + random.nextInt(var3 * 2 + 1) - var3;

							if (world.isAirBlock(var7, y, var8))
							{
								int var9 = 0;

								if (world.getBlockMaterial(var7 - 1, y, var8).isSolid())
								{
									++var9;
								}

								if (world.getBlockMaterial(var7 + 1, y, var8).isSolid())
								{
									++var9;
								}

								if (world.getBlockMaterial(var7, y, var8 - 1).isSolid())
								{
									++var9;
								}

								if (world.getBlockMaterial(var7, y, var8 + 1).isSolid())
								{
									++var9;
								}

								if (var9 == 1)
								{
									world.setBlock(var7, y, var8, Block.chest.blockID, 0, 2);
									TileEntityChest chest = (TileEntityChest)world.getBlockTileEntity(var7, y, var8);

									if (chest != null)
									{
										ChestGenHooks info = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
										WeightedRandomChestContent.generateChestContents(random, info.getItems(random), chest, info.getCount(random));
									}

									break label1;
								}
							}

							++var6;
							continue;
						}
					}

					++var5;
					break;
				}
			}

			world.setBlock(x, y, z, Block.mobSpawner.blockID, 0, 2);
			TileEntityMobSpawner mobSpawner = (TileEntityMobSpawner)world.getBlockTileEntity(x, y, z);

			if (mobSpawner != null)
			{
				mobSpawner.getSpawnerLogic().setMobID(DungeonHooks.getRandomDungeonMob(random));
			}
			else
			{
				System.err.println("Failed to fetch mob spawner entity at (" + x + ", " + y + ", " + z + ")");
			}

			return true;
		}
		else
		{
			return false;
		}
	}
}