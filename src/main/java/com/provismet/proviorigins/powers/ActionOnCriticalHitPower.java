package com.provismet.proviorigins.powers;

import java.util.function.Consumer;
import java.util.function.Predicate;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Pair;

@SuppressWarnings("rawtypes")
public class ActionOnCriticalHitPower extends Power {
    private final Consumer<Pair<Entity,Entity>> bientityAction;
    private final Predicate<Pair<Entity,Entity>> bientityCondition;

    public ActionOnCriticalHitPower(PowerType<?> type, LivingEntity entity, Consumer<Pair<Entity,Entity>> bientityAction, Predicate<Pair<Entity,Entity>> bientityCondition) {
        super(type, entity);
        this.bientityAction = bientityAction;
        this.bientityCondition = bientityCondition;
    }

    public void tryAction (Entity target) {
        if (bientityCondition == null || bientityCondition.test(new Pair<Entity,Entity>(this.entity, target))) {
            bientityAction.accept(new Pair<Entity,Entity>(this.entity, target));
        }
    }

    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("action_on_critical_hit"),
            new SerializableData()
                .add(Powers.BIENTITY_ACTION, ApoliDataTypes.BIENTITY_ACTION)
                .add(Powers.BIENTITY_CONDITION, ApoliDataTypes.BIENTITY_CONDITION, null),
                data -> (type, player) -> new ActionOnCriticalHitPower(type, player,
                    data.get(Powers.BIENTITY_ACTION),
                    data.get(Powers.BIENTITY_CONDITION)))
                .allowCondition();
    }
}
