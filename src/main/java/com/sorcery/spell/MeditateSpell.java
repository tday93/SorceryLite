package com.sorcery.spell;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.tileentity.AbstractMonolithTile;
import com.sorcery.tileentity.ArcanaStorageTile;
import com.sorcery.tileentity.ChiseledMonolithTile;
import com.sorcery.utils.Utils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.vector.Vector3d;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MeditateSpell extends Spell
{
    private final int arcanaGenRate = 1;

    private final int monolithRange = 8;

    private final int arcanaDrainRate = 100;

    public MeditateSpell(SpellTier tierIn, SpellSchool schoolIn)
    {
        super(0, tierIn, schoolIn);
        this.castDuration = 100000;
        this.castType = CastType.CHANNELED;
    }

    @Override
    public ActionResultType doCastPerTick(SpellUseContext context)
    {
        if(context.getWorld().getTileEntity(context.getHitPos()) instanceof ChiseledMonolithTile)
        {
            ChiseledMonolithTile tile = (ChiseledMonolithTile)context.getWorld().getTileEntity(context.getHitPos());
            tile.meditateArcanaGen();
            return ActionResultType.SUCCESS;
        } else
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

            Vector3d loc = context.getPlayer().getEyePosition(0);
            ParticleEffectPacket pkt1 = new ParticleEffectPacket(12, 14, loc, Vector3d.ZERO, 4, 1, 2, 20);
            PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);

            context.getArcanaSource().receiveArcana(this.arcanaGenRate, false);
            return ActionResultType.SUCCESS;
        }
    }

}
