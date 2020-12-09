package com.sorcery.spell;

import com.sorcery.Config;
import com.sorcery.Constants;
import com.sorcery.Sorcery;
import com.sorcery.potion.ModEffect;
import com.sorcery.utils.StructurePattern;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
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

    // Utility Spells
    public static final RegistryObject<Spell> SPELL_WAND_CRAFT_0 = SPELLS.register("spell_wand_craft_0", () -> new WandCraftSpell(0, SpellTier.INITIATE, SpellSchool.ENCHANTMENT));
    public static final RegistryObject<Spell> SPELL_WAND_CRAFT_1 = SPELLS.register("spell_wand_craft_1", () -> new WandCraftSpell(0, SpellTier.APPRENTICE, SpellSchool.ENCHANTMENT));
    public static final RegistryObject<Spell> SPELL_WAND_CRAFT_2 = SPELLS.register("spell_wand_craft_2", () -> new WandCraftSpell(0, SpellTier.JOURNEYMAN, SpellSchool.ENCHANTMENT));
    public static final RegistryObject<Spell> SPELL_WAND_CRAFT_3 = SPELLS.register("spell_wand_craft_3", () -> new WandCraftSpell(0, SpellTier.SORCERER, SpellSchool.ENCHANTMENT));
    public static final RegistryObject<Spell> SPELL_WAND_CRAFT_4 = SPELLS.register("spell_wand_craft_4", () -> new WandCraftSpell(0, SpellTier.MASTER_SORCERER, SpellSchool.ENCHANTMENT));
    public static final RegistryObject<Spell> SPELL_WAND_CRAFT_5 = SPELLS.register("spell_wand_craft_5", () -> new WandCraftSpell(0, SpellTier.ARCHSORCERER, SpellSchool.ENCHANTMENT));

    public static final RegistryObject<Spell> SPELL_ADVANCED_CRAFT = SPELLS.register("spell_advanced_craft", () -> new AdvancedCraftSpell(200, SpellTier.ARCHSORCERER, SpellSchool.ENCHANTMENT));

    // Initiate (Pre-Iron) Spells - Initiate
    public static final RegistryObject<Spell> SPELL_CHILLING_TOUCH = SPELLS.register("spell_chilling_touch", () -> new ChillingTouchSpell(50, SpellTier.INITIATE, SpellSchool.EVOCATION));
    public static final RegistryObject<Spell> SPELL_LESSER_DIG = SPELLS.register("spell_lesser_dig", () -> new DigSpell(10, 6, Arrays.asList(ToolType.SHOVEL), SpellTier.INITIATE, SpellSchool.TRANSMUTATION));
    public static final RegistryObject<Spell> SPELL_MEDITATE = SPELLS.register("spell_meditate", () -> new MeditateSpell(SpellTier.INITIATE, SpellSchool.ENCHANTMENT));
    public static final RegistryObject<Spell> SPELL_PLANT_DEATH = SPELLS.register("spell_plant_death", () -> new PlantDeathSpell(SpellTier.INITIATE, SpellSchool.NECROMANCY));
    public static final RegistryObject<Spell> SPELL_PLANT_LIFE = SPELLS.register("spell_plant_life", () -> new PlantLifeSpell(10, SpellTier.INITIATE, SpellSchool.NECROMANCY));

    // Apprentice (Iron) Spells - Apprentice
    public static final RegistryObject<Spell> SPELL_COBBLE_PLACEMENT = SPELLS.register("spell_cobble_placement", () -> new BlockPlacementSpell(10, (BlockItem)Items.COBBLESTONE, SpellTier.APPRENTICE, SpellSchool.CONJURATION));
    public static final RegistryObject<Spell> SPELL_LESSER_FIREBOLT = SPELLS.register("spell_lesser_firebolt", () -> new FireboltSpell(SpellTier.APPRENTICE, SpellSchool.EVOCATION));
    public static final RegistryObject<Spell> SPELL_LESSER_HEAL = SPELLS.register("spell_lesser_heal", () -> new HealSpell(10, 4, 2, 80, SpellTier.APPRENTICE, SpellSchool.ABJURATION));
    public static final RegistryObject<Spell> SPELL_LESSER_SHUNT = SPELLS.register("spell_lesser_shunt", () -> new ShuntSpell(10, 5, SpellTier.APPRENTICE, SpellSchool.CONJURATION));
    public static final RegistryObject<Spell> SPELL_LESSER_SLOW = SPELLS.register("spell_lesser_slow", () -> new PotionSpell(10, Effects.SLOWNESS, 40, 1, false, SpellTier.APPRENTICE, SpellSchool.ENCHANTMENT));
    public static final RegistryObject<Spell> SPELL_SIGNAL_FLARE = SPELLS.register("spell_signal_flare", () -> new SignalFlareSpell(10, SpellTier.APPRENTICE, SpellSchool.CONJURATION));

    // Journeyman (Diamond) Spells - Journeyman
    public static final RegistryObject<Spell> SPELL_COMBUSTION = SPELLS.register("spell_combustion", () -> new CombustionSpell(SpellTier.JOURNEYMAN, SpellSchool.EVOCATION));
    public static final RegistryObject<Spell> SPELL_DIG = SPELLS.register("spell_dig", () -> new DigSpell(10, 7, Arrays.asList(ToolType.SHOVEL, ToolType.PICKAXE), SpellTier.JOURNEYMAN, SpellSchool.TRANSMUTATION));
    public static final RegistryObject<Spell> SPELL_DRAIN_LIFE = SPELLS.register("spell_drain_life", () -> new DrainLifeSpell(10, 4, SpellTier.JOURNEYMAN, SpellSchool.NECROMANCY));
    public static final RegistryObject<Spell> SPELL_EARTHEN_WALL = SPELLS.register("spell_earthen_wall", () -> new BlockMoveSpell(10, new Vector3i(3, 3, 1), new Vector3i(-1, -2, 0), new Vector3i(0, 3, 0), Constants.EARTHEN_WALL_BLOCKS, SpellTier.JOURNEYMAN, SpellSchool.CONJURATION));
    public static final RegistryObject<Spell> SPELL_IGNITE = SPELLS.register("spell_ignite", () -> new IgniteSpell(SpellTier.JOURNEYMAN, SpellSchool.EVOCATION));
    public static final RegistryObject<Spell> SPELL_LESSER_FEATHER_FALL = SPELLS.register("spell_lesser_feather_fall", () -> new PotionSpell(10, ModEffect.FEATHER_FALLING, (20 * 60 * 5), 0, true, SpellTier.JOURNEYMAN, SpellSchool.ENCHANTMENT));
    public static final RegistryObject<Spell> SPELL_MAGIC_MISSILE = SPELLS.register("spell_magic_missile", () -> new MagicMissileSpell(10, SpellTier.JOURNEYMAN, SpellSchool.EVOCATION));
    public static final RegistryObject<Spell> SPELL_SEISMIC_ECHO = SPELLS.register("spell_seismic_echo", () -> new SeismicEchoSpell(10, 8, SpellTier.JOURNEYMAN, SpellSchool.CONJURATION));
    public static final RegistryObject<Spell> SPELL_STONEFLESH = SPELLS.register("spell_stoneflesh", () -> new PotionSpell(10, ModEffect.STONEFLESH, (20 * 60 * 5), 0, true, SpellTier.JOURNEYMAN, SpellSchool.ABJURATION));

    // Sorcerer (Nether) Spells - Mage
    public static final RegistryObject<Spell> SPELL_CREATE_WATER = SPELLS.register("spell_create_water", () -> new CreateWaterSpell(SpellTier.SORCERER, SpellSchool.CONJURATION));
    public static final RegistryObject<Spell> SPELL_REPEL = SPELLS.register("spell_repel", () -> new RepelSpell(SpellTier.SORCERER, SpellSchool.EVOCATION));
    public static final RegistryObject<Spell> SPELL_SPEED = SPELLS.register("spell_speed", () -> new PotionSpell(Config.SPELL_SPEED_COST.get(), Effects.SPEED, Config.SPELL_SPEED_DURATION.get(), 1, true, SpellTier.SORCERER, SpellSchool.ENCHANTMENT));
    public static final RegistryObject<Spell> SPELL_TRANSMUTE_STONE = SPELLS.register("spell_transmute_stone", () -> new BlockTransmuteSpell(10, SpellTier.SORCERER, SpellSchool.TRANSMUTATION));
    public static final RegistryObject<Spell> SPELL_RAY_OF_FROST = SPELLS.register("spell_ray_of_frost", () -> new RayAttackSpell(1, 1, DamageSource.GENERIC, Effects.SLOWNESS, (20 * 3), 18, 19, SpellTier.SORCERER, SpellSchool.EVOCATION));
    public static final RegistryObject<Spell> SPELL_COBBLECOON = SPELLS.register("spell_cobblecoon", () -> new BlockStructureSpell(10, StructurePattern.COCOON_PATTERN, SpellTier.SORCERER, SpellSchool.CONJURATION));
    public static final RegistryObject<Spell> SPELL_ACID_RAIN = SPELLS.register("spell_acid_rain", () -> new ElementalRainSpell(10, ModEffect.CORROSION, 4, 8, 22,SpellTier.SORCERER, SpellSchool.EVOCATION));

    // Netherite Spells - Master Mage
    public static final RegistryObject<Spell> SPELL_FEATHER_FALL = SPELLS.register("spell_feather_fall", () -> new PotionSpell(10, ModEffect.FEATHER_FALLING, (20 * 60 * 5), 2, true, SpellTier.MASTER_SORCERER, SpellSchool.ENCHANTMENT));
    // -- Tunnel Spell
    // -- spell bomb

    // End Spells - Arch Mage
    public static final RegistryObject<Spell> SPELL_BLINK = SPELLS.register("spell_blink", () -> new BlinkSpell());


    public static void init()
    {
        Sorcery.getLogger().debug("Initing spells");
        SPELLS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }



}
