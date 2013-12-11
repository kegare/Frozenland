package kegare.frozenland.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.MapGenCaves;

public class MapGenCavesFrozenland extends MapGenCaves
{
	@Override
	protected void generateCaveNode(long seed, int chunkX, int chunkZ, byte[] blocks, double par5, double par6, double par7, float par8, float par9, float par10, int par11, int par12, double par13)
	{
		double var1 = (double)(chunkX * 16 + 8);
		double var2 = (double)(chunkZ * 16 + 8);
		float var3 = 0.0F;
		float var4 = 0.0F;
		Random random = new Random(seed);

		if (par12 <= 0)
		{
			int var5 = range * 16 - 16;
			par12 = var5 - random.nextInt(var5 / 4);
		}

		boolean flag = false;

		if (par11 == -1)
		{
			par11 = par12 / 2;
			flag = true;
		}

		int var5 = random.nextInt(par12 / 2) + par12 / 4;

		for (boolean flag1 = random.nextInt(6) == 0; par11 < par12; ++par11)
		{
			double var6 = 1.5D + (double)(MathHelper.sin((float)par11 * (float)Math.PI / (float)par12) * par8 * 1.0F);
			double var7 = var6 * par13;
			float var8 = MathHelper.cos(par10);
			float var9 = MathHelper.sin(par10);
			par5 += (double)(MathHelper.cos(par9) * var8);
			par6 += (double)var9;
			par7 += (double)(MathHelper.sin(par9) * var8);

			if (flag1)
			{
				par10 *= 0.92F;
			}
			else
			{
				par10 *= 0.7F;
			}

			par10 += var4 * 0.1F;
			par9 += var3 * 0.1F;
			var4 *= 0.9F;
			var3 *= 0.75F;
			var4 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
			var3 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

			if (!flag && par11 == var5 && par8 > 1.0F && par12 > 0)
			{
				generateCaveNode(random.nextLong(), chunkX, chunkZ, blocks, par5, par6, par7, random.nextFloat() * 0.5F + 0.5F, par9 - ((float)Math.PI / 2F), par10 / 3.0F, par11, par12, 1.0D);
				generateCaveNode(random.nextLong(), chunkX, chunkZ, blocks, par5, par6, par7, random.nextFloat() * 0.5F + 0.5F, par9 + ((float)Math.PI / 2F), par10 / 3.0F, par11, par12, 1.0D);
				return;
			}

			if (flag || random.nextInt(4) != 0)
			{
				double var10 = par5 - var1;
				double var11 = par7 - var2;
				double var12 = (double)(par12 - par11);
				double var13 = (double)(par8 + 2.0F + 16.0F);

				if (var10 * var10 + var11 * var11 - var12 * var12 > var13 * var13)
				{
					return;
				}

				if (par5 >= var1 - 16.0D - var6 * 2.0D && par7 >= var2 - 16.0D - var6 * 2.0D && par5 <= var1 + 16.0D + var6 * 2.0D && par7 <= var2 + 16.0D + var6 * 2.0D)
				{
					int var14 = MathHelper.floor_double(par5 - var6) - chunkX * 16 - 1;
					int var15 = MathHelper.floor_double(par5 + var6) - chunkX * 16 + 1;
					int var16 = MathHelper.floor_double(par6 - var7) - 1;
					int var17 = MathHelper.floor_double(par6 + var7) + 1;
					int var18 = MathHelper.floor_double(par7 - var6) - chunkZ * 16 - 1;
					int var19 = MathHelper.floor_double(par7 + var6) - chunkZ * 16 + 1;

					if (var14 < 0)
					{
						var14 = 0;
					}

					if (var15 > 16)
					{
						var15 = 16;
					}

					if (var16 < 1)
					{
						var16 = 1;
					}

					if (var17 > 120)
					{
						var17 = 120;
					}

					if (var18 < 0)
					{
						var18 = 0;
					}

					if (var19 > 16)
					{
						var19 = 16;
					}

					boolean flag2 = false;
					int var20;
					int var21;

					for (var20 = var14; !flag2 && var20 < var15; ++var20)
					{
						for (int var22 = var18; !flag2 && var22 < var19; ++var22)
						{
							for (int var23 = var17 + 1; !flag2 && var23 >= var16 - 1; --var23)
							{
								var21 = (var20 * 16 + var22) * 128 + var23;

								if (var23 >= 0 && var23 < 128)
								{
									if (isOceanBlock(blocks, var21, var20, var23, var22, chunkX, chunkZ))
									{
										flag2 = true;
									}

									if (var23 != var16 - 1 && var20 != var14 && var20 != var15 - 1 && var22 != var18 && var22 != var19 - 1)
									{
										var23 = var16;
									}
								}
							}
						}
					}

					if (!flag2)
					{
						for (var20 = var14; var20 < var15; ++var20)
						{
							double var22 = ((double)(var20 + chunkX * 16) + 0.5D - par5) / var6;

							for (var21 = var18; var21 < var19; ++var21)
							{
								double var23 = ((double)(var21 + chunkZ * 16) + 0.5D - par7) / var6;
								int index = (var20 * 16 + var21) * 128 + var17;
								boolean flag3 = false;

								if (var22 * var22 + var23 * var23 < 1.0D)
								{
									for (int k4 = var17 - 1; k4 >= var16; --k4)
									{
										double d14 = ((double)k4 + 0.5D - par6) / var7;

										if (d14 > -0.7D && var22 * var22 + d14 * d14 + var23 * var23 < 1.0D)
										{
											if (blocks[index] == Block.ice.blockID)
											{
												flag3 = true;
											}

											digBlock(blocks, index, var20, k4, var21, chunkX, chunkZ, flag3);
										}

										--index;
									}
								}
							}
						}

						if (flag)
						{
							break;
						}
					}
				}
			}
		}
	}

	@Override
	protected void digBlock(byte[] data, int index, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop)
	{
		int block = data[index];

		if (block == Block.stone.blockID || block == Block.ice.blockID)
		{
			if (y < 10)
			{
				data[index] = (byte)Block.lavaMoving.blockID;
			}
			else
			{
				data[index] = 0;

				if (foundTop && data[index - 1] == Block.ice.blockID)
				{
					data[index - 1] = (byte)Block.ice.blockID;
				}
			}
		}
	}
}