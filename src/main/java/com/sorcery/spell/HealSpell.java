package com.sorcery.spell;

import com.sorcery.potion.ModEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.FoodStats;

public class HealSpell extends Spell
{

    private final double healAmount;
    private final double hungerMulti;
    private final int cooldown;

    public HealSpell(int arcanaCost, SpellTier tierIn, SpellSchool schoolIn, double healAmountIn, double hungerMultiIn, int cooldownIn)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.healAmount = healAmountIn;
        this.hungerMulti = hungerMultiIn;
        this.cooldown = cooldownIn;
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        if (context.getPlayer() != null)
        {
            PlayerEntity player = context.getPlayer();
            EffectInstance instance = player.getActivePotionEffect(ModEffect.HEALING_SICKNESS);
            if (instance == null)
            {
                FoodStats stats = player.getFoodStats();
                stats.setFoodLevel(stats.getFoodLevel() - (int)(healAmount * hungerMulti));
                player.heal((float)healAmount);
                player.addPotionEffect(new EffectInstance(ModEffect.HEALING_SICKNESS, this.cooldown));
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }



}
