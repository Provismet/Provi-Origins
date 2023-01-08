package com.provismet.proviorigins.powers;

import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.util.registry.Registry;

public class PowerFactories {
    public static void register () {
        register(PreventBreathingPower.createPowerFactory());
        register(PreventPotionCloudPower.createPowerFactory());
        register(AdjustPassengerHeightPower.createPowerFactory());
        register(ActionOnDetectVibrationPower.createPowerFactory());
        register(EmissivePower.createPowerFactory());
        register(ActiveItemPower.createPowerFactory());
        register(OccludeVibrationsPower.createPowerFactory());
    }

    private static void register (PowerFactory<?> powerFactory) {
        Registry.register(ApoliRegistries.POWER_FACTORY, powerFactory.getSerializerId(), powerFactory);
    }
}
