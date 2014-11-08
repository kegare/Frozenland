/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import shift.sextiarysector.api.SextiarySectorAPI;

import com.kegare.frozenland.plugin.sextiarysector.SextiarySectorPlugin;

public class ItemIceStick extends ItemFood
{
	public ItemIceStick(String name)
	{
		super(1, 0.05F, false);
		this.setUnlocalizedName(name);
		this.setTextureName("frozenland:ice_stick");
		this.setFull3D();
		this.setAlwaysEdible();
		this.setPotionEffect(Potion.hunger.id, 10, 1, 0.2F);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	protected void onFoodEaten(ItemStack itemstack, World world, EntityPlayer player)
	{
		super.onFoodEaten(itemstack, world, player);

		if (!world.isRemote && SextiarySectorPlugin.enabled())
		{
			SextiarySectorAPI.addMoistureStats(player, 1, 0.5F);
		}
	}
}