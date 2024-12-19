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

public class ModifyDarknessPulsePower extends PowerType {
    private static final String MULTIPLIER_LABEL = "multiplier";
    private static final String ADDITION_LABEL = "addition";

    private final float multiplier;
    private final float addition;

    public static final TypedDataObjectFactory<ModifyDarknessPulsePower> DATA_FACTORY = PowerType.createConditionedDataFactory(
        new SerializableData()
            .add(MULTIPLIER_LABEL, SerializableDataTypes.FLOAT, 1f)
            .add(ADDITION_LABEL, SerializableDataTypes.FLOAT, 0f),
        (data, condition) -> new ModifyDarknessPulsePower(
            data.getFloat(MULTIPLIER_LABEL),
            data.getFloat(ADDITION_LABEL),
            condition
        ),
        (powerType, data) -> data.instance()
            .set(MULTIPLIER_LABEL, powerType.multiplier)
            .set(ADDITION_LABEL, powerType.addition)
    );

    public ModifyDarknessPulsePower (float multiplier, float addition, Optional<EntityCondition> condition) {
        super(condition);
        this.multiplier = multiplier;
        this.addition = addition;
    }
    
    public float apply (float darknessModifier) {
        return darknessModifier * this.multiplier + this.addition;
    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig () {
        return POPowerTypes.MODIFY_DARKNESS_PULSE;
    }
}
