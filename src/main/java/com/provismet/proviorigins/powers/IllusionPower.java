package com.provismet.proviorigins.powers;

import java.util.ArrayList;
import java.util.List;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.extras.RightAngledTriangle;

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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@SuppressWarnings("rawtypes")
public class IllusionPower extends Power {
    private static final String DISTANCE_LABEL = "distance";
    private static final String COUNT_LABEL = "layers";
    private static final String MIRROR_TYPE_LABEL = "illusion_spread";

    private final double distance;
    private final int count;
    private final SpreadType spreadType;
    private final PowerType<?> resourceName;
    private Power resource;

    private List<Polar> randomSpreads;

    public IllusionPower (PowerType<?> type, LivingEntity entity, double distance, int count, String spreadType, PowerType<?> resourceType) {
        super(type, entity);
        this.distance = distance;
        this.count = count;
        this.resourceName = resourceType;
        this.randomSpreads = new ArrayList<>();

        SpreadType temp;
        try {
            temp = SpreadType.valueOf(spreadType.toUpperCase());
        }
        catch (Exception e) {
            temp = null;
            ProviOriginsMain.LOGGER.error(String.format("Power \"%s\" (skipping) contained illegal value for illusion_spread: \"%s\"", this.type.getIdentifier().toString(), spreadType));
        }
        this.spreadType = temp;
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

    public Vec3d[] getOffsets (Vec3d viewer) {
        switch (this.spreadType) {
            case LINE:
                return getOffsetsLine(viewer);
            case SPREAD:
                return getOffsetsSpread();
            default:
                return new Vec3d[0];
        }
    }

    public Vec3d[] getOffsetsLine (Vec3d viewer) {
        Vec3d[] offsets = new Vec3d[this.count * 2];
        final double distanceModifier = this.getDistance();

        RightAngledTriangle triangle = new RightAngledTriangle(viewer, this.entity.getPos());
        double cos = triangle.cosine();
        double sin = triangle.sine();

        for (int i = 0; i < this.count; ++i) {
            double distance = distanceModifier * (i + 1);
            offsets[i] = new Vec3d(distance * -cos, 0, distance * sin);
            offsets[i + this.count] = new Vec3d(distance * cos, 0, distance * -sin);
        }
        return offsets;
    }

    public Vec3d[] getOffsetsSpread () { // TODO: Random spread that works like the Illusioner mob.
        if (this.randomSpreads.size() == 0) {
            for (int i = 0; i < this.count; ++i) {
                this.randomSpreads.add(new Polar(
                    MathHelper.nextFloat(this.entity.getRandom(), 0, MathHelper.PI * 2),
                    MathHelper.nextDouble(this.entity.getRandom(), 0.2, 1.0)
                ));
            }
        }

        Vec3d[] offsets = new Vec3d[this.count];
        for (int i = 0; i < this.count; ++i) {
            float angle = this.randomSpreads.get(i).angle;
            double distance = this.randomSpreads.get(i).distance * this.getDistance();
            offsets[i] = new Vec3d(MathHelper.cos(angle) * distance, 0, -MathHelper.sin(angle) * distance);
        }

        return offsets;
    }

    @Override
    public boolean isActive() {
        boolean output = super.isActive();
        if (!output && this.randomSpreads.size() != 0) this.randomSpreads.clear();
        return output;
    }

    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("illusioner_mirror"),
            new SerializableData()
                .add(DISTANCE_LABEL, SerializableDataTypes.DOUBLE)
                .add(COUNT_LABEL, SerializableDataTypes.INT)
                .add(MIRROR_TYPE_LABEL, SerializableDataTypes.STRING)
                .add(Powers.RESOURCE, ApoliDataTypes.POWER_TYPE, null),
            data -> (type, player) -> new IllusionPower(type, player,
                data.getDouble(DISTANCE_LABEL),
                data.getInt(COUNT_LABEL),
                data.getString(MIRROR_TYPE_LABEL),
                data.get(Powers.RESOURCE)
            )
        ).allowCondition();
    }

    public enum SpreadType {
        LINE,
        SPREAD;
    }
    
    private static class Polar {
        public final float angle;
        public final double distance;

        public Polar (float angle, double distance) {
            this.angle = angle;
            this.distance = distance;
        }
    }
}
