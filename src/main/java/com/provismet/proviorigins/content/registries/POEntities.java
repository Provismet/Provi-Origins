package com.provismet.proviorigins.content.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.content.entities.CloneEntity;
import com.provismet.proviorigins.content.entities.MinionEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class POEntities {
    public static final EntityType<CloneEntity> CLONE = register(
        ProviOriginsMain.identifier("player_clone"),
        EntityType.Builder.create(CloneEntity::new, SpawnGroup.MISC)
            .setDimensions(PlayerEntity.STANDING_DIMENSIONS.width, PlayerEntity.STANDING_DIMENSIONS.height)
            .disableSummon()
    );

    public static final EntityType<MinionEntity> MINION = register(
        ProviOriginsMain.identifier("minion"),
        EntityType.Builder.create(MinionEntity::new, SpawnGroup.MISC)
            .setDimensions(0.5f, 0.5f)
            .disableSummon()
    );

    public static <T extends Entity> EntityType<T> register (Identifier id, EntityType.Builder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, id, builder.build(id.toString()));
    }

    public static void register () {
        FabricDefaultAttributeRegistry.register(CLONE, CloneEntity.createCloneAttributes());
        FabricDefaultAttributeRegistry.register(MINION, MinionEntity.createMinionAttributes());
    }
}
