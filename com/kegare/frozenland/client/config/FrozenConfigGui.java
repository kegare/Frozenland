/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.client.config;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import com.google.common.collect.Lists;
import com.kegare.frozenland.core.Config;
import com.kegare.frozenland.core.Frozenland;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FrozenConfigGui extends GuiConfig
{
	public FrozenConfigGui(GuiScreen parent)
	{
		super(parent, getConfigElements(), Frozenland.MODID, false, false, I18n.format(Frozenland.CONFIG_LANG + "title"));
	}

	private static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = Lists.newArrayList();

		list.addAll(new ConfigElement(Config.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
		list.addAll(new ConfigElement(Config.config.getCategory("blocks")).getChildElements());
		list.addAll(new ConfigElement(Config.config.getCategory("items")).getChildElements());
		list.addAll(new ConfigElement(Config.config.getCategory("recipes")).getChildElements());
		list.addAll(new ConfigElement(Config.config.getCategory("frozenland")).getChildElements());

		return list;
	}
}
