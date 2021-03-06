package com.sorcery.inventory;

import com.sorcery.Sorcery;
import com.sorcery.item.SpellScrollItem;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.SpellCapSyncPacket;
import com.sorcery.spell.ModSpell;
import com.sorcery.spellcasting.ISpellcasting;
import com.sorcery.spellcasting.SpellcastingCapability;
import com.sorcery.utils.Utils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class SpellbookInventory extends Inventory
{

    private final ItemStack stack;

    public SpellbookInventory(ItemStack stack, int count) {
        super(count);
        this.stack = stack;
        readItemStack();
    }

    public ItemStack getStack() {
        return stack;
    }

    public void readItemStack() {
        if (stack.getTag() != null) {
            readNBT(stack.getTag());
        }
    }

    public void writeItemStack(PlayerInventory playerInventory) {
        if (isEmpty()) {
            stack.removeChildTag("Items");
        } else {
            writeNBT(stack.getOrCreateTag(), stack, playerInventory);
        }
    }

    private void readNBT(CompoundNBT compound) {
        final NonNullList<ItemStack> list = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, list);
        for (int index = 0; index < list.size(); index++) {
            setInventorySlotContents(index, list.get(index));
        }
    }

    private void writeNBT(CompoundNBT compound, ItemStack stack, PlayerInventory playerInventory) {
        final NonNullList<ItemStack> list = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        ISpellcasting spellCasting = Utils.getSpellCap(stack);
        Sorcery.getLogger().debug(spellCasting);
        spellCasting.clearPreparedSpells();
        for (int index = 0; index < list.size(); index++) {
            ItemStack stack1 = getStackInSlot(index);
            list.set(index, getStackInSlot(index));
            if (stack1.getItem() instanceof SpellScrollItem)
            {
                ResourceLocation spellLoc = ((SpellScrollItem) stack1.getItem()).getSpell();
                spellCasting.addPreparedSpell(spellLoc);
            }
        }
        spellCasting.addPreparedSpell(ModSpell.SPELL_MEDITATE.getId());
        spellCasting.setActiveSpell(ModSpell.SPELL_MEDITATE.getId());
        ItemStackHelper.saveAllItems(compound, list, true);
        PacketHandler.sendToPlayer((ServerPlayerEntity) playerInventory.player, new SpellCapSyncPacket((CompoundNBT) SpellcastingCapability.SPELLCASTING.writeNBT(spellCasting, null)));
    }
}
