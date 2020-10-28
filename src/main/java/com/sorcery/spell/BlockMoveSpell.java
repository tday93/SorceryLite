package com.sorcery.spell;

import com.sorcery.Sorcery;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BlockMoveSpell extends Spell
{
    private  Vector3i size;
    private Vector3i offset;
    private Vector3i translation;

    public BlockMoveSpell(int arcanaCost, Vector3i sizeIn, Vector3i offsetIn, Vector3i translationIn)
    {
        super(arcanaCost);
        this.size = sizeIn;
        this.offset = offsetIn;
        this.translation = translationIn;
    }


    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        Direction direction = context.getPlayer().getAdjustedHorizontalFacing();

        Vector3i rotSize = rotateVector(this.size, direction);
        Vector3i rotOffest = rotateVector(this.offset, direction);
        Vector3i rotTranslation = rotateVector(this.translation, direction);

        Sorcery.getLogger().debug("Hit Block: " + context.getHitPos());
        BlockPos startingPos = context.getHitPos().add(rotOffest);
        BlockPos endingPos = startingPos.add(rotSize);
        Sorcery.getLogger().debug("Starting Pos: " + startingPos);
        Sorcery.getLogger().debug("Ending Pos: " + endingPos);

        Stream<BlockPos> AOE = BlockPos.getAllInBox(startingPos, endingPos);

        Map<BlockPos, BlockState> startingState = new HashMap<>();

        // collect all blocks to be moved, and clear current position
        AOE.forEach((pos ->
        {
            BlockState state = context.getWorld().getBlockState(pos);
            if (!state.getBlock().hasTileEntity(state))
            {
                startingState.put(pos, state);
                context.getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }));
        // move blocks into position
        for (Map.Entry<BlockPos, BlockState> entry : startingState.entrySet())
        {
            BlockPos transPos = entry.getKey().add(rotTranslation);
            if (context.getWorld().getBlockState(transPos).getBlock() == Blocks.AIR)
            {
                context.getWorld().setBlockState(transPos, entry.getValue());
            }
        }
        return ActionResultType.SUCCESS;
    }

    private Vector3i rotateVector(Vector3i vector, Direction direction)
    {
        // -Z
        if (direction == Direction.NORTH)
        {
            return new Vector3i(vector.getX() * -1, vector.getY(), vector.getZ() * -1);
        }
        // +Z
        if (direction == Direction.SOUTH)
        {
            return vector;
        }
        // +X
        if (direction == Direction.EAST)
        {
            return new Vector3i(vector.getZ() * -1, vector.getY(), vector.getX());
        }
        // -X
        if (direction == Direction.WEST)
        {
            return new Vector3i(vector.getZ(), vector.getY(), vector.getX() * -1);
        }
        return vector;
    }
}
