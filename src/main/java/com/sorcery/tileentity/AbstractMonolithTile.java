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
import net.minecraft.util.math.vector.Vector3d;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractMonolithTile extends ArcanaStorageTile implements ITickableTileEntity
{

    protected int arcanaPerRegen = 2;
    protected int ticksPerRegen = 2;

    protected boolean active = false;

    protected int interferenceRange = 5;

    protected boolean interference = false;


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
            if (this.getOffsetWorldTicks() % 40 == 0)
            {
                updateInterference();
            }
        }
        super.tick();
    }

    public void setActivity(boolean activity)
    {
        this.active = activity;
        MonolithTopBlock.setActivity(this.world, this.world.getBlockState(this.pos.up(1)), this.pos.up(1), this.active);
        MonolithBlock.setActivity(this.world, this.getBlockState(), this.pos, this.active);
        MonolithBottomBlock.setActivity(this.world, this.world.getBlockState(this.pos.down(1)), this.pos.down(1), this.active);
    }



    public void updateInterference()
    {
        Predicate<TileEntity> searchPredicate = Utils.getTESearchPredicate(AbstractMonolithTile.class, this, this.interferenceRange);

        Set<ArcanaStorageTile> otherMonoliths = new HashSet<>();

        List<TileEntity> allTEs = world.loadedTileEntityList;

        for (TileEntity tileEntity : Collections2.filter(allTEs, searchPredicate))
        {
            otherMonoliths.add((ArcanaStorageTile)tileEntity);
        }
        if (!otherMonoliths.isEmpty())
        {
            this.interference = true;
        } else {
            this.interference = false;
        }
    }

    public int getInterferenceRange()
    {
        return this.interferenceRange;
    }

}
