package com.provismet.personalOrigins.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.provismet.personalOrigins.powers.PreventBreathingPower;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
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

        private void applyAir (DamageSource source) {
            this.setAir(((LivingEntityAccessor)this).invokeNextAirUnderwater(this.getAir()) - ((LivingEntityAccessor)this).invokeNextAirOnLand(0));
            if (this.getAir() <= -20) {
                this.setAir(0);

                this.damage(source, 2.0f);
            }
        }
        
        @Inject(at=@At("TAIL"), method="tick")
        private void tick (CallbackInfo info) {
            LivingEntity living = (LivingEntity)(Object)this;
            List<PreventBreathingPower> noBreathes = PowerHolderComponent.getPowers(living, PreventBreathingPower.class);
            if (!noBreathes.isEmpty()) {
                if (!living.hasStatusEffect(StatusEffects.WATER_BREATHING)) applyAir(noBreathes.get(0).getDamageSource());
                else {
                    for (PreventBreathingPower powerInstance : noBreathes) {
                        if (!powerInstance.respectWaterBreathing) {
                            applyAir(powerInstance.getDamageSource());
                            break;
                        }
                    }
                }
            }
        }
    }
}
