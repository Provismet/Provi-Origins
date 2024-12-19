package com.provismet.proviorigins.actions.bientity;

import com.provismet.proviorigins.registries.POBientityActionTypes;
import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.type.BiEntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class SwapPositionsAction extends BiEntityActionType {
    private static final String RESET_FALL_LABEL = "reset_fall_height";
    private static final String RESET_VELOCITY_LABEL = "reset_velocity";

    private final boolean resetFall;
    private final boolean resetVelocity;

    public static final TypedDataObjectFactory<SwapPositionsAction> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(RESET_FALL_LABEL, SerializableDataTypes.BOOLEAN, true)
            .add(RESET_VELOCITY_LABEL, SerializableDataTypes.BOOLEAN),
        data -> new SwapPositionsAction(
            data.getBoolean(RESET_FALL_LABEL),
            data.getBoolean(RESET_VELOCITY_LABEL)
        ),
        (actionType, data) -> data.instance()
            .set(RESET_FALL_LABEL, actionType.resetFall)
            .set(RESET_VELOCITY_LABEL, actionType.resetVelocity)
    );

    public SwapPositionsAction (boolean resetFall, boolean resetVelocity) {
        this.resetFall = resetFall;
        this.resetVelocity = resetVelocity;
    }

    public void execute (Entity actor, Entity target) {
        if (actor instanceof LivingEntity livingActor && target instanceof LivingEntity livingTarget) {
            if (resetFall) {
                livingActor.fallDistance = 0;
                livingTarget.fallDistance = 0;
            }

            if (resetVelocity) {
                livingActor.setVelocity(Vec3d.ZERO);
                livingTarget.setVelocity(Vec3d.ZERO);
            }

            Vec3d temp = livingActor.getPos();
            livingActor.setPosition(livingTarget.getPos());
            livingTarget.setPosition(temp);
        }
    }

    @Override
    public @NotNull ActionConfiguration<SwapPositionsAction> getConfig () {
        return POBientityActionTypes.SWAP_POSITIONS;
    }
}
