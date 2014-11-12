/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.core;

import java.util.List;
import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Lists;
import com.kegare.frozenland.block.FrozenBlocks;
import com.kegare.frozenland.item.FrozenItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabFrozenland extends CreativeTabs
{
	@SideOnly(Side.CLIENT)
	public ItemStack tabIconItem;

	@SideOnly(Side.CLIENT)
	private List<ItemStack> iconItems;

	public CreativeTabFrozenland()
	{
		super(Frozenland.MODID);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getTabLabel()
	{
		return "Frozenland";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getTranslatedTabLabel()
	{
		return getTabLabel();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getTabIconItem()
	{
		return tabIconItem == null ? null : tabIconItem.getItem();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getIconItemStack()
	{
		if (tabIconItem == null)
		{
			if (iconItems == null)
			{
				iconItems = Lists.newArrayList();
			}

			if (iconItems.isEmpty())
			{
				if (Config.packedIceSlab)
				{
					iconItems.add(new ItemStack(FrozenBlocks.packed_ice_slab));
				}

				if (Config.stairsPackedIce)
				{
					iconItems.add(new ItemStack(FrozenBlocks.packed_ice_stairs));
				}

				if (Config.frozenlandDimensionalBook)
				{
					iconItems.add(new ItemStack(FrozenItems.frozenland_dimensional_book));
				}

				if (Config.stickIce)
				{
					iconItems.add(new ItemStack(FrozenItems.ice_stick));
				}

				if (Config.swordIce)
				{
					iconItems.add(new ItemStack(FrozenItems.ice_sword));
				}

				if (Config.pickaxeIce)
				{
					iconItems.add(new ItemStack(FrozenItems.ice_pickaxe));
				}

				if (Config.axeIce)
				{
					iconItems.add(new ItemStack(FrozenItems.ice_axe));
				}

				if (Config.shovelIce)
				{
					iconItems.add(new ItemStack(FrozenItems.ice_shovel));
				}

				if (Config.hoeIce)
				{
					iconItems.add(new ItemStack(FrozenItems.ice_hoe));
				}
			}

			if (!iconItems.isEmpty())
			{
				if (iconItems.size() > 1)
				{
					tabIconItem = iconItems.get(new Random().nextInt(iconItems.size()));
				}
				else
				{
					tabIconItem = iconItems.get(0);
				}
			}

			if (tabIconItem == null)
			{
				tabIconItem = new ItemStack(Blocks.ice);
			}
		}

		return tabIconItem;
	}
}