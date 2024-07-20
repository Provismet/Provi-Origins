package com.provismet.proviorigins.content.statusEffects;

import com.provismet.proviorigins.content.registries.POStatusEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class AlertEffect extends StatusEffect {
    public AlertEffect () {
        super(StatusEffectCategory.BENEFICIAL, 0xFFFFFF);
    }

    @Override
    public void onApplied (LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        if (entity.hasStatusEffect(POStatusEffects.SLEEP)) entity.removeStatusEffect(POStatusEffects.SLEEP);
    }
}
