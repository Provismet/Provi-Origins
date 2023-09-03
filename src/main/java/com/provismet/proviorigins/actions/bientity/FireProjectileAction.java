package com.provismet.proviorigins.actions.bientity;

import java.util.Optional;
import java.util.function.Consumer;

import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.util.MiscUtil;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3d;

public class FireProjectileAction {
    private static final String SPEED_LABEL = "speed";
    private static final String ENTITY_LABEL = "entity_type";
    private static final String NBT_LABEL = "tag";
    private static final String DIVERGENCE_LABEL = "divergence";

    public static void action(SerializableData.Instance data, Pair<Entity,Entity> pair) {
        if (pair.getLeft().getWorld().isClient()) return;

        final Entity actor = pair.getLeft();
        final Entity target = pair.getRight();
        final ServerWorld serverWorld = (ServerWorld)actor.getWorld();

        final int count = data.getInt(Powers.COUNT);
        final float speed = data.getFloat(SPEED_LABEL);
        final float divergence = data.getFloat(DIVERGENCE_LABEL);
        final EntityType<?> entityType = data.get(ENTITY_LABEL);
        final NbtCompound entityNbt = data.get(NBT_LABEL);

        final double dx = target.getX() - actor.getX();
        final double dy = target.getEyeY() - actor.getEyeY();
        final double dz = target.getZ() - actor.getZ();

        Vec3d projectileDirection = (new Vec3d(dx, dy, dz)).normalize();

        for (int i = 0; i < count; i++) {
            Optional<Entity> opt$entityToSpawn = MiscUtil.getEntityWithPassengers(
                serverWorld,
                entityType,
                entityNbt,
                actor.getPos().add(0, actor.getEyeHeight(actor.getPose()), 0),
                actor.getYaw(),
                actor.getPitch()
            );
            if (opt$entityToSpawn.isEmpty()) return;

            Entity entityToSpawn = opt$entityToSpawn.get();

            if (entityToSpawn instanceof ProjectileEntity projectileToSpawn) {
                if (projectileToSpawn instanceof ExplosiveProjectileEntity explosiveProjectileToSpawn) {
                    explosiveProjectileToSpawn.powerX = projectileDirection.x * speed;
                    explosiveProjectileToSpawn.powerY = projectileDirection.y * speed;
                    explosiveProjectileToSpawn.powerZ = projectileDirection.z * speed;
                }

                projectileToSpawn.setOwner(actor);
                projectileToSpawn.setVelocity(projectileDirection.x, projectileDirection.y, projectileDirection.z, speed, divergence);
            }
            else {
                Vec3d velocity = projectileDirection.multiply((double)speed);
                entityToSpawn.setVelocity(velocity);
            }
            
            serverWorld.spawnNewEntityAndPassengers(entityToSpawn);
            data.<Consumer<Entity>>ifPresent(Powers.PROJECTILE_ACTION, projectileAction -> projectileAction.accept(entityToSpawn));
        }
    }

    public static ActionFactory<Pair<Entity,Entity>> createBientityActionFactory () {
        return new ActionFactory<>(Powers.identifier("fire_projectile"),
            new SerializableData()
                .add(ENTITY_LABEL, SerializableDataTypes.ENTITY_TYPE)
                .add(DIVERGENCE_LABEL, SerializableDataTypes.FLOAT, 1F)
                .add(SPEED_LABEL, SerializableDataTypes.FLOAT, 1.5F)
                .add(Powers.COUNT, SerializableDataTypes.INT, 1)
                .add(NBT_LABEL, SerializableDataTypes.NBT, null)
                .add(Powers.PROJECTILE_ACTION, ApoliDataTypes.ENTITY_ACTION, null),
            FireProjectileAction::action
        );
    }

}
