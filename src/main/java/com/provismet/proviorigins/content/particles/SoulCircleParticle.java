package com.provismet.proviorigins.content.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SoulCircleParticle extends SpriteBillboardParticle {
    private static final float STARTING_SCALE = 0.42f;
    private static final float ENDING_SCALE = 1f;

    private final float rotationSpeed;

    protected SoulCircleParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);
        this.maxAge = 10;
        this.scale = STARTING_SCALE;
        this.alpha = 0.8f;
        this.rotationSpeed = (float)Math.toRadians(random.nextBoolean() ? 5.0 : -5.0);
    }

    @Override
    public void tick () {
        super.tick();
        this.scale = STARTING_SCALE + (ENDING_SCALE - STARTING_SCALE) * ((float)this.age / (float)this.maxAge);
        this.prevAngle = this.angle;
        this.angle += this.rotationSpeed;
        this.alpha = 0.8f - 0.6f * ((float)this.age / (float)this.maxAge);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }
    
    @Environment(value=EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory (SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle (DefaultParticleType defaultParticleType, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            SoulCircleParticle particle = new SoulCircleParticle(world, x, y, z);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}
