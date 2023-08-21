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
    private final float startingScale = 0.42f;
    private final float endingScale = 1f;

    private final boolean rotationDirection;

    protected SoulCircleParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);
        this.maxAge = 10;
        this.scale = startingScale;
        this.alpha = 0.8f;
        this.rotationDirection = random.nextBoolean();
    }

    @Override
    public void tick () {
        super.tick();
        this.scale = startingScale + (endingScale - startingScale) * ((float)this.age / (float)this.maxAge);
        this.prevAngle = this.angle;
        this.angle += (float)Math.toRadians(5.0 * (rotationDirection ? 1.0 : -1.0));
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
