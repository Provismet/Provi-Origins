package com.provismet.proviorigins.extras;

import com.provismet.proviorigins.content.registries.StatusEffects;

import net.minecraft.client.render.BackgroundRenderer.FogData;
import net.minecraft.client.render.BackgroundRenderer.FogType;
import net.minecraft.client.render.BackgroundRenderer.StatusEffectFogModifier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class SleepFogModifier implements StatusEffectFogModifier {
    @Override
    public StatusEffect getStatusEffect () {
        return StatusEffects.SLEEP;
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
