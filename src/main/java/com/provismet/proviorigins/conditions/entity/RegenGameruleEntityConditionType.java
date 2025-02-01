package com.provismet.proviorigins.conditions.entity;

import com.provismet.proviorigins.registries.POEntityConditionTypes;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.context.EntityConditionContext;
import io.github.apace100.apoli.condition.type.EntityConditionType;
import net.minecraft.world.GameRules;
import org.jetbrains.annotations.NotNull;

public class RegenGameruleEntityConditionType extends EntityConditionType {
    @Override
    public boolean test (EntityConditionContext context) {
        return context.entity().getWorld().getGameRules().get(GameRules.NATURAL_REGENERATION).get();
    }

    @Override
    public @NotNull ConditionConfiguration<RegenGameruleEntityConditionType> getConfig () {
        return POEntityConditionTypes.REGEN_GAMERULE;
    }
}
