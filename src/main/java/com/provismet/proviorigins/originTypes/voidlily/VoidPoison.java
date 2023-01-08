package com.provismet.proviorigins.originTypes.voidlily;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class VoidPoison extends StatusEffect {
    public VoidPoison () {
        super(StatusEffectCategory.HARMFUL, 0x7E0DB4);
    }

    @Override
    public boolean canApplyUpdateEffect (int duration, int amplifier) {
        return duration % 25 == 0;
    }

    @Override
    public void applyUpdateEffect (LivingEntity entity, int amplifier) {
        entity.damage(VoidLily.VOID_POISON, 1.5f + (0.5f * amplifier));
    }
}
