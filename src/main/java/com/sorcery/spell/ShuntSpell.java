package com.sorcery.spell;

import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.particle.Particles;
import com.sorcery.utils.Utils;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;

public class ShuntSpell extends Spell
{
    private double range;

    public ShuntSpell(int arcanaCost, double rangeIn)
    {
        super(arcanaCost);
        this.sound = SoundEvents.ENTITY_ENDERMAN_TELEPORT;
        this.range = rangeIn;
    }


    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        if (context.getTargetEntity() instanceof MobEntity)
        {
            MobEntity mobEntity = (MobEntity)context.getTargetEntity();
            Vector3d newPos = Utils.randomPosInRange(context.getWorld().rand, mobEntity.getPosX(), mobEntity.getPosY(), mobEntity.getPosZ(), this.range);
            this.doParticleEffects(context, mobEntity.getPositionVec());
            this.playSound(context);
            mobEntity.setPositionAndUpdate(newPos.x, newPos.y, newPos.z);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    // Send packets to play particle effects
    public void doParticleEffects(SpellUseContext context, Vector3d origin)
    {
        ParticleEffectPacket pkt = new ParticleEffectPacket(0, Particles.getPuff(), origin, context.getTargetEntity().getLookVec(), 5, 0.1, 0.2, 20);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt);
    }
}
