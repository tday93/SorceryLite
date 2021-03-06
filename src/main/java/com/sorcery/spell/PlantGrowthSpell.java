package com.sorcery.spell;

import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;

public class PlantGrowthSpell extends Spell
{
    public PlantGrowthSpell(int arcanaCost, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(arcanaCost, tierIn, schoolIn);
    }

    @Override
    public boolean allowCast(SpellUseContext context)
    {
        return context.wasUsedOnBlock();
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        this.playFinalSound(context);
        if (context.wasUsedOnBlock())
        {
            BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL), context.getWorld(), context.getHitPos());
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void doCastFinalClient(SpellUseContext context)
    {
        if (context.wasUsedOnBlock())
        {
            BoneMealItem.spawnBonemealParticles(context.getWorld(), context.getHitPos(), 7);
        }
    }
}
