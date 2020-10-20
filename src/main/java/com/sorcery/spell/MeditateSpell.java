package com.sorcery.spell;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.tileentity.AbstractMonolithTile;
import com.sorcery.tileentity.ArcanaStorageTile;
import com.sorcery.utils.Utils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MeditateSpell extends Spell
{
    private int arcanaGenRate = 1;

    private int monolithRange = 5;

    private int arcanaDrainRate = 10;

    public MeditateSpell()
    {
        super(0);
        this.castDuration = 100000;
        this.castType = CastType.CHANNELED;
    }

    @Override
    public ActionResultType doCastPerTick(SpellUseContext context)
    {
        Predicate<TileEntity> searchPredicate = Utils.getTESearchPredicate(AbstractMonolithTile.class, context.getPos(), this.monolithRange);

        Set<ArcanaStorageTile> otherMonoliths = new HashSet<>();

        List<TileEntity> allTEs = context.getWorld().loadedTileEntityList;

        for (TileEntity tile : Collections2.filter(allTEs, searchPredicate))
        {
            if (tile instanceof ArcanaStorageTile)
            {
                int arcanaExtracted = ((ArcanaStorageTile) tile).extractArcana(this.arcanaDrainRate);
                context.getArcanaSource().receiveArcana(arcanaExtracted, false);
            }
        }

        context.getArcanaSource().receiveArcana(this.arcanaGenRate, false);
        return ActionResultType.SUCCESS;
    }

}
