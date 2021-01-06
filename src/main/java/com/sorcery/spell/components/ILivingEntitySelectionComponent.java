package com.sorcery.spell.components;

import com.sorcery.spell.SpellUseContext;
import net.minecraft.entity.LivingEntity;

import java.util.List;

public interface ILivingEntitySelectionComponent
{
    public List<LivingEntity> selectEntities(SpellUseContext context);
}
