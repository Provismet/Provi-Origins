package com.provismet.proviorigins.content.StatusEffects;

import com.provismet.proviorigins.ProviOriginsMain;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class StatusEffects {
    public static final StatusEffect VOID_CORRUPTION = new VoidCorruption();
    public static final StatusEffect UNTARGETABLE = new Untargetable();

    public static void register () {
        Registry.register(Registries.STATUS_EFFECT, ProviOriginsMain.identifier("void_corruption"), VOID_CORRUPTION);
        Registry.register(Registries.STATUS_EFFECT, ProviOriginsMain.identifier("untargetable"), UNTARGETABLE);
    }
}
