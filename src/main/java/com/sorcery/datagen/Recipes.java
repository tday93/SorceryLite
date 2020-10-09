package com.sorcery.datagen;

import com.sorcery.block.ModBlock;
import com.sorcery.item.ModItem;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
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

        // Wands
        wandRecipeTier1(consumer, ModItem.WAND_LESSER_DIG.get(), Items.DIRT);
        wandRecipeTier1(consumer, ModItem.WAND_PLANT_DEATH.get(), Items.COARSE_DIRT);
        wandRecipeTier1(consumer, ModItem.WAND_PLANT_LIFE.get(), Tags.Items.SEEDS);
        wandRecipeTier1(consumer, ModItem.WAND_CHILLING_TOUCH.get(), Items.SNOWBALL);

        //Scrolls
    }

    protected void spellScrollRecipeTier1(Consumer<IFinishedRecipe> consumer, Item scrollItem, Item uniqueComponent)
    {
        ShapedRecipeBuilder.shapedRecipe(scrollItem)
                .patternLine(" x ")
                .patternLine(" y ")
                .patternLine(" z ")
                .key('x', uniqueComponent)
                .key('y', ModItem.SORCEROUS_CATALYST.get())
                .key('z', Items.PAPER)
                .setGroup("sorcery_spells")
                .addCriterion("sorcery:sorcerous_catalyst", InventoryChangeTrigger.Instance.forItems(ModItem.SORCEROUS_CATALYST.get()))
                .addCriterion("unique_component", InventoryChangeTrigger.Instance.forItems(uniqueComponent))
                .build(consumer);
    }


    protected void wandRecipeTier1(Consumer<IFinishedRecipe> consumer, Item wandItem, Item uniqueComponent)
    {
        ShapedRecipeBuilder.shapedRecipe(wandItem)
                .patternLine("  x")
                .patternLine(" y ")
                .patternLine("y  ")
                .key('x', uniqueComponent)
                .key('y', Items.STICK)
                .setGroup("sorcery")
                .addCriterion("unique_component", InventoryChangeTrigger.Instance.forItems(uniqueComponent))
                .build(consumer);
    }

    protected void wandRecipeTier1(Consumer<IFinishedRecipe> consumer, Item wandItem, ITag<Item> tag)
    {
        ShapedRecipeBuilder.shapedRecipe(wandItem)
                .patternLine("  x")
                .patternLine(" y ")
                .patternLine("y  ")
                .key('x', tag)
                .key('y', Items.STICK)
                .setGroup("sorcery")
                .addCriterion("unique_component", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(tag).build()))
                .build(consumer);
    }

}
