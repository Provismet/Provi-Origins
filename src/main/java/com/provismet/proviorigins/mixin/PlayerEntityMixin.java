package com.provismet.proviorigins.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.provismet.proviorigins.content.registries.StatusEffects;
import com.provismet.proviorigins.powers.ActionOnCriticalHitPower;
import com.provismet.proviorigins.powers.ActionOnGainExpPower;
import com.provismet.proviorigins.powers.ActionOnGainLevelPower;
import com.provismet.proviorigins.powers.PreventCriticalHitPower;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    // Action On Critical Hit
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addCritParticles(Lnet/minecraft/entity/Entity;)V", shift = At.Shift.AFTER), method = "attack(Lnet/minecraft/entity/Entity;)V")
    private void applyCritEffects (Entity target, CallbackInfo info) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        for (ActionOnCriticalHitPower critEffectPower : PowerHolderComponent.getPowers(player, ActionOnCriticalHitPower.class)) {
            critEffectPower.tryAction(target);
        }
    }
    
    // Prevent Critical Hit Power
    @ModifyVariable(at = @At("STORE"), method = "attack(Lnet/minecraft/entity/Entity;)V", ordinal = 2)
    private boolean preventCrits (boolean shouldCrit) {
        if (shouldCrit) {
            PlayerEntity player = (PlayerEntity)(Object)this;
            if (PowerHolderComponent.hasPower(player, PreventCriticalHitPower.class)) return false;
        }
        return shouldCrit;
    }

    // Cannot break blocks when inflicted with Sleep.
    @Inject(at=@At("RETURN"), method="getBlockBreakingSpeed", cancellable=true)
    private void preventBlockBreakWhenSleeping (BlockState block, CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player.hasStatusEffect(StatusEffects.SLEEP)) cir.setReturnValue(0f);
    }

    // Action On Gain Level Power
    @Inject(at=@At("HEAD"), method="addExperienceLevels")
    private void executeOnLevel (int levels, CallbackInfo info) {
        List<ActionOnGainLevelPower> powers = PowerHolderComponent.getPowers((PlayerEntity)(Object)this, ActionOnGainLevelPower.class);
        for (ActionOnGainLevelPower instance : powers) {
            instance.execute(levels);
        }
    }

    // Action On Gain Experience Power
    @Inject(at=@At("HEAD"), method="addExperience")
    private void executeOnExp (int experience, CallbackInfo info) {
        List<ActionOnGainExpPower> powers = PowerHolderComponent.getPowers((PlayerEntity)(Object)this, ActionOnGainExpPower.class);
        for (ActionOnGainExpPower instance : powers) {
            instance.execute(experience);
        }
    }
}
