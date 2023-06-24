package com.provismet.proviorigins.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

@SuppressWarnings("rawtypes")
public class OccludeVibrationsPower extends Power {
    public OccludeVibrationsPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }
    
    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("occlude_vibrations"),
            new SerializableData(),
            data -> (type, player) -> new OccludeVibrationsPower(type, player))
            .allowCondition();
    }
}
