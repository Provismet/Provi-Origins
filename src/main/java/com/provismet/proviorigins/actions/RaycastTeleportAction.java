package com.provismet.proviorigins.actions;

import java.util.function.Consumer;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public class RaycastTeleportAction {
    private static final String DISTANCE_LABEL = "distance";
    private static final String DESTINATION_LABEL = "destination";
    private static final String ALLOW_WATER_LABEL = "allow_water";
    private static final String ALLOW_LAVA_LABEL = "allow_lava";
    private static final String SHAPE_TYPE_LABEL = "shape_type";
    private static final String FLUID_HANDLING_LABEL = "fluid_handling";
    private static final String SUCCESS_ACTION_LABEL = "success_action";
    private static final String FAILURE_ACTION_LABEL = "miss_action";

    private static final String DIRECT = "direct";
    private static final String ON_TOP = "on_top";

    public static void action (SerializableData.Instance data, Entity entity) {
        final double distance = data.getDouble(DISTANCE_LABEL);
        final String destination = data.getString(DESTINATION_LABEL);
        final boolean allowWater = data.getBoolean(ALLOW_WATER_LABEL);
        final boolean allowLava = data.getBoolean(ALLOW_LAVA_LABEL);
        final ShapeType shapeType = data.get(SHAPE_TYPE_LABEL);
        final FluidHandling fluidHandling = data.get(FLUID_HANDLING_LABEL);
        final Consumer<Entity> successAction = data.get(SUCCESS_ACTION_LABEL);
        final Consumer<Entity> failureAction = data.get(FAILURE_ACTION_LABEL);

        Vec3d start = new Vec3d(entity.getX(), entity.getEyeY(), entity.getZ());
        Vec3d direction = entity.getRotationVec(1);
        Vec3d end = start.add(direction.multiply(distance));

        BlockHitResult blockHit = entity.world.raycast(new RaycastContext(start, end, shapeType, fluidHandling, entity));
        if (blockHit.getType() != HitResult.Type.MISS) {
            if (destination.equals(DIRECT)) {
                entity.setPosition(blockHit.getPos());
                if (successAction != null) successAction.accept(entity);
            }
            else if (destination.equals(ON_TOP)) {
                CachedBlockPosition upByOne = new CachedBlockPosition(entity.world, blockHit.getBlockPos().add(0, 1, 0), true);
                CachedBlockPosition upByTwo = new CachedBlockPosition(entity.world, blockHit.getBlockPos().add(0, 2, 0), true);

                if (isValid(upByOne, allowWater, allowLava) && isValid(upByTwo, allowWater, allowLava)) {
                    entity.setPosition(blockHit.getBlockPos().getX() + 0.5, blockHit.getBlockPos().getY() + 1, blockHit.getBlockPos().getZ() + 0.5);
                    if (successAction != null) successAction.accept(entity);
                }
                else if (failureAction != null) failureAction.accept(entity);
            }
            else ProviOriginsMain.LOGGER.warn("Invalid teleport destination attempted.");
        }
        else if (failureAction != null) failureAction.accept(entity);
    }

    private static boolean isValid (CachedBlockPosition block, boolean allowWater, boolean allowLava) {
        return block.getBlockState().isAir() ||
            (allowWater && block.getWorld().getFluidState(block.getBlockPos()).isIn(FluidTags.WATER)) ||
            (allowLava && block.getWorld().getFluidState(block.getBlockPos()).isIn(FluidTags.LAVA));
    }

    public static ActionFactory<Entity> createActionFactory () {
        return new ActionFactory<>(Powers.identifier("raycast_teleport"), 
        new SerializableData()
            .add(DISTANCE_LABEL, SerializableDataTypes.DOUBLE)
            .add(DESTINATION_LABEL, SerializableDataTypes.STRING)
            .add(ALLOW_WATER_LABEL, SerializableDataTypes.BOOLEAN, false)
            .add(ALLOW_LAVA_LABEL, SerializableDataTypes.BOOLEAN, false)
            .add(SHAPE_TYPE_LABEL, SerializableDataType.enumValue(ShapeType.class), ShapeType.OUTLINE)
            .add(FLUID_HANDLING_LABEL, SerializableDataType.enumValue(FluidHandling.class), FluidHandling.ANY)
            .add(SUCCESS_ACTION_LABEL, ApoliDataTypes.ENTITY_ACTION, null)
            .add(FAILURE_ACTION_LABEL, ApoliDataTypes.ENTITY_ACTION, null),
        RaycastTeleportAction::action);
    }
}
