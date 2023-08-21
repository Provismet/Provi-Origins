package com.provismet.proviorigins.content.particles;

import com.provismet.proviorigins.content.particles.utility.FlatParticle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class MagicCircleParticle extends FlatParticle {
    protected MagicCircleParticle (ClientWorld clientWorld, double x, double y, double z, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, spriteProvider);
        this.maxAge = 30;
        this.scale = 8f;
    }

    @Override
    public void tick () {
        super.tick();
        this.prevAngle = this.angle;
        this.angle += Math.toRadians(5.0);
    }

    @Environment(value=EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory (SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle (DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new MagicCircleParticle(clientWorld, x, y, z, this.spriteProvider);
        }
    }
}
