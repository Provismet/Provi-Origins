package com.provismet.proviorigins.extras;

import com.provismet.proviorigins.ProviOriginsMain;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class Tags {
    public static class DamageTypes {
        public static final TagKey<DamageType> DISABLES_SHIELDS = DamageTypes.of("disables_shields");
        public static final TagKey<DamageType> ALWAYS_BLOCK = DamageTypes.of("always_block");

        private static TagKey<DamageType> of (String identifierPath) {
            return TagKey.of(RegistryKeys.DAMAGE_TYPE, ProviOriginsMain.identifier(identifierPath));
        }
    }
}
