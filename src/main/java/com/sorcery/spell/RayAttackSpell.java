package com.sorcery.spell;

import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.utils.Utils;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;
import java.util.List;

public class RayAttackSpell extends Spell
{
    private int damage;
    private DamageSource damageSource;
    private Effect effect;
    private int effectDuration;
    private int particleCollection;

    public RayAttackSpell(int arcanaCost, int damage, DamageSource damageSource, @Nullable Effect effect, @Nullable int effectDuration, int particleCollection, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.damage = damage;
        this.damageSource = damageSource;
        this.effect = effect;
        this.effectDuration = effectDuration;
        this.particleCollection = particleCollection;
        this.castType = CastType.CHANNELED;
        this.castDuration = 100;
    }

    @Override
    public ActionResultType doCastPerTick(SpellUseContext context)
    {
        this.doParticleEffects(context);
        this.playFinalSound(context);
        List<Entity> entList = Utils.entitiesInCone(context.getWorld(), context.getPos(), context.getPlayer(), context.getPlayer().getEyePosition(1), context.getPlayer().getLook(1), 8, 0.1);

        for ( Entity entity : entList)
        {
            if (entity instanceof CreatureEntity)
            {
                entity.attackEntityFrom(this.damageSource, this.damage);
                if (this.effect != null)
                {
                    EffectInstance potionEffect = new EffectInstance(this.effect, this.effectDuration, 1, false, false);
                    ((LivingEntity)entity).addPotionEffect(potionEffect);
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void doParticleEffects(SpellUseContext context)
    {
        Vector3d loc = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLook(0), 1).add(0, -0.2, 0);
        Vector3d beamEnd = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLook(0), 8);

        ParticleEffectPacket pkt1 = new ParticleEffectPacket(13, this.particleCollection, loc, beamEnd, 30, 1, 0.05, 1);

        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
    }
}
