package com.sorcery.item;
import com.sorcery.Constants;
import com.sorcery.block.ModBlock;
import com.sorcery.spell.ModSpell;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


/**
 * Here is where we register the items in the mod.
 * Names in ObjectHolders correspond to resource pack names.
 */
public class ModItem
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);

    // Simple Items
    public static final RegistryObject<Item> ARCANE_DYNAMO = ITEMS.register("arcane_dynamo", () -> new Item(Constants.ITEM_PROPS));

    public static final RegistryObject<Item> MUNDANE_MECHANISM = ITEMS.register("mundane_mechanism", () -> new Item(Constants.ITEM_PROPS));

    // Materials
    public static final RegistryObject<Item> LODESTONE = ITEMS.register("lodestone", () -> new Item(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> WOLFRAM_INGOT = ITEMS.register("wolfram_ingot", () -> new Item(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> MYTHRIL_INGOT = ITEMS.register("mythril_ingot", () -> new Item(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> SIGIL_SLATE = ITEMS.register("sigil_slate", () -> new Item(Constants.ITEM_PROPS));

    // Tomes
    public static final RegistryObject<Item> TOME_ABJURATION = ITEMS.register("tome_abjuration", () -> new Item(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> TOME_CONJURATION = ITEMS.register("tome_conjuration", () -> new Item(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> TOME_ENCHANTMENT = ITEMS.register("tome_enchantment", () -> new Item(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> TOME_EVOCATION = ITEMS.register("tome_evocation", () -> new Item(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> TOME_NECROMANCY = ITEMS.register("tome_necromancy", () -> new Item(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> TOME_TRANSMUTATION = ITEMS.register("tome_transmutation", () -> new Item(Constants.ITEM_PROPS));

    // Sigils
    public static final RegistryObject<Item> SIGIL_EVOCATION = ITEMS.register("sigil_evocation", () -> new SigilItem(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> SIGIL_CONJURATION = ITEMS.register("sigil_conjuration", () -> new SigilItem(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> SIGIL_ABJURATION = ITEMS.register("sigil_abjuration", () -> new SigilItem(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> SIGIL_ENCHANTMENT = ITEMS.register("sigil_enchantment", () -> new SigilItem(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> SIGIL_NECROMANCY = ITEMS.register("sigil_necromancy", () -> new SigilItem(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> SIGIL_TRANSMUTATION = ITEMS.register("sigil_transmutation", () -> new SigilItem(Constants.ITEM_PROPS));

    // Crystals
    public static final RegistryObject<Item> CRYSTAL_ARCANE = ITEMS.register("crystal_arcane", () -> new CrystalItem(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> CRYSTAL_INERT = ITEMS.register("crystal_inert", () -> new CrystalItem(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> CRYSTAL_CARNELIAN = ITEMS.register("crystal_carnelian", () -> new CrystalItem(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> CRYSTAL_CHALCEDONY = ITEMS.register("crystal_chalcedony", () -> new CrystalItem(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> CRYSTAL_SUGILITE = ITEMS.register("crystal_sugilite", () -> new CrystalItem(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> CRYSTAL_JASPER = ITEMS.register("crystal_jasper", () -> new CrystalItem(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> CRYSTAL_SERPENTINE = ITEMS.register("crystal_serpentine", () -> new CrystalItem(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> CRYSTAL_NUUMMITE = ITEMS.register("crystal_nuummite", () -> new CrystalItem(Constants.ITEM_PROPS));

    // Geode
    public static final RegistryObject<Item> GEODE = ITEMS.register("geode", () -> new GeodeItem(Constants.ITEM_PROPS));

    // Crafting Items
    public static final RegistryObject<Item> SCROLL_INERT = ITEMS.register("scroll_inert", () -> new Item(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> SORCEROUS_CATALYST = ITEMS.register("sorcerous_catalyst", () -> new Item(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> ARCANE_MUTAGEN = ITEMS.register("arcane_mutagen", () -> new ArcaneMutagenItem());

    // Utility Items
    public static final RegistryObject<Item> CRYSTAL_RESONATOR = ITEMS.register("crystal_resonator", () -> new CrystalResonatorItem(Constants.ITEM_PROPS));
    public static final RegistryObject<Item> ARCANE_ASSEMBLER = ITEMS.register("arcane_assembler", () -> new WandItem(ModSpell.SPELL_ADVANCED_CRAFT, 2500, Constants.ITEM_PROPS_NONSTACK));

    public static final RegistryObject<Item> SPELL_PROJECTILE = ITEMS.register("spell_projectile", () -> new Item(Constants.ITEM_PROPS));

    // Staves
    public static final RegistryObject<Item> SORCEROUS_STAFF = ITEMS.register("sorcerous_staff", () -> new StaffItem(Constants.ITEM_PROPS));

    //Spellbook
    public static final RegistryObject<Item> SPELL_BOOK = ITEMS.register("spell_book", () -> new SpellbookItem(Constants.ITEM_PROPS));


    // Spell Scrolls

    // -- Testing
    public static final RegistryObject<Item> REMOVE_ARCANA_SPELL_SCROLL = ITEMS.register("remove_arcana_spell_scroll", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_REMOVE_ARCANA));
    public static final RegistryObject<Item> SCROLL_TEST_STRUCTURE = ITEMS.register("scroll_test_structure", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_TEST_STRUCTURE));

    // -- Pre-Iron
    public static final RegistryObject<Item> SCROLL_LESSER_DIG = ITEMS.register("scroll_lesser_dig", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_LESSER_DIG));
    public static final RegistryObject<Item> SCROLL_PLANT_DEATH = ITEMS.register("scroll_plant_death", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_PLANT_DEATH));
    public static final RegistryObject<Item> SCROLL_PLANT_GROWTH = ITEMS.register("scroll_plant_growth", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_PLANT_GROWTH));
    public static final RegistryObject<Item> SCROLL_CHILLING_TOUCH = ITEMS.register("scroll_chilling_touch", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_CHILLING_TOUCH));
    public static final RegistryObject<Item> SCROLL_FIERY_TOUCH = ITEMS.register("scroll_fiery_touch", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_FIERY_TOUCH));
    // -- Iron
    public static final RegistryObject<Item> SCROLL_COBBLE_PLACEMENT = ITEMS.register("scroll_cobble_placement", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_COBBLE_PLACEMENT));
    public static final RegistryObject<Item> SCROLL_LESSER_FIREBOLT = ITEMS.register("scroll_lesser_firebolt", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_LESSER_FIREBOLT));
    public static final RegistryObject<Item> SCROLL_SIGNAL_FLARE = ITEMS.register("scroll_signal_flare", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_SIGNAL_FLARE));
    public static final RegistryObject<Item> SCROLL_LESSER_HEAL = ITEMS.register("scroll_lesser_heal", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_LESSER_HEAL));
    public static final RegistryObject<Item> SCROLL_LESSER_SHUNT = ITEMS.register("scroll_lesser_shunt", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_LESSER_SHUNT));
    public static final RegistryObject<Item> SCROLL_LESSER_SLOW = ITEMS.register("scroll_lesser_slow", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_LESSER_SLOW));
    // -- Diamond
    public static final RegistryObject<Item> SCROLL_COMBUSTION = ITEMS.register("scroll_combustion", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_COMBUSTION));
    public static final RegistryObject<Item> SCROLL_DIG = ITEMS.register("scroll_dig", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_DIG));
    public static final RegistryObject<Item> SCROLL_DRAIN_LIFE = ITEMS.register("scroll_drain_life", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_DRAIN_LIFE));
    public static final RegistryObject<Item> SCROLL_EARTHEN_WALL = ITEMS.register("scroll_earthen_wall", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_EARTHEN_WALL));
    public static final RegistryObject<Item> SCROLL_IGNITE = ITEMS.register("scroll_ignite", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_IGNITE));
    public static final RegistryObject<Item> SCROLL_LESSER_FEATHER_FALL = ITEMS.register("scroll_lesser_feather_fall", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_LESSER_FEATHER_FALL));
    public static final RegistryObject<Item> SCROLL_MAGIC_MISSILE = ITEMS.register("scroll_magic_missile", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_MAGIC_MISSILE));
    public static final RegistryObject<Item> SCROLL_SEISMIC_ECHO = ITEMS.register("scroll_seismic_echo", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_SEISMIC_ECHO));
    public static final RegistryObject<Item> SCROLL_STONEFLESH = ITEMS.register("scroll_stoneflesh", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_STONEFLESH));

    // -- Nether
    public static final RegistryObject<Item> SCROLL_CREATE_WATER = ITEMS.register("scroll_create_water", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_CREATE_WATER));
    public static final RegistryObject<Item> SCROLL_REPEL = ITEMS.register("scroll_repel", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_REPEL));
    public static final RegistryObject<Item> SCROLL_SPEED = ITEMS.register("scroll_speed", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_SPEED));
    public static final RegistryObject<Item> SCROLL_TRANSMUTE_STONE = ITEMS.register("scroll_transmute_stone", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_TRANSMUTE_STONE));
    public static final RegistryObject<Item> SCROLL_RAY_OF_FROST = ITEMS.register("scroll_ray_of_frost", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_RAY_OF_FROST));
    public static final RegistryObject<Item> SCROLL_COBBLECOON = ITEMS.register("scroll_cobblecoon", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_COBBLECOON));
    public static final RegistryObject<Item> SCROLL_ACID_RAIN = ITEMS.register("scroll_acid_rain", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_ACID_RAIN));
    public static final RegistryObject<Item> SCROLL_FIRE_NOVA = ITEMS.register("scroll_fire_nova", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_FIRE_NOVA));
    // -- Netherite
    public static final RegistryObject<Item> SCROLL_TUNNEL = ITEMS.register("scroll_tunnel", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_TUNNEL));
    public static final RegistryObject<Item> SCROLL_STAIR_DOWN = ITEMS.register("scroll_stair_down", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_STAIR_DOWN));
    // -- End
    public static final RegistryObject<Item> SCROLL_BLINK = ITEMS.register("scroll_blink", () -> new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, ModSpell.SPELL_BLINK));


    // Wands
    // -- Utility
    public static final RegistryObject<Item> WAND_CORE_INITIATE = ITEMS.register("wand_core_initiate", () -> new WandItem(ModSpell.SPELL_WAND_CRAFT_0, 2500));
    public static final RegistryObject<Item> WAND_CORE_APPRENTICE = ITEMS.register("wand_core_apprentice", () -> new WandItem(ModSpell.SPELL_WAND_CRAFT_1, 2500));
    public static final RegistryObject<Item> WAND_CORE_JOURNEYMAN = ITEMS.register("wand_core_journeyman", () -> new WandItem(ModSpell.SPELL_WAND_CRAFT_2, 2500));
    public static final RegistryObject<Item> WAND_CORE_SORCERER = ITEMS.register("wand_core_sorcerer", () -> new WandItem(ModSpell.SPELL_WAND_CRAFT_3, 2500));
    public static final RegistryObject<Item> WAND_CORE_MASTER_SORCERER = ITEMS.register("wand_core_master_sorcerer", () -> new WandItem(ModSpell.SPELL_WAND_CRAFT_4, 2500));
    public static final RegistryObject<Item> WAND_CORE_ARCHSORCERER = ITEMS.register("wand_core_archsorcerer", () -> new WandItem(ModSpell.SPELL_WAND_CRAFT_5, 2500));

    // -- Pre-Iron
    public static final RegistryObject<Item> WAND_LESSER_DIG = ITEMS.register("wand_lesser_dig", () -> new WandItem(ModSpell.SPELL_LESSER_DIG, 2500));
    public static final RegistryObject<Item> WAND_PLANT_DEATH = ITEMS.register("wand_plant_death", () -> new WandItem(ModSpell.SPELL_PLANT_DEATH));
    public static final RegistryObject<Item> WAND_PLANT_GROWTH = ITEMS.register("wand_plant_life", () -> new WandItem(ModSpell.SPELL_PLANT_GROWTH));
    public static final RegistryObject<Item> WAND_CHILLING_TOUCH = ITEMS.register("wand_chilling_touch", () -> new WandItem(ModSpell.SPELL_CHILLING_TOUCH));
    // -- Iron
    public static final RegistryObject<Item> WAND_COBBLE_PLACEMENT = ITEMS.register("wand_cobble_placement", () -> new WandItem(ModSpell.SPELL_COBBLE_PLACEMENT));
    public static final RegistryObject<Item> WAND_LESSER_FIREBOLT = ITEMS.register("wand_lesser_firebolt", () -> new WandItem(ModSpell.SPELL_LESSER_FIREBOLT));
    public static final RegistryObject<Item> WAND_LESSER_SHUNT = ITEMS.register("wand_lesser_shunt", () -> new WandItem(ModSpell.SPELL_LESSER_SHUNT));
    public static final RegistryObject<Item> WAND_LESSER_HEAL = ITEMS.register("wand_lesser_heal", () -> new WandItem(ModSpell.SPELL_LESSER_HEAL));
    public static final RegistryObject<Item> WAND_LESSER_SLOW = ITEMS.register("wand_lesser_slow", () -> new WandItem(ModSpell.SPELL_LESSER_SLOW));
    public static final RegistryObject<Item> WAND_SIGNAL_FLARE = ITEMS.register("wand_signal_flare", () -> new WandItem(ModSpell.SPELL_SIGNAL_FLARE));
    // -- Diamond
    public static final RegistryObject<Item> WAND_COMBUSTION = ITEMS.register("wand_combustion", () -> new WandItem(ModSpell.SPELL_COMBUSTION));
    public static final RegistryObject<Item> WAND_DIG = ITEMS.register("wand_dig", () -> new WandItem(ModSpell.SPELL_DIG));
    public static final RegistryObject<Item> WAND_DRAIN_LIFE = ITEMS.register("wand_drain_life", () -> new WandItem(ModSpell.SPELL_DRAIN_LIFE));
    public static final RegistryObject<Item> WAND_EARTHEN_WALL = ITEMS.register("wand_earthen_wall", () -> new WandItem(ModSpell.SPELL_EARTHEN_WALL));
    public static final RegistryObject<Item> WAND_IGNITE = ITEMS.register("wand_ignite", () -> new WandItem(ModSpell.SPELL_IGNITE));
    public static final RegistryObject<Item> WAND_LESSER_FEATHER_FALL = ITEMS.register("wand_lesser_feather_fall", () -> new WandItem(ModSpell.SPELL_LESSER_FEATHER_FALL));
    public static final RegistryObject<Item> WAND_MAGIC_MISSILE = ITEMS.register("wand_magic_missile", () -> new WandItem(ModSpell.SPELL_MAGIC_MISSILE));
    public static final RegistryObject<Item> WAND_SEISMIC_ECHO = ITEMS.register("wand_seismic_echo", () -> new WandItem(ModSpell.SPELL_SEISMIC_ECHO));
    public static final RegistryObject<Item> WAND_STONEFLESH = ITEMS.register("wand_stoneflesh", () -> new WandItem(ModSpell.SPELL_STONEFLESH));

    // -- Nether
    public static final RegistryObject<Item> WAND_CREATE_WATER = ITEMS.register("wand_create_water", () -> new WandItem(ModSpell.SPELL_CREATE_WATER));
    // -- Netherite
    // -- End

    // Block Items
    public static final RegistryObject<Item> POLISHED_RUNESTONE = ITEMS.register("polished_runestone", () -> new BlockItem(ModBlock.POLISHED_RUNESTONE.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> RUNEWOOD_LOG = ITEMS.register("runewood_log", () -> new BlockItem(ModBlock.RUNEWOOD_LOG.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> STRIPPED_RUNEWOOD_LOG = ITEMS.register("stripped_runewood_log", () -> new BlockItem(ModBlock.STRIPPED_RUNEWOOD_LOG.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> RUNEWOOD_LEAVES = ITEMS.register("runewood_leaves", () -> new BlockItem(ModBlock.RUNEWOOD_LEAVES.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> RUNEWOOD_SAPLING = ITEMS.register("runewood_sapling", () -> new BlockItem(ModBlock.RUNEWOOD_SAPLING.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> RUNESTONE_BRICKS = ITEMS.register("runestone_bricks", () -> new BlockItem(ModBlock.RUNESTONE_BRICKS.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> RUNESTONE_BRICK_SLAB = ITEMS.register("runestone_brick_slab", () -> new BlockItem(ModBlock.RUNESTONE_BRICK_SLAB.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> RUNESTONE_BRICK_STAIRS = ITEMS.register("runestone_brick_stairs", () -> new BlockItem(ModBlock.RUNESTONE_BRICK_STAIRS.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> RUNESTONE_BRICK_WALL = ITEMS.register("runestone_brick_wall", () -> new BlockItem(ModBlock.RUNESTONE_BRICK_WALL.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> RUNEWOOD_PLANKS = ITEMS.register("runewood_planks", () -> new BlockItem(ModBlock.RUNEWOOD_PLANKS.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> RUNEWOOD_PLANK_SLAB = ITEMS.register("runewood_plank_slab", () -> new BlockItem(ModBlock.RUNEWOOD_PLANK_SLAB.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> RUNEWOOD_PLANK_STAIRS = ITEMS.register("runewood_plank_stairs", () -> new BlockItem(ModBlock.RUNEWOOD_PLANK_STAIRS.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> RUNEWOOD_PLANK_FENCE = ITEMS.register("runewood_plank_fence", () -> new BlockItem(ModBlock.RUNEWOOD_PLANK_FENCE.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> CHISELED_RUNESTONE = ITEMS.register("chiseled_runestone", () -> new BlockItem(ModBlock.CHISELED_RUNESTONE.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> DARK_RUNESTONE = ITEMS.register("dark_runestone", () -> new BlockItem(ModBlock.DARK_RUNESTONE.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> LAPIS_RUNESTONE = ITEMS.register("lapis_runestone", () -> new BlockItem(ModBlock.LAPIS_RUNESTONE.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> LUNAR_RUNESTONE = ITEMS.register("lunar_runestone", () -> new BlockItem(ModBlock.LUNAR_RUNESTONE.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> SOLAR_RUNESTONE = ITEMS.register("solar_runestone", () -> new BlockItem(ModBlock.SOLAR_RUNESTONE.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> WOLFRAM_LANTERN = ITEMS.register("wolfram_lantern", () -> new BlockItem(ModBlock.WOLFRAM_LANTERN.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> PYLON = ITEMS.register("pylon", () -> new BlockItem(ModBlock.PYLON.get(), Constants.ITEM_PROPS));

    // Monolith Pieces
    public static final RegistryObject<Item> MONOLITH_CHISELED_BOTTOM = ITEMS.register("monolith_chiseled_bottom", () -> new BlockItem(ModBlock.MONOLITH_CHISELED_BOTTOM.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> MONOLITH_CHISELED_MIDDLE = ITEMS.register("monolith_chiseled_middle", () -> new BlockItem(ModBlock.MONOLITH_CHISELED_MIDDLE.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> MONOLITH_CHISELED_TOP = ITEMS.register("monolith_chiseled_top", () -> new BlockItem(ModBlock.MONOLITH_CHISELED_TOP.get(), Constants.ITEM_PROPS));

    public static final RegistryObject<Item> MONOLITH_DARK_BOTTOM = ITEMS.register("monolith_dark_bottom", () -> new BlockItem(ModBlock.MONOLITH_DARK_BOTTOM.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> MONOLITH_DARK_MIDDLE = ITEMS.register("monolith_dark_middle", () -> new BlockItem(ModBlock.MONOLITH_DARK_MIDDLE.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> MONOLITH_DARK_TOP = ITEMS.register("monolith_dark_top", () -> new BlockItem(ModBlock.MONOLITH_DARK_TOP.get(), Constants.ITEM_PROPS));

    public static final RegistryObject<Item> MONOLITH_LAPIS_BOTTOM = ITEMS.register("monolith_lapis_bottom", () -> new BlockItem(ModBlock.MONOLITH_LAPIS_BOTTOM.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> MONOLITH_LAPIS_MIDDLE = ITEMS.register("monolith_lapis_middle", () -> new BlockItem(ModBlock.MONOLITH_LAPIS_MIDDLE.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> MONOLITH_LAPIS_TOP = ITEMS.register("monolith_lapis_top", () -> new BlockItem(ModBlock.MONOLITH_LAPIS_TOP.get(), Constants.ITEM_PROPS));

    public static final RegistryObject<Item> MONOLITH_LUNAR_BOTTOM = ITEMS.register("monolith_lunar_bottom", () -> new BlockItem(ModBlock.MONOLITH_LUNAR_BOTTOM.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> MONOLITH_LUNAR_MIDDLE = ITEMS.register("monolith_lunar_middle", () -> new BlockItem(ModBlock.MONOLITH_LUNAR_MIDDLE.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> MONOLITH_LUNAR_TOP = ITEMS.register("monolith_lunar_top", () -> new BlockItem(ModBlock.MONOLITH_LUNAR_TOP.get(), Constants.ITEM_PROPS));

    public static final RegistryObject<Item> MONOLITH_SOLAR_BOTTOM = ITEMS.register("monolith_solar_bottom", () -> new BlockItem(ModBlock.MONOLITH_SOLAR_BOTTOM.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> MONOLITH_SOLAR_MIDDLE = ITEMS.register("monolith_solar_middle", () -> new BlockItem(ModBlock.MONOLITH_SOLAR_MIDDLE.get(), Constants.ITEM_PROPS));
    public static final RegistryObject<Item> MONOLITH_SOLAR_TOP = ITEMS.register("monolith_solar_top", () -> new BlockItem(ModBlock.MONOLITH_SOLAR_TOP.get(), Constants.ITEM_PROPS));

    public static final RegistryObject<Item> CRAFT_BLOCK = ITEMS.register("craft_block", () -> new BlockItem(ModBlock.CRAFT_BLOCK.get(), Constants.ITEM_PROPS));


    public static void init()
    {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
