package com.provismet.proviorigins.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.actions.bientity.FireProjectileAction;
import com.provismet.proviorigins.actions.bientity.ParticleBeamAction;
import com.provismet.proviorigins.actions.bientity.StatusTransferAction;
import com.provismet.proviorigins.actions.bientity.SwapPositionsAction;
import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.type.BiEntityActionType;
import io.github.apace100.apoli.action.type.BiEntityActionTypes;
import io.github.apace100.apoli.data.TypedDataObjectFactory;

public abstract class POBientityActionTypes {
    public static final ActionConfiguration<FireProjectileAction> FIRE_PROJECTILE = register("fire_projectile", FireProjectileAction.DATA_FACTORY);
    public static final ActionConfiguration<ParticleBeamAction> PARTICLE_BEAM = register("particle_beam", ParticleBeamAction.DATA_FACTORY);
    public static final ActionConfiguration<StatusTransferAction> STATUS_TRANSFER = register("transfer_status", StatusTransferAction.DATA_FACTORY);
    public static final ActionConfiguration<SwapPositionsAction> SWAP_POSITIONS = register("swap_positions", SwapPositionsAction.DATA_FACTORY);

    private static <T extends BiEntityActionType> ActionConfiguration<T> register (String name, TypedDataObjectFactory<T> factory) {
        return BiEntityActionTypes.register(ActionConfiguration.of(ProviOriginsMain.identifier(name), factory));
    }

    public static void init () {}
}
