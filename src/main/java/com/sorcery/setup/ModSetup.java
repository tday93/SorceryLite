package com.sorcery.setup;

import com.sorcery.item.ModItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModSetup
{

    public static ItemGroup SORCERY = new ItemGroup("sorcery"){
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModItem.SPELL_BOOK.get());
        }
    };

    public static ItemGroup SORCERY_SPELLS = new ItemGroup("sorcery_spells")
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModItem.SCROLL_CREATE_WATER.get());
        }
    };

    public static ItemGroup SORCERY_WANDS = new ItemGroup("sorcery_wands")
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModItem.WAND_LESSER_DIG.get());
        }
    };
}
