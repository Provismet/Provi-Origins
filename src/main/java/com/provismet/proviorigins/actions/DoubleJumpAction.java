package com.provismet.proviorigins.actions;

import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class DoubleJumpAction {
    private final static String HEIGHT_LABEL = "velocity";
    private static final String FALL_LABEL = "reset_fall_height";

    public static void action (SerializableData.Instance data, Entity entity) {
        if (entity.getWorld().isClient()) {
            if (!data.getBoolean(Powers.CLIENT)) return;
        }
        else if (!data.getBoolean(Powers.SERVER)) return;

        Vec3d currentVelocity = entity.getVelocity();
        entity.setVelocity(currentVelocity.x, data.getDouble(HEIGHT_LABEL), currentVelocity.z);
        entity.velocityModified = true;
        
        if (data.getBoolean(FALL_LABEL)) entity.fallDistance = 0;
    }

    public static ActionFactory<Entity> createActionFactory () {
        return new ActionFactory<>(Powers.identifier("double_jump"),
            new SerializableData()
            .add(HEIGHT_LABEL, SerializableDataTypes.DOUBLE)
            .add(Powers.CLIENT, SerializableDataTypes.BOOLEAN, true)
            .add(Powers.SERVER, SerializableDataTypes.BOOLEAN, true)
            .add(FALL_LABEL, SerializableDataTypes.BOOLEAN, true),
            DoubleJumpAction::action
        );
    }
}
