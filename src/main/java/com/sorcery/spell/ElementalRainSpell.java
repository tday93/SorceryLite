package com.sorcery.spell;

import com.sorcery.entity.ModEntity;
import com.sorcery.entity.SpellCarrierEntity;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.spell.components.ElementalComponent;
import com.sorcery.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class ElementalRainSpell extends Spell
{

    private final double horizontalRadius;
    private final double verticalRange;
    private final double maxRange;
    private final ElementalComponent elementalComponent;

    public ElementalRainSpell(int arcanaCost, SpellTier tierIn, SpellSchool schoolIn, ElementalComponent damageComponent, double horizontalRadius, double verticalRange)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.horizontalRadius = horizontalRadius;
        this.verticalRange = verticalRange;
        this.maxRange = Math.sqrt(Math.pow(this.horizontalRadius, 2) + Math.pow(this.verticalRange, 2));
        this.elementalComponent = damageComponent;
    }

    public ActionResultType doCastFinal(SpellUseContext context)
    {
        Spell spell = context.getSpell();
        context.setInherentSpell(spell);

        SpellCarrierEntity entity = new SpellCarrierEntity(ModEntity.SPELL_CARRIER, context.getWorld());
        entity.setLifeDuration(200);
        entity.setContext(context);
        entity.setPosition(context.getHitVec());
        context.getWorld().addEntity(entity);
        return ActionResultType.SUCCESS;
    }

    @Override
    public ActionResultType doCastPerTick(SpellUseContext context)
    {
        List<Entity> entitiesInRange = Utils.entitiesInRange(context.getWorld(), context.getHitPos(), (int)this.maxRange, context.getCarrierEntity());
        for (Entity entity : entitiesInRange)
        {
            if(entity instanceof LivingEntity)
            {
                if (Utils.horizontalDistance(context.getHitVec(), entity.getPositionVec()) <= this.horizontalRadius && entity.getPosY() < context.getHitVec().add(0, 8, 0).getY())
                {
                    this.elementalComponent.damageEntity((LivingEntity)entity, context);
                }
            }
        }

        ParticleEffectPacket pkt1 = new ParticleEffectPacket(17, this.elementalComponent.getPrimaryParticleCollection(), context.getHitVec().add(0, this.verticalRange, 0), new Vector3d(0, -0.8, 0), 30, 1, this.horizontalRadius, 20);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);

        return ActionResultType.SUCCESS;
    }
}
