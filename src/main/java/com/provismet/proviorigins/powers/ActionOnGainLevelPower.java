package com.provismet.proviorigins.powers;

import java.util.function.Consumer;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class ActionOnGainLevelPower extends Power {
    private final Consumer<Entity> entityAction;
    private final Comparison comparison;
    private final int compareTo;

    public ActionOnGainLevelPower (PowerType<?> type, LivingEntity entity, Consumer<Entity> entityAction, Comparison comparison, int compareTo) {
        super(type, entity);
        this.entityAction = entityAction;
        this.comparison = comparison;
        this.compareTo = compareTo;
    }
    
    public void execute (int experienceAmount) {
        if (this.comparison.compare(experienceAmount, compareTo)) {
            this.entityAction.accept(this.entity);
        }
    }

    @SuppressWarnings("rawtypes")
    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("action_on_gain_level"),
            new SerializableData()
                .add(Powers.ENTITY_ACTION, ApoliDataTypes.ENTITY_ACTION)
                .add(Powers.COMPARISON, ApoliDataTypes.COMPARISON, Comparison.GREATER_THAN_OR_EQUAL)
                .add(Powers.COMPARE_TO, SerializableDataTypes.INT, 1),
            data -> (type, player) -> new ActionOnGainLevelPower(type, player,
                data.get(Powers.ENTITY_ACTION),
                data.get(Powers.COMPARISON),
                data.getInt(Powers.COMPARE_TO)
            )
        ).allowCondition();
    }
}
