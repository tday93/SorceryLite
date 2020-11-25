package com.sorcery.item;

import com.sorcery.spell.Spell;
import com.sorcery.utils.ModColor;
import com.sorcery.utils.Utils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;

public class SpellScrollItem extends Item
{
    public ResourceLocation spellLoc;

    public SpellScrollItem(Properties properties, RegistryObject<Spell> spellLocIn)
    {
        super(properties);
        this.spellLoc = spellLocIn.getId();
    }

    public ResourceLocation getSpell()
    {
        return this.spellLoc;
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        try {
            Spell spell = Utils.getSpell(this.spellLoc);
            TranslationTextComponent localizedSpellTier = new TranslationTextComponent("spelltier."+ spell.getRegistryName().getNamespace() + "." + spell.spellTier.tierName);
            Style style = localizedSpellTier.getStyle();
            style = style.setItalic(true);
            style = style.setColor(Color.fromInt(ModColor.ARCANA_PURPLE.getMainDecimal()));
            localizedSpellTier.setStyle(style);
            tooltip.add(localizedSpellTier);

            TranslationTextComponent localizedSpellSchool = new TranslationTextComponent("spellschool."+ spell.getRegistryName().getNamespace() + "." + spell.spellSchool.schoolName);
            Style schoolStyle = localizedSpellSchool.getStyle();
            schoolStyle = schoolStyle.setItalic(true);
            schoolStyle = schoolStyle.setColor(Color.fromInt(spell.spellSchool.schoolColor.getMainDecimal()));
            localizedSpellSchool.setStyle(schoolStyle);

            tooltip.add(localizedSpellSchool);
        } catch (NullPointerException exception)
        {

        }
    }


}
