package com.provismet.proviorigins.conditions.bientity;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.entity.Entity;
import net.minecraft.util.Pair;
import net.minecraft.registry.Registry;

public class BiEntityConditionFactories {
    public static void register () {
        register(TeammateCondition.getFactory());
        register(FriendlyCondition.getFactory());
        register(CanSeeCondition.getFactory());
    }

    private static void register (ConditionFactory<Pair<Entity,Entity>> conditionFactory) {
        Registry.register(ApoliRegistries.BIENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
