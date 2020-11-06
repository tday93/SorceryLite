package com.sorcery.spell;

import com.sorcery.utils.ModColor;

public enum SpellSchool
{

    ABJURATION(0, "abjuration", ModColor.ABJURATION_YELLOW),
    CONJURATION(1, "conjuration", ModColor.CONJURATION_PURPLE),
    ENCHANTMENT(2, "enchantment", ModColor.ENCHANTMENT_BLUE),
    EVOCATION(3, "evocation", ModColor.EVOCATION_RED),
    NECROMANCY(4, "necromancy", ModColor.NECROMANCY_BLACK),
    TRANSMUTATION(5, "transmutation", ModColor.TRANSMUTATION_GREEN);

    public int schoolIndex;
    public String schoolName;
    public ModColor schoolColor;

    private SpellSchool(int indexIn, String nameIn, ModColor colorIn)
    {
        this.schoolIndex = indexIn;
        this.schoolName = nameIn;
        this.schoolColor = colorIn;
    }

}
