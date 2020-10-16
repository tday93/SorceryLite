package com.sorcery.tileentity;

import com.sorcery.block.MonolithBlock;
import com.sorcery.block.MonolithBottomBlock;
import com.sorcery.block.MonolithTopBlock;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.vector.Vector3d;

public class ChiseledMonolithTile extends AbstractMonolithTile implements ITickableTileEntity
{

    protected int arcanaPerRegen = 2;
    protected int ticksPerRegen = 2;

    protected boolean active = true;


    public ChiseledMonolithTile()
    {
        super(ModTile.CHISELED_MONOLITH_TILE, 1000);
        this.arcanaPulseOffset = new Vector3d(0.5, 2, 0.5);
    }

    @Override
    public void tick()
    {
        // Check Activity
        long worldTicks = this.getOffsetWorldTicks();
        if (worldTicks % 20 == 0)
        {
            this.setActivity(true);
        }
        // To Abstract Monolith tick
        super.tick();
    }

    @Override
    public void generateArcana(Long worldTicks)
    {
        if (worldTicks % ticksPerRegen == 0 && !this.interference)
        {
            this.arcanaStorage.receiveArcana(arcanaPerRegen, false);
        }
    }



}
