package com.sorcery.spell;

import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.utils.Utils;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;

public class ChillingTouchSpell extends Spell
{
    private int damageAmount;

    public ChillingTouchSpell(int arcanaCost, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(arcanaCost);
        this.damageAmount = 3;
        this.finalSound = SoundEvents.BLOCK_SNOW_PLACE;
        this.spellTier = tierIn;
        this.spellSchool = schoolIn;
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
        this.doParticleEffects(context);
        this.playFinalSound(context);
        context.getTargetEntity().addPotionEffect(new EffectInstance(Effects.SLOWNESS, 3, 1));
        context.getTargetEntity().attackEntityFrom(DamageSource.MAGIC, 3);
        return ActionResultType.SUCCESS;
    }

    @Override
    public void doParticleEffects(SpellUseContext context)
    {
        Vector3d loc = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLook(0), 1f).add(0, -.1, 0);
        Vector3d look = context.getPlayer().getLookVec();

        ParticleEffectPacket pkt1 = new ParticleEffectPacket(3, 7, loc, look, 40, 0.5, 0.2, 20);

        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
    }
    // Play sound effects, override if you want different behavior
    @Override
    public void playFinalSound(SpellUseContext context)
    {
        context.getWorld().playSound(context.getPlayer(), context.getPos(), this.finalSound, this.soundCategory, 1.0F, context.getWorld().rand.nextFloat() * 0.4F + 0.8F);
    }
}
