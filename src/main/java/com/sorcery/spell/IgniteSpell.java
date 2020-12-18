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
    public IgniteSpell(int costIn, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(costIn, tierIn, schoolIn);
        this.finalSound = SoundEvents.ITEM_FLINTANDSTEEL_USE;
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
            this.playFinalSound(context);

            BlockState blockState = Blocks.FIRE.getDefaultState();
            context.getWorld().setBlockState(firePos, blockState, 11);
            this.playFinalSound(context);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }
}
