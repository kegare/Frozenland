package kegare.frozenland.handler;

import kegare.frozenland.core.Config;
import kegare.frozenland.world.WorldProviderFrozenland;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class FrozenPacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		if ("frozenland.sync".equals(packet.channel))
		{
			ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
			Config.dimensionFrozenland = dat.readInt();
			Config.generateCaves = dat.readBoolean();
			Config.generateRavine = dat.readBoolean();
			Config.generateMineshaft = dat.readBoolean();
			Config.generateVillage = dat.readBoolean();
			Config.generateLakes = dat.readBoolean();
			Config.generateDungeons = dat.readBoolean();
			WorldProviderFrozenland.dimensionSeed = dat.readLong();
		}
	}

	public static Packet250CustomPayload getPacketDataSync()
	{
		ByteArrayDataOutput dat = ByteStreams.newDataOutput();
		dat.writeInt(Config.dimensionFrozenland);
		dat.writeBoolean(Config.generateCaves);
		dat.writeBoolean(Config.generateRavine);
		dat.writeBoolean(Config.generateMineshaft);
		dat.writeBoolean(Config.generateVillage);
		dat.writeBoolean(Config.generateLakes);
		dat.writeBoolean(Config.generateDungeons);
		dat.writeLong(WorldProviderFrozenland.dimensionSeed);

		return new Packet250CustomPayload("frozenland.sync", dat.toByteArray());
	}
}