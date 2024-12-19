package com.provismet.proviorigins.conditions.entity;

import java.util.List;
import java.util.Optional;

import com.provismet.proviorigins.registries.POEntityConditionTypes;
import com.provismet.proviorigins.utility.ConditionUtil;
import com.provismet.proviorigins.utility.constants.FieldNames;

import io.github.apace100.apoli.condition.BiEntityCondition;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.condition.type.EntityConditionType;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.NotNull;

public class EntityInRadiusCondition extends EntityConditionType {
    private static final String INCLUDE_SELF = "include_self";

    private final double radius;
    private final Comparison comparisonType;
    private final int compareTo;
    private final boolean includeSelf;
    private final Optional<EntityCondition> entityCondition;
    private final Optional<BiEntityCondition> biEntityCondition;

    public static final TypedDataObjectFactory<EntityInRadiusCondition> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(FieldNames.ENTITY_CONDITION, EntityCondition.DATA_TYPE.optional(), Optional.empty())
            .add(FieldNames.BIENTITY_CONDITION, BiEntityCondition.DATA_TYPE.optional(), Optional.empty())
            .add(FieldNames.RADIUS, SerializableDataTypes.DOUBLE)
            .add(INCLUDE_SELF, SerializableDataTypes.BOOLEAN, true)
            .add(FieldNames.COMPARISON, ApoliDataTypes.COMPARISON, Comparison.GREATER_THAN_OR_EQUAL)
            .add(FieldNames.COMPARE_TO, SerializableDataTypes.INT, 1),
        data -> new EntityInRadiusCondition(
            data.getDouble(FieldNames.RADIUS),
            data.get(FieldNames.COMPARISON),
            data.getInt(FieldNames.COMPARE_TO),
            data.getBoolean(INCLUDE_SELF),
            data.get(FieldNames.ENTITY_CONDITION),
            data.get(FieldNames.BIENTITY_CONDITION)
        ),
        (conditionType, data) -> data.instance()
            .set(FieldNames.RADIUS, conditionType.radius)
            .set(FieldNames.COMPARISON, conditionType.comparisonType)
            .set(FieldNames.COMPARE_TO, conditionType.compareTo)
            .set(INCLUDE_SELF, conditionType.includeSelf)
            .set(FieldNames.ENTITY_CONDITION, conditionType.entityCondition)
            .set(FieldNames.BIENTITY_CONDITION, conditionType.biEntityCondition)
    );

    public EntityInRadiusCondition (double radius, Comparison comparisonType, int compareTo, boolean includeSelf, Optional<EntityCondition> entityCondition, Optional<BiEntityCondition> biEntityCondition) {
        this.radius = radius;
        this.comparisonType = comparisonType;
        this.compareTo = compareTo;
        this.includeSelf = includeSelf;
        this.entityCondition = entityCondition;
        this.biEntityCondition = biEntityCondition;
    }

    @Override
    public boolean test (Entity entity) {
        int count = 0;
        int stopAt = -1;
        List<Entity> others = entity.getWorld().getOtherEntities(entity, Box.from(entity.getPos()).expand(radius));

        switch(this.comparisonType) {
            case EQUAL:
            case LESS_THAN_OR_EQUAL:
            case GREATER_THAN:
                stopAt = this.compareTo + 1;
                break;

            case LESS_THAN:
            case GREATER_THAN_OR_EQUAL:
                stopAt = this.compareTo;
                break;

            case NONE:
            case NOT_EQUAL:
                break;
        }

        if (this.includeSelf && this.entityCondition.isPresent() && this.entityCondition.get().test(entity)) {
            ++count;
            if (count == stopAt) return this.comparisonType.compare(count, compareTo);
        }

        for (Entity other : others) {
            if (ConditionUtil.emptyOrTest(this.entityCondition, other) && ConditionUtil.emptyOrTest(this.biEntityCondition, entity, other)) {
                ++count;
                if (count == stopAt) break;
            }
        }

        return this.comparisonType.compare(count, this.compareTo);
    }

    @Override
    public @NotNull ConditionConfiguration<EntityInRadiusCondition> getConfig () {
        return POEntityConditionTypes.ENTITY_IN_RADIUS;
    }
}
