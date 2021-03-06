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
    private final double range;

    public ShuntSpell(int arcanaCost, SpellTier tierIn, SpellSchool schoolIn, double rangeIn)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.finalSound = SoundEvents.ENTITY_ENDERMAN_TELEPORT;
        this.range = rangeIn;
    }


    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        if (context.getTargetEntity() instanceof MobEntity)
        {
            MobEntity mobEntity = (MobEntity)context.getTargetEntity();
            Vector3d newPos = Utils.randomPosInRange(context.getWorld().rand, mobEntity.getPosX(), mobEntity.getPosY(), mobEntity.getPosZ(), this.range);
            this.doParticleEffects(context, mobEntity.getPositionVec(), newPos);
            this.playFinalSound(context);
            mobEntity.setPositionAndUpdate(newPos.x, newPos.y, newPos.z);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    // Enforce limited range by only running when entity target directly
    @Override
    public boolean allowCast(SpellUseContext context)
    {
        return context.wasEntityTargeted();
    }

    // Send packets to play particle effects
    public void doParticleEffects(SpellUseContext context, Vector3d origin, Vector3d newPosition)
    {
        ParticleEffectPacket pkt = new ParticleEffectPacket(0, Particles.getPuff(), origin, context.getTargetEntity().getLookVec(), 5, 0.1, 0.2, 10);
        ParticleEffectPacket pkt1 = new ParticleEffectPacket(16, 17, newPosition, Vector3d.ZERO, 10, 0.1, 2, 10);

        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
    }
}
