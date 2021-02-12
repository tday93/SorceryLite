package com.sorcery;


import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Config
{

    public static final String CATEGORY_GENERAL = "general";

    public static final String CATEGORY_SPELLS = "spells";

    public static final String CATEGORY_MONOLITHS = "monoliths";

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;


    // Spell config items

    public static ForgeConfigSpec.IntValue SPELL_LESSER_DIG_COST;
    public static ForgeConfigSpec.IntValue SPELL_PLANT_DEATH_COST;
    public static ForgeConfigSpec.IntValue SPELL_PLANT_DEATH_RANGE;
    public static ForgeConfigSpec.IntValue SPELL_PLANT_GROWTH_COST;

    public static ForgeConfigSpec.IntValue SPELL_CHILLING_TOUCH_COST;
    public static ForgeConfigSpec.IntValue SPELL_ACIDIC_TOUCH_COST;
    public static ForgeConfigSpec.IntValue SPELL_FIERY_TOUCH_COST;

    public static ForgeConfigSpec.IntValue SPELL_COBBLE_PLACEMENT_COST;
    public static ForgeConfigSpec.IntValue SPELL_LESSER_SHUNT_COST;
    public static ForgeConfigSpec.IntValue SPELL_LESSER_SLOW_COST;
    public static ForgeConfigSpec.IntValue SPELL_SIGNAL_FLARE_COST;
    public static ForgeConfigSpec.IntValue SPELL_LESSER_HEAL_COST;

    public static ForgeConfigSpec.IntValue SPELL_LESSER_FIREBOLT_COST;
    public static ForgeConfigSpec.IntValue SPELL_LESSER_FIREBOLT_DAMAGE;
    public static ForgeConfigSpec.IntValue SPELL_LESSER_FIREBOLT_FIRE_DURATION;

    public static ForgeConfigSpec.IntValue SPELL_DIG_COST;
    public static ForgeConfigSpec.IntValue SPELL_EARTHEN_WALL_COST;
    public static ForgeConfigSpec.IntValue SPELL_IGNITE_COST;
    public static ForgeConfigSpec.IntValue SPELL_LESSER_FEATHER_FALL_COST;
    public static ForgeConfigSpec.IntValue SPELL_SEISMIC_ECHO_COST;
    public static ForgeConfigSpec.IntValue SPELL_STONEFLESH_COST;

    public static ForgeConfigSpec.IntValue SPELL_DRAIN_LIFE_COST;
    public static ForgeConfigSpec.IntValue SPELL_RAY_OF_ACID_COST;
    public static ForgeConfigSpec.IntValue SPELL_RAY_OF_FROST_COST;
    public static ForgeConfigSpec.IntValue SPELL_MAGIC_MISSILE_COST;

    public static ForgeConfigSpec.IntValue SPELL_CREATE_WATER_COST;
    public static ForgeConfigSpec.IntValue SPELL_REPEL_COST;
    public static ForgeConfigSpec.IntValue SPELL_REPEL_RANGE;
    public static ForgeConfigSpec.IntValue SPELL_REPEL_VELOCITY;
    public static ForgeConfigSpec.IntValue SPELL_SPEED_COST;
    public static ForgeConfigSpec.IntValue SPELL_SPEED_DURATION;
    public static ForgeConfigSpec.IntValue SPELL_TRANSMUTE_STONE_COST;
    public static ForgeConfigSpec.IntValue SPELL_COBBLECOON_COST;

    public static ForgeConfigSpec.IntValue SPELL_COMBUSTION_COST;
    public static ForgeConfigSpec.IntValue SPELL_COMBUSTION_CAST_DURATION;
    public static ForgeConfigSpec.IntValue SPELL_COMBUSTION_DAMAGE;
    public static ForgeConfigSpec.IntValue SPELL_COMBUSTION_FIRE_DURATION;
    public static ForgeConfigSpec.IntValue SPELL_FIRE_NOVA_COST;
    public static ForgeConfigSpec.IntValue SPELL_FROST_NOVA_COST;

    public static ForgeConfigSpec.IntValue SPELL_FEATHER_FALL_COST;
    public static ForgeConfigSpec.IntValue SPELL_TUNNEL_COST;
    public static ForgeConfigSpec.IntValue SPELL_STAIR_DOWN_COST;
    public static ForgeConfigSpec.IntValue SPELL_MENDING_COST;

    public static ForgeConfigSpec.IntValue SPELL_ACID_RAIN_COST;
    public static ForgeConfigSpec.IntValue SPELL_FIRE_RAIN_COST;

    public static ForgeConfigSpec.IntValue SPELL_BLINK_COST;
    public static ForgeConfigSpec.IntValue SPELL_BLINK_DISTANCE;
    public static ForgeConfigSpec.IntValue SPELL_RECALL_COST;
    public static ForgeConfigSpec.IntValue SPELL_FIREBURST_COST;
    public static ForgeConfigSpec.IntValue SPELL_FIREBURST_RANGE;
    public static ForgeConfigSpec.IntValue SPELL_FIREBURST_RADIUS;


    // Monolith config items
    public static ForgeConfigSpec.IntValue MONOLITH_NORMAL_GENERATE;
    public static ForgeConfigSpec.IntValue MONOLITH_SOLAR_GENERATE;
    public static ForgeConfigSpec.IntValue MONOLITH_LUNAR_GENERATE;
    public static ForgeConfigSpec.IntValue MONOLITH_DARK_GENERATE;


    static {

        COMMON_BUILDER.comment("General Settings").push(CATEGORY_GENERAL);
        COMMON_BUILDER.pop();

        spellConfig();

        monolithConfig();

        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();

    }

    private static void spellConfig()
    {

        COMMON_BUILDER.comment("Spell Configs").push(CATEGORY_SPELLS);

        SPELL_LESSER_DIG_COST = spellIntParamHelper("lesser_dig", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_PLANT_DEATH_COST = spellIntParamHelper("plant_death", "cost", 200, 0, Integer.MAX_VALUE);
        SPELL_PLANT_DEATH_RANGE = spellIntParamHelper("plant_death", "range", 8, 0, 64);
        SPELL_PLANT_GROWTH_COST = spellIntParamHelper("plant_growth", "cost", 100, 0, Integer.MAX_VALUE);

        SPELL_CHILLING_TOUCH_COST = spellIntParamHelper("chilling_touch", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_ACIDIC_TOUCH_COST = spellIntParamHelper("acidic_touch", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_FIERY_TOUCH_COST = spellIntParamHelper("fiery_touch", "cost", 100, 0, Integer.MAX_VALUE);

        SPELL_COBBLE_PLACEMENT_COST = spellIntParamHelper("cobble_placement", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_LESSER_SHUNT_COST = spellIntParamHelper("lesser_shunt", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_LESSER_SLOW_COST = spellIntParamHelper("lesser_slow", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_SIGNAL_FLARE_COST = spellIntParamHelper("signal_flare", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_LESSER_HEAL_COST = spellIntParamHelper("lesser_heal", "cost", 100, 0, Integer.MAX_VALUE);

        SPELL_LESSER_FIREBOLT_COST = spellIntParamHelper("lesser_firebolt", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_LESSER_FIREBOLT_DAMAGE = spellIntParamHelper("lesser_firebolt", "cost", 3, 0, Integer.MAX_VALUE);
        SPELL_LESSER_FIREBOLT_FIRE_DURATION = spellIntParamHelper("lesser_firebolt", "cost", 3, 0, Integer.MAX_VALUE);

        SPELL_DIG_COST = spellIntParamHelper("dig", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_EARTHEN_WALL_COST = spellIntParamHelper("earthen_wall", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_IGNITE_COST = spellIntParamHelper("ignite", "cost", 20, 0, Integer.MAX_VALUE);
        SPELL_LESSER_FEATHER_FALL_COST = spellIntParamHelper("lesser_feather_fall", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_SEISMIC_ECHO_COST = spellIntParamHelper("seismic_echo", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_STONEFLESH_COST = spellIntParamHelper("stoneflesh", "cost", 100, 0, Integer.MAX_VALUE);

        SPELL_DRAIN_LIFE_COST = spellIntParamHelper("drain_life", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_RAY_OF_ACID_COST = spellIntParamHelper("ray_of_acid", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_RAY_OF_FROST_COST = spellIntParamHelper("ray_of_frost", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_MAGIC_MISSILE_COST = spellIntParamHelper("magic_missile", "cost", 100, 0, Integer.MAX_VALUE);

        SPELL_CREATE_WATER_COST = spellIntParamHelper("create_water", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_REPEL_COST = spellIntParamHelper("repel", "cost", 200, 0, Integer.MAX_VALUE);
        SPELL_REPEL_RANGE = spellIntParamHelper("repel", "range", 8, 0, 64);
        SPELL_REPEL_VELOCITY = spellIntParamHelper("repel", "velocity", 5, 0, 80);
        SPELL_SPEED_COST = spellIntParamHelper("speed", "cost", 200, 0, Integer.MAX_VALUE);
        SPELL_SPEED_DURATION = spellIntParamHelper("speed", "duration", 100, 0, Integer.MAX_VALUE);
        SPELL_TRANSMUTE_STONE_COST = spellIntParamHelper("transmute_stone", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_COBBLECOON_COST = spellIntParamHelper("cobblecoon", "cost", 100, 0, Integer.MAX_VALUE);

        SPELL_COMBUSTION_COST = spellIntParamHelper("combustion", "cost",10, 0, Integer.MAX_VALUE);
        SPELL_COMBUSTION_DAMAGE = spellIntParamHelper("combustion", "damage",1, 0, Integer.MAX_VALUE);
        SPELL_COMBUSTION_CAST_DURATION = spellIntParamHelper("combustion", "cast_duration",100, 0, Integer.MAX_VALUE);
        SPELL_COMBUSTION_FIRE_DURATION = spellIntParamHelper("combustion", "fire_duration",10, 0, Integer.MAX_VALUE);
        SPELL_FIRE_NOVA_COST = spellIntParamHelper("fire_nova", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_FROST_NOVA_COST = spellIntParamHelper("frost_nova", "cost", 100, 0, Integer.MAX_VALUE);

        SPELL_FEATHER_FALL_COST = spellIntParamHelper("feather_fall", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_TUNNEL_COST = spellIntParamHelper("tunnel", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_STAIR_DOWN_COST = spellIntParamHelper("stair_down", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_MENDING_COST = spellIntParamHelper("stair_down", "cost", 100, 0, Integer.MAX_VALUE);

        SPELL_ACID_RAIN_COST = spellIntParamHelper("acid_rain", "cost", 100, 0, Integer.MAX_VALUE);
        SPELL_FIRE_RAIN_COST = spellIntParamHelper("fire_rain", "cost", 100, 0, Integer.MAX_VALUE);

        SPELL_BLINK_COST = spellIntParamHelper("blink", "cost",500, 0, Integer.MAX_VALUE);
        SPELL_BLINK_DISTANCE = spellIntParamHelper("blink", "distance", 4, 2, 64);

        SPELL_RECALL_COST = spellIntParamHelper("recall", "cost",500, 0, Integer.MAX_VALUE);
        SPELL_FIREBURST_COST = spellIntParamHelper("fireburst", "cost",500, 0, Integer.MAX_VALUE);
        SPELL_FIREBURST_RANGE = spellIntParamHelper("fireburst", "range",16, 0, 64);
        SPELL_FIREBURST_RADIUS = spellIntParamHelper("fireburst", "radius",8, 0, 64);
        // After every config added
        COMMON_BUILDER.pop();
    }

    private static void monolithConfig()
    {
        COMMON_BUILDER.comment("Monolith Configs").push(CATEGORY_MONOLITHS);

        MONOLITH_NORMAL_GENERATE = COMMON_BUILDER.comment("Normal Monolith Arcana per tick").defineInRange("monoNormalGen", 50, 0, Integer.MAX_VALUE);
        MONOLITH_SOLAR_GENERATE = COMMON_BUILDER.comment("Solar Monolith Arcana per tick").defineInRange("monoSolarGen", 50, 0, Integer.MAX_VALUE);
        MONOLITH_LUNAR_GENERATE = COMMON_BUILDER.comment("Lunar Monolith Arcana per tick").defineInRange("monoLunarGen", 50, 0, Integer.MAX_VALUE);
        MONOLITH_DARK_GENERATE = COMMON_BUILDER.comment("Dark Monolith Arcana per tick").defineInRange("monoDarkGen", 50, 0, Integer.MAX_VALUE);


        COMMON_BUILDER.pop();
    }

    // Convenience method for adding spell config items
    private static ForgeConfigSpec.IntValue spellIntParamHelper(String spellName, String paramName, int def, int min, int max)
    {
        return COMMON_BUILDER.defineInRange("spell_" + spellName + "_" + paramName, def, min, max);
    }



    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }

}
