package com.provismet.proviorigins.extras;

import java.util.List;

import com.provismet.proviorigins.content.registries.POEntities;
import com.provismet.proviorigins.powers.EmissivePower;

import dev.lambdaurora.lambdynlights.api.DynamicLightHandlers;
import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.EntityType;

public class DynamicLightsEntry implements DynamicLightsInitializer {
    @Override
    public void onInitializeDynamicLights () {
        DynamicLightHandlers.registerDynamicLightHandler(EntityType.PLAYER, entity -> {
            int light = 0;
            List<EmissivePower> powers = PowerHolderComponent.getPowers(entity, EmissivePower.class);
            for (EmissivePower power : powers) {
                if (power.dynamicLight > light) light = power.dynamicLight;
            }
            return light;
        });

        DynamicLightHandlers.registerDynamicLightHandler(POEntities.CLONE, entity -> {
            int light = 0;
            List<EmissivePower> powers = PowerHolderComponent.getPowers(entity, EmissivePower.class);
            for (EmissivePower power : powers) {
                if (power.dynamicLight > light) light = power.dynamicLight;
            }
            return light;
        });

        DynamicLightHandlers.registerDynamicLightHandler(POEntities.MINION, entity -> {
            int light = 0;
            List<EmissivePower> powers = PowerHolderComponent.getPowers(entity, EmissivePower.class);
            for (EmissivePower power : powers) {
                if (power.dynamicLight > light) light = power.dynamicLight;
            }
            return light;
        });
    }
}
