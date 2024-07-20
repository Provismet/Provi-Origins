package com.provismet.proviorigins.utility.tags;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.utility.OriginList;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public abstract class POItemTags {
    public static final TagKey<Item> HYDRATE_ON_USE = POItemTags.of(OriginList.COMMON.getDataPath("hydration"));

    public static final TagKey<Item> GRANTS_ARMOUR = POItemTags.of(OriginList.LILY_OF_THE_VOID.getDataPath("grants_armour"));
    public static final TagKey<Item> GRANTS_BLINDNESS = POItemTags.of(OriginList.LILY_OF_THE_VOID.getDataPath("grants_blindness"));
    public static final TagKey<Item> GRANTS_CORRUPTION = POItemTags.of(OriginList.LILY_OF_THE_VOID.getDataPath("grants_corruption"));
    public static final TagKey<Item> GRANTS_FIRE_RESISTANCE = POItemTags.of(OriginList.LILY_OF_THE_VOID.getDataPath("grants_fire_resistance"));
    public static final TagKey<Item> GRANTS_GLOW = POItemTags.of(OriginList.LILY_OF_THE_VOID.getDataPath("grants_glow"));
    public static final TagKey<Item> GRANTS_JUMP_BOOST = POItemTags.of(OriginList.LILY_OF_THE_VOID.getDataPath("grants_jump_boost"));
    public static final TagKey<Item> GRANTS_NIGHT_VISION = POItemTags.of(OriginList.LILY_OF_THE_VOID.getDataPath("grants_night_vision"));
    public static final TagKey<Item> GRANTS_REGENERATION = POItemTags.of(OriginList.LILY_OF_THE_VOID.getDataPath("grants_regeneration"));
    public static final TagKey<Item> GRANTS_SATURATION = POItemTags.of(OriginList.LILY_OF_THE_VOID.getDataPath("grants_saturation"));
    public static final TagKey<Item> GRANTS_SPEED = POItemTags.of(OriginList.LILY_OF_THE_VOID.getDataPath("grants_speed"));
    public static final TagKey<Item> GRANTS_TELEPORT = POItemTags.of(OriginList.LILY_OF_THE_VOID.getDataPath("grants_teleport"));
    public static final TagKey<Item> GRANTS_THORNS = POItemTags.of(OriginList.LILY_OF_THE_VOID.getDataPath("grants_thorns"));
    public static final TagKey<Item> GRANTS_WATER_BREATHING = POItemTags.of(OriginList.LILY_OF_THE_VOID.getDataPath("grants_water_breathing"));
    public static final TagKey<Item> GRANTS_WEAKNESS = POItemTags.of(OriginList.LILY_OF_THE_VOID.getDataPath("grants_weakness"));
    public static final TagKey<Item> GRANTS_WITHER = POItemTags.of(OriginList.LILY_OF_THE_VOID.getDataPath("grants_wither"));

    public static final TagKey<Item> SOUL_CARRY = POItemTags.of(OriginList.KRAKEN_OF_DECAY.getDataPath("soul_carry"));
    public static final TagKey<Item> MAX_SOUL_CONSUME = POItemTags.of(OriginList.KRAKEN_OF_DECAY.getDataPath("max_consume"));
    public static final TagKey<Item> MEDIUM_SOUL_CONSUME = POItemTags.of(OriginList.KRAKEN_OF_DECAY.getDataPath("medium_consume"));
    public static final TagKey<Item> WEAK_SOUL_CONSUME = POItemTags.of(OriginList.KRAKEN_OF_DECAY.getDataPath("weak_consume"));

    public static final TagKey<Item> LIGHT_SOURCES = POItemTags.of(OriginList.FAERIE_MOTH.getDataPath("light_sources"));

    public static final TagKey<Item> SUMMON_AOE = POItemTags.of(OriginList.ALRAUNE.getDataPath("summon_aoe"));
    public static final TagKey<Item> SUMMON_PROJECTILE = POItemTags.of(OriginList.ALRAUNE.getDataPath("summon_projectile"));
    public static final TagKey<Item> SUMMON_PULL = POItemTags.of(OriginList.ALRAUNE.getDataPath("summon_pull"));
    public static final TagKey<Item> SUMMON_PUSH = POItemTags.of(OriginList.ALRAUNE.getDataPath("summon_push"));
    public static final TagKey<Item> FERTILISER = POItemTags.of(OriginList.ALRAUNE.getDataPath("super_fertiliser"));

    private static TagKey<Item> of (String path) {
        return TagKey.of(RegistryKeys.ITEM, ProviOriginsMain.identifier(path));
    }
}
