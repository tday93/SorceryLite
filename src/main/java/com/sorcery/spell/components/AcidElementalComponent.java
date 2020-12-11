package com.sorcery.spell.components;

import com.sorcery.potion.ModEffect;
import com.sorcery.spell.SpellUseContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;

public class AcidElementalComponent extends ElementalComponent
{
    private int damage;
    private int corrosionDuration;
    private int corrosionAmp;

    public AcidElementalComponent(int damage, int corrosionDuration, int corrosionAmp)
    {
        this.damage = damage;
        this.corrosionDuration = corrosionDuration;
        this.corrosionAmp = corrosionAmp;
    }

    @Override
    public void damageEntity(LivingEntity entity, SpellUseContext context)
    {
        EffectInstance potionEffect = new EffectInstance(ModEffect.CORROSION, this.corrosionDuration, this.corrosionAmp, false, true);
        entity.addPotionEffect(potionEffect);
        entity.attackEntityFrom(DamageSource.GENERIC, this.damage);
    }

    @Override
    public int getPrimaryParticleCollection()
    {
        return 22;
    }

    @Override
    public int getSecondaryParticleCollection()
    {
        return 22;
    }
}
