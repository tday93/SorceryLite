package com.sorcery.entity;

import com.sorcery.Constants;
import com.sorcery.entity.projectile.FireboltEntity;
import com.sorcery.entity.projectile.MagicMissileEntity;
import com.sorcery.entity.projectile.SpellProjectileEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("sorcery")
public class ModEntity
{


    @ObjectHolder("spell_projectile")
    public static EntityType<SpellProjectileEntity> SPELL_PROJECTILE;

    @ObjectHolder("firebolt")
    public static EntityType<FireboltEntity> FIREBOLT;

    @ObjectHolder("magic_missile")
    public static EntityType<MagicMissileEntity> MAGIC_MISSILE;

    public static void init()
    {
        // Other Entities
        SPELL_PROJECTILE = (EntityType<SpellProjectileEntity>) EntityType.Builder.create(SpellProjectileEntity::new, EntityClassification.MISC)
                .size(1,1)
                .setShouldReceiveVelocityUpdates(true)
                .setCustomClientFactory(((spawnEntity, world) -> new SpellProjectileEntity(SPELL_PROJECTILE, world)))
                .build("spell_projectile")
                .setRegistryName(Constants.MODID, "spell_projectile");

        FIREBOLT = (EntityType<FireboltEntity>) EntityType.Builder.create(FireboltEntity::new, EntityClassification.MISC)
                .size(1,1)
                .setShouldReceiveVelocityUpdates(true)
                .setCustomClientFactory(((spawnEntity, world) -> new FireboltEntity(FIREBOLT, world)))
                .build("firebolt")
                .setRegistryName(Constants.MODID, "firebolt");

    MAGIC_MISSILE = (EntityType<MagicMissileEntity>) EntityType.Builder.create(MagicMissileEntity::new, EntityClassification.MISC)
        .size(1,1)
                .setShouldReceiveVelocityUpdates(true)
                .setCustomClientFactory(((spawnEntity, world) -> new MagicMissileEntity(MAGIC_MISSILE, world)))
        .build("magic_missile")
                .setRegistryName(Constants.MODID, "magic_missile");
    }

    public static void register(RegistryEvent.Register<EntityType<?>> event)
    {
        event.getRegistry().register(SPELL_PROJECTILE);
        event.getRegistry().register(FIREBOLT);
        event.getRegistry().register(MAGIC_MISSILE);

    }
}
