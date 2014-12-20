/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.client;

import net.minecraftforge.client.MinecraftForgeClient;

import com.kegare.frozenland.client.renderer.ItemIceBowRenderer;
import com.kegare.frozenland.client.renderer.RenderIceball;
import com.kegare.frozenland.core.CommonProxy;
import com.kegare.frozenland.entity.EntityIceball;
import com.kegare.frozenland.item.FrozenItems;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityIceball.class, new RenderIceball());

		MinecraftForgeClient.registerItemRenderer(FrozenItems.ice_bow, new ItemIceBowRenderer());
	}
}