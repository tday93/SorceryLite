package com.root.sorcery.spell;

import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;

public class ChanneledSpell extends Spell
{
    int arcanaCost = 1;

    public ChanneledSpell()
    {
        this.castDuration = 100;
    }

    // all this does is cast the spell multiple times
    @Override
    public ActionResultType cast(SpellUseContext context){

        if (!drainArcana(context, arcanaCost))
            return ActionResultType.FAIL;

        context.getPlayer().sendMessage(new StringTextComponent("Still Channeling!"));

        return ActionResultType.PASS;
    }

}
