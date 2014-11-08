package com.kegare.frozenland.api;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;

/**
 * NOTE: Do NOT access to this class fields.
 * You should use this API from this class methods.
 */
public class FrozenlandAPI
{
	public static IFrozenlandAPI instance;

	private FrozenlandAPI() {}

	/**
	 * @see IFrozenlandAPI#getVersion()
	 */
	public static String getVersion()
	{
		return instance == null ? "" : instance.getVersion();
	}

	/**
	 * @see IFrozenlandAPI#getDimension()
	 */
	public static int getDimension()
	{
		return instance == null ? DimensionManager.getNextFreeDimId() : instance.getDimension();
	}

	/**
	 * @see IFrozenlandAPI#isEntityInFrozenland(Entity)
	 */
	public static boolean isEntityInFrozenland(Entity entity)
	{
		return instance != null && instance.isEntityInFrozenland(entity);
	}

	/**
	 * @see IFrozenlandAPI#getBiome()
	 */
	public static BiomeGenBase getBiome()
	{
		return instance == null ? BiomeGenBase.iceMountains : instance.getBiome();
	}

	/**
	 * @see IFrozenlandAPI#isIceTool(Item)
	 */
	public static boolean isIceTool(Item item)
	{
		return instance != null && instance.isIceTool(item);
	}

	/**
	 * @see IFrozenlandAPI#isIceTool(ItemStack)
	 */
	public static boolean isIceTool(ItemStack itemstack)
	{
		return instance != null && instance.isIceTool(itemstack);
	}

	/**
	 * @see IFrozenlandAPI#getIceToolGrade(ItemStack)
	 */
	public static int getIceToolGrade(ItemStack itemstack)
	{
		return instance == null ? 0 : instance.getIceToolGrade(itemstack);
	}

	/**
	 * @see IFrozenlandAPI#setIceToolGrade(ItemStack, int)
	 */
	public static void setIceToolGrade(ItemStack itemstack, int grade)
	{
		if (instance != null)
		{
			instance.setIceToolGrade(itemstack, grade);
		}
	}

	/**
	 * @see IFrozenlandAPI#addIceToolGrade(ItemStack, int)
	 */
	public static void addIceToolGrade(ItemStack itemstack, int amount)
	{
		if (instance != null)
		{
			instance.addIceToolGrade(itemstack, amount);
		}
	}
}