package com.provismet.proviorigins.actions.entity;

import java.util.Optional;

import com.provismet.proviorigins.content.entities.MinionEntity;
import com.provismet.proviorigins.content.registries.POEntities;
import com.provismet.proviorigins.registries.POEntityActionTypes;
import com.provismet.proviorigins.utility.constants.FieldNames;

import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.BiEntityAction;
import io.github.apace100.apoli.action.context.EntityActionContext;
import io.github.apace100.apoli.action.type.EntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class SummonMinionAction extends EntityActionType {
    private static final String TEXTURE_LABEL = "texture";
    private static final String FOLLOW_OWNER_LABEL = "follow_owner";
    private static final String FOLLOW_OWNER_OFFSET_LABEL = "follow_offset";
    private static final String SCALE_LABEL = "scale";
    private static final String INVULNERABLE_LABEL = "invulnerable";
    private static final String LIFE_LABEL = "max_life_ticks";

    private final Identifier texture;
    private final boolean followOwner;
    private final Vec3d followOffset;
    private final float scale;
    private final boolean invulnerable;
    private final int lifetime;
    private final Optional<BiEntityAction> postSummonAction;

    public static final TypedDataObjectFactory<SummonMinionAction> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(TEXTURE_LABEL, SerializableDataTypes.IDENTIFIER, MinionEntity.TEMPLATE_TEXTURE)
            .add(FOLLOW_OWNER_LABEL, SerializableDataTypes.BOOLEAN)
            .add(FOLLOW_OWNER_OFFSET_LABEL, SerializableDataTypes.VECTOR, Vec3d.ZERO)
            .add(SCALE_LABEL, SerializableDataTypes.FLOAT, 1f)
            .add(INVULNERABLE_LABEL, SerializableDataTypes.BOOLEAN, false)
            .add(LIFE_LABEL, SerializableDataTypes.INT, 1200)
            .add(FieldNames.BIENTITY_ACTION, BiEntityAction.DATA_TYPE.optional(), Optional.empty()),
        data -> new SummonMinionAction(
            data.get(TEXTURE_LABEL),
            data.getBoolean(FOLLOW_OWNER_LABEL),
            data.get(FOLLOW_OWNER_OFFSET_LABEL),
            data.getFloat(SCALE_LABEL),
            data.getBoolean(INVULNERABLE_LABEL),
            data.getInt(LIFE_LABEL),
            data.get(FieldNames.BIENTITY_ACTION)
        ),
        (actionType, data) -> data.instance()
            .set(TEXTURE_LABEL, actionType.texture)
            .set(FOLLOW_OWNER_LABEL, actionType.followOwner)
            .set(FOLLOW_OWNER_OFFSET_LABEL, actionType.followOffset)
            .set(SCALE_LABEL, actionType.scale)
            .set(INVULNERABLE_LABEL, actionType.invulnerable)
            .set(LIFE_LABEL, actionType.lifetime)
            .set(FieldNames.BIENTITY_ACTION, actionType.postSummonAction)
    );

    public SummonMinionAction (Identifier texture, boolean followOwner, Vec3d followOffset, float scale, boolean invulnerable, int lifetime, Optional<BiEntityAction> postSummonAction) {
        this.texture = texture;
        this.followOwner = followOwner;
        this.followOffset = followOffset;
        this.scale = scale;
        this.invulnerable = invulnerable;
        this.lifetime = lifetime;
        this.postSummonAction = postSummonAction;
    }

    @Override
    public void accept (EntityActionContext context) {
        if (context.entity() instanceof LivingEntity living && living.getWorld() instanceof ServerWorld world) {
            MinionEntity minion = new MinionEntity(POEntities.MINION, world);
            minion.setOwner(living);
            minion.setTexture(this.texture);
            minion.setFollowOwner(this.followOwner);
            minion.setScale(this.scale);
            minion.setInvulnerable(this.invulnerable);

            Vec3d minionPosition = new Vec3d(living.getX(), living.getY(), living.getZ());

            if (this.followOwner) minion.setFollowOwnerOffset(this.followOffset);
            minionPosition = minionPosition.add(this.followOffset);

            minion.refreshPositionAndAngles(minionPosition.getX(), minionPosition.getY(), minionPosition.getZ(), living.getHeadYaw(), living.getPitch());
            minion.initialize(world, world.getLocalDifficulty(living.getBlockPos()), SpawnReason.REINFORCEMENT, null);
            minion.setCustomName(Text.of("Minion of " + context.entity().getName().getString()));
            minion.setMaxLifetime(this.lifetime);

            living.getWorld().spawnEntity(minion);

            // Minion is now in the world, actions can be performed on it.
            this.postSummonAction.ifPresent(biEntityAction -> biEntityAction.execute(living, minion));
        }
    }

    @Override
    public @NotNull ActionConfiguration<SummonMinionAction> getConfig () {
        return POEntityActionTypes.SUMMON_MINION;
    }
}
