package com.sorcery.entity;


import com.sorcery.item.ModItem;
import com.sorcery.spell.SpellUseContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SpellCarrierEntity extends Entity
{

    private SpellUseContext context = null;
    private Integer lifeDuration = null;
    private Integer lifeTicks = 0;

    public SpellCarrierEntity(EntityType<?> entityTypeIn, World worldIn)
    {
        super(entityTypeIn, worldIn);
    }

    public void tick() {
        if (this.context instanceof SpellUseContext)
        {
            this.lifeTicks += 1;
            context.setCastingTicks(this.lifeTicks);
            if (this.lifeTicks > this.lifeDuration)
            {
                this.remove();
            }
            if (!this.world.isRemote())
            {
                context.getSpell().doCastPerTick(context);
            }
        } else {
            this.remove();
        }
        this.baseTick();
    }

    public void setLifeDuration(int durationIn)
    {
        this.lifeDuration = durationIn;
    }

    public void setContext(SpellUseContext context)
    {
        context.setCarrierEntity(this);
        this.context = context;
    }

    public void setPosition(Vector3d posVec)
    {
        this.setPosition(posVec.getX(), posVec.getY(), posVec.getZ());
    }

    @Override
    protected void registerData()
    {
    }

    @Override
    protected void readAdditional(CompoundNBT compound)
    {
    }

    @Override
    protected void writeAdditional(CompoundNBT compound)
    {
    }

    @Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
