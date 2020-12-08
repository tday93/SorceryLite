package com.sorcery.utils;

import net.minecraft.block.Blocks;

public enum StructurePattern
{
    COCOON_PATTERN(new BlockPattern.PatternBuilder()
            .addLine("---")
            .addLine("-X-")
            .addLine("---")
            .addLevel()
            .addLine("-X-")
            .addLine("X-X")
            .addLine("-X-")
            .addLevel()
            .addLine("-X-")
            .addLine("XIX")
            .addLine("-X-")
            .addLevel()
            .addLine("---")
            .addLine("-X-")
            .addLine("---")
            .addBlockMapping(Blocks.COBBLESTONE, 'X')
            .build()
    );

    public final BlockPattern pattern;

    StructurePattern(BlockPattern pattern)
    {
        this.pattern = pattern;
    }
}
