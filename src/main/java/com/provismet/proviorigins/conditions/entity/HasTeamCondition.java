package com.provismet.proviorigins.conditions.entity;

import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;

public class HasTeamCondition {
    public static boolean condition (SerializableData.Instance data, Entity entity) {
        return !(entity.getScoreboardTeam() == null);
    }

    public static ConditionFactory<Entity> getFactory () {
        return new ConditionFactory<>(Powers.identifier("has_team"), new SerializableData(), HasTeamCondition::condition);
    }
}
