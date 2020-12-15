package com.sorcery.spell;

import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.utils.BlockPattern;
import com.sorcery.utils.StructurePattern;
import net.minecraft.block.Block;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class BlockStructureSpell extends Spell
{

    private BlockPattern structurePattern;
    private Character clearCharacter;
    private Boolean clearNonAir;
    private Boolean centerPlayer;
    private Boolean clearCenter;

    public BlockStructureSpell(int arcanaCost, StructurePattern patternIn, Boolean centerPlayer, Boolean clearNonAir, Boolean clearCenter, @Nullable Character clearCharacter, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.structurePattern = patternIn.pattern;
        this.clearCharacter = clearCharacter;
        this.clearNonAir = clearNonAir;
        this.centerPlayer = centerPlayer;
        this.clearCenter = clearCenter;

    }

    public BlockStructureSpell(int arcanaCost, StructurePattern patternIn, Boolean centerPlayer, Boolean clearNonAir, Boolean clearCenter, @Nullable Character clearCharacter, int durationIn, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.structurePattern = patternIn.pattern;
        this.clearCharacter = clearCharacter;
        this.clearNonAir = clearNonAir;
        this.centerPlayer = centerPlayer;
        this.clearCenter = clearCenter;
        this.castType = CastType.DURATION;
        this.castDuration = durationIn;

    }


    // perform the final cast of the spell
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        BlockPos startPos = context.getPos();
        Direction direction = context.getPlayer().getHorizontalFacing();
        if (!this.centerPlayer)
        {
            startPos = context.getHitPos();
            if (!(direction == Direction.UP.UP || direction == Direction.DOWN))
            {
                direction = context.getHitFace().getOpposite();
            }
        }
        if (this.clearCharacter != null)
        {
            List<BlockPos> clearPoses = this.structurePattern.getBlockPositionsRotated(startPos, this.clearCharacter, direction);
            for (BlockPos pos : clearPoses)
            {
                context.getWorld().destroyBlock(pos, true, context.getPlayer(), 1);
                ParticleEffectPacket pkt1 = new ParticleEffectPacket(14, 17, new Vector3d(pos.getX(), pos.getY(), pos.getZ()).add(0.5, 0.5, 0.5), Vector3d.ZERO, 6, 0.2, 0.2, 10);
                PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
            }
        }
        if (this.clearCenter)
        {
            context.getWorld().destroyBlock(startPos, true, context.getPlayer(), 1);
            ParticleEffectPacket pkt1 = new ParticleEffectPacket(14, 17, new Vector3d(startPos.getX(), startPos.getY(), startPos.getZ()).add(0.5, 0.5, 0.5), Vector3d.ZERO, 6, 0.2, 0.2, 10);
            PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
        }
        for (Map.Entry<Block, Character> entry : this.structurePattern.getBlockMap().entrySet())
        {
            List<BlockPos> poses = this.structurePattern.getBlockPositionsRotated(startPos, entry.getKey(), direction);
            for (BlockPos pos : poses)
            {
                if (this.clearNonAir)
                {
                    context.getWorld().destroyBlock(pos, true, context.getPlayer(), 1);

                }

                if (context.getWorld().isAirBlock(pos))
                {
                    ParticleEffectPacket pkt1 = new ParticleEffectPacket(14, 17, new Vector3d(pos.getX(), pos.getY(), pos.getZ()).add(0.5, 0.5, 0.5), Vector3d.ZERO, 6, 0.2, 0.2, 10);
                    PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
                    context.getWorld().setBlockState(pos, entry.getKey().getDefaultState());
                }
            }
        }
        return ActionResultType.SUCCESS;
    }
}
