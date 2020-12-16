package com.sorcery.spell.components;

import com.sorcery.spell.SpellUseContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

public class FireElementalComponent extends ElementalComponent
{
    private final int fireDuration;
    private final int fireDamage;

    public FireElementalComponent(int fireDamage, int fireDuration)
    {
        this.fireDuration = fireDuration;
        this.fireDamage = fireDamage;
    }

    @Override
    public void damageEntity(LivingEntity entity, SpellUseContext context)
    {
        entity.attackEntityFrom(DamageSource.IN_FIRE, this.fireDamage);
        entity.setFire(this.fireDuration);
    }

    @Override
    public int getPrimaryParticleCollection()
    {
        return 23;
    }

    @Override
    public int getSecondaryParticleCollection()
    {
        return 23;
    }
}
