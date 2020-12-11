package com.sorcery.spell;

import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.utils.BlockPattern;
import com.sorcery.utils.StructurePattern;
import net.minecraft.block.Block;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;
import java.util.Map;

public class BlockStructureSpell extends Spell
{

    private BlockPattern structurePattern;

    public BlockStructureSpell(int arcanaCost, StructurePattern patternIn, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.structurePattern = patternIn.pattern;
    }

    // perform the final cast of the spell
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        for (Map.Entry<Block, Character> entry : this.structurePattern.getBlockMap().entrySet())
        {
            List<BlockPos> poses = this.structurePattern.getBlockPositions(context.getPos(), entry.getKey());
            for (BlockPos pos : poses)
            {
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
