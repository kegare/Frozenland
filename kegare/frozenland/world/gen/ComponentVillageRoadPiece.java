package kegare.frozenland.world.gen;

public abstract class ComponentVillageRoadPiece extends ComponentVillage
{
	public ComponentVillageRoadPiece() {}

	protected ComponentVillageRoadPiece(ComponentVillageStartPiece villageStartPiece, int type)
	{
		super(villageStartPiece, type);
	}
}