/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import com.kegare.frozenland.api.FrozenlandAPI;

public class RecipeUpgradeIceTool implements IRecipe
{
	@Override
	public boolean matches(InventoryCrafting crafting, World world)
	{
		if (!FrozenlandAPI.isIceTool(crafting.getStackInRowAndColumn(1, 1)))
		{
			return false;
		}

		int i = 0;

		for (int row = 0; row < 3; ++row)
		{
			for (int column = 0; column < 3; ++column)
			{
				if (row != 1 && column == 1 || row == 1 && column != 1)
				{
					ItemStack itemstack = crafting.getStackInRowAndColumn(row, column);

					if (itemstack != null && itemstack.getItem() != null &&
						(itemstack.getItem() == Item.getItemFromBlock(Blocks.ice) || itemstack.getItem() == Item.getItemFromBlock(Blocks.packed_ice)))
					{
						if (++i >= 4)
						{
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting crafting)
	{
		ItemStack result = crafting.getStackInRowAndColumn(1, 1).copy();
		int ice = 0;
		int packed = 0;

		for (int row = 0; row < 3; ++row)
		{
			for (int column = 0; column < 3; ++column)
			{
				ItemStack itemstack = crafting.getStackInRowAndColumn(row, column);

				if (itemstack != null && itemstack.getItem() != null)
				{
					if (itemstack.getItem() == Item.getItemFromBlock(Blocks.ice))
					{
						++ice;
					}
					else if (itemstack.getItem() == Item.getItemFromBlock(Blocks.packed_ice))
					{
						++packed;
					}
				}
			}
		}

		if (result.isItemStackDamageable() && result.isItemDamaged())
		{
			result.setItemDamage(0);
		}
		else
		{
			FrozenlandAPI.addIceToolGrade(result, ice + packed * 9);
		}

		return result;
	}

	@Override
	public int getRecipeSize()
	{
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return null;
	}
}