package kegare.frozenland.world;

import java.io.File;
import java.io.OutputStreamWriter;
import java.util.Random;

import kegare.frozenland.core.Config;
import kegare.frozenland.renderer.EmptyRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.common.DimensionManager;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.primitives.Longs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldProviderFrozenland extends WorldProvider
{
	public static long dimensionSeed;

	@Override
	protected void registerWorldChunkManager()
	{
		worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.iceMountains, -2.0F, 2.0F);
		dimensionId = Config.dimensionFrozenland;
	}

	@Override
	public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderFrozenland(worldObj);
	}

	@Override
	public boolean canCoordinateBeSpawn(int x, int z)
	{
		return !worldObj.isAirBlock(x, worldObj.getTopSolidOrLiquidBlock(x, z), z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float[] calcSunriseSunsetColors(float angle, float ticks)
	{
		return null;
	}

	@Override
	public String getDimensionName()
	{
		return "Frozenland";
	}

	@Override
	public String getSaveFolder()
	{
		return "DIM-Frozenland";
	}

	@Override
	public String getWelcomeMessage()
	{
		return "Entering the Frozenland";
	}

	@Override
	public String getDepartMessage()
	{
		return "Leaving the Frozenland";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer()
	{
		return new EmptyRenderer();
	}

	@Override
	public boolean shouldMapSpin(String entity, double posX, double posY, double posZ)
	{
		return false;
	}

	@Override
	public boolean isDaytime()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getSkyColor(Entity entity, float ticks)
	{
		return worldObj.getWorldVec3Pool().getVecFromPool(0.175D, 0.175D, 0.175D);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 drawClouds(float ticks)
	{
		long cloudColour = 7829367L;
		float angle = worldObj.getCelestialAngle(ticks);
		float var1 = MathHelper.cos(angle * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

		if (var1 < 0.0F)
		{
			var1 = 0.0F;
		}

		if (var1 > 1.0F)
		{
			var1 = 1.0F;
		}

		float vecX = (float)(cloudColour >> 16 & 255L) / 255.0F;
		float vecY = (float)(cloudColour >> 8 & 255L) / 255.0F;
		float vecZ = (float)(cloudColour & 255L) / 255.0F;
		float rainStrength = worldObj.getRainStrength(ticks);
		float var2;
		float var3;

		if (rainStrength > 0.0F)
		{
			var2 = (vecX * 0.3F + vecY * 0.59F + vecZ * 0.11F) * 0.6F;
			var3 = 1.0F - rainStrength * 0.95F;
			vecX = vecX * var3 + var2 * (1.0F - var3);
			vecY = vecY * var3 + var2 * (1.0F - var3);
			vecZ = vecZ * var3 + var2 * (1.0F - var3);
		}

		vecX *= var1 * 0.9F + 0.1F;
		vecY *= var1 * 0.9F + 0.1F;
		vecZ *= var1 * 0.85F + 0.15F;
		var2 = worldObj.getWeightedThunderStrength(ticks);

		if (var2 > 0.0F)
		{
			var3 = (vecX * 0.3F + vecY * 0.59F + vecZ * 0.11F) * 0.2F;
			float var4 = 1.0F - var2 * 0.95F;
			vecX = vecX * var4 + var3 * (1.0F - var4);
			vecY = vecY * var4 + var3 * (1.0F - var4);
			vecZ = vecZ * var4 + var3 * (1.0F - var4);
		}

		return worldObj.getWorldVec3Pool().getVecFromPool((double)vecX, (double)vecY, (double)vecZ);
	}

	@Override
	public void calculateInitialWeather()
	{
		worldObj.rainingStrength = 1.0F;
	}

	@Override
	public void updateWeather()
	{
		worldObj.prevRainingStrength = worldObj.rainingStrength;
		worldObj.rainingStrength = (float)((double)worldObj.rainingStrength + 0.01D);

		if (worldObj.rainingStrength < 0.0F)
		{
			worldObj.rainingStrength = 0.0F;
		}

		if (worldObj.rainingStrength > 1.0F)
		{
			worldObj.rainingStrength = 1.0F;
		}
	}

	@Override
	public long getSeed()
	{
		if (!worldObj.isRemote && dimensionSeed == 0)
		{
			try
			{
				File dir = new File(DimensionManager.getCurrentSaveRootDirectory(), getSaveFolder());

				if (!dir.exists())
				{
					dir.mkdirs();
				}

				File file = new File(dir, "frozenland.txt");

				if (file.createNewFile())
				{
					OutputStreamWriter writer = Files.newWriterSupplier(file, Charsets.US_ASCII).getOutput();

					writer.write(Long.valueOf((new Random()).nextLong()).toString());
					writer.close();
				}

				dimensionSeed = Longs.tryParse(Files.readFirstLine(file, Charsets.US_ASCII));
			}
			catch (Exception e)
			{
				dimensionSeed = Long.reverseBytes(worldObj.getWorldInfo().getSeed());
			}
		}

		return dimensionSeed;
	}
}