package com.sorcery.spell;

import com.google.common.collect.Lists;
import com.sorcery.utils.ModColor;
import com.sorcery.utils.Utils;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class SignalFlareSpell extends Spell
{
    public SignalFlareSpell(int arcanaCost, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(arcanaCost, tierIn, schoolIn);
    }

    // perform the final cast of the spell
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        ItemStack firework = getFirework();
        Vector3d pos = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(1), context.getPlayer().getLookVec(), 2);
        context.getWorld().addEntity(new FireworkRocketEntity(context.getWorld(), null, pos.getX(), pos.getY(), pos.getZ(), firework));
        return ActionResultType.SUCCESS;
    }

    private ItemStack getFirework()
    {
        ItemStack firework = new ItemStack(Items.FIREWORK_ROCKET);
        CompoundNBT compoundnbt = firework.getOrCreateChildTag("Fireworks");
        // Explosions
        ListNBT explosions = new ListNBT();

        // One Explosion
        CompoundNBT explosion = new CompoundNBT();
        explosion.putBoolean("Flicker", false);
        explosion.putBoolean("Trail", true);
        explosion.putByte("Type", (byte)FireworkRocketItem.Shape.SMALL_BALL.getIndex());
        List<Integer> colors = Lists.newArrayList();
        colors.add(ModColor.ARCANA_PURPLE.getHiDecimal());
        colors.add(ModColor.ARCANA_PURPLE.getMainDecimal());
        colors.add(ModColor.ARCANA_PURPLE.getLowDecimal());
        explosion.putIntArray("Colors", colors);

        explosions.add(explosion);
        // Assemble full tag
        compoundnbt.putByte("Flight", (byte)3);
        compoundnbt.put("Explosions", explosions);

        return firework;
    }

}
