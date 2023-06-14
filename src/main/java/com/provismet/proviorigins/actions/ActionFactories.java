package com.provismet.proviorigins.actions;

import com.provismet.proviorigins.actions.bientity.StatusTransferAction;
import com.provismet.proviorigins.actions.bientity.SwapPositionsAction;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.entity.Entity;
import net.minecraft.util.Pair;
import net.minecraft.registry.Registry;

public class ActionFactories {
    private static void registerEntityAction (ActionFactory<Entity> actionFactory) {
        Registry.register(ApoliRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }

    private static void registerBientityAction (ActionFactory<Pair<Entity, Entity>> actionFactory) {
        Registry.register(ApoliRegistries.BIENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }

    public static void register () {
        // Entity Actions
        registerEntityAction(DoubleJumpAction.createActionFactory());
        registerEntityAction(RaycastTeleportAction.createActionFactory());
        registerEntityAction(SummonCloneAction.createActionFactory());
        registerEntityAction(ActOnClosestEntity.createActionFactory());
        registerEntityAction(ActOnFarthestEntity.createActionFactory());

        // Bientity Actions
        registerBientityAction(SwapPositionsAction.createBientityActionFactory());
        registerBientityAction(StatusTransferAction.createBientityActionFactory());
    }
}
