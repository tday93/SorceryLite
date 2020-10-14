package com.sorcery.spell;

import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;

public class SignalFlareSpell extends Spell
{
    public SignalFlareSpell(int arcanaCost)
    {
        super(arcanaCost);
    }

    // perform the final cast of the spell
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        ItemStack firework = getFirework();
        BlockPos pos = context.getPos();
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
        // TODO needs color here

        explosions.add(explosion);
        // Assemble full tag
        compoundnbt.putByte("Flight", (byte)3);
        compoundnbt.put("Explosions", explosions);

        return firework;
    }

}
