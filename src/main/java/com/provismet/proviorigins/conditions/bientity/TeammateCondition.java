package com.provismet.proviorigins.conditions.bientity;

import com.provismet.proviorigins.registries.POBientityConditionTypes;

import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.BiEntityConditionType;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.AbstractTeam;
import org.jetbrains.annotations.NotNull;

public class TeammateCondition extends BiEntityConditionType {
    @Override
    public boolean test (Entity actor, Entity target) {
        AbstractTeam actorTeam = actor.getScoreboardTeam();
        AbstractTeam targetTeam = target.getScoreboardTeam();

        if (actorTeam == null || targetTeam == null) return false;
        else return actorTeam.isEqual(targetTeam);
    }

    @Override
    public @NotNull ConditionConfiguration<TeammateCondition> getConfig () {
        return POBientityConditionTypes.TEAMMATE;
    }
}
