package com.provismet.proviorigins.conditions.entity;

import com.provismet.proviorigins.registries.POEntityConditionTypes;

import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.context.EntityConditionContext;
import io.github.apace100.apoli.condition.type.EntityConditionType;
import org.jetbrains.annotations.NotNull;

public class HasTeamEntityConditionType extends EntityConditionType {
    @Override
    public boolean test (EntityConditionContext context) {
        return context.entity().getScoreboardTeam() != null;
    }

    @Override
    public @NotNull ConditionConfiguration<HasTeamEntityConditionType> getConfig () {
        return POEntityConditionTypes.HAS_TEAM;
    }
}
