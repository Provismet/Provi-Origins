package com.provismet.proviorigins.actions;

import java.util.function.Consumer;

import com.provismet.proviorigins.content.entities.MinionEntity;
import com.provismet.proviorigins.content.entities.renderers.MinionEntityRenderer;
import com.provismet.proviorigins.content.registries.Entities;
import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3d;

public class SummonMinionAction {
    private static final String TEXTURE_LABEL = "texture";
    private static final String FOLLOW_OWNER_LABEL = "follow_owner";
    private static final String FOLLOW_OWNER_OFFSET_LABEL = "follow_offset";
    private static final String SCALE_LABEL = "scale";
    private static final String INVULNERABLE_LABEL = "invulnerable";
    private static final String LIFE_LABEL = "max_life_ticks";

    public static void action (SerializableData.Instance data, Entity entity) {
        if (entity instanceof LivingEntity living && living.getWorld() instanceof ServerWorld world) {
            final Identifier texture = data.get(TEXTURE_LABEL);
            final boolean shouldFollow = data.getBoolean(FOLLOW_OWNER_LABEL);
            final Vec3d offset = data.get(FOLLOW_OWNER_OFFSET_LABEL);
            final float scale = data.getFloat(SCALE_LABEL);
            final boolean isInvulnerable = data.getBoolean(INVULNERABLE_LABEL);
            final int maxLife = data.getInt(LIFE_LABEL);

            final Consumer<Pair<Entity, Entity>> bientityAction = data.get(Powers.BIENTITY_ACTION);

            MinionEntity minion = new MinionEntity(Entities.MINION, world);
            minion.setOwner(living);
            minion.setTexture(texture);
            minion.setFollowOwner(shouldFollow);
            minion.setScale(scale);
            minion.setInvulnerable(isInvulnerable);

            if (shouldFollow && offset != null) {
                minion.setFollowOwnerOffset(offset);
            }

            minion.refreshPositionAndAngles(living.getX(), living.getY(), living.getZ(), living.getHeadYaw(), living.getPitch());
            minion.initialize(world, world.getLocalDifficulty(living.getBlockPos()), SpawnReason.REINFORCEMENT, null, null);
            minion.setCustomName(Text.of("Minion of " + entity.getName().getString()));
            minion.setMaxLifeTime(maxLife);

            living.getWorld().spawnEntity(minion);

            // Minion is now in the world, actions can be performed on it.
            if (bientityAction != null) bientityAction.accept(new Pair<>(living, minion));
        }
    }

    public static ActionFactory<Entity> createActionFactory () {
        return new ActionFactory<>(Powers.identifier("summon_minion"),
            new SerializableData()
                .add(TEXTURE_LABEL, SerializableDataTypes.IDENTIFIER, MinionEntityRenderer.TEMPLATE_TEXTURE)
                .add(FOLLOW_OWNER_LABEL, SerializableDataTypes.BOOLEAN)
                .add(FOLLOW_OWNER_OFFSET_LABEL, SerializableDataTypes.VECTOR, null)
                .add(SCALE_LABEL, SerializableDataTypes.FLOAT, 1f)
                .add(INVULNERABLE_LABEL, SerializableDataTypes.BOOLEAN, false)
                .add(LIFE_LABEL, SerializableDataTypes.INT, 1200)
                .add(Powers.BIENTITY_ACTION, ApoliDataTypes.BIENTITY_ACTION, null),
            SummonMinionAction::action);
    }
}
