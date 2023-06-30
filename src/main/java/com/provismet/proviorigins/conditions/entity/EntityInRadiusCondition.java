package com.provismet.proviorigins.conditions.entity;

import java.util.List;
import java.util.function.Predicate;

import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;

public class EntityInRadiusCondition {
    private static final String INCLUDE_SELF = "include_self";

    public static boolean condition (SerializableData.Instance data, Entity entity) {
        final double radius = data.getDouble(Powers.RADIUS);
        final int compareTo = data.getInt(Powers.COMPARE_TO);
        final boolean includeSelf = data.getBoolean(INCLUDE_SELF);
        Predicate<Entity> entityCondition = data.get(Powers.ENTITY_CONDITION);
        Comparison comparison = data.get(Powers.COMPARISON);

        int count = 0;
        int stopAt = -1;
        List<Entity> others = entity.world.getOtherEntities(entity, Box.from(entity.getPos()).expand(radius));

        switch(comparison) {
            case EQUAL:
            case LESS_THAN_OR_EQUAL:
            case GREATER_THAN:
                stopAt = compareTo + 1;
                break;

            case LESS_THAN:
            case GREATER_THAN_OR_EQUAL:
                stopAt = compareTo;
                break;

            case NONE:
            case NOT_EQUAL:
                break;
        }

        if (includeSelf && entityCondition.test(entity)) {
            ++count;
            if (count == stopAt) return comparison.compare(count, compareTo);
        }

        for (Entity other : others) {
            if (entityCondition.test(other)) {
                ++count;
                if (count == stopAt) break;
            }
        }

        return comparison.compare(count, compareTo);
    }

    public static ConditionFactory<Entity> getFactory () {
        return new ConditionFactory<>(Powers.identifier("entity_in_radius"),
            new SerializableData()
                .add(Powers.ENTITY_CONDITION, ApoliDataTypes.ENTITY_CONDITION)
                .add(Powers.RADIUS, SerializableDataTypes.DOUBLE)
                .add(INCLUDE_SELF, SerializableDataTypes.BOOLEAN, true)
                .add(Powers.COMPARISON, ApoliDataTypes.COMPARISON, Comparison.GREATER_THAN_OR_EQUAL)
                .add(Powers.COMPARE_TO, SerializableDataTypes.INT, 1),
            EntityInRadiusCondition::condition);
    }
}
