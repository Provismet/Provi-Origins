package com.provismet.proviorigins.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.provismet.proviorigins.powers.IllusionPower;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;

@Mixin(MobEntityRenderer.class)
public abstract class MobEntityRendererMixin extends LivingEntityRenderer<MobEntity, EntityModel<MobEntity>> {
    protected MobEntityRendererMixin (Context ctx, EntityModel<MobEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @SuppressWarnings("resource")
    @Inject(at=@At("HEAD"), method="render")
    public void addMirrors (MobEntity livingEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo info) {
        List<IllusionPower> mirrors = PowerHolderComponent.getPowers(livingEntity, IllusionPower.class);
        if (!mirrors.isEmpty()) {
            IllusionPower power = mirrors.get(0);
            Vec3d[] offsets = power.getOffsets(MinecraftClient.getInstance().gameRenderer.getCamera().getPos());

            for (Vec3d offset : offsets) {
                matrixStack.push();
                matrixStack.translate(offset.getX(), offset.getY(), offset.getZ());
                super.render(livingEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
                matrixStack.pop();
            }
        }
    }
}
