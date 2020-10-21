package com.sorcery.block;


import com.sorcery.block.state.States;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.stream.Stream;

public class MonolithTopBlock extends AbstractMonolithBlock
{
    private static Float hardness   = 3.0F;
    private static Float resistance = 6.0F;
    public static final BooleanProperty ACTIVE = States.ACTIVE;
    public static final int LIT_LIGHT_LEVEL = 7;

    public static final Optional<VoxelShape> SHAPE = Stream.of(
                    Block.makeCuboidShape(3, 11.999999999999993, 3, 13, 13.999999999999993, 13),
                    Block.makeCuboidShape(4, 13.999999999999993, 4, 12, 15.999999999999993, 12),
                    Block.makeCuboidShape(2, 8, 2, 14, 12, 14),
                    Block.makeCuboidShape(3, 0, 3, 13, 8, 13),
                    Block.makeCuboidShape(6, 6, 2, 10, 8, 3),
                    Block.makeCuboidShape(6, 6, 13, 10, 8, 14),
                    Block.makeCuboidShape(2, 6, 6, 3, 8, 10),
                    Block.makeCuboidShape(13, 6, 6, 14, 8, 10)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);});

    public MonolithTopBlock()
    {
        super();
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        Block block1 = worldIn.getBlockState(pos.down(1)).getBlock();
        Block block2 = worldIn.getBlockState(pos.down(2)).getBlock();
        if (block1 instanceof MonolithMiddleBlock)
        {
            worldIn.destroyBlock(pos.down(1), true, player);
        }
        if (block2 instanceof MonolithBottomBlock)
        {
            worldIn.destroyBlock(pos.down(2), true, player);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE.get();
    }
}
