package com.sorcery.tileentity;

import com.sorcery.block.MonolithBlock;
import com.sorcery.block.MonolithBottomBlock;
import com.sorcery.block.MonolithTopBlock;
import com.sorcery.utils.MonolithData;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class ChiseledMonolithTile extends AbstractMonolithTile implements ITickableTileEntity
{

    protected int arcanaPerRegen = 2;
    protected int ticksPerRegen = 2;

    protected boolean active = true;


    public ChiseledMonolithTile()
    {
        super(ModTile.CHISELED_MONOLITH_TILE, 1000, MonolithData.CHISELED);
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
    public boolean checkInterference(BlockPos pos)
    {
        int relX = pos.getX() - this.pos.getX();
        int relZ = pos.getZ() - this.pos.getZ();

        return this.monolithData.pattern.isNegInterference(relX, relZ);

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
