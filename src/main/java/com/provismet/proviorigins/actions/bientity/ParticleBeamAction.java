package com.provismet.proviorigins.actions.bientity;

import com.provismet.proviorigins.registries.POBientityActionTypes;
import com.provismet.proviorigins.utility.constants.FieldNames;

import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.context.BiEntityActionContext;
import io.github.apace100.apoli.action.type.BiEntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class ParticleBeamAction extends BiEntityActionType {
    private static final String STEP_LABEL = "step";
    private static final String SPEED_LABEL = "speed";
    private static final String ACTOR_OFFSET_LABEL = "actor_offset_y";
    private static final String TARGET_OFFSET_LABEL = "target_offset_y";

    private final double step;
    private final ParticleEffect particleEffect;
    private final double speed;
    private final int count;
    private final double actorY;
    private final double targetY;

    public static final TypedDataObjectFactory<ParticleBeamAction> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(STEP_LABEL, SerializableDataTypes.DOUBLE)
            .add(FieldNames.PARTICLE, SerializableDataTypes.PARTICLE_EFFECT_OR_TYPE)
            .add(SPEED_LABEL, SerializableDataTypes.DOUBLE, 0.0)
            .add(FieldNames.COUNT, SerializableDataTypes.INT, 1)
            .add(ACTOR_OFFSET_LABEL, SerializableDataTypes.DOUBLE, 0.0)
            .add(TARGET_OFFSET_LABEL, SerializableDataTypes.DOUBLE, 0.0),
        data -> new ParticleBeamAction(
            data.getDouble(FieldNames.STEP),
            data.get(FieldNames.PARTICLE),
            data.getDouble(FieldNames.SPEED),
            data.getInt(FieldNames.COUNT),
            data.getDouble(ACTOR_OFFSET_LABEL),
            data.getDouble(TARGET_OFFSET_LABEL)
        ),
        (actionType, data) -> data.instance()
            .set(FieldNames.STEP, actionType.step)
            .set(FieldNames.PARTICLE, actionType.particleEffect)
            .set(FieldNames.SPEED, actionType.speed)
            .set(FieldNames.COUNT, actionType.count)
            .set(ACTOR_OFFSET_LABEL, actionType.actorY)
            .set(TARGET_OFFSET_LABEL, actionType.targetY)
    );

    public ParticleBeamAction (double step, ParticleEffect particleEffect, double speed, int count, double actorY, double targetY) {
        this.step = step;
        this.particleEffect = particleEffect;
        this.speed = speed;
        this.count = count;
        this.actorY = actorY;
        this.targetY = targetY;
    }

    @Override
    public void accept (BiEntityActionContext context) {
        if (context.actor().getWorld().isClient()) return;
        ServerWorld sWorld = (ServerWorld)context.actor().getWorld();

        Vec3d startPos = context.actor().getPos().add(0.0, this.actorY, 0.0);
        Vec3d currentPos = context.actor().getPos().add(0.0, this.actorY, 0.0);
        Vec3d destPos = context.target().getPos().add(0.0, this.targetY, 0.0);
        final double maxDistance = startPos.distanceTo(destPos);

        Vec3d eachStep = destPos.subtract(currentPos).normalize().multiply(this.step);
        
        while (currentPos.distanceTo(startPos) <= maxDistance) {
            sWorld.spawnParticles(this.particleEffect, currentPos.x, currentPos.y, currentPos.z, this.count, 0.0, 0.0, 0.0, this.speed);
            currentPos = currentPos.add(eachStep);
        }
    }

    @Override
    public @NotNull ActionConfiguration<ParticleBeamAction> getConfig () {
        return POBientityActionTypes.PARTICLE_BEAM;
    }
}
