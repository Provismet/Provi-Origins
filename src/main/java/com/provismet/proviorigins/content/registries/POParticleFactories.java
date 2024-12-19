package com.provismet.proviorigins.content.registries;

import com.provismet.proviorigins.content.particles.CrystalParticle;
import com.provismet.proviorigins.content.particles.FlowerParticle;
import com.provismet.proviorigins.content.particles.LilyPetalParticle;
import com.provismet.proviorigins.content.particles.MagicCircleParticle;
import com.provismet.proviorigins.content.particles.SoulCircleParticle;
import com.provismet.proviorigins.content.particles.TelegraphParticle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry.PendingParticleFactory;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class POParticleFactories {
    private static void registerDefaultParticleFactory (SimpleParticleType particleType, PendingParticleFactory<SimpleParticleType> constructor) {
        ParticleFactoryRegistry.getInstance().register(particleType, constructor);
    }

    private static <T extends ParticleEffect> void registerParticleFactory (ParticleType<T> particleType, PendingParticleFactory<T> constructor) {
        ParticleFactoryRegistry.getInstance().register(particleType, constructor);
    }

    public static void register () {
        registerDefaultParticleFactory(POParticles.LILY_PETAL, LilyPetalParticle.Factory::new);
        registerDefaultParticleFactory(POParticles.SOUL_CIRCLE, SoulCircleParticle.Factory::new);
        registerDefaultParticleFactory(POParticles.MAGIC_CIRCLE, MagicCircleParticle.Factory::new);

        registerParticleFactory(POParticles.FLOWER, FlowerParticle.Factory::new);
        registerParticleFactory(POParticles.TELEGRAPH, TelegraphParticle.Factory::new);
        registerParticleFactory(POParticles.CRYSTAL, CrystalParticle.Factory::new);
    }
}
