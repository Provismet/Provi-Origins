package com.provismet.proviorigins.actions.entity;

import java.util.Optional;

import com.provismet.proviorigins.registries.POEntityActionTypes;
import com.provismet.proviorigins.utility.ConditionUtil;
import com.provismet.proviorigins.utility.constants.FieldNames;

import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.BiEntityAction;
import io.github.apace100.apoli.action.type.EntityActionType;
import io.github.apace100.apoli.condition.BiEntityCondition;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Tameable;
import org.jetbrains.annotations.NotNull;

public class ActOnOwnerAction extends EntityActionType {
    private final BiEntityAction action;
    private final Optional<BiEntityCondition> condition;

    public static final TypedDataObjectFactory<ActOnOwnerAction> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(FieldNames.BIENTITY_ACTION, BiEntityAction.DATA_TYPE)
            .add(FieldNames.BIENTITY_CONDITION, BiEntityCondition.DATA_TYPE.optional(), Optional.empty()),
        data -> new ActOnOwnerAction(
            data.get(FieldNames.BIENTITY_ACTION),
            data.get(FieldNames.BIENTITY_CONDITION)
        ),
        (actionType, data) -> data.instance()
            .set(FieldNames.BIENTITY_ACTION, actionType.action)
            .set(FieldNames.BIENTITY_CONDITION, actionType.condition)
    );

    public ActOnOwnerAction (BiEntityAction action, Optional<BiEntityCondition> condition) {
        this.action = action;
        this.condition = condition;
    }

    @Override
    protected void execute (Entity entity) {
        if (entity instanceof Tameable tameable && tameable.getOwner() != null) {
            if (ConditionUtil.emptyOrTest(this.condition, entity, tameable.getOwner())) this.action.execute(entity, tameable.getOwner());
        }
    }

    @Override
    public @NotNull ActionConfiguration<ActOnOwnerAction> getConfig () {
        return POEntityActionTypes.ACT_ON_OWNER;
    }
}
