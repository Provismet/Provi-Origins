package com.provismet.proviorigins.actions.bientity;

import java.util.Optional;

import com.provismet.proviorigins.registries.POBientityActionTypes;
import com.provismet.proviorigins.utility.constants.FieldNames;

import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.EntityAction;
import io.github.apace100.apoli.action.context.BiEntityActionContext;
import io.github.apace100.apoli.action.type.BiEntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.util.MiscUtil;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class FireProjectileAction extends BiEntityActionType {
    private static final String SPEED_LABEL = "speed";
    private static final String ENTITY_LABEL = "entity_type";
    private static final String NBT_LABEL = "tag";
    private static final String DIVERGENCE_LABEL = "divergence";

    private final int count;
    private final float speed;
    private final float divergence;
    private final EntityType<?> entityType;
    private final NbtCompound entityNbt;
    private final Optional<EntityAction> postSummonAction;

    public static final TypedDataObjectFactory<FireProjectileAction> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(FieldNames.COUNT, SerializableDataTypes.INT, 1)
            .add(SPEED_LABEL, SerializableDataTypes.FLOAT, 1.5f)
            .add(DIVERGENCE_LABEL, SerializableDataTypes.FLOAT, 1f)
            .add(ENTITY_LABEL, SerializableDataTypes.ENTITY_TYPE)
            .add(NBT_LABEL, SerializableDataTypes.NBT_COMPOUND, new NbtCompound())
            .add(FieldNames.PROJECTILE_ACTION, EntityAction.DATA_TYPE.optional(), Optional.empty()),
        data -> new FireProjectileAction(
            data.getInt(FieldNames.COUNT),
            data.getFloat(SPEED_LABEL),
            data.getFloat(DIVERGENCE_LABEL),
            data.get(ENTITY_LABEL),
            data.get(NBT_LABEL),
            data.get(FieldNames.PROJECTILE_ACTION)
        ),
        (actionType, data) -> data.instance()
            .set(FieldNames.COUNT, actionType.count)
            .set(SPEED_LABEL, actionType.speed)
            .set(DIVERGENCE_LABEL, actionType.divergence)
            .set(ENTITY_LABEL, actionType.entityType)
            .set(NBT_LABEL, actionType.entityNbt)
            .set(FieldNames.PROJECTILE_ACTION, actionType.postSummonAction)
    );

    public FireProjectileAction (int count, float speed, float divergence, EntityType<?> entityType, NbtCompound entityNbt, Optional<EntityAction> postSummonAction) {
        this.count = count;
        this.speed = speed;
        this.divergence = divergence;
        this.entityType = entityType;
        this.entityNbt = entityNbt;
        this.postSummonAction = postSummonAction;
    }

    @Override
    public void accept (BiEntityActionContext context) {
        if (context.actor().getWorld().isClient) return;
        final ServerWorld serverWorld = (ServerWorld)context.actor().getWorld();

        final double dx = context.target().getX() - context.actor().getX();
        final double dy = context.target().getEyeY() - context.actor().getEyeY();
        final double dz = context.target().getZ() - context.actor().getZ();

        Vec3d projectileDirection = (new Vec3d(dx, dy, dz)).normalize();

        for (int i = 0; i < this.count; i++) {
            Optional<Entity> opt$entityToSpawn = MiscUtil.getEntityWithPassengers(
                serverWorld,
                this.entityType,
                this.entityNbt,
                context.actor().getPos().add(0, context.actor().getEyeHeight(context.actor().getPose()), 0),
                context.actor().getYaw(),
                context.actor().getPitch()
            );
            if (opt$entityToSpawn.isEmpty()) return;

            Entity entityToSpawn = opt$entityToSpawn.get();

            if (entityToSpawn instanceof ProjectileEntity projectileToSpawn) {
                projectileToSpawn.setOwner(context.actor());
                projectileToSpawn.setVelocity(projectileDirection.x, projectileDirection.y, projectileDirection.z, this.speed, this.divergence);
            }
            else {
                Vec3d velocity = projectileDirection.multiply(this.speed);
                entityToSpawn.setVelocity(velocity);
            }
            
            serverWorld.spawnNewEntityAndPassengers(entityToSpawn);
            this.postSummonAction.ifPresent(entityAction -> entityAction.execute(entityToSpawn));
        }
    }

    @Override
    public @NotNull ActionConfiguration<FireProjectileAction> getConfig () {
        return POBientityActionTypes.FIRE_PROJECTILE;
    }
}
