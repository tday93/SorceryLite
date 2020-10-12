package com.sorcery.spell;

import com.sorcery.utils.Utils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.ActionResultType;

import java.util.List;

public class TransmutationSpell extends Spell
{
    public TransmutationSpell(int arcanaCost)
    {
        super(arcanaCost);
        this.castDuration = 20;
    }

    // perform the final cast of the spell
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        List<ItemEntity> itemEntities = Utils.itemEntitiesInRange(context.getWorld(), context.getFacePos(), 2);
        for (ItemEntity itemEntity : itemEntities)
        {
            // check if in mapping
            // check amount
            // shrink existing itemstacks
            // spawn new item entity

        }
        return ActionResultType.SUCCESS;
    }
}
