package com.sorcery.spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;

public class DrainLifeSpell extends Spell
{
    private int lifeToDrain;

    public DrainLifeSpell(int arcanaCost, int lifeToDrainIn)
    {
        super(arcanaCost);
        this.lifeToDrain = lifeToDrainIn;
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
        LivingEntity entity = context.getTargetEntity();
        float entityHealth = entity.getHealth();

        if (entity.isEntityUndead())
        {
            entityHealth = entity.getMaxHealth() - entityHealth;
            entity.heal(this.lifeToDrain);
        } else {
            entity.attackEntityFrom(DamageSource.MAGIC, this.lifeToDrain);
        }

        context.getPlayer().heal(Math.min(entityHealth, this.lifeToDrain));

        return ActionResultType.SUCCESS;
    }
}
