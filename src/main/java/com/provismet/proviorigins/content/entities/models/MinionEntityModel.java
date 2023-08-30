package com.provismet.proviorigins.content.entities.models;

import com.provismet.proviorigins.content.entities.MinionEntity;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class MinionEntityModel<T extends MinionEntity> extends EntityModel<T> {
	private final ModelPart bone;
	
	public MinionEntityModel (ModelPart root) {
		this.bone = root.getChild("main");
	}
	
	public static TexturedModelData getTexturedModelData () {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bone = modelPartData.addChild("main", ModelPartBuilder.create().uv(24, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
		.uv(24, 16).cuboid(-3.0F, -7.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F))
		.uv(24, 28).cuboid(-2.0F, -6.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
		.uv(0, 12).cuboid(0.0F, -10.0F, -6.0F, 0.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		bone.addChild("flat3_r1", ModelPartBuilder.create().uv(0, -12).cuboid(0.0F, -6.0F, -6.0F, 0.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, 0.0F, 0.0F, 0.0F, 1.5708F));
		bone.addChild("flat2_r1", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -10.0F, -6.0F, 0.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
		
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(MinionEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bone.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}