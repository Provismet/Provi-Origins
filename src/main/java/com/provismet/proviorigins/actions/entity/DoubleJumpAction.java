package com.provismet.proviorigins.actions.entity;

import com.provismet.proviorigins.registries.POEntityActionTypes;
import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.type.EntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class DoubleJumpAction extends EntityActionType {
    private static final String VELOCITY_LABEL = "velocity";
    private static final String FALL_LABEL = "reset_fall_height";

    private final double velocity;
    private final boolean resetFall;

    public static final TypedDataObjectFactory<DoubleJumpAction> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(VELOCITY_LABEL, SerializableDataTypes.DOUBLE)
            .add(FALL_LABEL, SerializableDataTypes.BOOLEAN, true),
        data -> new DoubleJumpAction(
            data.getDouble(VELOCITY_LABEL),
            data.getBoolean(FALL_LABEL)
        ),
        (actionType, data) -> data.instance()
            .set(VELOCITY_LABEL, actionType.velocity)
            .set(FALL_LABEL, actionType.resetFall)
    );

    public DoubleJumpAction (double velocity, boolean resetFall) {
        this.velocity = velocity;
        this.resetFall = resetFall;
    }

    @Override
    protected void execute (Entity entity) {
        Vec3d currentVelocity = entity.getVelocity();
        entity.setVelocity(currentVelocity.x, this.velocity, currentVelocity.z);
        entity.velocityModified = true;

        if (this.resetFall) entity.fallDistance = 0;
    }

    @Override
    public @NotNull ActionConfiguration<DoubleJumpAction> getConfig () {
        return POEntityActionTypes.DOUBLE_JUMP;
    }
}
