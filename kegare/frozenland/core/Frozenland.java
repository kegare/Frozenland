package kegare.frozenland.core;

import kegare.frozenland.handler.FrozenConnectionHandler;
import kegare.frozenland.handler.FrozenEventHooks;
import kegare.frozenland.handler.FrozenPacketHandler;
import kegare.frozenland.item.FrozenItem;
import kegare.frozenland.proxy.CommonProxy;
import kegare.frozenland.util.Version;
import kegare.frozenland.world.WorldProviderFrozenland;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod
(
	modid = "kegare.frozenland"
)
@NetworkMod
(
	clientSideRequired = true,
	serverSideRequired = false,
	channels = {"frozenland.sync"},
	packetHandler = FrozenPacketHandler.class,
	connectionHandler = FrozenConnectionHandler.class
)
public class Frozenland
{
	@Metadata("kegare.frozenland")
	public static ModMetadata metadata;

	@SidedProxy(modId = "kegare.frozenland", clientSide = "kegare.frozenland.proxy.ClientProxy", serverSide = "kegare.frozenland.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Version.versionCheck();
		Config.buildConfiguration();

		FrozenItem.configure();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		DimensionManager.registerProviderType(Config.dimensionFrozenland, WorldProviderFrozenland.class, true);
		DimensionManager.registerDimension(Config.dimensionFrozenland, Config.dimensionFrozenland);

		MinecraftForge.EVENT_BUS.register(new FrozenEventHooks());

		proxy.registerRenderers();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandFrozenland());

		if (event.getSide().isServer() && Config.versionNotify && Version.isOutdated())
		{
			event.getServer().logInfo("A new Frozenland version is available : " + Version.LATEST);
		}
	}
}