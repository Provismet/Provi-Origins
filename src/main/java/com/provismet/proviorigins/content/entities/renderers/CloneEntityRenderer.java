package com.provismet.proviorigins.content.entities.renderers;

import com.provismet.proviorigins.content.entities.CloneEntity;
import com.provismet.proviorigins.content.entities.models.CloneEntityModel;
import com.provismet.proviorigins.content.registries.POModelLayerRegistry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.StuckArrowsFeatureRenderer;
import net.minecraft.client.render.entity.feature.StuckStingersFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CloneEntityRenderer<T extends CloneEntity> extends BipedEntityRenderer<T, CloneEntityModel<T>> {
    private static final Identifier DEFAULT_STEVE = Identifier.of("minecraft", "textures/entity/steve.png");

    public CloneEntityRenderer (Context context, boolean slimArms) {
        super(context, new CloneEntityModel<>(context.getPart(slimArms ? POModelLayerRegistry.CLONE_SLIM_MODEL_LAYER : POModelLayerRegistry.CLONE_MODEL_LAYER), slimArms), 0.5f);

        BipedEntityModel<T> inner = new BipedEntityModel<>(context.getPart(slimArms ? POModelLayerRegistry.CLONE_SLIM_INNER_LAYER : POModelLayerRegistry.CLONE_INNER_LAYER));
        BipedEntityModel<T> outer = new BipedEntityModel<>(context.getPart(slimArms ? POModelLayerRegistry.CLONE_SLIM_OUTER_LAYER : POModelLayerRegistry.CLONE_OUTER_LAYER));
        this.addFeature(new ArmorFeatureRenderer<>(this, inner, outer, context.getModelManager()));
        this.addFeature(new StuckArrowsFeatureRenderer<>(context, this));
        this.addFeature(new StuckStingersFeatureRenderer<>(this));
    }

    @Override
    public Identifier getTexture (T clone) {
        if (!clone.isOwned()) return DEFAULT_STEVE;

        ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
        PlayerListEntry entry;

        if (networkHandler != null) entry = networkHandler.getPlayerListEntry(clone.getOwnerUuid());
        else entry = null;
        
        if (entry == null) return DEFAULT_STEVE;
        else return entry.getSkinTextures().texture();
    }

    @Override
    public void render (T clone, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        if (clone.isSitting()) {
            matrixStack.push();
            matrixStack.translate(0, -0.5, 0);
            super.render(clone, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
            matrixStack.pop();
        }
        else super.render(clone, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
    }

    @Override
    protected void scale (T clone, MatrixStack matrices, float amount) {
        final float AMOUNT = 0.9375f;
        matrices.scale(AMOUNT, AMOUNT, AMOUNT);
    }
}
