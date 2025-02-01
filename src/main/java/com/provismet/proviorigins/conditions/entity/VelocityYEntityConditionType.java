package com.provismet.proviorigins.conditions.entity;

import com.provismet.proviorigins.registries.POEntityConditionTypes;
import com.provismet.proviorigins.utility.constants.FieldNames;

import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.context.EntityConditionContext;
import io.github.apace100.apoli.condition.type.EntityConditionType;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import org.jetbrains.annotations.NotNull;

public class VelocityYEntityConditionType extends EntityConditionType {
    private final Comparison comparisonType;
    private final double compareTo;

    public static final TypedDataObjectFactory<VelocityYEntityConditionType> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(FieldNames.COMPARISON, ApoliDataTypes.COMPARISON)
            .add(FieldNames.COMPARE_TO, SerializableDataTypes.DOUBLE),
        data -> new VelocityYEntityConditionType(
            data.get(FieldNames.COMPARISON),
            data.getDouble(FieldNames.COMPARE_TO)
        ),
        (conditionType, data) -> data.instance()
            .set(FieldNames.COMPARISON, conditionType.comparisonType)
            .set(FieldNames.COMPARE_TO, conditionType.compareTo)
    );

    public VelocityYEntityConditionType (Comparison comparison, double compareTo) {
        this.comparisonType = comparison;
        this.compareTo = compareTo;
    }

    @Override
    public boolean test (EntityConditionContext context) {
        return this.comparisonType.compare(context.entity().getVelocity().getY(), this.compareTo);
    }

    @Override
    public @NotNull ConditionConfiguration<VelocityYEntityConditionType> getConfig () {
        return POEntityConditionTypes.VELOCITY_Y;
    }
}
