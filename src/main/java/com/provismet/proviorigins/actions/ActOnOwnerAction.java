package com.provismet.proviorigins.actions;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Tameable;
import net.minecraft.util.Pair;

public class ActOnOwnerAction {
    public static void action (SerializableData.Instance data, Entity entity) {
        final Consumer<Pair<Entity, Entity>> bientityAction = data.get(Powers.BIENTITY_ACTION);
        final Predicate<Pair<Entity,Entity>> bientityCondition = data.get(Powers.BIENTITY_CONDITION);

        if (entity instanceof Tameable tameable && tameable.getOwner() != null) {
            Pair<Entity,Entity> pair = new Pair<Entity,Entity>(entity, tameable.getOwner());
            if (bientityCondition == null || bientityCondition.test(pair)) bientityAction.accept(pair);
        }
    }

    public static ActionFactory<Entity> createActionFactory () {
        return new ActionFactory<>(Powers.identifier("owner_bientity"),
            new SerializableData()
            .add(Powers.BIENTITY_ACTION, ApoliDataTypes.BIENTITY_ACTION)
            .add(Powers.BIENTITY_CONDITION, ApoliDataTypes.BIENTITY_CONDITION, null),
            ActOnOwnerAction::action
        );
    }
}
