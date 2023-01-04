package com.provismet.personalOrigins.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;

@SuppressWarnings("rawtypes")
public class EmissivePower extends Power {
    public static final String LIGHT = "light";

    public final int light;

    public EmissivePower(PowerType<?> type, LivingEntity entity, int light) {
        super(type, entity);
        this.light = light;
    }
    
    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("emissive"),
        new SerializableData()
        .add(LIGHT, SerializableDataTypes.INT),
        data -> (type, player) -> new EmissivePower(type, player, data.getInt(LIGHT)))
        .allowCondition();
    }
}
