package com.provismet.proviorigins.content.particles.effects;

import org.joml.Vector3f;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.provismet.proviorigins.content.registries.Particles;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.AbstractDustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public class CrystalParticleEffect extends AbstractDustParticleEffect {
    @SuppressWarnings("deprecation")
    public static final ParticleEffect.Factory<CrystalParticleEffect> PARAMETERS_FACTORY = new ParticleEffect.Factory<CrystalParticleEffect>(){

        @Override
        public CrystalParticleEffect read (ParticleType<CrystalParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            Vector3f vector3f = AbstractDustParticleEffect.readColor(stringReader);
            stringReader.expect(' ');
            float f = stringReader.readFloat();
            return new CrystalParticleEffect(vector3f, f);
        }

        @Override
        public CrystalParticleEffect read (ParticleType<CrystalParticleEffect> particleType, PacketByteBuf packetByteBuf) {
            return new CrystalParticleEffect(AbstractDustParticleEffect.readColor(packetByteBuf), packetByteBuf.readFloat());
        }
    };

    public CrystalParticleEffect (Vector3f color, float scale) {
        super(color, scale);
    }

    @Override
    public ParticleType<CrystalParticleEffect> getType () {
        return Particles.CRYSTAL;
    }
    
}
