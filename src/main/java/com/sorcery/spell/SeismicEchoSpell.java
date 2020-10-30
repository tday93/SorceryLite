package com.sorcery.spell;

import com.sorcery.Constants;
import com.sorcery.Sorcery;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import net.minecraft.block.Block;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.stream.Stream;

public class SeismicEchoSpell extends Spell
{
    private int range;

    public SeismicEchoSpell(int arcanaCost, int range)
    {
        super(arcanaCost);
        this.castType = CastType.DURATION;
        this.castDuration = 40;
        this.range = range;
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        ParticleEffectPacket pkt = new ParticleEffectPacket(2, 12, context.getPlayer().getEyePosition(1.0f), Vector3d.ZERO, 140, 1, 0.2, 20);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt);

        ITag<Block> tag = BlockTags.getCollection().getTagByID(Constants.SEISMIC_ECHO_BLOCKS);
        AxisAlignedBB aaBB = new AxisAlignedBB(context.getPos(), context.getPos()).grow(range);


        Stream<BlockPos> AOE = BlockPos.getAllInBox(aaBB);

        AOE.forEach((blockPos) ->
        {
           if (tag.contains(context.getWorld().getBlockState(blockPos).getBlock()))
            {
                ParticleEffectPacket pkt1 = new ParticleEffectPacket(7, 12, context.getPlayer().getEyePosition(1.0f), new Vector3d(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5), 4, 1, 1, 20);
                PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
            }
        });

        return ActionResultType.SUCCESS;
    }
}
