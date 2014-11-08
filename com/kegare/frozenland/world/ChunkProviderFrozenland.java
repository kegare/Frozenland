/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.world;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType;
import net.minecraftforge.event.terraingen.TerrainGen;

import com.google.common.base.Strings;
import com.kegare.frozenland.core.Config;
import com.kegare.frozenland.world.gen.MapGenCavesFrozenland;
import com.kegare.frozenland.world.gen.MapGenRavineFrozenland;
import com.kegare.frozenland.world.gen.MapGenVillageFrozenland;
import com.kegare.frozenland.world.gen.WorldGenDungeonsFrozenland;

import cpw.mods.fml.common.eventhandler.Event.Result;

public class ChunkProviderFrozenland implements IChunkProvider
{
	private final World worldObj;
	private final Random rand;
	private final boolean mapFeaturesEnabled;

	private NoiseGeneratorOctaves noiseGen1;
	private NoiseGeneratorOctaves noiseGen2;
	private NoiseGeneratorOctaves noiseGen3;
	private NoiseGeneratorPerlin noiseGen4;
	private NoiseGeneratorOctaves noiseGen5;
	private NoiseGeneratorOctaves noiseGen6;

	private final double[] noiseField;
	private final float[] parabolicField;
	private double[] stoneNoise = new double[256];

	private BiomeGenBase[] biomesForGeneration;
	private double[] noise1;
	private double[] noise2;
	private double[] noise3;
	private double[] noise6;

	private final MapGenBase caveGenerator = new MapGenCavesFrozenland();
	private final MapGenBase ravineGenerator = new MapGenRavineFrozenland();
	private MapGenStructure mineshaftGenerator = new MapGenMineshaft();
	private final MapGenStructure villageGenerator = new MapGenVillageFrozenland();

	private final WorldGenerator lakeWaterGen = new WorldGenLakes(Blocks.water);
	private final WorldGenerator dungeonGen = new WorldGenDungeonsFrozenland();

	{
		mineshaftGenerator = (MapGenStructure)TerrainGen.getModdedMapGen(mineshaftGenerator, InitMapGenEvent.EventType.MINESHAFT);
	}

	public ChunkProviderFrozenland(World world)
	{
		this.worldObj = world;
		this.rand = new Random(world.getSeed());
		this.mapFeaturesEnabled = world.getWorldInfo().isMapFeaturesEnabled();
		this.noiseGen1 = new NoiseGeneratorOctaves(rand, 16);
		this.noiseGen2 = new NoiseGeneratorOctaves(rand, 16);
		this.noiseGen3 = new NoiseGeneratorOctaves(rand, 8);
		this.noiseGen4 = new NoiseGeneratorPerlin(rand, 4);
		this.noiseGen5 = new NoiseGeneratorOctaves(rand, 10);
		this.noiseGen6 = new NoiseGeneratorOctaves(rand, 16);
		this.noiseField = new double[825];
		this.parabolicField = new float[25];

		for (int i = -2; i <= 2; ++i)
		{
			for (int j = -2; j <= 2; ++j)
			{
				float f = 10.0F / MathHelper.sqrt_float(i * i + j * j + 0.2F);

				parabolicField[i + 2 + (j + 2) * 5] = f;
			}
		}

		NoiseGenerator[] noiseGens = {noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5, noiseGen6};
		noiseGens = TerrainGen.getModdedNoiseGenerators(world, rand, noiseGens);
		this.noiseGen1 = (NoiseGeneratorOctaves)noiseGens[0];
		this.noiseGen2 = (NoiseGeneratorOctaves)noiseGens[1];
		this.noiseGen3 = (NoiseGeneratorOctaves)noiseGens[2];
		this.noiseGen4 = (NoiseGeneratorPerlin)noiseGens[3];
		this.noiseGen5 = (NoiseGeneratorOctaves)noiseGens[4];
		this.noiseGen6 = (NoiseGeneratorOctaves)noiseGens[5];
	}

	public void generateTerrain(int chunkX, int chunkZ, Block[] blocks)
	{
		biomesForGeneration = worldObj.getWorldChunkManager().getBiomesForGeneration(biomesForGeneration, chunkX * 4 - 2, chunkZ * 4 - 2, 10, 10);
		initializeNoiseField(chunkX * 4, 0, chunkZ * 4);

		for (int i = 0; i < 4; ++i)
		{
			int j = i * 5;
			int k = (i + 1) * 5;

			for (int l = 0; l < 4; ++l)
			{
				int k1 = (j + l) * 33;
				int l1 = (j + l + 1) * 33;
				int i2 = (k + l) * 33;
				int j2 = (k + l + 1) * 33;

				for (int m = 0; m < 32; ++m)
				{
					double d0 = 0.125D;
					double d1 = noiseField[k1 + m];
					double d2 = noiseField[l1 + m];
					double d3 = noiseField[i2 + m];
					double d4 = noiseField[j2 + m];
					double d5 = (noiseField[k1 + m + 1] - d1) * d0;
					double d6 = (noiseField[l1 + m + 1] - d2) * d0;
					double d7 = (noiseField[i2 + m + 1] - d3) * d0;
					double d8 = (noiseField[j2 + m + 1] - d4) * d0;

					for (int n = 0; n < 8; ++n)
					{
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int i3 = 0; i3 < 4; ++i3)
						{
							int index = i3 + i * 4 << 12 | 0 + l * 4 << 8 | m * 8 + n;
							short height = 256;
							index -= height;
							double d14 = 0.25D;
							double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;

							for (int k3 = 0; k3 < 4; ++k3)
							{
								if ((d15 += d16) > 0.0D)
								{
									blocks[index += height] = Blocks.stone;
								}
								else if (m * 8 + n < 63)
								{
									blocks[index += height] = Blocks.water;
								}
								else
								{
									blocks[index += height] = null;
								}
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}

	private void initializeNoiseField(int posX, int posY, int posZ)
	{
		noise6 = noiseGen6.generateNoiseOctaves(noise6, posX, posZ, 5, 5, 200.0D, 200.0D, 0.5D);
		noise3 = noiseGen3.generateNoiseOctaves(noise3, posX, posY, posZ, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
		noise1 = noiseGen1.generateNoiseOctaves(noise1, posX, posY, posZ, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		noise2 = noiseGen2.generateNoiseOctaves(noise2, posX, posY, posZ, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		int i = 0;
		int j = 0;

		for (int k = 0; k < 5; ++k)
		{
			for (int l = 0; l < 5; ++l)
			{
				float f = 0.0F;
				float f1 = 0.0F;
				float f2 = 0.0F;
				byte b0 = 2;
				BiomeGenBase biome = biomesForGeneration[k + 2 + (l + 2) * 10];

				for (int m = -b0; m <= b0; ++m)
				{
					for (int n = -b0; n <= b0; ++n)
					{
						BiomeGenBase biome1 = biomesForGeneration[k + m + 2 + (l + n + 2) * 10];
						float f3 = biome1.rootHeight;
						float f4 = biome1.heightVariation;

						if (rand.nextInt(100) == 0)
						{
							f3 += 1.5F + rand.nextFloat();
							f4 = 1.0F + f4 * 3.0F;
						}
						else if (rand.nextInt(50) == 0)
						{
							f3 -= rand.nextFloat();
						}

						float f5 = parabolicField[m + 2 + (n + 2) * 5] / (f3 + 2.0F);

						if (biome1.rootHeight > biome.rootHeight)
						{
							f5 /= 2.0F;
						}

						f += f4 * f5;
						f1 += f3 * f5;
						f2 += f5;
					}
				}

				f /= f2;
				f1 /= f2;
				f = f * 0.9F + 0.1F;
				f1 = (f1 * 4.0F - 1.0F) / 8.0F;
				double d0 = noise6[j] / 8000.0D;

				if (d0 < 0.0D)
				{
					d0 = -d0 * 0.3D;
				}

				d0 = d0 * 3.0D - 2.0D;

				if (d0 < 0.0D)
				{
					d0 /= 2.0D;

					if (d0 < -1.0D)
					{
						d0 = -1.0D;
					}

					d0 /= 1.4D;
					d0 /= 2.0D;
				}
				else
				{
					if (d0 > 1.0D)
					{
						d0 = 1.0D;
					}

					d0 /= 8.0D;
				}

				++j;
				double d1 = f1;
				double d2 = f;
				d1 += d0 * 0.2D;
				d1 = d1 * 8.5D / 8.0D;
				double d3 = 8.5D + d1 * 4.0D;

				for (int m = 0; m < 33; ++m)
				{
					double d4 = (m - d3) * 12.0D * 128.0D / 256.0D / d2;

					if (d4 < 0.0D)
					{
						d4 *= 4.0D;
					}

					double d5 = noise1[i] / 512.0D;
					double d6 = noise2[i] / 512.0D;
					double d7 = (noise3[i] / 10.0D + 1.0D) / 2.0D;
					double d8 = MathHelper.denormalizeClamp(d5, d6, d7) - d4;

					if (m > 29)
					{
						double d11 = (m - 29) / 3.0F;
						d8 = d8 * (1.0D - d11) + -10.0D * d11;
					}

					noiseField[i] = d8;
					++i;
				}
			}
		}
	}

	private void replaceBlocksForBiome(int chunkX, int chunkZ, Block[] blocks, byte[] meta, BiomeGenBase[] biomes)
	{
		ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, chunkX, chunkZ, blocks, meta, biomes, worldObj);
		MinecraftForge.EVENT_BUS.post(event);

		if (event.getResult() == Result.DENY)
		{
			return;
		}

		double d0 = 0.03125D;
		stoneNoise = noiseGen4.func_151599_a(stoneNoise, chunkX * 16, chunkZ * 16, 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

		for (int i = 0; i < 16; ++i)
		{
			for (int j = 0; j < 16; ++j)
			{
				BiomeGenBase biome = biomes[j + i * 16];

				biome.genTerrainBlocks(worldObj, rand, blocks, meta, chunkX * 16 + i, chunkZ * 16 + j, stoneNoise[j + i * 16]);
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
		rand.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);

		Block[] blocks = new Block[65536];
		byte[] meta = new byte[65536];
		generateTerrain(chunkX, chunkZ, blocks);
		biomesForGeneration = worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);
		replaceBlocksForBiome(chunkX, chunkZ, blocks, meta, biomesForGeneration);

		if (Config.generateCaves)
		{
			caveGenerator.func_151539_a(this, worldObj, chunkX, chunkZ, blocks);
		}

		if (Config.generateRavine)
		{
			ravineGenerator.func_151539_a(this, worldObj, chunkX, chunkZ, blocks);
		}

		if (mapFeaturesEnabled)
		{
			if (Config.generateMineshaft)
			{
				mineshaftGenerator.func_151539_a(this, worldObj, chunkX, chunkZ, blocks);
			}

			if (Config.generateVillage)
			{
				villageGenerator.func_151539_a(this, worldObj, chunkX, chunkZ, blocks);
			}
		}

		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				int i = (x * 16 + z) * 256;

				for (int y = 1; y < worldObj.getActualHeight() - 3; ++y)
				{
					if (blocks[i + y] != null && blocks[i + y].getMaterial() != Material.ice && blocks[i + y].getMaterial().isSolid() && blocks[i + y + 1] == null)
					{
						blocks[i + y] = Blocks.packed_ice;
						meta[i + y] = 0;
					}
				}
			}
		}

		Chunk chunk = new Chunk(worldObj, blocks, meta, chunkX, chunkZ);
		byte[] biomes = chunk.getBiomeArray();

		for (int index = 0; index < biomes.length; ++index)
		{
			biomes[index] = (byte)biomesForGeneration[index].biomeID;
		}

		chunk.generateSkylightMap();

		return chunk;
	}

	@Override
	public boolean chunkExists(int chunkX, int chunkZ)
	{
		return true;
	}

	@Override
	public void populate(IChunkProvider chunkProvider, int chunkX, int chunkZ)
	{
		BlockFalling.fallInstantly = true;

		int x = chunkX * 16;
		int z = chunkZ * 16;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(x + 16, z + 16);
		rand.setSeed(worldObj.getSeed());
		long xSeed = rand.nextLong() / 2L * 2L + 1L;
		long zSeed = rand.nextLong() / 2L * 2L + 1L;
		rand.setSeed(chunkX * xSeed + chunkZ * zSeed ^ worldObj.getSeed());
		boolean hasVillage = false;

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

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(chunkProvider, worldObj, rand, chunkX, chunkZ, hasVillage));

		int var1, var2, var3;
		boolean doGen;

		if (!hasVillage && rand.nextInt(3) == 0 && TerrainGen.populate(chunkProvider, worldObj, rand, chunkX, chunkZ, hasVillage, EventType.LAKE))
		{
			var1 = x + rand.nextInt(16) + 8;
			var2 = rand.nextInt(128);
			var3 = z + rand.nextInt(16) + 8;

			lakeWaterGen.generate(worldObj, rand, var1, var2, var3);
		}

		doGen = TerrainGen.populate(chunkProvider, worldObj, rand, chunkX, chunkZ, hasVillage, EventType.DUNGEON);
		for (int i = 0; Config.generateDungeons && doGen && i < 8; ++i)
		{
			var1 = x + rand.nextInt(16) + 8;
			var2 = rand.nextInt(128);
			var3 = z + rand.nextInt(16) + 8;

			dungeonGen.generate(worldObj, rand, var1, var2, var3);
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
					worldObj.setBlock(var1 + x, var3 - 1, var2 + z, Blocks.ice, 0, 2);
				}

				if (worldObj.func_147478_e(var1 + x, var3, var2 + z, true))
				{
					worldObj.setBlock(var1 + x, var3, var2 + z, Blocks.snow_layer, 0, 2);
				}
			}
		}

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(chunkProvider, worldObj, rand, chunkX, chunkZ, hasVillage));

		BlockFalling.fallInstantly = false;
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
	public ChunkPosition func_147416_a(World world, String name, int x, int y, int z)
	{
		if (Strings.isNullOrEmpty(name))
		{
			return null;
		}

		switch (name)
		{
			case "Mineshaft":
				return mineshaftGenerator.func_151545_a(world, x, y, z);
			case "Village":
				return villageGenerator.func_151545_a(world, x, y, z);
			default:
				return null;
		}
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
				mineshaftGenerator.func_151539_a(this, worldObj, chunkX, chunkZ, null);
			}

			if (Config.generateVillage)
			{
				villageGenerator.func_151539_a(this, worldObj, chunkX, chunkZ, null);
			}
		}
	}
}