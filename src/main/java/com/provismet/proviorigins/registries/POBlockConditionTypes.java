package com.provismet.proviorigins.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.conditions.block.CollisionCondition;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.BlockConditionType;
import io.github.apace100.apoli.condition.type.BlockConditionTypes;

import java.util.function.Supplier;

public abstract class POBlockConditionTypes {
    public static final ConditionConfiguration<CollisionCondition> CAN_COLLIDE = register("can_collide", CollisionCondition::new);

    private static <T extends BlockConditionType> ConditionConfiguration<T> register (String name, Supplier<T> constructor) {
        return BlockConditionTypes.register(ConditionConfiguration.simple(ProviOriginsMain.identifier(name), constructor));
    }

    public static void init () {}
}
