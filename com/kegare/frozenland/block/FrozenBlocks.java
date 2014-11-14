/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.block;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.kegare.frozenland.core.Config;
import com.kegare.frozenland.item.ItemSlabPackedIce;
import com.kegare.frozenland.item.ItemSlabSlipperyIce;

import cpw.mods.fml.common.registry.GameRegistry;

public class FrozenBlocks
{
	public static final BlockSlabPackedIce packed_ice_slab = new BlockSlabPackedIce("packedIceSlab");
	public static final BlockStairsPackedIce packed_ice_stairs = new BlockStairsPackedIce("stairsPackedIce");
	public static final BlockSlipperyIce slippery_ice = new BlockSlipperyIce("slipperyIce");
	public static final BlockSlabSlipperyIce slippery_ice_slab = new BlockSlabSlipperyIce("slipperyIceSlab");
	public static final BlockStairsSlipperyIce slippery_ice_stairs = new BlockStairsSlipperyIce("stairsSlipperyIce");

	public static void registerBlocks()
	{
		if (Config.packedIceSlab)
		{
			GameRegistry.registerBlock(packed_ice_slab, ItemSlabPackedIce.class, "packed_ice_slab");

			GameRegistry.addShapedRecipe(new ItemStack(packed_ice_slab, 6),
				"III",
				'I', Blocks.packed_ice
			);

			OreDictionary.registerOre("slabPackedIce", packed_ice_slab);
			OreDictionary.registerOre("packedIceSlab", packed_ice_slab);
		}

		if (Config.stairsPackedIce)
		{
			GameRegistry.registerBlock(packed_ice_stairs, "packed_ice_stairs");

			GameRegistry.addShapedRecipe(new ItemStack(packed_ice_stairs, 4),
				"  I", " II", "III",
				'I', Blocks.packed_ice
			);

			OreDictionary.registerOre("stairPackedIce", packed_ice_stairs);
			OreDictionary.registerOre("stairsPackedIce", packed_ice_stairs);
			OreDictionary.registerOre("packedIceStair", packed_ice_stairs);
			OreDictionary.registerOre("packedIceStairs", packed_ice_stairs);
		}

		if (Config.slipperyIce)
		{
			GameRegistry.registerBlock(slippery_ice, "slippery_ice");

			GameRegistry.addShapedRecipe(new ItemStack(slippery_ice),
				"III", "III", "III",
				'I', Blocks.packed_ice
			);

			OreDictionary.registerOre("slipperyIce", slippery_ice);
			OreDictionary.registerOre("iceSlippery", slippery_ice);
			OreDictionary.registerOre("ice", slippery_ice);
		}

		if (Config.slipperyIceSlab)
		{
			GameRegistry.registerBlock(slippery_ice_slab, ItemSlabSlipperyIce.class, "slippery_ice_slab");

			GameRegistry.addShapedRecipe(new ItemStack(slippery_ice_slab, 6),
				"III",
				'I', slippery_ice
			);

			OreDictionary.registerOre("slabSlipperyIce", slippery_ice_slab);
			OreDictionary.registerOre("slipperyIceSlab", slippery_ice_slab);
		}

		if (Config.stairsSlipperyIce)
		{
			GameRegistry.registerBlock(slippery_ice_stairs, "slippery_ice_stairs");

			GameRegistry.addShapedRecipe(new ItemStack(slippery_ice_stairs, 4),
				"  I", " II", "III",
				'I', slippery_ice
			);

			OreDictionary.registerOre("stairSlipperyIce", slippery_ice_stairs);
			OreDictionary.registerOre("stairsSlipperyIce", slippery_ice_stairs);
			OreDictionary.registerOre("slipperyIceStair", slippery_ice_stairs);
			OreDictionary.registerOre("slipperyIceStairs", slippery_ice_stairs);
		}

		if (Config.packedIceCraftRecipe)
		{
			GameRegistry.addShapedRecipe(new ItemStack(Blocks.packed_ice),
				"III", "III", "III",
				'I', Blocks.ice
			);
		}
	}
}