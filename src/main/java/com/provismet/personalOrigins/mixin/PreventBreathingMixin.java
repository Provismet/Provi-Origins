package com.provismet.personalOrigins.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.provismet.personalOrigins.powers.PreventBreathing;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;

public final class PreventBreathingMixin {
    @Mixin(LivingEntity.class)
    public interface LivingEntityAccessor {
        @Invoker("getNextAirOnLand")
        public int invokeNextAirOnLand(int air);

        @Invoker("getNextAirUnderwater")
        public int invokeNextAirUnderwater(int air);
    }

    @Mixin(LivingEntity.class)
    public static abstract class PreventBreathingLivingEntity extends Entity {
        protected PreventBreathingLivingEntity (EntityType<?> entityType, World world) {
            super(entityType, world);
        }
        
        @Inject(at=@At("TAIL"), method="tick")
        private void tick (CallbackInfo info) {
            List<PreventBreathing> noBreathes = PowerHolderComponent.getPowers((LivingEntity)(Object)this, PreventBreathing.class);
            if (!noBreathes.isEmpty() && !((LivingEntity)(Object)this).hasStatusEffect(StatusEffects.WATER_BREATHING)) {
                this.setAir(((LivingEntityAccessor)this).invokeNextAirUnderwater(this.getAir()) - ((LivingEntityAccessor)this).invokeNextAirOnLand(0));
                if (this.getAir() <= -20) {
                    this.setAir(0);

                    this.damage(noBreathes.get(0).getDamageSource(), 2.0f);
                }
            }
        }
    }
}
