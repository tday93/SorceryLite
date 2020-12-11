package com.sorcery.spell.components;

import com.sorcery.spell.SpellUseContext;
import net.minecraft.entity.LivingEntity;

public interface IDamageEntityComponent
{
    void damageEntity(LivingEntity entity, SpellUseContext context);
}
