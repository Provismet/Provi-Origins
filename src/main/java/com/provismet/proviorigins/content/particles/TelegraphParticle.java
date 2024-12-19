package com.provismet.proviorigins.content.particles;

import com.provismet.lilylib.particle.FlatParticle;
import com.provismet.proviorigins.content.particles.effects.TelegraphParticleEffect;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;

public class TelegraphParticle<T extends TelegraphParticleEffect> extends FlatParticle {
    private final float maxScale;
    private final float staticAlpha;
    private float prevScale;

    protected TelegraphParticle (ClientWorld clientWorld, double x, double y, double z, T particleEffect, SpriteProvider spriteProvider) {
        this(clientWorld, x, y, z, 0.0, 0.0, 0.0, particleEffect, spriteProvider);
    }

    protected TelegraphParticle (ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ, T particleEffect, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, spriteProvider);
        this.setSpriteForAge(this.spriteProvider);
        this.scale = 0f;
        this.red = particleEffect.colour().x();
        this.green = particleEffect.colour().y();
        this.blue = particleEffect.colour().z();
        this.alpha = particleEffect.alpha();
        this.staticAlpha = alpha;
        this.maxScale = particleEffect.scale();
        this.maxAge = particleEffect.duration();
        this.prevScale = this.scale;
    }

    @Override
    public void tick () {
        super.tick();
        this.setSpriteForAge(this.spriteProvider);
        this.setAlpha(this.staticAlpha);
        this.prevScale = this.scale;

        if ((float)this.age > (float)this.maxAge / 3f) this.scale = this.maxScale;
        else this.scale = this.maxScale * (((float)this.age * 3f) / (float)this.maxAge);
    }

    @Override
    public float getSize (float tickDelta) {
        return MathHelper.lerp(tickDelta, this.prevScale, this.scale);
    }

    @Environment(value=EnvType.CLIENT)
    public static class Factory implements ParticleFactory<TelegraphParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory (SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle (TelegraphParticleEffect particleEffect, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new TelegraphParticle<>(world, x, y, z, particleEffect, spriteProvider);
        }
        
    }
}
