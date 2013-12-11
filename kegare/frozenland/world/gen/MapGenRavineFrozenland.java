package kegare.frozenland.world.gen;

import net.minecraft.block.Block;
import net.minecraft.world.gen.MapGenRavine;

public class MapGenRavineFrozenland extends MapGenRavine
{
	@Override
	protected void digBlock(byte[] data, int index, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop)
	{
		int block = data[index];

		if (block == Block.stone.blockID || block == Block.ice.blockID)
		{
			if (y < 10)
			{
				data[index] = (byte)Block.lavaMoving.blockID;
			}
			else
			{
				data[index] = 0;

				if (foundTop && data[index - 1] == Block.ice.blockID)
				{
					data[index - 1] = (byte)Block.ice.blockID;
				}
			}
		}
	}
}