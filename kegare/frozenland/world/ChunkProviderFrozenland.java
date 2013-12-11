package kegare.frozenland.world;

import java.util.List;
import java.util.Random;

import kegare.frozenland.core.Config;
import kegare.frozenland.world.gen.MapGenCavesFrozenland;
import kegare.frozenland.world.gen.MapGenRavineFrozenland;
import kegare.frozenland.world.gen.MapGenVillageFrozenland;
import kegare.frozenland.world.gen.WorldGenDungeonsFrozenland;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderFrozenland implements IChunkProvider
{
	private final World worldObj;
	private final Random rand;
	private final boolean mapFeaturesEnabled;

	private NoiseGeneratorOctaves noiseGen1;
	private NoiseGeneratorOctaves noiseGen2;
	private NoiseGeneratorOctaves noiseGen3;
	private NoiseGeneratorOctaves noiseGen4;
	private NoiseGeneratorOctaves noiseGen5;
	private NoiseGeneratorOctaves noiseGen6;

	private double[] noiseArray;
	private double[] stoneNoise = new double[256];

	private double[] noise1;
	private double[] noise2;
	private double[] noise3;
	private double[] noise5;
	private double[] noise6;

	private float[] parabolicField;

	private BiomeGenBase[] biomesForGeneration;

	private MapGenBase caveGenerator = new MapGenCavesFrozenland();
	private MapGenBase ravineGenerator = new MapGenRavineFrozenland();
	private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
	private final MapGenVillage villageGenerator = new MapGenVillageFrozenland();

	{
		caveGenerator = TerrainGen.getModdedMapGen(caveGenerator, InitMapGenEvent.EventType.CAVE);
		ravineGenerator = TerrainGen.getModdedMapGen(ravineGenerator, InitMapGenEvent.EventType.RAVINE);
		mineshaftGenerator = (MapGenMineshaft)TerrainGen.getModdedMapGen(mineshaftGenerator, InitMapGenEvent.EventType.MINESHAFT);
	}

	public ChunkProviderFrozenland(World world)
	{
		this.worldObj = world;
		this.rand = new Random(world.getSeed());
		this.mapFeaturesEnabled = world.getWorldInfo().isMapFeaturesEnabled();
		this.noiseGen1 = new NoiseGeneratorOctaves(rand, 16);
		this.noiseGen2 = new NoiseGeneratorOctaves(rand, 16);
		this.noiseGen3 = new NoiseGeneratorOctaves(rand, 8);
		this.noiseGen4 = new NoiseGeneratorOctaves(rand, 4);
		this.noiseGen5 = new NoiseGeneratorOctaves(rand, 10);
		this.noiseGen6 = new NoiseGeneratorOctaves(rand, 16);

		NoiseGeneratorOctaves[] noiseGens = {noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5, noiseGen6};
		noiseGens = TerrainGen.getModdedNoiseGenerators(world, rand, noiseGens);
		this.noiseGen1 = noiseGens[0];
		this.noiseGen2 = noiseGens[1];
		this.noiseGen3 = noiseGens[2];
		this.noiseGen4 = noiseGens[3];
		this.noiseGen5 = noiseGens[4];
		this.noiseGen6 = noiseGens[5];
	}

	private void generateTerrain(int chunkX, int chunkZ, byte[] blocks)
	{
		byte var1 = 4;
		byte var2 = 16;
		byte horizon = 63;
		int var3 = var1 + 1;
		byte var4 = 17;
		int var5 = var1 + 1;
		biomesForGeneration = worldObj.getWorldChunkManager().getBiomesForGeneration(biomesForGeneration, chunkX * 4 - 2, chunkZ * 4 - 2, var3 + 5, var5 + 5);
		noiseArray = initializeNoiseField(noiseArray, chunkX * var1, 0, chunkZ * var1, var3, var4, var5);

		for (int i = 0; i < var1; ++i)
		{
			for (int j = 0; j < var1; ++j)
			{
				for (int k = 0; k < var2; ++k)
				{
					double var6 = 0.125D;
					double var7 = noiseArray[((i + 0) * var5 + j + 0) * var4 + k + 0];
					double var8 = noiseArray[((i + 0) * var5 + j + 1) * var4 + k + 0];
					double var9 = noiseArray[((i + 1) * var5 + j + 0) * var4 + k + 0];
					double var10 = noiseArray[((i + 1) * var5 + j + 1) * var4 + k + 0];
					double var11 = (noiseArray[((i + 0) * var5 + j + 0) * var4 + k + 1] - var7) * var6;
					double var12 = (noiseArray[((i + 0) * var5 + j + 1) * var4 + k + 1] - var8) * var6;
					double var13 = (noiseArray[((i + 1) * var5 + j + 0) * var4 + k + 1] - var9) * var6;
					double var14 = (noiseArray[((i + 1) * var5 + j + 1) * var4 + k + 1] - var10) * var6;

					for (int l = 0; l < 8; ++l)
					{
						double var15 = 0.25D;
						double var16 = var7;
						double var17 = var8;
						double var18 = (var9 - var7) * var15;
						double var19 = (var10 - var8) * var15;

						for (int m = 0; m < 4; ++m)
						{
							int var20 = m + i * 4 << 11 | 0 + j * 4 << 7 | k * 8 + l;
							short height = 128;
							var20 -= height;
							double var21 = 0.25D;
							double var22 = (var17 - var16) * var21;
							double var23 = var16 - var22;

							for (int n = 0; n < 4; ++n)
							{
								if ((var23 += var22) > 0.0D)
								{
									blocks[var20 += height] = (byte)Block.stone.blockID;
								}
								else if (k * 8 + l < horizon)
								{
									blocks[var20 += height] = (byte)Block.waterStill.blockID;
								}
								else
								{
									blocks[var20 += height] = 0;
								}
							}

							var16 += var18;
							var17 += var19;
						}

						var7 += var11;
						var8 += var12;
						var9 += var13;
						var10 += var14;
					}
				}
			}
		}
	}

	private void replaceBlocksForBiome(int chunkX, int chunkZ, byte[] blocks, BiomeGenBase[] biomes)
	{
		ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, chunkX, chunkZ, blocks, biomes);
		MinecraftForge.EVENT_BUS.post(event);

		if (event.getResult() == Result.DENY)
		{
			return;
		}

		byte horizon = 63;
		double var1 = 0.03125D;
		stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, var1 * 2.0D, var1 * 2.0D, var1 * 2.0D);

		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				int var2 = (int)(stoneNoise[x + z * 16] / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
				int var3 = -1;
				byte top = (byte)Block.ice.blockID;
				byte filler = (byte)Block.ice.blockID;

				for (int y = 127; y >= 0; --y)
				{
					int index = (z * 16 + x) * 128 + y;

					if (y <= 0 + rand.nextInt(4))
					{
						blocks[index] = (byte)Block.bedrock.blockID;
					}
					else
					{
						byte b3 = blocks[index];

						if (b3 == 0)
						{
							var3 = -1;
						}
						else if (b3 == Block.stone.blockID)
						{
							if (var3 == -1)
							{
								if (var2 <= 0)
								{
									top = 0;
									filler = (byte)Block.stone.blockID;
								}
								else if (y >= horizon - 4 && y <= horizon + 1)
								{
									top = filler = (byte)Block.ice.blockID;
								}

								if (y < horizon && top == 0)
								{
									top = (byte)Block.ice.blockID;
								}

								var3 = var2;

								if (y >= horizon - 1)
								{
									blocks[index] = top;
								}
								else
								{
									blocks[index] = filler;
								}
							}
							else if (var3 > 0)
							{
								--var3;
								blocks[index] = filler;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public Chunk loadChunk(int chunkX, int chunkZ)
	{
		return provideChunk(chunkX, chunkZ);
	}

	@Override
	public Chunk provideChunk(int chunkX, int chunkZ)
	{
		rand.setSeed((long)chunkX * 341873128712L + (long)chunkZ * 132897987541L);
		byte[] blocks = new byte[32768];
		generateTerrain(chunkX, chunkZ, blocks);
		biomesForGeneration = worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);
		replaceBlocksForBiome(chunkX, chunkZ, blocks, biomesForGeneration);

		if (Config.generateCaves)
		{
			caveGenerator.generate(this, worldObj, chunkX, chunkZ, blocks);
		}

		if (Config.generateRavine)
		{
			ravineGenerator.generate(this, worldObj, chunkX, chunkZ, blocks);
		}

		if (mapFeaturesEnabled)
		{
			if (Config.generateMineshaft)
			{
				mineshaftGenerator.generate(this, worldObj, chunkX, chunkZ, blocks);
			}

			if (Config.generateVillage)
			{
				villageGenerator.generate(this, worldObj, chunkX, chunkZ, blocks);
			}
		}

		Chunk chunk = new Chunk(worldObj, blocks, chunkX, chunkZ);
		byte[] biomes = chunk.getBiomeArray();

		for (int index = 0; index < biomes.length; ++index)
		{
			biomes[index] = (byte)biomesForGeneration[index].biomeID;
		}

		chunk.generateSkylightMap();

		return chunk;
	}

	private double[] initializeNoiseField(double[] noisefield, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ)
	{
		ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, noisefield, posX, posY, posZ, sizeX, sizeY, sizeZ);
		MinecraftForge.EVENT_BUS.post(event);

		if (event.getResult() == Result.DENY)
		{
			return event.noisefield;
		}

		if (noisefield == null)
		{
			noisefield = new double[sizeX * sizeY * sizeZ];
		}

		if (parabolicField == null)
		{
			parabolicField = new float[25];

			for (int i = -2; i <= 2; ++i)
			{
				for (int j = -2; j <= 2; ++j)
				{
					parabolicField[i + 2 + (j + 2) * 5] = 10.0F / MathHelper.sqrt_float((float)(i * i + j * j) + 0.2F);
				}
			}
		}

		double var1 = 684.412D;
		double var2 = 684.412D;
		noise5 = noiseGen5.generateNoiseOctaves(noise5, posX, posZ, sizeX, sizeZ, 1.121D, 1.121D, 0.5D);
		noise6 = noiseGen6.generateNoiseOctaves(noise6, posX, posZ, sizeX, sizeZ, 200.0D, 200.0D, 0.5D);
		noise3 = noiseGen3.generateNoiseOctaves(noise3, posX, posY, posZ, sizeX, sizeY, sizeZ, var1 / 80.0D, var2 / 160.0D, var1 / 80.0D);
		noise1 = noiseGen1.generateNoiseOctaves(noise1, posX, posY, posZ, sizeX, sizeY, sizeZ, var1, var2, var1);
		noise2 = noiseGen2.generateNoiseOctaves(noise2, posX, posY, posZ, sizeX, sizeY, sizeZ, var1, var2, var1);
		int var3 = 0;
		int var4 = 0;

		for (int i = 0; i < sizeX; ++i)
		{
			for (int j = 0; j < sizeZ; ++j)
			{
				float var5 = 0.0F;
				float var6 = 0.0F;
				float var7 = 0.0F;

				for (int k = -2; k <= 2; ++k)
				{
					for (int l = -2; l <= 2; ++l)
					{
						float var8 = parabolicField[k + 2 + (l + 2) * 5] / (3.0F);

						var5 += 2.0F * var8;
						var6 += 1.0F * var8;
						var7 += var8;
					}
				}

				var5 /= var7;
				var6 /= var7;
				var5 = var5 * 0.9F + 0.1F;
				var6 = (var6 * 4.0F - 1.0F) / 8.0F;
				double var8 = noise6[var4] / 8000.0D;

				if (var8 < 0.0D)
				{
					var8 = -var8 * 0.3D;
				}

				var8 = var8 * 3.0D - 2.0D;

				if (var8 < 0.0D)
				{
					var8 /= 2.0D;

					if (var8 < -1.0D)
					{
						var8 = -1.0D;
					}

					var8 /= 1.4D;
					var8 /= 2.0D;
				}
				else
				{
					if (var8 > 1.0D)
					{
						var8 = 1.0D;
					}

					var8 /= 8.0D;
				}

				++var4;

				for (int k = 0; k < sizeY; ++k)
				{
					double var9 = (double)var6;
					double var10 = (double)var5;
					var9 += var8 * 0.2D;
					var9 = var9 * (double)sizeY / 16.0D;
					double var11 = (double)sizeY / 2.0D + var9 * 4.0D;
					double var12 = 0.0D;
					double var13 = ((double)k - var11) * 12.0D * 128.0D / 128.0D / var10;

					if (var13 < 0.0D)
					{
						var13 *= 4.0D;
					}

					double var14 = noise1[var3] / 512.0D;
					double var15 = noise2[var3] / 512.0D;
					double var16 = (noise3[var3] / 10.0D + 1.0D) / 2.0D;

					if (var16 < 0.0D)
					{
						var12 = var14;
					}
					else if (var16 > 1.0D)
					{
						var12 = var15;
					}
					else
					{
						var12 = var14 + (var15 - var14) * var16;
					}

					var12 -= var13;

					if (k > sizeY - 4)
					{
						double d11 = (double)((float)(k - (sizeY - 4)) / 3.0F);
						var12 = var12 * (1.0D - d11) + -10.0D * d11;
					}

					noisefield[var3] = var12;
					++var3;
				}
			}
		}

		return noisefield;
	}

	@Override
	public boolean chunkExists(int chunkX, int chunkZ)
	{
		return true;
	}

	@Override
	public void populate(IChunkProvider chunkProvider, int chunkX, int chunkZ)
	{
		BlockSand.fallInstantly = true;

		int x = chunkX * 16;
		int z = chunkZ * 16;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(x + 16, z + 16);
		rand.setSeed(worldObj.getSeed());
		long xSeed = rand.nextLong() / 2L * 2L + 1L;
		long zSeed = rand.nextLong() / 2L * 2L + 1L;
		rand.setSeed((long)chunkX * xSeed + (long)chunkZ * zSeed ^ worldObj.getSeed());
		boolean hasVillage = false;

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(chunkProvider, worldObj, rand, chunkX, chunkZ, hasVillage));

		if (mapFeaturesEnabled)
		{
			if (Config.generateMineshaft)
			{
				mineshaftGenerator.generateStructuresInChunk(worldObj, rand, chunkX, chunkZ);
			}

			if (Config.generateVillage)
			{
				hasVillage = villageGenerator.generateStructuresInChunk(worldObj, rand, chunkX, chunkZ);
			}
		}

		int var1, var2, var3;

		if (Config.generateLakes && !hasVillage && rand.nextInt(3) == 0 && TerrainGen.populate(chunkProvider, worldObj, rand, chunkX, chunkZ, hasVillage, EventType.LAKE))
		{
			var1 = x + rand.nextInt(16) + 8;
			var2 = rand.nextInt(128);
			var3 = z + rand.nextInt(16) + 8;
			(new WorldGenLakes(Block.waterStill.blockID)).generate(worldObj, rand, var1, var2, var3);
		}

		boolean doGen = TerrainGen.populate(chunkProvider, worldObj, rand, chunkX, chunkZ, hasVillage, EventType.DUNGEON);
		for (int i = 0; Config.generateDungeons && doGen && i < 8; ++i)
		{
			var1 = x + rand.nextInt(16) + 8;
			var2 = rand.nextInt(128);
			var3 = z + rand.nextInt(16) + 8;
			(new WorldGenDungeonsFrozenland()).generate(worldObj, rand, var1, var2, var3);
		}

		biome.decorate(worldObj, rand, x, z);
		x += 8;
		z += 8;

		doGen = TerrainGen.populate(chunkProvider, worldObj, rand, chunkX, chunkZ, hasVillage, EventType.ICE);
		for (var1 = 0; doGen && var1 < 16; ++var1)
		{
			for (var2 = 0; var2 < 16; ++var2)
			{
				var3 = worldObj.getPrecipitationHeight(x + var1, z + var2);

				if (worldObj.isBlockFreezable(var1 + x, var3 - 1, var2 + z))
				{
					worldObj.setBlock(var1 + x, var3 - 1, var2 + z, Block.ice.blockID, 0, 2);
				}

				if (worldObj.canSnowAt(var1 + x, var3, var2 + z))
				{
					worldObj.setBlock(var1 + x, var3, var2 + z, Block.snow.blockID, 0, 2);
				}
			}
		}

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(chunkProvider, worldObj, rand, chunkX, chunkZ, hasVillage));

		BlockSand.fallInstantly = false;
	}

	@Override
	public boolean saveChunks(boolean flag, IProgressUpdate progress)
	{
		return true;
	}

	@Override
	public void saveExtraData() {}

	@Override
	public boolean unloadQueuedChunks()
	{
		return false;
	}

	@Override
	public boolean canSave()
	{
		return true;
	}

	@Override
	public String makeString()
	{
		return "FrozenlandRandomLevelSource";
	}

	@Override
	public List getPossibleCreatures(EnumCreatureType creature, int x, int y, int z)
	{
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(x, z);

		return biome == null ? null : biome.getSpawnableList(creature);
	}

	@Override
	public ChunkPosition findClosestStructure(World world, String name, int x, int y, int z)
	{
		return "Village".equals(name) ? villageGenerator.getNearestInstance(world, x, y, z) : "Mineshaft".equals(name) ? mineshaftGenerator.getNearestInstance(world, x, y, z) : null;
	}

	@Override
	public int getLoadedChunkCount()
	{
		return 0;
	}

	@Override
	public void recreateStructures(int chunkX, int chunkZ)
	{
		if (mapFeaturesEnabled)
		{
			if (Config.generateMineshaft)
			{
				mineshaftGenerator.generate(this, worldObj, chunkX, chunkZ, (byte[])null);
			}

			if (Config.generateVillage)
			{
				villageGenerator.generate(this, worldObj, chunkX, chunkZ, (byte[])null);
			}
		}
	}
}