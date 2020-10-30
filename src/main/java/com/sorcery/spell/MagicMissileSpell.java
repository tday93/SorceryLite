package com.sorcery.spell;

import com.sorcery.Sorcery;
import com.sorcery.entity.ModEntity;
import com.sorcery.entity.projectile.MagicMissileEntity;
import com.sorcery.utils.BasisVector;
import com.sorcery.utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public class MagicMissileSpell extends Spell
{
    double startingSpeed = 1;

    public MagicMissileSpell(int arcanaCost)
    {
        super(arcanaCost);
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {

        MagicMissileEntity magicMissile = new MagicMissileEntity(ModEntity.MAGIC_MISSILE, context.getWorld());

        Vector3d startingPos = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(1.0f), context.getPlayer().getLookVec(), 1);
        magicMissile.setPosition(startingPos.x, startingPos.y, startingPos.z);

        BasisVector basis = new BasisVector(context.getPlayer().getLookVec());
        // horizontal deflection
        double xDeflection = (context.getWorld().getRandom().nextDouble() - 0.5);
        // vertical deflection, always global up
        double yDeflection = 0.1 + (context.getWorld().getRandom().nextDouble() * 0.5);
        Vector3d initialVec = basis.addXYZ(context.getPlayer().getLookVec(), xDeflection, 0, 0).add(0, yDeflection, 0).normalize().scale(this.startingSpeed);

        // deflected motion
        magicMissile.addVelocity(initialVec.getX(), initialVec.getY(), initialVec.getZ());

        Vector3d endVec = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(1.0f), context.getPlayer().getLookVec(), 16);
        AxisAlignedBB aaBB = new AxisAlignedBB(startingPos, endVec).grow(2);
        EntityRayTraceResult result = ProjectileHelper.rayTraceEntities(context.getWorld(), magicMissile, startingPos, endVec, aaBB, Utils.getShootableEntites(context.getPlayer()));
        if (result != null)
        {
            magicMissile.setTargetEntity(result.getEntity());
        } else
        {
            magicMissile.setTargetVector(endVec);
        }

        // Spawn entity
        context.getWorld().addEntity(magicMissile);
        return ActionResultType.SUCCESS;
    }
}