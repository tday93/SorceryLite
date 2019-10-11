package com.root.sorcery.network.packets;

import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.spellcasting.ISpellcasting;
import com.root.sorcery.spellcasting.SpellcastingCapability;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class KeyPressPacket
{
    private int key;

    public KeyPressPacket(){}

    public KeyPressPacket(int keyIn)
    {
        this.key = keyIn;
    }

    public static void encode(KeyPressPacket pkt, PacketBuffer buf)
    {
        buf.writeVarInt(pkt.key);
    }

    public static KeyPressPacket decode(PacketBuffer buf)
    {
        return new KeyPressPacket(buf.readVarInt());
    }

    public static class Handler
    {
        public static void handle(final KeyPressPacket message, Supplier<NetworkEvent.Context> ctx)
        {
            ctx.get().enqueueWork(() -> {
                switch (message.key)
                {
                    // Cycle Spell Key
                    case 1:
                        ServerPlayerEntity player = ctx.get().getSender();
                        ISpellcasting playerCap = player.getCapability(SpellcastingCapability.SPELLCASTING).orElseThrow(NullPointerException::new);

                        playerCap.cycleActiveSpell();

                        player.sendMessage(new StringTextComponent(String.format("Active Spell is now: %s", playerCap.getActiveSpell().toString())));
                        PacketHandler.sendToPlayer(player, new SpellCapSyncPacket((CompoundNBT) SpellcastingCapability.SPELLCASTING_STORAGE.writeNBT(SpellcastingCapability.SPELLCASTING, playerCap, null)));

                }

            });
            ctx.get().setPacketHandled(true);
        }
    }
}