package kegare.frozenland.world.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentVillageTorch extends ComponentVillage
{
	public ComponentVillageTorch() {}

	protected ComponentVillageTorch(ComponentVillageStartPiece villageStartPiece, int type, Random random, StructureBoundingBox structureBoundingBox, int coordBaseMode)
	{
		super(villageStartPiece, type);
		this.coordBaseMode = coordBaseMode;
		this.boundingBox = structureBoundingBox;
	}

	protected static StructureBoundingBox findValidPlacement(ComponentVillageStartPiece villageStartPiece, List list, Random random, int x, int y, int z, int coordBaseMode)
	{
		StructureBoundingBox structureBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 3, 4, 2, coordBaseMode);

		return StructureComponent.findIntersecting(list, structureBoundingBox) != null ? null : structureBoundingBox;
	}

	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox)
	{
		if (averageGroundLevel < 0)
		{
			averageGroundLevel = getAverageGroundLevel(world, structureBoundingBox);

			if (averageGroundLevel < 0)
			{
				return true;
			}

			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 4 - 1, 0);
		}

		fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 2, 3, 1, 0, 0, false);
		placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 1, 0, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 1, 1, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.fence.blockID, 0, 1, 2, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.cloth.blockID, 15, 1, 3, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.torchWood.blockID, 0, 0, 3, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.torchWood.blockID, 0, 1, 3, 1, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.torchWood.blockID, 0, 2, 3, 0, structureBoundingBox);
		placeBlockAtCurrentPosition(world, Block.torchWood.blockID, 0, 1, 3, -1, structureBoundingBox);

		return true;
	}
}