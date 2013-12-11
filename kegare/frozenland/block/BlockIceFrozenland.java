package kegare.frozenland.block;

import java.util.Random;

import kegare.frozenland.core.Config;
import kegare.frozenland.item.ItemPickaxeIce;
import net.minecraft.block.BlockIce;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class BlockIceFrozenland extends BlockIce
{
	public BlockIceFrozenland(int blockID)
	{
		super(blockID);
		this.setUnlocalizedName("ice");
		this.setTextureName("ice");
		this.setHardness(0.5F);
		this.setLightOpacity(3);
		this.setStepSound(soundGlassFootstep);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int metadata)
	{
		Random random = new Random();
		ItemStack itemstack = player.getCurrentEquippedItem();

		if (player.dimension == Config.dimensionFrozenland && itemstack != null && !(itemstack.getItem() instanceof ItemPickaxeIce) && itemstack.getItem() instanceof ItemPickaxe)
		{
			player.addStat(StatList.mineBlockStatArray[blockID], 1);
			player.addExhaustion(0.025F);

			if (random.nextInt(10) == 0)
			{
				itemstack = createStackedBlock(metadata);

				if (itemstack != null)
				{
					dropBlockAsItem_do(world, x, y, z, itemstack);
				}
			}
		}
		else
		{
			super.harvestBlock(world, player, x, y, z, metadata);
		}
	}
}