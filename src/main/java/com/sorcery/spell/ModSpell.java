package com.sorcery.spell;

import com.sorcery.Config;
import com.sorcery.Constants;
import com.sorcery.Sorcery;
import com.sorcery.potion.ModEffect;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
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

    // Pre-Iron Spells
    public static final RegistryObject<Spell> SPELL_LESSER_DIG = SPELLS.register("spell_lesser_dig", () -> new DigSpell(10, 6, Arrays.asList(ToolType.SHOVEL)));
    public static final RegistryObject<Spell> SPELL_MEDITATE = SPELLS.register("spell_meditate", () -> new MeditateSpell());
    public static final RegistryObject<Spell> SPELL_PLANT_DEATH = SPELLS.register("spell_plant_death", () -> new PlantDeathSpell());
    public static final RegistryObject<Spell> SPELL_PLANT_LIFE = SPELLS.register("spell_plant_life", () -> new PlantLifeSpell(10));
    public static final RegistryObject<Spell> SPELL_CHILLING_TOUCH = SPELLS.register("spell_chilling_touch", () -> new ChillingTouchSpell(50));

    // Iron Spells
    public static final RegistryObject<Spell> SPELL_COBBLE_PLACEMENT = SPELLS.register("spell_cobble_placement", () -> new BlockPlacementSpell(10, (BlockItem)Items.COBBLESTONE));
    public static final RegistryObject<Spell> SPELL_LESSER_FIREBOLT = SPELLS.register("spell_lesser_firebolt", () -> new FireboltSpell());
    public static final RegistryObject<Spell> SPELL_LESSER_SHUNT = SPELLS.register("spell_lesser_shunt", () -> new ShuntSpell(10, 5));
    public static final RegistryObject<Spell> SPELL_LESSER_HEAL = SPELLS.register("spell_lesser_heal", () -> new HealSpell(10, 4, 2, 80));
    public static final RegistryObject<Spell> SPELL_LESSER_SLOW = SPELLS.register("spell_lesser_slow", () -> new PotionSpell(10, Effects.SLOWNESS, 40, 1));
    public static final RegistryObject<Spell> SPELL_SIGNAL_FLARE = SPELLS.register("spell_signal_flare", () -> new SignalFlareSpell(10));

    // Diamond Spells
    public static final RegistryObject<Spell> SPELL_COMBUSTION = SPELLS.register("spell_combustion", () -> new CombustionSpell());
    public static final RegistryObject<Spell> SPELL_IGNITE = SPELLS.register("spell_ignite", () -> new IgniteSpell());
    public static final RegistryObject<Spell> SPELL_MAGIC_MISSILE = SPELLS.register("spell_magic_missile", () -> new MagicMissileSpell(10));
    public static final RegistryObject<Spell> SPELL_DIG = SPELLS.register("spell_dig", () -> new DigSpell(10, 8, Arrays.asList(ToolType.SHOVEL, ToolType.PICKAXE)));
    // -- seismic echo
    // -- earth wall
    public static final RegistryObject<Spell> SPELL_EARTHEN_WALL = SPELLS.register("spell_earthen_wall", () -> new BlockMoveSpell(10, new Vector3i(2, 2, 0), new Vector3i(-1, -2, 0), new Vector3i(0, 3, 0)));

    // -- drain life
    public static final RegistryObject<Spell> SPELL_STONEFLESH = SPELLS.register("spell_stoneflesh", () -> new PotionSpell(10, ModEffect.STONEFLESH, (20 * 60 * 5), 1));
    public static final RegistryObject<Spell> SPELL_LESSER_FEATHER_FALL = SPELLS.register("spell_lesser_feather_fall", () -> new PotionSpell(10, ModEffect.FEATHER_FALLING, (20 * 60 * 5), 1));
    // -- confuse

    // Nether Spells
    public static final RegistryObject<Spell> SPELL_CREATE_WATER = SPELLS.register("spell_create_water", () -> new CreateWaterSpell());
    public static final RegistryObject<Spell> SPELL_REPEL = SPELLS.register("spell_repel", () -> new RepelSpell());
    public static final RegistryObject<Spell> SPELL_SPEED = SPELLS.register("spell_speed", () -> new PotionSpell(Config.SPELL_SPEED_COST.get(), Effects.SPEED, Config.SPELL_SPEED_DURATION.get(), 1));
    // -- Cocoon Spell

    // Netherite Spells
    public static final RegistryObject<Spell> SPELL_FEATHER_FALL = SPELLS.register("spell_feather_fall", () -> new PotionSpell(10, ModEffect.FEATHER_FALLING, (20 * 60 * 5), 2));
    // -- Tunnel Spell

    // End Spells
    public static final RegistryObject<Spell> SPELL_BLINK = SPELLS.register("spell_blink", () -> new BlinkSpell());


    public static void init()
    {
        Sorcery.getLogger().debug("Initing spells");
        SPELLS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


}
