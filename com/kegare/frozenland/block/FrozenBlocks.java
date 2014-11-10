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

import cpw.mods.fml.common.registry.GameRegistry;

public class FrozenBlocks
{
	public static final BlockSlabPackedIce packed_ice_slab = new BlockSlabPackedIce("packedIceSlab");
	public static final BlockStairsPackedIce packed_ice_stairs = new BlockStairsPackedIce("stairsPackedIce");

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

		if (Config.packedIceCraftRecipe)
		{
			GameRegistry.addShapedRecipe(new ItemStack(Blocks.packed_ice),
				"III", "III", "III",
				'I', Blocks.ice
			);
		}
	}
}