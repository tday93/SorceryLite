package com.sorcery.spell;

import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.vector.Vector3d;

import java.util.HashMap;
import java.util.Map;

public class BlockTransmuteSpell extends Spell
{

    private Map<Block, Block> blockMap;

    public BlockTransmuteSpell(int arcanaCost, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.blockMap = getBlockMap();
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        Block source = context.getWorld().getBlockState(context.getHitPos()).getBlock();
        if (blockMap.containsKey(source))
        {
            Block newBlock = blockMap.get(source);
            context.getWorld().setBlockState(context.getHitPos(), newBlock.getDefaultState());
            ParticleEffectPacket pkt1 = new ParticleEffectPacket(14, 16, new Vector3d(context.getHitPos().getX(), context.getHitPos().getY(), context.getHitPos().getZ()).add(0.5, 0.5, 0.5), Vector3d.ZERO, 16, 0.3, 0.2, 10);

            PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
        }
        return ActionResultType.SUCCESS;
    }

    private static HashMap<Block, Block> getBlockMap()
    {
        HashMap<Block, Block> blocks = new HashMap();
        blocks.put(Blocks.STONE, Blocks.ANDESITE);
        blocks.put(Blocks.ANDESITE, Blocks.GRANITE);
        blocks.put(Blocks.GRANITE, Blocks.DIORITE);
        blocks.put(Blocks.DIORITE, Blocks.STONE);

        return blocks;
    }
}
