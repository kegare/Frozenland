package kegare.frozenland.handler;

import java.util.Random;

import kegare.frozenland.core.Config;
import kegare.frozenland.item.ItemPickaxeIce;
import kegare.frozenland.util.FrozenLog;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.BlockEvent;
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
	public void doRenderOverlayDim(RenderGameOverlayEvent.Text event)
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

	@ForgeSubscribe
	public void onBlockBreakFrozenlandIce(BlockEvent.BreakEvent event)
	{
		World world = event.world;
		EntityPlayer player = event.getPlayer();

		if (!world.isRemote && !player.capabilities.isCreativeMode)
		{
			int x = event.x;
			int y = event.y;
			int z = event.z;
			int blockID = world.getBlockId(x, y, z);
			ItemStack itemstack = player.getCurrentEquippedItem();

			if (world.provider.dimensionId == Config.dimensionFrozenland && blockID == Block.ice.blockID && itemstack != null)
			{
				Random random = new Random();
				Item item = itemstack.getItem();

				if (item instanceof ItemPickaxe)
				{
					int rate = 10;

					world.playAuxSFXAtEntity(player, 2001, x, y, z, blockID + (world.getBlockMetadata(x, y, z) << 12));
					world.setBlockToAir(x, y, z);

					if (EnchantmentHelper.getSilkTouchModifier(player))
					{
						rate = 1;
					}
					else if (item instanceof ItemPickaxeIce)
					{
						rate = 5;
					}

					if (rate == 1 ? true : rate > 0 && random.nextInt(rate) == 0)
					{
						EntityItem entity = new EntityItem(world, (double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, new ItemStack(Block.ice));
						entity.delayBeforeCanPickup = 10;

						world.spawnEntityInWorld(entity);
					}

					event.setCanceled(true);
				}
			}
		}
	}
}