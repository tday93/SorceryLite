package com.sorcery.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class RGBLitParticle extends SpriteTexturedParticle
{
    IAnimatedSprite spriteSet;

    Boolean doAnimation = true;

    public RGBLitParticle(ClientWorld world, double x, double y, double z, double vX, double vY, double vZ, IAnimatedSprite spriteSetIn, float r, float g, float b, float a)
    {
        super(world, x, y, z, vX, vY, vZ);

        // Override motion randomization
        this.motionX = vX;
        this.motionY = vY;
        this.motionZ = vZ;
        this.spriteSet = spriteSetIn;
        this.particleRed = r;
        this.particleBlue = b;
        this.particleGreen = g;
        this.particleAlpha = a;
        this.canCollide = false;
    }


    @Override
    public void tick() {
        if (this.doAnimation)
        {
            this.selectSpriteWithAge(this.spriteSet);
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            this.motionY -= 0.04D * (double)this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);
            if (this.onGround) {
                this.motionX *= (double)0.7F;
                this.motionZ *= (double)0.7F;
            }
        }
    }

    @Override
    public IParticleRenderType getRenderType()
    {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }


    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<RGBAParticleData>
    {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet)
        {
            this.spriteSet = spriteSet;

        }

        @Nullable
        @Override
        public Particle makeParticle(RGBAParticleData data, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            RGBLitParticle simpleParticle = new RGBLitParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet, data.r, data.g, data.b, data.a);
            simpleParticle.setMaxAge(data.t);
            simpleParticle.doAnimation = data.q;
            if (data.q)
            {
                simpleParticle.selectSpriteWithAge(spriteSet);
            } else {
                simpleParticle.selectSpriteRandomly(spriteSet);
            }
            return simpleParticle;
        }
    }

}
