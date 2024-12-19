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

public record FlowerParticleEffect (Vector3f colour, float scale) implements ParticleEffect {
    public static final MapCodec<FlowerParticleEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Codecs.VECTOR_3F.fieldOf("colour").forGetter(effect -> effect.colour),
            Codecs.POSITIVE_FLOAT.fieldOf("scale").forGetter(effect -> effect.scale)
        ).apply(instance, FlowerParticleEffect::new)
    );

    public static final PacketCodec<RegistryByteBuf, FlowerParticleEffect> PACKET_CODEC = PacketCodec.tuple(
        PacketCodecs.VECTOR3F,
        effect -> effect.colour,
        PacketCodecs.FLOAT,
        effect -> effect.scale,
        FlowerParticleEffect::new
    );

    @Override
    public ParticleType<FlowerParticleEffect> getType () {
        return POParticles.FLOWER;
    }
}
