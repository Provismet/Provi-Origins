package com.provismet.proviorigins.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;

@SuppressWarnings("rawtypes")
public class ModifyPassengerHeightPower extends Power {
    public final Double offset;

    public ModifyPassengerHeightPower(PowerType<?> type, LivingEntity entity, Double offset) {
        super(type, entity);
        this.offset = offset;
    }
    
    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("modify_passenger_height"),
            new SerializableData()
            .add("offset", SerializableDataTypes.DOUBLE),
            data -> (type, player) -> new ModifyPassengerHeightPower(type, player, data.getDouble("offset")))
            .allowCondition();
    }
}
