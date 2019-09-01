package com.root.sorcery.Items;

import com.root.sorcery.Setup.ModMaterials;
import com.root.sorcery.Setup.ModSetup;
import net.minecraft.item.IItemTier;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.registry.Registry;

public class ModPickaxe extends PickaxeItem {
    public ModPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, String registryName) {
        super(tier, attackDamageIn, attackSpeedIn, new PickaxeItem.Properties().group(ModSetup.sorcery));
        this.setRegistryName(registryName);
        Registry.register(Registry.ITEM, registryName, this);
    }


    public static ModPickaxe chondrite_pickaxe;


    public static void init(){
        chondrite_pickaxe = new ModPickaxe(ModMaterials.CHONDRITE, 1, 2.0F, "chondrite_pickaxe");
    }
}