package com.sorcery.block;

import com.sorcery.Constants;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ModBlock
{

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MODID);

    public static void init()
    {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Simple Blocks
    public static final RegistryObject<Block> POLISHED_RUNESTONE = BLOCKS.register("polished_runestone", () -> new Block(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> RUNESTONE_BRICKS = BLOCKS.register("runestone_bricks", () -> new Block(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> RUNEWOOD_PLANKS = BLOCKS.register("runewood_planks", () -> new Block(Block.Properties.create(Material.WOOD)));

    // Runestone Variations

    public static final RegistryObject<Block> RUNESTONE_BRICK_STAIRS = BLOCKS.register("runestone_brick_stairs", () -> new StairsBlock(() -> RUNESTONE_BRICKS.get().getDefaultState(), Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> RUNESTONE_BRICK_SLAB = BLOCKS.register("runestone_brick_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> RUNESTONE_BRICK_WALL = BLOCKS.register("runestone_brick_wall", () -> new WallBlock(Block.Properties.create(Material.ROCK)));

    // Runewood Variations

    public static final RegistryObject<Block> RUNEWOOD_PLANK_STAIRS = BLOCKS.register("runewood_plank_stairs", () -> new StairsBlock(() -> RUNEWOOD_PLANKS.get().getDefaultState(), Block.Properties.create(Material.WOOD)));
    public static final RegistryObject<Block> RUNEWOOD_PLANK_SLAB = BLOCKS.register("runewood_plank_slab", () -> new SlabBlock(Block.Properties.create(Material.WOOD)));
    public static final RegistryObject<Block> RUNEWOOD_PLANK_FENCE = BLOCKS.register("runewood_plank_fence", () -> new FenceBlock(Block.Properties.create(Material.WOOD)));

    // Monoliths

    public static final RegistryObject<Block> MONOLITH_CHISELED_TOP = BLOCKS.register("monolith_chiseled_top", () -> new MonolithTopBlock());
    public static final RegistryObject<Block> MONOLITH_CHISELED_MIDDLE = BLOCKS.register("monolith_chiseled_middle", () -> new MonolithMiddleBlock());
    public static final RegistryObject<Block> MONOLITH_CHISELED_BOTTOM = BLOCKS.register("monolith_chiseled_bottom", () -> new MonolithBottomBlock());

    public static final RegistryObject<Block> MONOLITH_DARK_TOP = BLOCKS.register("monolith_dark_top", () -> new MonolithTopBlock());
    public static final RegistryObject<Block> MONOLITH_DARK_MIDDLE = BLOCKS.register("monolith_dark_middle", () -> new DarkMonolithBlock());
    public static final RegistryObject<Block> MONOLITH_DARK_BOTTOM = BLOCKS.register("monolith_dark_bottom", () -> new MonolithBottomBlock());

    public static final RegistryObject<Block> MONOLITH_LAPIS_TOP = BLOCKS.register("monolith_lapis_top", () -> new MonolithTopBlock());
    public static final RegistryObject<Block> MONOLITH_LAPIS_MIDDLE = BLOCKS.register("monolith_lapis_middle", () -> new LapisMonolithBlock());
    public static final RegistryObject<Block> MONOLITH_LAPIS_BOTTOM = BLOCKS.register("monolith_lapis_bottom", () -> new MonolithBottomBlock());

    public static final RegistryObject<Block> MONOLITH_LUNAR_TOP =  BLOCKS.register("monolith_lunar_top", () -> new MonolithTopBlock());
    public static final RegistryObject<Block> MONOLITH_LUNAR_MIDDLE = BLOCKS.register("monolith_lunar_middle", () -> new LunarMonolithBlock());
    public static final RegistryObject<Block> MONOLITH_LUNAR_BOTTOM = BLOCKS.register("monolith_lunar_bottom", () -> new MonolithBottomBlock());

    public static final RegistryObject<Block> MONOLITH_SOLAR_TOP = BLOCKS.register("monolith_solar_top", () -> new MonolithTopBlock());
    public static final RegistryObject<Block> MONOLITH_SOLAR_MIDDLE = BLOCKS.register("monolith_solar_middle", () -> new SolarMonolithBlock());
    public static final RegistryObject<Block> MONOLITH_SOLAR_BOTTOM = BLOCKS.register("monolith_solar_bottom", () -> new MonolithBottomBlock());


    public static final RegistryObject<Block> CRAFT_BLOCK = BLOCKS.register("craft_block", () -> new CraftBlock());

    // Runestones

    public static final RegistryObject<Block> CHISELED_RUNESTONE = BLOCKS.register("chiseled_runestone", () -> new RunestoneBlock(MONOLITH_CHISELED_TOP.getId(), MONOLITH_CHISELED_MIDDLE.getId(), MONOLITH_CHISELED_BOTTOM.getId()));
    public static final RegistryObject<Block> DARK_RUNESTONE = BLOCKS.register("dark_runestone", () -> new RunestoneBlock(MONOLITH_DARK_TOP.getId(), MONOLITH_DARK_MIDDLE.getId(), MONOLITH_DARK_BOTTOM.getId()));
    public static final RegistryObject<Block> LAPIS_RUNESTONE = BLOCKS.register("lapis_runestone", () -> new RunestoneBlock(MONOLITH_LAPIS_TOP.getId(), MONOLITH_LAPIS_MIDDLE.getId(), MONOLITH_LAPIS_BOTTOM.getId()));
    public static final RegistryObject<Block> LUNAR_RUNESTONE = BLOCKS.register("lunar_runestone", () -> new RunestoneBlock(MONOLITH_LUNAR_TOP.getId(), MONOLITH_LUNAR_MIDDLE.getId(), MONOLITH_LUNAR_BOTTOM.getId()));
    public static final RegistryObject<Block> SOLAR_RUNESTONE = BLOCKS.register("solar_runestone", () -> new RunestoneBlock(MONOLITH_SOLAR_TOP.getId(), MONOLITH_SOLAR_MIDDLE.getId(), MONOLITH_SOLAR_BOTTOM.getId()));

    // non simple blocks
    public static final RegistryObject<Block> RUNEWOOD_LOG = BLOCKS.register("runewood_log", () -> new RunewoodLogBlock());
    public static final RegistryObject<Block> STRIPPED_RUNEWOOD_LOG = BLOCKS.register("stripped_runewood_log", () -> new StrippedRunewoodLogBlock());
    public static final RegistryObject<Block> RUNEWOOD_SAPLING = BLOCKS.register("runewood_sapling", () -> new RunewoodSaplingBlock());
    public static final RegistryObject<Block> RUNEWOOD_LEAVES = BLOCKS.register("runewood_leaves", () -> new LeavesBlock(Block.Properties.create(Material.LEAVES)));

    // Decor
    public static final RegistryObject<Block> WOLFRAM_LANTERN = BLOCKS.register("wolfram_lantern", () -> new WolframLanternBlock());

    // Other Tiles
    public static final RegistryObject<Block> PYLON = BLOCKS.register("pylon", () -> new PylonBlock());



}
