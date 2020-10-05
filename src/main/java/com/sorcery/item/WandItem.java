package com.sorcery.item;

import com.sorcery.Constants;
import com.sorcery.spell.Spell;
import net.minecraftforge.fml.RegistryObject;

public class WandItem extends SpellcastingItem
{
    public final RegistryObject<Spell> SPELL;

    public WandItem(RegistryObject<Spell> spellin)
    {
        super(Constants.ITEM_PROPS_NONSTACK);
        this.SPELL = spellin;
    }

    public Spell getSpell()
    {
        return this.SPELL.get();
    }
}
