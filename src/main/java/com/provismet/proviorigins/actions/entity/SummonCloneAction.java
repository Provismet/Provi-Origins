package com.provismet.proviorigins.actions.entity;

import java.util.Optional;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.content.entities.CloneEntity;
import com.provismet.proviorigins.content.registries.POEntities;
import com.provismet.proviorigins.registries.POEntityActionTypes;
import com.provismet.proviorigins.utility.constants.FieldNames;

import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.BiEntityAction;
import io.github.apace100.apoli.action.type.EntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;

public class SummonCloneAction extends EntityActionType {
    private static final String CAN_SIT_LABEL = "can_sit";
    private static final String CAN_ATTACK_LABEL = "can_attack";
    private static final String FOLLOW_OWNER_LABEL = "follow_owner";
    private static final String INHERIT_EQUIPMENT_LABEL = "inherit_equipment";
    private static final String INHERIT_ENCHANTMENTS_LABEL = "inherit_enchantments";

    private final boolean canSit;
    private final boolean canAttack;
    private final boolean followOwner;
    private final boolean inheritEquipment;
    private final boolean inheritEnchantments;
    private final Optional<BiEntityAction> action;

    public static final TypedDataObjectFactory<SummonCloneAction> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(CAN_SIT_LABEL, SerializableDataTypes.BOOLEAN, true)
            .add(CAN_ATTACK_LABEL, SerializableDataTypes.BOOLEAN, true)
            .add(FOLLOW_OWNER_LABEL, SerializableDataTypes.BOOLEAN, true)
            .add(INHERIT_EQUIPMENT_LABEL, SerializableDataTypes.BOOLEAN, true)
            .add(INHERIT_ENCHANTMENTS_LABEL, SerializableDataTypes.BOOLEAN, true)
            .add(FieldNames.BIENTITY_ACTION, BiEntityAction.DATA_TYPE.optional(), Optional.empty()),
        data -> new SummonCloneAction(
            data.getBoolean(CAN_SIT_LABEL),
            data.getBoolean(CAN_ATTACK_LABEL),
            data.getBoolean(FOLLOW_OWNER_LABEL),
            data.getBoolean(INHERIT_EQUIPMENT_LABEL),
            data.getBoolean(INHERIT_ENCHANTMENTS_LABEL),
            data.get(FieldNames.BIENTITY_ACTION)
        ),
        (actionType, data) -> data.instance()
            .set(CAN_SIT_LABEL, actionType.canSit)
            .set(CAN_ATTACK_LABEL, actionType.canAttack)
            .set(FOLLOW_OWNER_LABEL, actionType.followOwner)
            .set(INHERIT_EQUIPMENT_LABEL, actionType.inheritEquipment)
            .set(INHERIT_ENCHANTMENTS_LABEL, actionType.inheritEnchantments)
            .set(FieldNames.BIENTITY_ACTION, actionType.action)
    );

    public SummonCloneAction (boolean canSit, boolean canAttack, boolean followOwner, boolean inheritEquipment, boolean inheritEnchantments, Optional<BiEntityAction> action) {
        this.canSit = canSit;
        this.canAttack = canAttack;
        this.followOwner = followOwner;
        this.inheritEquipment = inheritEquipment;
        this.inheritEnchantments = inheritEnchantments;
        this.action = action;
    }

    @Override
    public void execute (Entity entity) {
        if (entity instanceof PlayerEntity player) {
            CloneEntity clone = summon(player, this.canSit, this.followOwner, this.canAttack, this.inheritEquipment, this.inheritEnchantments);
            if (clone != null) this.action.ifPresent(biEntityAction -> biEntityAction.execute(player, clone));
        }
        else ProviOriginsMain.LOGGER.warn("Attempted to summon clone of invalid entity. Only Players are compatible with this action type.");
    }

    private static CloneEntity summon (PlayerEntity player, boolean canSit, boolean followOwner, boolean canAttack, boolean inheritsEquipment, boolean inheritsEnchantments) {
        if (player.getWorld() instanceof ServerWorld serverWorld) {
            CloneEntity clone = new CloneEntity(POEntities.CLONE, serverWorld);
            clone.setCanSit(canSit);
            clone.setCanAttack(canAttack);
            clone.setFollowOwner(followOwner);

            clone.refreshPositionAndAngles(player.getX(), player.getY(), player.getZ(), player.getHeadYaw(), 0);

            clone.initialize(serverWorld, serverWorld.getLocalDifficulty(player.getBlockPos()), SpawnReason.REINFORCEMENT, null);
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
                            EnchantmentHelper.set(copy, EnchantmentHelper.getEnchantments(player.getEquippedStack(slot)));
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

    @Override
    public @NotNull ActionConfiguration<SummonCloneAction> getConfig () {
        return POEntityActionTypes.SUMMON_CLONE;
    }
}
