package com.sorcery.spell;

import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

public class PlantLifeSpell extends Spell
{
    public PlantLifeSpell(int arcanaCost)
    {
        super(arcanaCost);
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        this.doParticleEffects(context);
        this.playSound(context);
        World world = context.getWorld();
        if (context.wasUsedOnBlock())
        {
            BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL), context.getWorld(), context.getHitPos());
        }
        return ActionResultType.SUCCESS;
    }
}
