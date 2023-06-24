package com.provismet.proviorigins.actions;

import java.util.function.Consumer;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.originTypes.splinter.CloneEntity;
import com.provismet.proviorigins.originTypes.splinter.Splinter;
import com.provismet.proviorigins.powers.Powers;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;

public class SummonCloneAction {
    private static final String CAN_SIT_LABEL = "can_sit";
    private static final String CAN_ATTACK_LABEL = "can_attack";
    private static final String FOLLOW_OWNER_LABEL = "follow_owner";
    private static final String INHERIT_EQUIPMENT_LABEL = "inherit_equipment";
    private static final String INHERIT_ENCHANTMENTS_LABEL = "inherit_enchantments";
    
    public static void action (SerializableData.Instance data, Entity entity) {
        if (entity instanceof PlayerEntity player) {
            final boolean canSit = data.getBoolean(CAN_SIT_LABEL);
            final boolean canAttack = data.getBoolean(CAN_ATTACK_LABEL);
            final boolean followOwner = data.getBoolean(FOLLOW_OWNER_LABEL);
            final boolean inheritsEquipment = data.getBoolean(INHERIT_EQUIPMENT_LABEL);
            final boolean inheritsEnchantments = data.getBoolean(INHERIT_ENCHANTMENTS_LABEL);
            final Consumer<Pair<Entity, Entity>> bientityAction = data.get(Powers.BIENTITY_ACTION);

            CloneEntity clone = summon(player, canSit, followOwner, canAttack, inheritsEquipment, inheritsEnchantments);
            if (bientityAction != null && clone != null) bientityAction.accept(new Pair<>(player, clone)); 
        }
        else ProviOriginsMain.LOGGER.warn("Attempted to summon clone of invalid entity. Only Players are compatible with this action type.");
    }

    private static CloneEntity summon (PlayerEntity player, boolean canSit, boolean followOwner, boolean canAttack, boolean inheritsEquipment, boolean inheritsEnchantments) {
        if (player.getWorld() instanceof ServerWorld serverWorld) {
            CloneEntity clone = new CloneEntity(Splinter.CLONE, serverWorld);
            clone.setCanSit(canSit);
            clone.setCanAttack(canAttack);
            clone.setFollowOwner(followOwner);

            clone.setPosition(player.getPos());

            clone.initialize(serverWorld, serverWorld.getLocalDifficulty(player.getBlockPos()), SpawnReason.REINFORCEMENT, null, null);
            clone.setCustomName(player.getName());
            clone.setOwnerUUID(player.getUuid());
            clone.setCanPickUpLoot(false);
            clone.setPersistent();

            serverWorld.spawnEntity(clone);

            if (inheritsEquipment) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (!player.getEquippedStack(slot).isEmpty()) {
                        clone.setEquipmentDropChance(slot, 0f);

                        ItemStack copy = new ItemStack(player.getEquippedStack(slot).getItem(), player.getEquippedStack(slot).getCount());
                        if (inheritsEnchantments) {
                            EnchantmentHelper.set(EnchantmentHelper.get(player.getEquippedStack(slot)), copy);
                        }
                        clone.equipStack(slot, copy);
                    }
                }
            }
            clone.updateWeaponGoals();
            return clone;
        }
        return null;
    }

    public static ActionFactory<Entity> createActionFactory () {
        return new ActionFactory<>(Powers.identifier("summon_clone"),
            new SerializableData()
                .add(CAN_SIT_LABEL, SerializableDataTypes.BOOLEAN, true)
                .add(CAN_ATTACK_LABEL, SerializableDataTypes.BOOLEAN, true)
                .add(FOLLOW_OWNER_LABEL, SerializableDataTypes.BOOLEAN, true)
                .add(INHERIT_EQUIPMENT_LABEL, SerializableDataTypes.BOOLEAN, true)
                .add(INHERIT_ENCHANTMENTS_LABEL, SerializableDataTypes.BOOLEAN, true)
                .add(Powers.BIENTITY_ACTION, ApoliDataTypes.BIENTITY_ACTION, null),
            SummonCloneAction::action);
    }
}
