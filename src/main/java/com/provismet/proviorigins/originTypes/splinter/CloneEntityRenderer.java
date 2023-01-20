package com.provismet.proviorigins.originTypes.splinter;

import com.provismet.proviorigins.extras.ModelLayerRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
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
    private static final Identifier DEFAULT_STEVE = new Identifier("minecraft", "textures/entity/steve.png");

    public CloneEntityRenderer (Context context, boolean slimArms) {
        super(context, new CloneEntityModel<>(context.getPart(slimArms ? ModelLayerRegistry.CLONE_SLIM_MODEL_LAYER : ModelLayerRegistry.CLONE_MODEL_LAYER), slimArms), 0.5f);

        BipedEntityModel<T> inner = new BipedEntityModel<>(context.getPart(slimArms ? ModelLayerRegistry.CLONE_SLIM_INNER_LAYER : ModelLayerRegistry.CLONE_INNER_LAYER));
        BipedEntityModel<T> outer = new BipedEntityModel<>(context.getPart(slimArms ? ModelLayerRegistry.CLONE_SLIM_OUTER_LAYER : ModelLayerRegistry.CLONE_OUTER_LAYER));
        this.addFeature(new ArmorFeatureRenderer<>(this, inner, outer));
        this.addFeature(new StuckArrowsFeatureRenderer<>(context, this));
        this.addFeature(new StuckStingersFeatureRenderer<>(this));
    }

    @Override
    public Identifier getTexture (T clone) {
        if (!clone.isOwned()) return DEFAULT_STEVE;
        PlayerListEntry entry = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(clone.getOwnerUuid());
        
        if (entry == null) return DEFAULT_STEVE;
        else return entry.getSkinTexture();
    }

    @Override
    public void render (T clone, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        this.getModel().sneaking = clone.isSitting();
        super.render(clone, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
    }

    @Override
    protected void scale (T clone, MatrixStack matrices, float amount) {
        final float AMOUNT = 0.9375f;
        matrices.scale(AMOUNT, AMOUNT, AMOUNT);
    }
}
