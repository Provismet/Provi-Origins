package com.provismet.personalOrigins.powers;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.provismet.personalOrigins.ProviOriginsMain;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.ActiveCooldownPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

@SuppressWarnings("rawtypes")
public class ActiveItemPower extends ActiveCooldownPower {
    private final Consumer<Entity> entityAction;
    private final Predicate<ItemStack> itemCondition;
    private final Predicate<Entity> consumeCondition;
    private final int consumeAmount;
    private final boolean shouldSwingArm;

    public ActiveItemPower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender, Consumer<Entity> entityAction, Predicate<ItemStack> itemCondition, Predicate<Entity> consumeCondition, int consumeAmount, boolean shouldSwingArm) {
        super(type, entity, cooldownDuration, hudRender, entityAction);
        this.entityAction = entityAction;
        this.itemCondition = itemCondition;
        this.consumeCondition = consumeCondition;
        this.consumeAmount = consumeAmount;
        this.shouldSwingArm = shouldSwingArm;
    }

    @Override
    public void onUse () {
        if (this.canUse()) {
            ItemStack mainhand = this.entity.getEquippedStack(EquipmentSlot.MAINHAND);
            ItemStack offhand = this.entity.getEquippedStack(EquipmentSlot.OFFHAND);

            if (this.itemCondition.test(mainhand) && mainhand.getCount() >= this.consumeAmount) {
                perform(Hand.MAIN_HAND, mainhand);
            }
            else if (this.itemCondition.test(offhand) && offhand.getCount() >= this.consumeAmount) {
                // If multiple of this power exist on one entity, avoid double using items.
                List<ActiveItemPower> activeItemPowers = PowerHolderComponent.getPowers(this.entity, ActiveItemPower.class);
                for (ActiveItemPower powerInstance : activeItemPowers) {
                    if (powerInstance.itemCondition.test(mainhand)) return;
                }
                perform(Hand.OFF_HAND, offhand);
            }
        }
    }

    private void perform (Hand hand, ItemStack itemStack) {
        if (this.shouldSwingArm) this.entity.swingHand(hand, true);
        if ((this.consumeCondition == null || this.consumeCondition.test(this.entity)) &&
            !(this.entity instanceof PlayerEntity && ((PlayerEntity)this.entity).isCreative()))
                itemStack.decrement(this.consumeAmount);
        use();
        this.entityAction.accept(this.entity);
    }
    
    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(ProviOriginsMain.identifier("active_item"),
        new SerializableData()
            .add(Powers.ENTITY_ACTION, ApoliDataTypes.ENTITY_ACTION)
            .add(Powers.ITEM_CONDITION, ApoliDataTypes.ITEM_CONDITION)
            .add("consume_condition", ApoliDataTypes.ENTITY_CONDITION, null)
            .add("consume_amount", SerializableDataTypes.INT, 1)
            .add("swing_arm", SerializableDataTypes.BOOLEAN, true)
            .add(Powers.COOLDOWN, SerializableDataTypes.INT, 1)
            .add(Powers.HUD_RENDER, ApoliDataTypes.HUD_RENDER, HudRender.DONT_RENDER)
            .add(Powers.KEY, ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY, new Active.Key()),
            data -> (type, player) -> {
                ActiveItemPower power = new ActiveItemPower(type, player,
                    data.getInt(Powers.COOLDOWN),
                    data.get(Powers.HUD_RENDER),
                    data.get(Powers.ENTITY_ACTION),
                    data.get(Powers.ITEM_CONDITION),
                    data.get("consume_condition"),
                    data.getInt("consume_amount"),
                    data.getBoolean("swing_arm"));
                power.setKey((Active.Key)data.get(Powers.KEY));
                return power;
        }).allowCondition();
    }
}
