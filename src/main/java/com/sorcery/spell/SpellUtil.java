package com.sorcery.spell;

import com.sorcery.item.ModItem;
import com.sorcery.item.SpellScrollItem;
import com.sorcery.item.WandItem;
import com.sorcery.utils.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;

public class SpellUtil
{
    public static WandItem getWand(WandItem wandItem, Item catalystItem)
    {
        if (catalystItem instanceof SpellScrollItem)
        {
            Spell spell = GameRegistry.findRegistry(Spell.class).getValue(((SpellScrollItem) catalystItem).spellLoc);
            if (spell.spellTier == wandItem.getTier())
            {
                return Utils.getWand(spell);
            } else {
                return null;
            }
        }
        WandItem craftedWand = wandMap().get(catalystItem);
        if (craftedWand == null)
        {
            return null;
        }
        if (craftedWand.SPELL.get().spellTier == wandItem.getTier())
        {
            return craftedWand;
        } else {
            return null;
        }
    }

    public static HashMap<Item, WandItem> wandMap()
    {
        HashMap<Item, WandItem> catMap = new HashMap<>();
        catMap.put(Items.DIRT, (WandItem) ModItem.WAND_LESSER_DIG.get());

        return catMap;
    }
}
