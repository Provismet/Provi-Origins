package com.provismet.proviorigins.utility;

import io.github.apace100.apoli.condition.BiEntityCondition;
import io.github.apace100.apoli.condition.BlockCondition;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.condition.ItemCondition;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public interface ConditionUtil {
    static boolean emptyOrTest (Optional<EntityCondition> condition, Entity entity) {
        return condition.map(entityCondition -> entityCondition.test(entity)).orElse(true);
    }

    static boolean emptyOrTest (Optional<BiEntityCondition> condition, Entity actor, Entity target) {
        return condition.map(biEntityCondition -> biEntityCondition.test(actor, target)).orElse(true);
    }

    static boolean emptyOrTest (Optional<ItemCondition> condition, World world, ItemStack itemStack) {
        return condition.map(itemCondition -> itemCondition.test(world, itemStack)).orElse(true);
    }

    static boolean emptyOrTest (Optional<BlockCondition> condition, World world, BlockPos blockPos) {
        return condition.map(blockCondition -> blockCondition.test(world, blockPos)).orElse(true);
    }
}
