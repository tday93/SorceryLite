package com.sorcery.tileentity;

import com.sorcery.Sorcery;
import com.sorcery.particle.ParticleEffectContext;
import com.sorcery.particle.ParticleEffects;
import com.sorcery.particle.Particles;
import com.sorcery.utils.MonolithPattern;
import com.sorcery.utils.Utils;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.vector.Vector3d;

import java.util.HashMap;
import java.util.Map;

public class LunarMonolithTile extends AbstractMonolithTile implements ITickableTileEntity
{

    protected int arcanaPerRegen;
    protected int ticksPerRegen = 2;

    protected boolean active = false;

    private double cycleMultiplier = 0.5;

    private final Map<Integer, Double> phaseMap = new HashMap();


    public LunarMonolithTile(){
        super(ModTile.LUNAR_MONOLITH_TILE, 1000, MonolithPattern.LUNAR);
        this.arcanaStorage.extractArcana(1000, false);
        this.arcanaPerRegen = 10;
        this.phaseMap.put(0, 3d);
        this.phaseMap.put(1, 2d);
        this.phaseMap.put(2, 1d);
        this.phaseMap.put(3, 0.75d);
        this.phaseMap.put(4, 0.5d);
        this.phaseMap.put(5, 0.75d);
        this.phaseMap.put(6, 1d);
        this.phaseMap.put(7, 2d);
    }

    @Override
    public void tick()
    {
        long worldTicks = this.getOffsetWorldTicks();
        if (!this.world.isRemote())
        {
            // Arcana generation
            if (worldTicks % ticksPerRegen == 0 && !this.world.isDaytime() && !this.beingInterfered())
            {
                this.receiveArcana((int)((double)this.arcanaPerRegen * this.cycleMultiplier));
            }
            // Set generation amounts
            if (worldTicks % 20 == 0)
            {
                if (!this.world.isDaytime())
                {
                    int moonPhase = this.world.getDimensionType().getMoonPhase(worldTicks);
                    this.cycleMultiplier = this.phaseMap.get(moonPhase);
                }
            }
        } else {
            // Particles
            if (worldTicks % 5 == 0)
            {
                if (!this.beingInterfered() && this.world.canBlockSeeSky(this.pos))
                {
                    Vector3d moonVec = Utils.getMoonVector(this.world);
                    if (isMoonOut(moonVec))
                    {
                        ParticleEffects.drawIn(new ParticleEffectContext(world, Particles.getLunarSparks(), this.getOwnPulseTarget(), moonVec, 10, 1, 1, 40));
                    }
                }
            }

        }
        super.tick();
    }

    private boolean isMoonOut(Vector3d moonVec)
    {
        return moonVec.y > 0;
    }
}
