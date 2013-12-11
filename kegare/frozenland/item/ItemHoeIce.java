package kegare.frozenland.item;

import java.util.Random;

import kegare.frozenland.core.Frozenland;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.UseHoeEvent;

public class ItemHoeIce extends ItemHoe
{
	public ItemHoeIce(int itemID, String name)
	{
		super(itemID - 256, FrozenItem.toolIce);
		this.setUnlocalizedName(name);
		this.setTextureName("frozenland:ice_hoe");
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (player.canPlayerEdit(x, y, z, side, itemstack))
		{
			UseHoeEvent event = new UseHoeEvent(player, itemstack, world, x, y, z);

			if (MinecraftForge.EVENT_BUS.post(event))
			{
				return false;
			}

			Random random = new Random();
			BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
			boolean inFrozen = BiomeDictionary.isBiomeOfType(biome, Type.FROZEN);

			if (event.getResult() == Result.ALLOW)
			{
				if (itemstack.isItemDamaged() && inFrozen ? random.nextInt(4) > 0 : true)
				{
					itemstack.damageItem(1, player);
				}

				return true;
			}

			int blockID = world.getBlockId(x, y, z);

			if (side != 0 && world.isAirBlock(x, y + 1, z) && (blockID == Block.grass.blockID || blockID == Block.dirt.blockID))
			{
				Block block = Block.tilledField;
				world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);

				if (world.isRemote)
				{
					return true;
				}
				else
				{
					world.setBlock(x, y, z, block.blockID);

					if (itemstack.isItemDamaged() && inFrozen ? random.nextInt(4) > 0 : true)
					{
						itemstack.damageItem(1, player);
					}

					return true;
				}
			}
		}

		return false;
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