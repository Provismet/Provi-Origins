package com.provismet.proviorigins.actions.bientity;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.provismet.proviorigins.registries.POBientityActionTypes;

import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.context.BiEntityActionContext;
import io.github.apace100.apoli.action.type.BiEntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import org.jetbrains.annotations.NotNull;

public class StatusTransferAction extends BiEntityActionType {
    private static final String STATUS_TYPE_LABEL = "status_types";
    private static final String EFFECTS_LABEL = "effects";
    private static final String CLEANSE_SELF_LABEL = "cleanse_self";
    private static final String AMPLIFIER_MODIFIER_LABEL = "amplifier_multiplier";
    private static final String DURATION_MODIFIER_LABEL = "duration_multiplier";

    private static final Map<StatusEffectCategory, String> CATEGORIES = ImmutableMap.of(
        StatusEffectCategory.BENEFICIAL, "beneficial",
        StatusEffectCategory.NEUTRAL, "neutral",
        StatusEffectCategory.HARMFUL, "harmful"
    );

    private final List<String> statusCategories;
    private final List<String> statusTypes;
    private final boolean cleanseSelf;
    private final double amplifierMultiplier;
    private final double durationMultiplier;

    public static final TypedDataObjectFactory<StatusTransferAction> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(STATUS_TYPE_LABEL, SerializableDataTypes.STRINGS, List.of())
            .add(EFFECTS_LABEL, SerializableDataTypes.STRINGS, List.of())
            .add(CLEANSE_SELF_LABEL, SerializableDataTypes.BOOLEAN)
            .add(AMPLIFIER_MODIFIER_LABEL, SerializableDataTypes.DOUBLE, 1.0)
            .add(DURATION_MODIFIER_LABEL, SerializableDataTypes.DOUBLE, 1.0),
        data -> new StatusTransferAction(
            data.get(STATUS_TYPE_LABEL),
            data.get(EFFECTS_LABEL),
            data.getBoolean(CLEANSE_SELF_LABEL),
            data.getDouble(AMPLIFIER_MODIFIER_LABEL),
            data.getDouble(DURATION_MODIFIER_LABEL)
        ),
        (actionType, data) -> data.instance()
            .set(STATUS_TYPE_LABEL, actionType.statusCategories)
            .set(EFFECTS_LABEL, actionType.statusTypes)
            .set(CLEANSE_SELF_LABEL, actionType.cleanseSelf)
            .set(AMPLIFIER_MODIFIER_LABEL, actionType.amplifierMultiplier)
            .set(DURATION_MODIFIER_LABEL, actionType.durationMultiplier)
    );

    public StatusTransferAction (List<String> statusCategories, List<String> statusTypes, boolean cleanseSelf, double amplifierMultiplier, double durationMultiplier) {
        this.statusCategories = statusCategories;
        this.statusTypes = statusTypes;
        this.cleanseSelf = cleanseSelf;
        this.amplifierMultiplier = amplifierMultiplier;
        this.durationMultiplier = durationMultiplier;
    }

    @Override
    public void accept (BiEntityActionContext context) {
        if (!(context.actor() instanceof LivingEntity livingActor) || !(context.target() instanceof LivingEntity livingTarget) || context.actor().getWorld().isClient) return;

        List<StatusEffectInstance> effects = livingActor.getStatusEffects()
            .stream()
            .filter(
                effect -> {
                    if (!this.statusCategories.isEmpty() && this.statusCategories.contains(CATEGORIES.get(effect.getEffectType().value().getCategory()))) return true;
                    return !this.statusTypes.isEmpty() && this.statusTypes.contains(Registries.STATUS_EFFECT.getId(effect.getEffectType().value()).toString());
                }
            ).toList();
        
        for (StatusEffectInstance instance : effects) {
            if (this.cleanseSelf) livingActor.removeStatusEffect(instance.getEffectType());
            
            StatusEffectInstance newInstance = new StatusEffectInstance(
                instance.getEffectType(),
                (int)(instance.getDuration() * this.durationMultiplier),
                (int)(instance.getAmplifier() * this.amplifierMultiplier),
                instance.isAmbient(),
                instance.shouldShowParticles(),
                instance.shouldShowIcon()
            );
            livingTarget.addStatusEffect(newInstance, livingActor);
        }
    }

    @Override
    public @NotNull ActionConfiguration<StatusTransferAction> getConfig () {
        return POBientityActionTypes.STATUS_TRANSFER;
    }
}
