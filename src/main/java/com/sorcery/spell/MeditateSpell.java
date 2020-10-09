package com.sorcery.spell;

import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.tileentity.ArcanaStorageTile;
import com.sorcery.utils.Utils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class MeditateSpell extends Spell
{
    private int arcanaGenRate = 1;

    public MeditateSpell()
    {
        super(0);
        this.castDuration = 100000;
        this.castType = CastType.CHANNELED;
    }

    @Override
    public ActionResultType doCastPerTick(SpellUseContext context)
    {
        context.getArcanaSource().receiveArcana(this.arcanaGenRate, false);
        return ActionResultType.SUCCESS;
    }

}
