/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.client.renderer;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FrozenlandWheatherRenderer extends IRenderHandler
{
	private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");

	private final Random random = new Random();

	private int rendererUpdateCount;
	private float[] rainXCoords;
	private float[] rainYCoords;

	@Override
	public void render(float ticks, WorldClient world, Minecraft mc)
	{
		++rendererUpdateCount;

		float rainStrength = mc.theWorld.getRainStrength(ticks);

		if (rainStrength > 0.0F)
		{
			mc.entityRenderer.enableLightmap(ticks);

			if (rainXCoords == null)
			{
				rainXCoords = new float[1024];
				rainYCoords = new float[1024];

				for (int i = 0; i < 32; ++i)
				{
					for (int j = 0; j < 32; ++j)
					{
						float f1 = j - 16;
						float f2 = i - 16;
						float f3 = MathHelper.sqrt_float(f1 * f1 + f2 * f2);

						rainXCoords[i << 5 | j] = -f2 / f3;
						rainYCoords[i << 5 | j] = f1 / f3;
					}
				}
			}

			EntityLivingBase entity = mc.renderViewEntity;
			int x = MathHelper.floor_double(entity.posX);
			int y = MathHelper.floor_double(entity.posY);
			int z = MathHelper.floor_double(entity.posZ);
			Tessellator tessellator = Tessellator.instance;
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ticks;
			double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ticks;
			double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ticks;
			int i = MathHelper.floor_double(d1);
			byte range = 5;

			if (mc.gameSettings.fancyGraphics)
			{
				range = 10;
			}

			byte b1 = -1;
			float f1 = rendererUpdateCount + ticks;

			if (mc.gameSettings.fancyGraphics)
			{
				range = 10;
			}

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			for (int j = z - range; j <= z + range; ++j)
			{
				for (int k = x - range; k <= x + range; ++k)
				{
					int index = (j - z + 16) * 32 + k - x + 16;
					float f2 = rainXCoords[index] * 0.5F;
					float f3 = rainYCoords[index] * 0.5F;
					int height = world.getPrecipitationHeight(k, j);
					int cloud = MathHelper.floor_float(world.provider.getCloudHeight());
					int l = MathHelper.clamp_int(y - range, height, cloud);
					int m = MathHelper.clamp_int(y + range, height, cloud);

					if (l == cloud || m == cloud)
					{
						continue;
					}

					float f4 = 1.35F;
					int n = height;

					if (height < i)
					{
						n = i;
					}

					if (l != m)
					{
						random.setSeed(k * k * 3121 + k * 45238971 ^ j * j * 418711 + j * 13761);

						if (b1 != 1)
						{
							if (b1 >= 0)
							{
								tessellator.draw();
							}

							b1 = 1;
							mc.getTextureManager().bindTexture(locationSnowPng);
							tessellator.startDrawingQuads();
						}

						float f5 = ((rendererUpdateCount & 0x1FF) + ticks) / 512.0F;
						float f6 = random.nextFloat() + f1 * 0.01F * (float)random.nextGaussian();
						float f7 = random.nextFloat() + f1 * (float)random.nextGaussian() * 0.001F;
						double xDist = k + 0.5F - entity.posX;
						double zDist = j + 0.5F - entity.posZ;
						float f8 = MathHelper.sqrt_double(xDist * xDist + zDist * zDist) / range;
						tessellator.setBrightness((world.getLightBrightnessForSkyBlocks(k, n, j, 0) * 3 + 15728880) / 4);
						tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, ((1.0F - f8 * f8) * 0.3F + 0.5F) * rainStrength);
						tessellator.setTranslation(-d0 * 1.0D, -d1 * 1.0D, -d2 * 1.0D);
						tessellator.addVertexWithUV(k - f2 + 0.5D, l, j - f3 + 0.5D, 0.0F * f4 + f6, l * f4 / 4.0F + f5 * f4 + f7);
						tessellator.addVertexWithUV(k + f2 + 0.5D, l, j + f3 + 0.5D, 1.0F * f4 + f6, l * f4 / 4.0F + f5 * f4 + f7);
						tessellator.addVertexWithUV(k + f2 + 0.5D, m, j + f3 + 0.5D, 1.0F * f4 + f6, m * f4 / 4.0F + f5 * f4 + f7);
						tessellator.addVertexWithUV(k - f2 + 0.5D, m, j - f3 + 0.5D, 0.0F * f4 + f6, m * f4 / 4.0F + f5 * f4 + f7);
						tessellator.setTranslation(0.0D, 0.0D, 0.0D);
					}
				}
			}

			if (b1 >= 0)
			{
				tessellator.draw();
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);

			mc.entityRenderer.disableLightmap(ticks);
		}
	}
}