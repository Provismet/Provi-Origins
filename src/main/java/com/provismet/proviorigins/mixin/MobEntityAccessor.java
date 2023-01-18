package com.provismet.proviorigins.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Mixin(MobEntity.class)
public interface MobEntityAccessor {
    @Invoker("disablePlayerShield")
    public void invokeDisablePlayerShield (PlayerEntity player, ItemStack mobStack, ItemStack playerStack);
}
