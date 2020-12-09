package com.sorcery.spell;

import com.sorcery.entity.ModEntity;
import com.sorcery.entity.SpellCarrierEntity;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class ElementalRainSpell extends Spell
{

    private double horizontalRadius;
    private double verticalRange;
    private double maxRange;
    private Effect rainEffect;
    private int particleCollection;

    public ElementalRainSpell(int arcanaCost, Effect effectIn, double horizontalRadius, double verticalRange, int particleCollection, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.horizontalRadius = horizontalRadius;
        this.verticalRange = verticalRange;
        this.maxRange = Math.sqrt(Math.pow(this.horizontalRadius, 2) + Math.pow(this.verticalRange, 2));
        this.rainEffect = effectIn;
        this.particleCollection = particleCollection;
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
                    EffectInstance potionEffect = new EffectInstance(this.rainEffect, 10, 1, false, true);
                    ((LivingEntity)entity).addPotionEffect(potionEffect);
                    entity.attackEntityFrom(DamageSource.GENERIC, 1);
                }
            }
        }

        ParticleEffectPacket pkt1 = new ParticleEffectPacket(17, this.particleCollection, context.getHitVec().add(0, this.verticalRange, 0), new Vector3d(0, -0.8, 0), 30, 1, this.horizontalRadius, 40);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);

        return ActionResultType.SUCCESS;
    }
}
