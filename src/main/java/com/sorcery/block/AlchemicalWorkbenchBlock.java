package com.sorcery.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class AlchemicalWorkbenchBlock extends Block
{
    private static final Float hardness   = 3.0F;
    private static final Float resistance = 6.0F;

    public AlchemicalWorkbenchBlock()
    {
        super(Block.Properties.create(Material.ROCK).hardnessAndResistance(hardness, resistance).sound(SoundType.STONE));
    }
}
