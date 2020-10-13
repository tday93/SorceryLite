package com.sorcery.spell;

import com.sorcery.Config;
import com.sorcery.Constants;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

public class ModSpell
{

    public static final DeferredRegister<Spell> SPELLS = DeferredRegister.create(Spell.class, Constants.MODID);


    // Testing Spells
    public static final RegistryObject<Spell> SPELL_DURATION = SPELLS.register("spell_duration", () -> new DurationSpell());
    public static final RegistryObject<Spell> SPELL_REMOVE_ARCANA = SPELLS.register("spell_remove_arcana", () -> new TestSpell("Arcana Removed!", 1000));
    public static final RegistryObject<Spell> SPELL_TEST = SPELLS.register("spell_test", () -> new TestSpell("poof!", 0));
    public static final RegistryObject<Spell> SPELL_ARCANA_DRAIN = SPELLS.register("spell_arcana_drain", () -> new ArcanaDrainSpell());

    // Pre-Iron Spells
    public static final RegistryObject<Spell> SPELL_LESSER_DIG = SPELLS.register("spell_lesser_dig", () -> new DigSpell(10, 6));
    public static final RegistryObject<Spell> SPELL_MEDITATE = SPELLS.register("spell_meditate", () -> new MeditateSpell());
    public static final RegistryObject<Spell> SPELL_PLANT_DEATH = SPELLS.register("spell_plant_death", () -> new PlantDeathSpell());
    public static final RegistryObject<Spell> SPELL_PLANT_LIFE = SPELLS.register("spell_plant_life", () -> new PlantLifeSpell(10));
    public static final RegistryObject<Spell> SPELL_CHILLING_TOUCH = SPELLS.register("spell_chilling_touch", () -> new ChillingTouchSpell(50));

    // Iron Spells
    public static final RegistryObject<Spell> SPELL_COBBLE_PLACEMENT = SPELLS.register("spell_cobble_placement", () -> new BlockPlacementSpell(10, (BlockItem)Items.COBBLESTONE));
    public static final RegistryObject<Spell> SPELL_LESSER_FIREBOLT = SPELLS.register("spell_lesser_firebolt", () -> new FireboltSpell());
    public static final RegistryObject<Spell> SPELL_LESSER_SHUNT = SPELLS.register("spell_lesser_shunt", () -> new ShuntSpell(10, 5));

    // Diamond Spells
    public static final RegistryObject<Spell> SPELL_COMBUSTION = SPELLS.register("spell_combustion", () -> new CombustionSpell());
    public static final RegistryObject<Spell> SPELL_IGNITE = SPELLS.register("spell_ignite", () -> new IgniteSpell());

    // Nether Spells
    public static final RegistryObject<Spell> SPELL_CREATE_WATER = SPELLS.register("spell_create_water", () -> new CreateWaterSpell());
    public static final RegistryObject<Spell> SPELL_REPEL = SPELLS.register("spell_repel", () -> new RepelSpell());
    public static final RegistryObject<Spell> SPELL_SPEED = SPELLS.register("spell_speed", () -> new PotionSpell(Effects.SPEED, Config.SPELL_SPEED_COST.get(), Config.SPELL_SPEED_DURATION.get()));

    // Netherite Spells
    // -- Tunnel Spell

    // End Spells
    public static final RegistryObject<Spell> SPELL_BLINK = SPELLS.register("spell_blink", () -> new BlinkSpell());


    public static void init()
    {
        System.out.println("Initing spells");
        SPELLS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


}
