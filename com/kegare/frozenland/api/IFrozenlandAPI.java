package com.kegare.frozenland.api;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

public interface IFrozenlandAPI
{
	/**
	 * Returns the current mod version of Frozenland.
	 */
	public String getVersion();

	/**
	 * Returns the dimension id of the Frozenland dimension.
	 */
	public int getDimension();

	/**
	 * Checks if entity is in the Frozenland dimension.
	 * @param entity The entity
	 * @return <tt>true</tt> if the entity is in the Frozenland dimension.
	 */
	public boolean isEntityInFrozenland(Entity entity);

	/**
	 * Returns the Frozenland biome.
	 */
	public BiomeGenBase getBiome();

	/**
	 * Checks if item is an ice tool.
	 * @param item The item
	 * @return <tt>true</tt> if the item is an ice tool.
	 */
	public boolean isIceTool(Item item);

	/**
	 * Checks if itemstack is an ice tool.
	 * @param itemstack The itemstack
	 * @return <tt>true</tt> if the itemstack is an ice tool.
	 */
	public boolean isIceTool(ItemStack itemstack);

	/**
	 * @see IItemIceTool#getGrade(ItemStack)
	 */
	public int getIceToolGrade(ItemStack itemstack);

	/**
	 * @see IItemIceTool#setGrade(ItemStack, int)
	 */
	public void setIceToolGrade(ItemStack itemstack, int grade);

	/**
	 * @see IItemIceTool#addGrade(ItemStack, int)
	 */
	public void addIceToolGrade(ItemStack itemstack, int amount);
}