package com.provismet.proviorigins.powers;

import com.provismet.proviorigins.registries.POPowerTypes;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class OccludeVibrationsPower extends PowerType {
    public OccludeVibrationsPower(Optional<EntityCondition> condition) {
        super(condition);
    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig () {
        return POPowerTypes.OCCLUDE_VIBRATIONS;
    }
}
