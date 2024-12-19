package com.provismet.proviorigins.content.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.content.entities.CloneEntity;
import com.provismet.proviorigins.content.entities.MinionEntity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class POEntities {
    public static final EntityType<CloneEntity> CLONE = Registry.register(
        Registries.ENTITY_TYPE,
        ProviOriginsMain.identifier("player_clone"),
        EntityType.Builder.create(CloneEntity::new, SpawnGroup.MISC)
            .dimensions(PlayerEntity.STANDING_DIMENSIONS.width(), PlayerEntity.STANDING_DIMENSIONS.height())
            .disableSummon()
            .build()
    );

    public static final EntityType<MinionEntity> MINION = Registry.register(
        Registries.ENTITY_TYPE,
        ProviOriginsMain.identifier("minion"),
        EntityType.Builder.create(MinionEntity::new, SpawnGroup.MISC)
            .dimensions(0.5f, 0.5f)
            .eyeHeight(0.25f)
            .disableSummon()
            .build()
    );

    public static void register () {
        FabricDefaultAttributeRegistry.register(CLONE, CloneEntity.createCloneAttributes());
        FabricDefaultAttributeRegistry.register(MINION, MinionEntity.createMinionAttributes());
    }
}
