package com.provismet.proviorigins.conditions.entity;

import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;

public class VelocityYCondition {
    public static boolean condition (SerializableData.Instance data, Entity entity) {
        final double compareTo = data.getDouble(Powers.COMPARE_TO);
        Comparison comparison = data.get(Powers.COMPARISON);
        final double velocity = entity.getVelocity().getY();

        return comparison.compare(velocity, compareTo);
    }

    public static ConditionFactory<Entity> getFactory () {
        return new ConditionFactory<>(Powers.identifier("velocity_y"),
            new SerializableData()
                .add(Powers.COMPARISON, ApoliDataTypes.COMPARISON)
                .add(Powers.COMPARE_TO, SerializableDataTypes.DOUBLE),
            VelocityYCondition::condition
        );
    }
}
