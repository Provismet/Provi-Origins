package com.provismet.proviorigins.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.provismet.proviorigins.powers.EvadeProjectilesPower;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin extends Entity {
    protected ProjectileEntityMixin (EntityType<?> type, World world) {
        super(type, world);
    }
    
    @Inject(at=@At("HEAD"), method="onEntityHit")
    private void actionOnEvade (EntityHitResult hitResult, CallbackInfo info) {
        if (hitResult.getEntity() instanceof LivingEntity living) {
            List<EvadeProjectilesPower> powers = PowerHolderComponent.getPowers(living, EvadeProjectilesPower.class);
            for (EvadeProjectilesPower powerInstance : powers) {
                powerInstance.executeAction((ProjectileEntity)(Object)this);
            }
        }
    }
}
