package com.provismet.proviorigins.utility.tags;

import com.provismet.proviorigins.ProviOriginsMain;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class POFluidTags {
    public static final TagKey<Fluid> PREVENTS_DOUBLE_JUMP = POFluidTags.of("prevents_double_jump");

    private static TagKey<Fluid> of (String path) {
        return TagKey.of(RegistryKeys.FLUID, ProviOriginsMain.identifier(path));
    }
}
