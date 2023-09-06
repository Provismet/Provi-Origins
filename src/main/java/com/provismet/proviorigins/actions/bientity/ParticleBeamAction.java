package com.provismet.proviorigins.actions.bientity;

import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3d;

public class ParticleBeamAction {
    private static final String STEP_LABEL = "step";
    private static final String SPEED_LABEL = "speed";
    private static final String ACTOR_OFFSET_LABEL = "actor_offset_y";
    private static final String TARGET_OFFSET_LABEL = "target_offset_y";

    public static void action (SerializableData.Instance data, Pair<Entity, Entity> bientity) {
        if (bientity.getLeft().getWorld().isClient()) return;

        final Entity actor = bientity.getLeft();
        final Entity target = bientity.getRight();

        ServerWorld sWorld = (ServerWorld)actor.getWorld();

        final double step = data.getDouble(STEP_LABEL);
        final ParticleEffect particle = data.get(Powers.PARTICLE);
        final double speed = data.getDouble(SPEED_LABEL);
        final int count = data.getInt(Powers.COUNT);

        final double actor_y = data.getDouble(ACTOR_OFFSET_LABEL);
        final double target_y = data.getDouble(TARGET_OFFSET_LABEL);

        Vec3d currentPos = actor.getPos().add(0, actor_y, 0);
        Vec3d destPos = target.getPos().add(0, target_y, 0);
        final double maxDistance = actor.getPos().distanceTo(target.getPos());

        Vec3d eachStep = destPos.subtract(currentPos).normalize().multiply(step);
        
        while (currentPos.distanceTo(destPos) < maxDistance) {
            sWorld.spawnParticles(particle, currentPos.x, currentPos.y, currentPos.z, count, 0.0, 0.0, 0.0, speed);
            currentPos = currentPos.add(eachStep);
        }
    }

    public static ActionFactory<Pair<Entity,Entity>> createBientityActionFactory () {
        return new ActionFactory<>(Powers.identifier("particle_beam"),
            new SerializableData()
                .add(STEP_LABEL, SerializableDataTypes.DOUBLE)
                .add(Powers.PARTICLE, SerializableDataTypes.PARTICLE_EFFECT_OR_TYPE)
                .add(SPEED_LABEL, SerializableDataTypes.DOUBLE, 0.0)
                .add(Powers.COUNT, SerializableDataTypes.INT, 1)
                .add(ACTOR_OFFSET_LABEL, SerializableDataTypes.DOUBLE, 0.0)
                .add(TARGET_OFFSET_LABEL, SerializableDataTypes.DOUBLE, 0.0),
            ParticleBeamAction::action);
    }
}
