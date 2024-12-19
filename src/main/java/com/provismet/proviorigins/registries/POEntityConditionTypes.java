package com.provismet.proviorigins.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.conditions.entity.ClientServerCondition;
import com.provismet.proviorigins.conditions.entity.EntityInRadiusCondition;
import com.provismet.proviorigins.conditions.entity.HasTeamCondition;
import com.provismet.proviorigins.conditions.entity.RegenGameruleCondition;
import com.provismet.proviorigins.conditions.entity.VelocityYCondition;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.EntityConditionType;
import io.github.apace100.apoli.condition.type.EntityConditionTypes;
import io.github.apace100.apoli.data.TypedDataObjectFactory;

import java.util.function.Supplier;

public abstract class POEntityConditionTypes {
    public static final ConditionConfiguration<ClientServerCondition> CLIENT_SERVER = register("client_server", ClientServerCondition.DATA_FACTORY);
    public static final ConditionConfiguration<EntityInRadiusCondition> ENTITY_IN_RADIUS = register("entity_in_radius", EntityInRadiusCondition.DATA_FACTORY);
    public static final ConditionConfiguration<HasTeamCondition> HAS_TEAM = register("has_team", HasTeamCondition::new);
    public static final ConditionConfiguration<RegenGameruleCondition> REGEN_GAMERULE = register("can_natural_regen", RegenGameruleCondition::new);
    public static final ConditionConfiguration<VelocityYCondition> VELOCITY_Y = register("velocity_y", VelocityYCondition.DATA_FACTORY);

    private static <T extends EntityConditionType> ConditionConfiguration<T> register (String name, TypedDataObjectFactory<T> factory) {
        return EntityConditionTypes.register(ConditionConfiguration.of(ProviOriginsMain.identifier(name), factory));
    }

    private static <T extends EntityConditionType> ConditionConfiguration<T> register (String name, Supplier<T> constructor) {
        return EntityConditionTypes.register(ConditionConfiguration.simple(ProviOriginsMain.identifier(name), constructor));
    }

    public static void init () {}
}
