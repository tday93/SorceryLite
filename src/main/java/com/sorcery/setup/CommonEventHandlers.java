package com.sorcery.setup;

import com.sorcery.arcana.ArcanaCapability;
import com.sorcery.arcana.ArcanaProvider;
import com.sorcery.arcana.IArcanaStorage;
import com.sorcery.item.PortableArcanaItem;
import com.sorcery.item.SpellbookItem;
import com.sorcery.item.SpellcastingItem;
import com.sorcery.item.WandItem;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ArcanaCapSyncPacket;
import com.sorcery.network.packets.SpellCapSyncPacket;
import com.sorcery.spellcasting.ISpellcasting;
import com.sorcery.spellcasting.SpellcastingCapability;
import com.sorcery.spellcasting.SpellcastingProvider;
import com.sorcery.tileentity.AbstractMonolithTile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// Event Handlers
@Mod.EventBusSubscriber
public class CommonEventHandlers
{

    @SubscribeEvent
    public static void attachCapabilitiesEntities(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof PlayerEntity)
        {
            event.addCapability(SpellcastingCapability.SPELLCASTING_LOC, new SpellcastingProvider());
            event.addCapability(ArcanaCapability.ARCANA_LOC, new ArcanaProvider());
        }
    }

    @SubscribeEvent
    public static void attachCapabilitiesItems(AttachCapabilitiesEvent<ItemStack> event)
    {
        if (event.getObject().getItem() instanceof SpellcastingItem)
        {
            if (event.getObject().getItem() instanceof WandItem)
            {
                event.addCapability(ArcanaCapability.ARCANA_LOC, new ArcanaProvider(((WandItem) event.getObject().getItem()).getArcanaAmount()));
                event.addCapability(SpellcastingCapability.SPELLCASTING_LOC, new SpellcastingProvider(((WandItem) event.getObject().getItem()).getSpell().getRegistryName()));
            } else {
                event.addCapability(ArcanaCapability.ARCANA_LOC, new ArcanaProvider());
                event.addCapability(SpellcastingCapability.SPELLCASTING_LOC, new SpellcastingProvider());
            }
        }
        if (event.getObject().getItem() instanceof PortableArcanaItem)
        {
            event.addCapability(ArcanaCapability.ARCANA_LOC, new ArcanaProvider());
        }
        if (event.getObject().getItem() instanceof SpellbookItem)
        {
            event.addCapability(SpellcastingCapability.SPELLCASTING_LOC, new SpellcastingProvider());
        }
    }

    @SubscribeEvent
    public static void attachCapabilitiesTileEntities(AttachCapabilitiesEvent<TileEntity> event)
    {
        if (event.getObject().getTileEntity() instanceof AbstractMonolithTile)
        {
            event.addCapability(ArcanaCapability.ARCANA_LOC, new ArcanaProvider());
        }
    }

    @SubscribeEvent
    public static void playerCloneEvent(PlayerEvent.Clone event)
    {
        if (event.isWasDeath())
        {
            ISpellcasting origCap = event.getOriginal().getCapability(SpellcastingCapability.SPELLCASTING).orElseThrow(NullPointerException::new);
            ISpellcasting newCap = event.getPlayer().getCapability(SpellcastingCapability.SPELLCASTING).orElseThrow(NullPointerException::new);
            newCap.setActiveSpell(origCap.getActiveSpell());
            newCap.setPreparedSpells(origCap.getPreparedSpells());
        }
    }

    @SubscribeEvent
    public static void playerLoginEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        IArcanaStorage playerArcanaCap = event.getPlayer().getCapability(ArcanaCapability.ARCANA, null).orElseThrow(NullPointerException::new);
        ISpellcasting playerSpellCap = event.getPlayer().getCapability(SpellcastingCapability.SPELLCASTING, null).orElseThrow(NullPointerException::new);

        ServerPlayerEntity serverPlayer = event.getPlayer().getServer().getPlayerList().getPlayerByUUID(event.getPlayer().getUniqueID());

        PacketHandler.sendToPlayer(serverPlayer, new SpellCapSyncPacket((CompoundNBT) SpellcastingCapability.SPELLCASTING.writeNBT(playerSpellCap, null)));
        PacketHandler.sendToPlayer(serverPlayer, new ArcanaCapSyncPacket(ArcanaCapability.ARCANA.writeNBT(playerArcanaCap, null)));



    }

}
