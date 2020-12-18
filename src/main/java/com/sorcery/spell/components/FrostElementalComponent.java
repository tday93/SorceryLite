package com.sorcery.spell.components;

import com.sorcery.spell.SpellUseContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;

public class FrostElementalComponent extends ElementalComponent
{
    private final int damage;
    private final int slowDuration;
    private final int slowAmp;

    public FrostElementalComponent(int damage, int slowDuration, int slowAmp)
    {
        this.damage = damage;
        this.slowDuration = slowDuration;
        this.slowAmp = slowAmp;

    }

    @Override
    public void damageEntity(LivingEntity entity, SpellUseContext context)
    {
        EffectInstance potionEffect = new EffectInstance(Effects.SLOWNESS, this.slowDuration, this.slowAmp, false, true);
        entity.addPotionEffect(potionEffect);
        entity.attackEntityFrom(DamageSource.GENERIC, this.damage);
    }

    @Override
    public int getPrimaryParticleCollection()
    {
        return 18;
    }

    @Override
    public int getSecondaryParticleCollection()
    {
        return 19;
    }
}
