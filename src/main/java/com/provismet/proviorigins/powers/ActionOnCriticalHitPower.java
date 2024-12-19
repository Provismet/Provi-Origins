package com.provismet.proviorigins.powers;

import java.util.Optional;

import com.provismet.proviorigins.registries.POPowerTypes;
import com.provismet.proviorigins.utility.ConditionUtil;
import com.provismet.proviorigins.utility.constants.FieldNames;
import io.github.apace100.apoli.action.BiEntityAction;
import io.github.apace100.apoli.condition.BiEntityCondition;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ActionOnCriticalHitPower extends PowerType {
    private final BiEntityAction bientityAction;
    private final Optional<BiEntityCondition> bientityCondition;

    public ActionOnCriticalHitPower(BiEntityAction bientityAction, Optional<BiEntityCondition> bientityCondition, Optional<EntityCondition> condition) {
        super(condition);
        this.bientityAction = bientityAction;
        this.bientityCondition = bientityCondition;
    }

    public void tryAction (Entity target) {
        if (ConditionUtil.emptyOrTest(this.bientityCondition, this.getHolder(), target)) {
            bientityAction.execute(this.getHolder(), target);
        }
    }

    public static final TypedDataObjectFactory<ActionOnCriticalHitPower> DATA_FACTORY = PowerType.createConditionedDataFactory(
        new SerializableData()
            .add(FieldNames.BIENTITY_ACTION, BiEntityAction.DATA_TYPE)
            .add(FieldNames.BIENTITY_CONDITION, BiEntityCondition.DATA_TYPE.optional(), Optional.empty()),
        (data, condition) -> new ActionOnCriticalHitPower(
            data.get(FieldNames.BIENTITY_ACTION),
            data.get(FieldNames.BIENTITY_CONDITION),
            condition
        ),
        (powerType, data) -> data.instance()
            .set(FieldNames.BIENTITY_ACTION, powerType.bientityAction)
            .set(FieldNames.BIENTITY_CONDITION, powerType.bientityCondition)
    );

    @Override
    public @NotNull PowerConfiguration<?> getConfig () {
        return POPowerTypes.ACTION_ON_CRITICAL_HIT;
    }
}
