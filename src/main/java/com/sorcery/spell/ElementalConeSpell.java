package com.sorcery.spell;

import com.sorcery.Config;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.spell.components.ElementalComponent;
import com.sorcery.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class ElementalConeSpell extends Spell
{
    private final ElementalComponent elementalComponent;
    private final int part1;
    private final int part2;

    public ElementalConeSpell(SpellTier tierIn, SpellSchool schoolIn, ElementalComponent elementalComponent)
    {
        super(Config.SPELL_COMBUSTION_COST.get(), tierIn, schoolIn);
        this.castDuration = Config.SPELL_COMBUSTION_CAST_DURATION.get();
        this.castType = CastType.CHANNELED;
        this.tickSound = SoundEvents.ITEM_FIRECHARGE_USE;
        this.finalSound = SoundEvents.ITEM_FIRECHARGE_USE;
        this.castFrequency = 2;
        this.elementalComponent = elementalComponent;
        this.part1 = elementalComponent.getPrimaryParticleCollection();
        this.part2 = elementalComponent.getSecondaryParticleCollection();
    }

    @Override
    public ActionResultType doCastPerTick(SpellUseContext context)
    {
        this.doParticleEffects(context);
        this.playFinalSound(context);
        List<Entity> entList = Utils.entitiesInCone(context.getWorld(), context.getPos(), context.getPlayer(), context.getPlayer().getEyePosition(1), context.getPlayer().getLook(1), 8, 0.2);

        for ( Entity entity : entList)
        {
            if (entity instanceof LivingEntity)
            {
                this.elementalComponent.damageEntity((LivingEntity) entity, context);
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void doParticleEffects(SpellUseContext context)
    {
        Vector3d loc = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLook(0), 1f).add(0, -.1, 0);
        Vector3d look = context.getPlayer().getLookVec();

        ParticleEffectPacket pkt1 = new ParticleEffectPacket(3, this.part1, loc, look, 40, 0.5, 0.2, 20);
        ParticleEffectPacket pkt2 = new ParticleEffectPacket(3, this.part2, loc, look, 10, 0.3, 0.2, 20);

        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt2);
    }
}
