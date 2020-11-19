package com.sorcery.spell;

import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;

public class BlockPlacementSpell extends Spell
{

    public BlockItem placementItem;
    public ItemStack placementItemStack;

    public BlockPlacementSpell(int arcanaCost, BlockItem placementItem, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.finalSound = SoundEvents.BLOCK_STONE_PLACE;
        this.placementItem = placementItem;
        this.placementItemStack = new ItemStack(placementItem);
    }

    @Override
    public boolean allowCast(SpellUseContext context)
    {
        if (context.wasHitBlockAir())
        {
            return false;
        }
        Integer index = context.getPlayer().inventory.findSlotMatchingUnusedItem(this.placementItemStack);
        if(index > -1)
        {
            if (context.getWorld().isAirBlock(context.getFacePos()))
            {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    // perform the final cast of the spell
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        Integer index = context.getPlayer().inventory.findSlotMatchingUnusedItem(this.placementItemStack);
        if(index > -1)
        {
            if (context.getWorld().isAirBlock(context.getFacePos()))
            {
                BlockState state = placementItem.getBlock().getDefaultState();
                context.getPlayer().inventory.getStackInSlot(index).shrink(1);
                this.playFinalSound(context);
                this.doParticleEffects(context);
                context.getWorld().setBlockState(context.getFacePos(), state, 3);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    //needs work still
    public void doParticleEffects(SpellUseContext context)
    {
        ParticleEffectPacket pkt = new ParticleEffectPacket(14, 17, Utils.getVectorFromPos(context.getFacePos()).add(0.5, 0.5, 0.5), Vector3d.ZERO, 10, 0.1, 0.2, 20);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt);
    }
}
