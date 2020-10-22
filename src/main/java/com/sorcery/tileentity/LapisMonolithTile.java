package com.sorcery.tileentity;

import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.utils.MonolithPattern;
import com.sorcery.utils.Utils;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Vector3d;

import java.util.ArrayList;
import java.util.List;


public class LapisMonolithTile extends AbstractMonolithTile
{

    private int arcanaPerLapis = 100;

    public LapisMonolithTile()
    {
        super(ModTile.LAPIS_MONOLITH_TILE, 1000, MonolithPattern.LAPIS);
        this.arcanaStorage.extractArcana(1000, false);
    }

    public void acceptLapis()
    {
        this.receiveArcana(arcanaPerLapis);
    }

    public boolean canAcceptLapis()
    {
        int arcanaSpace = this.arcanaStorage.getMaxArcanaStored() - this.arcanaStorage.getArcanaStored();
        if (arcanaSpace >= this.arcanaPerLapis)
        {
            return true;
        } else {
            return false;
        }
    }

    public void doSuckParticleEffect(ItemEntity entity)
    {
        Vector3d suckVec = entity.getPositionVec();
        ParticleEffectPacket pkt =  new ParticleEffectPacket(7, 3, this.getOwnPulseTarget(), suckVec, 20, 1, 0.5, 40);
        PacketHandler.sendToAllTrackingEntity(entity, pkt);
    }

    @Override
    public void tick()
    {
        if (!world.isRemote())
        {
            long worldTicks = this.getOffsetWorldTicks();
            if (worldTicks % 40 == 0)
            {
                // look for lapis on ground
                List<ItemEntity> allItemEntities = world.getEntitiesWithinAABB(ItemEntity.class, Utils.getRangeAABB(this.pos, 8, 4, 2));
                List<ItemEntity> lapisItemEntities = new ArrayList<>();
                // set no despawn on all
                for ( ItemEntity itemEntity : allItemEntities)
                {
                    if (itemEntity.getItem().getItem() == Items.LAPIS_LAZULI)
                    {
                        itemEntity.setNoDespawn();
                        lapisItemEntities.add(itemEntity);
                    }
                }
                // suck up one lapis
                if (!lapisItemEntities.isEmpty())
                {
                    ItemEntity itemEntity = lapisItemEntities.get(0);
                    if (this.canAcceptLapis() && !this.interference)
                    {
                        this.doSuckParticleEffect(itemEntity);
                        this.acceptLapis();
                        itemEntity.getItem().shrink(1);
                    }
                }
            }
        }
        super.tick();
    }
}
