package com.provismet.proviorigins.conditions.bientity;

import com.provismet.lilylib.util.Relations;

import com.provismet.proviorigins.registries.POBientityConditionTypes;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.context.BiEntityConditionContext;
import io.github.apace100.apoli.condition.type.BiEntityConditionType;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class FriendlyBiEntityConditionType extends BiEntityConditionType {
    @Override
    public boolean test (BiEntityConditionContext context) {
        if (context.actor() instanceof LivingEntity livingActor && context.target() instanceof LivingEntity livingTarget) {
            return Relations.isFriendly(livingActor, livingTarget);
        }
        return false;
    }

    @Override
    public @NotNull ConditionConfiguration<FriendlyBiEntityConditionType> getConfig () {
        return POBientityConditionTypes.FRIENDLY;
    }
}
