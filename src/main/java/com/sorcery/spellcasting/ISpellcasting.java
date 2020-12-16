package com.sorcery.spellcasting;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;

public interface ISpellcasting extends INBTSerializable<CompoundNBT>
{
    // Active Spell
    ResourceLocation getActiveSpell();

    void setActiveSpell(ResourceLocation spell);


    // Prepared Spells

    ArrayList<ResourceLocation> getPreparedSpells();

    void setPreparedSpells(ArrayList<ResourceLocation> allSpells);

    void addPreparedSpell(ResourceLocation spell);

    void removePreparedSpell(ResourceLocation spell);

    boolean hasPreparedSpell(ResourceLocation spell);

    void clearPreparedSpells();


    // Utility

    void cycleActiveSpell(int delta);

    int getIndexFromSpell(ResourceLocation rl);

    ResourceLocation getSpellFromIndex(int index);

    ResourceLocation getNextSpell();

    ResourceLocation getPreviousSpell();

}
