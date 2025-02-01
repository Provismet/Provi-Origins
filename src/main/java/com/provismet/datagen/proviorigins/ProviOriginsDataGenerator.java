package com.provismet.datagen.proviorigins;

import com.provismet.proviorigins.content.PODamageTypes;
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
        pack.addProvider(LanguageGenerator::new);
        pack.addProvider(LanguageGeneratorUK::new);
        pack.addProvider(DamageTypeGenerator::new);
        pack.addProvider(ItemTagGenerator::new);
        pack.addProvider(FluidTagGenerator::new);
        pack.addProvider(EntityTypeTagGenerator::new);
        pack.addProvider(BlockTagGenerator::new);
        pack.addProvider(BiomeTagGenerator::new);
        pack.addProvider(DamageTypeTagGenerator::new);
        pack.addProvider(LootTableGenerator::new);
        pack.addProvider(ModelGenerator::new);
        pack.addProvider(PowerGenerator::new);
    }
    
    @Override
    public void buildRegistry (RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, FeaturesConfigured::buildFeatures);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, FeaturesPlaced::buildPlacedFeatures);
        registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, PODamageTypes::bootstrap);
    }
}
