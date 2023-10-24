package com.provismet.proviorigins.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.ImmutableMap;
import com.provismet.proviorigins.content.entities.CloneEntity;
import com.provismet.proviorigins.content.entities.renderers.CloneEntityRenderer;
import com.provismet.proviorigins.content.registries.Entities;

import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.impl.client.rendering.RegistrationHelperImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.resource.ResourceManager;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
    @Shadow
    private @Final ItemRenderer itemRenderer;

    @Shadow
    private @Final TextRenderer textRenderer;

    @Shadow
    private @Final EntityModelLoader modelLoader;

    @Shadow
    private @Final HeldItemRenderer heldItemRenderer;

    @Shadow
    private @Final BlockRenderManager blockRenderManager;

    @Unique
    private Map<String, EntityRenderer<CloneEntity>> cloneRenderers = ImmutableMap.of();

    @SuppressWarnings("unchecked")
    @Inject(at=@At("HEAD"), method="getRenderer", cancellable=true)
    public <T extends Entity> void getCloneRenderer (T entity, CallbackInfoReturnable<EntityRenderer<? super T>> cir) {
        if (entity instanceof CloneEntity clone) {
            if (clone.isOwned()) {
                String modelType = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(clone.getOwnerUuid()).getSkinTextures().model().getName();
                EntityRenderer<? super T> renderer = (EntityRenderer<? super T>)cloneRenderers.get(modelType);
                cir.setReturnValue(renderer);
            }
            else {
                cir.setReturnValue((EntityRenderer<? super T>)cloneRenderers.get("default"));
            }
        }
    }

    @Inject(at = @At("HEAD"), method="reload")
    public void reload (ResourceManager manager, CallbackInfo info) {
        Context context = new Context((EntityRenderDispatcher)(Object)this, this.itemRenderer, this.blockRenderManager, this.heldItemRenderer, manager, this.modelLoader, this.textRenderer);
        this.cloneRenderers = ImmutableMap.of(
            "default", createCloneEntityRenderer(context, false),
            "slim", createCloneEntityRenderer(context, true)
        );
    }

    @SuppressWarnings({"unchecked", "ConstantConditions", "rawtypes"})
    private static CloneEntityRenderer<CloneEntity> createCloneEntityRenderer (Context context, boolean slimArms) {
        CloneEntityRenderer<CloneEntity> renderer = new CloneEntityRenderer<>(context, slimArms);
        LivingEntityRendererAccessor accessor = (LivingEntityRendererAccessor)renderer;
        LivingEntityFeatureRendererRegistrationCallback.EVENT.invoker()
            .registerRenderers(Entities.CLONE, renderer, new RegistrationHelperImpl(accessor::invokeAddFeature), context);
        return renderer;
    }
}
