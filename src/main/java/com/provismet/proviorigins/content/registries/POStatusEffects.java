package com.provismet.proviorigins.content.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.content.statusEffects.AlertEffect;
import com.provismet.proviorigins.content.statusEffects.SleepEffect;
import com.provismet.proviorigins.content.statusEffects.Untargetable;
import com.provismet.proviorigins.content.statusEffects.VoidCorruption;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class POStatusEffects {
    public static final RegistryEntry<StatusEffect> VOID_CORRUPTION = register("void_corruption", new VoidCorruption());
    public static final RegistryEntry<StatusEffect> UNTARGETABLE = register("untargetable", new Untargetable());
    public static final RegistryEntry<StatusEffect> SLEEP = register("sleep", new SleepEffect());
    public static final RegistryEntry<StatusEffect> ALERT = register("alert", new AlertEffect());

    private static RegistryEntry<StatusEffect> register (String name, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, ProviOriginsMain.identifier(name), effect);
    }

    public static void init () {};
}
