package com.sorcery.datagen;

import com.sorcery.Constants;
import com.sorcery.block.ModBlock;
import com.sorcery.item.ModItem;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
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

        // This should be any of the base crystals, but for now just carnelian to see if the bug gets fixed.
        ShapelessRecipeBuilder.shapelessRecipe(ModItem.CRYSTAL_ARCANE.get(), 8)
                .addIngredient(Items.DIAMOND)
                .addIngredient(ModItem.SORCEROUS_CATALYST.get())
                .addIngredient(ModItem.CRYSTAL_CARNELIAN.get())
                .setGroup("sorcery")
                .addCriterion("diamond", InventoryChangeTrigger.Instance.forItems(Items.REDSTONE))
                .addCriterion("sorcerous", InventoryChangeTrigger.Instance.forItems(Items.LAPIS_LAZULI))
                .addCriterion("crystal", InventoryChangeTrigger.Instance.forItems(ModItem.CRYSTAL_CARNELIAN.get()))
                .build(consumer);

        // Shaped Recipes
        ShapedRecipeBuilder.shapedRecipe(ModBlock.RUNESTONE_BRICKS.get(), 4)
                .patternLine("xx")
                .patternLine("xx")
                .key('x', ModBlock.POLISHED_RUNESTONE.get())
                .setGroup("sorcery")
                .addCriterion("sorcery:polished_runestone", InventoryChangeTrigger.Instance.forItems(ModBlock.POLISHED_RUNESTONE.get()))
                .build(consumer);

        // -- Runestones
        runestoneRecipe(consumer, ModBlock.CHISELED_RUNESTONE.get(), ModItem.SORCEROUS_CATALYST.get());
        runestoneRecipe(consumer, ModBlock.DARK_RUNESTONE.get(), Items.BEEF);
        runestoneRecipe(consumer, ModBlock.LAPIS_RUNESTONE.get(), Items.LAPIS_BLOCK);
        runestoneRecipe(consumer, ModBlock.LUNAR_RUNESTONE.get(), Items.IRON_INGOT);
        runestoneRecipe(consumer, ModBlock.SOLAR_RUNESTONE.get(), Items.GOLD_INGOT);

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
        // -- Pre-Iron
        wandRecipeTier1(consumer, ModItem.WAND_LESSER_DIG.get(), Items.DIRT);
        wandRecipeTier1(consumer, ModItem.WAND_PLANT_DEATH.get(), Items.COARSE_DIRT);
        wandRecipeTier1(consumer, ModItem.WAND_PLANT_LIFE.get(), Tags.Items.SEEDS);
        wandRecipeTier1(consumer, ModItem.WAND_CHILLING_TOUCH.get(), Items.SNOWBALL);
        // -- Iron
        wandRecipeTier2(consumer, ModItem.WAND_COBBLE_PLACEMENT.get(), Items.COBBLESTONE);
        wandRecipeTier2(consumer, ModItem.WAND_LESSER_FIREBOLT.get(), Items.COAL);
        wandRecipeTier2(consumer, ModItem.WAND_LESSER_SHUNT.get(), Items.ENDER_PEARL);
        wandRecipeTier2(consumer, ModItem.WAND_LESSER_HEAL.get(), Items.GLISTERING_MELON_SLICE);
        wandRecipeTier2(consumer, ModItem.WAND_LESSER_SLOW.get(), Items.COBWEB);
        wandRecipeTier2(consumer, ModItem.WAND_SIGNAL_FLARE.get(), Items.GUNPOWDER);

        //Scrolls
        // -- Pre-Iron
        // -- Iron
        // -- Diamond
        spellScrollRecipeTier3(consumer, ModItem.SCROLL_COMBUSTION.get(), Items.COAL_BLOCK);
        spellScrollRecipeTier3(consumer, ModItem.SCROLL_DIG.get(), Items.DIAMOND_PICKAXE);
        spellScrollRecipeTier3(consumer, ModItem.SCROLL_DRAIN_LIFE.get(), Items.ROTTEN_FLESH);
        spellScrollRecipeTier3(consumer, ModItem.SCROLL_EARTHEN_WALL.get(), Items.DIRT);
        spellScrollRecipeTier3(consumer, ModItem.SCROLL_IGNITE.get(), Items.FLINT_AND_STEEL);
        spellScrollRecipeTier3(consumer, ModItem.SCROLL_LESSER_FEATHER_FALL.get(), Items.FEATHER);
        spellScrollRecipeTier3(consumer, ModItem.SCROLL_MAGIC_MISSILE.get(), Items.ARROW);
        spellScrollRecipeTier3(consumer, ModItem.SCROLL_SEISMIC_ECHO.get(), Items.DIAMOND_BLOCK);
        spellScrollRecipeTier3(consumer, ModItem.SCROLL_STONEFLESH.get(), Items.STONE);
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

    protected void spellScrollRecipeTier3(Consumer<IFinishedRecipe> consumer, Item scrollItem, Item uniqueComponent)
    {
        ShapedRecipeBuilder.shapedRecipe(scrollItem)
                .patternLine(" x ")
                .patternLine(" y ")
                .patternLine(" z ")
                .key('x', uniqueComponent)
                .key('y', ModItem.CRYSTAL_ARCANE.get())
                .key('z', Items.PAPER)
                .setGroup("sorcery_spells")
                .addCriterion("arcane_crystal", InventoryChangeTrigger.Instance.forItems(ModItem.CRYSTAL_ARCANE.get()))
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

    protected void wandRecipeTier2(Consumer<IFinishedRecipe> consumer, Item wandItem, Item uniqueComponent)
    {
        ShapedRecipeBuilder.shapedRecipe(wandItem)
                .patternLine(" zx")
                .patternLine(" yz")
                .patternLine("y  ")
                .key('x', uniqueComponent)
                .key('y', Items.STICK)
                .key('z', ModItem.SORCEROUS_CATALYST.get())
                .setGroup("sorcery")
                .addCriterion("unique_component", InventoryChangeTrigger.Instance.forItems(uniqueComponent))
                .build(consumer);
    }

    protected void runestoneRecipe(Consumer<IFinishedRecipe> consumer, IItemProvider monoBlock, Item catalyst)
    {
        ShapedRecipeBuilder.shapedRecipe(monoBlock, 1)
                .patternLine("yyy")
                .patternLine("yxy")
                .patternLine("yyy")
                .key('x', catalyst)
                .key('y', ModBlock.POLISHED_RUNESTONE.get())
                .setGroup("sorcery")
                .addCriterion("catalyst", InventoryChangeTrigger.Instance.forItems(catalyst))
                .addCriterion("runestone", InventoryChangeTrigger.Instance.forItems(ModBlock.POLISHED_RUNESTONE.get()))
                .build(consumer);

    }

}
