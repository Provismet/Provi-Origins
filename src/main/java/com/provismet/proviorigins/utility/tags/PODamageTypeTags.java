package com.provismet.proviorigins.utility.tags;

import com.provismet.proviorigins.ProviOriginsMain;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public abstract class PODamageTypeTags {
    public static final TagKey<DamageType> DISABLES_SHIELDS = PODamageTypeTags.of("disables_shields");
    public static final TagKey<DamageType> ALWAYS_BLOCK = PODamageTypeTags.of("always_block");

    private static TagKey<DamageType> of (String identifierPath) {
        return TagKey.of(RegistryKeys.DAMAGE_TYPE, ProviOriginsMain.identifier(identifierPath));
    }
}
