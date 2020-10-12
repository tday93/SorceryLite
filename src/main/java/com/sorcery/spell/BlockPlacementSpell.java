package com.sorcery.spell;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;

public class BlockPlacementSpell extends Spell
{

    public BlockItem placementItem;
    public ItemStack placementItemStack;

    public BlockPlacementSpell(int arcanaCost, BlockItem placementItem)
    {
        super(arcanaCost);
        this.placementItem = placementItem;
        this.placementItemStack = new ItemStack(placementItem);
    }

    @Override
    public boolean allowCast(SpellUseContext context)
    {
        Integer index = context.getPlayer().inventory.findSlotMatchingUnusedItem(this.placementItemStack);
        if(index > -1)
        {
            if (context.getWorld().isAirBlock(context.getFacePos()))
            {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    // perform the final cast of the spell
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        Integer index = context.getPlayer().inventory.findSlotMatchingUnusedItem(this.placementItemStack);
        if(index > -1)
        {
            if (context.getWorld().isAirBlock(context.getFacePos()))
            {
                BlockState state = placementItem.getBlock().getDefaultState();
                context.getPlayer().inventory.getStackInSlot(index).shrink(1);
                context.getWorld().setBlockState(context.getFacePos(), state);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }
}
