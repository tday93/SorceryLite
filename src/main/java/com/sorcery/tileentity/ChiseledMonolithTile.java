package com.sorcery.tileentity;

import com.sorcery.utils.MonolithPattern;
import com.sorcery.utils.MonolithPatterns;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.registries.ObjectHolder;

public class ChiseledMonolithTile extends AbstractMonolithTile implements ITickableTileEntity
{

    protected int arcanaPerRegen = 2;
    protected int ticksPerRegen = 2;

    protected boolean active = true;

    protected MonolithPatterns ringPattern = MonolithPatterns.CHISELED_RING;


    public ChiseledMonolithTile()
    {
        super(ModTile.CHISELED_MONOLITH_TILE, 1000, MonolithPatterns.CHISELED);
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
    public int checkInterference(BlockPos pos)
    {
        int relX = pos.getX() - this.pos.getX();
        int relZ = pos.getZ() - this.pos.getZ();

        if (this.monolithData.pattern.isNegInterference(relX, relZ))
        {
            return -1;
        }
        return 0;
    }

    @Override
    public void generateArcana(Long worldTicks)
    {
        if (worldTicks % ticksPerRegen == 0 && !this.interference)
        {
            this.arcanaStorage.receiveArcana(arcanaPerRegen, false);
        }
    }


    @Override
    public ItemStack onResonatorWhack(ItemStack resonator)
    {
        return resonator;
    }


}
