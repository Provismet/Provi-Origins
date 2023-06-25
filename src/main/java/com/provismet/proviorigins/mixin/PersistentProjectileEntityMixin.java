package com.provismet.proviorigins.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.provismet.proviorigins.powers.EvadeProjectilesPower;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin extends ProjectileEntity {
    protected PersistentProjectileEntityMixin (EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
    
    // Evade Projectile Power
    @Inject(at=@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", shift=At.Shift.AFTER), method="onEntityHit", cancellable=true)
    private void ignoreProjectile (EntityHitResult hitResult, CallbackInfo info) {
        if (hitResult.getEntity() instanceof LivingEntity living) {
            if (PowerHolderComponent.hasPower(living, EvadeProjectilesPower.class)) {
                info.cancel();
            }
        }
    }
}
