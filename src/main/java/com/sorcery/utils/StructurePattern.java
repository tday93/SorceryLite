package com.sorcery.utils;

import net.minecraft.block.Blocks;

public enum StructurePattern
{
    TEST_PATTERN(new BlockPattern.PatternBuilder()
            .addLine("--X--")
            .addLine("--X--")
            .addLine("QQ-YY")
            .addLine("--Z--")
            .addLine("--Z--")
            .addLevel()
            .addLine("--X--")
            .addLine("--X--")
            .addLine("QQIYY")
            .addLine("--Z--")
            .addLine("--Z--")
            .addBlockMapping(Blocks.IRON_BLOCK, 'X') //north
            .addBlockMapping(Blocks.GOLD_BLOCK, 'Y')
            .addBlockMapping(Blocks.DIAMOND_BLOCK, 'Z') //south
            .addBlockMapping(Blocks.EMERALD_BLOCK, 'Q')
            .build()

    ),
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
    ),
    STAIR_DOWN_PATTERN(new BlockPattern.PatternBuilder()
            .addLine("~")
            .addLine("~")
            .addLine("~")
            .addLevel()
            .addLine("~")
            .addLine("~")
            .addLine("~")
            .addLevel()
            .addLine("~")
            .addLine("~")
            .addLine("I")
            .addLevel()
            .addLine("~")
            .addLine("~")
            .addLine("~")
            .addLevel()
            .addLine("~")
            .addLine("~")
            .addLine("X")
            .addLevel()
            .addLine("~")
            .addLine("X")
            .addLine("-")
            .addLevel()
            .addLine("X")
            .addLine("-")
            .addLine("-")
            .addLevel()
            .addBlockMapping(Blocks.COBBLESTONE, 'X')
            .build()
    ),
    TUNNEL_PATTERN(new BlockPattern.PatternBuilder()
            .addLine("-~-")
            .addLine("-~-")
            .addLine("-~-")
            .addLine("-~-")
            .addLine("-~T")
            .addLine("-~-")
            .addLine("-~-")
            .addLine("-~-")
            .addLine("-~-")
            .addLevel()
            .addLine("-~-")
            .addLine("-~-")
            .addLine("-~-")
            .addLine("-~-")
            .addLine("-~-")
            .addLine("-~-")
            .addLine("-~-")
            .addLine("-~-")
            .addLine("-I-")
            .addBlockMapping(Blocks.TORCH, 'T')
            .build()
    );

    public final BlockPattern pattern;

    StructurePattern(BlockPattern pattern)
    {
        this.pattern = pattern;
    }
}