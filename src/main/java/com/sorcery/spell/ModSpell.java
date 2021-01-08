package com.sorcery.spell;

import com.sorcery.Config;
import com.sorcery.Constants;
import com.sorcery.Sorcery;
import com.sorcery.potion.ModEffect;
import com.sorcery.spell.components.AcidElementalComponent;
import com.sorcery.spell.components.FireElementalComponent;
import com.sorcery.spell.components.FrostElementalComponent;
import com.sorcery.spell.components.SpellPhaseComponent;
import com.sorcery.utils.StructurePattern;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Arrays;

public class ModSpell
{

    public static final DeferredRegister<Spell> SPELLS = DeferredRegister.create(Spell.class, Constants.MODID);


    // Testing Spells
    public static final RegistryObject<Spell> SPELL_DURATION = SPELLS.register("spell_duration", () -> new DurationSpell());
    public static final RegistryObject<Spell> SPELL_REMOVE_ARCANA = SPELLS.register("spell_remove_arcana", () -> new TestSpell("Arcana Removed!", 1000));
    public static final RegistryObject<Spell> SPELL_TEST = SPELLS.register("spell_test", () -> new TestSpell("poof!", 0));
    public static final RegistryObject<Spell> SPELL_ARCANA_DRAIN = SPELLS.register("spell_arcana_drain", () -> new ArcanaDrainSpell());
    public static final RegistryObject<Spell> SPELL_TEST_STRUCTURE = SPELLS.register("spell_test_structure", () -> new BlockStructureSpell(10, SpellTier.ARCHSORCERER, SpellSchool.CONJURATION, StructurePattern.TEST_PATTERN, true, false, false, null));

    // Utility Spells
    public static final RegistryObject<Spell> SPELL_MEDITATE = SPELLS.register("spell_meditate", () -> new MeditateSpell(SpellTier.INITIATE, SpellSchool.ENCHANTMENT));

    public static final RegistryObject<Spell> SPELL_WAND_CRAFT_0 = SPELLS.register("spell_wand_craft_0", () -> new WandCraftSpell(0, SpellTier.INITIATE, SpellSchool.ENCHANTMENT));
    public static final RegistryObject<Spell> SPELL_WAND_CRAFT_1 = SPELLS.register("spell_wand_craft_1", () -> new WandCraftSpell(0, SpellTier.APPRENTICE, SpellSchool.ENCHANTMENT));
    public static final RegistryObject<Spell> SPELL_WAND_CRAFT_2 = SPELLS.register("spell_wand_craft_2", () -> new WandCraftSpell(0, SpellTier.JOURNEYMAN, SpellSchool.ENCHANTMENT));
    public static final RegistryObject<Spell> SPELL_WAND_CRAFT_3 = SPELLS.register("spell_wand_craft_3", () -> new WandCraftSpell(0, SpellTier.SORCERER, SpellSchool.ENCHANTMENT));
    public static final RegistryObject<Spell> SPELL_WAND_CRAFT_4 = SPELLS.register("spell_wand_craft_4", () -> new WandCraftSpell(0, SpellTier.MASTER_SORCERER, SpellSchool.ENCHANTMENT));
    public static final RegistryObject<Spell> SPELL_WAND_CRAFT_5 = SPELLS.register("spell_wand_craft_5", () -> new WandCraftSpell(0, SpellTier.ARCHSORCERER, SpellSchool.ENCHANTMENT));

    public static final RegistryObject<Spell> SPELL_ADVANCED_CRAFT = SPELLS.register("spell_advanced_craft", () -> new AdvancedCraftSpell(200, SpellTier.ARCHSORCERER, SpellSchool.ENCHANTMENT));


    // Initiate (Pre-Iron) Spells
    // -- Non-Combat --
    public static final RegistryObject<Spell> SPELL_LESSER_DIG = SPELLS.register("spell_lesser_dig", () -> new DigSpell(10, SpellTier.INITIATE, SpellSchool.TRANSMUTATION, 6, Arrays.asList(ToolType.SHOVEL)));
    public static final RegistryObject<Spell> SPELL_PLANT_DEATH = SPELLS.register("spell_plant_death", () -> new PlantDeathSpell(Config.SPELL_PLANT_DEATH_COST.get(), SpellTier.INITIATE, SpellSchool.NECROMANCY));
    public static final RegistryObject<Spell> SPELL_PLANT_GROWTH = SPELLS.register("spell_plant_growth", () -> new PlantGrowthSpell(10, SpellTier.INITIATE, SpellSchool.NECROMANCY));
    // -- Combat -- (Touches)
    public static final RegistryObject<Spell> SPELL_CHILLING_TOUCH = SPELLS.register("spell_chilling_touch", () -> new ElementalTouchSpell(50, SpellTier.INITIATE, SpellSchool.EVOCATION, new FrostElementalComponent(3, 30, 1), 19, SoundEvents.BLOCK_SNOW_PLACE));
    public static final RegistryObject<Spell> SPELL_ACIDIC_TOUCH = SPELLS.register("spell_acidic_touch", () -> new ElementalTouchSpell(50, SpellTier.INITIATE, SpellSchool.EVOCATION, new AcidElementalComponent(3, 3, 1), 23, SoundEvents.BLOCK_WATER_AMBIENT));
    public static final RegistryObject<Spell> SPELL_FIERY_TOUCH = SPELLS.register("spell_fiery_touch", () -> new ElementalTouchSpell(50, SpellTier.INITIATE, SpellSchool.EVOCATION, new FireElementalComponent(3, 30), 23, SoundEvents.ITEM_FIRECHARGE_USE));

    // Apprentice (Iron) Spells
    // -- Non-Combat --
    public static final RegistryObject<Spell> SPELL_COBBLE_PLACEMENT = SPELLS.register("spell_cobble_placement", () -> new BlockPlacementSpell(10, SpellTier.APPRENTICE, SpellSchool.CONJURATION, (BlockItem)Items.COBBLESTONE));
    public static final RegistryObject<Spell> SPELL_LESSER_SHUNT = SPELLS.register("spell_lesser_shunt", () -> new ShuntSpell(10, SpellTier.APPRENTICE, SpellSchool.CONJURATION, 5));
    public static final RegistryObject<Spell> SPELL_LESSER_SLOW = SPELLS.register("spell_lesser_slow", () -> new PotionSpell(10, SpellTier.APPRENTICE, SpellSchool.ENCHANTMENT, Effects.SLOWNESS, 40, 1, false));
    public static final RegistryObject<Spell> SPELL_SIGNAL_FLARE = SPELLS.register("spell_signal_flare", () -> new SignalFlareSpell(10, SpellTier.APPRENTICE, SpellSchool.CONJURATION));
    public static final RegistryObject<Spell> SPELL_LESSER_HEAL = SPELLS.register("spell_lesser_heal", () -> new HealSpell(10, SpellTier.APPRENTICE, SpellSchool.ABJURATION, 4, 2, 80));
    // -- Combat -- (Bolts)
    public static final RegistryObject<Spell> SPELL_LESSER_FIREBOLT = SPELLS.register("spell_lesser_firebolt", () -> new FireboltSpell(Config.SPELL_FIREBOLT_COST.get(), SpellTier.APPRENTICE, SpellSchool.EVOCATION));

    // Journeyman (Diamond) Spells
    // -- Non-Combat --
    public static final RegistryObject<Spell> SPELL_DIG = SPELLS.register("spell_dig", () -> new DigSpell(10, SpellTier.JOURNEYMAN, SpellSchool.TRANSMUTATION, 7, Arrays.asList(ToolType.SHOVEL, ToolType.PICKAXE)));
    public static final RegistryObject<Spell> SPELL_EARTHEN_WALL = SPELLS.register("spell_earthen_wall", () -> new BlockMoveSpell(10, SpellTier.JOURNEYMAN, SpellSchool.CONJURATION, new Vector3i(3, 3, 1), new Vector3i(-1, -2, 0), new Vector3i(0, 3, 0), Constants.EARTHEN_WALL_BLOCKS));
    public static final RegistryObject<Spell> SPELL_IGNITE = SPELLS.register("spell_ignite", () -> new IgniteSpell(Config.SPELL_IGNITE_COST.get(), SpellTier.JOURNEYMAN, SpellSchool.EVOCATION));
    public static final RegistryObject<Spell> SPELL_LESSER_FEATHER_FALL = SPELLS.register("spell_lesser_feather_fall", () -> new PotionSpell(10, SpellTier.JOURNEYMAN, SpellSchool.ENCHANTMENT, ModEffect.FEATHER_FALLING, (20 * 60 * 5), 0, true));
    public static final RegistryObject<Spell> SPELL_SEISMIC_ECHO = SPELLS.register("spell_seismic_echo", () -> new SeismicEchoSpell(10, SpellTier.JOURNEYMAN, SpellSchool.CONJURATION, 8));
    public static final RegistryObject<Spell> SPELL_STONEFLESH = SPELLS.register("spell_stoneflesh", () -> new PotionSpell(10, SpellTier.JOURNEYMAN, SpellSchool.ABJURATION, ModEffect.STONEFLESH, (20 * 60 * 5), 0, true));
    // TODO: Pillar Spell

    // -- Combat -- (Rays)
    public static final RegistryObject<Spell> SPELL_DRAIN_LIFE = SPELLS.register("spell_drain_life", () -> new DrainLifeSpell(10, SpellTier.JOURNEYMAN, SpellSchool.NECROMANCY, 4));
    public static final RegistryObject<Spell> SPELL_RAY_OF_ACID = SPELLS.register("spell_ray_of_acid", () -> new ElementalRaySpell(1, SpellTier.JOURNEYMAN, SpellSchool.EVOCATION, new AcidElementalComponent(1, 3, 1)));
    public static final RegistryObject<Spell> SPELL_RAY_OF_FROST = SPELLS.register("spell_ray_of_frost", () -> new ElementalRaySpell(1, SpellTier.JOURNEYMAN, SpellSchool.EVOCATION, new FrostElementalComponent(1, 20, 1)));
    public static final RegistryObject<Spell> SPELL_MAGIC_MISSILE = SPELLS.register("spell_magic_missile", () -> new MagicMissileSpell(10, SpellTier.JOURNEYMAN, SpellSchool.EVOCATION));

    // Sorcerer (Nether) Spells
    // -- Non-Combat --
    public static final RegistryObject<Spell> SPELL_CREATE_WATER = SPELLS.register("spell_create_water", () -> new CreateWaterSpell(Config.SPELL_CREATE_WATER_COST.get(), SpellTier.SORCERER, SpellSchool.CONJURATION));
    public static final RegistryObject<Spell> SPELL_REPEL = SPELLS.register("spell_repel", () -> new RepelSpell(Config.SPELL_REPEL_COST.get(), SpellTier.SORCERER, SpellSchool.EVOCATION));
    public static final RegistryObject<Spell> SPELL_SPEED = SPELLS.register("spell_speed", () -> new PotionSpell(Config.SPELL_SPEED_COST.get(), SpellTier.SORCERER, SpellSchool.ENCHANTMENT, Effects.SPEED, Config.SPELL_SPEED_DURATION.get(), 1, true));
    public static final RegistryObject<Spell> SPELL_TRANSMUTE_STONE = SPELLS.register("spell_transmute_stone", () -> new BlockTransmuteSpell(10, SpellTier.SORCERER, SpellSchool.TRANSMUTATION));
    public static final RegistryObject<Spell> SPELL_COBBLECOON = SPELLS.register("spell_cobblecoon", () -> new BlockStructureSpell(10, SpellTier.SORCERER, SpellSchool.CONJURATION, StructurePattern.COCOON_PATTERN, true, false, false, null));

    // -- Combat -- (Cones + Novas)
    public static final RegistryObject<Spell> SPELL_COMBUSTION = SPELLS.register("spell_combustion", () -> new ElementalConeSpell(SpellTier.SORCERER, SpellSchool.EVOCATION, new FireElementalComponent(1, 10)));
    public static final RegistryObject<Spell> SPELL_FIRE_NOVA = SPELLS.register("spell_fire_nova", () -> new ElementalNovaSpell(10, SpellTier.SORCERER, SpellSchool.EVOCATION, new FireElementalComponent(4, 4), 6, 23));
    public static final RegistryObject<Spell> SPELL_FROST_NOVA = SPELLS.register("spell_frost_nova", () -> new ElementalNovaSpell(10, SpellTier.SORCERER, SpellSchool.EVOCATION, new FrostElementalComponent(4, 20, 1), 6, 23));


    // Master Sorcerer (Netherite) Spells
    // -- Non-Combat --
    public static final RegistryObject<Spell> SPELL_FEATHER_FALL = SPELLS.register("spell_feather_fall", () -> new PotionSpell(10, SpellTier.MASTER_SORCERER, SpellSchool.ENCHANTMENT, ModEffect.FEATHER_FALLING, (20 * 60 * 5), 2, true));
    public static final RegistryObject<Spell> SPELL_TUNNEL = SPELLS.register("spell_tunnel", () -> new BlockStructureSpell(10, SpellTier.MASTER_SORCERER, SpellSchool.CONJURATION, StructurePattern.TUNNEL_PATTERN, false, true, true, '~', 40));
    public static final RegistryObject<Spell> SPELL_STAIR_DOWN = SPELLS.register("spell_stair_down", () -> new BlockStructureSpell(10, SpellTier.MASTER_SORCERER, SpellSchool.CONJURATION, StructurePattern.STAIR_DOWN_PATTERN, false, true, true, '~', 40));
    // -- Combat -- (Rains)
    public static final RegistryObject<Spell> SPELL_ACID_RAIN = SPELLS.register("spell_acid_rain", () -> new ElementalRainSpell(10, SpellTier.MASTER_SORCERER, SpellSchool.EVOCATION, new AcidElementalComponent(1, 10, 1), 4, 8));
    public static final RegistryObject<Spell> SPELL_FIRE_RAIN = SPELLS.register("spell_fire_rain", () -> new ElementalRainSpell(10, SpellTier.MASTER_SORCERER, SpellSchool.EVOCATION, new FireElementalComponent(1, 10), 4, 8));

    // Archsorcerer (End) Spells
    // -- Non-Combat --
    // -- Combat -- (Nukes)
    public static final RegistryObject<Spell> SPELL_BLINK = SPELLS.register("spell_blink", () -> new BlinkSpell(50, SpellTier.ARCHSORCERER, SpellSchool.CONJURATION));


    public static void init()
    {
        Sorcery.getLogger().debug("Initing spells");
        SPELLS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }



}
