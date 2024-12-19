package com.provismet.proviorigins.content.statusEffects;

import java.util.UUID;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.content.registries.POStatusEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

public class SleepEffect extends StatusEffect {
    public SleepEffect () {
        super(StatusEffectCategory.HARMFUL, 0xFF9EC8);
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, ProviOriginsMain.identifier("sleep_movement_down"), -1f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_FLYING_SPEED, ProviOriginsMain.identifier("sleep_flying_speed_down"), -1f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, ProviOriginsMain.identifier("sleep_attack_speed_down"), -1f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, ProviOriginsMain.identifier("sleep_attack_down"), -1f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean canApplyUpdateEffect (int duration, int amplifier) {
        return duration <= 1;
    }

    @Override
    public boolean applyUpdateEffect (LivingEntity entity, int amplifier) {
        if (!entity.hasStatusEffect(POStatusEffects.ALERT)) entity.addStatusEffect(new StatusEffectInstance(POStatusEffects.ALERT, 150));
        return true;
    }
}
