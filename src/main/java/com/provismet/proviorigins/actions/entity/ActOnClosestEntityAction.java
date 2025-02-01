package com.provismet.proviorigins.actions.entity;

import java.util.List;

import com.provismet.proviorigins.registries.POEntityActionTypes;
import com.provismet.proviorigins.utility.constants.FieldNames;

import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.BiEntityAction;
import io.github.apace100.apoli.action.context.EntityActionContext;
import io.github.apace100.apoli.action.type.EntityActionType;
import io.github.apace100.apoli.condition.BiEntityCondition;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ActOnClosestEntityAction extends EntityActionType {
    private final double distance;
    private final BiEntityAction action;
    private final BiEntityCondition condition;

    public static final TypedDataObjectFactory<ActOnClosestEntityAction> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(FieldNames.DISTANCE, SerializableDataTypes.DOUBLE)
            .add(FieldNames.BIENTITY_ACTION, BiEntityAction.DATA_TYPE)
            .add(FieldNames.BIENTITY_CONDITION, BiEntityCondition.DATA_TYPE),
        data -> new ActOnClosestEntityAction(
            data.getDouble(FieldNames.DISTANCE),
            data.get(FieldNames.BIENTITY_ACTION),
            data.get(FieldNames.BIENTITY_CONDITION)
        ),
        (actionType, data) -> data.instance()
            .set(FieldNames.DISTANCE, actionType.distance)
            .set(FieldNames.BIENTITY_ACTION, actionType.action)
            .set(FieldNames.BIENTITY_CONDITION, actionType.condition)
    );

    public ActOnClosestEntityAction (double distance, BiEntityAction action, BiEntityCondition condition) {
        this.distance = distance;
        this.action = action;
        this.condition = condition;
    }

    @Override
    public void accept (EntityActionContext context) {
        List<Entity> others = context.entity().getWorld().getOtherEntities(context.entity(), context.entity().getBoundingBox().expand(distance));
        Entity closest = null;

        for (Entity other : others) {
            if (this.condition.test(context.entity(), other) && (closest == null || context.entity().distanceTo(other) < context.entity().distanceTo(closest))) closest = other;
        }

        if (closest != null) this.action.execute(context.entity(), closest);
    }

    @Override
    public @NotNull ActionConfiguration<ActOnClosestEntityAction> getConfig () {
        return POEntityActionTypes.ACT_ON_CLOSEST_ENTITY;
    }
}
