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
    private static final float MAX_SCALE = 8f;
    private static final float SCALE_TIME = 5f;

    private final float rotationSpeed;

    protected MagicCircleParticle (ClientWorld clientWorld, double x, double y, double z, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, spriteProvider);
        this.maxAge = 30;
        this.scale = 0f;
        this.rotationSpeed = (float)Math.toRadians(random.nextBoolean() ? 5.0 : -5.0);
    }

    @Override
    public void tick () {
        super.tick();
        this.setAngleY(this.angle + this.rotationSpeed);

        if (this.scale < MAX_SCALE) this.scale += MAX_SCALE / SCALE_TIME;
        else if (this.scale > MAX_SCALE) this.scale = MAX_SCALE;

        float multiplier = (float)this.age / (float)this.maxAge;
        float tempAlpha = 1f - multiplier * multiplier * multiplier;
        this.alpha = tempAlpha >= 0.11f ? tempAlpha : 0.11f;
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
