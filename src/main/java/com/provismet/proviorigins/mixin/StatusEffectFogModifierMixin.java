package com.provismet.proviorigins.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.provismet.proviorigins.content.StatusEffects.StatusEffects;

import net.minecraft.entity.LivingEntity;

@Mixin(targets = {"net.minecraft.client.render.BackgroundRenderer$StatusEffectFogModifier"})
public interface StatusEffectFogModifierMixin {
    @Inject(at=@At("RETURN"), method="shouldApply", cancellable=true)
    private void applySleepFogModifier (LivingEntity entity, float tickDelta, CallbackInfoReturnable<Boolean> cir) {
        if (entity.hasStatusEffect(StatusEffects.SLEEP)) cir.setReturnValue(true);
    }
}
