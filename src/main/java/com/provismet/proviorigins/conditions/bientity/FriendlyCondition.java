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
            if (actor == target) return true;
            if (actor instanceof Tameable tameable && tameable.getOwner() != null) {
                if (tameable.getOwner() == target) return true; // One owns the other.
                if (target instanceof Tameable tameable2 && tameable2.getOwner() != null) { // These next two are symmetrical conditions.
                    if (tameable.getOwner() == tameable2.getOwner()) return true; // Same owner.
                    if (FriendlyCondition.condition(data, new Pair<>(tameable.getOwner(), tameable2.getOwner()))) return true; // Owners are friendly to each other.
                }
                if (target == tameable.getOwner().getAttacker() || target == tameable.getOwner().getAttacking()) return false; // Help owner.
            }
            if (target instanceof Tameable tameable && tameable.getOwner() != null) {
                if (tameable.getOwner() == actor) return true;
                if (actor == tameable.getOwner().getAttacker() || actor == tameable.getOwner().getAttacking()) return false; // Help owner.
            }
            if (actor.getAttacker() == target || actor.getAttacking() == target || target.getAttacker() == actor || target.getAttacking() == actor) return false; // In combat with each other.
            if (TeammateCondition.condition(data, pair)) return true;
            if ((actor instanceof HostileEntity) != (target instanceof HostileEntity)) return false; // Only one is hostile.
            if (actor.getScoreboardTeam() == null && target.getScoreboardTeam() == null) return true; // Neither is on a team.
        }
        return false;
    }

    public static ConditionFactory<Pair<Entity,Entity>> getFactory () {
        return new ConditionFactory<>(Powers.identifier("friendly"), new SerializableData(), FriendlyCondition::condition);
    }
}
