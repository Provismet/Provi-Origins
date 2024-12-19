package com.provismet.proviorigins.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.actions.entity.ActOnClosestEntityAction;
import com.provismet.proviorigins.actions.entity.ActOnFarthestEntityAction;
import com.provismet.proviorigins.actions.entity.ActOnOwnerAction;
import com.provismet.proviorigins.actions.entity.DoubleJumpAction;
import com.provismet.proviorigins.actions.entity.ParticleRingAction;
import com.provismet.proviorigins.actions.entity.RaycastTeleportAction;
import com.provismet.proviorigins.actions.entity.SetSummonMaxLifeAction;
import com.provismet.proviorigins.actions.entity.SummonCloneAction;
import com.provismet.proviorigins.actions.entity.SummonMinionAction;
import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.type.EntityActionType;
import io.github.apace100.apoli.action.type.EntityActionTypes;
import io.github.apace100.apoli.data.TypedDataObjectFactory;

public abstract class POEntityActionTypes {
    public static final ActionConfiguration<ActOnClosestEntityAction> ACT_ON_CLOSEST_ENTITY = register("closest_bientity", ActOnClosestEntityAction.DATA_FACTORY);
    public static final ActionConfiguration<ActOnFarthestEntityAction> ACT_ON_FARTHEST_ENTITY = register("farthest_bientity", ActOnFarthestEntityAction.DATA_FACTORY);
    public static final ActionConfiguration<ActOnOwnerAction> ACT_ON_OWNER = register("owner_bientity", ActOnOwnerAction.DATA_FACTORY);
    public static final ActionConfiguration<DoubleJumpAction> DOUBLE_JUMP = register("double_jump", DoubleJumpAction.DATA_FACTORY);
    public static final ActionConfiguration<ParticleRingAction> PARTICLE_RING = register("particle_ring", ParticleRingAction.DATA_FACTORY);
    public static final ActionConfiguration<RaycastTeleportAction> RAYCAST_TELEPORT = register("raycast_teleport", RaycastTeleportAction.DATA_FACTORY);
    public static final ActionConfiguration<SetSummonMaxLifeAction> SET_SUMMON_MAX_LIFE = register("set_summon_max_life_ticks", SetSummonMaxLifeAction.DATA_FACTORY);
    public static final ActionConfiguration<SummonCloneAction> SUMMON_CLONE = register("summon_clone", SummonCloneAction.DATA_FACTORY);
    public static final ActionConfiguration<SummonMinionAction> SUMMON_MINION = register("summon_minion", SummonMinionAction.DATA_FACTORY);

    private static <T extends EntityActionType> ActionConfiguration<T> register (String name, TypedDataObjectFactory<T> factory) {
        return EntityActionTypes.register(ActionConfiguration.of(ProviOriginsMain.identifier(name), factory));
    }

    public static void init () {}
}
