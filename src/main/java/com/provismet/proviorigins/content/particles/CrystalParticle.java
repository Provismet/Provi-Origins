package com.provismet.proviorigins.content.particles;

import com.provismet.proviorigins.content.particles.effects.CrystalParticleEffect;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.AbstractDustParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;

public class CrystalParticle extends AbstractDustParticle<CrystalParticleEffect> {
    private final float rotationSpeed;

    protected CrystalParticle (ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CrystalParticleEffect parameters, SpriteProvider spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, parameters, spriteProvider);
        this.rotationSpeed = (float)Math.toRadians(random.nextDouble() * (random.nextBoolean() ? 2.5 : -2.5));
        this.maxAge /= 1.5f;
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
    public static class Factory implements ParticleFactory<CrystalParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory (SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle (CrystalParticleEffect dustParticleEffect, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new CrystalParticle(world, x, y, z, velocityX, velocityY, velocityZ, dustParticleEffect, spriteProvider);
        }
    }
}
