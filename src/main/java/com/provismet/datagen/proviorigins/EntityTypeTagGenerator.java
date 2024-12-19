package com.provismet.datagen.proviorigins;

import com.provismet.proviorigins.content.registries.POEntities;
import com.provismet.proviorigins.utility.tags.POEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class EntityTypeTagGenerator extends FabricTagProvider.EntityTypeTagProvider {
    public EntityTypeTagGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure (RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(POEntityTypeTags.CAN_TRADE)
            .add(EntityType.VILLAGER)
            .add(EntityType.WANDERING_TRADER);

        getOrCreateTagBuilder(POEntityTypeTags.ALWAYS_DETECT)
            .addOptionalTag(ConventionalEntityTypeTags.BOSSES)
            .add(EntityType.GHAST)
            .add(EntityType.PHANTOM)
            .add(EntityType.WARDEN);

        getOrCreateTagBuilder(POEntityTypeTags.ALWAYS_VISIBLE)
            .add(EntityType.ARMOR_STAND);

        getOrCreateTagBuilder(POEntityTypeTags.BYPASSES_DETECTION_CHECK)
            .addOptionalTag(POEntityTypeTags.ALWAYS_DETECT)
            .addOptionalTag(POEntityTypeTags.ALWAYS_DETECT);

        getOrCreateTagBuilder(POEntityTypeTags.GRANTS_EXTRA_SHARDS)
            .add(EntityType.PLAYER)
            .add(POEntities.CLONE)
            .addOptionalTag(ConventionalEntityTypeTags.BOSSES);
    }
}
