package com.sorcery.spell;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.tileentity.AbstractMonolithTile;
import com.sorcery.tileentity.ArcanaStorageTile;
import com.sorcery.tileentity.CraftBlockTile;
import com.sorcery.utils.Utils;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class AdvancedCraftSpell extends Spell
{

    private int monolithRange = 7;
    private int arcanaDrainRate = 5;
    private int arcanaPerTickRate = 5;

    public AdvancedCraftSpell(int arcanaCost, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.castDuration = 100000;
        this.castType = CastType.DURATION;
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        Item catalystItem = context.getPlayer().getHeldItemOffhand().getItem();

        Item craftedItem = null;

        boolean flag = true;

        for (Item item : SpellUtil.getAdvancedComponents(catalystItem))
        {
            int itemSlot = context.getPlayer().inventory.findSlotMatchingUnusedItem(new ItemStack(item));
            if(itemSlot == -1)
            {
                flag = false;
            }
        }

        if (flag)
        {
            craftedItem = SpellUtil.getAdvancedResult(catalystItem);
            for (Item item : SpellUtil.getAdvancedComponents(catalystItem))
            {
                context.getPlayer().inventory.getStackInSlot(context.getPlayer().inventory.findSlotMatchingUnusedItem(new ItemStack(item))).shrink(1);
            }
            context.getPlayer().getHeldItemOffhand().shrink(1);
        }

        if(craftedItem != null)
        {
            Vector3d pos = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLookVec(), 1);
            ItemEntity wandEntity = new ItemEntity(context.getWorld(), pos.x, pos.y, pos.z, new ItemStack(craftedItem));
            context.getWorld().addEntity(wandEntity);
            ParticleEffectPacket pkt1 = new ParticleEffectPacket(2, 20, context.getPlayer().getEyePosition(0).add(0, -1, 0), Vector3d.ZERO, 20, 0.5, 0.2, 10);
            PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
        } else {
            this.doSpellFailureEffect(context);
        }


        return ActionResultType.SUCCESS;
    }

    // perform the per-tick action of the spell
    public ActionResultType doCastPerTick(SpellUseContext context)
    {
        // Drain arcana
        drainArcana(context, this.arcanaPerTickRate);
        // Crafting Enchanting Effects
        ParticleEffectPacket pkt1 = new ParticleEffectPacket(14, 20, context.getPlayer().getEyePosition(0).add(0, -1, 0), Vector3d.ZERO, 1, 0.5, 0.2, 10);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);

        // Arcana draining ala meditate
        Predicate<TileEntity> searchPredicate = Utils.getTESearchPredicate(AbstractMonolithTile.class, context.getPos(), this.monolithRange);

        List<TileEntity> allTEs = context.getWorld().loadedTileEntityList;

        for (TileEntity tile : Collections2.filter(allTEs, searchPredicate))
        {
            if (tile instanceof ArcanaStorageTile)
            {
                int arcanaExtracted = ((ArcanaStorageTile) tile).extractArcana(this.arcanaDrainRate);
                context.getArcanaSource().receiveArcana(arcanaExtracted, false);
            }
        }

        Vector3d loc = context.getPlayer().getEyePosition(0);
        ParticleEffectPacket pkt2 = new ParticleEffectPacket(12, 14, loc, Vector3d.ZERO, 4, 1, 2, 20);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt2);

        return ActionResultType.SUCCESS;
    }

    @Override
    public int getCastDuration(SpellUseContext context){
        return SpellUtil.getAdvancedDuration(context.getPlayer().getHeldItemOffhand().getItem());
    }

    @Override
    public boolean allowCast(SpellUseContext context)
    {
        Predicate<TileEntity> searchPredicate = Utils.getTESearchPredicate(CraftBlockTile.class, context.getPos(), 4);
        List<TileEntity> allTEs = context.getWorld().loadedTileEntityList;
        boolean flag = false;
        for (TileEntity tile : Collections2.filter(allTEs, searchPredicate))
        {
            if (((CraftBlockTile)tile).craftAreaActive())
            {
                flag = true;
            }
        }
        return flag;
    }

    // get arcana cost. override to define variable arcana costs
    @Override
    public int getArcanaCost(SpellUseContext context)
    {
        return SpellUtil.getAdvancedCost(context.getPlayer().getHeldItemOffhand().getItem());
    }
}
