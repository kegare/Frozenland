package kegare.frozenland.item;

import java.util.Random;

import kegare.frozenland.core.Frozenland;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class ItemSpadeIce extends ItemSpade
{
	public ItemSpadeIce(int itemID, String name)
	{
		super(itemID - 256, FrozenItem.toolIce);
		this.setUnlocalizedName(name);
		this.setTextureName("frozenland:ice_shovel");
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, int blockID, int x, int y, int z, EntityLivingBase entityLiving)
	{
		Block block = Block.blocksList[blockID];

		if (block != null && (double)block.getBlockHardness(world, x, y, z) != 0.0D)
		{
			Random random = new Random();
			BiomeGenBase biome = world.getBiomeGenForCoords(x, z);

			if (itemstack.isItemDamaged() && BiomeDictionary.isBiomeOfType(biome, Type.FROZEN) ? random.nextInt(4) > 0 : true)
			{
				itemstack.damageItem(1, entityLiving);
			}
		}

		return true;
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean current)
	{
		super.onUpdate(itemstack, world, entity, slot, current);

		if (!world.isRemote && entity instanceof EntityPlayer)
		{
			NBTTagCompound nbt = itemstack.getTagCompound();
			EntityPlayer player = (EntityPlayer)entity;

			if (nbt == null)
			{
				nbt = new NBTTagCompound();

				itemstack.setTagCompound(nbt);
			}

			nbt.setString("Owner", player.username);
		}
	}

	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player)
	{
		super.onCreated(itemstack, world, player);

		if (!world.isRemote)
		{
			NBTTagCompound nbt = itemstack.getTagCompound();

			if (nbt == null)
			{
				nbt = new NBTTagCompound();

				itemstack.setTagCompound(nbt);
			}

			nbt.setString("Owner", player.username);
		}
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entity)
	{
		World world = entity.worldObj;

		if (!world.isRemote && entity.onGround && entity.ticksExisted % 120 == 0)
		{
			ItemStack itemstack = entity.getEntityItem();
			NBTTagCompound nbt = itemstack.getTagCompound();
			int x = MathHelper.floor_double(entity.posX);
			int y = MathHelper.floor_double(entity.posY) - 1;
			int z = MathHelper.floor_double(entity.posZ);
			BiomeGenBase biome = world.getBiomeGenForCoords(x, z);

			if (itemstack.isItemDamaged() && nbt != null && biome.getFloatTemperature() < 1.0F && world.getBlockId(x, y, z) == Block.ice.blockID)
			{
				EntityPlayerMP player = Frozenland.proxy.getServer().getConfigurationManager().getPlayerForUsername(nbt.getString("Owner"));

				if (player != null)
				{
					itemstack.damageItem(entity.isInWater() ? 1 : -1, player);
				}
			}
		}

		return super.onEntityItemUpdate(entity);
	}
}