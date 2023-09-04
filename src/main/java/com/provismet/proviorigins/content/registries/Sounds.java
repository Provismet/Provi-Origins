package com.provismet.proviorigins.content.registries;

import com.provismet.proviorigins.ProviOriginsMain;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public class Sounds {
    public static final SoundEvent LILY_MIMIC = SoundEvent.of(ProviOriginsMain.identifier("origin.voidlily.mimic"));
    public static final SoundEvent LILY_MIMIC_END = SoundEvent.of(ProviOriginsMain.identifier("origin.voidlily.mimic_end"));
    public static final SoundEvent LILY_TELEPORT = SoundEvent.of(ProviOriginsMain.identifier("origin.voidlily.teleport"));

    private static void registerSoundEvent (SoundEvent sound) {
        Registry.register(Registries.SOUND_EVENT, sound.getId(), sound);
    }

    public static void register () {
        registerSoundEvent(LILY_MIMIC);
        registerSoundEvent(LILY_MIMIC_END);
        registerSoundEvent(LILY_TELEPORT);
    }
}
