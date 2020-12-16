package com.sorcery.spell;

import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.utils.Utils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class DrainLifeSpell extends Spell
{
    private final int lifeToDrain;

    public DrainLifeSpell(int arcanaCost, SpellTier tierIn, SpellSchool schoolIn, int lifeToDrainIn)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.lifeToDrain = lifeToDrainIn;
    }

    // Enforce limited range by only running when entity target directly
    @Override
    public boolean allowCast(SpellUseContext context)
    {
        return context.wasEntityTargeted();
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        LivingEntity entity = context.getTargetEntity();
        float entityHealth = entity.getHealth();

        if (entity.isEntityUndead())
        {
            entityHealth = entity.getMaxHealth() - entityHealth;
            entity.heal(this.lifeToDrain);
        } else {
            entity.attackEntityFrom(DamageSource.MAGIC, this.lifeToDrain);
        }
        this.doParticleEffects(context);

        context.getPlayer().heal(Math.min(entityHealth, this.lifeToDrain));

        return ActionResultType.SUCCESS;
    }

    @Override
    public void doParticleEffects(SpellUseContext context)
    {
        Vector3d loc = context.getTargetEntity().getEyePosition(0);
        ParticleEffectPacket pkt1 = new ParticleEffectPacket(0, 15, loc, Vector3d.ZERO, 10, 0.1, 0.5, 20);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
    }
}
