package com.provismet.proviorigins.powers;

import com.provismet.proviorigins.registries.POPowerTypes;
import com.provismet.proviorigins.utility.constants.FieldNames;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PreventBreathingPower extends PowerType {
    private static final String DAMAGE_TYPE = "damage_type";
    private static final String RESPECT_BREATHING = "respect_water_breathing";

    private final RegistryKey<DamageType> damageType;
    public final boolean respectWaterBreathing;

    public static final TypedDataObjectFactory<PreventBreathingPower> DATA_FACTORY = PowerType.createConditionedDataFactory(
        new SerializableData()
            .add(DAMAGE_TYPE, SerializableDataTypes.DAMAGE_TYPE)
            .add(RESPECT_BREATHING, SerializableDataTypes.BOOLEAN, true),
        (data, condition) -> new PreventBreathingPower(
            data.get(DAMAGE_TYPE),
            data.getBoolean(RESPECT_BREATHING),
            condition
        ),
        (powerType, data) -> data.instance()
            .set(DAMAGE_TYPE, powerType.damageType)
            .set(RESPECT_BREATHING, powerType.respectWaterBreathing)
    );

    public PreventBreathingPower (RegistryKey<DamageType> damageType, boolean respectWaterBreathing, Optional<EntityCondition> condition) {
        super(condition);
        this.damageType = damageType;
        this.respectWaterBreathing = respectWaterBreathing;
    }

    public DamageSource getDamageSource () {
        return this.getHolder().getDamageSources().create(damageType);
    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig () {
        return POPowerTypes.PREVENT_BREATHING;
    }
}
