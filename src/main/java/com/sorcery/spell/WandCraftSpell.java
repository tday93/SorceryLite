package com.sorcery.spell;

import com.sorcery.item.ModItem;
import com.sorcery.item.WandItem;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.utils.Utils;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.swing.text.html.parser.Entity;

public class WandCraftSpell extends Spell
{
    public WandCraftSpell(int arcanaCost, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.castDuration = 100000;
        this.castType = CastType.DURATION;
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        Item catalystItem = context.getPlayer().getHeldItemOffhand().getItem();
        WandItem wandCore = (WandItem)context.getPlayer().getHeldItemMainhand().getItem();

        WandItem craftedWand = SpellUtil.getWand(wandCore, catalystItem);


        if (craftedWand == null)
        {
            if(context.getWorld().getDimensionKey() == World.THE_NETHER)
            {
                if (catalystItem == Items.GOLD_INGOT && wandCore == ModItem.WAND_CORE_JOURNEYMAN.get())
                {
                    craftedWand = (WandItem)ModItem.WAND_CORE_SORCERER.get();
                }

            }
        }

        if(craftedWand != null)
        {
            context.getPlayer().getHeldItemOffhand().shrink(1);
            context.getPlayer().getHeldItemMainhand().shrink(1);
            Vector3d pos = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLookVec(), 1);
            ItemEntity wandEntity = new ItemEntity(context.getWorld(), pos.x, pos.y, pos.z, new ItemStack(craftedWand));
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
        ParticleEffectPacket pkt1 = new ParticleEffectPacket(14, 20, context.getPlayer().getEyePosition(0).add(0, -1, 0), Vector3d.ZERO, 1, 0.5, 0.2, 10);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
        return ActionResultType.SUCCESS;
    }

    @Override
    public int getCastDuration(SpellUseContext context){
        return (this.spellTier.tierInt + 1) * 20;
    }

}
