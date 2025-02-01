package com.provismet.proviorigins.powers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.provismet.lilylib.util.MoreMath.RightAngledTriangle;
import com.provismet.proviorigins.ProviOriginsMain;

import com.provismet.proviorigins.registries.POPowerTypes;
import com.provismet.proviorigins.utility.constants.FieldNames;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.PowerReference;
import io.github.apace100.apoli.power.type.CooldownPowerType;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.power.type.VariableIntPowerType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class IllusionPower extends PowerType {
    private static final String DISTANCE_LABEL = "distance";
    private static final String COUNT_LABEL = "layers";
    private static final String MIRROR_TYPE_LABEL = "illusion_spread";

    private final double distance;
    private final int count;
    private final SpreadType spreadType;
    private final Optional<PowerReference> resourceName;
    private PowerType resourcePowerType = null;

    private final List<Polar> randomSpreads;

    public static final TypedDataObjectFactory<IllusionPower> DATA_FACTORY = PowerType.createConditionedDataFactory(
        new SerializableData()
            .add(DISTANCE_LABEL, SerializableDataTypes.DOUBLE)
            .add(COUNT_LABEL, SerializableDataTypes.INT)
            .add(MIRROR_TYPE_LABEL, SerializableDataTypes.STRING)
            .add(FieldNames.RESOURCE, ApoliDataTypes.POWER_REFERENCE.optional(), Optional.empty()),
        (data, condition) -> new IllusionPower(
            data.getDouble(DISTANCE_LABEL),
            data.getInt(COUNT_LABEL),
            data.getString(MIRROR_TYPE_LABEL),
            data.get(FieldNames.RESOURCE),
            condition
        ),
        (powerType, data) -> data.instance()
            .set(DISTANCE_LABEL, powerType.distance)
            .set(COUNT_LABEL, powerType.count)
            .set(MIRROR_TYPE_LABEL, powerType.spreadType.toString())
            .set(FieldNames.RESOURCE, powerType.resourceName)
    );

    public IllusionPower (double distance, int count, String spreadType, Optional<PowerReference> resourceType, Optional<EntityCondition> condition) {
        super(condition);
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
            ProviOriginsMain.LOGGER.error("Power \"{}\" (skipping) contained illegal value for illusion_spread: \"{}\"", this.getPower().getId().toString(), spreadType);
        }
        this.spreadType = temp;
    }
    
    private double getDistance () {
        if (this.resourcePowerType == null && this.resourceName.isPresent()) {
            this.resourcePowerType = this.resourceName.get().getNullablePowerType(this.getHolder());
        }

        return switch (this.resourcePowerType) {
            case VariableIntPowerType rPower -> this.distance * (double) rPower.getValue();
            case CooldownPowerType cPower -> this.distance * (double) cPower.getRemainingTicks();
            case null, default -> this.distance;
        };
    }

    public Vec3d[] getOffsets (Vec3d viewer) {
        return switch (this.spreadType) {
            case LINE -> getOffsetsLine(viewer);
            case SPREAD -> getOffsetsSpread();
            default -> new Vec3d[0];
        };
    }

    public Vec3d[] getOffsetsLine (Vec3d viewer) {
        Vec3d[] offsets = new Vec3d[this.count * 2];
        final double distanceModifier = this.getDistance();

        RightAngledTriangle triangle = new RightAngledTriangle(viewer, this.getHolder().getPos());
        double cos = triangle.cosine();
        double sin = triangle.sine();

        for (int i = 0; i < this.count; ++i) {
            double distance = distanceModifier * (i + 1);
            offsets[i] = new Vec3d(distance * -cos, 0, distance * sin);
            offsets[i + this.count] = new Vec3d(distance * cos, 0, distance * -sin);
        }
        return offsets;
    }

    public Vec3d[] getOffsetsSpread () {
        if (this.randomSpreads.isEmpty()) {
            for (int i = 0; i < this.count; ++i) {
                this.randomSpreads.add(new Polar(
                    MathHelper.nextFloat(this.getHolder().getRandom(), 0, MathHelper.PI * 2),
                    MathHelper.nextDouble(this.getHolder().getRandom(), 0.2, 1.0)
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
    public @NotNull PowerConfiguration<?> getConfig () {
        return POPowerTypes.ILLUSION;
    }

    @Override
    public boolean isActive() {
        boolean output = super.isActive();
        if (!output && !this.randomSpreads.isEmpty()) this.randomSpreads.clear();
        return output;
    }

    public enum SpreadType {
        LINE,
        SPREAD;
    }

    private record Polar (float angle, double distance) {}
}
