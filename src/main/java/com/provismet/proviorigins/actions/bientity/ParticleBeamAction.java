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

    public static void action (SerializableData.Instance data, Pair<Entity, Entity> bientity) {
        if (bientity.getLeft().getWorld().isClient()) return;

        final Entity actor = bientity.getLeft();
        final Entity target = bientity.getRight();

        ServerWorld sWorld = (ServerWorld)actor.getWorld();

        final double step = data.getDouble(STEP_LABEL);
        final ParticleEffect particle = data.get(Powers.PARTICLE);
        final double speed = data.getDouble(SPEED_LABEL);
        final int count = data.getInt(Powers.COUNT);

        Vec3d currentPos = actor.getPos();
        final double maxDistance = actor.getPos().distanceTo(target.getPos());

        Vec3d eachStep = (new Vec3d(target.getX() - actor.getX(), target.getEyeY() - actor.getEyeY(), target.getZ() - actor.getZ())).normalize().multiply(step);
        
        while (currentPos.distanceTo(actor.getPos()) < maxDistance) {
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
                .add(Powers.COUNT, SerializableDataTypes.INT, 1),
            ParticleBeamAction::action);
    }
}
