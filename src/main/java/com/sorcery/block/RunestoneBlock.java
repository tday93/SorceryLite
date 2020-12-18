package com.sorcery.block;

import com.sorcery.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

public class RunestoneBlock extends Block
{
    private final ResourceLocation monolithTopBlock;
    private final ResourceLocation monolithMiddleBlock;
    private final ResourceLocation monolithBottomBlock;


    public RunestoneBlock(ResourceLocation monolithTopBlockIn, ResourceLocation monolithMiddleBlockIn, ResourceLocation monolithBottomBlockIn)
    {
        super(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 3.0f).sound(SoundType.STONE).setLightLevel((x) -> 15));
        this.monolithTopBlock = monolithTopBlockIn;
        this.monolithMiddleBlock = monolithMiddleBlockIn;
        this.monolithBottomBlock = monolithBottomBlockIn;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        // only run server side
        if (worldIn.isRemote())
        {
            return;
        }
        // tags can expire, need to renew every time
        ITag<Block> tag = BlockTags.getCollection().getTagByID(Constants.MONOLITH_FORMABLE_TAG);

        Block sub1 = worldIn.getBlockState(pos.down(1)).getBlock();
        Block sub2 = worldIn.getBlockState(pos.down(2)).getBlock();

        // check for runestone below
        if (tag.contains(sub1) && tag.contains(sub2))
        {
            Block topBlock = GameRegistry.findRegistry(Block.class).getValue(this.monolithTopBlock);
            Block middleBlock = GameRegistry.findRegistry(Block.class).getValue(this.monolithMiddleBlock);
            Block bottomBlock = GameRegistry.findRegistry(Block.class).getValue(this.monolithBottomBlock);
            // remove blocks, add monolith
            worldIn.removeBlock(pos, false);
            worldIn.removeBlock(pos.down(1), false);
            worldIn.removeBlock(pos.down(2), false);
            worldIn.setBlockState(pos, topBlock.getDefaultState(), 3);
            worldIn.setBlockState(pos.down(1), middleBlock.getDefaultState(), 3);
            worldIn.setBlockState(pos.down(2), bottomBlock.getDefaultState(), 3);

        }
    }
}
