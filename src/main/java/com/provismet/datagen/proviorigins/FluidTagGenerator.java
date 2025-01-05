package com.provismet.datagen.proviorigins;

import com.provismet.proviorigins.utility.tags.POFluidTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.FluidTags;

import java.util.concurrent.CompletableFuture;

public class FluidTagGenerator extends FabricTagProvider.FluidTagProvider {
    public FluidTagGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure (RegistryWrapper.WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(POFluidTags.PREVENTS_DOUBLE_JUMP)
            .addOptionalTag(FluidTags.WATER)
            .addOptionalTag(FluidTags.LAVA);
    }
}
