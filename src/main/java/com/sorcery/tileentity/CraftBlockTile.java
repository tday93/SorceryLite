package com.sorcery.tileentity;

import com.sorcery.block.ModBlock;
import com.sorcery.particle.ParticleCollection;
import com.sorcery.particle.ParticleEffectContext;
import com.sorcery.particle.ParticleEffects;
import com.sorcery.particle.Particles;
import com.sorcery.utils.MonolithPattern;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.ArrayList;

public class CraftBlockTile extends AbstractMonolithTile
{

    protected MonolithPattern craftRingPattern = MonolithPattern.CRAFTING_RING;

    public CraftBlockTile()
    {
        super(ModTile.CRAFT_BLOCK_TILE, 10, MonolithPattern.NULL_PATTERN);
        this.arcanaPulseOffset = new Vector3d(0.5, 0.5, 0.5);
    }


    @Override
    public void tick()
    {
        if (!this.gridMonoliths.isEmpty())
        {
            long worldTicks = this.getOffsetWorldTicks();
            if (worldTicks % 10 == 0)
            {
                ParticleEffects.horizontalRingArea(new ParticleEffectContext(world, Particles.getParticleSet(21, 20), this.getOwnPulseTarget(), new Vector3d(0, 0.1, 0), 4, 0.5, 4, 20));
            }
        }
        super.tick();
    }

    public boolean craftAreaActive()
    {
        return !this.gridMonoliths.isEmpty();
    }

    @Override
    public ItemStack onResonatorWhack(ItemStack resonator)
    {
        if (this.craftRingPattern.pattern.isPatternValid(this.world, this.pos))
        {
            this.addInterferenceOverride(this.pos);
            this.gridMonoliths.add(this.pos);
            ArrayList<ChiseledMonolithTile> ringMonos = new ArrayList<>();
            ArrayList<BlockPos> monoPoses = new ArrayList<>();
            for (BlockPos pos : this.craftRingPattern.pattern.getBlockPositions(this.pos, ModBlock.MONOLITH_CHISELED_BOTTOM.get()))
            {
                ChiseledMonolithTile otherMono = (ChiseledMonolithTile)this.world.getTileEntity(pos.add(0, 1, 0));
                this.gridMonoliths.add(pos.add(0, 1, 0));
                ringMonos.add(otherMono);
                monoPoses.add(otherMono.getPos());
                otherMono.addInterferenceOverride(this.pos);
            }
            ringMonos.get(0).addArcanaTransferTarget(monoPoses.get(1));
            ringMonos.get(1).addArcanaTransferTarget(monoPoses.get(3));
            ringMonos.get(3).addArcanaTransferTarget(monoPoses.get(5));
            ringMonos.get(5).addArcanaTransferTarget(monoPoses.get(7));
            ringMonos.get(7).addArcanaTransferTarget(monoPoses.get(9));
            ringMonos.get(9).addArcanaTransferTarget(monoPoses.get(11));
            ringMonos.get(11).addArcanaTransferTarget(monoPoses.get(10));
            ringMonos.get(10).addArcanaTransferTarget(monoPoses.get(8));
            ringMonos.get(8).addArcanaTransferTarget(monoPoses.get(6));
            ringMonos.get(6).addArcanaTransferTarget(monoPoses.get(4));
            ringMonos.get(4).addArcanaTransferTarget(monoPoses.get(2));
            ringMonos.get(2).addArcanaTransferTarget(monoPoses.get(0));
        }

        return resonator;
    }
}
