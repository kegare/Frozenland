/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.core;

import io.netty.buffer.ByteBuf;

import java.io.File;
import java.util.List;

import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;
import com.kegare.frozenland.util.FrozenLog;
import com.kegare.frozenland.world.WorldProviderFrozenland;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class Config implements IMessage, IMessageHandler<Config, IMessage>
{
	public static Configuration config;

	public static boolean versionNotify;

	public static boolean packedIceSlab;
	public static boolean stairsPackedIce;
	public static boolean slipperyIce;
	public static boolean slipperyIceSlab;
	public static boolean stairsSlipperyIce;

	public static boolean frozenlandDimensionalBook;
	public static boolean stickIce;
	public static boolean swordIce;
	public static boolean pickaxeIce;
	public static boolean axeIce;
	public static boolean shovelIce;
	public static boolean hoeIce;
	public static boolean bowIce;
	public static boolean iceball;

	public static boolean packedIceCraftRecipe;

	public static int dimensionFrozenland;
	public static int biomeFrozenland;
	public static boolean generateCaves;
	public static boolean generateRavine;
	public static boolean generateMineshaft;
	public static boolean generateVillage;
	public static boolean generateDungeons;

	public static void refreshDimension(int dim)
	{
		int old = dimensionFrozenland;
		dimensionFrozenland = dim;

		if (old != 0 && old != dim && DimensionManager.isDimensionRegistered(old))
		{
			DimensionManager.unregisterProviderType(old);
			DimensionManager.unregisterDimension(old);

			FrozenLog.fine("Unregister the dimension (" + old + ")");
		}

		if (old != dim)
		{
			if (old != 0 && DimensionManager.isDimensionRegistered(dim))
			{
				dim = old;
			}

			if (DimensionManager.registerProviderType(dim, WorldProviderFrozenland.class, true))
			{
				DimensionManager.registerDimension(dim, dim);

				FrozenLog.fine("Register the Frozenland dimension (" + dim + ")");
			}
		}
	}

	public static void syncConfig()
	{
		if (config == null)
		{
			File file = new File(Loader.instance().getConfigDir(), "Frozenland.cfg");
			config = new Configuration(file);

			try
			{
				config.load();
			}
			catch (Exception e)
			{
				File dest = new File(file.getParentFile(), file.getName() + ".bak");

				if (dest.exists())
				{
					dest.delete();
				}

				file.renameTo(dest);

				FrozenLog.log(Level.ERROR, e, "A critical error occured reading the " + file.getName() + " file, defaults will be used - the invalid file is backed up at " + dest.getName());
			}
		}

		String category = Configuration.CATEGORY_GENERAL;
		Property prop;
		List<String> propOrder = Lists.newArrayList();

		prop = config.get(category, "versionNotify", true);
		prop.setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		versionNotify = prop.getBoolean(versionNotify);

		config.setCategoryPropertyOrder(category, propOrder);

		category = "blocks";
		prop = config.get(category, "packedIceSlab", true);
		prop.setRequiresMcRestart(true).setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		packedIceSlab = prop.getBoolean(packedIceSlab);
		prop = config.get(category, "stairsPackedIce", true);
		prop.setRequiresMcRestart(true).setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		stairsPackedIce = prop.getBoolean(stairsPackedIce);
		prop = config.get(category, "slipperyIce", true);
		prop.setRequiresMcRestart(true).setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		slipperyIce = prop.getBoolean(slipperyIce);
		prop = config.get(category, "slipperyIceSlab", true);
		prop.setRequiresMcRestart(true).setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		slipperyIceSlab = prop.getBoolean(slipperyIceSlab);
		prop = config.get(category, "stairsSlipperyIce", true);
		prop.setRequiresMcRestart(true).setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		stairsSlipperyIce = prop.getBoolean(stairsSlipperyIce);

		config.setCategoryRequiresMcRestart(category, true);
		config.setCategoryPropertyOrder(category, propOrder);

		category = "items";
		prop = config.get(category, "frozenlandDimensionalBook", true);
		prop.setRequiresMcRestart(true).setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		frozenlandDimensionalBook = prop.getBoolean(frozenlandDimensionalBook);
		prop = config.get(category, "stickIce", true);
		prop.setRequiresMcRestart(true).setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		stickIce = prop.getBoolean(stickIce);
		prop = config.get(category, "swordIce", true);
		prop.setRequiresMcRestart(true).setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		swordIce = prop.getBoolean(swordIce);
		prop = config.get(category, "pickaxeIce", true);
		prop.setRequiresMcRestart(true).setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		pickaxeIce = prop.getBoolean(pickaxeIce);
		prop = config.get(category, "axeIce", true);
		prop.setRequiresMcRestart(true).setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		axeIce = prop.getBoolean(axeIce);
		prop = config.get(category, "shovelIce", true);
		prop.setRequiresMcRestart(true).setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		shovelIce = prop.getBoolean(shovelIce);
		prop = config.get(category, "hoeIce", true);
		prop.setRequiresMcRestart(true).setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		hoeIce = prop.getBoolean(hoeIce);
		prop = config.get(category, "bowIce", true);
		prop.setRequiresMcRestart(true).setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		bowIce = prop.getBoolean(bowIce);
		prop = config.get(category, "iceball", true);
		prop.setRequiresMcRestart(true).setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		iceball = prop.getBoolean(iceball);

		config.setCategoryRequiresMcRestart(category, true);
		config.setCategoryPropertyOrder(category, propOrder);

		category = "recipes";
		prop = config.get(category, "packedIceCraftRecipe", true);
		prop.setRequiresMcRestart(true).setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		packedIceCraftRecipe = prop.getBoolean(packedIceCraftRecipe);

		config.setCategoryRequiresMcRestart(category, true);
		config.setCategoryPropertyOrder(category, propOrder);

		category = "frozenland";
		prop = config.get(category, "dimensionFrozenland", -8);
		prop.setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [range: " + prop.getMinValue() + " ~ " + prop.getMaxValue() + ", default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		refreshDimension(MathHelper.clamp_int(prop.getInt(dimensionFrozenland), Integer.parseInt(prop.getMinValue()), Integer.parseInt(prop.getMaxValue())));
		prop = config.get(category, "biomeFrozenland", 85);
		prop.setMinValue(0).setMaxValue(255).setRequiresMcRestart(true).setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [range: " + prop.getMinValue() + " ~ " + prop.getMaxValue() + ", default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		biomeFrozenland = MathHelper.clamp_int(prop.getInt(biomeFrozenland), Integer.parseInt(prop.getMinValue()), Integer.parseInt(prop.getMaxValue()));
		prop = config.get(category, "generateCaves", true);
		prop.setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		generateCaves = prop.getBoolean(generateCaves);
		prop = config.get(category, "generateRavine", true);
		prop.setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		generateRavine = prop.getBoolean(generateRavine);
		prop = config.get(category, "generateMineshaft", true);
		prop.setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		generateMineshaft = prop.getBoolean(generateMineshaft);
		prop = config.get(category, "generateVillage", true);
		prop.setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		generateVillage = prop.getBoolean(generateVillage);
		prop = config.get(category, "generateDungeons", true);
		prop.setLanguageKey(Frozenland.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		generateDungeons = prop.getBoolean(generateDungeons);

		config.setCategoryPropertyOrder(category, propOrder);

		if (config.hasChanged())
		{
			config.save();
		}
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		dimensionFrozenland = buf.readInt();
		biomeFrozenland = buf.readInt();
		generateCaves = buf.readBoolean();
		generateRavine = buf.readBoolean();
		generateMineshaft = buf.readBoolean();
		generateVillage = buf.readBoolean();
		generateDungeons = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(dimensionFrozenland);
		buf.writeInt(biomeFrozenland);
		buf.writeBoolean(generateCaves);
		buf.writeBoolean(generateRavine);
		buf.writeBoolean(generateMineshaft);
		buf.writeBoolean(generateVillage);
		buf.writeBoolean(generateDungeons);
	}

	@Override
	public IMessage onMessage(Config message, MessageContext ctx)
	{
		refreshDimension(dimensionFrozenland);

		return null;
	}
}