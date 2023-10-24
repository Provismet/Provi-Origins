package com.provismet.proviorigins.mixin;

import java.util.List;
import java.util.function.BiConsumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.listener.EntityGameEventHandler;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    private EntityDimensions dimensions;

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
    @ModifyVariable(at=@At("STORE"), ordinal=0, method="updatePassengerPosition(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity$PositionUpdater;)V")
    private Vec3d adjustHeight (Vec3d position) {
        if ((Object)this instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)(Object)this;
            List<ModifyPassengerHeightPower> powers = PowerHolderComponent.getPowers(living, ModifyPassengerHeightPower.class);

            if (!powers.isEmpty()) {
                double offsetAdd = powers.get(0).offsetAdditive;
                double offsetMul = powers.get(0).offsetMultiplicative;

                return position.multiply(1.0, offsetMul, 1.0).add(0.0, offsetAdd, 0.0);
            }
        }
        return position;
    }

    // Occlude Vibration Power
    @Inject(at=@At("HEAD"), method="occludeVibrationSignals", cancellable=true)
    public void removeVibrations (CallbackInfoReturnable<Boolean> cir) {
        if ((Object)this instanceof LivingEntity livingEntity) {
            if (!PowerHolderComponent.getPowers(livingEntity, OccludeVibrationsPower.class).isEmpty()) cir.setReturnValue(true);
        }
    }
}
