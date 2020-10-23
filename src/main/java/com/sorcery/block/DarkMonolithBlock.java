package com.sorcery.block;

import com.sorcery.tileentity.DarkMonolithTile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class DarkMonolithBlock extends MonolithMiddleBlock
{
    public DarkMonolithBlock()
    {
        super();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new DarkMonolithTile();
    }
}
