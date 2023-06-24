package com.provismet.proviorigins.actions;

import org.joml.Vector3f;

import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.util.Space;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ParticleRingAction {
    private static final String PARTICLE_LABEL = "particle";
    private static final String STEP_LABEL = "step_size";
    private static final String COUNT_LABEL = "count";
    private static final String DX_LABEL = "offset_x";
    private static final String DY_LABEL = "offset_y";
    private static final String DZ_LABEL = "offset_z";
    private static final String SPACE_LABEL = "space";
    private static final String SPEED_LABEL = "speed";
    private static final String GROUND_LABEL = "on_ground";

    public static void action (SerializableData.Instance data, Entity entity) {
        if (entity.world.isClient) return;
        
        final float radius = data.getFloat(Powers.RADIUS);
        final float step = data.getFloat(STEP_LABEL) / MathHelper.DEGREES_PER_RADIAN;
        final int count = data.getInt(COUNT_LABEL);
        final Space space = data.get(SPACE_LABEL);
        final double speed = data.getDouble(SPEED_LABEL);
        final boolean onGround = data.getBoolean(GROUND_LABEL);
        final ParticleEffect particles = data.get(PARTICLE_LABEL);

        Vector3f offsets = new Vector3f(data.getFloat(DX_LABEL), data.getFloat(DY_LABEL), data.getFloat(DZ_LABEL));
        space.toGlobal(offsets, entity);
        Vec3d initial = entity.getPos();
        initial = initial.add(offsets.x, offsets.y, offsets.z);

        final float pitchRadians = (MathHelper.PI / 2) - (entity.getPitch() / MathHelper.DEGREES_PER_RADIAN);
        final float yawRadians = -entity.getHeadYaw() / MathHelper.DEGREES_PER_RADIAN;

        ServerWorld sWorld = (ServerWorld)entity.world;
        for (float angle = 0; angle < 2 * MathHelper.PI; angle += step) {
            Vec3d point = initial.add(radius * MathHelper.sin(angle), 0.0, radius * MathHelper.cos(angle));

            if (!onGround) {
                point = point.subtract(initial);
                point = point.rotateX(pitchRadians);
                point = point.rotateY(yawRadians);
                point = point.add(initial);
            }

            sWorld.spawnParticles(particles, point.x, point.y, point.z, count, 0.0, 0.0, 0.0, speed);
        }
    }

    public static ActionFactory<Entity> createActionFactory () {
        return new ActionFactory<>(Powers.identifier("particle_ring"),
            new SerializableData()
                .add(Powers.RADIUS, SerializableDataTypes.FLOAT)
                .add(STEP_LABEL, SerializableDataTypes.FLOAT)
                .add(COUNT_LABEL, SerializableDataTypes.INT, 1)
                .add(DX_LABEL, SerializableDataTypes.FLOAT, 0.0f)
                .add(DY_LABEL, SerializableDataTypes.FLOAT, 0.25f)
                .add(DZ_LABEL, SerializableDataTypes.FLOAT, 0.0f)
                .add(SPACE_LABEL, ApoliDataTypes.SPACE, Space.LOCAL)
                .add(SPEED_LABEL, SerializableDataTypes.DOUBLE, 0.0)
                .add(GROUND_LABEL, SerializableDataTypes.BOOLEAN, true)
                .add(PARTICLE_LABEL, SerializableDataTypes.PARTICLE_EFFECT_OR_TYPE),
            ParticleRingAction::action);
    }
}
