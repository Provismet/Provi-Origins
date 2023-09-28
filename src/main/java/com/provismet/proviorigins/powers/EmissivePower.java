package com.provismet.proviorigins.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;

@SuppressWarnings("rawtypes")
public class EmissivePower extends Power {
    public static final String LIGHT = "light";
    public static final String DYNAMIC_LIGHT = "dynamic_light";

    public final int light;
    public final int dynamicLight;

    public EmissivePower(PowerType<?> type, LivingEntity entity, int light, int dynamicLight) {
        super(type, entity);
        this.light = light;
        this.dynamicLight = dynamicLight;
    }
    
    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("emissive"),
        new SerializableData()
        .add(LIGHT, SerializableDataTypes.INT)
        .add(DYNAMIC_LIGHT, SerializableDataTypes.INT, 0),
        data -> (type, player) -> new EmissivePower(type, player,
            data.getInt(LIGHT),
            data.getInt(DYNAMIC_LIGHT)
        ))
        .allowCondition();
    }
}
