package com.provismet.proviorigins.conditions.block;

import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.block.pattern.CachedBlockPosition;

public class CollisionCondition {
    public static boolean condition (SerializableData.Instance data, CachedBlockPosition block) {
        return !block.getBlockState().getCollisionShape(block.getWorld(), block.getBlockPos()).isEmpty();
    }

    public static ConditionFactory<CachedBlockPosition> getFactory () {
        return new ConditionFactory<>(Powers.identifier("can_collide"),
            new SerializableData(),
            CollisionCondition::condition
        );
    }
}
