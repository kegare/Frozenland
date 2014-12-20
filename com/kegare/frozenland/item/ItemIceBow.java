package com.kegare.frozenland.item;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

import com.kegare.frozenland.api.IItemIceTool;
import com.kegare.frozenland.core.Frozenland;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemIceBow extends ItemBow implements IItemIceTool
{
	private final Random random = new Random();

	public ItemIceBow(String name)
	{
		super();
		this.setUnlocalizedName(name);
		this.setTextureName("frozenland:ice_bow");
		this.setCreativeTab(Frozenland.tabFrozenland);
	}

	@Override
	public int getGrade(ItemStack itemstack)
	{
		if (itemstack == null || itemstack.getItem() != this || itemstack.getTagCompound() == null)
		{
			return 0;
		}

		return itemstack.getTagCompound().getInteger("Grade");
	}

	@Override
	public void setGrade(ItemStack itemstack, int grade)
	{
		if (itemstack == null || itemstack.getItem() != this)
		{
			return;
		}

		if (itemstack.getTagCompound() == null)
		{
			itemstack.setTagCompound(new NBTTagCompound());
		}

		itemstack.getTagCompound().setInteger("Grade", grade);
	}

	@Override
	public void addGrade(ItemStack itemstack, int amount)
	{
		setGrade(itemstack, getGrade(itemstack) + amount);
	}

	@Override
	public int getMaxDamage(ItemStack itemstack)
	{
		int max = super.getMaxDamage(itemstack);

		return max + max / 4 * getGrade(itemstack);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer player, int useRemaining)
	{
		int charge = this.getMaxItemUseDuration(itemstack) - useRemaining;

		ArrowLooseEvent event = new ArrowLooseEvent(player, itemstack, charge);
		MinecraftForge.EVENT_BUS.post(event);

		if (event.isCanceled())
		{
			return;
		}

		charge = event.charge;

		boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) > 0;

		if (flag || player.inventory.hasItem(Items.arrow))
		{
			float power = charge / 20.0F;
			power = (power * power + power * 2.0F) / 3.0F;

			if (power < 0.1D)
			{
				return;
			}

			if (power > 1.0F)
			{
				power = 1.0F;
			}

			EntityArrow arrow = new EntityArrow(world, player, power * (1.25F + random.nextFloat() * 0.5F));

			int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);

			if (i > 0)
			{
				arrow.setDamage(arrow.getDamage() + i * 0.5D + 0.5D);
			}

			i = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);

			if (i > 0)
			{
				arrow.setKnockbackStrength(i);
			}

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) > 0)
			{
				arrow.setFire(20);
			}

			itemstack.damageItem(1, player);
			world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + power * 0.5F);

			if (flag)
			{
				arrow.canBePickedUp = 2;
			}
			else
			{
				player.inventory.consumeInventoryItem(Items.arrow);
			}

			if (!world.isRemote)
			{
				world.spawnEntityInWorld(arrow);
			}
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack)
	{
		return super.getMaxItemUseDuration(itemstack) / 2;
	}

	@Override
	public int getItemEnchantability()
	{
		return 0;
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entity)
	{
		World world = entity.worldObj;

		if (!world.isRemote && entity.onGround && entity.ticksExisted % 120 == 0)
		{
			ItemStack itemstack = entity.getEntityItem();
			int x = MathHelper.floor_double(entity.posX);
			int y = MathHelper.floor_double(entity.posY) - 1;
			int z = MathHelper.floor_double(entity.posZ);
			BiomeGenBase biome = world.getBiomeGenForCoords(x, z);

			if (BiomeDictionary.isBiomeOfType(biome, Type.HOT))
			{
				if (itemstack.attemptDamageItem(1, random) && --itemstack.stackSize <= 0)
				{
					entity.setEntityItemStack(null);
					entity.setDead();

					return false;
				}
			}
			else if (itemstack.isItemDamaged() && BiomeDictionary.isBiomeOfType(biome, Type.COLD) && world.getBlock(x, y, z).getMaterial() == Material.ice)
			{
				itemstack.attemptDamageItem(-1, random);
			}
		}

		return super.onEntityItemUpdate(entity);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean advanced)
	{
		if (advanced || GuiConfig.isShiftKeyDown())
		{
			int grade = getGrade(itemstack);

			if (grade > 0 || GuiConfig.isShiftKeyDown())
			{
				list.add(I18n.format("item.toolIce.upgraded") + ": " + grade);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(ItemStack itemstack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
		if (usingItem != null && usingItem.getItem() == this)
		{
			int i = usingItem.getMaxItemUseDuration() - useRemaining;

			if (i >= 10)
			{
				return getItemIconForUseDuration(2);
			}

			if (i >= 5)
			{
				return getItemIconForUseDuration(1);
			}

			if (i > 0)
			{
				return getItemIconForUseDuration(0);
			}
		}

		return super.getIcon(itemstack, renderPass, player, usingItem, useRemaining);
	}
}