package com.provismet.proviorigins.powers;

import com.provismet.proviorigins.registries.POPowerTypes;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class EmissivePower extends PowerType {
    public static final String LIGHT = "light";
    public static final String DYNAMIC_LIGHT = "dynamic_light";

    public final int light;
    public final int dynamicLight;

    public static final TypedDataObjectFactory<EmissivePower> DATA_FACTORY = PowerType.createConditionedDataFactory(
        new SerializableData()
            .add(LIGHT, SerializableDataTypes.INT)
            .add(DYNAMIC_LIGHT, SerializableDataTypes.INT, 0),
        (data, condition) -> new EmissivePower(
            data.getInt(LIGHT),
            data.getInt(DYNAMIC_LIGHT),
            condition
        ),
        (powerType, data) -> data.instance()
            .set(LIGHT, powerType.light)
            .set(DYNAMIC_LIGHT, powerType.dynamicLight)
    );

    public EmissivePower(int light, int dynamicLight, Optional<EntityCondition> condition) {
        super(condition);
        this.light = light;
        this.dynamicLight = dynamicLight;
    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig () {
        return POPowerTypes.EMISSIVE;
    }
}
