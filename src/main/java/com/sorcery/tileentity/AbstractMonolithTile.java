package com.sorcery.tileentity;

import com.sorcery.block.MonolithBlock;
import com.sorcery.block.MonolithBottomBlock;
import com.sorcery.block.MonolithTopBlock;
import com.sorcery.particle.ParticleEffectContext;
import com.sorcery.particle.ParticleEffects;
import com.sorcery.particle.Particles;
import com.sorcery.utils.MonolithData;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public abstract class AbstractMonolithTile extends ArcanaStorageTile implements ITickableTileEntity
{

    protected int arcanaPerRegen = 2;
    protected int ticksPerRegen = 2;

    protected boolean active = false;

    protected MonolithData monolithData;


    // TODO: rework interference to be more interesting
    public AbstractMonolithTile(TileEntityType variantIn, int maxArcana, MonolithData dataIn)
    {
        super(variantIn);
        this.arcanaStorage.setMaxArcanaStored(maxArcana);
        this.monolithData = dataIn;
        this.arcanaPulseOffset = new Vector3d(0.5, 1, 0.5);
    }

    @Override
    public void tick()
    {
        long worldTicks = this.getOffsetWorldTicks();
        if (this.interference && worldTicks % 10 == 0)
        {
            if (world.isRemote)
            {
                BlockPos basePos = this.pos.add(0, -1, 0);
                ParticleEffects.staticVolume(new ParticleEffectContext(this.world, Particles.getParticleSet(8), new Vector3d(basePos.getX(), basePos.getY(), basePos.getZ()), new Vector3d(1,3,1), 20, 0, 0, 10));
            }
        }
        if (!world.isRemote())
        {
            if (!this.interference)
            {
                this.generateArcana(worldTicks);
            }
        }
        super.tick();
    }

    @Override
    public boolean checkInterference(BlockPos pos)
    {
        return pos.withinDistance(this.pos, 5);
    }

    public void generateArcana(Long worldTicks)
    {

    }

    public void spawnInterferenceParticles()
    {
        for (List<Integer> intLoc : this.monolithData.pattern.getNegInterferenceLocs())
        {
            BlockPos pos = new BlockPos(this.pos.getX() + intLoc.get(0), this.pos.getY(), this.pos.getZ() + intLoc.get(1));
            ParticleEffects.staticVolume(new ParticleEffectContext(this.world, Particles.getParticleSet(9, 200), new Vector3d(pos.getX(), pos.getY()-1, pos.getZ()), new Vector3d(1,0.25,1), 20, 0, 0, 100));
        }

        for (List<Integer> intLoc : this.monolithData.pattern.getPosInterferenceLocs())
        {
            BlockPos pos = new BlockPos(this.pos.getX() + intLoc.get(0), this.pos.getY(), this.pos.getZ() + intLoc.get(1));
            ParticleEffects.staticVolume(new ParticleEffectContext(this.world, Particles.getParticleSet(10, 200), new Vector3d(pos.getX(), pos.getY()-1, pos.getZ()), new Vector3d(1,0.25,1), 20, 0, 0, 100));
        }



    }

    public void setActivity(boolean activity)
    {
        this.active = activity;
        MonolithTopBlock.setActivity(this.world, this.world.getBlockState(this.pos.up(1)), this.pos.up(1), this.active);
        MonolithBlock.setActivity(this.world, this.getBlockState(), this.pos, this.active);
        MonolithBottomBlock.setActivity(this.world, this.world.getBlockState(this.pos.down(1)), this.pos.down(1), this.active);
    }

}
