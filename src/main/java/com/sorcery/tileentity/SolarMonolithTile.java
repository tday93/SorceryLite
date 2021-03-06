package com.sorcery.tileentity;

import com.sorcery.particle.ParticleEffectContext;
import com.sorcery.particle.ParticleEffects;
import com.sorcery.particle.Particles;
import com.sorcery.utils.MonolithPattern;
import com.sorcery.utils.Utils;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.vector.Vector3d;

public class SolarMonolithTile extends AbstractMonolithTile implements ITickableTileEntity
{

    public SolarMonolithTile(){
        super(ModTile.SOLAR_MONOLITH_TILE, 1000, MonolithPattern.SOLAR);
        this.arcanaStorage.extractArcana(1000, false);
        this.arcanaPerRegen = 4;
    }

    @Override
    public void tick()
    {
        long worldTicks = this.getOffsetWorldTicks();

        if (!this.world.isRemote())
        {
            // Arcana generation
            if (worldTicks % ticksPerRegen == 0 && this.world.isDaytime() && this.world.canBlockSeeSky(this.pos) && !this.beingInterfered())
            {
                this.receiveArcana(arcanaPerRegen);
            }
            // Activity setting
        } else {
            // Particles
            if (worldTicks % 5 == 0)
            {
                if (!this.beingInterfered() && this.world.isDaytime() && this.world.canBlockSeeSky(this.pos))
                {
                    Vector3d sunVec = Utils.getSunVector(this.world);
                    ParticleEffects.drawIn(new ParticleEffectContext(world, Particles.getSolarSparks(), this.getOwnPulseTarget(), sunVec, 10, 1, 1, 40));
                }
            }
        }
        super.tick();
    }
}
