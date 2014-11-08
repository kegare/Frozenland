/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.plugin.sextiarysector;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional.Method;

public class SextiarySectorPlugin
{
	public static final String MODID = "SextiarySector";

	public static boolean enabled()
	{
		return Loader.isModLoaded(MODID);
	}

	@Method(modid = MODID)
	public static void invoke() {}
}