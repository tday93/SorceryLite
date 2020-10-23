package com.sorcery.block;

import com.sorcery.tileentity.LapisMonolithTile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LapisMonolithBlock extends MonolithMiddleBlock
{
    public LapisMonolithBlock()
    {
        super();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new LapisMonolithTile();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemStack = player.getHeldItem(handIn);
        if (itemStack.getItem() == Items.LAPIS_LAZULI)
        {
            TileEntity monolithTile = worldIn.getTileEntity(pos);
            if (monolithTile instanceof LapisMonolithTile)
            {
                if (((LapisMonolithTile)monolithTile).canAcceptLapis())
                {
                    if (!worldIn.isRemote())
                    {
                        itemStack.shrink(1);
                        ((LapisMonolithTile)monolithTile).acceptLapis();
                    }
                    return ActionResultType.SUCCESS;
                } else {
                   return ActionResultType.FAIL;
                }
            }
        }
            return ActionResultType.FAIL;
    }

}
