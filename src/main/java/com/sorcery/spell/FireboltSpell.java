package com.sorcery.spell;

import com.sorcery.Config;
import com.sorcery.entity.ModEntity;
import com.sorcery.entity.projectile.FireboltEntity;
import com.sorcery.utils.Utils;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class FireboltSpell extends Spell
{
    private final int velocity;

    public FireboltSpell(int costIn, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(costIn, tierIn, schoolIn);
        this.finalSound = SoundEvents.ITEM_FIRECHARGE_USE;
        this.velocity = 3;
    }


    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        this.doParticleEffects(context);
        this.playFinalSound(context);
        World world = context.getWorld();
        FireboltEntity entity = new FireboltEntity(ModEntity.FIREBOLT, world);
        entity.setDamageAndDuration(Config.SPELL_FIREBOLT_DAMAGE.get(), Config.SPELL_FIREBOLT_FIRE_DURATION.get());
        Vector3d eyePos = context.getPlayer().getEyePosition(1);
        Vector3d playerLook = context.getPlayer().getLookVec();
        Vector3d entityPos = Utils.nBlocksAlongVector(eyePos, playerLook, 2.0f);
        entity.setPosition(entityPos.x, entityPos.y, entityPos.z);
        entity.addVelocity(playerLook.x * velocity, playerLook.y * velocity, playerLook.z * velocity);
        entity.accelerationY = -0.1;
        world.addEntity(entity);
        return ActionResultType.SUCCESS;
    }
}
