package com.kegare.frozenland.api;

import net.minecraft.item.ItemStack;

public interface IItemIceTool
{
	/**
	 * Returns the ice tool grade.
	 * @param itemstack The itemstack
	 */
	public int getGrade(ItemStack itemstack);

	/**
	 * Sets the ice tool grade.
	 * @param itemstack The itemstack
	 * @param grade The grade
	 */
	public void setGrade(ItemStack itemstack, int grade);

	/**
	 * Adds the ice tool grade.
	 * @param itemstack The itemstack
	 * @param amount The amount
	 */
	public void addGrade(ItemStack itemstack, int amount);
}