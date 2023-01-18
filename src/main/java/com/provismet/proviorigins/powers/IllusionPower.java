package com.provismet.proviorigins.powers;

import com.provismet.proviorigins.ProviOriginsMain;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.CooldownPower;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.VariableIntPower;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;

@SuppressWarnings("rawtypes")
public class IllusionPower extends Power {
    private static final String DISTANCE_LABEL = "distance";
    private static final String COUNT_LABEL = "layers";

    private final double distance;
    private final int count;
    private final PowerType<?> resourceName;
    private Power resource;

    public IllusionPower (PowerType<?> type, LivingEntity entity, double distance, int count, PowerType<?> resourceType) {
        super(type, entity);
        this.distance = distance;
        this.count = count;
        this.resourceName = resourceType;

        if (resourceType != null) {
            Power power = PowerHolderComponent.KEY.get(entity).getPower(resourceType);

            if (power instanceof VariableIntPower || power instanceof CooldownPower) this.resource = power;
            else this.resource = null;
        }
        else this.resource = null;
    }

    private void getResource () {
        if (this.resource == null && this.resourceName != null) {
            Power power = PowerHolderComponent.KEY.get(entity).getPower(this.resourceName);

            if (power instanceof VariableIntPower || power instanceof CooldownPower) this.resource = power;
            else this.resource = null;
        }
    }
    
    private double getDistance () {
        this.getResource();

        if (this.resource == null) {
            return this.distance;
        }
        else if (this.resource instanceof VariableIntPower rPower) {
            return this.distance * (double)rPower.getValue();
        }
        else if (this.resource instanceof CooldownPower cPower) {
            return this.distance * (double)cPower.getRemainingTicks();
        }
        else return this.distance;
    }

    public double[] getOffsets () {
        double[] offsets = new double[this.count];
        final double distanceModifier = this.getDistance();
        for (int i = 0; i < this.count; ++i) {
            offsets[i] = distanceModifier * (i + 1);
        }
        return offsets;
    }

    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(ProviOriginsMain.identifier("illusioner_mirror"),
            new SerializableData()
                .add(DISTANCE_LABEL, SerializableDataTypes.DOUBLE)
                .add(COUNT_LABEL, SerializableDataTypes.INT)
                .add(Powers.RESOURCE, ApoliDataTypes.POWER_TYPE, null),
            data -> (type, player) -> new IllusionPower(type, player,
                data.getDouble(DISTANCE_LABEL),
                data.getInt(COUNT_LABEL),
                data.get(Powers.RESOURCE)
            )
        ).allowCondition();
    }
}
