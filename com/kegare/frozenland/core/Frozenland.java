/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.core;

import static com.kegare.frozenland.core.Frozenland.*;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Level;

import com.kegare.frozenland.api.FrozenlandAPI;
import com.kegare.frozenland.handler.FrozenEventHooks;
import com.kegare.frozenland.handler.FrozenlandAPIHandler;
import com.kegare.frozenland.item.FrozenItems;
import com.kegare.frozenland.network.DimSyncMessage;
import com.kegare.frozenland.plugin.sextiarysector.SextiarySectorPlugin;
import com.kegare.frozenland.plugin.thaumcraft.ThaumcraftPlugin;
import com.kegare.frozenland.util.FrozenLog;
import com.kegare.frozenland.util.Version;
import com.kegare.frozenland.world.BiomeGenFrozenland;
import com.kegare.frozenland.world.WorldProviderFrozenland;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@Mod
(
	modid = MODID,
	acceptedMinecraftVersions = "[1.7.10,)",
	guiFactory = MOD_PACKAGE + ".client.config.FrozenGuiFactory"
)
public class Frozenland
{
	public static final String
	MODID = "kegare.frozenland",
	MOD_PACKAGE = "com.kegare.frozenland",
	CONFIG_LANG = "frozenland.config.";

	@Metadata(MODID)
	public static ModMetadata metadata;

	public static final SimpleNetworkWrapper network = new SimpleNetworkWrapper(MODID);

	@EventHandler
	public void construct(FMLConstructionEvent event)
	{
		FrozenlandAPI.instance = new FrozenlandAPIHandler();

		Version.versionCheck();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Config.syncConfig();

		FrozenItems.registerItems();

		BiomeGenFrozenland.frozenland = new BiomeGenFrozenland(Config.biomeFrozenland, true);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		int id = 0;

		network.registerMessage(DimSyncMessage.class, DimSyncMessage.class, id++, Side.CLIENT);

		id = Config.dimensionFrozenland;
		DimensionManager.registerProviderType(id, WorldProviderFrozenland.class, true);
		DimensionManager.registerDimension(id, id);

		FMLCommonHandler.instance().bus().register(FrozenEventHooks.instance);

		MinecraftForge.EVENT_BUS.register(FrozenEventHooks.instance);
		MinecraftForge.TERRAIN_GEN_BUS.register(FrozenEventHooks.instance);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		try
		{
			if (ThaumcraftPlugin.enabled())
			{
				ThaumcraftPlugin.invoke();
			}
		}
		catch (Throwable e)
		{
			FrozenLog.log(Level.WARN, e, "Failed to trying invoke plugin: ThaumcraftPlugin");
		}

		try
		{
			if (SextiarySectorPlugin.enabled())
			{
				SextiarySectorPlugin.invoke();
			}
		}
		catch (Throwable e)
		{
			FrozenLog.log(Level.WARN, e, "Failed to trying invoke plugin: SextiarySectorPlugin");
		}
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandFrozenland());

		if (event.getSide().isServer() && (Version.DEV_DEBUG || Config.versionNotify && Version.isOutdated()))
		{
			event.getServer().logInfo(String.format(StatCollector.translateToLocal("frozenland.version.message"), "Frozenland") + ": " + Version.getLatest());
		}
	}
}