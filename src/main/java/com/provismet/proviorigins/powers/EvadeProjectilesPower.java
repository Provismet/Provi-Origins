package com.provismet.proviorigins.powers;

import java.util.Optional;

import com.provismet.proviorigins.registries.POPowerTypes;
import com.provismet.proviorigins.utility.ConditionUtil;
import com.provismet.proviorigins.utility.constants.FieldNames;
import io.github.apace100.apoli.action.BiEntityAction;
import io.github.apace100.apoli.action.EntityAction;
import io.github.apace100.apoli.condition.BiEntityCondition;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.projectile.ProjectileEntity;
import org.jetbrains.annotations.NotNull;

public class EvadeProjectilesPower extends PowerType {
    private static final String PROJECTILE_ACTION_LABEL = "projectile_action";
    private static final String PROJECTILE_CONDITION_LABEL = "projectile_condition";

    private final Optional<EntityAction> entityAction;
    private final Optional<BiEntityAction> bientityAction;
    private final Optional<BiEntityCondition> bientityCondition;
    private final Optional<BiEntityAction> projectileAction;
    private final Optional<BiEntityCondition> projectileCondition;

    public static final TypedDataObjectFactory<EvadeProjectilesPower> DATA_FACTORY = PowerType.createConditionedDataFactory(
        new SerializableData()
            .add(FieldNames.ENTITY_ACTION, EntityAction.DATA_TYPE.optional(), Optional.empty())
            .add(FieldNames.BIENTITY_ACTION, BiEntityAction.DATA_TYPE.optional(), Optional.empty())
            .add(FieldNames.BIENTITY_CONDITION, BiEntityCondition.DATA_TYPE.optional(), Optional.empty())
            .add(PROJECTILE_ACTION_LABEL, BiEntityAction.DATA_TYPE.optional(), Optional.empty())
            .add(PROJECTILE_CONDITION_LABEL, BiEntityCondition.DATA_TYPE.optional(), Optional.empty()),
        (data, condition) -> new EvadeProjectilesPower(
            data.get(FieldNames.ENTITY_ACTION),
            data.get(FieldNames.BIENTITY_ACTION),
            data.get(FieldNames.BIENTITY_CONDITION),
            data.get(PROJECTILE_ACTION_LABEL),
            data.get(PROJECTILE_CONDITION_LABEL),
            condition
        ),
        (powerType, data) -> data.instance()
            .set(FieldNames.ENTITY_ACTION, powerType.entityAction)
            .set(FieldNames.BIENTITY_ACTION, powerType.bientityAction)
            .set(FieldNames.BIENTITY_CONDITION, powerType.bientityCondition)
            .set(PROJECTILE_ACTION_LABEL, powerType.projectileAction)
            .set(PROJECTILE_CONDITION_LABEL, powerType.projectileCondition)
    );

    public EvadeProjectilesPower(Optional<EntityAction> entityAction, Optional<BiEntityAction> bientityAction, Optional<BiEntityCondition> bientityCondition, Optional<BiEntityAction> projectileAction, Optional<BiEntityCondition> projectileCondition, Optional<EntityCondition> condition) {
        super(condition);
        this.entityAction = entityAction;
        this.bientityAction = bientityAction;
        this.bientityCondition = bientityCondition;
        this.projectileAction = projectileAction;
        this.projectileCondition = projectileCondition;
    }
    
    public void executeAction (ProjectileEntity projectile) {
        this.entityAction.ifPresent(action -> action.execute(this.getHolder()));

        if (projectile.getOwner() != null) {
            if (ConditionUtil.emptyOrTest(this.bientityCondition, projectile.getOwner(), this.getHolder())) {
                this.bientityAction.ifPresent(action -> action.execute(projectile.getOwner(), this.getHolder()));
            }
        }

        if (ConditionUtil.emptyOrTest(this.projectileCondition, projectile, this.getHolder())) {
            this.projectileAction.ifPresent(action -> action.execute(projectile, this.getHolder()));
        }
    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig () {
        return POPowerTypes.EVADE_PROJECTILES;
    }
}
