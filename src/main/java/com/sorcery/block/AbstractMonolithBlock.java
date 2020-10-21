package com.sorcery.block;

import com.sorcery.block.state.States;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class AbstractMonolithBlock extends Block
{
    private static Float hardness   = 3.0F;
    private static Float resistance = 6.0F;
    public static final BooleanProperty ACTIVE = States.ACTIVE;
    public static final int LIT_LIGHT_LEVEL = 7;

    public AbstractMonolithBlock()
    {
        super(Properties.create(Material.ROCK).hardnessAndResistance(hardness, resistance).sound(SoundType.STONE));
        this.setDefaultState(this.stateContainer.getBaseState().with(ACTIVE, Boolean.FALSE));
    }


    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    public static void setActivity(World world, BlockState state, BlockPos pos, Boolean active)
    {
        if (state.getBlock() instanceof AbstractMonolithBlock)
        {
            world.setBlockState(pos, state.with(ACTIVE, Boolean.valueOf(active)), 3);
        }
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return blockState.get(ACTIVE) ? 15 : 0;
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side)
    {
        return blockState.get(ACTIVE) ? 15 : 0;
    }

}
