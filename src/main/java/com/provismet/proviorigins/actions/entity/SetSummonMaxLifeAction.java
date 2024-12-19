package com.provismet.proviorigins.actions.entity;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.extras.Temporary;
import com.provismet.proviorigins.registries.POEntityActionTypes;
import com.provismet.proviorigins.utility.constants.FieldNames;

import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.type.EntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class SetSummonMaxLifeAction extends EntityActionType {
    private final int maxLife;

    public static final TypedDataObjectFactory<SetSummonMaxLifeAction> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(FieldNames.AMOUNT, SerializableDataTypes.INT),
        data -> new SetSummonMaxLifeAction(
            data.getInt(FieldNames.AMOUNT)
        ),
        (actionType, data) -> data.instance()
            .set(FieldNames.AMOUNT, actionType.maxLife)
    );

    public SetSummonMaxLifeAction (int maxLife) {
        this.maxLife = maxLife;
    }

    @Override
    protected void execute (Entity entity) {
        if (entity instanceof Temporary summonable) summonable.setMaxLifetime(this.maxLife);
        else ProviOriginsMain.LOGGER.warn("Attempted to use set_summon_max_life_ticks action on a non-temporary entity.");
    }

    @Override
    public @NotNull ActionConfiguration<SetSummonMaxLifeAction> getConfig () {
        return POEntityActionTypes.SET_SUMMON_MAX_LIFE;
    }
}
