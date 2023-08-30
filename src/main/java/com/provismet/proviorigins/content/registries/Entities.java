package com.provismet.proviorigins.content.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.content.entities.CloneEntity;
import com.provismet.proviorigins.content.entities.MinionEntity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class Entities {
    public static final EntityType<CloneEntity> CLONE = Registry.register(
        Registries.ENTITY_TYPE,
        ProviOriginsMain.identifier("player_clone"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, CloneEntity::new)
            .dimensions(PlayerEntity.STANDING_DIMENSIONS)
            .disableSummon()
            .build()
    );

    public static final EntityType<MinionEntity> MINION = Registry.register(
        Registries.ENTITY_TYPE,
        ProviOriginsMain.identifier("minion"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, MinionEntity::new)
            .dimensions(new EntityDimensions(0.8f, 0.8f, false))
            .disableSummon()
            .build()
    );

    public static void register () {
        FabricDefaultAttributeRegistry.register(CLONE, CloneEntity.createCloneAttributes());
        FabricDefaultAttributeRegistry.register(MINION, MinionEntity.createMinionAttributes());
    }
}
