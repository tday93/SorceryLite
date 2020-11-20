package com.sorcery.spell;

import com.sorcery.Constants;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class BlockMoveSpell extends Spell
{
    private  Vector3i size;
    private Vector3i offset;
    private Vector3i translation;
    private ResourceLocation allowedBlocksTag;

    public BlockMoveSpell(int arcanaCost, Vector3i sizeIn, Vector3i offsetIn, Vector3i translationIn, ResourceLocation allowedBlocks, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(arcanaCost, tierIn, schoolIn);
        // doing this so that sizeIn can represent the total size
        this.size = new Vector3i(sizeIn.getX() - 1, sizeIn.getY() - 1, sizeIn.getZ() -1);
        this.finalSound = SoundEvents.BLOCK_STONE_PLACE;
        this.offset = offsetIn;
        this.translation = translationIn;
        this.allowedBlocksTag = allowedBlocks;

    }


    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        ITag<Block> tag = BlockTags.getCollection().getTagByID(this.allowedBlocksTag);
        Direction direction = context.getPlayer().getAdjustedHorizontalFacing();

        Vector3i rotSize = rotateVector(this.size, direction);
        Vector3i rotOffest = rotateVector(this.offset, direction);
        Vector3i rotTranslation = rotateVector(this.translation, direction);

        BlockPos startingPos = context.getHitPos().add(rotOffest);
        BlockPos endingPos = startingPos.add(rotSize);

        Stream<BlockPos> AOE = BlockPos.getAllInBox(startingPos, endingPos);

        Map<BlockPos, BlockState> startingState = new HashMap<>();

        // collect all blocks to be moved, and clear current position
        AOE.forEach((pos) ->
        {
            BlockState state = context.getWorld().getBlockState(pos);
            if (!state.getBlock().hasTileEntity(state) && tag.contains(state.getBlock()))
            {
                startingState.put(pos.toImmutable(), state);
                context.getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        });

        // move blocks into position
        for (Map.Entry<BlockPos, BlockState> entry : startingState.entrySet())
        {
            BlockPos transPos = entry.getKey().add(rotTranslation);
            if (context.getWorld().getBlockState(transPos).getBlock() == Blocks.AIR)
            {

                ParticleEffectPacket pkt1 = new ParticleEffectPacket(14, 17, new Vector3d(transPos.getX(), transPos.getY(), transPos.getZ()).add(0.5, 0.5, 0.5), Vector3d.ZERO, 6, 0.2, 0.2, 10);

                PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
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
