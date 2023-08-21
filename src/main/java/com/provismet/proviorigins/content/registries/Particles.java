package com.provismet.proviorigins.content.registries;

import com.provismet.proviorigins.ProviOriginsMain;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class Particles {
    public static final DefaultParticleType LILY_PETAL_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType SOUL_CIRCLE_PARTICLE = FabricParticleTypes.simple();

    private static void registerSimple (DefaultParticleType particle, String path) {
        Registry.register(Registries.PARTICLE_TYPE, ProviOriginsMain.identifier(path), particle);
    }

    public static void register () {
        registerSimple(LILY_PETAL_PARTICLE, "lily_petal");
        registerSimple(SOUL_CIRCLE_PARTICLE, "soul_circle");
    }
}
