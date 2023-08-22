package com.provismet.proviorigins.conditions.entity;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registry;

public class EntityConditionFactories {
    private static void register (ConditionFactory<Entity> conditionFactory) {
        Registry.register(ApoliRegistries.ENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }

    public static void register () {
        register(HasTeamCondition.getFactory());
        register(EntityInRadiusCondition.getFactory());
        register(ClientServerCondition.getFactory());
        register(VelocityYCondition.getFactory());
    }
}
