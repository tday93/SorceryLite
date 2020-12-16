package com.sorcery.spell;

import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.spell.components.ElementalComponent;
import com.sorcery.spell.components.IDamageEntityComponent;
import com.sorcery.utils.Utils;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class ElementalRaySpell extends Spell
{
    private final ElementalComponent elementalComponent;
    private final int part1;
    private final int part2;

    public ElementalRaySpell(int arcanaCost, SpellTier tierIn, SpellSchool schoolIn, ElementalComponent elementalComponent)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.elementalComponent = elementalComponent;
        this.tickSound = SoundEvents.BLOCK_SNOW_HIT;
        this.castType = CastType.CHANNELED;
        this.castDuration = 100;
        this.part1 = elementalComponent.getPrimaryParticleCollection();
        this.part2 = elementalComponent.getSecondaryParticleCollection();
    }

    @Override
    public ActionResultType doCastPerTick(SpellUseContext context)
    {
        if (context.getCastingTicks() % 3 == 0)
        {
            this.doParticleEffects(context);
            this.playFinalSound(context);
            List<Entity> entList = Utils.entitiesInCone(context.getWorld(), context.getPos(), context.getPlayer(), context.getPlayer().getEyePosition(0), context.getPlayer().getLook(0), 8, 0.1);

            for ( Entity entity : entList)
            {
                if (entity instanceof LivingEntity)
                {
                    this.elementalComponent.damageEntity((LivingEntity)entity, context);
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void doParticleEffects(SpellUseContext context)
    {
        Vector3d loc = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLook(0), 1).add(0, -0.2, 0);
        Vector3d beamEnd = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLook(0), 8);

        ParticleEffectPacket pkt1 = new ParticleEffectPacket(13, this.part1, loc, beamEnd, 30, 1, 0.05, 3);
        ParticleEffectPacket pkt2 = new ParticleEffectPacket(15, this.part2, loc, beamEnd, 2, 0.2, -0.1, 10);

        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt2);
    }
}
