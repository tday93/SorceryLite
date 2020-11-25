package com.sorcery.spell;

public enum SpellTier
{
    INITIATE(0, "initiate"),
    APPRENTICE(1, "apprentice"),
    JOURNEYMAN(2, "journeyman"),
    SORCERER(3, "sorcerer"),
    MASTER_SORCERER(4, "master_sorcerer"),
    ARCHSORCERER(5, "archsorcerer");

    public int tierInt;
    public String tierName;

    private SpellTier(int tierInt, String tierName)
    {
        this.tierInt = tierInt;
        this.tierName = tierName;
    }
}
