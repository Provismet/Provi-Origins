package com.provismet.proviorigins.content.statusEffects;

import java.util.UUID;

import com.provismet.proviorigins.content.registries.StatusEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

public class SleepEffect extends StatusEffect {
    public SleepEffect () {
        super(StatusEffectCategory.HARMFUL, 0xFF9EC8);
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, UUID.nameUUIDFromBytes("PO: SleepMovementDown".getBytes()).toString(), -1f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_FLYING_SPEED, UUID.nameUUIDFromBytes("PO: SleepFlyingSpeedDown".getBytes()).toString(), -1f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, UUID.nameUUIDFromBytes("PO: SleepAttackSpeedDown".getBytes()).toString(), -1f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, UUID.nameUUIDFromBytes("PO: SleepAttackDown".getBytes()).toString(), -1f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public boolean canApplyUpdateEffect (int duration, int amplifier) {
        return duration <= 1;
    }

    @Override
    public void applyUpdateEffect (LivingEntity entity, int amplifier) {
        if (!entity.hasStatusEffect(StatusEffects.ALERT)) entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ALERT, 150));
    }
}
