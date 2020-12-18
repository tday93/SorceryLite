package com.sorcery.entity.projectile;

import com.sorcery.Constants;
import com.sorcery.Sorcery;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SpellProjectileEntity extends DamagingProjectileEntity implements IRendersAsItem
{
    public ResourceLocation projectileTexture;
    public IParticleData baseParticle;

    private final int maxTicksExisted = 100;

    public SpellProjectileEntity(EntityType<? extends SpellProjectileEntity> entityType, World world)
    {
        super(entityType, world);
        this.projectileTexture = new ResourceLocation(Constants.MODID, "textures/entity/spell_projectile.png");
        this.baseParticle = ParticleTypes.SMOKE;
        this.setNoGravity(true);
    }

    // Called when projectile hits block
    public void onBlockHit(BlockRayTraceResult result)
    {
    }

    // Called when projectile hits entity
    public void onEntityHit(EntityRayTraceResult result)
    {
    }

    @Override
    public void tick()
    {
        if (this.ticksExisted > this.maxTicksExisted)
        {
            this.remove();
        }
        this.ticksExisted++;
        super.tick();
    }

    // Override of base onImpact, and dispatch to onBlockHit and onEntity Hit
    @Override
    protected void onImpact(RayTraceResult result)
    {
        if (!this.world.isRemote) {
            if (result.getType() == RayTraceResult.Type.ENTITY)
            {
                this.onEntityHit((EntityRayTraceResult) result);
            }
            if (result.getType() == RayTraceResult.Type.BLOCK)
            {
                this.onBlockHit((BlockRayTraceResult) result);
            }
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }
    }

    @Override
    protected float getMotionFactor() {
        // gonna shove a bunch of nonesense in here to deal with how super handles motion in its tick method
        return 0.95F;
    }

    @Override
    protected IParticleData getParticle() {
        return this.baseParticle;
    }

    @Override
    public boolean isFireballFiery() {
        return false;
    }

    public IPacket<?> createSpawnPacket() {
       return NetworkHooks.getEntitySpawningPacket(this);
    }

    public ResourceLocation getEntityTexture()
    {
       return this.projectileTexture;
    }

    @Override
    public ItemStack getItem()
    {
        return new ItemStack(Items.FIRE_CHARGE);
    }
}
