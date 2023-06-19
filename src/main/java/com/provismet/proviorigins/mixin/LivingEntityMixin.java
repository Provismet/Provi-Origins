package com.provismet.proviorigins.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.provismet.proviorigins.powers.PreventBreathingPower;
import com.provismet.proviorigins.powers.PreventPotionCloudPower;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;

public final class LivingEntityMixin {
    @Mixin(LivingEntity.class)
    public interface LivingEntityAccessor {
        @Invoker("getNextAirOnLand")
        public int invokeNextAirOnLand(int air);

        @Invoker("getNextAirUnderwater")
        public int invokeNextAirUnderwater(int air);
    }

    @Mixin(LivingEntity.class)
    public static abstract class LivingEntityMixins extends Entity {
        protected LivingEntityMixins(EntityType<?> type, World world) {
            super(type, world);
        }

        // Prevent Breathing Power
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
        
        // Prevent Potion Cloud Power
        @Inject(at=@At("RETURN"), method="isAffectedBySplashPotions", cancellable=true)
        private void canBeSplashed (CallbackInfoReturnable<Boolean> cir) {
            List<PreventPotionCloudPower> noPots = PowerHolderComponent.getPowers((LivingEntity)(Object)this, PreventPotionCloudPower.class);
            cir.setReturnValue(noPots.isEmpty());
        }

        // Untargetable Status Effect
        @Inject(at=@At("RETURN"), method="canTarget(Lnet/minecraft/entity/LivingEntity;)Z", cancellable=true)
        private void applyUntargetable (LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
            if (target.hasStatusEffect(com.provismet.proviorigins.content.StatusEffects.StatusEffects.UNTARGETABLE)) cir.setReturnValue(false);
        }
    }
}
