package com.provismet.proviorigins.conditions.entity;

import com.provismet.proviorigins.registries.POEntityConditionTypes;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.EntityConditionType;
import net.minecraft.entity.Entity;
import net.minecraft.world.GameRules;
import org.jetbrains.annotations.NotNull;

public class RegenGameruleCondition extends EntityConditionType {
    @Override
    public boolean test (Entity entity) {
        return entity.getWorld().getGameRules().get(GameRules.NATURAL_REGENERATION).get();
    }

    @Override
    public @NotNull ConditionConfiguration<RegenGameruleCondition> getConfig () {
        return POEntityConditionTypes.REGEN_GAMERULE;
    }
}
