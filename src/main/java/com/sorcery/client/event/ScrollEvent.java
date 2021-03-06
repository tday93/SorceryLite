package com.sorcery.client.event;


import com.sorcery.Constants;
import com.sorcery.item.SpellcastingItem;
import com.sorcery.item.WandItem;
import com.sorcery.keybinding.KeyBindings;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.KeyPressPacket;
import com.sorcery.spellcasting.ISpellcasting;
import com.sorcery.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MODID)
public class ScrollEvent
{
    @SubscribeEvent
    public static void onMouseScrollEvent(InputEvent.MouseScrollEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        ItemStack heldItemStack = mc.player.getHeldItemMainhand();
        Item heldItem = heldItemStack.getItem();
        ItemStack spellBook = Utils.getPlayerSpellbook(mc.player);

        if (heldItem == null || !(heldItem instanceof SpellcastingItem) || !(KeyBindings.SPELL_SCROLL.isKeyDown()))
        {
            return;
        }

        ISpellcasting spellCap = null;

        if (spellBook != null)
        {
            spellCap = Utils.getSpellCap(spellBook);
        }
        if (heldItem instanceof WandItem)
        {
            spellCap = Utils.getSpellCap(heldItemStack);
        }
        if (spellCap == null)
        {
            return;
        }

        Double delta = event.getScrollDelta();

        if (delta > 0)
        {
            ResourceLocation selectedSpell = spellCap.getNextSpell();
            DrawScreenEvent.setSelectedSpell(selectedSpell);
            PacketHandler.sendToServer(new KeyPressPacket(1));

        } else {
            ResourceLocation selectedSpell = spellCap.getPreviousSpell();
            DrawScreenEvent.setSelectedSpell(selectedSpell);
            PacketHandler.sendToServer(new KeyPressPacket(2));
        }

        event.setCanceled(true);
    }
}
