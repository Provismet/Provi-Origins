package com.provismet.proviorigins.conditions.block;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.registry.Registry;

public class BlockConditionFactories {
    public static void register () {
        register(CollisionCondition.getFactory());
    }

    private static void register (ConditionFactory<CachedBlockPosition> factory) {
        Registry.register(ApoliRegistries.BLOCK_CONDITION, factory.getSerializerId(), factory);
    }
}
