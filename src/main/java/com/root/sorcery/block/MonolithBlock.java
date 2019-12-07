package com.root.sorcery.block;

import com.root.sorcery.block.state.States;
import com.root.sorcery.tileentity.MonolithTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MonolithBlock extends Block
{
    private static Float hardness   = 3.0F;
    private static Float resistance = 6.0F;
    public static final BooleanProperty ACTIVE = States.ACTIVE;

    public MonolithBlock()
    {
        super(Properties.create(Material.ROCK).hardnessAndResistance(hardness, resistance).sound(SoundType.STONE));
        this.setDefaultState(this.stateContainer.getBaseState().with(ACTIVE, Boolean.valueOf(false)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean isSolid(BlockState state)
    {
        return false;
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

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        System.out.println("created monolith tile");
        return new MonolithTile();
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
}
