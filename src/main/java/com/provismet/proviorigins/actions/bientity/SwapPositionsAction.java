package com.provismet.proviorigins.actions.bientity;

import com.provismet.proviorigins.ProviOriginsMain;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3d;

public class SwapPositionsAction {
    private static final String RESET_FALL_LABEL = "reset_fall_height";
    private static final String RESET_VELOCITY_LABEL = "reset_velocity";

    public static void action (SerializableData.Instance data, Pair<Entity, Entity> bientity) {
        final boolean resetFall = data.getBoolean(RESET_FALL_LABEL);
        final boolean resetVelocity = data.getBoolean(RESET_VELOCITY_LABEL);

        if (bientity.getLeft() instanceof LivingEntity actor && bientity.getRight() instanceof LivingEntity target) {
            if (resetFall) {
                actor.fallDistance = 0;
                target.fallDistance = 0;
            }

            if (resetVelocity) {
                actor.setVelocity(Vec3d.ZERO);
                target.setVelocity(Vec3d.ZERO);
            }

            Vec3d temp = actor.getPos();
            actor.setPosition(target.getPos());
            target.setPosition(temp);
        }
    }

    public static ActionFactory<Pair<Entity,Entity>> createBientityActionFactory () {
        return new ActionFactory<>(ProviOriginsMain.identifier("swap_positions"),
            new SerializableData()
                .add(RESET_FALL_LABEL, SerializableDataTypes.BOOLEAN, true)
                .add(RESET_VELOCITY_LABEL, SerializableDataTypes.BOOLEAN),
            SwapPositionsAction::action);
    }
}
