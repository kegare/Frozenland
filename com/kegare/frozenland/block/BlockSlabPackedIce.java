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
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.kegare.frozenland.core.Frozenland;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSlabPackedIce extends BlockSlab
{
	public BlockSlabPackedIce(String name)
	{
		super(false, Material.packedIce);
		this.setBlockName(name);
		this.setHardness(0.5F);
		this.setStepSound(soundTypeGlass);
		this.setCreativeTab(Frozenland.tabFrozenland);
		this.slipperiness = 0.98F;
		this.useNeighborBrightness = true;
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

	@Override
	public String func_150002_b(int metadata)
	{
		return getUnlocalizedName();
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