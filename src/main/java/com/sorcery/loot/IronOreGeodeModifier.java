package com.sorcery.loot;

import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;

public class IronOreGeodeModifier extends LootModifier
{
    private final int dropChance;
    private final Item itemReward;

    public IronOreGeodeModifier(ILootCondition[] conditionsIn, int dropChance, Item itemReward)
    {
        super(conditionsIn);
        this.dropChance = dropChance;
        this.itemReward = itemReward;
    }

    @Nonnull
    @Override
    public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        //
        // Additional conditions can be checked, though as much as possible should be parameterized via JSON data.
        // It is better to write a new ILootCondition implementation than to do things here.
        //
        if(context.getRandom().nextInt(100) <= this.dropChance)
        {
            generatedLoot.add(new ItemStack(itemReward));
        }

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<IronOreGeodeModifier>
    {

        @Override
        public IronOreGeodeModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
            int dropChance = JSONUtils.getInt(object, "dropChance");
            Item geode = ForgeRegistries.ITEMS.getValue(new ResourceLocation((JSONUtils.getString(object, "geodeItem"))));
            return new IronOreGeodeModifier(conditionsIn, dropChance, geode);
        }

        @Override
        public JsonObject write(IronOreGeodeModifier instance)
        {
            return makeConditions(instance.conditions);
        }
    }
}
