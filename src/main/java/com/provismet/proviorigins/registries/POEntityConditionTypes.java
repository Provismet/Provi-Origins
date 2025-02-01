package com.provismet.proviorigins.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.conditions.entity.ClientServerEntityConditionType;
import com.provismet.proviorigins.conditions.entity.EntityInRadiusEntityConditionType;
import com.provismet.proviorigins.conditions.entity.HasTeamEntityConditionType;
import com.provismet.proviorigins.conditions.entity.RegenGameruleEntityConditionType;
import com.provismet.proviorigins.conditions.entity.VelocityYEntityConditionType;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.EntityConditionType;
import io.github.apace100.apoli.condition.type.EntityConditionTypes;
import io.github.apace100.apoli.data.TypedDataObjectFactory;

import java.util.function.Supplier;

public abstract class POEntityConditionTypes {
    public static final ConditionConfiguration<ClientServerEntityConditionType> CLIENT_SERVER = register("client_server", ClientServerEntityConditionType.DATA_FACTORY);
    public static final ConditionConfiguration<EntityInRadiusEntityConditionType> ENTITY_IN_RADIUS = register("entity_in_radius", EntityInRadiusEntityConditionType.DATA_FACTORY);
    public static final ConditionConfiguration<HasTeamEntityConditionType> HAS_TEAM = register("has_team", HasTeamEntityConditionType::new);
    public static final ConditionConfiguration<RegenGameruleEntityConditionType> REGEN_GAMERULE = register("can_natural_regen", RegenGameruleEntityConditionType::new);
    public static final ConditionConfiguration<VelocityYEntityConditionType> VELOCITY_Y = register("velocity_y", VelocityYEntityConditionType.DATA_FACTORY);

    private static <T extends EntityConditionType> ConditionConfiguration<T> register (String name, TypedDataObjectFactory<T> factory) {
        return EntityConditionTypes.register(ConditionConfiguration.of(ProviOriginsMain.identifier(name), factory));
    }

    private static <T extends EntityConditionType> ConditionConfiguration<T> register (String name, Supplier<T> constructor) {
        return EntityConditionTypes.register(ConditionConfiguration.simple(ProviOriginsMain.identifier(name), constructor));
    }

    public static void init () {}
}
