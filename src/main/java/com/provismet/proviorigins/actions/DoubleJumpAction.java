package com.provismet.proviorigins.actions;

import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public class DoubleJumpAction {
    public static void action (SerializableData.Instance data, Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)entity;
            Vec3d currentVelocity = living.getVelocity();
            living.setVelocity(currentVelocity.x, data.getDouble("height"), currentVelocity.z);
            living.velocityModified = true;
        }
    }

    public static ActionFactory<Entity> createActionFactory () {
        return new ActionFactory<>(Powers.identifier("double_jump"),
            new SerializableData()
            .add("height", SerializableDataTypes.DOUBLE, Double.valueOf(2)),
            DoubleJumpAction::action
        );
    }
}
