package com.sorcery.spell;

import net.minecraft.block.BlockState;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ToolType;

import java.util.ArrayList;
import java.util.List;

public class DigSpell extends Spell
{
    List<ToolType> tools;
    int speedMultiplier;

    public DigSpell(int arcanaCost, int speedMulitplier)
    {
        super(arcanaCost);
        this.castDuration = 100000;
        this.tools = new ArrayList<ToolType>();
        this.tools.add(ToolType.SHOVEL);
        this.speedMultiplier = speedMulitplier;
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
            float effectivenessMulti = 10f;
            BlockState state = context.getWorld().getBlockState(context.getHitPos());
            if (this.isEffective(state))
            {
                effectivenessMulti = 1.5f;
            }
            float baseTime = state.getBlockHardness(context.getWorld(), context.getHitPos());

            baseTime = ((baseTime * effectivenessMulti) / this.speedMultiplier) * 20;
            return (int)baseTime + 2;
        }
        return 100000;
    }

    public boolean isEffective(BlockState state)
    {
        for (ToolType tool : this.tools)
        {
            if (state.getBlock().isToolEffective(state, tool))
            {
                return true;
            }
        }
        return false;
    }
}
