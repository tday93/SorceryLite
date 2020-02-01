package com.root.sorcery.setup;

import com.root.sorcery.item.ModItem;
import com.root.sorcery.world.OreGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModSetup {

    public static ItemGroup sorcery = new ItemGroup("Sorcery"){
        @Override
        public ItemStack createIcon(){
            return new ItemStack(ModItem.wolfram_chunk);

        }

    };


    public void init(){

        OreGenerator.overworldOreGen();

    }


}
