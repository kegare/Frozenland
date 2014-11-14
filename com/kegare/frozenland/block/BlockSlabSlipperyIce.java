/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.block;

import java.util.Random;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.kegare.frozenland.core.Frozenland;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSlabSlipperyIce extends BlockSlab
{
	public BlockSlabSlipperyIce(String name)
	{
		super(false, Material.packedIce);
		this.setBlockName(name);
		this.setStepSound(FrozenBlocks.slippery_ice.stepSound);
		this.setCreativeTab(Frozenland.tabFrozenland);
		this.slipperiness = FrozenBlocks.slippery_ice.slipperiness;
		this.useNeighborBrightness = true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata)
	{
		return FrozenBlocks.slippery_ice.getIcon(side, metadata);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBlockColor()
	{
		return FrozenBlocks.slippery_ice.getBlockColor();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderColor(int metadata)
	{
		return FrozenBlocks.slippery_ice.getRenderColor(metadata);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
	{
		return FrozenBlocks.slippery_ice.colorMultiplier(blockAccess, x, y, z);
	}

	@Override
	public String func_150002_b(int metadata)
	{
		return getUnlocalizedName();
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		return FrozenBlocks.slippery_ice.getBlockHardness(world, x, y, z);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getItem(World world, int x, int y, int z)
	{
		return Item.getItemFromBlock(this);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	protected boolean canSilkHarvest()
	{
		return true;
	}
}