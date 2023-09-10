package com.provismet.proviorigins.content.world.features;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.content.registries.Blocks;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class FeaturesConfigured {
    public static final RegistryKey<ConfiguredFeature<?,?>> LILY_OF_THE_VOID = registerKey("lily_of_the_void_feature");

    private static RegistryKey<ConfiguredFeature<?, ?>> registerKey (String path) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, ProviOriginsMain.identifier(path));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register (Registerable<ConfiguredFeature<?,?>> context, RegistryKey<ConfiguredFeature<?,?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    public static void buildFeatures (Registerable<ConfiguredFeature<?,?>> context) {
        register(context, LILY_OF_THE_VOID, Feature.FLOWER,
            new RandomPatchFeatureConfig(96, 7, 4,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.LILY_OF_THE_VOID)))));
    }
}
