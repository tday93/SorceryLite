package com.sorcery.spell;

import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.particle.Particles;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.vector.Vector3d;

public class PotionSpell extends Spell
{
    // Potion effect to apply
    private Effect effect;
    // Duration in ticks, 1 second = 20 ticks
    private int duration;
    // effect amplifier
    private int amp;
    private boolean castOnSelf = true;


    public PotionSpell(int costIn, Effect effectIn, int durationIn, int ampIn)
    {
        super(costIn);
        this.effect = effectIn;
        this.duration = durationIn;
        this.amp = ampIn;
    }

    public PotionSpell(int costIn, Effect effectIn, int durationIn, int ampIn, boolean selfCastIn, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(costIn);
        this.effect = effectIn;
        this.duration = durationIn;
        this.amp = ampIn;
        this.castOnSelf = selfCastIn;
        this.spellTier = tierIn;
        this.spellSchool = schoolIn;
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        this.doParticleEffects(context);
        this.playSound(context);

        if (context.wasEntityTargeted())
        {
            EffectInstance potionEffect = new EffectInstance(effect, duration, this.amp, false, false);
            context.getTargetEntity().addPotionEffect(potionEffect);
            return ActionResultType.SUCCESS;
        }
        else if (this.castOnSelf)
        {
            EffectInstance potionEffect = new EffectInstance(effect, duration, this.amp, false, false);
            context.getPlayer().addPotionEffect(potionEffect);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    @Override
    public void doParticleEffects(SpellUseContext context)
    {
        Vector3d loc = context.getPlayer().getPositionVec().add(0,1, 0);
        Vector3d look = context.getPlayer().getLook(1);

        ParticleEffectPacket pkt = new ParticleEffectPacket(2, Particles.getPuff(), loc, look, 100, 0.5, 0.2, 20);

        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt);
    }
}
