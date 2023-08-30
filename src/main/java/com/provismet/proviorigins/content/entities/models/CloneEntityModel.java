package com.provismet.proviorigins.content.entities.models;

import com.provismet.proviorigins.content.entities.CloneEntity;
import com.provismet.proviorigins.content.registries.ModelLayerRegistry;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;

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

    @Override
    public void animateModel (T clone, float limbAngle, float limbDistance, float tickDelta) {
        this.rightArmPose = BipedEntityModel.ArmPose.EMPTY;
        this.leftArmPose = BipedEntityModel.ArmPose.EMPTY;
        ItemStack itemStackMain = clone.getStackInHand(Hand.MAIN_HAND);
        ItemStack itemStackOff = clone.getStackInHand(Hand.OFF_HAND);
        if (itemStackMain.isOf(Items.BOW) && clone.isAttacking()) {
            if (clone.getMainArm() == Arm.RIGHT) this.rightArmPose = BipedEntityModel.ArmPose.BOW_AND_ARROW;
            else this.leftArmPose = BipedEntityModel.ArmPose.BOW_AND_ARROW;
        }
        else {
            if (!itemStackMain.isEmpty()) {
                if (clone.getMainArm() == Arm.RIGHT) this.rightArmPose = BipedEntityModel.ArmPose.ITEM;
                else this.leftArmPose = BipedEntityModel.ArmPose.ITEM;
            }
            if (!itemStackOff.isEmpty()) {
                if (clone.getMainArm() == Arm.RIGHT) this.leftArmPose = BipedEntityModel.ArmPose.ITEM;
                else this.rightArmPose = BipedEntityModel.ArmPose.ITEM;
            }
        }
        super.animateModel(clone, limbAngle, limbDistance, tickDelta);
    }

    @Override
    public void setAngles (T clone, float f, float g, float h, float i, float j) {
        this.riding = clone.isSitting();

        super.setAngles(clone, f, g, h, i, j);

        if (clone.isHolding(Items.CROSSBOW)) {
            if (clone.isCharging()) CrossbowPosing.charge(this.rightArm, this.leftArm, clone, true);
            else CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, true);
        }
    }
}
