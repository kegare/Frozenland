/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.recipe;

import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import com.kegare.frozenland.api.FrozenlandAPI;
import com.kegare.frozenland.block.BlockSlipperyIce;

public class RecipeUpgradeIceTool implements IRecipe
{
	@Override
	public boolean matches(InventoryCrafting crafting, World world)
	{
		if (!FrozenlandAPI.isIceTool(crafting.getStackInRowAndColumn(1, 1)))
		{
			return false;
		}

		int ice = 0;

		for (int row = 0; row < 3; ++row)
		{
			for (int column = 0; column < 3; ++column)
			{
				if (row == 1 && column == 1)
				{
					continue;
				}

				ItemStack itemstack = crafting.getStackInRowAndColumn(row, column);

				if (itemstack != null && itemstack.getItem() != null)
				{
					Block block = Block.getBlockFromItem(itemstack.getItem());

					if (block instanceof BlockIce || block instanceof BlockPackedIce || block instanceof BlockSlipperyIce)
					{
						if (row != 1 && column == 1 || row == 1 && column != 1)
						{
							++ice;
						}
					}
					else if (row != 1 && column != 1)
					{
						return false;
					}
				}
			}
		}

		return ice >= 4;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting crafting)
	{
		ItemStack result = crafting.getStackInRowAndColumn(1, 1).copy();
		int ice = 0;
		int packed = 0;
		int slippery = 0;

		for (int row = 0; row < 3; ++row)
		{
			for (int column = 0; column < 3; ++column)
			{
				if (row == 1 && column == 1)
				{
					continue;
				}

				ItemStack itemstack = crafting.getStackInRowAndColumn(row, column);

				if (itemstack != null && itemstack.getItem() != null)
				{
					Block block = Block.getBlockFromItem(itemstack.getItem());

					if (block instanceof BlockSlipperyIce)
					{
						++slippery;
					}
					else if (block instanceof BlockPackedIce)
					{
						++packed;
					}
					else if (block instanceof BlockIce)
					{
						++ice;
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
			FrozenlandAPI.addIceToolGrade(result, ice + packed * 9 + slippery * 9 * 9);
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