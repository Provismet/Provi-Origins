package com.provismet.proviorigins.conditions.bientity;

import com.provismet.proviorigins.registries.POBientityConditionTypes;

import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.context.BiEntityConditionContext;
import io.github.apace100.apoli.condition.type.BiEntityConditionType;
import net.minecraft.scoreboard.AbstractTeam;
import org.jetbrains.annotations.NotNull;

public class TeammateBiEntityConditionType extends BiEntityConditionType {
    @Override
    public boolean test (BiEntityConditionContext context) {
        AbstractTeam actorTeam = context.actor().getScoreboardTeam();
        AbstractTeam targetTeam = context.target().getScoreboardTeam();

        if (actorTeam == null || targetTeam == null) return false;
        else return actorTeam.isEqual(targetTeam);
    }

    @Override
    public @NotNull ConditionConfiguration<TeammateBiEntityConditionType> getConfig () {
        return POBientityConditionTypes.TEAMMATE;
    }
}
