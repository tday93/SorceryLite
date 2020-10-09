package com.sorcery.spellcasting;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.naming.spi.ResolveResult;

public class SpellcastingProvider implements ICapabilitySerializable<CompoundNBT>
{

    private  ISpellcasting impl = new SpellcastingDefault();

    private final LazyOptional<ISpellcasting> cap = LazyOptional.of(() -> impl);

    public SpellcastingProvider()
    {

    }

    public SpellcastingProvider(ResourceLocation startingSpell)
    {
        impl = new SpellcastingDefault(startingSpell);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side)
    {
        if (capability == SpellcastingCapability.SPELLCASTING)
        {
            return cap.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        return impl.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        impl.deserializeNBT(nbt);
    }

}
