package com.provismet.personalOrigins.originClasses.jellysculk;

import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class JellySculk {
    public static final TagKey<EntityType<?>> BYPASS_DETECTION_CHECK = TagKey.of(Registry.ENTITY_TYPE_KEY, identifier("bypass_detection_check"));

    public static Identifier identifier (String path) {
        return new Identifier("jellysculk", path);
    }
}
