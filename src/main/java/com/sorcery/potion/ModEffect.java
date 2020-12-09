package com.sorcery.potion;

import com.sorcery.utils.ModColor;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("sorcery")
public class ModEffect
{

    @ObjectHolder("dimensional_fraying")
    public static Effect DIMENSIONAL_FRAYING;

    @ObjectHolder("healing_sickness")
    public static EffectMod HEALING_SICKNESS;

    @ObjectHolder("corrosion")
    public static Effect CORROSION;

    @ObjectHolder("necrosis")
    public static Effect NECROSIS;

    @ObjectHolder("feather_falling")
    public static Effect FEATHER_FALLING;

    @ObjectHolder("stoneflesh")
    public static Effect STONEFLESH;


    public static void init(RegistryEvent.Register<Effect> event)
    {
        registerEffect("dimensional_fraying", new EffectMod(EffectType.NEUTRAL, 13223375), event);

        registerEffect("healing_sickness", new EffectMod(EffectType.NEUTRAL, 13458603), event);

        registerEffect("corrosion", new EffectMod(EffectType.HARMFUL, 1531410).addAttributesModifier(Attributes.ARMOR, "d5dfa148-cd83-45db-adba-48b6a0925210", 0.75, AttributeModifier.Operation.MULTIPLY_TOTAL), event);

        registerEffect("necrosis", new EffectMod(EffectType.HARMFUL, 5378331).addAttributesModifier(Attributes.MAX_HEALTH, "8589daf4-84a8-4189-beee-57ffa27838bb", -2, AttributeModifier.Operation.ADDITION), event);

        registerEffect("feather_falling", new EffectMod(EffectType.BENEFICIAL, ModColor.ABJURATION_YELLOW.getMainDecimal()), event);

        registerEffect("stoneflesh", new EffectMod(EffectType.BENEFICIAL, ModColor.ENCHANTMENT_BLUE.getMainDecimal()).addAttributesModifier(Attributes.ARMOR, "5d2339f8-5863-427b-a1a3-e4affef3d943", 2, AttributeModifier.Operation.ADDITION), event);



    }

    public static void registerEffect(String registryName, Effect effect, RegistryEvent.Register<Effect> event)
    {
        effect.setRegistryName(registryName);
        event.getRegistry().register(effect);
    }
}
