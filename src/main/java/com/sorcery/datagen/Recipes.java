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

        // Wand Cores
        ShapedRecipeBuilder.shapedRecipe(ModItem.WAND_CORE_INITIATE.get(), 1)
                .patternLine("  x")
                .patternLine(" x ")
                .patternLine("x  ")
                .key('x', Items.STICK)
                .setGroup("sorcery")
                .addCriterion("minecraft:stick", InventoryChangeTrigger.Instance.forItems(Items.STICK))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItem.WAND_CORE_APPRENTICE.get(), 1)
                .patternLine("  y")
                .patternLine(" x ")
                .patternLine("x  ")
                .key('x', Items.STICK)
                .key('y', Items.IRON_INGOT)
                .setGroup("sorcery")
                .addCriterion("minecraft:iron_ingot", InventoryChangeTrigger.Instance.forItems(Items.IRON_INGOT))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItem.WAND_CORE_JOURNEYMAN.get(), 1)
                .patternLine("  y")
                .patternLine(" x ")
                .patternLine("z  ")
                .key('x', Items.STICK)
                .key('y', Items.IRON_INGOT)
                .key('z', ModItem.CRYSTAL_ARCANE.get())
                .setGroup("sorcery")
                .addCriterion("sorcery:crystal_arcane", InventoryChangeTrigger.Instance.forItems(ModItem.CRYSTAL_ARCANE.get()))
                .build(consumer);

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
