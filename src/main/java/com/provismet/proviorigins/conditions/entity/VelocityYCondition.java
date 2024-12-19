package com.provismet.proviorigins.conditions.entity;

import com.provismet.proviorigins.registries.POEntityConditionTypes;
import com.provismet.proviorigins.utility.constants.FieldNames;

import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.EntityConditionType;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class VelocityYCondition extends EntityConditionType {
    private final Comparison comparisonType;
    private final double compareTo;

    public static final TypedDataObjectFactory<VelocityYCondition> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(FieldNames.COMPARISON, ApoliDataTypes.COMPARISON)
            .add(FieldNames.COMPARE_TO, SerializableDataTypes.DOUBLE),
        data -> new VelocityYCondition(
            data.get(FieldNames.COMPARISON),
            data.getDouble(FieldNames.COMPARE_TO)
        ),
        (conditionType, data) -> data.instance()
            .set(FieldNames.COMPARISON, conditionType.comparisonType)
            .set(FieldNames.COMPARE_TO, conditionType.compareTo)
    );

    public VelocityYCondition (Comparison comparison, double compareTo) {
        this.comparisonType = comparison;
        this.compareTo = compareTo;
    }

    @Override
    public boolean test (Entity entity) {
        return this.comparisonType.compare(entity.getVelocity().getY(), this.compareTo);
    }

    @Override
    public @NotNull ConditionConfiguration<VelocityYCondition> getConfig () {
        return POEntityConditionTypes.VELOCITY_Y;
    }
}
