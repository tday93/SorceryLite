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

public class BlockPlacementSpell extends Spell
{

    public BlockItem placementItem;
    public ItemStack placementItemStack;

    public BlockPlacementSpell(int arcanaCost, BlockItem placementItem, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.sound = SoundEvents.BLOCK_STONE_PLACE;
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
                this.playSound(context);
                context.getWorld().setBlockState(context.getFacePos(), state, 3);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    //needs work still
    public void doParticleEffects(SpellUseContext context)
    {
        IParticleData blockPartData = new BlockParticleData(ParticleTypes.BLOCK, this.placementItem.getBlock().getDefaultState());
        ParticleEffectPacket pkt = new ParticleEffectPacket(5, blockPartData, Utils.getVectorFromPos(context.getFacePos()), context.getPlayer().getLookVec(), 20, 0.5, 0.2, 20);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt);
    }
}
