package com.provismet.proviorigins.content.particles.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.dynamic.Codecs;
import org.joml.Vector3f;

import com.provismet.proviorigins.content.registries.POParticles;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public record TelegraphParticleEffect (Vector3f colour, float alpha, float scale, int duration) implements ParticleEffect {
    public static final MapCodec<TelegraphParticleEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Codecs.VECTOR_3F.fieldOf("colour").forGetter(effect -> effect.colour),
            Codecs.POSITIVE_FLOAT.fieldOf("alpha").forGetter(effect -> effect.alpha),
            Codecs.POSITIVE_FLOAT.fieldOf("scale").forGetter(effect -> effect.scale),
            Codecs.POSITIVE_INT.fieldOf("duration").forGetter(effect -> effect.duration)
        ).apply(instance, TelegraphParticleEffect::new)
    );

    public static final PacketCodec<RegistryByteBuf, TelegraphParticleEffect> PACKET_CODEC = PacketCodec.tuple(
        PacketCodecs.VECTOR3F,
        effect -> effect.colour,
        PacketCodecs.FLOAT,
        effect -> effect.alpha,
        PacketCodecs.FLOAT,
        effect -> effect.scale,
        PacketCodecs.INTEGER,
        effect -> effect.duration,
        TelegraphParticleEffect::new
    );

    @Override
    public ParticleType<TelegraphParticleEffect> getType () {
        return POParticles.TELEGRAPH;
    }
}
