/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.plugin.thaumcraft;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import com.kegare.frozenland.item.FrozenItems;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional.Method;

public class ThaumcraftPlugin
{
	public static final String MODID = "Thaumcraft";

	public static boolean enabled()
	{
		return Loader.isModLoaded(MODID);
	}

	@Method(modid = MODID)
	public static void invoke()
	{
		ThaumcraftApi.registerObjectTag(new ItemStack(FrozenItems.frozenland_dimensional_book, 0, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.TRAVEL, 3).add(Aspect.COLD, 6));
		ThaumcraftApi.registerObjectTag(new ItemStack(FrozenItems.ice_stick, 0, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.COLD, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(FrozenItems.ice_sword, 0, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.COLD, 2).add(Aspect.WEAPON, 1).add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(FrozenItems.ice_pickaxe, 0, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.COLD, 3).add(Aspect.MINE, 1).add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(FrozenItems.ice_axe, 0, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.COLD, 3).add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(FrozenItems.ice_shovel, 0, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.COLD, 1).add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(FrozenItems.ice_hoe, 0, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.COLD, 2).add(Aspect.HARVEST, 1).add(Aspect.TOOL, 1));
	}
}