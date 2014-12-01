/*
 * Frozenland
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.frozenland.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityIceball extends EntityThrowable
{
	public EntityIceball(World world)
	{
		super(world);
	}

	public EntityIceball(World world, EntityLivingBase entity)
	{
		super(world, entity);
	}

	public EntityIceball(World world, double posX, double posY, double posZ)
	{
		super(world, posX, posY, posZ);
	}

	@Override
	protected void onImpact(MovingObjectPosition moving)
	{
		if (moving.entityHit != null)
		{
			byte damage = 1;

			if (moving.entityHit instanceof EntityBlaze)
			{
				damage = 5;
			}

			moving.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), damage);

			if (moving.entityHit.isBurning() && rand.nextInt(4) == 0)
			{
				moving.entityHit.extinguish();
			}
		}

		for (int i = 0; i < 10; ++i)
		{
			worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
		}

		worldObj.playSoundAtEntity(this, "dig.glass", 0.75F, rand.nextFloat() * 0.6F + 0.8F);

		if (!worldObj.isRemote)
		{
			setDead();
		}
	}
}