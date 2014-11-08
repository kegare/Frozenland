/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.network;

import io.netty.buffer.ByteBuf;

import com.kegare.frozenland.core.Config;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class DimSyncMessage implements IMessage, IMessageHandler<DimSyncMessage, IMessage>
{
	private int dimensionId;
	private int biomeId;

	public DimSyncMessage() {}

	public DimSyncMessage(int dim, int biome)
	{
		this.dimensionId = dim;
		this.biomeId = biome;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		dimensionId = buffer.readInt();
		biomeId = buffer.readInt();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(dimensionId);
		buffer.writeInt(biomeId);
	}

	@Override
	public IMessage onMessage(DimSyncMessage message, MessageContext ctx)
	{
		Config.dimensionFrozenland = message.dimensionId;
		Config.biomeFrozenland = message.biomeId;

		return null;
	}
}
