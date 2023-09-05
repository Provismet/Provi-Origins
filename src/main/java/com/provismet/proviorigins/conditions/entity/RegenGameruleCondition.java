package com.provismet.proviorigins.conditions.entity;

import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.world.GameRules;

public class RegenGameruleCondition {
    public static boolean condition (SerializableData.Instance data, Entity entity) {
        return entity.getWorld().getGameRules().get(GameRules.NATURAL_REGENERATION).get();
    }

    public static ConditionFactory<Entity> getFactory () {
        return new ConditionFactory<>(Powers.identifier("can_natural_regen"),
            new SerializableData(), 
            RegenGameruleCondition::condition);
    }
}
