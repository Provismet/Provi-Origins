package com.provismet.proviorigins.content.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.content.particles.effects.CrystalParticleEffect;
import com.provismet.proviorigins.content.particles.effects.FlowerParticleEffect;
import com.provismet.proviorigins.content.particles.effects.TelegraphParticleEffect;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class Particles {
    public static final DefaultParticleType LILY_PETAL = FabricParticleTypes.simple();
    public static final DefaultParticleType SOUL_CIRCLE = FabricParticleTypes.simple(true);
    public static final DefaultParticleType MAGIC_CIRCLE = FabricParticleTypes.simple();

    public static final ParticleType<FlowerParticleEffect> FLOWER = FabricParticleTypes.complex(FlowerParticleEffect.PARAMETERS_FACTORY);
    public static final ParticleType<TelegraphParticleEffect> TELEGRAPH = FabricParticleTypes.complex(true, TelegraphParticleEffect.PARAMETERS_FACTORY);
    public static final ParticleType<CrystalParticleEffect> CRYSTAL = FabricParticleTypes.complex(true, CrystalParticleEffect.PARAMETERS_FACTORY);

    private static void registerSimple (DefaultParticleType particle, String path) {
        Registry.register(Registries.PARTICLE_TYPE, ProviOriginsMain.identifier(path), particle);
    }

    private static <T extends ParticleEffect> void registerComplex (ParticleType<T> particle, String path) {
        Registry.register(Registries.PARTICLE_TYPE, ProviOriginsMain.identifier(path), particle);
    }

    public static void register () {
        registerSimple(LILY_PETAL, "lily_petal");
        registerSimple(SOUL_CIRCLE, "soul_circle");
        registerSimple(MAGIC_CIRCLE, "magic_circle");

        registerComplex(FLOWER, "flower");
        registerComplex(TELEGRAPH, "telegraph");
        registerComplex(CRYSTAL, "crystal");
    }
}
