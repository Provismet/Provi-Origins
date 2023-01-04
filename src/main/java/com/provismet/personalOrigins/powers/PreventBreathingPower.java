package com.provismet.personalOrigins.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

@SuppressWarnings("rawtypes")
public class PreventBreathingPower extends Power {
    private static final String DAMAGE_SOURCE = "damage_source";
    private static final String RESPECT_BREATHING = "respect_water_breathing";

    private final DamageSource damageSource;
    public final boolean respectWaterBreathing;

    public PreventBreathingPower (PowerType<?> type, LivingEntity entity, DamageSource damageSource, boolean respectWaterBreathing) {
        super(type, entity);
        this.damageSource = damageSource;
        this.respectWaterBreathing = respectWaterBreathing;
    }

    public DamageSource getDamageSource () {
        return this.damageSource;
    }

    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("prevent_breathing"),
            new SerializableData()
            .add(DAMAGE_SOURCE, SerializableDataTypes.DAMAGE_SOURCE)
            .add(RESPECT_BREATHING, SerializableDataTypes.BOOLEAN, true),
            data -> (type, player) -> new PreventBreathingPower(type, player, ((DamageSource)data.get(DAMAGE_SOURCE)), data.getBoolean(RESPECT_BREATHING)))
            .allowCondition();
    }
}
