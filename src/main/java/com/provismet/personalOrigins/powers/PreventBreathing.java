package com.provismet.personalOrigins.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class PreventBreathing extends Power {
    private final DamageSource damageSource;

    public PreventBreathing (PowerType<?> type, LivingEntity entity, DamageSource damageSource) {
        super(type, entity);
        this.damageSource = damageSource;
    }

    public DamageSource getDamageSource () {
        return this.damageSource;
    }

    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("prevent_breathing"),
            new SerializableData().add("damage_source", SerializableDataTypes.DAMAGE_SOURCE),
            data -> (type, player) -> new PreventBreathing(type, player, ((DamageSource)data.get("damage_source")))).allowCondition();
    }
}
