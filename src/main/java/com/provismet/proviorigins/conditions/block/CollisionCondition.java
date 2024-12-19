package com.provismet.proviorigins.conditions.block;

import com.provismet.proviorigins.registries.POBlockConditionTypes;

import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.BlockConditionType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CollisionCondition extends BlockConditionType {
    @Override
    public boolean test (World world, BlockPos pos, BlockState blockState, Optional<BlockEntity> blockEntity) {
        return !blockState.getCollisionShape(world, pos).isEmpty();
    }

    @Override
    public @NotNull ConditionConfiguration<CollisionCondition> getConfig () {
        return POBlockConditionTypes.CAN_COLLIDE;
    }
}
