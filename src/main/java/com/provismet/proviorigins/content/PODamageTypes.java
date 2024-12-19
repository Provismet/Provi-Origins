package com.provismet.proviorigins.content;

import com.provismet.lilylib.container.DamageTypeContainer;
import com.provismet.proviorigins.ProviOriginsMain;

import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;

public class PODamageTypes {
    public static final DamageTypeContainer BAD_DIMENSION = new DamageTypeContainer(
        ProviOriginsMain.identifier("bad_dimension"),
        new DamageType("bad_dimension", 0.1f)
    );

    public static final DamageTypeContainer COMPOST = new DamageTypeContainer(
        ProviOriginsMain.identifier("compost"),
        new DamageType("compost", 0.1f)
    );

    public static final DamageTypeContainer CRYSTAL_BEAM = new DamageTypeContainer(
        ProviOriginsMain.identifier("crystal_beam"),
        new DamageType("crystal_beam", DamageScaling.NEVER, 0.1f)
    );

    public static final DamageTypeContainer FRESHWATER = new DamageTypeContainer(
        ProviOriginsMain.identifier("freshwater"),
        new DamageType("freshwater", 0.1f)
    );

    public static final DamageTypeContainer KRAKEN_SOUL_STEAL = new DamageTypeContainer(
        ProviOriginsMain.identifier("kraken_soul_steal"),
        new DamageType("kraken_soul_steal", 0.1f)
    );

    public static final DamageTypeContainer VOID_CORRUPTION = new DamageTypeContainer(
        ProviOriginsMain.identifier("void_corrupted"),
        new DamageType("void_corrupted", 0.1f)
    );

    public static void bootstrap (Registerable<DamageType> registerable) {
        registerable.register(BAD_DIMENSION.getKey(), BAD_DIMENSION.getDamageType());
        registerable.register(COMPOST.getKey(), COMPOST.getDamageType());
        registerable.register(CRYSTAL_BEAM.getKey(), CRYSTAL_BEAM.getDamageType());
        registerable.register(FRESHWATER.getKey(), FRESHWATER.getDamageType());
        registerable.register(KRAKEN_SOUL_STEAL.getKey(), KRAKEN_SOUL_STEAL.getDamageType());
        registerable.register(VOID_CORRUPTION.getKey(), VOID_CORRUPTION.getDamageType());
    }
}
