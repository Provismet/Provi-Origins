package com.provismet.proviorigins.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import com.provismet.proviorigins.powers.ModifyDarknessPulsePower;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.entity.LivingEntity;

@Environment(value=EnvType.CLIENT)
@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {
    @ModifyVariable(method="update", at=@At("STORE"), ordinal=3)
    private float modifyDarknessPulse (float original) {
        if (MinecraftClient.getInstance().getCameraEntity() instanceof LivingEntity living) {
            List<ModifyDarknessPulsePower> powers = PowerHolderComponent.getPowers(living, ModifyDarknessPulsePower.class);
            if (!powers.isEmpty()) {
                return powers.get(0).apply(original);
            }
        }
        return original;
    }
}
