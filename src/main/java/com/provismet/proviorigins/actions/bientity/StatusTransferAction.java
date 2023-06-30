package com.provismet.proviorigins.actions.bientity;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import net.minecraft.util.Pair;

public class StatusTransferAction {
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

    public static void action (SerializableData.Instance data, Pair<Entity, Entity> bientity) {
        if (!(bientity.getLeft() instanceof LivingEntity) || !(bientity.getRight() instanceof LivingEntity) || bientity.getLeft().getWorld().isClient()) return;
        
        final List<String> statusTypes = data.get(STATUS_TYPE_LABEL);
        final List<String> effectTypes = data.get(EFFECTS_LABEL);
        final boolean cleanse = data.getBoolean(CLEANSE_SELF_LABEL);
        final double amplifierMultiplier = data.getDouble(AMPLIFIER_MODIFIER_LABEL);
        final double durationMultiplier = data.getDouble(DURATION_MODIFIER_LABEL);

        LivingEntity actor = (LivingEntity)bientity.getLeft();
        LivingEntity target = (LivingEntity)bientity.getRight();

        List<StatusEffectInstance> effects = actor.getStatusEffects().stream().filter(
            effect -> {
                if (statusTypes != null && statusTypes.contains(CATEGORIES.get(effect.getEffectType().getCategory()))) return true;
                if (effectTypes != null && effectTypes.contains(Registries.STATUS_EFFECT.getId(effect.getEffectType()).toString())) return true;
                return false;
            }
        ).toList();
        
        for (StatusEffectInstance instance : effects) {
            if (cleanse) actor.removeStatusEffect(instance.getEffectType());
            
            StatusEffectInstance newInstance = new StatusEffectInstance(
                instance.getEffectType(),
                (int)(instance.getDuration() * durationMultiplier),
                (int)(instance.getAmplifier() * amplifierMultiplier),
                instance.isAmbient(),
                instance.shouldShowParticles(),
                instance.shouldShowIcon()
            );
            target.addStatusEffect(newInstance, actor);
        }
    }

    public static ActionFactory<Pair<Entity, Entity>> createBientityActionFactory () {
        return new ActionFactory<>(Powers.identifier("transfer_status"),
            new SerializableData()
                .add(STATUS_TYPE_LABEL, SerializableDataTypes.STRINGS, null)
                .add(EFFECTS_LABEL, SerializableDataTypes.STRINGS, null)
                .add(CLEANSE_SELF_LABEL, SerializableDataTypes.BOOLEAN)
                .add(AMPLIFIER_MODIFIER_LABEL, SerializableDataTypes.DOUBLE, 1.0)
                .add(DURATION_MODIFIER_LABEL, SerializableDataTypes.DOUBLE, 1.0),
            StatusTransferAction::action);
    }
}
