package com.provismet.proviorigins.conditions.bientity;

import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.util.Pair;

public class TeammateCondition {
    public static boolean condition (SerializableData.Instance data, Pair<Entity,Entity> pair) {
        AbstractTeam actorTeam = pair.getLeft().getScoreboardTeam();
        AbstractTeam targetTeam = pair.getRight().getScoreboardTeam();

        if (actorTeam == null || targetTeam == null) return false;
        else return actorTeam.isEqual(targetTeam);
    }

    public static ConditionFactory<Pair<Entity,Entity>> getFactory () {
        return new ConditionFactory<>(Powers.identifier("teammate"), new SerializableData(), TeammateCondition::condition);
    }
}
