package com.sorcery.block;

import com.sorcery.tileentity.ChiseledMonolithTile;
import com.sorcery.tileentity.CraftBlockTile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class CraftBlock extends AbstractMonolithBlock
{
    public CraftBlock()
    {
        super();
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new CraftBlockTile();
    }
}
