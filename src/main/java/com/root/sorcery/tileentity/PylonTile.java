package com.root.sorcery.tileentity;

import com.root.sorcery.block.PylonBlock;
import net.minecraft.tileentity.ITickableTileEntity;

public class PylonTile extends ArcanaStorageTile implements ITickableTileEntity
{
    public PylonTile()
    {
        super(ModTile.PYLON_TILE);
    }


    @Override
    public void tick()
    {
        long worldTicks = world.getWorld().getGameTime();
        if (worldTicks % 20 == 0)
        {
            boolean active = false;
            if (!this.arcanaTransferSources.isEmpty())
            {
               active = true;
            }
            PylonBlock.setActivity(this.world, this.getBlockState(), this.pos, active);
        }
        super.tick();
    }
}