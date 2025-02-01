package com.provismet.proviorigins.powers;

import java.util.List;
import java.util.Optional;

import com.provismet.proviorigins.registries.POPowerTypes;
import com.provismet.proviorigins.utility.ConditionUtil;
import com.provismet.proviorigins.utility.constants.FieldNames;
import io.github.apace100.apoli.action.EntityAction;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.condition.ItemCondition;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.ActiveCooldownPowerType;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.apoli.util.keybinding.KeyBindingReference;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class ActiveItemPower extends ActiveCooldownPowerType {
    private static final String CONSUME_CONDITION_LABEL = "consume_condition";
    private static final String CONSUME_AMOUNT_LABEL = "consume_amount";
    private static final String SWING_ARM_LABEL = "swing_arm";

    private final EntityAction entityAction;
    private final ItemCondition itemCondition;
    private final Optional<EntityCondition> consumeCondition;
    private final int consumeAmount;
    private final boolean shouldSwingArm;

    public static final TypedDataObjectFactory<ActiveItemPower> DATA_FACTORY = PowerType.createConditionedDataFactory(
        new SerializableData()
            .add(FieldNames.ENTITY_ACTION, EntityAction.DATA_TYPE)
            .add(FieldNames.ITEM_CONDITION, ItemCondition.DATA_TYPE)
            .add(CONSUME_CONDITION_LABEL, EntityCondition.DATA_TYPE.optional(), Optional.empty())
            .add(CONSUME_AMOUNT_LABEL, SerializableDataTypes.INT, 1)
            .add(SWING_ARM_LABEL, SerializableDataTypes.BOOLEAN, true)
            .add(FieldNames.COOLDOWN, SerializableDataTypes.INT, 1)
            .add(FieldNames.HUD_RENDER, ApoliDataTypes.HUD_RENDER, HudRender.DONT_RENDER)
            .add(FieldNames.KEY, ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY, KeyBindingReference.NONE),
        (data, condition) -> new ActiveItemPower(
            data.getInt(FieldNames.COOLDOWN),
            data.get(FieldNames.HUD_RENDER),
            data.get(FieldNames.KEY),
            data.get(FieldNames.ENTITY_ACTION),
            data.get(FieldNames.ITEM_CONDITION),
            data.get(CONSUME_CONDITION_LABEL),
            data.getInt(CONSUME_AMOUNT_LABEL),
            data.getBoolean(SWING_ARM_LABEL),
            condition
        ),
        (powerType, data) -> data.instance()
            .set(FieldNames.ENTITY_ACTION, powerType.entityAction)
            .set(FieldNames.ITEM_CONDITION, powerType.itemCondition)
            .set(CONSUME_CONDITION_LABEL, powerType.consumeCondition)
            .set(CONSUME_AMOUNT_LABEL, powerType.consumeAmount)
            .set(SWING_ARM_LABEL, powerType.shouldSwingArm)
            .set(FieldNames.COOLDOWN, powerType.getCooldown())
            .set(FieldNames.HUD_RENDER, powerType.getRenderSettings())
            .set(FieldNames.KEY, powerType.getKey())
    );

    public ActiveItemPower (int cooldownDuration, HudRender hudRender, KeyBindingReference key, EntityAction entityAction, ItemCondition itemCondition, Optional<EntityCondition> consumeCondition, int consumeAmount, boolean shouldSwingArm, Optional<EntityCondition> condition) {
        super(hudRender, cooldownDuration, key, condition);
        this.entityAction = entityAction;
        this.itemCondition = itemCondition;
        this.consumeCondition = consumeCondition;
        this.consumeAmount = consumeAmount;
        this.shouldSwingArm = shouldSwingArm;
    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig () {
        return POPowerTypes.ACTIVE_ITEM;
    }

    @Override
    public void onUse () {
        if (this.canUse()) {
            World world = this.getHolder().getWorld();
            ItemStack mainhand = this.getHolder().getEquippedStack(EquipmentSlot.MAINHAND);
            ItemStack offhand = this.getHolder().getEquippedStack(EquipmentSlot.OFFHAND);

            if (this.itemCondition.test(world, mainhand) && mainhand.getCount() >= this.consumeAmount) {
                this.perform(Hand.MAIN_HAND, mainhand);
            }
            else if (this.itemCondition.test(world, offhand) && offhand.getCount() >= this.consumeAmount) {
                // If multiple of this power exist on one entity, avoid double using items.
                List<ActiveItemPower> activeItemPowers = PowerHolderComponent.getPowerTypes(this.getHolder(), ActiveItemPower.class);
                for (ActiveItemPower powerInstance : activeItemPowers) {
                    if (powerInstance.itemCondition.test(world, mainhand) && this.getKey().equals(powerInstance.getKey())) return;
                }
                this.perform(Hand.OFF_HAND, offhand);
            }
        }
    }

    private void perform (Hand hand, ItemStack itemStack) {
        if (this.shouldSwingArm) this.getHolder().swingHand(hand, true);
        if (ConditionUtil.emptyOrTest(this.consumeCondition, this.getHolder()) && !(this.getHolder() instanceof PlayerEntity player && player.isCreative())) {
            itemStack.decrement(this.consumeAmount);
        }
        this.use();
        this.entityAction.execute(this.getHolder());
    }
}
