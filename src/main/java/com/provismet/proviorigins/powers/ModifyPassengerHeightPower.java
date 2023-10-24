package com.provismet.proviorigins.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;

@SuppressWarnings("rawtypes")
public class ModifyPassengerHeightPower extends Power {
    private static final String OFFSET_ADD_LABEL = "offset_add";
    private static final String OFFSET_MULTIPLY_LABEL = "offset_multiply";

    public final double offsetAdditive;
    public final double offsetMultiplicative;

    public ModifyPassengerHeightPower(PowerType<?> type, LivingEntity entity, double offsetAdd, double offsetMul) {
        super(type, entity);
        this.offsetAdditive = offsetAdd;
        this.offsetMultiplicative = offsetMul;
    }
    
    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("modify_passenger_height"),
            new SerializableData()
            .add(OFFSET_ADD_LABEL, SerializableDataTypes.DOUBLE, 0.0)
            .add(OFFSET_MULTIPLY_LABEL, SerializableDataTypes.DOUBLE, 1.0),
            data -> (type, player) -> new ModifyPassengerHeightPower(type, player,
                data.getDouble(OFFSET_ADD_LABEL),
                data.getDouble(OFFSET_MULTIPLY_LABEL)
            )
        ).allowCondition();
    }
}
