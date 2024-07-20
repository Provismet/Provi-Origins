package com.provismet.proviorigins.utility.tags;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.utility.OriginList;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public abstract class POBiomeTags {
    public static final TagKey<Biome> HOT_UNDERGROUND = POBiomeTags.of(OriginList.COMMON.getDataPath("hot_underground"));

    public static final TagKey<Biome> HAS_SALTWATER = POBiomeTags.of(OriginList.KRAKEN_OF_DECAY.getDataPath("has_saltwater"));
    public static final TagKey<Biome> IRRELEVANT_SALINITY = POBiomeTags.of(OriginList.KRAKEN_OF_DECAY.getDataPath("irrelevant_salinity"));
    public static final TagKey<Biome> SOUL_COLLECTOR = POBiomeTags.of(OriginList.KRAKEN_OF_DECAY.getDataPath("soul_collector"));

    public static final TagKey<Biome> ALWAYS_GROW = POBiomeTags.of(OriginList.ALRAUNE.getDataPath("always_grow"));
    public static final TagKey<Biome> NATURAL = POBiomeTags.of(OriginList.ALRAUNE.getDataPath("natural"));

    private static TagKey<Biome> of (String path) {
        return TagKey.of(RegistryKeys.BIOME, ProviOriginsMain.identifier(path));
    }
}
