package com.sorcery;

import com.sorcery.setup.ModSetup;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class Constants
{
    public static final String MODID = "sorcery";
    public static final String MODNAME = "Sorcery";
    public static final String VERSION = "0.1.0";
    public static final Item.Properties ITEM_PROPS = new Item.Properties().group(ModSetup.SORCERY);
    public static final Item.Properties ITEM_PROPS_NONSTACK = new Item.Properties().group(ModSetup.SORCERY).maxStackSize(1);
    public static final Item.Properties ITEM_PROPS_SCROLLS = new Item.Properties().group(ModSetup.SORCERY_SPELLS).maxStackSize(1);
    public static final Item.Properties ITEM_PROPS_WANDS = new Item.Properties().group(ModSetup.SORCERY_WANDS).maxStackSize(1);

    public static final ResourceLocation CATALYST_TAG = new ResourceLocation(MODID, "catalysts");
    public static final ResourceLocation FITTING_TAG = new ResourceLocation(MODID, "fittings");
    public static final ResourceLocation ROD_TAG = new ResourceLocation(MODID, "rods");

    public static final ResourceLocation CRYSTALS_TAG = new ResourceLocation(MODID, "crystals");

    public static final ResourceLocation MONOLITH_FORMABLE_TAG = new ResourceLocation(MODID, "monolith_formable");
    public static final ResourceLocation PLANT_DEATHABLE_TAG = new ResourceLocation(MODID, "plant_deathable");
    public static final ResourceLocation EARTHEN_WALL_BLOCKS = new ResourceLocation(MODID, "earthen_wall_blocks");
    public static final ResourceLocation SEISMIC_ECHO_BLOCKS = new ResourceLocation(MODID, "seismic_echo_blocks");

}
