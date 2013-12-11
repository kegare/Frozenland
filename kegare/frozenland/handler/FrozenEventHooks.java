package kegare.frozenland.handler;

import kegare.frozenland.core.Config;
import kegare.frozenland.util.FrozenLog;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FrozenEventHooks
{
	@SideOnly(Side.CLIENT)
	@ForgeSubscribe
	public void onSoundLoad(SoundLoadEvent event)
	{
		try
		{
			event.manager.addSound("frozenland:dimensional_teleport_frozenland.ogg");
		}
		catch (Exception e)
		{
			FrozenLog.exception(e);
		}
	}

	@SideOnly(Side.CLIENT)
	@ForgeSubscribe(priority = EventPriority.HIGH)
	public void onRenderOverlayText(RenderGameOverlayEvent.Text event)
	{
		Minecraft mc = FMLClientHandler.instance().getClient();

		if (mc != null && mc.gameSettings.showDebugInfo && mc.thePlayer.dimension == Config.dimensionFrozenland)
		{
			for (String str : event.left)
			{
				if (str == null)
				{
					continue;
				}
				else if (str.startsWith("dim:"))
				{
					return;
				}
			}

			event.left.add("dim: " + mc.thePlayer.worldObj.provider.getDimensionName());
		}
	}

	@ForgeSubscribe
	public void doColdExhaustion(LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayerMP && event.entityLiving.ticksExisted % 900 == 0)
		{
			EntityPlayerMP player = (EntityPlayerMP)event.entityLiving;

			if (!player.capabilities.isCreativeMode && player.dimension == Config.dimensionFrozenland)
			{
				if (player.getArmorVisibility() <= 0.0F)
				{
					player.addExhaustion(0.05F);

					if (player.ticksExisted % 1200 == 0)
					{
						player.attackEntityFrom(DamageSource.generic, 0.5F);
					}
				}
				else if (player.getArmorVisibility() < 0.5F)
				{
					player.addExhaustion(0.035F);
				}
				else if (player.getArmorVisibility() < 0.75F)
				{
					player.addExhaustion(0.015F);
				}
			}
		}
	}
}