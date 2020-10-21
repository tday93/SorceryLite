package com.sorcery.utils;


import com.sorcery.block.ModBlock;

public enum MonolithPattern
{

    CHISELED(new BlockPattern.PatternBuilder()
            .addLine("-----------------")
            .addLine("------NNNNN------")
            .addLine("----NN-----NN----")
            .addLine("---N---NNN---N---")
            .addLine("--N--NN---NN--N--")
            .addLine("--N-N--NNN--N-N--")
            .addLine("-N--N-N---N-N--N-")
            .addLine("-N-N-N-NNN-N-N-N-")
            .addLine("-N-N-N-NIN-N-N-N-")
            .addLine("-N-N-N-NNN-N-N-N-")
            .addLine("-N--N-N---N-N--N-")
            .addLine("--N-N--NNN--N-N--")
            .addLine("--N--NN---NN--N--")
            .addLine("---N---NNN---N---")
            .addLine("----NN-----NN----")
            .addLine("------NNNNN------")
            .addLine("-----------------")
            .build()
    ),
    DARK(new BlockPattern.PatternBuilder()
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("--------I--------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .build()
    ),
    LAPIS(new BlockPattern.PatternBuilder()
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("--------I--------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .build()
    ),
    LUNAR(new BlockPattern.PatternBuilder()
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("--------I--------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .build()
    ),
    SOLAR(new BlockPattern.PatternBuilder()
            .addLine("N-------N-------N")
            .addLine("-N------N------N-")
            .addLine("--N-----N-----N--")
            .addLine("---N----N----N---")
            .addLine("----N---N---N----")
            .addLine("-----N--N--N-----")
            .addLine("------N-N-N------")
            .addLine("-------NNN-------")
            .addLine("NNNNNNNNINNNNNNNN")
            .addLine("-------NNN-------")
            .addLine("------N-N-N------")
            .addLine("-----N--N--N-----")
            .addLine("----N---N---N----")
            .addLine("---N----N----N---")
            .addLine("--N-----N-----N--")
            .addLine("-N------N------N-")
            .addLine("N-------N-------N")
            .build()
    ),
    CHISELED_RING(new BlockPattern.PatternBuilder()
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("--------C--------")
            .addLine("------C---C------")
            .addLine("-----------------")
            .addLine("-----C--I--C-----")
            .addLine("-----------------")
            .addLine("------C---C------")
            .addLine("--------C--------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addLine("-----------------")
            .addBlockMapping(ModBlock.MONOLITH_CHISELED_MIDDLE.get(), 'C')
            .build()
    );


    public final BlockPattern pattern;

    private MonolithPattern(BlockPattern pattern)
    {
        this.pattern = pattern;
    }
}
