package com.sorcery.tileentity;

import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.utils.MonolithPattern;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;


public class DarkMonolithTile extends AbstractMonolithTile
{

    public double arcanaPerHP = 1;

    public DarkMonolithTile()
    {
        super(ModTile.DARK_MONOLITH_TILE, 1000, MonolithPattern.DARK);
        this.arcanaStorage.extractArcana(1000, false);
    }

    public void processDeath(LivingEntity entity)
    {
        if (this.beingInterfered())
        {
            return;
        }
        int arcanaToAdd = (int)(entity.getMaxHealth() * (float)arcanaPerHP);
        this.receiveArcana(arcanaToAdd);
        ParticleEffectPacket pkt =  new ParticleEffectPacket(7, 4, new Vector3d(this.pos.getX(), this.pos.getY(), this.pos.getZ()), entity.getPositionVec(), 20, 1, 1, 40);
        PacketHandler.sendToAllTrackingEntity(entity, pkt);
    }

    @Override
    public void tick()
    {
        super.tick();
    }
}
