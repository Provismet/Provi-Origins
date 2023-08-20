package com.provismet.proviorigins.content.statusEffects;

import com.provismet.proviorigins.content.DamageTypes;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class VoidCorruption extends StatusEffect {
    public VoidCorruption () {
        super(StatusEffectCategory.HARMFUL, 0x7E0DB4);
    }

    @Override
    public boolean canApplyUpdateEffect (int duration, int amplifier) {
        return duration % 25 == 0;
    }

    @Override
    public void applyUpdateEffect (LivingEntity entity, int amplifier) {
        entity.damage(entity.getDamageSources().create(DamageTypes.VOID_CORRUPTION), 1.5f + (0.5f * amplifier));
    }
}
