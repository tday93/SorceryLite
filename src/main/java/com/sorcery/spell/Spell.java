package com.sorcery.spell;

import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.utils.Utils;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.registries.ForgeRegistryEntry;


public class Spell extends ForgeRegistryEntry<Spell>
{
    public int arcanaCost;
    public int castDuration = 1;
    public SoundEvent finalSound = SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP;
    public SoundEvent tickSound = null;
    public SoundCategory soundCategory = SoundCategory.BLOCKS;
    public CastType castType = CastType.INSTANT;
    public int castFrequency = 1;

    public SpellSchool spellSchool = SpellSchool.EVOCATION;
    public SpellTier spellTier = SpellTier.INITIATE;

    public Spell(int arcanaCost)
    {
        this.arcanaCost = arcanaCost;
    }

    public Spell(int arcanaCost, SpellTier tierIn, SpellSchool schoolIn)
    {
       this.arcanaCost = arcanaCost;
       this.spellTier = tierIn;
       this.spellSchool = schoolIn;
    }

    /**
     * Called when a spell "finishes"
     * @param context
     * @return
     */
    public ActionResultType castFinal(SpellUseContext context)
    {
        if (this.castType == CastType.CHANNELED)
        {
            return ActionResultType.SUCCESS;
        }

        if (!preCast(context))
        {
            doSpellFailureEffect(context);
            return ActionResultType.SUCCESS;
        }

        if (!context.getWorld().isRemote())
        {
            doCastFinal(context);
            return ActionResultType.SUCCESS;
        } else {
            doCastFinalClient(context);
            return ActionResultType.SUCCESS;
        }
    }

    /**
     * Called every tick a spell is being cast.
     * Spell's castFrequency defines how many ticks between procs.
     * @param context
     * @return
     */
    public ActionResultType castPerTick(SpellUseContext context)
    {
        if (!(context.getWorld().getGameTime() % this.castFrequency == 0))
        {
            return ActionResultType.PASS;
        }

        if( this.castType == CastType.CHANNELED)
        {
            if (!preCast(context))
            {
                return ActionResultType.FAIL;
            }
        }

        if (!context.getWorld().isRemote())
        {
            doCastPerTick(context);
            return ActionResultType.SUCCESS;
        } else {
            doCastPerTickClient(context);
            return ActionResultType.SUCCESS;
        }
    }

    /**
     * Checks to see if the spell can be cast.
     * @param context
     * @return
     */
    public boolean preCast(SpellUseContext context)
    {
        if (!allowCast(context))
        {
            return false;
        }

        return drainArcana(context, this.getArcanaCost(context));
    }

    // check to see if cast will be allowed
    public boolean allowCast(SpellUseContext context)
    {
        return true;
    }

    // get arcana cost. override to define variable arcana costs
    public int getArcanaCost(SpellUseContext context)
    {
        return this.arcanaCost;
    }

    // perform the final cast of the spell
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        this.playFinalSound(context);
        return ActionResultType.SUCCESS;
    }

    // final cast of spell client-only actions
    public void doCastFinalClient(SpellUseContext context)
    {
        this.playFinalSound(context);
    }

    // perform the per-tick action of the spell
    public ActionResultType doCastPerTick(SpellUseContext context)
    {
        this.playTickSound(context);
        return ActionResultType.SUCCESS;
    }

    public void doCastPerTickClient(SpellUseContext context)
    {
        this.playTickSound(context);
    }

    // Send packets to play particle effects
    public void doParticleEffects(SpellUseContext context)
    {

    }

    // Play sound effects, override if you want different behavior
    public void playFinalSound(SpellUseContext context)
    {
        context.getWorld().playSound(context.getPlayer(), context.getPos(), this.finalSound, this.soundCategory, 1.0F, context.getWorld().rand.nextFloat() * 0.4F + 0.8F);
    }

    public void playTickSound(SpellUseContext context)
    {
        if (this.tickSound != null)
        {
            context.getWorld().playSound(context.getPlayer(), context.getPos(), this.tickSound, this.soundCategory, 1.0F, context.getWorld().rand.nextFloat() * 0.4F + 0.8F);
        }
    }

    // Drain Arcana from caster, return true if successful
    public boolean drainArcana(SpellUseContext context, int arcanaCost)
    {
        if (!context.getWorld().isRemote())
        {
            // Server side, check + drain
            if (context.getArcanaSource().getArcanaStored() >= arcanaCost)
            {
                context.getArcanaSource().extractArcana(arcanaCost, false);
                return true;
            } else {
                return false;
            }
        } else {
            // Client side, just check
            return context.getArcanaSource().getArcanaStored() >= arcanaCost;
        }
    }

    public int getCastDuration(){
        return this.castDuration;
    }

    public int getCastDuration(SpellUseContext context){
        return this.castDuration;
    }

    public CastType getCastType()
    {
        return this.castType;
    }

    protected double getCastPercent(SpellUseContext context)
    {
        if (this.castType == CastType.INSTANT)
        {
            return 1;
        } else {
            return (double)context.getCastingTicks() / (double)this.castDuration;
        }
    }

    protected void doSpellFailureEffect(SpellUseContext context)
    {

        //context.getWorld().playSound(context.getPlayer(), context.getPos(), SoundEvents.BLOCK_CONDUIT_DEACTIVATE, this.soundCategory, 1.0F, context.getWorld().rand.nextFloat() * 0.4F + 0.8F);

        if (!context.getWorld().isRemote())
        {
            Vector3d loc = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLook(0), 0.5f).add(0, 0, 0);
            Vector3d look = context.getPlayer().getLookVec();
            ParticleEffectPacket pkt1 = new ParticleEffectPacket(3, 0, loc, look, 20, 1, 0.1, 20);
            PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
        }
    }


}
