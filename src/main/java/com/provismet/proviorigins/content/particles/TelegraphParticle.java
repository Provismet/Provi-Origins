package com.provismet.proviorigins.content.particles;

import com.provismet.proviorigins.content.particles.effects.TelegraphParticleEffect;
import com.provismet.proviorigins.content.particles.utility.FlatParticle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;

public class TelegraphParticle<T extends TelegraphParticleEffect> extends FlatParticle {
    private final float maxScale;
    private final float staticAlpha;

    protected TelegraphParticle (ClientWorld clientWorld, double x, double y, double z, T particleEffect, SpriteProvider spriteProvider) {
        this(clientWorld, x, y, z, 0.0, 0.0, 0.0, particleEffect, spriteProvider);
    }

    protected TelegraphParticle (ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ, T particleEffect, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, spriteProvider);
        this.setSpriteForAge(this.spriteProvider);
        this.scale = 0f;
        this.red = particleEffect.getColour().x();
        this.green = particleEffect.getColour().y();
        this.blue = particleEffect.getColour().z();
        this.alpha = particleEffect.getAlpha();
        this.staticAlpha = alpha;
        this.maxScale = particleEffect.getScale();
        this.maxAge = particleEffect.getDuration();
    }

    @Override
    public void tick () {
        super.tick();
        this.setSpriteForAge(this.spriteProvider);
        this.setAlpha(this.staticAlpha);

        if ((float)this.age > (float)this.maxAge / 3f) this.scale = this.maxScale;
        else this.scale = this.maxScale * (((float)this.age * 3f) / (float)this.maxAge);
    }
    
    @Environment(value=EnvType.CLIENT)
    public static class Factory implements ParticleFactory<TelegraphParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory (SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle (TelegraphParticleEffect particleEffect, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new TelegraphParticle<TelegraphParticleEffect>(world, x, y, z, particleEffect, spriteProvider);
        }
        
    }
}
