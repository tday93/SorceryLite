package com.sorcery.spell;

import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.vector.Vector3d;

public class MendingSpell extends Spell
{
    private int repairAmount;

    public MendingSpell(int arcanaCost, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.castType = CastType.DURATION;
        this.castDuration = 20;
        this.repairAmount = 8;
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        this.doParticleEffects(context);
        ItemStack offhandItem = context.getPlayer().getHeldItemOffhand();
        if (offhandItem.isDamageable())
        {
            offhandItem.setDamage(Math.max(offhandItem.getDamage() - this.repairAmount, 0));
            return ActionResultType.SUCCESS;
        }

        for (ItemStack armorItem : context.getPlayer().getArmorInventoryList())
        {
            if (armorItem.isDamageable())
            {
                armorItem.setDamage(Math.max(armorItem.getDamage() - this.repairAmount/4, 0));
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void doParticleEffects(SpellUseContext context)
    {

        Vector3d loc = context.getPlayer().getPositionVec().add(0,1, 0);
        if (context.wasEntityTargeted())
        {
            loc = context.getTargetEntity().getPositionVec().add(0.5, 0.5, 0.5);
        }
        ParticleEffectPacket pkt = new ParticleEffectPacket(2, 25, loc, Vector3d.ZERO, 20, 0.2, 0.2, 20);

        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt);
    }
}
