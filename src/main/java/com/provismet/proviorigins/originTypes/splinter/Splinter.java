package com.provismet.proviorigins.originTypes.splinter;

import com.provismet.proviorigins.ProviOriginsMain;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class Splinter {
    public static final EntityType<CloneEntity> CLONE = Registry.register(
        Registries.ENTITY_TYPE,
        ProviOriginsMain.identifier("player_clone"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, CloneEntity::new)
            .dimensions(PlayerEntity.STANDING_DIMENSIONS)
            .disableSummon()
            .build()
    );

    public static void register () {
        FabricDefaultAttributeRegistry.register(CLONE, CloneEntity.createCloneAttributes());
    }
}
