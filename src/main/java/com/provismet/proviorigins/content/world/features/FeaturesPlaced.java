package com.provismet.proviorigins.content.world.features;

import java.util.List;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.content.registries.Blocks;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;

public class FeaturesPlaced {
    public static final RegistryKey<PlacedFeature> LILY_OF_THE_VOID = registerKey("lily_of_the_void_placed");

    public static void buildPlacedFeatures (Registerable<PlacedFeature> context) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, LILY_OF_THE_VOID, configuredFeatureRegistryEntryLookup.getOrThrow(FeaturesConfigured.LILY_OF_THE_VOID),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(RarityFilterPlacementModifier.of(47), Blocks.LILY_OF_THE_VOID));
    }

    public static RegistryKey<PlacedFeature> registerKey(String path) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, ProviOriginsMain.identifier(path));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
