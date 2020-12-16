package com.sorcery.spell;


import com.sorcery.arcana.IArcanaStorage;
import com.sorcery.item.StaffItem;
import com.sorcery.item.WandItem;
import com.sorcery.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;


/**
 * This whole class is very much in flux.
 * Right now it is exceedingly verbose as different parts are worked out.
 */
public class SpellUseContext
{
    private final World world;
    private final BlockPos pos;
    private final IArcanaStorage arcanaSource;
    private final BlockRayTraceResult rayTraceResult;
    private final PlayerEntity player;
    private final Hand hand;
    private final ItemStack item;
    private final LivingEntity targetEntity;
    private final boolean usedOnBlock;
    private int castingTicks;
    private Spell inherentSpell = null;
    private Entity carrierEntity = null;


    /**
     *
     * @param worldIn the world where a spell was cast
     * @param posIn the postion the spell was cast from
     * @param arcanaSourceIn the source of arcana for the spell
     * @param rayTraceResultIn the blockRayTraceResult for block hits
     * @param playerIn the player that cast the spell
     * @param handIn the hand the spell was cast from
     * @param heldItem the item that cast the spell
     * @param targetEntityIn the target entity, if any
     */
    public SpellUseContext(World worldIn, BlockPos posIn, IArcanaStorage arcanaSourceIn, @Nullable BlockRayTraceResult rayTraceResultIn, @Nullable PlayerEntity playerIn, @Nullable Hand handIn, @Nullable ItemStack heldItem, @Nullable LivingEntity targetEntityIn)
    {
        this.world = worldIn;
        this.pos = posIn;
        this.arcanaSource = arcanaSourceIn;
        this.rayTraceResult = rayTraceResultIn;
        this.player = playerIn;
        this.hand = handIn;
        this.item = heldItem;
        this.targetEntity = targetEntityIn;
        this.usedOnBlock = false;
        this.castingTicks = 1;

    }

    // Convenience constructor from ItemUseContext
    public SpellUseContext(ItemUseContext itemUseContext)
    {
        this.world = itemUseContext.getWorld();
        this.pos = itemUseContext.getPlayer().getPosition();
        this.arcanaSource = Utils.getArcanaCap(itemUseContext.getItem());
        this.rayTraceResult = new BlockRayTraceResult(itemUseContext.getHitVec(), itemUseContext.getFace(), itemUseContext.getPos(), itemUseContext.isInside());
        this.player = itemUseContext.getPlayer();
        this.hand = itemUseContext.getHand();
        this.item = itemUseContext.getItem();
        this.targetEntity = null;
        this.usedOnBlock = true;
        this.castingTicks = 1;

    }

    // Convenience constructor for block hits
    public SpellUseContext(PlayerEntity player, BlockRayTraceResult rayTraceResultIn, Hand handIn)
    {
        this.world = player.getEntityWorld();
        this.pos = player.getPosition();
        this.arcanaSource = Utils.getArcanaCap(player.getHeldItem(handIn));
        this.rayTraceResult = rayTraceResultIn;
        this.player = player;
        this.hand = handIn;
        this.item = player.getHeldItem(handIn);
        this.targetEntity = null;
        this.usedOnBlock = false;
        this.castingTicks = 1;

    }

    // Convenience constructor for entity hits
    public SpellUseContext(World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, ItemStack itemStackIn, LivingEntity targetEntityIn)
    {
        this.world = worldIn;
        this.pos = pos;
        this.arcanaSource = Utils.getArcanaCap(playerIn.getHeldItem(handIn));
        this.rayTraceResult = null;
        this.player = playerIn;
        this.hand = handIn;
        this.item = itemStackIn;
        this.targetEntity = targetEntityIn;
        this.usedOnBlock = false;
        this.castingTicks = 1;
    }

    // Convenience constructor from onRightClick
    public SpellUseContext(World worldIn, LivingEntity playerIn, Hand handIn)
    {
        this.world = worldIn;
        this.pos = playerIn.getPosition();
        this.arcanaSource = Utils.getArcanaCap(playerIn.getHeldItem(handIn));
        this.rayTraceResult = Utils.blockAlongRay(playerIn.getEyePosition(1.0F), playerIn.getLook(1.0F), 8, worldIn, playerIn);
        this.player = playerIn instanceof PlayerEntity ? (PlayerEntity) playerIn : null;
        this.hand = handIn;
        this.item = playerIn.getHeldItem(handIn);
        this.targetEntity = null;
        this.usedOnBlock = false;
        this.castingTicks = 1;
    }

    public PlayerEntity getPlayer()
    {
        return this.player;
    }

    public ItemStack getItem()
    {
        return this.item;
    }

    public boolean isCasterSneaking()
    {
        return this.player != null && this.player.isSneaking();
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    public BlockPos getHitPos()
    {
        return this.rayTraceResult.getPos();
    }

    public Direction getHitFace()
    {
        return this.rayTraceResult.getFace();
    }

    public Vector3d getHitVec()
    {
        return this.rayTraceResult.getHitVec();
    }

    public Hand getHand()
    {
        return this.hand;
    }

    public World getWorld()
    {
        return this.world;
    }

    public boolean wasEntityTargeted(){
        return targetEntity != null;
    }

    public LivingEntity getTargetEntity()
    {
        return this.targetEntity;
    }

    public IArcanaStorage getArcanaSource()
    {
        return arcanaSource;
    }

    public boolean hasHitPos()
    {
        return this.rayTraceResult != null;
    }

    public BlockPos getFacePos()
    {
        return this.getHitPos().offset(this.getHitFace());
    }

    public boolean wasUsedOnBlock()
    {
        return this.usedOnBlock;
    }

    public BlockRayTraceResult getRayTraceResult()
    {
        return this.rayTraceResult;
    }

    public void setCastingTicks(int ticks)
    {
        this.castingTicks = ticks;
    }

    public int getCastingTicks()
    {
        return this.castingTicks;
    }

    public boolean wasHitBlockAir()
    {
        return this.world.isAirBlock(this.rayTraceResult.getPos());
    }

    public void setInherentSpell(Spell spell)
    {
        this.inherentSpell = spell;
    }

    public Entity getCarrierEntity()
    {
        return this.carrierEntity;
    }

    public void setCarrierEntity(Entity entity)
    {
        this.carrierEntity = entity;
    }

    public Spell getSpell()
    {
        if (this.inherentSpell != null)
        {
            return this.inherentSpell;
        }
        if (this.item.getItem() instanceof WandItem)
        {
            return Utils.getSpellFromProvider(this.item);
        }
        if (this.item.getItem() instanceof StaffItem && this.getPlayer() != null)
        {
            return Utils.getSpellFromProvider(Utils.getPlayerSpellbook(this.getPlayer()));
        }
        return ModSpell.SPELL_REMOVE_ARCANA.get();
    }

}
