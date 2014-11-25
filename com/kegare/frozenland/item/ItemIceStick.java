/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.item;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import shift.sextiarysector.api.IDrink;

import com.kegare.frozenland.core.Frozenland;
import com.kegare.frozenland.plugin.sextiarysector.SextiarySectorPlugin;

import cpw.mods.fml.common.Optional.Interface;

@Interface(iface = "shift.sextiarysector.api.IDrink", modid = SextiarySectorPlugin.MODID, striprefs = true)
public class ItemIceStick extends ItemFood implements IDrink
{
	public ItemIceStick(String name)
	{
		super(1, 0.05F, false);
		this.setUnlocalizedName(name);
		this.setTextureName("frozenland:ice_stick");
		this.setFull3D();
		this.setAlwaysEdible();
		this.setPotionEffect(Potion.hunger.id, 10, 1, 0.2F);
		this.setCreativeTab(Frozenland.tabFrozenland);
	}

	@Override
	public int getMoisture(ItemStack itemstack)
	{
		return 1;
	}

	@Override
	public float getMoistureSaturation(ItemStack itemstack)
	{
		return 0.5F;
	}
}