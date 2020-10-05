package com.sorcery.datagen;

import com.sorcery.block.ModBlock;
import com.sorcery.item.ModItem;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider
{

    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        // Shapeless Recipes
        ShapelessRecipeBuilder.shapelessRecipe(ModItem.SORCEROUS_CATALYST.get(), 2)
                .addIngredient(Items.REDSTONE)
                .addIngredient(Items.LAPIS_LAZULI)
                .setGroup("sorcery")
                .addCriterion("redstone", InventoryChangeTrigger.Instance.forItems(Items.REDSTONE))
                .addCriterion("lapis", InventoryChangeTrigger.Instance.forItems(Items.LAPIS_LAZULI))
                .build(consumer);


        // Shaped Recipes
        ShapedRecipeBuilder.shapedRecipe(ModBlock.RUNESTONE_BRICKS.get(), 4)
                .patternLine("xx")
                .patternLine("xx")
                .key('x', ModBlock.POLISHED_RUNESTONE.get())
                .setGroup("sorcery")
                .addCriterion("sorcery:polished_runestone", InventoryChangeTrigger.Instance.forItems(ModBlock.POLISHED_RUNESTONE.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlock.POLISHED_RUNESTONE.get(), 8)
                .patternLine("xxx")
                .patternLine("xyx")
                .patternLine("xxx")
                .key('y', ModItem.SORCEROUS_CATALYST.get())
                .key('x', Tags.Items.STONE)
                .setGroup("sorcery")
                .addCriterion("sorcery:sorcerous_catalyst", InventoryChangeTrigger.Instance.forItems(ModItem.SORCEROUS_CATALYST.get()))
                .build(consumer);
    }


}
