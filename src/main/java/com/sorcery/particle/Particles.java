package com.sorcery.particle;

import com.sorcery.utils.ModColor;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;

import java.util.List;

/**
 * Helper methods to get common particles and sets of particles.
 *
 * This is structured this way to help keep the size of particle effect packets as small as possible.
 * Instead of a more descriptive name, they only need to store a single int to refer to a given particle collection.
 *
 * Additional helper methods exist for collections that will be called often by client-side-only constructs, such as passive generation monoliths.
 */
public class Particles
{

    public static IParticleData getSpark()
    {
        return new RGBAParticleData();
    }

    public static IParticleData getPuff()
    {
        return new RGBAParticleData(ModParticle.SIMPLE_PUFF, 1, 1, 1, 1, 10, true, false, 0.0f, 0.9f, false);
    }

    public static IParticleData getSkullSmoke()
    {
        return new RGBAParticleData(ModParticle.SKULL_SMOKE, 1, 1, 1, 0.7f);
    }

    public static IParticleData getSnowflake()
    {
        return new RGBAParticleData(ModParticle.SNOWFLAKE, 1, 1, 1, 0.8f);
    }

    public static IParticleData getColoredParticle(List<Integer> rgb, ParticleType type, float alpha, int age)
    {
        return new RGBAParticleData(type, ((float)rgb.get(0))/255f, ((float)rgb.get(1))/255f, ((float)rgb.get(2))/255f, alpha, age);
    }

    public static IParticleData getColoredParticle(List<Integer> rgb, ParticleType type, float alpha, int age, boolean doAnimation)
    {
        return new RGBAParticleData(type, ((float)rgb.get(0))/255f, ((float)rgb.get(1))/255f, ((float)rgb.get(2))/255f, alpha, age, doAnimation);
    }

    public static IParticleData getColoredParticle(List<Integer> rgb, ParticleType type, float alpha, int age, boolean doAnimation, boolean doCollision)
    {
        return new RGBAParticleData(type, ((float)rgb.get(0))/255f, ((float)rgb.get(1))/255f, ((float)rgb.get(2))/255f, alpha, age, doAnimation, doCollision);
    }

    public static IParticleData getColoredParticle(List<Integer> rgb, ParticleType type, float alpha, int age, boolean doAnimation, boolean doCollision, float gravity)
    {
        return new RGBAParticleData(type, ((float)rgb.get(0))/255f, ((float)rgb.get(1))/255f, ((float)rgb.get(2))/255f, alpha, age, doAnimation, doCollision, gravity);
    }

    public static IParticleData getColoredParticle(List<Integer> rgb, ParticleType type, float alpha, int age, boolean doAnimation, boolean doCollision, float gravity, float motionDamp)
    {
        return new RGBAParticleData(type, ((float)rgb.get(0))/255f, ((float)rgb.get(1))/255f, ((float)rgb.get(2))/255f, alpha, age, doAnimation, doCollision, gravity, motionDamp);
    }

    public static IParticleData getColoredParticle(List<Integer> rgb, ParticleType type, float alpha, int age, boolean doAnimation, boolean doCollision, float gravity, float motionDamp, boolean lit)
    {
        return new RGBAParticleData(type, ((float)rgb.get(0))/255f, ((float)rgb.get(1))/255f, ((float)rgb.get(2))/255f, alpha, age, doAnimation, doCollision, gravity, motionDamp, lit);
    }

    public static ParticleCollection getLunarSparks()
    {
        return getParticleSet(2);
    }

    public static ParticleCollection getSolarSparks()
    {
        return getParticleSet(1);
    }
    public static ParticleCollection getParticleSet(int set)
    {
        return getParticleSet(set, 20);
    }

    public static ParticleCollection getParticleSet(int set, int age)
    {
        ParticleCollection collection = new ParticleCollection();
        switch (set)
        {
            case 0:
                collection.add(50, getColoredParticle(ModColor.ARCANA_PURPLE.getMainList(), ModParticle.SIMPLE_SPARK, 1.0f, 40, true, true, 1.0f, 0.8f));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getLowList(), ModParticle.SIMPLE_SPARK, 1.0f, 40, true, true, 1.0f, 0.8f));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getHiList(), ModParticle.SIMPLE_SPARK, 1.0f, 40, true, true, 1.0f, 0.8f));
                return collection;
            case 1:
                collection.add(50, getColoredParticle(ModColor.SOLAR_GOLD.getMainList(), ModParticle.ARCANA, 1.0f, age, false, false, 0.0f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.SOLAR_GOLD.getLowList(), ModParticle.ARCANA, 1.0f, age, false, false, 0.0f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.SOLAR_GOLD.getHiList(), ModParticle.ARCANA, 1.0f, age, false, false, 0.0f, 1.0f, true));
                return collection;
            case 2:
                collection.add(50, getColoredParticle(ModColor.LUNAR_SILVER.getMainList(), ModParticle.ARCANA, 1.0f, age, false, false, 0.0f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.LUNAR_SILVER.getLowList(), ModParticle.ARCANA, 1.0f, age, false, false, 0.0f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.LUNAR_SILVER.getHiList(), ModParticle.ARCANA, 1.0f, age, false, false, 0.0f, 1.0f, true));
                return collection;
            case 3:
                collection.add(50, getColoredParticle(ModColor.LAPIS_BLUE.getMainList(), ModParticle.ARCANA, 1.0f, age, false, false, 0.0f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.LAPIS_BLUE.getLowList(), ModParticle.ARCANA, 1.0f, age, false, false, 0.0f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.LAPIS_BLUE.getHiList(), ModParticle.ARCANA, 1.0f, age, false, false, 0.0f, 1.0f, true));
                return collection;
            case 4:
                collection.add(50, getColoredParticle(ModColor.BLOOD_RED.getMainList(), ModParticle.ARCANA, 1.0f, age, false, false, 0.0f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.BLOOD_RED.getLowList(), ModParticle.ARCANA, 1.0f, age, false, false, 0.0f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.BLOOD_RED.getHiList(), ModParticle.ARCANA, 1.0f, age, false, false, 0.0f, 1.0f, true));
                return collection;
            case 5:
                collection.add(50, getColoredParticle(ModColor.ARCANA_PURPLE.getMainList(), ModParticle.ARCANA_SPARK_1, 0.7f, age));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getLowList(), ModParticle.ARCANA_SPARK_1, 0.7f, age));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getHiList(), ModParticle.ARCANA_SPARK_1, 0.7f, age));
                collection.add(50, getColoredParticle(ModColor.ARCANA_PURPLE.getMainList(), ModParticle.ARCANA_SPARK_3, 0.7f, age));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getLowList(), ModParticle.ARCANA_SPARK_3, 0.7f, age));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getHiList(), ModParticle.ARCANA_SPARK_3, 0.7f, age));
                return collection;
            case 6:
                collection.add(50, getColoredParticle(ModColor.ARCANA_PURPLE.getMainList(), ModParticle.ARCANA_ORB, 1.0f, age));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getLowList(), ModParticle.ARCANA_ORB, 1.0f, age));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getHiList(), ModParticle.ARCANA_ORB, 1.0f, age));
                return collection;
            case 7:
                collection.add(100, getSnowflake());
                return collection;
            case 8:
                collection.add(50, getColoredParticle(ModColor.ARCANA_PURPLE.getMainList(), ModParticle.ZAP_2, 0.5f, age));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getLowList(), ModParticle.ZAP_2, 0.5f, age));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getHiList(), ModParticle.ZAP_2, 0.5f, age));
                return collection;
            case 9:
                collection.add(50, getColoredParticle(ModColor.EVOCATION_RED.getMainList(), ModParticle.ZAP_2, 0.5f, age));
                collection.add(25, getColoredParticle(ModColor.EVOCATION_RED.getLowList(), ModParticle.ZAP_2, 0.5f, age));
                collection.add(25, getColoredParticle(ModColor.EVOCATION_RED.getHiList(), ModParticle.ZAP_2, 0.5f, age));
                return collection;
            case 10:
                collection.add(50, getColoredParticle(ModColor.TRANSMUTATION_GREEN.getMainList(), ModParticle.ZAP_2, 0.5f, age));
                collection.add(25, getColoredParticle(ModColor.TRANSMUTATION_GREEN.getLowList(), ModParticle.ZAP_2, 0.5f, age));
                collection.add(25, getColoredParticle(ModColor.TRANSMUTATION_GREEN.getHiList(), ModParticle.ZAP_2, 0.5f, age));
                return collection;
            case 11:
                collection.add(50, getColoredParticle(ModColor.ARCANA_PURPLE.getMainList(), ModParticle.ARCANA, 0.5f, age, false));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getLowList(), ModParticle.ARCANA, 0.5f, age, false));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getHiList(), ModParticle.ARCANA, 0.5f, age, false));
                return collection;
            case 12:
                collection.add(50, getColoredParticle(ModColor.CONJURATION_PURPLE.getMainList(), ModParticle.ARCANA, 0.5f, age, false, false, 0.0f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.CONJURATION_PURPLE.getLowList(), ModParticle.ARCANA, 0.5f, age, false, false, 0.0f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.CONJURATION_PURPLE.getHiList(), ModParticle.ARCANA, 0.5f, age, false, false, 0.0f, 1.0f, true));
                return collection;
            case 13:
                collection.add(50, getColoredParticle(ModColor.TRANSMUTATION_GREEN.getMainList(), ModParticle.BEAM, 1.0f, age, false, false, 0.0f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.TRANSMUTATION_GREEN.getLowList(), ModParticle.BEAM, 1.0f, age, false, false, 0.0f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.TRANSMUTATION_GREEN.getHiList(), ModParticle.BEAM, 1.0f, age, false, false, 0.0f, 1.0f, true));
                return collection;
            case 14:
                collection.add(50, getColoredParticle(ModColor.ARCANA_PURPLE.getMainList(), ModParticle.ARCANA, 0.1f, age, false));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getLowList(), ModParticle.ARCANA, 0.1f, age, false));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getHiList(), ModParticle.ARCANA, 0.1f, age, false));
                return collection;
            case 15:
                collection.add(50, getColoredParticle(ModColor.NECROMANCY_BLACK.getMainList(), ModParticle.SKULL_SMOKE, 0.8f, age, true));
                collection.add(25, getColoredParticle(ModColor.NECROMANCY_BLACK.getLowList(), ModParticle.SKULL_SMOKE, 0.8f, age, true));
                collection.add(25, getColoredParticle(ModColor.NECROMANCY_BLACK.getHiList(), ModParticle.SKULL_SMOKE, 0.8f, age, true));
                return collection;
            case 16:
                collection.add(50, getColoredParticle(ModColor.TRANSMUTATION_GREEN.getMainList(), ModParticle.ARCANA_BROWNIAN, 0.5f, age, false, false, 0.0f, 0.9f, true));
                collection.add(25, getColoredParticle(ModColor.TRANSMUTATION_GREEN.getLowList(), ModParticle.ARCANA_BROWNIAN, 0.5f, age, false, false, 0.0f, 0.9f, true));
                collection.add(25, getColoredParticle(ModColor.TRANSMUTATION_GREEN.getHiList(), ModParticle.ARCANA_BROWNIAN, 0.5f, age, false, false, 0.0f, 0.9f, true));
                return collection;
            case 17:
                // Brownian Arcana Conjuration
                collection.add(50, getColoredParticle(ModColor.CONJURATION_PURPLE.getMainList(), ModParticle.ARCANA_BROWNIAN, 0.5f, age, false, false, 0.0f, 0.9f, true));
                collection.add(25, getColoredParticle(ModColor.CONJURATION_PURPLE.getLowList(), ModParticle.ARCANA_BROWNIAN, 0.5f, age, false, false, 0.0f, 0.9f, true));
                collection.add(25, getColoredParticle(ModColor.CONJURATION_PURPLE.getHiList(), ModParticle.ARCANA_BROWNIAN, 0.5f, age, false, false, 0.0f, 0.9f, true));
                return collection;
            case 18:
                // Ice Beam
                collection.add(50, getColoredParticle(ModColor.LUNAR_SILVER.getMainList(), ModParticle.BEAM, 1.0f, age, false, false, 0.0f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.LUNAR_SILVER.getLowList(), ModParticle.BEAM, 1.0f, age, false, false, 0.0f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.LUNAR_SILVER.getHiList(), ModParticle.BEAM, 1.0f, age, false, false, 0.0f, 1.0f, true));
                return collection;
            case 19:
                // Falling snowflakes
                collection.add(50, getColoredParticle(ModColor.LUNAR_SILVER.getMainList(), ModParticle.SNOWFLAKE, 1.0f, age, true, true, 0.5f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.LUNAR_SILVER.getLowList(), ModParticle.SNOWFLAKE, 1.0f, age, true, true, 0.5f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.LUNAR_SILVER.getHiList(), ModParticle.SNOWFLAKE, 1.0f, age, true, true, 0.5f, 1.0f, true));
                return collection;
            case 20:
                // Arcana Enchantment Lit
                collection.add(50, getColoredParticle(ModColor.ENCHANTMENT_BLUE.getMainList(), ModParticle.ARCANA, 0.5f, age, false, false, 0.0f, 0.9f, true));
                collection.add(25, getColoredParticle(ModColor.ENCHANTMENT_BLUE.getLowList(), ModParticle.ARCANA, 0.5f, age, false, false, 0.0f, 0.9f, true));
                collection.add(25, getColoredParticle(ModColor.ENCHANTMENT_BLUE.getHiList(), ModParticle.ARCANA, 0.5f, age, false, false, 0.0f, 0.9f, true));
                return collection;
            case 21:
                // Arcana Arcana Lit
                collection.add(50, getColoredParticle(ModColor.ARCANA_PURPLE.getMainList(), ModParticle.ARCANA, 0.3f, age, false, false, 0.0f, 0.9f, false));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getLowList(), ModParticle.ARCANA, 0.3f, age, false, false, 0.0f, 0.9f, false));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getHiList(), ModParticle.ARCANA, 0.3f, age, false, false, 0.0f, 0.9f, false));
                return collection;
            case 22:
                collection.add(50, getColoredParticle(ModColor.TRANSMUTATION_GREEN.getMainList(), ModParticle.BEAM, 1.0f, age, false, true, 0.0f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.TRANSMUTATION_GREEN.getLowList(), ModParticle.BEAM, 1.0f, age, false, true, 0.0f, 1.0f, true));
                collection.add(25, getColoredParticle(ModColor.TRANSMUTATION_GREEN.getHiList(), ModParticle.BEAM, 1.0f, age, false, true, 0.0f, 1.0f, true));
                return collection;
            case 23:
                collection.add(100, ParticleTypes.FLAME);
                return collection;
            case 24:
                collection.add(100, ParticleTypes.SMOKE);
                return collection;
            case 25:
                // Abjuration Arcana Lit
                collection.add(50, getColoredParticle(ModColor.ABJURATION_YELLOW.getMainList(), ModParticle.ARCANA, 0.3f, age, false, false, 0.0f, 0.8f, false));
                collection.add(25, getColoredParticle(ModColor.ABJURATION_YELLOW.getLowList(), ModParticle.ARCANA, 0.3f, age, false, false, 0.0f, 0.8f, false));
                collection.add(25, getColoredParticle(ModColor.ABJURATION_YELLOW.getHiList(), ModParticle.ARCANA, 0.3f, age, false, false, 0.0f, 0.8f, false));
                return collection;
            default:
                collection.add(100, getSpark());
                return collection;
        }

    }

}
