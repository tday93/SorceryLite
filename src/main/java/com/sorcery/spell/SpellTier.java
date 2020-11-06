package com.sorcery.spell;

public enum SpellTier
{
    INITIATE(0, "initiate"),
    APPRENTICE(1, "apprentice"),
    JOURNEYMAN(2, "journeyman"),
    MAGE(3, "mage"),
    MASTER(4, "master"),
    ARCHMAGE(5, "archmage");

    public int tierInt;
    public String tierName;

    private SpellTier(int tierInt, String tierName)
    {
        this.tierInt = tierInt;
        this.tierName = tierName;
    }
}
