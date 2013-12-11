package kegare.frozenland.item;

import java.util.List;

import kegare.frozenland.core.Config;
import kegare.frozenland.world.TeleporterFrozenland;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityOwnable;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFrozenlandDimensionalBook extends Item
{
	public ItemFrozenlandDimensionalBook(int itemID, String name)
	{
		super(itemID - 256);
		this.setUnlocalizedName(name);
		this.setTextureName("frozenland:frozenland_dimensional_book");
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
	{
		if (!world.isRemote && player.onGround)
		{
			NBTTagCompound nbt = itemstack.getTagCompound();
			EntityPlayerMP thePlayer = (EntityPlayerMP)player;
			int dim = thePlayer.dimension == Config.dimensionFrozenland ? 0 : Config.dimensionFrozenland;

			if (nbt != null && nbt.getString("Owner").equals(thePlayer.username))
			{
				if (thePlayer.capabilities.isCreativeMode || nbt.getLong("LastUseTime") + (world.difficultySetting > 2 ? 6000L : 3600L) < world.getTotalWorldTime())
				{
					if (thePlayer.dimension == Config.dimensionFrozenland && nbt.hasKey("LastDim"))
					{
						dim = nbt.getInteger("LastDim");

						if (!DimensionManager.isDimensionRegistered(dim) || dim == 1)
						{
							dim = 0;
						}
					}

					MinecraftServer server = thePlayer.mcServer;
					WorldServer worldNew = server.worldServerForDimension(dim);

					NBTTagCompound pos = new NBTTagCompound();
					pos.setDouble("PosX", thePlayer.posX);
					pos.setDouble("PosY", thePlayer.posY);
					pos.setDouble("PosZ", thePlayer.posZ);
					nbt.setCompoundTag("LastUsePos." + world.provider.dimensionId, pos);

					world.playSoundToNearExcept(thePlayer, "frozenland:dimensional_teleport_frozenland", 0.5F, 1.0F);

					server.getConfigurationManager().transferPlayerToDimension(thePlayer, dim, new TeleporterFrozenland(worldNew, itemstack));

					worldNew.playSoundAtEntity(thePlayer, "frozenland:dimensional_teleport_frozenland", 0.75F, 1.0F);

					if (!thePlayer.capabilities.isCreativeMode)
					{
						nbt.setLong("LastUseTime", world.getTotalWorldTime());
					}

					nbt.setInteger("LastDim", world.provider.dimensionId);
				}
			}
		}

		return itemstack;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
	{
		if (player instanceof EntityPlayerMP && player.onGround && player.isSneaking() && (entity instanceof EntityOwnable || entity instanceof EntityHorse))
		{
			NBTTagCompound nbt = itemstack.getTagCompound();
			EntityPlayerMP thePlayer = (EntityPlayerMP)player;
			boolean isOwner = false;

			if (entity instanceof EntityOwnable)
			{
				isOwner = ((EntityOwnable)entity).getOwnerName().equals(thePlayer.username);
			}
			else if (entity instanceof EntityHorse)
			{
				isOwner = ((EntityHorse)entity).getOwnerName().equals(thePlayer.username);
			}

			if (isOwner && nbt != null && nbt.getString("Owner").equals(thePlayer.username))
			{
				WorldServer worldOld = thePlayer.getServerForPlayer();
				int dim = thePlayer.dimension == Config.dimensionFrozenland ? 0 : Config.dimensionFrozenland;

				if (thePlayer.capabilities.isCreativeMode || nbt.getLong("LastUseTime") + (worldOld.difficultySetting > 2 ? 6000L : 3600L) < worldOld.getTotalWorldTime())
				{
					if (!thePlayer.capabilities.isCreativeMode)
					{
						nbt.setLong("LastUseTime", worldOld.getTotalWorldTime());
					}

					if (thePlayer.dimension == Config.dimensionFrozenland && nbt.hasKey("LastDim"))
					{
						dim = nbt.getInteger("LastDim");

						if (!DimensionManager.isDimensionRegistered(dim) || dim == 1)
						{
							dim = 0;
						}
					}

					MinecraftServer server = thePlayer.mcServer;
					WorldServer worldNew = server.worldServerForDimension(dim);

					if (entity instanceof EntityLiving)
					{
						EntityLiving living = (EntityLiving)entity;

						living.setAttackTarget(null);
						living.clearLeashed(true, false);
						living.playLivingSound();
					}

					worldOld.playSoundAtEntity(entity, "frozenland:dimensional_teleport_frozenland", 0.35F, 1.0F);

					entity.dimension = dim;
					server.getConfigurationManager().transferEntityToWorld(entity, dim, worldOld, worldNew, new TeleporterFrozenland(worldNew, itemstack));

					Entity target = EntityList.createEntityByID(EntityList.getEntityID(entity), worldNew);

					if (target != null)
					{
						target.copyDataFrom(entity, true);
						target.isDead = false;
						target.forceSpawn = true;

						worldNew.spawnEntityInWorld(target);
						worldNew.updateEntity(target);

						worldNew.playSoundAtEntity(target, "frozenland:dimensional_teleport_frozenland", 0.5F, 1.0F);
					}

					entity.setDead();

					worldOld.resetUpdateEntityTick();
					worldNew.resetUpdateEntityTick();
				}

				return true;
			}
		}

		return super.onLeftClickEntity(itemstack, player, entity);
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

			if (!nbt.hasKey("Owner"))
			{
				nbt.setString("Owner", player.username);
			}
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
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean advanced)
	{
		NBTTagCompound nbt = itemstack.getTagCompound();

		if (nbt != null && nbt.hasKey("Owner"))
		{
			list.add(EnumChatFormatting.ITALIC + "Owner: " + EnumChatFormatting.RESET + nbt.getString("Owner"));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemstack)
	{
		return EnumRarity.rare;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack itemstack, int pass)
	{
		NBTTagCompound nbt = itemstack.getTagCompound();

		return nbt != null && nbt.hasKey("Owner");
	}
}