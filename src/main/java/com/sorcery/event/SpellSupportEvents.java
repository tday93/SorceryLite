package com.sorcery.event;

import com.sorcery.potion.ModEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SpellSupportEvents
{
    @SubscribeEvent
    public static void onPlayerDamageEvent(LivingHurtEvent event)
    {
        if (event.getEntity() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity)event.getEntity();

            // Feather falling spell handling
            if (event.getSource() == DamageSource.FALL)
            {
                EffectInstance effectInstance = player.getActivePotionEffect(ModEffect.FEATHER_FALLING);
                if (effectInstance != null)
                {
                   int level = effectInstance.getAmplifier();
                   switch (level)
                   {
                       case 1:
                           player.removeActivePotionEffect(ModEffect.FEATHER_FALLING);
                           break;
                       case 3:
                           player.addPotionEffect(new EffectInstance(ModEffect.FEATHER_FALLING, effectInstance.getDuration() + (30 * 20), 3));
                           break;
                   }
                   event.setCanceled(true);
                }
            }
        }
    }


}
