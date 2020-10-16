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

    public static final String CATEGORY_WORLDGEN = "worldgen";

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;


    // Spell config items
    public static ForgeConfigSpec.IntValue SPELL_BLINK_COST;
    public static ForgeConfigSpec.IntValue SPELL_BLINK_DISTANCE;

    public static ForgeConfigSpec.IntValue SPELL_COMBUSTION_COST;
    public static ForgeConfigSpec.IntValue SPELL_COMBUSTION_CAST_DURATION;
    public static ForgeConfigSpec.IntValue SPELL_COMBUSTION_DAMAGE;
    public static ForgeConfigSpec.IntValue SPELL_COMBUSTION_FIRE_DURATION;

    public static ForgeConfigSpec.IntValue SPELL_IGNITE_COST;

    public static ForgeConfigSpec.IntValue SPELL_PLANT_DEATH_COST;
    public static ForgeConfigSpec.IntValue SPELL_PLANT_DEATH_RANGE;

    public static ForgeConfigSpec.IntValue SPELL_REPEL_COST;
    public static ForgeConfigSpec.IntValue SPELL_REPEL_RANGE;
    public static ForgeConfigSpec.IntValue SPELL_REPEL_VELOCITY;


    public static ForgeConfigSpec.IntValue SPELL_SPEED_COST;
    public static ForgeConfigSpec.IntValue SPELL_SPEED_DURATION;

    public static ForgeConfigSpec.IntValue SPELL_FIREBOLT_COST;
    public static ForgeConfigSpec.IntValue SPELL_FIREBOLT_DAMAGE;
    public static ForgeConfigSpec.IntValue SPELL_FIREBOLT_FIRE_DURATION;

    public static ForgeConfigSpec.IntValue SPELL_CREATE_WATER_COST;

    public static ForgeConfigSpec.IntValue SPELL_ON_OFF_COST;


    // Monolith config items
    public static ForgeConfigSpec.IntValue MONOLITH_NORMAL_GENERATE;
    public static ForgeConfigSpec.IntValue MONOLITH_SOLAR_GENERATE;
    public static ForgeConfigSpec.IntValue MONOLITH_LUNAR_GENERATE;
    public static ForgeConfigSpec.IntValue MONOLITH_DARK_GENERATE;

    // Worldgen Config Items
    public static ForgeConfigSpec.IntValue WOLFRAMITE_COUNT;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_BOTTOM_OFFSET;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_TOP_OFFSET;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue WOLFRAMITE_CLUSTERS;


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

        SPELL_BLINK_COST = spellIntParamHelper("blink", "Cost",500, 0, Integer.MAX_VALUE);
        SPELL_BLINK_DISTANCE = spellIntParamHelper("blink", "Distance", 4, 2, 64);

        SPELL_COMBUSTION_COST = spellIntParamHelper("combustion", "Cost",10, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.comment("the damage done per tick");
        SPELL_COMBUSTION_DAMAGE = spellIntParamHelper("combustion", "Damage",1, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.comment("number of ticks this can be held for");
        SPELL_COMBUSTION_CAST_DURATION = spellIntParamHelper("combustion", "CastDuration",100, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.comment("fire duration in seconds");
        SPELL_COMBUSTION_FIRE_DURATION = spellIntParamHelper("combustion", "FireDuration",10, 0, Integer.MAX_VALUE);

        SPELL_IGNITE_COST = spellIntParamHelper("ignite", "Cost", 20, 0, Integer.MAX_VALUE);

        SPELL_PLANT_DEATH_COST = spellIntParamHelper("plantDeath", "Cost", 200, 0, Integer.MAX_VALUE);
        SPELL_PLANT_DEATH_RANGE = spellIntParamHelper("plantDeath", "Range", 8, 0, 64);

        SPELL_REPEL_COST = spellIntParamHelper("repel", "Cost", 200, 0, Integer.MAX_VALUE);
        SPELL_REPEL_RANGE = spellIntParamHelper("repel", "Range", 8, 0, 64);
        SPELL_REPEL_VELOCITY = spellIntParamHelper("repel", "Velocity", 5, 0, 80);

        SPELL_SPEED_COST = spellIntParamHelper("speed", "Cost", 200, 0, Integer.MAX_VALUE);
        SPELL_SPEED_DURATION = spellIntParamHelper("speed", "Duration", 100, 0, Integer.MAX_VALUE);

        SPELL_FIREBOLT_COST = spellIntParamHelper("firebolt", "Cost", 100, 0, Integer.MAX_VALUE);
        SPELL_FIREBOLT_DAMAGE = spellIntParamHelper("firebolt", "Cost", 3, 0, Integer.MAX_VALUE);
        SPELL_FIREBOLT_FIRE_DURATION = spellIntParamHelper("firebolt", "Cost", 3, 0, Integer.MAX_VALUE);

        SPELL_CREATE_WATER_COST = spellIntParamHelper("createWater", "Cost", 100, 0, Integer.MAX_VALUE);

        SPELL_ON_OFF_COST = spellIntParamHelper("onOff", "Cost", 100, 0, Integer.MAX_VALUE);


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
        return COMMON_BUILDER.comment(String.format("Spell: %s, Param: %s", spellName, paramName)).defineInRange(spellName + paramName, def, min, max);
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
