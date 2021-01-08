package com.sorcery.item;

import com.sorcery.block.MonolithBottomBlock;
import com.sorcery.block.MonolithTopBlock;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.tileentity.AbstractMonolithTile;
import com.sorcery.tileentity.ArcanaStorageTile;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;


public class CrystalResonatorItem extends Item
{

    public CrystalResonatorItem(Properties properties)
    {
        super(properties);
    }
    @Nullable
    public BlockPos getLinkPos(ItemStack stack)
    {
        CompoundNBT nbt = stack.getTag();
        if ( nbt.contains("linkPos"))
        {
            CompoundNBT posTag = (CompoundNBT) nbt.get("linkPos");
            return new BlockPos(posTag.getInt("x"), posTag.getInt("y"), posTag.getInt("z"));
        } else {
            return null;
        }
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        if (!context.getWorld().isRemote())
        {
            World world = context.getWorld();
            CompoundNBT nbt = context.getItem().getOrCreateTag();
            BlockPos pos = new BlockPos(context.getPos());
            Block block = world.getBlockState(pos).getBlock();
            Boolean showInterference = false;

            if (block instanceof MonolithTopBlock)
            {
                pos = pos.add(0, -1, 0);
            }
            // redirect to main tile, and show interference if bottom block hit
            if (block instanceof MonolithBottomBlock)
            {
                pos = pos.add(0, 1, 0);
                showInterference = true;
            }

            TileEntity tile =  world.getTileEntity(pos);


            if (tile instanceof ArcanaStorageTile)
            {
                if (context.getPlayer().isSneaking())
                {
                    setLinkPos(context.getItem(), pos);
                    context.getPlayer().sendStatusMessage(new StringTextComponent("Source position set!"), true);
                } else {

                    if (nbt.contains("linkPos"))
                    {
                        CompoundNBT posTag = (CompoundNBT) nbt.get("linkPos");
                        BlockPos linkPos = new BlockPos(posTag.getInt("x"), posTag.getInt("y"), posTag.getInt("z"));

                        TileEntity linkTile = world.getTileEntity(linkPos);

                        context.getPlayer().sendStatusMessage(new StringTextComponent("Arcana target linked!"), true);

                        if (linkTile instanceof ArcanaStorageTile)
                        {
                            ((ArcanaStorageTile) linkTile).addArcanaTransferTarget(tile.getPos());
                        }
                    }

                    // do interference particle effect for monoliths
                    if (tile instanceof AbstractMonolithTile)
                    {
                        ((AbstractMonolithTile) tile).onResonatorWhack(context.getItem());
                        if(showInterference)
                        {
                            Vector3d tilePos = new Vector3d(pos.getX(), pos.getY(), pos.getZ());
                            ParticleEffectPacket pkt = new ParticleEffectPacket(10, ParticleTypes.ENCHANT, tilePos, tilePos, 10, 0, 0, 20);
                            PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt);
                        }
                    }

                }

            }
        }
        return ActionResultType.SUCCESS;
    }


    // When item is used not targeting anything
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack item = playerIn.getHeldItem(handIn);
        item = clearLinkPos(item);
        playerIn.sendStatusMessage(new StringTextComponent("Arcana source cleared!"), true);
        return new ActionResult<>(ActionResultType.SUCCESS, item);
    }

    public ItemStack clearLinkPos(ItemStack stack)
    {
       CompoundNBT nbt = stack.getTag();
       if(nbt != null)
       {
           if (nbt.contains("linkPos"))
           {
               nbt.remove("linkPos");
               stack.setTag(nbt);
           }
       }
       return stack;
    }

    public void setLinkPos(ItemStack stack, BlockPos pos)
    {
        CompoundNBT nbt = stack.getTag();
        CompoundNBT posTag = new CompoundNBT();
        posTag.putInt("x", pos.getX());
        posTag.putInt("y", pos.getY());
        posTag.putInt("z", pos.getZ());
        nbt.put("linkPos", posTag);
        stack.setTag(nbt);
    }

    public void nullLinkPos(ItemStack stack)
    {
        CompoundNBT nbt = stack.getTag();
        if (nbt.contains("linkPos"))
        {
            nbt.remove("linkPos");
        }
    }

}
