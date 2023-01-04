package com.provismet.personalOrigins.powers;

import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.util.registry.Registry;

public class PowerFactories {
    public static void register () {
        register(PreventBreathing.createPowerFactory());
        register(PreventPotionCloud.createPowerFactory());
        register(AdjustPassengerHeight.createPowerFactory());
        register(ActionOnDetectVibration.createPowerFactory());
        register(Emissive.createPowerFactory());
        register(ActiveItemPower.createPowerFactory());
    }

    private static void register (PowerFactory<?> powerFactory) {
        Registry.register(ApoliRegistries.POWER_FACTORY, powerFactory.getSerializerId(), powerFactory);
    }
}
