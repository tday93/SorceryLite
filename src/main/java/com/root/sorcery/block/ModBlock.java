package com.root.sorcery.block;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;


@ObjectHolder("sorcery")
public class ModBlock
{

    // Simple Blocks
    @ObjectHolder("polished_chondrite")
    public static Block POLISHED_CHONDRITE;

    @ObjectHolder("chondrite_bricks")
    public static Block CHONDRITE_BRICKS;

    // Stairs
    @ObjectHolder("chondrite_brick_stairs")
    public static StairsBlock CHONDRITE_BRICK_STAIRS;

    // Slabs
    @ObjectHolder("chondrite_brick_slab")
    public static SlabBlock CHONDRITE_BRICK_SLAB;

    // Walls
    @ObjectHolder("chondrite_brick_wall")
    public static WallBlock CHONDRITE_BRICK_WALL;

    // Tile Blocks
    @ObjectHolder("reliquary")
    public static Block RELIQUARY;

    @ObjectHolder("chondrite_blast_furnace")
    public static Block CHONDRITE_BLAST_FURNACE;

    @ObjectHolder("monolith_normal")
    public static Block MONOLITH_NORMAL;

    @ObjectHolder("monolith_dark")
    public static Block MONOLITH_DARK;

    @ObjectHolder("monolith_solar")
    public static Block MONOLITH_SOLAR;

    @ObjectHolder("monolith_lunar")
    public static Block MONOLITH_LUNAR;

    @ObjectHolder("chondrite_lantern")
    public static Block CHONDRITE_LANTERN;

    @ObjectHolder("podium")
    public static Block PODIUM;

    @ObjectHolder("phylactery")
    public static Block PHYLACTERY;

    public static void init(RegistryEvent.Register<Block> event)
    {
        // Simple Blocks
        POLISHED_CHONDRITE = simpleBlockFactory(event, "polished_chondrite", Material.ROCK, 3.0F, 5.0F, SoundType.STONE);
        CHONDRITE_BRICKS = simpleBlockFactory(event, "chondrite_bricks", Material.ROCK, 3.0F, 5.0F, SoundType.STONE);

       registerBlock(new ChondriteLanternBlock(), "chondrite_lantern", event);

        // Slabs
        CHONDRITE_BRICK_SLAB = slabFactory(event, CHONDRITE_BRICKS, "chondrite_brick_slab");

        // Walls
        CHONDRITE_BRICK_WALL = wallFactory(event, CHONDRITE_BRICKS, "chondrite_brick_wall");

        // Stairs
        CHONDRITE_BRICK_STAIRS = stairsFactory(event, CHONDRITE_BRICKS, "chondrite_brick_stairs");

        // Tile Blocks
        registerTileBlocks(event, "chondrite_blast_furnace", new ChondriteBlastFurnaceBlock());
        registerTileBlocks(event, "reliquary", new ReliquaryBlock());
        registerTileBlocks(event, "podium", new PodiumBlock());
        registerTileBlocks(event, "phylactery", new PhylacteryBlock());

        registerTileBlocks(event, "monolith_normal", new MonolithBlock());
        registerTileBlocks(event, "monolith_dark", new MonolithBlock());
        registerTileBlocks(event, "monolith_lunar", new MonolithBlock());
        registerTileBlocks(event, "monolith_solar", new MonolithBlock());

    }

    public static Block simpleBlockFactory(RegistryEvent.Register<Block> event, String registryName, Material material, Float hardness, Float resistance, SoundType sound)
    {
        Block block = new Block(Block.Properties
                .create(material)
                .hardnessAndResistance(hardness, resistance)
                .sound(sound));

        block.setRegistryName(registryName);
        event.getRegistry().register(block);

        return block;
    }

    public static void registerBlock(Block block, String registryName, RegistryEvent.Register<Block> event)
    {
        block.setRegistryName(registryName);
        event.getRegistry().register(block);
    }

    public static SlabBlock slabFactory(RegistryEvent.Register<Block> event, Block block, String registryName)
    {
        SlabBlock slabBlock = new SlabBlock(Block.Properties.from(block));

        slabBlock.setRegistryName(registryName);

        event.getRegistry().register(slabBlock);

        return slabBlock;
    }

    public static WallBlock wallFactory(RegistryEvent.Register<Block> event, Block block, String registryName)
    {
        WallBlock wallBlock = new WallBlock(Block.Properties.from(block));

        wallBlock.setRegistryName(registryName);
        event.getRegistry().register(wallBlock);

        return wallBlock;
    }

    public static StairsBlock stairsFactory(RegistryEvent.Register<Block> event, Block block, String registryName)
    {
        StairsBlock stairsBlock = new StairMod(block);

        stairsBlock.setRegistryName(registryName);
        event.getRegistry().register(stairsBlock);

        return stairsBlock;
    }

    public static void registerTileBlocks(RegistryEvent.Register<Block> event, String registryName, Block block)
    {
        block.setRegistryName(registryName);
        event.getRegistry().register(block);
    }
}
