package com.provismet.datagen.proviorigins;

import com.provismet.proviorigins.utility.tags.POBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.concurrent.CompletableFuture;

public class BiomeTagGenerator extends FabricTagProvider<Biome> {
    public BiomeTagGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure (RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(POBiomeTags.HOT_UNDERGROUND)
            .addOptionalTag(ConventionalBiomeTags.IN_NETHER)
            .addOptional(Identifier.of("terralith", "cave/thermal_caves"));

        getOrCreateTagBuilder(POBiomeTags.HAS_SALTWATER)
            .addOptionalTag(POBiomeTags.IRRELEVANT_SALINITY)
            .add(BiomeKeys.STONY_SHORE)
            .addOptionalTag(ConventionalBiomeTags.BEACH)
            .addOptionalTag(ConventionalBiomeTags.OCEAN)
            .addOptional(Identifier.of("terralith", "alpha_islands"))
            .addOptional(Identifier.of("terralith", "alpha_islands_winter"))
            .addOptional(Identifier.of("terralith", "mirage_isles"));

        getOrCreateTagBuilder(POBiomeTags.IRRELEVANT_SALINITY)
            .addOptionalTag(ConventionalBiomeTags.IN_NETHER)
            .addOptionalTag(ConventionalBiomeTags.IN_THE_END)
            .add(BiomeKeys.THE_VOID)
            .add(BiomeKeys.MUSHROOM_FIELDS)
            .addOptionalTag(Identifier.of("terralith", "skylands"));

        getOrCreateTagBuilder(POBiomeTags.SOUL_COLLECTOR)
            .add(BiomeKeys.SOUL_SAND_VALLEY)
            .addOptional(Identifier.of("byg", "warped_desert"))
            .addOptional(Identifier.of("betternether", "wart_forest"))
            .addOptional(Identifier.of("betternether", "wart_forest_edge"))
            .addOptional(Identifier.of("betternether", "soul_plain"))
            .addOptional(Identifier.of("betternether", "nether_grasslands"))
            .addOptional(Identifier.of("betternether", "poor_nether_grasslands"));

        getOrCreateTagBuilder(POBiomeTags.ALWAYS_GROW)
            .add(BiomeKeys.LUSH_CAVES)
            .addOptional(Identifier.of("terralith", "cave/underground_jungle"));

        getOrCreateTagBuilder(POBiomeTags.NATURAL)
            .addOptionalTag(ConventionalBiomeTags.FOREST)
            .addOptionalTag(ConventionalBiomeTags.JUNGLE)
            .addOptionalTag(ConventionalBiomeTags.FLORAL)
            .addOptionalTag(ConventionalBiomeTags.VEGETATION_DENSE)
            .addOptionalTag(POBiomeTags.ALWAYS_GROW);
    }
}
