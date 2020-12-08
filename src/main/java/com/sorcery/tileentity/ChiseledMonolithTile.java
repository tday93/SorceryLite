package com.sorcery.tileentity;

import com.sorcery.block.ModBlock;
import com.sorcery.utils.MonolithPattern;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class ChiseledMonolithTile extends AbstractMonolithTile implements ITickableTileEntity
{

    protected int arcanaPerRegen = 2;
    protected int ticksPerRegen = 2;

    protected boolean active = true;

    protected MonolithPattern ringPattern = MonolithPattern.CHISELED_RING;


    public ChiseledMonolithTile()
    {
        super(ModTile.CHISELED_MONOLITH_TILE, 1000, MonolithPattern.CHISELED);
        this.arcanaPulseOffset = new Vector3d(0.5, 2, 0.5);
    }

    @Override
    public void tick()
    {

        // To Abstract Monolith tick
        super.tick();
    }

    @Override
    public int checkInterference(BlockPos pos)
    {
        int relX = pos.getX() - this.pos.getX();
        int relY = pos.getY() - this.pos.getY();
        int relZ = pos.getZ() - this.pos.getZ();

        return this.monolithData.pattern.getInterference(relX, relY, relZ);
    }

    @Override
    public void generateArcana(Long worldTicks)
    {
    }

    public void meditateArcanaGen()
    {
        this.receiveArcana(1000);
    }


    @Override
    public ItemStack onResonatorWhack(ItemStack resonator)
    {
        if (this.ringPattern.pattern.isPatternValid(this.world, this.pos))
        {
            this.addInterferenceOverride(this.pos);
            this.gridMonoliths.add(this.pos);
            for (BlockPos pos : this.ringPattern.pattern.getBlockPositions(this.pos, ModBlock.MONOLITH_CHISELED_MIDDLE.get()))
            {
                ChiseledMonolithTile otherMono = (ChiseledMonolithTile)this.world.getTileEntity(pos);
                otherMono.addInterferenceOverride(this.pos);
                otherMono.addArcanaTransferTarget(this.pos);
                this.gridMonoliths.add(pos);
            }
        }
        return resonator;
    }


}
