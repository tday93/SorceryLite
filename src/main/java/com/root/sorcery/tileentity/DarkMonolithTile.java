package com.root.sorcery.tileentity;

import com.root.sorcery.block.MonolithBlock;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ParticleEffectPacket;
import com.root.sorcery.particle.Particles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;


public class DarkMonolithTile extends AbstractMonolithTile
{

    public double arcanaPerHP = 1;

    public DarkMonolithTile()
    {
        super(ModTile.DARK_MONOLITH_TILE, 1000);
        this.arcanaStorage.extractArcana(1000, false);
    }

    public void processDeath(LivingEntity entity)
    {
        System.out.println("Death detected!");
        int arcanaToAdd = (int)(entity.getMaxHealth() * (float)arcanaPerHP);
        this.receiveArcana(arcanaToAdd);
        ParticleEffectPacket pkt =  new ParticleEffectPacket(6, Particles.getBloodSpark(), new Vec3d(this.pos), entity.getPositionVec(), 20, 1, 1, 40);
        PacketHandler.sendToAllTracking(entity, pkt);
    }

    @Override
    public void tick()
    {
        if (!world.isRemote())
        {
            if (world.getGameTime() % 20 == 0)
            {
                if (this.arcanaStorage.getArcanaStored() < 10)
                {
                    MonolithBlock.setActivity(world, this.getBlockState(), this.pos, false);
                } else {
                    MonolithBlock.setActivity(world, this.getBlockState(), this.pos, true);

                }
            }
        }
        super.tick();
    }
}
