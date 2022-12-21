package com.provismet.personalOrigins.mixin;

import java.util.function.BiConsumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.provismet.personalOrigins.powers.ActionOnDetectVibration;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.event.listener.EntityGameEventHandler;

@Mixin(Entity.class)
public abstract class ActionOnDetectGameEventMixin {
    @Inject(at=@At("HEAD"), method="updateEventHandler")
    public void addEventHandler (BiConsumer<EntityGameEventHandler<?>, ServerWorld> callback, CallbackInfo info) {
        if ((Object)this instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)(Object)this;

            if (!(living.world instanceof ServerWorld)) return;
            ServerWorld world = (ServerWorld)living.world;
    
            for (ActionOnDetectVibration power : PowerHolderComponent.getPowers(living, ActionOnDetectVibration.class)) {
                callback.accept(power.eventHandler, world);
            }
        }
    }
}
