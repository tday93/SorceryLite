package com.sorcery.spell;

import com.sorcery.Config;
import com.sorcery.Constants;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.particle.Particles;
import com.sorcery.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class PlantDeathSpell extends Spell
{
    private final double spellRange;

    public PlantDeathSpell(int costIn, SpellTier tierIn, SpellSchool schoolIn) {
        super(costIn, tierIn, schoolIn);
        this.spellRange = Config.SPELL_PLANT_DEATH_RANGE.get();
        this.finalSound = SoundEvents.BLOCK_CROP_BREAK;
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        ITag<Block> tag = BlockTags.getCollection().getTagByID(Constants.PLANT_DEATHABLE_TAG);

        // Extra Data
        World world = context.getWorld();
        AxisAlignedBB aaBB = new AxisAlignedBB(context.getPlayer().getPosition()).grow(spellRange);

        // List of all blocks in the affected area.
        Stream<BlockPos> AOE = BlockPos.getAllInBox(aaBB);

        playFinalSound(context);

        AOE.forEach((blockPosInAOE) ->
        {
            Block blockInAOE = world.getBlockState(blockPosInAOE).getBlock();

            if (tag.contains(blockInAOE))
            {
                if (blockInAOE == Blocks.GRASS_BLOCK)
                {
                    world.setBlockState(blockPosInAOE, Blocks.DIRT.getDefaultState(), 3);
                } else {
                    world.removeBlock(blockPosInAOE, false);
                }
                doDeathPoof(blockPosInAOE, context.getPlayer());
            }
        });

        return ActionResultType.SUCCESS;
    }

    public static void doDeathPoof(BlockPos pos, PlayerEntity player)
    {
        Vector3d loc = Utils.getVectorFromPos(pos).add(0.5, 1.1, 0.5);
        ParticleEffectPacket pkt = new ParticleEffectPacket(0, Particles.getSkullSmoke(), loc, loc, 2, 0.1, 0.4, 20);
        PacketHandler.sendToAllTrackingPlayer(player, pkt);
    }
}
