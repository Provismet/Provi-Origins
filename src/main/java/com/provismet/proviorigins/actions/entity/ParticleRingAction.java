package com.provismet.proviorigins.actions.entity;

import com.provismet.proviorigins.registries.POEntityActionTypes;
import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.type.EntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import com.provismet.proviorigins.utility.constants.FieldNames;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.util.Space;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ParticleRingAction extends EntityActionType {
    private static final String PARTICLE_LABEL = "particle";
    private static final String STEP_LABEL = "step_size";
    private static final String COUNT_LABEL = "count";
    private static final String DX_LABEL = "offset_x";
    private static final String DY_LABEL = "offset_y";
    private static final String DZ_LABEL = "offset_z";
    private static final String SPACE_LABEL = "space";
    private static final String SPEED_LABEL = "speed";
    private static final String GROUND_LABEL = "on_ground";

    private final float radius;
    private final float step;
    private final int count;
    private final Space space;
    private final double speed;
    private final boolean onGround;
    private final ParticleEffect particles;
    private final Vector3f offset;

    public static final TypedDataObjectFactory<ParticleRingAction> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(FieldNames.RADIUS, SerializableDataTypes.FLOAT)
            .add(STEP_LABEL, SerializableDataTypes.FLOAT)
            .add(COUNT_LABEL, SerializableDataTypes.INT, 1)
            .add(DX_LABEL, SerializableDataTypes.FLOAT, 0.0f)
            .add(DY_LABEL, SerializableDataTypes.FLOAT, 0.25f)
            .add(DZ_LABEL, SerializableDataTypes.FLOAT, 0.0f)
            .add(SPACE_LABEL, ApoliDataTypes.SPACE, Space.LOCAL)
            .add(SPEED_LABEL, SerializableDataTypes.DOUBLE, 0.0)
            .add(GROUND_LABEL, SerializableDataTypes.BOOLEAN, true)
            .add(PARTICLE_LABEL, SerializableDataTypes.PARTICLE_EFFECT_OR_TYPE),
        data -> new ParticleRingAction(
            data.getFloat(FieldNames.RADIUS),
            data.getFloat(STEP_LABEL),
            data.getInt(COUNT_LABEL),
            data.get(SPACE_LABEL),
            data.getDouble(SPEED_LABEL),
            data.getBoolean(GROUND_LABEL),
            data.get(PARTICLE_LABEL),
            new Vector3f(
                data.getFloat(DX_LABEL),
                data.getFloat(DY_LABEL),
                data.getFloat(DZ_LABEL)
            )
        ),
        (actionType, data) -> data.instance()
            .set(FieldNames.RADIUS, actionType.radius)
            .set(STEP_LABEL, actionType.step)
            .set(COUNT_LABEL, actionType.count)
            .set(DX_LABEL, actionType.offset.x)
            .set(DY_LABEL, actionType.offset.y)
            .set(DZ_LABEL, actionType.offset.z)
            .set(SPACE_LABEL, actionType.space)
            .set(SPEED_LABEL, actionType.speed)
            .set(GROUND_LABEL, actionType.onGround)
            .set(PARTICLE_LABEL, actionType.particles)
    );

    public ParticleRingAction (float radius, float step, int count, Space space, double speed, boolean onGround, ParticleEffect particles, Vector3f offset) {
        this.radius = radius;
        this.step = step;
        this.count = count;
        this.space = space;
        this.speed = speed;
        this.onGround = onGround;
        this.particles = particles;
        this.offset = offset;
    }

    @Override
    protected void execute (Entity entity) {
        Vector3f offsetCopy = new Vector3f(this.offset.x, this.offset.y, this.offset.z);
        this.space.toGlobal(offsetCopy, entity);
        Vec3d initial = entity.getPos();
        initial = initial.add(offsetCopy.x, offsetCopy.y, offsetCopy.z);

        final float pitchRadians = (MathHelper.PI / 2) - (entity.getPitch() / MathHelper.DEGREES_PER_RADIAN);
        final float yawRadians = -entity.getHeadYaw() / MathHelper.DEGREES_PER_RADIAN;

        ServerWorld sWorld = (ServerWorld)entity.getWorld();
        for (float angle = 0; angle < 2 * MathHelper.PI; angle += this.step) {
            Vec3d point = initial.add(this.radius * MathHelper.sin(angle), 0.0, this.radius * MathHelper.cos(angle));

            if (!onGround) {
                point = point.subtract(initial);
                point = point.rotateX(pitchRadians);
                point = point.rotateY(yawRadians);
                point = point.add(initial);
            }

            sWorld.spawnParticles(this.particles, point.x, point.y, point.z, this.count, 0.0, 0.0, 0.0, this.speed);
        }
    }

    @Override
    public @NotNull ActionConfiguration<ParticleRingAction> getConfig () {
        return POEntityActionTypes.PARTICLE_RING;
    }
}
