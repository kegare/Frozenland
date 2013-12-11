package kegare.frozenland.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemIceStick extends ItemFood
{
	public ItemIceStick(int itemID, String name)
	{
		super(itemID - 256, 1, 0.05F, false);
		this.setUnlocalizedName(name);
		this.setTextureName("frozenland:ice_stick");
		this.setFull3D();
		this.setAlwaysEdible();
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}
}