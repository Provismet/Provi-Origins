package com.provismet.proviorigins.content.particles;

import com.provismet.proviorigins.content.particles.effects.FlowerParticleEffect;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.AbstractDustParticle;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;

public class FlowerParticle extends AnimatedParticle {
    private final float rotationSpeed;

    protected FlowerParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, FlowerParticleEffect parameters, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0);
        this.rotationSpeed = (float)Math.toRadians(random.nextDouble() * (random.nextBoolean() ? 2.5 : -2.5));
        this.setSpriteForAge(spriteProvider);

        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;

        this.scale = parameters.scale();
        this.red = parameters.colour().x();
        this.green = parameters.colour().y();
        this.blue = parameters.colour().z();
        this.maxAge = (int)(10 * this.random.nextDouble()) + 10;
    }

    @Override
    public void tick () {
        super.tick();
        if (this.age > this.maxAge / 2) {
            this.setAlpha(1.0f - ((float)this.age - (float)(this.maxAge / 2)) / (float)this.maxAge);
        }
        this.prevAngle = this.angle;
        this.angle += this.rotationSpeed;
    }

    @Override
    public ParticleTextureSheet getType () {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(value=EnvType.CLIENT)
    public static class Factory implements ParticleFactory<FlowerParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory (SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle (FlowerParticleEffect dustParticleEffect, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new FlowerParticle(world, x, y, z, velocityX, velocityY, velocityZ, dustParticleEffect, spriteProvider);
        }
    }
}
