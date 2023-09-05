package com.provismet.proviorigins.content.particles.effects;

import org.joml.Vector3f;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.provismet.proviorigins.content.registries.Particles;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.AbstractDustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;

public class TelegraphParticleEffect implements ParticleEffect {
    protected final Vector3f colour;
    protected final float alpha;
    protected final float scale;
    protected final int duration;

    public TelegraphParticleEffect (Vector3f colour, float alpha, float scale, int duration) {
        this.colour = colour;
        this.alpha = alpha;
        this.scale = scale;
        this.duration = duration;
    }

    @SuppressWarnings("deprecation")
    public static final ParticleEffect.Factory<TelegraphParticleEffect> PARAMETERS_FACTORY = new ParticleEffect.Factory<TelegraphParticleEffect>() {
        @Override
        public TelegraphParticleEffect read (ParticleType<TelegraphParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            Vector3f vector3f = AbstractDustParticleEffect.readColor(stringReader);
            stringReader.expect(' ');
            float alpha = stringReader.readFloat();
            stringReader.expect(' ');
            float scale = stringReader.readFloat();
            stringReader.expect(' ');
            int duration = stringReader.readInt();
            return new TelegraphParticleEffect(vector3f, alpha, scale, duration);
        }

        @Override
        public TelegraphParticleEffect read (ParticleType<TelegraphParticleEffect> particleType, PacketByteBuf packetByteBuf) {
            return new TelegraphParticleEffect (AbstractDustParticleEffect.readColor(packetByteBuf), packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readInt());
        }
    };

    @Override
    public ParticleType<TelegraphParticleEffect> getType () {
        return Particles.TELEGRAPH;
    }

    @Override
    public void write (PacketByteBuf buffer) {
        buffer.writeFloat(this.colour.x());
        buffer.writeFloat(this.colour.y());
        buffer.writeFloat(this.colour.z());
        buffer.writeFloat(this.alpha);
        buffer.writeFloat(this.scale);
        buffer.writeInt(this.duration);
    }

    @Override
    public String asString () {
        return String.format("%s %.2f %.2f %.2f %.2f %.2f %d", Registries.PARTICLE_TYPE.getId(this.getType()), this.colour.x(), this.colour.y(), this.colour.z(), this.alpha, this.scale, this.duration);
    }

    public Vector3f getColour () {
        return this.colour;
    }

    public float getAlpha () {
        return this.alpha;
    }

    public float getScale () {
        return this.scale;
    }

    public int getDuration () {
        return this.duration;
    }
}
