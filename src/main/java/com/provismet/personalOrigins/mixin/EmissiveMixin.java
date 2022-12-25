package com.provismet.personalOrigins.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.provismet.personalOrigins.powers.Emissive;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

@Mixin(EntityRenderer.class)
public abstract class EmissiveMixin {
    @Inject(at=@At("HEAD"), method="getBlockLight", cancellable=true)
    public void makeEmissive (Entity entity, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)entity;
            List<Emissive> emissives = PowerHolderComponent.getPowers(living, Emissive.class);
            if (!emissives.isEmpty()) {
                int max = emissives.get(0).light;
                for (Emissive p : emissives) {
                    if (p.light > max) max = p.light;
                }
                cir.setReturnValue(max);
            }
        }
    }
}
