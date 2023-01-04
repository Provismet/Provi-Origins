package com.provismet.personalOrigins.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.provismet.personalOrigins.powers.AdjustPassengerHeightPower;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.LivingEntity;

public final class AdjustPassengerHeightMixin {
    @Mixin(Entity.class)
    public interface EntityAccessor {
        @Accessor
        EntityDimensions getDimensions();
    }

    @Mixin(Entity.class)
    public static abstract class AdjustPassengerHeightEntity {
        @Inject(at=@At("HEAD"), method="getMountedHeightOffset()D", cancellable=true)
        private void adjustHeight (CallbackInfoReturnable<Double> cir) {
            if ((Object)this instanceof LivingEntity) {
                LivingEntity living = (LivingEntity)(Object)this;
                List<AdjustPassengerHeightPower> powers = PowerHolderComponent.getPowers(living, AdjustPassengerHeightPower.class);

                if (!powers.isEmpty()) {
                    Double offset = powers.get(0).offset;
                    EntityDimensions dimensions = ((EntityAccessor)this).getDimensions();

                    cir.setReturnValue(dimensions.height * 0.75 + offset);
                }
            }
        }
    }
}
