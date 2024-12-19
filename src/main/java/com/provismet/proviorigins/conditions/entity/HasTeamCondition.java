package com.provismet.proviorigins.conditions.entity;

import com.provismet.proviorigins.registries.POEntityConditionTypes;

import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.EntityConditionType;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class HasTeamCondition extends EntityConditionType {
    @Override
    public boolean test (Entity entity) {
        return entity.getScoreboardTeam() != null;
    }

    @Override
    public @NotNull ConditionConfiguration<HasTeamCondition> getConfig () {
        return POEntityConditionTypes.HAS_TEAM;
    }
}
