package com.provismet.proviorigins.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

/*
 * Prevents the user from being affected by a potion cloud from splash or lingering potions.
 * Relies on the associated mixin.
 */
@SuppressWarnings("rawtypes")
public class PreventPotionCloudPower extends Power {
    public PreventPotionCloudPower (PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("prevent_potion_cloud"),
            new SerializableData(),
            data -> (type, player) -> new PreventPotionCloudPower(type, player))
            .allowCondition();
    }
}
