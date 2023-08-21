package com.provismet.proviorigins.content.registries;

import com.provismet.proviorigins.content.particles.LilyPetalParticle;
import com.provismet.proviorigins.content.particles.SoulCircleParticle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry.PendingParticleFactory;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class ParticleFactories {
    private static void registerDefaultParticleFactory (DefaultParticleType particleType, PendingParticleFactory<DefaultParticleType> constructor) {
        ParticleFactoryRegistry.getInstance().register(particleType, constructor);
    }

    public static void register () {
        registerDefaultParticleFactory(Particles.LILY_PETAL_PARTICLE, LilyPetalParticle.Factory::new);
        registerDefaultParticleFactory(Particles.SOUL_CIRCLE_PARTICLE, SoulCircleParticle.Factory::new);
    }
}
