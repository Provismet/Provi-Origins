package com.provismet.proviorigins.content.registries;

import com.provismet.proviorigins.ProviOriginsMain;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public class Sounds {
    public static final SoundEvent LILY_MIMIC = SoundEvent.of(ProviOriginsMain.identifier("origin.voidlily.mimic"));
    public static final SoundEvent LILY_MIMIC_END = SoundEvent.of(ProviOriginsMain.identifier("origin.voidlily.mimic_end"));
    public static final SoundEvent LILY_TELEPORT = SoundEvent.of(ProviOriginsMain.identifier("origin.voidlily.teleport"));

    public static final SoundEvent KRAKEN_BIDE = SoundEvent.of(ProviOriginsMain.identifier("origin.decaykraken.bide"));
    public static final SoundEvent KRAKEN_SOUL_STEAL = SoundEvent.of(ProviOriginsMain.identifier("origin.decaykraken.soul_steal"));
    public static final SoundEvent KRAKEN_INK = SoundEvent.of(ProviOriginsMain.identifier("origin.decaykraken.ink"));
    public static final SoundEvent KRAKEN_SALT = SoundEvent.of(ProviOriginsMain.identifier("origin.decaykraken.salt"));

    public static final SoundEvent DRAKLING_EVOLVE = SoundEvent.of(ProviOriginsMain.identifier("origin.drakling.evolve"));
    public static final SoundEvent DRAKLING_BIRTH = SoundEvent.of(ProviOriginsMain.identifier("origin.drakling.birth"));
    public static final SoundEvent DRAKLING_FLAP = SoundEvent.of(ProviOriginsMain.identifier("origin.drakling.flap"));

    private static void registerSoundEvent (SoundEvent sound) {
        Registry.register(Registries.SOUND_EVENT, sound.getId(), sound);
    }

    public static void register () {
        registerSoundEvent(LILY_MIMIC);
        registerSoundEvent(LILY_MIMIC_END);
        registerSoundEvent(LILY_TELEPORT);

        registerSoundEvent(KRAKEN_BIDE);
        registerSoundEvent(KRAKEN_SOUL_STEAL);
        registerSoundEvent(KRAKEN_INK);
        registerSoundEvent(KRAKEN_SALT);

        registerSoundEvent(DRAKLING_EVOLVE);
        registerSoundEvent(DRAKLING_BIRTH);
        registerSoundEvent(DRAKLING_FLAP);
    }
}
