package com.provismet.proviorigins.conditions.bientity;

import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import net.minecraft.entity.Entity;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public class CanSeeCondition {
    public static boolean condition (SerializableData.Instance data, Pair<Entity,Entity> pair) {
        ShapeType shapeType = data.get(Powers.SHAPE_TYPE);
        FluidHandling fluidHandling = data.get(Powers.FLUID_HANDLING);
        if (pair.getLeft().getWorld() != pair.getRight().getWorld()) {
            return false;
        }
        else {
            Vec3d actorEyes = new Vec3d(pair.getLeft().getX(), pair.getLeft().getEyeY(), pair.getLeft().getZ());
            Vec3d targetEyes = new Vec3d(pair.getRight().getX(), pair.getRight().getEyeY(), pair.getRight().getZ());
            Vec3d targetFeet = new Vec3d(pair.getRight().getX(), pair.getRight().getY(), pair.getRight().getZ());
            if (actorEyes.distanceTo(targetEyes) > 128.0) {
                return false;
            }
            else {
                return pair.getLeft().getWorld().raycast(new RaycastContext(actorEyes, targetEyes, shapeType, fluidHandling, pair.getLeft())).getType() == HitResult.Type.MISS
                    || pair.getLeft().getWorld().raycast(new RaycastContext(actorEyes, targetFeet, shapeType, fluidHandling, pair.getLeft())).getType() == HitResult.Type.MISS;
            }
        }
    }

    public static ConditionFactory<Pair<Entity,Entity>> getFactory () {
        return new ConditionFactory<>(Powers.identifier("can_see_any"),
            new SerializableData()
                .add(Powers.SHAPE_TYPE, SerializableDataType.enumValue(ShapeType.class), ShapeType.VISUAL)
                .add(Powers.FLUID_HANDLING, SerializableDataType.enumValue(FluidHandling.class), FluidHandling.NONE),
            CanSeeCondition::condition);
    }
}
