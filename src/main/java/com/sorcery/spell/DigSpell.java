package com.sorcery.spell;

import net.minecraft.block.BlockState;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;

public class DigSpell extends Spell
{
    public DigSpell(int arcanaCost)
    {
        super(arcanaCost);
        this.castDuration = 100000;
        this.castType = CastType.DURATION;
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context){

        if (context.hasHitPos())
        {
            BlockPos pos = context.getHitPos();
            System.out.println("Breaking block at:" + pos);
            context.getWorld().destroyBlock(context.getHitPos(), true);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    @Override
    public int getCastDuration(SpellUseContext context){
        if (context.hasHitPos())
        {
            BlockState state = context.getWorld().getBlockState(context.getHitPos());
            System.out.println("Block digging:" + state.getBlock());
            float baseTime = state.getBlockHardness(context.getWorld(), context.getHitPos());
            baseTime = baseTime * 1.5f * 20;
            baseTime = baseTime / 6f;
            System.out.println("cast duration: "+(int)baseTime);
            return (int)baseTime + 2;
        }
        return 100000;
    }
}
