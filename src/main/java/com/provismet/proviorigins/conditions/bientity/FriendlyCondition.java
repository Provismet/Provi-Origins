package com.provismet.proviorigins.conditions.bientity;

import com.provismet.lilylib.util.Relations;

import com.provismet.proviorigins.registries.POBientityConditionTypes;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.BiEntityConditionType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class FriendlyCondition extends BiEntityConditionType {
    @Override
    public boolean test (Entity actor, Entity target) {
        if (actor instanceof LivingEntity livingActor && target instanceof LivingEntity livingTarget) {
            return Relations.isFriendly(livingActor, livingTarget);
        }
        return false;
    }

    @Override
    public @NotNull ConditionConfiguration<FriendlyCondition> getConfig () {
        return POBientityConditionTypes.FRIENDLY;
    }
}
