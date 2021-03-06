package com.sorcery.spell;

import com.sorcery.Config;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.particle.Particles;
import com.sorcery.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class RepelSpell extends Spell
{
    private final int range;
    private final double velocity;

    public RepelSpell(int costIn, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(costIn, tierIn, schoolIn);
        this.range = Config.SPELL_REPEL_RANGE.get();
        this.velocity = Config.SPELL_REPEL_VELOCITY.get() * 0.1;
    }

    // Server-side only stuff happens here
    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        this.playFinalSound(context);
        LivingEntity repelFrom = context.getPlayer();
        List<Entity> entities = Utils.entitiesInRange(context.getWorld(), context.getPos(), range, repelFrom);

        for (Entity entity : entities)
        {
            if (entity instanceof LivingEntity)
            {
                // Send them flying away, also slightly up
                Vector3d repelVec = entity.getPositionVec().subtract(repelFrom.getPositionVec()).normalize().add(0, 0.2, 0).normalize();
                entity.addVelocity(repelVec.x * velocity, repelVec.y * velocity, repelVec.z * velocity);

                // Poof effects
                ParticleEffectPacket pkt = new ParticleEffectPacket(0, Particles.getPuff(), entity.getPositionVec(), entity.getLookVec(), 5, 0, 0.2, 10);
                PacketHandler.sendToAllTrackingPlayer(repelFrom, pkt);
            }
        }

        return ActionResultType.SUCCESS;
    }
}
