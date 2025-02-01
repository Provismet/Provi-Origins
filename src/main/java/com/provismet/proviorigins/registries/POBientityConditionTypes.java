package com.provismet.proviorigins.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.conditions.bientity.CanSeeBiEntityConditionType;
import com.provismet.proviorigins.conditions.bientity.FriendlyBiEntityConditionType;
import com.provismet.proviorigins.conditions.bientity.TeammateBiEntityConditionType;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.BiEntityConditionType;
import io.github.apace100.apoli.condition.type.BiEntityConditionTypes;
import io.github.apace100.apoli.data.TypedDataObjectFactory;

import java.util.function.Supplier;

public abstract class POBientityConditionTypes {
    public static final ConditionConfiguration<CanSeeBiEntityConditionType> CAN_SEE = register("can_see_any", CanSeeBiEntityConditionType.DATA_FACTORY);
    public static final ConditionConfiguration<FriendlyBiEntityConditionType> FRIENDLY = register("friendly", FriendlyBiEntityConditionType::new);
    public static final ConditionConfiguration<TeammateBiEntityConditionType> TEAMMATE = register("teammate", TeammateBiEntityConditionType::new);

    private static <T extends BiEntityConditionType> ConditionConfiguration<T> register (String name, TypedDataObjectFactory<T> factory) {
        return BiEntityConditionTypes.register(ConditionConfiguration.of(ProviOriginsMain.identifier(name), factory));
    }

    private static <T extends BiEntityConditionType> ConditionConfiguration<T> register (String name, Supplier<T> constructor) {
        return BiEntityConditionTypes.register(ConditionConfiguration.simple(ProviOriginsMain.identifier(name), constructor));
    }

    public static void init () {}
}
