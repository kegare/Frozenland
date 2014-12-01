/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.client.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderIceball extends Render
{
	@Override
	public void doRender(Entity entity, double posX, double posY, double posZ, float yaw, float ticks)
	{
		IIcon icon = Items.snowball.getIconFromDamage(0);

		if (icon != null)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef((float)posX, (float)posY, (float)posZ);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			bindEntityTexture(entity);
			Tessellator tessellator = Tessellator.instance;
			int i = 0xD3EDFB;
			float r = (i >> 16 & 255) / 255.0F;
			float g = (i >> 8 & 255) / 255.0F;
			float b = (i & 255) / 255.0F;
			GL11.glColor3f(r, g, b);
			GL11.glPushMatrix();
			doRender(tessellator, icon);
			GL11.glPopMatrix();
			GL11.glColor3f(1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return TextureMap.locationItemsTexture;
	}

	private void doRender(Tessellator tessellator, IIcon icon)
	{
		float f = icon.getMinU();
		float f1 = icon.getMaxU();
		float f2 = icon.getMinV();
		float f3 = icon.getMaxV();
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		GL11.glRotatef(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertexWithUV(0.0F - f5, 0.0F - f6, 0.0D, f, f3);
		tessellator.addVertexWithUV(f4 - f5, 0.0F - f6, 0.0D, f1, f3);
		tessellator.addVertexWithUV(f4 - f5, f4 - f6, 0.0D, f1, f2);
		tessellator.addVertexWithUV(0.0F - f5, f4 - f6, 0.0D, f, f2);
		tessellator.draw();
	}
}