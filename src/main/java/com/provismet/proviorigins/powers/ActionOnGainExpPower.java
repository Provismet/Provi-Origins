package com.provismet.proviorigins.powers;

import java.util.Optional;

import com.provismet.proviorigins.registries.POPowerTypes;
import com.provismet.proviorigins.utility.constants.FieldNames;
import io.github.apace100.apoli.action.EntityAction;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import org.jetbrains.annotations.NotNull;

public class ActionOnGainExpPower extends PowerType {
    private final EntityAction entityAction;
    private final Comparison comparison;
    private final int compareTo;

    public ActionOnGainExpPower (EntityAction entityAction, Comparison comparison, int compareTo, Optional<EntityCondition> condition) {
        super(condition);
        this.entityAction = entityAction;
        this.comparison = comparison;
        this.compareTo = compareTo;
    }

    public static final TypedDataObjectFactory<ActionOnGainExpPower> DATA_FACTORY = PowerType.createConditionedDataFactory(
        new SerializableData()
            .add(FieldNames.ENTITY_ACTION, EntityAction.DATA_TYPE)
            .add(FieldNames.COMPARISON, ApoliDataTypes.COMPARISON, Comparison.GREATER_THAN_OR_EQUAL)
            .add(FieldNames.COMPARE_TO, SerializableDataTypes.INT, 1),
        (data, condition) -> new ActionOnGainExpPower(
            data.get(FieldNames.ENTITY_ACTION),
            data.get(FieldNames.COMPARISON),
            data.getInt(FieldNames.COMPARE_TO),
            condition
        ),
        (powerType, data) -> data.instance()
            .set(FieldNames.ENTITY_ACTION, powerType.entityAction)
            .set(FieldNames.COMPARISON, powerType.comparison)
            .set(FieldNames.COMPARE_TO, powerType.compareTo)
    );
    
    public void execute (int experienceAmount) {
        if (this.comparison.compare(experienceAmount, compareTo)) {
            this.entityAction.execute(this.getHolder());
        }
    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig () {
        return POPowerTypes.ACTION_ON_GAIN_EXPERIENCE;
    }
}
