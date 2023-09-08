package com.provismet.proviorigins.content.entities.renderers;

import com.provismet.proviorigins.content.entities.MinionEntity;
import com.provismet.proviorigins.content.entities.models.MinionEntityModel;
import com.provismet.proviorigins.content.registries.ModelLayerRegistry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(value=EnvType.CLIENT)
public class MinionEntityRenderer<T extends MinionEntity> extends MobEntityRenderer<T, MinionEntityModel<T>> {
    public MinionEntityRenderer  (Context context) {
        super(context, new MinionEntityModel<>(context.getPart(ModelLayerRegistry.MINION_MODEL_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture (MinionEntity entity) {
        return entity.getTexture();
    }

    @Override
    protected void scale (T minion, MatrixStack matrices, float amount) {
        float scaleAmount = minion.getScale();
        matrices.scale(scaleAmount, scaleAmount, scaleAmount);
        super.scale(minion, matrices, amount);
    }
}
