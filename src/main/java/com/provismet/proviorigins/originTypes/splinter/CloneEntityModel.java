package com.provismet.proviorigins.originTypes.splinter;

import com.provismet.proviorigins.extras.ModelLayerRegistry;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

public class CloneEntityModel<T extends CloneEntity> extends PlayerEntityModel<T> {
    public CloneEntityModel (ModelPart root) {
        this(root, false);
    }

    public CloneEntityModel (ModelPart root, boolean slimArms) {
        super(root, slimArms);
    }
    
    public static TexturedModelData getTexturedModelData () {
        return TexturedModelData.of(PlayerEntityModel.getTexturedModelData(Dilation.NONE, false), 64, 64);
    }

    public static TexturedModelData getSlimTexturedModelData () {
        return TexturedModelData.of(PlayerEntityModel.getTexturedModelData(Dilation.NONE, true), 64, 64);
    }

    public static TexturedModelData getTexturedModelDataInner () {
        return TexturedModelData.of(PlayerEntityModel.getModelData(ModelLayerRegistry.HAT_DILATION, 0.0f), 64, 32);
    }

    public static TexturedModelData getTexturedModelDataOuter () {
        return TexturedModelData.of(PlayerEntityModel.getModelData(ModelLayerRegistry.ARMOR_DILATION, 0.0f), 64, 32);
    }
}
