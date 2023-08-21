package com.provismet.proviorigins.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;

public class ModifyDarknessPulsePower extends Power {
    private static final String MULTIPLIER_LABEL = "multiplier";
    private static final String ADDITION_LABEL = "addition";

    private final float multiplier;
    private final float addition;

    public ModifyDarknessPulsePower(PowerType<?> type, LivingEntity entity, float multiplier, float addition) {
        super(type, entity);
        this.multiplier = multiplier;
        this.addition = addition;
    }
    
    public float apply (float darknessModifier) {
        return darknessModifier * this.multiplier + this.addition;
    }

    @SuppressWarnings("rawtypes")
    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("modify_darkness_pulse"),
            new SerializableData()
                .add(MULTIPLIER_LABEL, SerializableDataTypes.FLOAT, 1f)
                .add(ADDITION_LABEL, SerializableDataTypes.FLOAT, 0f),
            data -> (type, player) -> new ModifyDarknessPulsePower(type, player,
                data.getFloat(MULTIPLIER_LABEL),
                data.getFloat(ADDITION_LABEL)
            )
        );
    }
}
