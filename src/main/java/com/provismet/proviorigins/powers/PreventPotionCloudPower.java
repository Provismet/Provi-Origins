package com.provismet.proviorigins.powers;

import com.provismet.proviorigins.registries.POPowerTypes;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/*
 * Prevents the user from being affected by a potion cloud from splash or lingering potions.
 * Relies on the associated mixin.
 */
public class PreventPotionCloudPower extends PowerType {
    public PreventPotionCloudPower (Optional<EntityCondition> condition) {
        super(condition);
    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig () {
        return POPowerTypes.PREVENT_POTION_CLOUD;
    }
}
