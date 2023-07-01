package com.provismet.proviorigins.mixin;

import java.util.List;
import java.util.function.BiConsumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.provismet.proviorigins.powers.ActionOnDetectVibrationPower;
import com.provismet.proviorigins.powers.ModifyPassengerHeightPower;
import com.provismet.proviorigins.powers.OccludeVibrationsPower;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.event.listener.EntityGameEventHandler;

public final class EntityMixin {
    @Mixin(Entity.class)
    public interface EntityAccessor {
        @Accessor
        EntityDimensions getDimensions();
    }

    @Mixin(Entity.class)
    public static abstract class EntityMixins {
        // Action On Detect GameEvent Power
        @Inject(at=@At("HEAD"), method="updateEventHandler")
        public void addEventHandler (BiConsumer<EntityGameEventHandler<?>, ServerWorld> callback, CallbackInfo info) {
            if ((Object)this instanceof LivingEntity) {
                LivingEntity living = (LivingEntity)(Object)this;

                if (!(living.getWorld() instanceof ServerWorld)) return;
                ServerWorld world = (ServerWorld)living.getWorld();
        
                for (ActionOnDetectVibrationPower power : PowerHolderComponent.getPowers(living, ActionOnDetectVibrationPower.class)) {
                    callback.accept(power.eventHandler, world);
                }
            }
        }

        // Adjust Passenger Height Power
        @Inject(at=@At("HEAD"), method="getMountedHeightOffset()D", cancellable=true)
        private void adjustHeight (CallbackInfoReturnable<Double> cir) {
            if ((Object)this instanceof LivingEntity) {
                LivingEntity living = (LivingEntity)(Object)this;
                List<ModifyPassengerHeightPower> powers = PowerHolderComponent.getPowers(living, ModifyPassengerHeightPower.class);

                if (!powers.isEmpty()) {
                    Double offsetAdd = powers.get(0).offsetAdditive;
                    Double offsetMul = powers.get(0).offsetMultiplicative;
                    EntityDimensions dimensions = ((EntityAccessor)this).getDimensions();

                    cir.setReturnValue(dimensions.height * 0.75 * offsetMul + offsetAdd);
                }
            }
        }

        // Occlude Vibration Power
        @Inject(at=@At("HEAD"), method="occludeVibrationSignals", cancellable=true)
        public void removeVibrations (CallbackInfoReturnable<Boolean> cir) {
            if ((Object)this instanceof LivingEntity livingEntity) {
                if (!PowerHolderComponent.getPowers(livingEntity, OccludeVibrationsPower.class).isEmpty()) cir.setReturnValue(true);
            }
        }
    }
}
