package com.sorcery.particle;

import com.sorcery.Constants;
import com.sorcery.block.ModBlock;
import com.sorcery.utils.ModColor;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

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
        return new RGBAParticleData(ModParticle.SIMPLE_PUFF, 1, 1, 1, 1);
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

    public static ParticleCollection getArcanaOrbs()
    {
        return getParticleSet(6);
    }

    public static ParticleCollection getArcanaOrbSparks()
    {
        return getParticleSet(5);
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
                collection.add(50, getColoredParticle(ModColor.ARCANA_PURPLE.getMainList(), ModParticle.SIMPLE_SPARK, 1.0f, age));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getLowList(), ModParticle.SIMPLE_SPARK, 1.0f, age));
                collection.add(25, getColoredParticle(ModColor.ARCANA_PURPLE.getHiList(), ModParticle.SIMPLE_SPARK, 1.0f, age));
                return collection;
            case 1:
                collection.add(50, getColoredParticle(ModColor.SOLAR_GOLD.getMainList(), ModParticle.ARCANA, 1.0f, age, false));
                collection.add(25, getColoredParticle(ModColor.SOLAR_GOLD.getLowList(), ModParticle.ARCANA, 1.0f, age, false));
                collection.add(25, getColoredParticle(ModColor.SOLAR_GOLD.getHiList(), ModParticle.ARCANA, 1.0f, age, false));
                return collection;
            case 2:
                collection.add(50, getColoredParticle(ModColor.LUNAR_SILVER.getMainList(), ModParticle.ARCANA, 1.0f, age, false));
                collection.add(25, getColoredParticle(ModColor.LUNAR_SILVER.getLowList(), ModParticle.ARCANA, 1.0f, age, false));
                collection.add(25, getColoredParticle(ModColor.LUNAR_SILVER.getHiList(), ModParticle.ARCANA, 1.0f, age, false));
                return collection;
            case 3:
                collection.add(50, getColoredParticle(ModColor.LAPIS_BLUE.getMainList(), ModParticle.ARCANA, 1.0f, age, false));
                collection.add(25, getColoredParticle(ModColor.LAPIS_BLUE.getLowList(), ModParticle.ARCANA, 1.0f, age, false));
                collection.add(25, getColoredParticle(ModColor.LAPIS_BLUE.getHiList(), ModParticle.ARCANA, 1.0f, age, false));
                return collection;
            case 4:
                collection.add(50, getColoredParticle(ModColor.BLOOD_RED.getMainList(), ModParticle.ARCANA, 1.0f, age));
                collection.add(25, getColoredParticle(ModColor.BLOOD_RED.getLowList(), ModParticle.ARCANA, 1.0f, age));
                collection.add(25, getColoredParticle(ModColor.BLOOD_RED.getHiList(), ModParticle.ARCANA, 1.0f, age));
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
            default:
                collection.add(100, getSpark());
                return collection;
        }

    }

}
