package com.provismet.proviorigins.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.provismet.proviorigins.content.registries.StatusEffects;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.LivingEntity;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    // Don't render sky if the player has Sleep.
    @Inject(at=@At("RETURN"), method="hasBlindnessOrDarkness", cancellable=true)
    private void checkForSleep (Camera camera, CallbackInfoReturnable<Boolean> cir) {
        if (camera.getFocusedEntity() instanceof LivingEntity living) {
            if (living.hasStatusEffect(StatusEffects.SLEEP)) cir.setReturnValue(true);
        }
    }
}
