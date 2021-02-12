package com.sorcery.spell;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.NoSuchElementException;
import java.util.Optional;

public class RecallSpell extends Spell
{
    public RecallSpell(int arcanaCost, SpellTier tierIn, SpellSchool schoolIn)
    {
        super(arcanaCost, tierIn, schoolIn);
        this.castDuration = 40;
        this.castType = CastType.DURATION;
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {

        BlockPos bedPosition = BlockPos.ZERO;
        try
        {
            bedPosition = ((ServerPlayerEntity)context.getPlayer()).func_241140_K_();
        } catch(NoSuchElementException exception) {
            System.out.println("NO BED FOUND");
            return ActionResultType.FAIL;
        }
        RegistryKey<World> spawnWorld = ((ServerPlayerEntity)context.getPlayer()).func_241141_L_();
        try
        {
            ServerWorld newWorld = context.getPlayer().getServer().getWorld(spawnWorld);
            ((ServerPlayerEntity) context.getPlayer()).teleport(newWorld, bedPosition.getX(), bedPosition.getY(), bedPosition.getZ(), 0, 0);
        } catch (NullPointerException e)
        {
            System.out.println("Server not found");
            return ActionResultType.FAIL;
        }
        return ActionResultType.SUCCESS;

    }
}
