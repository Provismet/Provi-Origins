package com.provismet.proviorigins.actions;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.util.Pair;

public class ActOnClosestEntityAction {
    public static void action (SerializableData.Instance data, Entity entity) {
        final double distance = data.getDouble(Powers.DISTANCE);
        final Consumer<Pair<Entity, Entity>> bientityAction = data.get(Powers.BIENTITY_ACTION);
        final Predicate<Pair<Entity,Entity>> bientityCondition = data.get(Powers.BIENTITY_CONDITION);

        List<Entity> others = entity.world.getOtherEntities(entity, entity.getBoundingBox().expand(distance));
        Entity closest = null;

        for (Entity other : others) {
            if (bientityCondition.test(new Pair<>(entity, other)) && (closest == null || entity.distanceTo(other) < entity.distanceTo(closest))) closest = other;
        }

        if (closest != null) bientityAction.accept(new Pair<>(entity, closest));
    }

    public static ActionFactory<Entity> createActionFactory () {
        return new ActionFactory<>(Powers.identifier("closest_bientity"),
            new SerializableData()
            .add(Powers.DISTANCE, SerializableDataTypes.DOUBLE)
            .add(Powers.BIENTITY_ACTION, ApoliDataTypes.BIENTITY_ACTION)
            .add(Powers.BIENTITY_CONDITION, ApoliDataTypes.BIENTITY_CONDITION),
            ActOnClosestEntityAction::action
        );
    }
}
