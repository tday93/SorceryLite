package com.sorcery.spell;

import com.sorcery.entity.ModEntity;
import com.sorcery.entity.SpellCarrierEntity;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.spell.components.ElementalComponent;
import com.sorcery.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;

import java.util.List;

public class ElementalBurstSpell extends Spell
{
    private ElementalComponent elementalComponent;
    private int burstDistance;
    private int burstRadius;
    private int part1;


    public ElementalBurstSpell(int arcanaCost, SpellTier tierIn, SpellSchool schoolIn, ElementalComponent elementalComponentIn, int burstDistance, int burstRadius)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.elementalComponent = elementalComponentIn;
        this.burstDistance = burstDistance;
        this.burstRadius = burstRadius;
        this.part1 = elementalComponentIn.getPrimaryParticleCollection();
    }

    public ActionResultType doCastFinal(SpellUseContext context)
    {
        PlayerEntity player = context.getPlayer();
        BlockRayTraceResult blockRTR = Utils.blockAlongRay(player.getEyePosition(1.0f), player.getLookVec(), burstDistance, context.getWorld(), player);

        // if raytrace hits a block, burst at that block, otherwise burst at max range.
        if (blockRTR.getType() == RayTraceResult.Type.BLOCK)
        {

            Vector3d baseVec = blockRTR.getHitVec();
            Direction hitDir = blockRTR.getFace();

            double yOffset = 0.01;
            // If looking up at block
            if (hitDir.getIndex() == 0)
                yOffset = -2.0;

            Vector3d finalVec =  baseVec.add(hitDir.getXOffset(), yOffset, hitDir.getZOffset());
            // Explosion
            context.getWorld().createExplosion(null, finalVec.x, finalVec.y, finalVec.z, this.burstRadius, Explosion.Mode.BREAK);
            // Extra Particles
            ParticleEffectPacket pkt1 = new ParticleEffectPacket(14, this.part1, finalVec, Vector3d.ZERO, 200, 1, 0, 20);
            PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
            ParticleEffectPacket pkt2 = new ParticleEffectPacket(14, this.part1, finalVec, Vector3d.ZERO, 200, 0.8, 0, 20);
            PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt2);
            // Elemental Effect
            List<Entity> entitiesInRange = Utils.entitiesInRange(context.getWorld(), context.getPos(), this.burstRadius, context.getPlayer());
            for (Entity entity : entitiesInRange)
            {
                if ( entity instanceof LivingEntity)
                {
                    this.elementalComponent.damageEntity((LivingEntity)entity, context);
                }
            }
            return ActionResultType.SUCCESS;
        }
        else
        {
            Vector3d finalVec = Utils.nBlocksAlongVector(player.getEyePosition(1.0f), player.getLookVec(), (float) burstDistance);
            // Explosion
            context.getWorld().createExplosion(null, finalVec.x, finalVec.y, finalVec.z, this.burstRadius, Explosion.Mode.BREAK);
            // Extra Particles
            ParticleEffectPacket pkt1 = new ParticleEffectPacket(14, this.part1, finalVec, Vector3d.ZERO, 200, 1, 0, 20);
            PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
            ParticleEffectPacket pkt2 = new ParticleEffectPacket(14, this.part1, finalVec, Vector3d.ZERO, 200, 0.8, 0, 20);
            PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt2);
            // Elemental Effect
            List<Entity> entitiesInRange = Utils.entitiesInRange(context.getWorld(), context.getPos(), this.burstRadius, context.getPlayer());
            for (Entity entity : entitiesInRange)
            {
                if ( entity instanceof LivingEntity)
                {
                    this.elementalComponent.damageEntity((LivingEntity)entity, context);
                }
            }
            return ActionResultType.SUCCESS;
        }

    }
}
