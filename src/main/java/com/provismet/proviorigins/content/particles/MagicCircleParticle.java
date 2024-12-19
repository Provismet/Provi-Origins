package com.provismet.proviorigins.content.particles;

import com.provismet.lilylib.particle.FlatParticle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;

public class MagicCircleParticle extends FlatParticle {
    private static final float MAX_SCALE = 8f;
    private static final float SCALE_TIME = 5f;

    private final float rotationSpeed;

    private float prevScale;

    protected MagicCircleParticle (ClientWorld clientWorld, double x, double y, double z, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, spriteProvider);
        this.maxAge = 30;
        this.scale = 0f;
        this.prevScale = this.scale;
        this.rotationSpeed = (float)Math.toRadians(random.nextBoolean() ? 5.0 : -5.0);
    }

    @Override
    public void tick () {
        super.tick();
        this.setAngleY(this.angle + this.rotationSpeed);
        this.prevScale = this.scale;

        if (this.scale < MAX_SCALE) this.scale += MAX_SCALE / SCALE_TIME;
        else if (this.scale > MAX_SCALE) this.scale = MAX_SCALE;

        float multiplier = (float)this.age / (float)this.maxAge;
        float tempAlpha = 1f - multiplier * multiplier * multiplier;
        this.alpha = Math.max(tempAlpha, 0.11f);
    }

    @Override
    public float getSize (float tickDelta) {
        return MathHelper.lerp(tickDelta, this.prevScale, this.scale);
    }

    @Environment(value=EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory (SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle (SimpleParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new MagicCircleParticle(clientWorld, x, y, z, this.spriteProvider);
        }
    }
}
