package com.sorcery.datagen;

import com.sorcery.block.ModBlock;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;

public class LootTables extends BaseLootTableProvider {

    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        lootTables.put(ModBlock.RUNESTONE_BRICKS.get(), createStandardTable("runestone_bricks", ModBlock.RUNESTONE_BRICKS.get()));

        monoTop(ModBlock.MONOLITH_CHISELED_TOP.get(), ModBlock.CHISELED_RUNESTONE.get());
        monoPiece(ModBlock.MONOLITH_CHISELED_MIDDLE.get());
        monoPiece(ModBlock.MONOLITH_CHISELED_BOTTOM.get());

        monoTop(ModBlock.MONOLITH_DARK_TOP.get(), ModBlock.DARK_RUNESTONE.get());
        monoPiece(ModBlock.MONOLITH_DARK_MIDDLE.get());
        monoPiece(ModBlock.MONOLITH_DARK_BOTTOM.get());

        monoTop(ModBlock.MONOLITH_LAPIS_TOP.get(), ModBlock.LAPIS_RUNESTONE.get());
        monoPiece(ModBlock.MONOLITH_LAPIS_MIDDLE.get());
        monoPiece(ModBlock.MONOLITH_LAPIS_BOTTOM.get());

        monoTop(ModBlock.MONOLITH_LUNAR_TOP.get(), ModBlock.LUNAR_RUNESTONE.get());
        monoPiece(ModBlock.MONOLITH_LUNAR_MIDDLE.get());
        monoPiece(ModBlock.MONOLITH_LUNAR_BOTTOM.get());

        monoTop(ModBlock.MONOLITH_SOLAR_TOP.get(), ModBlock.SOLAR_RUNESTONE.get());
        monoPiece(ModBlock.MONOLITH_SOLAR_MIDDLE.get());
        monoPiece(ModBlock.MONOLITH_SOLAR_BOTTOM.get());

    }

    private void monoTop(Block blockTop, Block runestone)
    {
        lootTables.put(blockTop, createStandardTable("mono_runestone", runestone));

    }

    private void monoPiece(Block block)
    {
        lootTables.put(block, createStandardTable("polished_runestone", ModBlock.POLISHED_RUNESTONE.get()));
    }
}