package com.provismet.proviorigins.conditions.bientity;

import com.provismet.proviorigins.registries.POBientityConditionTypes;
import com.provismet.proviorigins.utility.constants.FieldNames;

import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.context.BiEntityConditionContext;
import io.github.apace100.apoli.condition.type.BiEntityConditionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.jetbrains.annotations.NotNull;

public class CanSeeBiEntityConditionType extends BiEntityConditionType {
    private final ShapeType shapeType;
    private final FluidHandling fluidHandling;

    public static final TypedDataObjectFactory<CanSeeBiEntityConditionType> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(FieldNames.SHAPE_TYPE, SerializableDataType.enumValue(ShapeType.class), ShapeType.VISUAL)
            .add(FieldNames.FLUID_HANDLING, SerializableDataType.enumValue(FluidHandling.class), FluidHandling.NONE),
        data -> new CanSeeBiEntityConditionType(
            data.get(FieldNames.SHAPE_TYPE),
            data.get(FieldNames.FLUID_HANDLING)
        ),
        (conditionType, data) -> data.instance()
            .set(FieldNames.SHAPE_TYPE, conditionType.shapeType)
            .set(FieldNames.FLUID_HANDLING, conditionType.fluidHandling)
    );

    public CanSeeBiEntityConditionType (ShapeType shapeType, FluidHandling fluidHandling) {
        this.shapeType = shapeType;
        this.fluidHandling = fluidHandling;
    }

    @Override
    public boolean test (BiEntityConditionContext context) {
        if (context.actor().getWorld() != context.target().getWorld()) {
            return false;
        }
        else {
            Vec3d actorEyes = new Vec3d(context.actor().getX(), context.actor().getEyeY(), context.actor().getZ());
            Vec3d targetEyes = new Vec3d(context.target().getX(), context.target().getEyeY(), context.target().getZ());
            Vec3d targetFeet = new Vec3d(context.target().getX(), context.target().getY(), context.target().getZ());
            if (actorEyes.distanceTo(targetEyes) > 128.0) {
                return false;
            }
            else {
                return context.actor().getWorld().raycast(new RaycastContext(actorEyes, targetEyes, shapeType, fluidHandling, context.actor())).getType() == HitResult.Type.MISS
                    || context.actor().getWorld().raycast(new RaycastContext(actorEyes, targetFeet, shapeType, fluidHandling, context.actor())).getType() == HitResult.Type.MISS;
            }
        }
    }

    @Override
    public @NotNull ConditionConfiguration<CanSeeBiEntityConditionType> getConfig () {
        return POBientityConditionTypes.CAN_SEE;
    }
}
