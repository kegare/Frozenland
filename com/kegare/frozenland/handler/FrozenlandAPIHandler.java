/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.handler;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

import com.kegare.frozenland.api.IFrozenlandAPI;
import com.kegare.frozenland.api.IItemIceTool;
import com.kegare.frozenland.core.Config;
import com.kegare.frozenland.util.Version;
import com.kegare.frozenland.world.BiomeGenFrozenland;

public class FrozenlandAPIHandler implements IFrozenlandAPI
{
	@Override
	public String getVersion()
	{
		return Version.getCurrent();
	}

	@Override
	public int getDimension()
	{
		return Config.dimensionFrozenland;
	}

	@Override
	public boolean isEntityInFrozenland(Entity entity)
	{
		return entity != null && entity.dimension == getDimension();
	}

	@Override
	public BiomeGenBase getBiome()
	{
		if (BiomeGenFrozenland.frozenland == null)
		{
			BiomeGenBase biome = BiomeGenBase.getBiome(Config.biomeFrozenland);

			if (biome == null || !(biome instanceof BiomeGenFrozenland))
			{
				biome = BiomeGenBase.iceMountains;
			}

			BiomeGenFrozenland.frozenland = biome;
		}

		return BiomeGenFrozenland.frozenland;
	}

	@Override
	public boolean isIceTool(Item item)
	{
		return item != null && item instanceof IItemIceTool;
	}

	@Override
	public boolean isIceTool(ItemStack itemstack)
	{
		return itemstack != null && isIceTool(itemstack.getItem());
	}

	@Override
	public int getIceToolGrade(ItemStack itemstack)
	{
		return isIceTool(itemstack) ? ((IItemIceTool)itemstack.getItem()).getGrade(itemstack) : 0;
	}

	@Override
	public void setIceToolGrade(ItemStack itemstack, int grade)
	{
		if (isIceTool(itemstack))
		{
			((IItemIceTool)itemstack.getItem()).setGrade(itemstack, grade);
		}
	}

	@Override
	public void addIceToolGrade(ItemStack itemstack, int amount)
	{
		if (isIceTool(itemstack))
		{
			((IItemIceTool)itemstack.getItem()).addGrade(itemstack, amount);
		}
	}
}