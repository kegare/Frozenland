/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.world;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.client.IRenderHandler;

import com.kegare.frozenland.api.FrozenlandAPI;
import com.kegare.frozenland.client.renderer.EmptyRenderer;
import com.kegare.frozenland.client.renderer.FrozenlandWheatherRenderer;
import com.kegare.frozenland.world.gen.StructureVillagePieces;
import com.kegare.frozenland.world.gen.StructureVillageStart;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldProviderFrozenland extends WorldProviderSurface
{
	@Override
	protected void registerWorldChunkManager()
	{
		worldChunkMgr = new WorldChunkManagerHell(FrozenlandAPI.getBiome(), 1.0F);
		dimensionId = FrozenlandAPI.getDimension();

		MapGenStructureIO.registerStructure(StructureVillageStart.class, "Village.Frozenland");
		StructureVillagePieces.registerPieces();
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

	@SideOnly(Side.CLIENT)
	@Override
	public float[] calcSunriseSunsetColors(float angle, float ticks)
	{
		float[] colors = super.calcSunriseSunsetColors(angle, ticks);

		if (colors != null)
		{
			for (int i = 0; i < colors.length; ++i)
			{
				colors[i] *= 0.7F;
			}
		}

		return colors;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getCloudHeight()
	{
		return getActualHeight() - 50.0F;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IRenderHandler getSkyRenderer()
	{
		if (super.getSkyRenderer() == null)
		{
			setSkyRenderer(EmptyRenderer.instance);
		}

		return super.getSkyRenderer();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IRenderHandler getWeatherRenderer()
	{
		if (super.getWeatherRenderer() == null)
		{
			setWeatherRenderer(new FrozenlandWheatherRenderer());
		}

		return super.getWeatherRenderer();
	}

	@Override
	public boolean shouldMapSpin(String entity, double posX, double posY, double posZ)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Vec3 getSkyColor(Entity entity, float ticks)
	{
		Vec3 vec = super.getSkyColor(entity, ticks);
		double d = 3.25D;

		return Vec3.createVectorHelper(vec.xCoord / d, vec.yCoord / d, vec.zCoord / d);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Vec3 drawClouds(float ticks)
	{
		long cloudColour = 7829367L;
		float angle = worldObj.getCelestialAngle(ticks);
		float var1 = MathHelper.clamp_float(MathHelper.cos(angle * (float)Math.PI * 2.0F) * 2.0F + 0.5F, 0.05F, 1.0F);
		float vecX = (cloudColour >> 16 & 255L) / 255.0F;
		float vecY = (cloudColour >> 8 & 255L) / 255.0F;
		float vecZ = (cloudColour & 255L) / 255.0F;
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

		return Vec3.createVectorHelper(vecX, vecY, vecZ);
	}

	@Override
	public float getSunBrightnessFactor(float ticks)
	{
		return super.getSunBrightnessFactor(ticks) / 1.75F;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getSunBrightness(float ticks)
	{
		return super.getSunBrightness(ticks) / 2.25F;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getStarBrightness(float ticks)
	{
		return super.getStarBrightness(ticks) / 2.0F;
	}

	@Override
	public long getSeed()
	{
		return Long.reverseBytes(super.getSeed());
	}

	@Override
	public void resetRainAndThunder()
	{
		super.resetRainAndThunder();

		if (worldObj.getGameRules().getGameRuleBooleanValue("doDaylightCycle"))
		{
			WorldInfo worldInfo = ObfuscationReflectionHelper.getPrivateValue(DerivedWorldInfo.class, (DerivedWorldInfo)worldObj.getWorldInfo(), "theWorldInfo", "field_76115_a");
			long i = worldInfo.getWorldTime() + 24000L;

			worldInfo.setWorldTime(i - i % 24000L);
		}
	}

	@Override
	public boolean canSnowAt(int x, int y, int z, boolean checkLight)
	{
		return y < MathHelper.floor_float(getCloudHeight()) && super.canSnowAt(x, y, z, checkLight);
	}

	@Override
	public boolean isBlockHighHumidity(int x, int y, int z)
	{
		return y < MathHelper.floor_float(getCloudHeight()) && super.isBlockHighHumidity(x, y, z);
	}
}