package com.sorcery.block;

import com.sorcery.block.state.States;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class AbstractMonolithBlock extends Block
{
    private static final Float hardness   = 3.0F;
    private static final Float resistance = 6.0F;
    public static final BooleanProperty ACTIVE = States.ACTIVE;
    public static final IntegerProperty ARCANA_FILL = States.ARCANA_FILL;

    public AbstractMonolithBlock()
    {
        super(Properties.create(Material.ROCK).hardnessAndResistance(hardness, resistance).sound(SoundType.STONE).setLightLevel((state) -> {return state.get(ARCANA_FILL);}));
        this.setDefaultState(this.stateContainer.getBaseState().with(ACTIVE, Boolean.FALSE).with(ARCANA_FILL, 0));
    }


    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE).add(ARCANA_FILL);
    }

    public static void setArcanaFill(World world, BlockState state, BlockPos pos, int arcanaFill)
    {
        if (state.getBlock() instanceof AbstractMonolithBlock)
        {
            world.setBlockState(pos, state.with(ARCANA_FILL, arcanaFill).with(ACTIVE, (arcanaFill > 2)));
        }
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return blockState.get(ACTIVE) ? blockState.get(ARCANA_FILL) : 0;
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side)
    {
        return blockState.get(ACTIVE) ? blockState.get(ARCANA_FILL) : 0;
    }

}
