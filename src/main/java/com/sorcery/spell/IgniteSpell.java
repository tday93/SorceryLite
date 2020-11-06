package com.sorcery.spell;

import com.sorcery.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class IgniteSpell extends Spell
{
    public IgniteSpell(SpellTier tierIn, SpellSchool schoolIn)
    {
        super(Config.SPELL_IGNITE_COST.get());
        this.sound = SoundEvents.ITEM_FIRECHARGE_USE;
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        if (context.getHitPos() != null)
        {
            BlockPos firePos = context.getFacePos();

            if (!context.getWorld().isAirBlock(firePos))
            {
                return ActionResultType.FAIL;
            }
            this.doParticleEffects(context);
            this.playSound(context);

            BlockState blockState = ((FireBlock) Blocks.FIRE).getDefaultState();
            context.getWorld().setBlockState(firePos, blockState, 11);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }
}
