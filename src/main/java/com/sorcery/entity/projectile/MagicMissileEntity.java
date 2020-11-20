package com.sorcery.entity.projectile;

import com.sorcery.Constants;
import com.sorcery.item.ModItem;
import com.sorcery.particle.ModParticle;
import com.sorcery.particle.Particles;
import com.sorcery.utils.ModColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;


public class MagicMissileEntity extends SpellProjectileEntity
{
    Entity targetEntity = null;

    Vector3d targetVector = null;

    double targetAcceleration = 1;

    int damage = 2;

    double speedLimit = 1;

    public MagicMissileEntity(EntityType<? extends MagicMissileEntity> entityType, World world)
    {
        super(entityType, world);
        this.projectileTexture = new ResourceLocation(Constants.MODID, "textures/item/spell_projectile.png");
        this.setNoGravity(true);
    }

    @Override
    protected IParticleData getParticle() {
        return Particles.getColoredParticle(ModColor.EVOCATION_RED.getMainList(), ModParticle.ARCANA_BROWNIAN, 0.8f, 20, false);
    }

    @Override
    public void onEntityHit(EntityRayTraceResult result)
    {
        if (result.getEntity() instanceof LivingEntity)
        {
            LivingEntity hitEntity = (LivingEntity) result.getEntity();
            hitEntity.attackEntityFrom(DamageSource.MAGIC, this.damage);
        }
    }

    // gonna shove a bunch of nonsense in here to deal with how super handles motion in its tick method
    @Override
    protected float getMotionFactor() {

        Vector3d target = null;
        if (this.targetEntity != null)
        {
            target = this.targetEntity.getPositionVec().add(0, 0.5, 0);
        } else if (this.targetVector != null)
        {
            target = this.targetVector;
        }
        if (target != null)
        {

            Vector3d accelVec = target.subtract(this.getPositionVec()).normalize().scale(this.targetAcceleration);
            accelVec = accelVec.subtract(this.getMotion()).normalize().scale(0.5);
            this.accelerationX = accelVec.getX();
            this.accelerationY = accelVec.getY();
            this.accelerationZ = accelVec.getZ();
        }
        return 1.0F;
    }

    public void setTargetEntity(Entity entity)
    {
        this.targetEntity = entity;
    }

    public void setTargetVector(Vector3d vector)
    {
        this.targetVector = vector;
    }

    @Override
    public ItemStack getItem()
    {
        return new ItemStack(ModItem.SPELL_PROJECTILE.get());
    }
}
