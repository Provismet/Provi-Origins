package com.provismet.proviorigins.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.provismet.proviorigins.extras.Tags;
import com.provismet.proviorigins.powers.EvadeProjectilesPower;
import com.provismet.proviorigins.powers.PreventBreathingPower;
import com.provismet.proviorigins.powers.PreventPotionCloudPower;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    public abstract int getNextAirOnLand(int air);

    @Shadow
    public abstract int getNextAirUnderwater(int air);

    protected LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    // Prevent Breathing Power
    private void applyAir (DamageSource source) {
        this.setAir(this.getNextAirUnderwater(this.getAir()) - this.getNextAirOnLand(0));
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
        if (target.hasStatusEffect(com.provismet.proviorigins.content.registries.StatusEffects.UNTARGETABLE)) cir.setReturnValue(false);
    }

    // Apply double damage from sleep.
    @Inject(at=@At("RETURN"), method="modifyAppliedDamage", cancellable=true)
    private void applySleepDamage (DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (!source.isIn(DamageTypeTags.BYPASSES_EFFECTS)) {
            LivingEntity livingEntity = (LivingEntity)(Object)this;

            if (livingEntity.hasStatusEffect(com.provismet.proviorigins.content.registries.StatusEffects.SLEEP)) {
                livingEntity.removeStatusEffect(com.provismet.proviorigins.content.registries.StatusEffects.SLEEP);
                cir.setReturnValue(cir.getReturnValue() * 2);
            }
        }
    }

    // Prevent sleeping entities from jumping.
    @Inject(at=@At("HEAD"), method="jump", cancellable=true)
    private void preventSleepJump (CallbackInfo info) {
        LivingEntity livingEntity = (LivingEntity)(Object)this;
        if (livingEntity.hasStatusEffect(com.provismet.proviorigins.content.registries.StatusEffects.SLEEP)) info.cancel();
    }

    // Allow custom damage sources to disable shields when blocked.
    @Inject(at=@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;damageShield(F)V", shift=At.Shift.AFTER), method="damage")
    private void disableShield (DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        if (source.isIn(Tags.DamageTypes.DISABLES_SHIELDS) && (LivingEntity)(Object)this instanceof PlayerEntity player) {
            player.disableShield(true);
        }
    }

    // Force certain damage sources to always be blocked when the player has their shield raised.
    @Inject(at=@At("RETURN"), method="blockedByShield", cancellable=true)
    private void alwaysBlock (DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity living = (LivingEntity)(Object)this;
        if (living.isBlocking() && source.isIn(Tags.DamageTypes.ALWAYS_BLOCK)) cir.setReturnValue(true);
    }

    // Evade Projectile Power
    @Inject(at=@At("HEAD"), method="damage", cancellable=true)
    private void actOnProjectile (DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity living = (LivingEntity)(Object)this;
        if (source.isIn(DamageTypeTags.IS_PROJECTILE) && PowerHolderComponent.hasPower(living, EvadeProjectilesPower.class) && !living.isBlocking()) {
            cir.setReturnValue(true); // This reports that damage was dealt, but prevents it from actually happening.
        }
    }

    @Inject(at=@At("RETURN"), method="canHaveStatusEffect", cancellable=true)
    private void cannotHaveSleepAndAlert (StatusEffectInstance effectInstance, CallbackInfoReturnable<Boolean> cir) {
        if (effectInstance.getEffectType() == com.provismet.proviorigins.content.registries.StatusEffects.SLEEP &&
            ((LivingEntity)(Object)this).hasStatusEffect(com.provismet.proviorigins.content.registries.StatusEffects.ALERT)) cir.setReturnValue(false);
    }
}
