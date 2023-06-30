package com.provismet.proviorigins.conditions.entity;

import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;

public class ClientServerCondition {
    public static boolean condition (SerializableData.Instance data, Entity entity) {
        if (entity.getWorld().isClient() && data.getBoolean(Powers.CLIENT)) return true;
        if (!entity.getWorld().isClient() && data.getBoolean(Powers.SERVER)) return true;
        return false;
    }

    public static ConditionFactory<Entity> getFactory () {
        return new ConditionFactory<>(Powers.identifier("client_server"),
            new SerializableData()
                .add(Powers.SERVER, SerializableDataTypes.BOOLEAN, true)
                .add(Powers.CLIENT, SerializableDataTypes.BOOLEAN, true),
            ClientServerCondition::condition);
    }
}
