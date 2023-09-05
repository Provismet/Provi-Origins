package com.provismet.proviorigins.content.particles.effects;

import org.joml.Vector3f;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.provismet.proviorigins.content.registries.Particles;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.AbstractDustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public class FlowerParticleEffect extends AbstractDustParticleEffect {
    @SuppressWarnings("deprecation")
    public static final ParticleEffect.Factory<FlowerParticleEffect> PARAMETERS_FACTORY = new ParticleEffect.Factory<FlowerParticleEffect>(){

        @Override
        public FlowerParticleEffect read (ParticleType<FlowerParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            Vector3f vector3f = AbstractDustParticleEffect.readColor(stringReader);
            stringReader.expect(' ');
            float f = stringReader.readFloat();
            return new FlowerParticleEffect(vector3f, f);
        }

        @Override
        public FlowerParticleEffect read (ParticleType<FlowerParticleEffect> particleType, PacketByteBuf packetByteBuf) {
            return new FlowerParticleEffect(AbstractDustParticleEffect.readColor(packetByteBuf), packetByteBuf.readFloat());
        }
    };

    public FlowerParticleEffect(Vector3f color, float scale) {
        super(color, scale);
    }

    @Override
    public ParticleType<?> getType () {
        return Particles.FLOWER;
    }
}
