package com.sorcery.block;

import com.sorcery.block.state.States;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.stream.Stream;

public class MonolithBottomBlock extends Block
{
    private static Float hardness   = 3.0F;
    private static Float resistance = 6.0F;
    public static final BooleanProperty ACTIVE = States.ACTIVE;
    public static final int LIT_LIGHT_LEVEL = 7;

    public static final Optional<VoxelShape> SHAPE = Stream.of(
            Block.makeCuboidShape(3, 4, 3, 13, 8, 13),
            Block.makeCuboidShape(3, 8, 3, 13, 16, 13),
            Block.makeCuboidShape(6, 4, 1, 10, 10, 3),
            Block.makeCuboidShape(6, 4, 13, 10, 10, 15),
            Block.makeCuboidShape(1, 4, 6, 3, 10, 10),
            Block.makeCuboidShape(13, 4, 6, 15, 10, 10),
            Block.makeCuboidShape(0, 0, 0, 16, 4, 16)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);});

    public MonolithBottomBlock()
    {
        super(Properties.create(Material.ROCK).hardnessAndResistance(hardness, resistance).sound(SoundType.STONE));
        this.setDefaultState(this.stateContainer.getBaseState().with(ACTIVE, Boolean.valueOf(false)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    public static void setActivity(World world, BlockState state, BlockPos pos, Boolean active)
    {
        if (state.getBlock() instanceof MonolithBottomBlock)
        {
            world.setBlockState(pos, state.with(ACTIVE, Boolean.valueOf(active)), 3);
        }
    }
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        Block block1 = worldIn.getBlockState(pos.up(1)).getBlock();
        Block block2 = worldIn.getBlockState(pos.up(2)).getBlock();
        if (block1 instanceof MonolithBlock)
        {
            worldIn.destroyBlock(pos.up(1), true, player);
        }
        if (block2 instanceof MonolithTopBlock)
        {
            worldIn.destroyBlock(pos.up(2), true, player);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE.get();
    }
}