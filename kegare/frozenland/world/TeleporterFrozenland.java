package kegare.frozenland.world;

import kegare.frozenland.core.Config;
import kegare.frozenland.item.FrozenItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterFrozenland extends Teleporter
{
	private final WorldServer worldObj;

	private ItemStack itemstack;

	public TeleporterFrozenland(WorldServer worldServer)
	{
		super(worldServer);
		this.worldObj = worldServer;
	}

	public TeleporterFrozenland(WorldServer worldServer, ItemStack itemstack)
	{
		this(worldServer);
		this.itemstack = itemstack;
	}

	@Override
	public void placeInPortal(Entity entity, double posX, double posY, double posZ, float rotationYaw)
	{
		int dim = worldObj.provider.dimensionId;
		ChunkCoordinates spawn = worldObj.provider.getRandomizedSpawnPoint();
		int x = spawn.posX;
		int y = spawn.posY;
		int z = spawn.posZ;
		boolean flag = false;

		if (itemstack != null && FrozenItem.frozenlandDimensionalBook.isPresent() && itemstack.itemID == FrozenItem.frozenlandDimensionalBook.get().itemID)
		{
			NBTTagCompound nbt = itemstack.getTagCompound();

			if (nbt != null && nbt.hasKey("LastUsePos." + dim))
			{
				nbt = nbt.getCompoundTag("LastUsePos." + dim);
				x = MathHelper.floor_double(nbt.getDouble("PosX"));
				y = MathHelper.floor_double(nbt.getDouble("PosY"));
				z = MathHelper.floor_double(nbt.getDouble("PosZ"));
				flag = true;
			}
		}

		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			spawn = player.getBedLocation(dim);

			if (!flag && spawn != null)
			{
				x = spawn.posX;
				y = spawn.posY;
				z = spawn.posZ;
				flag = true;
			}
		}

		if (!flag && dim == Config.dimensionFrozenland)
		{
			ChunkPosition village = worldObj.findClosestStructure("Village", x, y, z);

			if (village != null)
			{
				x = village.x;
				z = village.z + 3;
				y = worldObj.getTopSolidOrLiquidBlock(x, z);

				if (y > 0)
				{
					flag = true;
				}
			}
		}

		entity.setLocationAndAngles((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, rotationYaw, 10.0F);
		entity.motionX = entity.motionY = entity.motionZ = 0.0D;

		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;

			if (flag && spawn == null)
			{
				player.setSpawnChunk(new ChunkCoordinates(x, y, z), true, dim);
			}

			player.addExperienceLevel(0);
			player.addPotionEffect(new PotionEffect(Potion.blindness.getId(), 20));

			if (!player.capabilities.isCreativeMode)
			{
				player.addExhaustion(0.1F);
			}
		}
	}

	@Override
	public boolean placeInExistingPortal(Entity entity, double posX, double posY, double posZ, float rotationYaw)
	{
		return false;
	}

	@Override
	public boolean makePortal(Entity entity)
	{
		return false;
	}

	@Override
	public void removeStalePortalLocations(long time) {}
}