package kegare.frozenland.item;

import java.util.Random;

import kegare.frozenland.core.Frozenland;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBowIce extends ItemBow
{
	public ItemBowIce(int itemID, String name)
	{
		super(itemID - 256);
		this.setUnlocalizedName(name);
		this.setTextureName("frozenland:ice_bow");
		this.setMaxDamage(120);
	}

	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer player, int count)
	{
		int charge = this.getMaxItemUseDuration(itemstack) - count;
		ArrowLooseEvent event = new ArrowLooseEvent(player, itemstack, charge);

		if (MinecraftForge.EVENT_BUS.post(event))
		{
			return;
		}

		charge = event.charge;

		Random rand = new Random();
		BiomeGenBase biome = world.getBiomeGenForCoords(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posZ));
		boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) > 0;

		if (flag || player.inventory.hasItem(Item.arrow.itemID))
		{
			float power = (float)charge / 20.0F;
			power = (power * power + power * 2.0F) / 3.5F;

			if ((double)power < 0.1D)
			{
				return;
			}

			if (power > 1.0F)
			{
				power = 1.0F;
			}

			EntityArrow entityArrow = new EntityArrow(world, player, power * 2.0F);

			if (power == 1.0F)
			{
				entityArrow.setIsCritical(true);
			}

			int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);

			if (powerLevel > 0)
			{
				entityArrow.setDamage(entityArrow.getDamage() + (double)powerLevel * 0.5D + 0.5D);
			}

			int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);

			if (punchLevel > 0)
			{
				entityArrow.setKnockbackStrength(punchLevel);
			}

			if (itemstack.isItemDamaged() && BiomeDictionary.isBiomeOfType(biome, Type.FROZEN) ? rand.nextInt(4) > 0 : true)
			{
				itemstack.damageItem(1, player);
			}

			world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + power * 0.5F);

			if (flag)
			{
				entityArrow.canBePickedUp = 2;
			}
			else
			{
				player.inventory.consumeInventoryItem(Item.arrow.itemID);
			}

			if (!world.isRemote)
			{
				world.spawnEntityInWorld(entityArrow);
			}
		}
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean current)
	{
		super.onUpdate(itemstack, world, entity, slot, current);

		if (!world.isRemote && entity instanceof EntityPlayer)
		{
			NBTTagCompound nbt = itemstack.getTagCompound();
			EntityPlayer player = (EntityPlayer)entity;

			if (nbt == null)
			{
				nbt = new NBTTagCompound();

				itemstack.setTagCompound(nbt);
			}

			nbt.setString("Owner", player.username);
		}
	}

	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player)
	{
		super.onCreated(itemstack, world, player);

		if (!world.isRemote)
		{
			NBTTagCompound nbt = itemstack.getTagCompound();

			if (nbt == null)
			{
				nbt = new NBTTagCompound();

				itemstack.setTagCompound(nbt);
			}

			nbt.setString("Owner", player.username);
		}
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entity)
	{
		World world = entity.worldObj;

		if (!world.isRemote && entity.onGround && entity.ticksExisted % 120 == 0)
		{
			ItemStack itemstack = entity.getEntityItem();
			NBTTagCompound nbt = itemstack.getTagCompound();
			int x = MathHelper.floor_double(entity.posX);
			int y = MathHelper.floor_double(entity.posY) - 1;
			int z = MathHelper.floor_double(entity.posZ);
			BiomeGenBase biome = world.getBiomeGenForCoords(x, z);

			if (itemstack.isItemDamaged() && nbt != null && biome.getFloatTemperature() < 1.0F && world.getBlockId(x, y, z) == Block.ice.blockID)
			{
				EntityPlayerMP player = Frozenland.proxy.getServer().getConfigurationManager().getPlayerForUsername(nbt.getString("Owner"));

				if (player != null)
				{
					itemstack.damageItem(entity.isInWater() ? 1 : -1, player);
				}
			}
		}

		return super.onEntityItemUpdate(entity);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(ItemStack itemstack, int pass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
		if (usingItem != null && usingItem.getItem().itemID == itemID)
		{
			int charge = usingItem.getMaxItemUseDuration() - useRemaining;
			if (charge >= 18) return getItemIconForUseDuration(2);
			if (charge > 13) return getItemIconForUseDuration(1);
			if (charge > 0) return getItemIconForUseDuration(0);
		}

		return super.getIcon(itemstack, pass, player, usingItem, useRemaining);
	}
}