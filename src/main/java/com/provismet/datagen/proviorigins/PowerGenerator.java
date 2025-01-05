package com.provismet.datagen.proviorigins;

import com.provismet.datagen.proviorigins.provider.POPowerProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class PowerGenerator extends POPowerProvider {
    public PowerGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    protected void generate (PowerCollector collector) {

    }
}
