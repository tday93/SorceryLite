package com.sorcery.spell;

import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.spell.components.ElementalComponent;
import com.sorcery.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class ElementalNovaSpell extends Spell
{
    public int range;
    private ElementalComponent elementalComponent;
    private int part1;

    public ElementalNovaSpell(int arcanaCost, SpellTier tierIn, SpellSchool schoolIn, ElementalComponent elementalComponent, int range, int particleCollection)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.range = range;
        this.elementalComponent = elementalComponent;
        this.part1 = elementalComponent.getPrimaryParticleCollection();
    }

    public ActionResultType doCastFinal(SpellUseContext context)
    {
        List<Entity> entitiesInRange = Utils.entitiesInRange(context.getWorld(), context.getPos(), this.range, context.getPlayer());
        for (Entity entity : entitiesInRange)
        {
            if ( entity instanceof LivingEntity)
            {
                if (entity.getPosY() > context.getPlayer().getPosY() - 1 && entity.getPosY() < context.getPlayer().getPosY() + 1)
                {
                    this.elementalComponent.damageEntity((LivingEntity)entity, context);
                }
            }
        }
        ParticleEffectPacket pkt1 = new ParticleEffectPacket(1, this.part1, context.getPlayer().getPositionVec().add(0.5, 1, 0.5), Vector3d.ZERO, 60, 1, 0, 20);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
        ParticleEffectPacket pkt2 = new ParticleEffectPacket(1, this.part1, context.getPlayer().getPositionVec().add(0.5, 1.1, 0.5), Vector3d.ZERO, 60, 1, 0, 20);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt2);
        ParticleEffectPacket pkt3 = new ParticleEffectPacket(1, this.part1, context.getPlayer().getPositionVec().add(0.5, 0.9, 0.5), Vector3d.ZERO, 60, 1, 0, 20);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt3);
        ParticleEffectPacket pkt4 = new ParticleEffectPacket(1, this.part1, context.getPlayer().getPositionVec().add(0.5, 1, 0.5), Vector3d.ZERO, 60, 0.9, 0, 20);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt4);

        return ActionResultType.SUCCESS;
    }
}
