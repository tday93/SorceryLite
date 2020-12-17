package com.sorcery.spell.components;

import com.sorcery.spell.SpellUseContext;

public interface IMaterialConsumptionComponent
{
    public boolean canCast(SpellUseContext context);

    public void consumeComponents(SpellUseContext context);
}
