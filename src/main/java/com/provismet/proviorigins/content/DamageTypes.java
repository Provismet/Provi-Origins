package com.provismet.proviorigins.content;

import com.provismet.proviorigins.ProviOriginsMain;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class DamageTypes {
    public static final RegistryKey<DamageType> VOID_CORRUPTION = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, ProviOriginsMain.identifier("void_corrupted"));
}
