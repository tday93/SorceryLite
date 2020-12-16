package com.sorcery.spell;

import com.sorcery.Sorcery;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.ToolType;

import java.util.ArrayList;
import java.util.List;

public class DigSpell extends Spell
{
    List<ToolType> tools;
    int speedMultiplier;

    public DigSpell(int arcanaCost, SpellTier tierIn, SpellSchool schoolIn, int speedMulitplier, List<ToolType> toolsIn)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.castDuration = 100000;
        this.tools = toolsIn;
        this.tickSound = SoundEvents.BLOCK_STONE_HIT;
        this.speedMultiplier = speedMulitplier;
        this.castType = CastType.DURATION;
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context){

        if (context.hasHitPos())
        {
            BlockPos pos = context.getHitPos();
            Sorcery.getLogger().debug("Breaking block at:" + pos);
            context.getWorld().destroyBlock(context.getHitPos(), true);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    // perform the per-tick action of the spell
    public ActionResultType doCastPerTick(SpellUseContext context)
    {
        if (!context.getWorld().isRemote())
        {
            Vector3d loc = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLook(0), 1).add(0, -0.2, 0);
            Vector3d hitLoc = context.getHitVec();
            ParticleEffectPacket pkt1 = new ParticleEffectPacket(13, 13, loc, hitLoc, 30, 1, 0.05, 1);
            PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public int getCastDuration(SpellUseContext context){
        if (context.hasHitPos())
        {
            float effectivenessMulti = 10f;
            BlockState state = context.getWorld().getBlockState(context.getHitPos());
            if (this.isEffective(state))
            {
                effectivenessMulti = 1.5f;
            }
            float baseTime = state.getBlockHardness(context.getWorld(), context.getHitPos());

            baseTime = ((baseTime * effectivenessMulti) / this.speedMultiplier) * 20;
            return (int)baseTime + 2;
        }
        return 100000;
    }

    public boolean isEffective(BlockState state)
    {
        for (ToolType tool : this.tools)
        {
            if (state.getBlock().isToolEffective(state, tool))
            {
                return true;
            }
        }
        return false;
    }
}
