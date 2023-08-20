package com.provismet.datagen.proviorigins;

import com.provismet.proviorigins.content.world.WorldGenerator;
import com.provismet.proviorigins.content.world.features.FeaturesConfigured;
import com.provismet.proviorigins.content.world.features.FeaturesPlaced;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class ProviOriginsDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator (FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(WorldGenerator::new);
        pack.addProvider(RecipeGenerator::new);
    }
    
    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, FeaturesConfigured::buildFeatures);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, FeaturesPlaced::buildPlacedFeatures);
    }
}
