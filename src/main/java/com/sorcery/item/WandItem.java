package com.sorcery.item;

import com.sorcery.Constants;
import com.sorcery.spell.Spell;
import net.minecraftforge.fml.RegistryObject;

public class WandItem extends SpellcastingItem
{
    public final RegistryObject<Spell> SPELL;

    private int arcanaAmount = 100;

    public WandItem(RegistryObject<Spell> spellin)
    {
        super(Constants.ITEM_PROPS_NONSTACK);
        this.SPELL = spellin;
    }

    public WandItem(RegistryObject<Spell> spellin, int arcanaAmount)
    {
        super(Constants.ITEM_PROPS_NONSTACK);
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
}
