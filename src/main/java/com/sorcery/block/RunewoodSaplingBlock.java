package com.sorcery.block;

import com.sorcery.block.trees.RunewoodTree;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class RunewoodSaplingBlock extends SaplingBlock
{
    public RunewoodSaplingBlock()
    {
        super(new RunewoodTree(), AbstractBlock.Properties
                .create(Material.PLANTS)
                .zeroHardnessAndResistance()
                .doesNotBlockMovement()
                .tickRandomly()
                .sound(SoundType.PLANT));
    }
}
