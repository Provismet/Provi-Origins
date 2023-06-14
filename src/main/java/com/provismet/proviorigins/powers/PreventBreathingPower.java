package com.provismet.proviorigins.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;

@SuppressWarnings("rawtypes")
public class PreventBreathingPower extends Power {
    private static final String DAMAGE_TYPE = "damage_type";
    private static final String RESPECT_BREATHING = "respect_water_breathing";

    private final RegistryKey<DamageType> damageType;
    public final boolean respectWaterBreathing;

    public PreventBreathingPower (PowerType<?> type, LivingEntity entity, RegistryKey<DamageType> damageType, boolean respectWaterBreathing) {
        super(type, entity);
        this.damageType = damageType;
        this.respectWaterBreathing = respectWaterBreathing;
    }

    public DamageSource getDamageSource () {
        return this.entity.getDamageSources().create(damageType);
    }

    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("prevent_breathing"),
            new SerializableData()
            .add(DAMAGE_TYPE, SerializableDataTypes.DAMAGE_TYPE)
            .add(RESPECT_BREATHING, SerializableDataTypes.BOOLEAN, true),
            data -> (type, player) -> new PreventBreathingPower(type, player,
                data.get(DAMAGE_TYPE),
                data.getBoolean(RESPECT_BREATHING)))
            .allowCondition();
    }
}
