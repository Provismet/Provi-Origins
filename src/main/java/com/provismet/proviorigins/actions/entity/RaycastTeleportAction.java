package com.provismet.proviorigins.actions.entity;

import java.util.Optional;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.registries.POEntityActionTypes;
import com.provismet.proviorigins.utility.constants.FieldNames;

import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.EntityAction;
import io.github.apace100.apoli.action.context.EntityActionContext;
import io.github.apace100.apoli.action.type.EntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.jetbrains.annotations.NotNull;

public class RaycastTeleportAction extends EntityActionType {
    private static final String DESTINATION_LABEL = "destination";
    private static final String ALLOW_WATER_LABEL = "allow_water";
    private static final String ALLOW_LAVA_LABEL = "allow_lava";
    private static final String SHAPE_TYPE_LABEL = "shape_type";
    private static final String FLUID_HANDLING_LABEL = "fluid_handling";
    private static final String SUCCESS_ACTION_LABEL = "success_action";
    private static final String FAILURE_ACTION_LABEL = "miss_action";

    private static final String DIRECT = "direct";
    private static final String ON_TOP = "on_top";

    private final double distance;
    private final String destination;
    private final boolean allowWater;
    private final boolean allowLava;
    private final ShapeType shapeType;
    private final FluidHandling fluidHandling;
    private final Optional<EntityAction> successAction;
    private final Optional<EntityAction> failureAction;

    public static final TypedDataObjectFactory<RaycastTeleportAction> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(FieldNames.DISTANCE, SerializableDataTypes.DOUBLE)
            .add(DESTINATION_LABEL, SerializableDataTypes.STRING)
            .add(ALLOW_WATER_LABEL, SerializableDataTypes.BOOLEAN, false)
            .add(ALLOW_LAVA_LABEL, SerializableDataTypes.BOOLEAN, false)
            .add(SHAPE_TYPE_LABEL, SerializableDataType.enumValue(ShapeType.class), ShapeType.OUTLINE)
            .add(FLUID_HANDLING_LABEL, SerializableDataType.enumValue(FluidHandling.class), FluidHandling.ANY)
            .add(SUCCESS_ACTION_LABEL, EntityAction.DATA_TYPE.optional(), Optional.empty())
            .add(FAILURE_ACTION_LABEL, EntityAction.DATA_TYPE.optional(), Optional.empty()),
        data -> new RaycastTeleportAction(
            data.getDouble(FieldNames.DISTANCE),
            data.getString(DESTINATION_LABEL),
            data.getBoolean(ALLOW_WATER_LABEL),
            data.getBoolean(ALLOW_LAVA_LABEL),
            data.get(SHAPE_TYPE_LABEL),
            data.get(FLUID_HANDLING_LABEL),
            data.get(SUCCESS_ACTION_LABEL),
            data.get(FAILURE_ACTION_LABEL)
        ),
        (actionType, data) -> data.instance()
            .set(FieldNames.DISTANCE, actionType.distance)
            .set(DESTINATION_LABEL, actionType.destination)
            .set(ALLOW_WATER_LABEL, actionType.allowWater)
            .set(ALLOW_LAVA_LABEL, actionType.allowLava)
            .set(SHAPE_TYPE_LABEL, actionType.shapeType)
            .set(FLUID_HANDLING_LABEL, actionType.fluidHandling)
            .set(SUCCESS_ACTION_LABEL, actionType.successAction)
            .set(FAILURE_ACTION_LABEL, actionType.failureAction)
    );

    public RaycastTeleportAction (double distance, String destination, boolean allowWater, boolean allowLava, ShapeType shapeType, FluidHandling fluidHandling, Optional<EntityAction> successAction, Optional<EntityAction> failureAction) {
        this.distance = distance;
        this.destination = destination;
        this.allowWater = allowWater;
        this.allowLava = allowLava;
        this.shapeType = shapeType;
        this.fluidHandling = fluidHandling;
        this.successAction = successAction;
        this.failureAction = failureAction;
    }

    @Override
    public void accept (EntityActionContext context) {
        Vec3d start = new Vec3d(context.entity().getX(), context.entity().getEyeY(), context.entity().getZ());
        Vec3d direction = context.entity().getRotationVec(1);
        Vec3d end = start.add(direction.multiply(this.distance));

        BlockHitResult blockHit = context.entity().getWorld().raycast(new RaycastContext(start, end, this.shapeType, this.fluidHandling, context.entity()));
        if (blockHit.getType() != HitResult.Type.MISS) {
            if (this.destination.equals(DIRECT)) {
                context.entity().fallDistance = 0f;
                context.entity().setPosition(blockHit.getPos());
                this.successAction.ifPresent(entityAction -> entityAction.execute(context.entity()));
            }
            else if (destination.equals(ON_TOP)) {
                CachedBlockPosition upByOne = new CachedBlockPosition(context.entity().getWorld(), blockHit.getBlockPos().add(0, 1, 0), true);
                CachedBlockPosition upByTwo = new CachedBlockPosition(context.entity().getWorld(), blockHit.getBlockPos().add(0, 2, 0), true);

                if (isValid(upByOne, this.allowWater, this.allowLava) && isValid(upByTwo, this.allowWater, this.allowLava)) {
                    context.entity().fallDistance = 0f;
                    context.entity().setPosition(blockHit.getBlockPos().getX() + 0.5, blockHit.getBlockPos().getY() + 1, blockHit.getBlockPos().getZ() + 0.5);
                    this.successAction.ifPresent(entityAction -> entityAction.execute(context.entity()));
                }
                else this.failureAction.ifPresent(entityAction -> entityAction.execute(context.entity()));
            }
            else ProviOriginsMain.LOGGER.warn("Invalid teleport destination attempted.");
        }
        else this.failureAction.ifPresent(entityAction -> entityAction.execute(context.entity()));
    }

    private static boolean isValid (CachedBlockPosition block, boolean allowWater, boolean allowLava) {
        return block.getBlockState().isAir() ||
            (allowWater && block.getWorld().getFluidState(block.getBlockPos()).isIn(FluidTags.WATER)) ||
            (allowLava && block.getWorld().getFluidState(block.getBlockPos()).isIn(FluidTags.LAVA));
    }

    @Override
    public @NotNull ActionConfiguration<RaycastTeleportAction> getConfig () {
        return POEntityActionTypes.RAYCAST_TELEPORT;
    }
}
