package com.sorcery.item;

import com.sorcery.Constants;
import com.sorcery.spell.Spell;
import com.sorcery.spell.SpellTier;
import net.minecraftforge.fml.RegistryObject;

public class WandItem extends SpellcastingItem
{
    public final RegistryObject<Spell> SPELL;

    private int arcanaAmount = 1000;

    public WandItem(RegistryObject<Spell> spellin)
    {
        super(Constants.ITEM_PROPS_WANDS);
        this.SPELL = spellin;
    }

    public WandItem(RegistryObject<Spell> spellin, int arcanaAmount)
    {
        super(Constants.ITEM_PROPS_WANDS);
        this.arcanaAmount = arcanaAmount;
        this.SPELL = spellin;
    }

    public Spell getSpell()
    {
        return this.SPELL.get();
    }

    public int getArcanaAmount()
    {
        return this.arcanaAmount;
    }

    public SpellTier getTier()
    {
        final SpellTier spellTier = this.SPELL.get().spellTier;
        return spellTier;
    }
}
