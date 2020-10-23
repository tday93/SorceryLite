package com.sorcery.utils;

import java.util.Arrays;
import java.util.List;

public enum ModColor
{

    EVOCATION_RED(208, 3, 35,  153, 0, 36,  91, 2, 48),
    TRANSMUTATION_GREEN(145, 239, 59,  53, 187, 93,  23, 95, 66),
    CONJURATION_PURPLE(216, 124, 242,  135, 74, 203,  79, 52, 141),
    NECROMANCY_BLACK(73, 78, 93,  38, 41, 49,  22, 24, 31),
    ABJURATION_YELLOW(253, 225, 95,  234, 171, 46,  203, 106, 23),
    ENCHANTMENT_BLUE(16, 155, 219,  24, 89, 183,  24, 89, 183),
    ARCANA_PURPLE(195, 27, 167,  151, 24, 174,  102, 13, 157),
    SOLAR_GOLD(255, 250, 155,  255, 198, 28,  228, 131, 14),
    LUNAR_SILVER(211, 247, 243,  177, 222, 228,  136, 184, 211),
    LAPIS_BLUE(62, 177, 228,  60, 110, 216,  57, 73, 195),
    BLOOD_RED(143, 1, 11,  118, 1, 44,  95, 1, 61);


    private final int hiRed;
    private final int hiBlue;
    private final int hiGreen;
    private final int hiDecimal;

    private final int mainRed;
    private final int mainBlue;
    private final int mainGreen;
    private final int mainDecimal;

    private final int lowRed;
    private final int lowBlue;
    private final int lowGreen;
    private final int lowDecimal;

    private ModColor(int hiRed, int hiGreen, int hiBlue, int mainRed, int mainGreen, int mainBlue, int lowRed, int lowGreen, int lowBlue)
    {

        this.hiRed = hiRed;
        this.hiBlue = hiBlue;
        this.hiGreen = hiGreen;
        this.hiDecimal = (((hiRed << 8) + hiGreen) << 8) + hiBlue;
        this.mainRed = mainRed;
        this.mainBlue = mainBlue;
        this.mainGreen = mainGreen;
        this.mainDecimal = (((mainRed << 8) + mainGreen) << 8) + mainBlue;
        this.lowRed = lowRed;
        this.lowBlue = lowBlue;
        this.lowGreen = lowGreen;
        this.lowDecimal = (((lowRed << 8) + lowGreen) << 8) + lowBlue;
    }

    public List<Integer> getHiList()
    {
        return Arrays.asList(this.hiRed, this.hiGreen, this.hiBlue);
    }

    public List<Integer> getMainList()
    {
        return Arrays.asList(this.mainRed, this.mainGreen, this.mainBlue);
    }

    public List<Integer> getLowList()
    {
        return Arrays.asList(this.lowRed, this.lowGreen, this.lowBlue);
    }

    public int getHiDecimal()
    {
        return this.hiDecimal;
    }

    public int getMainDecimal()
    {
        return this.mainDecimal;
    }

    public int getLowDecimal()
    {
        return this.lowDecimal;
    }
}
