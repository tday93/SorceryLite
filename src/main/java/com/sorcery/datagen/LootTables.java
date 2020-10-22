package com.sorcery.datagen;

import com.sorcery.block.ModBlock;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;

public class LootTables extends BaseLootTableProvider {

    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        lootTables.put(ModBlock.RUNESTONE_BRICKS.get(), createStandardTable("runestone_bricks", ModBlock.RUNESTONE_BRICKS.get()));
        lootTables.put(ModBlock.MONOLITH_CHISELED_TOP.get(), createStandardTable("chiseled_runestone", ModBlock.CHISELED_RUNESTONE.get()));
        lootTables.put(ModBlock.MONOLITH_CHISELED_MIDDLE.get(), createStandardTable("polished_runestone", ModBlock.POLISHED_RUNESTONE.get()));
        lootTables.put(ModBlock.MONOLITH_CHISELED_BOTTOM.get(), createStandardTable("polished_runestone", ModBlock.POLISHED_RUNESTONE.get()));
    }
}