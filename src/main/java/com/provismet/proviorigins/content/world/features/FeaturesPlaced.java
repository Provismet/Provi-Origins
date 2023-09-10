package com.provismet.proviorigins.content.world.features;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.provismet.proviorigins.ProviOriginsMain;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

public class FeaturesPlaced {
    public static final RegistryKey<PlacedFeature> LILY_OF_THE_VOID = registerKey("lily_of_the_void_all");
    public static final RegistryKey<PlacedFeature> LILY_OF_THE_VOID_END = registerKey("lily_of_the_void_end");

    public static void buildPlacedFeatures (Registerable<PlacedFeature> context) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, LILY_OF_THE_VOID, configuredFeatureRegistryEntryLookup.getOrThrow(FeaturesConfigured.LILY_OF_THE_VOID), Builders.flowerModifiers(255));        
        register(context, LILY_OF_THE_VOID_END, configuredFeatureRegistryEntryLookup.getOrThrow(FeaturesConfigured.LILY_OF_THE_VOID), Builders.flowerModifiers(8));
    }

    public static RegistryKey<PlacedFeature> registerKey(String path) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, ProviOriginsMain.identifier(path));
    }

    private static void register (Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static final class Builders {
        private static List<PlacementModifier> flowerModifiers (int chance) {
            ImmutableList.Builder<PlacementModifier> builder = ImmutableList.builder();
            builder.add(RarityFilterPlacementModifier.of(chance));
            builder.add(SquarePlacementModifier.of());
            builder.add(PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP);
            builder.add(BiomePlacementModifier.of());
            return builder.build();
        }
    }
}
