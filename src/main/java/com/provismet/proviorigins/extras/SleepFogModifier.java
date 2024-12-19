package com.provismet.proviorigins.extras;

import com.provismet.proviorigins.content.registries.POStatusEffects;

import net.minecraft.client.render.BackgroundRenderer.FogData;
import net.minecraft.client.render.BackgroundRenderer.FogType;
import net.minecraft.client.render.BackgroundRenderer.StatusEffectFogModifier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;

public class SleepFogModifier implements StatusEffectFogModifier {
    @Override
    public RegistryEntry<StatusEffect> getStatusEffect () {
        return POStatusEffects.SLEEP;
    }

    @Override
    public void applyStartEndModifier (FogData fogData, LivingEntity entity, StatusEffectInstance effect, float viewDistance, float tickDelta) {
        if (fogData.fogType == FogType.FOG_SKY) {
            fogData.fogStart = 0f;
            fogData.fogEnd = 2f;
        } else {
            fogData.fogStart = 0.5f;
            fogData.fogEnd = 5f;
        }
    }
    
}
