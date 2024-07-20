package com.provismet.proviorigins.utility.tags;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.utility.OriginList;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public abstract class POEntityTypeTags {
    public static final TagKey<EntityType<?>> CAN_TRADE = POEntityTypeTags.of(OriginList.COMMON.getDataPath("can_trade"));

    public static final TagKey<EntityType<?>> GRANTS_EXTRA_SHARDS = POEntityTypeTags.of(OriginList.CRYSTALLISER.getDataPath("extra_shards"));

    public static final TagKey<EntityType<?>> ALWAYS_DETECT = POEntityTypeTags.of(OriginList.JELLY_SCULK.getDataPath("always_detect"));
    public static final TagKey<EntityType<?>> ALWAYS_VISIBLE = POEntityTypeTags.of(OriginList.JELLY_SCULK.getDataPath("always_visible"));
    public static final TagKey<EntityType<?>> BYPASSES_DETECTION_CHECK = POEntityTypeTags.of(OriginList.JELLY_SCULK.getDataPath("bypass_detection_check"));

    private static TagKey<EntityType<?>> of (String name) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, ProviOriginsMain.identifier(name));
    }
}
