package com.provismet.proviorigins.powers;

import com.provismet.proviorigins.registries.POPowerTypes;
import com.provismet.proviorigins.utility.constants.FieldNames;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ModifyPassengerHeightPower extends PowerType {
    private static final String OFFSET_ADD_LABEL = "offset_add";
    private static final String OFFSET_MULTIPLY_LABEL = "offset_multiply";

    public final double offsetAdditive;
    public final double offsetMultiplicative;

    public static final TypedDataObjectFactory<ModifyPassengerHeightPower> DATA_FACTORY = PowerType.createConditionedDataFactory(
        new SerializableData()
            .add(OFFSET_ADD_LABEL, SerializableDataTypes.DOUBLE, 0.0)
            .add(OFFSET_MULTIPLY_LABEL, SerializableDataTypes.DOUBLE, 1.0),
        (data, condition) -> new ModifyPassengerHeightPower(
            data.getDouble(OFFSET_ADD_LABEL),
            data.getDouble(OFFSET_MULTIPLY_LABEL),
            condition
        ),
        (powerType, data) -> data.instance()
            .set(OFFSET_ADD_LABEL, powerType.offsetAdditive)
            .set(OFFSET_MULTIPLY_LABEL, powerType.offsetMultiplicative)
    );

    public ModifyPassengerHeightPower(double offsetAdd, double offsetMul, Optional<EntityCondition> condition) {
        super(condition);
        this.offsetAdditive = offsetAdd;
        this.offsetMultiplicative = offsetMul;
    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig () {
        return POPowerTypes.MODIFY_PASSENGER_HEIGHT;
    }
}
