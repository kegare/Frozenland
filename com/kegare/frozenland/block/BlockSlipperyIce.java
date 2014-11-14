/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.block;

import net.minecraft.block.BlockPackedIce;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.kegare.frozenland.core.Frozenland;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSlipperyIce extends BlockPackedIce
{
	public BlockSlipperyIce(String name)
	{
		super();
		this.setBlockName(name);
		this.setHardness(0.6F);
		this.setStepSound(soundTypeGlass);
		this.setCreativeTab(Frozenland.tabFrozenland);
		this.slipperiness = 1.08F;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata)
	{
		return Blocks.packed_ice.getIcon(side, metadata);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBlockColor()
	{
		return 0xD3EDFB;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderColor(int metadata)
	{
		return getBlockColor();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
	{
		return getBlockColor();
	}

	@Override
	protected boolean canSilkHarvest()
	{
		return true;
	}
}