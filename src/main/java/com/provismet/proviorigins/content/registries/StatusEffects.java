package com.provismet.proviorigins.content.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.content.StatusEffects.AlertEffect;
import com.provismet.proviorigins.content.StatusEffects.SleepEffect;
import com.provismet.proviorigins.content.StatusEffects.Untargetable;
import com.provismet.proviorigins.content.StatusEffects.VoidCorruption;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class StatusEffects {
    public static final StatusEffect VOID_CORRUPTION = new VoidCorruption();
    public static final StatusEffect UNTARGETABLE = new Untargetable();
    public static final StatusEffect SLEEP = new SleepEffect();
    public static final StatusEffect ALERT = new AlertEffect();

    public static void register () {
        Registry.register(Registries.STATUS_EFFECT, ProviOriginsMain.identifier("void_corruption"), VOID_CORRUPTION);
        Registry.register(Registries.STATUS_EFFECT, ProviOriginsMain.identifier("untargetable"), UNTARGETABLE);
        Registry.register(Registries.STATUS_EFFECT, ProviOriginsMain.identifier("sleep"), SLEEP);
        Registry.register(Registries.STATUS_EFFECT, ProviOriginsMain.identifier("alert"), ALERT);
    }
}
