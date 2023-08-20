package com.provismet.proviorigins.content.world.gen;

import com.provismet.proviorigins.content.world.features.FeaturesPlaced;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

public class FlowerGen {
    public static void generateFlowers () {
        BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Feature.VEGETAL_DECORATION, FeaturesPlaced.LILY_OF_THE_VOID);
    }
}
