package com.provismet.proviorigins.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.provismet.proviorigins.extras.RightAngledTriangle;
import com.provismet.proviorigins.powers.IllusionPower;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    protected PlayerEntityRendererMixin (Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }
    
    @SuppressWarnings("resource")
    @Inject(at=@At("TAIL"), method="render")
    public void addMirrors (AbstractClientPlayerEntity livingEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo info) {
        List<IllusionPower> mirrors = PowerHolderComponent.getPowers(livingEntity, IllusionPower.class);
        if (!mirrors.isEmpty()) {
            IllusionPower power = mirrors.get(0);
            double[] offsets = power.getOffsets();

            // The trigonometry that makes this work is too tedious to write in a comment, it's just a lot of right-angles and this is merely the solved problem.
            Vec3d cameraPos = MinecraftClient.getInstance().gameRenderer.getCamera().getPos();
            RightAngledTriangle triangle = new RightAngledTriangle(cameraPos, livingEntity.getPos());
            double cos = triangle.cosine();
            double sin = triangle.sine();

            for (double offset : offsets) {
                matrixStack.push();
                matrixStack.translate(offset * -cos, 0, offset * sin);
                super.render(livingEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
                matrixStack.pop();

                matrixStack.push();
                matrixStack.translate(offset * cos, 0, offset * -sin);
                super.render(livingEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
                matrixStack.pop();
            }
        }
    }
}
