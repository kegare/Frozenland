package kegare.frozenland.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.FMLClientHandler;

public class RenderItemBowIce implements IItemRenderer
{
	private static final ResourceLocation ENCHANTED_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

	@Override
	public boolean handleRenderType(ItemStack itemstack, ItemRenderType type)
	{
		if (type == ItemRenderType.EQUIPPED)
		{
			return true;
		}

		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack itemstack, ItemRendererHelper helper)
	{
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(0.6F, -0.6F, -0.25F);
		GL11.glRotatef(15.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(-128.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-10.0F, 1.0F, 0.0F, 1.0F);
		render((EntityLivingBase)data[1], itemstack, 0);
		GL11.glPopMatrix();
	}

	private void render(EntityLivingBase entityLivingBase, ItemStack itemstack, int pass)
	{
		GL11.glPushMatrix();
		TextureManager manager = FMLClientHandler.instance().getClient().getTextureManager();
		Icon icon = entityLivingBase.getItemIcon(itemstack, pass);

		if (icon == null)
		{
			GL11.glPopMatrix();

			return;
		}

		manager.bindTexture(manager.getResourceLocation(itemstack.getItemSpriteNumber()));
		Tessellator tessellator = Tessellator.instance;
		float var1 = icon.getMinU();
		float var2 = icon.getMaxU();
		float var3 = icon.getMinV();
		float var4 = icon.getMaxV();
		float var5 = 0.0F;
		float var6 = 0.3F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslatef(-var5, -var6, 0.0F);
		float var7 = 1.5F;
		GL11.glScalef(var7, var7, var7);
		GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
		ItemRenderer.renderItemIn2D(tessellator, var2, var3, var1, var4, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);

		if (itemstack.hasEffect(pass))
		{
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_LIGHTING);
			manager.bindTexture(ENCHANTED_ITEM_GLINT);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			float var8 = 0.76F;
			GL11.glColor4f(0.5F * var8, 0.25F * var8, 0.8F * var8, 1.0F);
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glPushMatrix();
			float var9 = 0.125F;
			GL11.glScalef(var9, var9, var9);
			float var10 = Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F;
			GL11.glTranslatef(var10, 0.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(var9, var9, var9);
			var10 = Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F;
			GL11.glTranslatef(-var10, 0.0F, 0.0F);
			GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
			GL11.glPopMatrix();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}