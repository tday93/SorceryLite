package com.sorcery.tileentity;

import com.sorcery.block.MonolithBlock;
import com.sorcery.block.MonolithBottomBlock;
import com.sorcery.block.MonolithTopBlock;
import com.sorcery.particle.ParticleEffectContext;
import com.sorcery.particle.ParticleEffects;
import com.sorcery.particle.Particles;
import com.sorcery.utils.Utils;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.*;

public abstract class AbstractMonolithTile extends ArcanaStorageTile implements ITickableTileEntity
{

    protected int arcanaPerRegen = 2;
    protected int ticksPerRegen = 2;

    protected boolean active = false;

    protected int interferenceRange = 5;

    protected int interferenceMaxRange = 8;
    protected List<List> noInterference = Arrays.asList(Arrays.asList(1.51,2.5), Arrays.asList(3.51, 4.5), Arrays.asList(5.51, 6.5), Arrays.asList(7.51, 8.5));

    protected List<BlockPos> interferingMonoliths = new LinkedList<>();


    // TODO: rework interference to be more interesting
    public AbstractMonolithTile(TileEntityType variantIn, int maxArcana)
    {
        super(variantIn);
        this.arcanaStorage.setMaxArcanaStored(maxArcana);
        this.arcanaPulseOffset = new Vector3d(0.5, 1, 0.5);
    }

    @Override
    public void tick()
    {
        long worldTicks = this.getOffsetWorldTicks();
        if (worldTicks % 40 == 0)
        {
            // TODO: save&load this, don't just check every 40 seconds
            updateInterference();
        }
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

    public void generateArcana(Long worldTicks)
    {

    }

    public void setActivity(boolean activity)
    {
        this.active = activity;
        MonolithTopBlock.setActivity(this.world, this.world.getBlockState(this.pos.up(1)), this.pos.up(1), this.active);
        MonolithBlock.setActivity(this.world, this.getBlockState(), this.pos, this.active);
        MonolithBottomBlock.setActivity(this.world, this.world.getBlockState(this.pos.down(1)), this.pos.down(1), this.active);
    }


    /**
     * Interference rules:
     * 1) All monoliths interfere with one another.
     * 2) Interference occurs regardless of monolith state.
     */
    public void updateInterference()
    {
        // When monolith is formed, find all monoliths in max interference range
        Set<AbstractMonolithTile> tilesInRange = Utils.getTEInRange(this.world, this, AbstractMonolithTile.class, this.interferenceMaxRange);
        if (tilesInRange.isEmpty())
        {
            this.interferingMonoliths.clear();
            this.interference = false;
        }
        for (AbstractMonolithTile monolithTile : tilesInRange)
        {
            if (this.willInterfere(monolithTile))
            {
                this.addInterference(monolithTile);
                monolithTile.addInterference(this);
            } else if (monolithTile.willInterfere(this))
            {
                this.addInterference(monolithTile);
                monolithTile.addInterference(this);
            } else
            {
                this.removeInterference(monolithTile);
                monolithTile.removeInterference(this);
            }
        }
    }

    public void clearAllInterference()
    {
        Set<AbstractMonolithTile> tilesInRange = Utils.getTEInRange(this.world, this, AbstractMonolithTile.class, this.interferenceMaxRange);
        for (AbstractMonolithTile monolithTile : tilesInRange)
        {
            monolithTile.removeInterference(this);
        }
    }

    public void addInterference(TileEntity tile)
    {
       this.interferingMonoliths.add(tile.getPos());
       this.interference = true;
    }

    public void removeInterference(TileEntity tileEntity)
    {
        this.interferingMonoliths.remove(tileEntity.getPos());
        if (this.interferingMonoliths.isEmpty())
        {
            this.interference = false;
        }
    }


    public int getInterferenceRange()
    {
        return this.interferenceRange;
    }

    // Return true if incoming monolith will interfere with this monolith
    public boolean willInterfere(AbstractMonolithTile tile)
    {
        boolean interference = true;
        BlockPos tilePos = tile.getPos();
        double hDistance = Math.sqrt(Math.pow((double)this.pos.getX() - (double)tilePos.getX(), 2) + Math.pow((double)this.pos.getZ() - (double)tilePos.getZ(), 2));
        for (List<Double> band : this.noInterference)
        {
            if (hDistance >= band.get(0) && hDistance <= band.get(1))
            {
                interference = false;
                break;
            }
        }
        return interference;
    }

    @Override
    public void remove()
    {
        this.clearAllInterference();
        super.remove();
    }

}
