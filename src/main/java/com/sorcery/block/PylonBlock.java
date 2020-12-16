package com.sorcery.block;

import com.sorcery.block.state.States;
import com.sorcery.tileentity.PylonTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
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

public class PylonBlock extends Block
{
    private static final Float hardness = 3.0F;
    private static final Float resistance = 6.0F;
    public static final BooleanProperty ACTIVE = States.ACTIVE;

    public static final Optional<VoxelShape> ACTIVE_SHAPE = Stream.of(
                    Block.makeCuboidShape(2, 0, 2, 14, 3, 14),
                    Block.makeCuboidShape(4, 3, 4, 12, 14, 12),
                    Block.makeCuboidShape(3, 14, 3, 13, 16, 13),
                    Block.makeCuboidShape(5, 16, 5, 11, 22, 11)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);});

    public static final Optional<VoxelShape> INACTIVE_SHAPE = Stream.of(
            Block.makeCuboidShape(2, 0, 2, 14, 3, 14),
            Block.makeCuboidShape(4, 3, 4, 12, 14, 12),
            Block.makeCuboidShape(3, 14, 3, 13, 16, 13),
            Block.makeCuboidShape(5, 14, 5, 11, 20, 11)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);});

    public PylonBlock()
    {
        super(Properties.create(Material.ROCK).hardnessAndResistance( hardness, resistance).sound(SoundType.STONE));
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
        return new PylonTile();
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }


    @Override
    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    public static void makeActive(World world, BlockState state, BlockPos pos)
    {
        world.setBlockState(pos, state.with(ACTIVE, Boolean.valueOf(true)));
    }

    public static void makeInActive(World world, BlockState state, BlockPos pos)
    {
        world.setBlockState(pos, state.with(ACTIVE, Boolean.valueOf(false)));
    }

    public static void setActivity(World world, BlockState state, BlockPos pos, Boolean active)
    {
        world.setBlockState(pos, state.with(ACTIVE, Boolean.valueOf(active)));
    }
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        if (state.get(ACTIVE))
        {
            return ACTIVE_SHAPE.get();
        } else {
            return INACTIVE_SHAPE.get();
        }
    }

}
