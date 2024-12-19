package com.provismet.proviorigins.conditions.bientity;

import com.provismet.proviorigins.registries.POBientityConditionTypes;
import com.provismet.proviorigins.utility.constants.FieldNames;

import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.BiEntityConditionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.jetbrains.annotations.NotNull;

public class CanSeeCondition extends BiEntityConditionType {
    private final ShapeType shapeType;
    private final FluidHandling fluidHandling;

    public static final TypedDataObjectFactory<CanSeeCondition> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(FieldNames.SHAPE_TYPE, SerializableDataType.enumValue(ShapeType.class), ShapeType.VISUAL)
            .add(FieldNames.FLUID_HANDLING, SerializableDataType.enumValue(FluidHandling.class), FluidHandling.NONE),
        data -> new CanSeeCondition(
            data.get(FieldNames.SHAPE_TYPE),
            data.get(FieldNames.FLUID_HANDLING)
        ),
        (conditionType, data) -> data.instance()
            .set(FieldNames.SHAPE_TYPE, conditionType.shapeType)
            .set(FieldNames.FLUID_HANDLING, conditionType.fluidHandling)
    );

    public CanSeeCondition (ShapeType shapeType, FluidHandling fluidHandling) {
        this.shapeType = shapeType;
        this.fluidHandling = fluidHandling;
    }

    @Override
    public boolean test (Entity actor, Entity target) {
        if (actor.getWorld() != target.getWorld()) {
            return false;
        }
        else {
            Vec3d actorEyes = new Vec3d(actor.getX(), actor.getEyeY(), actor.getZ());
            Vec3d targetEyes = new Vec3d(target.getX(), target.getEyeY(), target.getZ());
            Vec3d targetFeet = new Vec3d(target.getX(), target.getY(), target.getZ());
            if (actorEyes.distanceTo(targetEyes) > 128.0) {
                return false;
            }
            else {
                return actor.getWorld().raycast(new RaycastContext(actorEyes, targetEyes, shapeType, fluidHandling, actor)).getType() == HitResult.Type.MISS
                    || actor.getWorld().raycast(new RaycastContext(actorEyes, targetFeet, shapeType, fluidHandling, actor)).getType() == HitResult.Type.MISS;
            }
        }
    }

    @Override
    public @NotNull ConditionConfiguration<CanSeeCondition> getConfig () {
        return POBientityConditionTypes.CAN_SEE;
    }
}
