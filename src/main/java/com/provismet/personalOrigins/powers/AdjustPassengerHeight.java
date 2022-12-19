package com.provismet.personalOrigins.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;

@SuppressWarnings("rawtypes")
public class AdjustPassengerHeight extends Power {
    public final Double offset;

    public AdjustPassengerHeight(PowerType<?> type, LivingEntity entity, Double offset) {
        super(type, entity);
        this.offset = offset;
    }
    
    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("passenger_height"),
            new SerializableData()
            .add("offset", SerializableDataTypes.DOUBLE),
            data -> (type, player) -> new AdjustPassengerHeight(type, player, data.getDouble("offset")))
            .allowCondition();
    }
}
