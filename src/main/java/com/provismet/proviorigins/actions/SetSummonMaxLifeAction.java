package com.provismet.proviorigins.actions;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.extras.Temporary;
import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;

public class SetSummonMaxLifeAction {
    public static void action (SerializableData.Instance data, Entity entity) {
        if (entity instanceof Temporary summonable) {
            final int newMaxLife = data.getInt(Powers.AMOUNT);
            summonable.setMaxLifetime(newMaxLife);
        }
        else {
            ProviOriginsMain.LOGGER.warn("Attempted to use set_summon_max_life_ticks action on a non-temporary entity.");
        }
    }

    public static ActionFactory<Entity> createActionFactory () {
        return new ActionFactory<>(Powers.identifier("set_summon_max_life_ticks"),
            new SerializableData()
                .add(Powers.AMOUNT, SerializableDataTypes.INT),
            SetSummonMaxLifeAction::action);
    }
}
