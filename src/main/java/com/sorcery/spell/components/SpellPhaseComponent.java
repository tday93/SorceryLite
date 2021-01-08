package com.sorcery.spell.components;

import com.sorcery.spell.Spell;
import com.sorcery.spell.SpellUseContext;
import net.minecraft.util.ActionResultType;

public class SpellPhaseComponent
{
    // FOR TESTING
    private Spell tickSpell;

    public SpellPhaseComponent(Spell tickSpell)
    {
        this.tickSpell = tickSpell;
    }

    // perform the per-tick action of the spell
    public ActionResultType doCastPerTick(SpellUseContext context)
    {
        return this.tickSpell.doCastPerTick(context);
    }

    public void doCastPerTickClient(SpellUseContext context)
    {
        this.tickSpell.doCastPerTickClient(context);
    }
}
