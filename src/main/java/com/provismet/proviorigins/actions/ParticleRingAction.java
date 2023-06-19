package com.provismet.proviorigins.actions;

import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;

public class ParticleRingAction {
    private static final String PARTICLE_LABEL = "particle";
    private static final String STEP_LABEL = "step_size";
    private static final String COUNT_LABEL = "count";
    private static final String DY_LABEL = "offset_y";
    private static final String SPEED_LABEL = "speed";

    public static void action (SerializableData.Instance data, Entity entity) {
        if (entity.world.isClient) return;
        
        final float radius = data.getFloat(Powers.RADIUS);
        final float step = data.getFloat(STEP_LABEL) / MathHelper.DEGREES_PER_RADIAN;
        final int count = data.getInt(COUNT_LABEL);
        final double dy = data.getDouble(DY_LABEL);
        final double speed = data.getDouble(SPEED_LABEL);
        final ParticleEffect particles = data.get(PARTICLE_LABEL);

        ServerWorld sWorld = (ServerWorld)entity.world;
        for (float angle = 0; angle < 2 * MathHelper.PI; angle += step) {
            double x = entity.getX() + (radius * MathHelper.sin(angle));
            double y = entity.getY() + dy;
            double z = entity.getZ() + (radius * MathHelper.cos(angle));
            sWorld.spawnParticles(particles, x, y, z, count, 0.0, 0.0, 0.0, speed);
        }
    }

    public static ActionFactory<Entity> createActionFactory () {
        return new ActionFactory<>(Powers.identifier("particle_ring"),
            new SerializableData()
                .add(Powers.RADIUS, SerializableDataTypes.FLOAT)
                .add(STEP_LABEL, SerializableDataTypes.FLOAT)
                .add(COUNT_LABEL, SerializableDataTypes.INT, 1)
                .add(DY_LABEL, SerializableDataTypes.DOUBLE, 0.25)
                .add(SPEED_LABEL, SerializableDataTypes.DOUBLE, 0.0)
                .add(PARTICLE_LABEL, SerializableDataTypes.PARTICLE_EFFECT_OR_TYPE),
            ParticleRingAction::action);
    }
}
