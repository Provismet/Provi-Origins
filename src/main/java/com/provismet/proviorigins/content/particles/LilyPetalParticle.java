package com.provismet.proviorigins.content.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class LilyPetalParticle extends AnimatedParticle {
    private final float rotationSpeed;

    protected LilyPetalParticle (ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0f);
        this.maxAge = 15 + (int)(10 * random.nextDouble());
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.setSpriteForAge(this.spriteProvider);
        this.angle = (float)Math.toRadians(random.nextDouble() * 360);
        this.prevAngle = angle;
        this.rotationSpeed = (float)Math.toRadians(random.nextDouble() * (random.nextBoolean() ? 2.5 : -2.5));
    }

    @Override
    public void tick () {
        super.tick();
        this.prevAngle = this.angle;
        this.angle += this.rotationSpeed;
    }

    @Environment(value=EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory (SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle (DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new LilyPetalParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider);
        }
    }
}
