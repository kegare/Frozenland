/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.kegare.frozenland.api.FrozenlandAPI;
import com.kegare.frozenland.core.Config;
import com.kegare.frozenland.recipe.RecipeUpgradeIceTool;

import cpw.mods.fml.common.registry.GameRegistry;

public class FrozenItems
{
	public static final ToolMaterial ICE = EnumHelper.addToolMaterial("ice", 1, 120, 5.0F, 1.0F, 10);

	public static final ItemDimensionalBook frozenland_dimensional_book = new ItemDimensionalBook("frozenlandDimensionalBook", "frozenland_dimensional_book", FrozenlandAPI.getDimension());
	public static final ItemIceStick ice_stick = new ItemIceStick("stickIce");
	public static final ItemIceSword ice_sword = new ItemIceSword("swordIce");
	public static final ItemIcePickaxe ice_pickaxe = new ItemIcePickaxe("pickaxeIce");
	public static final ItemIceAxe ice_axe = new ItemIceAxe("axeIce");
	public static final ItemIceSpade ice_shovel = new ItemIceSpade("shovelIce");
	public static final ItemIceHoe ice_hoe = new ItemIceHoe("hoeIce");

	public static void registerItems()
	{
		if (Config.frozenlandDimensionalBook)
		{
			GameRegistry.registerItem(frozenland_dimensional_book, "frozenland_dimensional_book");

			GameRegistry.addShapedRecipe(new ItemStack(frozenland_dimensional_book),
				" I ", "IEI", " B ",
				'I', Blocks.ice,
				'E', Items.ender_pearl,
				'B', Items.book
			);

			OreDictionary.registerOre("dimensionalBook", frozenland_dimensional_book);
			OreDictionary.registerOre("frozenlandDimensionalBook", frozenland_dimensional_book);
			OreDictionary.registerOre("dimensionalBookFrozenland", frozenland_dimensional_book);
		}

		if (Config.stickIce)
		{
			GameRegistry.registerItem(ice_stick, "ice_stick");

			GameRegistry.addShapedRecipe(new ItemStack(ice_stick, 2),
				"I", "I",
				'I', Blocks.ice
			);

			OreDictionary.registerOre("stickIce", ice_stick);
			OreDictionary.registerOre("iceStick", ice_stick);
		}

		if (Config.swordIce)
		{
			GameRegistry.registerItem(ice_sword, "ice_sword");

			GameRegistry.addRecipe(new ShapedOreRecipe(ice_sword,
				"I", "I", "S",
				'I', Blocks.ice,
				'S', "stickIce"
			));
			GameRegistry.addRecipe(new ShapedOreRecipe(ice_sword,
				"I", "I", "S",
				'I', Blocks.ice,
				'S', "iceStick"
			));

			OreDictionary.registerOre("swordIce", ice_sword);
			OreDictionary.registerOre("iceSword", ice_sword);
		}

		if (Config.pickaxeIce)
		{
			GameRegistry.registerItem(ice_pickaxe, "ice_pickaxe");

			GameRegistry.addRecipe(new ShapedOreRecipe(ice_pickaxe,
				"III", " S ", " S ",
				'I', Blocks.ice,
				'S', "stickIce"
			));
			GameRegistry.addRecipe(new ShapedOreRecipe(ice_pickaxe,
				"III", " S ", " S ",
				'I', Blocks.ice,
				'S', "iceStick"
			));

			OreDictionary.registerOre("pickaxeIce", ice_pickaxe);
			OreDictionary.registerOre("icePickaxe", ice_pickaxe);
		}

		if (Config.axeIce)
		{
			GameRegistry.registerItem(ice_axe, "ice_axe");

			GameRegistry.addRecipe(new ShapedOreRecipe(ice_axe,
				"II", "IS", " S",
				'I', Blocks.ice,
				'S', "stickIce"
			));
			GameRegistry.addRecipe(new ShapedOreRecipe(ice_axe,
				"II", "IS", " S",
				'I', Blocks.ice,
				'S', "iceStick"
			));

			OreDictionary.registerOre("axeIce", ice_axe);
			OreDictionary.registerOre("iceAxe", ice_axe);
		}

		if (Config.shovelIce)
		{
			GameRegistry.registerItem(ice_shovel, "ice_shovel");

			GameRegistry.addRecipe(new ShapedOreRecipe(ice_shovel,
				"I", "S", "S",
				'I', Blocks.ice,
				'S', "stickIce"
			));
			GameRegistry.addRecipe(new ShapedOreRecipe(ice_shovel,
				"I", "S", "S",
				'I', Blocks.ice,
				'S', "iceStick"
			));

			OreDictionary.registerOre("shovelIce", ice_shovel);
			OreDictionary.registerOre("iceShovel", ice_shovel);
		}

		if (Config.hoeIce)
		{
			GameRegistry.registerItem(ice_hoe, "ice_hoe");

			GameRegistry.addRecipe(new ShapedOreRecipe(ice_hoe,
				"II", " S", " S",
				'I', Blocks.ice,
				'S', "stickIce"
			));
			GameRegistry.addRecipe(new ShapedOreRecipe(ice_hoe,
				"II", " S", " S",
				'I', Blocks.ice,
				'S', "iceStick"
			));

			OreDictionary.registerOre("hoeIce", ice_hoe);
			OreDictionary.registerOre("iceHoe", ice_hoe);
		}

		GameRegistry.addRecipe(new RecipeUpgradeIceTool());

		if (Config.packedIceCraftRecipe)
		{
			GameRegistry.addShapedRecipe(new ItemStack(Blocks.packed_ice),
				"III", "III", "III",
				'I', Blocks.ice
			);
		}
	}
}