package com.provismet.proviorigins.conditions.block;

import com.provismet.proviorigins.registries.POBlockConditionTypes;

import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.context.BlockConditionContext;
import io.github.apace100.apoli.condition.type.BlockConditionType;
import org.jetbrains.annotations.NotNull;

public class CollisionBiEntityConditionType extends BlockConditionType {
    @Override
    public boolean test (BlockConditionContext context) {
        return !context.blockState().getCollisionShape(context.world(), context.pos()).isEmpty();
    }

    @Override
    public @NotNull ConditionConfiguration<CollisionBiEntityConditionType> getConfig () {
        return POBlockConditionTypes.CAN_COLLIDE;
    }
}
