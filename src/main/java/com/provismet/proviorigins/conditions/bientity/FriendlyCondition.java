package com.provismet.proviorigins.conditions.bientity;

import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.Pair;

public class FriendlyCondition {
    public static boolean condition (SerializableData.Instance data, Pair<Entity,Entity> pair) {
        if (pair.getLeft() instanceof LivingEntity actor && pair.getRight() instanceof LivingEntity target) {
            if (actor.getAttacker() == target || actor.getAttacking() == target || target.getAttacker() == actor || target.getAttacking() == actor) return false;
            if (TeammateCondition.condition(data, pair)) return true;
            if (actor instanceof Tameable tameable && tameable.getOwner() == target) return true;
            if (target instanceof Tameable tameable && tameable.getOwner() == actor) return true;
            if ((actor instanceof HostileEntity) != (target instanceof HostileEntity)) return false;
            if (actor.getScoreboardTeam() == null && target.getScoreboardTeam() == null) return true;
        }
        return false;
    }

    public static ConditionFactory<Pair<Entity,Entity>> getFactory () {
        return new ConditionFactory<>(Powers.identifier("friendly"), new SerializableData(), FriendlyCondition::condition);
    }
}
