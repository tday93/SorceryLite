package com.sorcery.block;

import com.sorcery.block.state.States;
import com.sorcery.tileentity.ChiseledMonolithTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MonolithMiddleBlock extends AbstractMonolithBlock
{
    private static final Float hardness   = 3.0F;
    private static final Float resistance = 6.0F;
    public static final BooleanProperty ACTIVE = States.ACTIVE;
    public static final int LIT_LIGHT_LEVEL = 7;

    public static final VoxelShape SHAPE = Block.makeCuboidShape(3, 0, 3, 13, 16, 13);

    public MonolithMiddleBlock()
    {
        super();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        Block block1 = worldIn.getBlockState(pos.up(1)).getBlock();
        Block block2 = worldIn.getBlockState(pos.down(1)).getBlock();
        if (block1 instanceof MonolithTopBlock)
        {
            worldIn.destroyBlock(pos.up(1), true, player);
        }
        if (block2 instanceof MonolithBottomBlock)
        {
            worldIn.destroyBlock(pos.down(1), true, player);
        }
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new ChiseledMonolithTile();
    }

    public static void setActivity(World world, BlockState state, BlockPos pos, Boolean active)
    {
        world.setBlockState(pos, state.with(ACTIVE, active), 3);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }
}
