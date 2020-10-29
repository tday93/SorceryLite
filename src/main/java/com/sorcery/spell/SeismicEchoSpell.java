package com.sorcery.spell;

import com.sorcery.Constants;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.stream.Stream;

public class SeismicEchoSpell extends Spell
{
    private int range;

    public SeismicEchoSpell(int arcanaCost)
    {
        super(arcanaCost);
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        ITag<Block> tag = BlockTags.getCollection().getTagByID(Constants.SEISMIC_ECHO_BLOCKS);
        AxisAlignedBB aaBB = new AxisAlignedBB(context.getPos(), context.getPos());
        aaBB.grow(range);

        Stream<BlockPos> AOE = BlockPos.getAllInBox(aaBB);

        AOE.forEach((blockPos) ->
        {
           if (tag.contains(context.getWorld().getBlockState(blockPos).getBlock()))
            {
                //TODO: do the thing
            }
        });

        return ActionResultType.SUCCESS;
    }
}
