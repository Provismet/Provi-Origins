package com.provismet.proviorigins.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class PreventPortalsPower extends Power {
    public PreventPortalsPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }
    
    @SuppressWarnings("rawtypes")
    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("prevent_portal_use"),
            new SerializableData(),
            data -> (type, player) -> new PreventPortalsPower(type, player))
            .allowCondition();
    }
}
