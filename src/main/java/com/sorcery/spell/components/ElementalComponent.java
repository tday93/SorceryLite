package com.sorcery.spell.components;

import com.sorcery.spell.SpellUseContext;
import net.minecraft.entity.LivingEntity;

public class ElementalComponent implements IDamageEntityComponent, IParticleCollectionComponent
{
    @Override
    public void damageEntity(LivingEntity entity, SpellUseContext context)
    {

    }

    @Override
    public int getPrimaryParticleCollection()
    {
        return 0;
    }

    @Override
    public int getSecondaryParticleCollection()
    {
        return 0;
    }
}
