package com.provismet.personalOrigins.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.provismet.personalOrigins.powers.PreventPotionCloudPower;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class PreventPotionCloudMixin extends Entity {
    protected PreventPotionCloudMixin (EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at=@At("RETURN"), method="isAffectedBySplashPotions", cancellable=true)
    private void canBeSplashed (CallbackInfoReturnable<Boolean> cir) {
        List<PreventPotionCloudPower> noPots = PowerHolderComponent.getPowers((LivingEntity)(Object)this, PreventPotionCloudPower.class);
        cir.setReturnValue(noPots.isEmpty());
    }
}
