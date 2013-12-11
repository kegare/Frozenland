package kegare.frozenland.proxy;

import kegare.frozenland.item.FrozenItem;
import kegare.frozenland.renderer.RenderItemBowIce;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers()
	{
		if (FrozenItem.bowIce.isPresent())
		{
			MinecraftForgeClient.registerItemRenderer(FrozenItem.bowIce.get().itemID, new RenderItemBowIce());
		}
	}

	@Override
	public MinecraftServer getServer()
	{
		return FMLClientHandler.instance().getServer();
	}
}