package com.provismet.proviorigins.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.conditions.bientity.CanSeeCondition;
import com.provismet.proviorigins.conditions.bientity.FriendlyCondition;
import com.provismet.proviorigins.conditions.bientity.TeammateCondition;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.BiEntityConditionType;
import io.github.apace100.apoli.condition.type.BiEntityConditionTypes;
import io.github.apace100.apoli.data.TypedDataObjectFactory;

import java.util.function.Supplier;

public abstract class POBientityConditionTypes {
    public static final ConditionConfiguration<CanSeeCondition> CAN_SEE = register("can_see_any", CanSeeCondition.DATA_FACTORY);
    public static final ConditionConfiguration<FriendlyCondition> FRIENDLY = register("friendly", FriendlyCondition::new);
    public static final ConditionConfiguration<TeammateCondition> TEAMMATE = register("teammate", TeammateCondition::new);

    private static <T extends BiEntityConditionType> ConditionConfiguration<T> register (String name, TypedDataObjectFactory<T> factory) {
        return BiEntityConditionTypes.register(ConditionConfiguration.of(ProviOriginsMain.identifier(name), factory));
    }

    private static <T extends BiEntityConditionType> ConditionConfiguration<T> register (String name, Supplier<T> constructor) {
        return BiEntityConditionTypes.register(ConditionConfiguration.simple(ProviOriginsMain.identifier(name), constructor));
    }

    public static void init () {}
}
