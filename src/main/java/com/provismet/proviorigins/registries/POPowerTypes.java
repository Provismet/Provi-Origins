package com.provismet.proviorigins.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.powers.ActionOnCriticalHitPower;
import com.provismet.proviorigins.powers.ActionOnDetectVibrationPower;
import com.provismet.proviorigins.powers.ActionOnGainExpPower;
import com.provismet.proviorigins.powers.ActionOnGainLevelPower;
import com.provismet.proviorigins.powers.ActiveItemPower;
import com.provismet.proviorigins.powers.EmissivePower;
import com.provismet.proviorigins.powers.EvadeProjectilesPower;
import com.provismet.proviorigins.powers.IllusionPower;
import com.provismet.proviorigins.powers.ModifyDarknessPulsePower;
import com.provismet.proviorigins.powers.ModifyPassengerHeightPower;
import com.provismet.proviorigins.powers.OccludeVibrationsPower;
import com.provismet.proviorigins.powers.PreventBreathingPower;
import com.provismet.proviorigins.powers.PreventCriticalHitPower;
import com.provismet.proviorigins.powers.PreventPortalsPower;
import com.provismet.proviorigins.powers.PreventPotionCloudPower;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.origins.power.type.OriginsPowerTypes;

import java.util.Optional;
import java.util.function.Function;

public abstract class POPowerTypes {
    public static final PowerConfiguration<ActionOnCriticalHitPower> ACTION_ON_CRITICAL_HIT = register("action_on_critical_hit", ActionOnCriticalHitPower.DATA_FACTORY);
    public static final PowerConfiguration<ActionOnDetectVibrationPower> ACTION_ON_DETECT_VIBRATION = register("action_on_detect_vibration", ActionOnDetectVibrationPower.DATA_FACTORY);
    public static final PowerConfiguration<ActionOnGainExpPower> ACTION_ON_GAIN_EXPERIENCE = register("action_on_gain_experience", ActionOnGainExpPower.DATA_FACTORY);
    public static final PowerConfiguration<ActionOnGainLevelPower> ACTION_ON_GAIN_LEVEL = register("action_on_gain_level", ActionOnGainLevelPower.DATA_FACTORY);
    public static final PowerConfiguration<ActiveItemPower> ACTIVE_ITEM = register("active_item", ActiveItemPower.DATA_FACTORY);
    public static final PowerConfiguration<EmissivePower> EMISSIVE = register("emissive", EmissivePower.DATA_FACTORY);
    public static final PowerConfiguration<EvadeProjectilesPower> EVADE_PROJECTILES = register("evade_projectiles", EvadeProjectilesPower.DATA_FACTORY);
    public static final PowerConfiguration<IllusionPower> ILLUSION = register("illusioner_mirror", IllusionPower.DATA_FACTORY);
    public static final PowerConfiguration<ModifyDarknessPulsePower> MODIFY_DARKNESS_PULSE = register("modify_darkness_pulse", ModifyDarknessPulsePower.DATA_FACTORY);
    public static final PowerConfiguration<ModifyPassengerHeightPower> MODIFY_PASSENGER_HEIGHT = register("modify_passenger_height", ModifyPassengerHeightPower.DATA_FACTORY);
    public static final PowerConfiguration<OccludeVibrationsPower> OCCLUDE_VIBRATIONS = register("occlude_vibrations", OccludeVibrationsPower::new);
    public static final PowerConfiguration<PreventBreathingPower> PREVENT_BREATHING = register("prevent_breathing", PreventBreathingPower.DATA_FACTORY);
    public static final PowerConfiguration<PreventCriticalHitPower> PREVENT_CRITICAL_HITS = register("prevent_critical_hits", PreventCriticalHitPower::new);
    public static final PowerConfiguration<PreventPortalsPower> PREVENT_PORTALS = register("prevent_portal_use", PreventPortalsPower::new);
    public static final PowerConfiguration<PreventPotionCloudPower> PREVENT_POTION_CLOUD = register("prevent_potion_cloud", PreventPotionCloudPower::new);

    private static <T extends PowerType> PowerConfiguration<T> register (String name, TypedDataObjectFactory<T> factory) {
        return OriginsPowerTypes.register(PowerConfiguration.of(ProviOriginsMain.identifier(name), factory));
    }

    private static <T extends PowerType> PowerConfiguration<T> register (String name, Function<Optional<EntityCondition>, T> constructor) {
        return OriginsPowerTypes.register(PowerConfiguration.conditionedSimple(ProviOriginsMain.identifier(name), constructor));
    }

    public static void init () {}
}
