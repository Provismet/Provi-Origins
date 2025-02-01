package com.provismet.proviorigins.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public abstract class POLootFunctions {
    public static final RegistryKey<LootFunction> APPLY_CURSES = POLootFunctions.of("apply_curses");

    private static RegistryKey<LootFunction> of (String path) {
        return RegistryKey.of(RegistryKeys.ITEM_MODIFIER, ProviOriginsMain.identifier(path));
    }
}
