package com.sorcery.tileentity;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.sorcery.block.MonolithBlock;
import com.sorcery.block.MonolithBottomBlock;
import com.sorcery.block.MonolithTopBlock;
import com.sorcery.utils.Utils;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractMonolithTile extends ArcanaStorageTile implements ITickableTileEntity
{

    protected int arcanaPerRegen = 2;
    protected int ticksPerRegen = 2;

    protected boolean active = false;

    protected int interferenceRange = 5;

    protected int interferenceMaxRange = 8;
    protected List<Integer> noInterference = Arrays.asList(2,4,6,8);

    protected List<BlockPos> interferingMonoliths = Arrays.asList();


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
        if (!world.isRemote())
        {
            if (this.interference && worldTicks % 10 == 0)
            {
                // Do interference effect
            }
            if (worldTicks % 40 == 0)
            {
                // TODO: save&load this, don't just check every 40 seconds
                updateInterference();
            }
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

    public void addInterference(TileEntity tile)
    {
       this.interferingMonoliths.add(tile.getPos());
       this.interference = true;
    }

    public void removeInterference(TileEntity tileEntity)
    {
        if (this.interferingMonoliths.contains(tileEntity.getPos()))
        {
            this.interferingMonoliths.remove(tileEntity.getPos());
        }
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
        BlockPos tilePos = tile.getPos();
        int diffX = Math.abs(this.pos.getX() - tilePos.getX());
        int diffZ = Math.abs(this.pos.getZ() - tilePos.getZ());
        if (this.noInterference.contains(diffX))
        {
            if (diffZ >= -diffX && diffZ <= diffX)
            {
                return false;
            }
            if (this.noInterference.contains(diffZ))
            {
                return false;
            }
        }
        return !this.noInterference.contains(diffZ);
    }

}
