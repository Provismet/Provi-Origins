package com.provismet.proviorigins.utility.tags;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.utility.OriginList;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public abstract class POBlockTags {
    public static final TagKey<Block> VOID_PLANTABLE = POBlockTags.of("lily_of_the_void_plantable");

    public static final TagKey<Block> VOID_TOUCHING = POBlockTags.of(OriginList.LILY_OF_THE_VOID.getDataPath("void_touching"));
    public static final TagKey<Block> WEAKLY_VOID_TOUCHING = POBlockTags.of(OriginList.LILY_OF_THE_VOID.getDataPath("weakly_void_touching"));

    public static final TagKey<Block> SOUL_FIRES = POBlockTags.of(OriginList.KRAKEN_OF_DECAY.getDataPath("soul_fires"));
    public static final TagKey<Block> SOUL_FIRES_WITH_FIRE_BLOCKSTATE = POBlockTags.of(OriginList.KRAKEN_OF_DECAY.getDataPath("soul_fires_fire_state"));
    public static final TagKey<Block> SOUL_FIRES_WITH_LIT_BLOCKSTATE = POBlockTags.of(OriginList.KRAKEN_OF_DECAY.getDataPath("soul_fires_lit_state"));

    public static final TagKey<Block> SCULK = POBlockTags.of(OriginList.JELLY_SCULK.getDataPath("sculk"));

    public static final TagKey<Block> LARGE_FIRES = POBlockTags.of(OriginList.FAERIE_MOTH.getDataPath("large_fires"));
    public static final TagKey<Block> LARGE_FIRES_WITH_FIRE_BLOCKSTATE = POBlockTags.of(OriginList.FAERIE_MOTH.getDataPath("large_fires_fire_state"));
    public static final TagKey<Block> LARGE_FIRES_WITH_LIT_BLOCKSTATE = POBlockTags.of(OriginList.FAERIE_MOTH.getDataPath("large_fires_lit_state"));
    public static final TagKey<Block> TORCHES = POBlockTags.of(OriginList.FAERIE_MOTH.getDataPath("torches"));

    public static final TagKey<Block> FOLIAGE = POBlockTags.of(OriginList.ALRAUNE.getDataPath("foliage"));

    private static TagKey<Block> of (String identifierPath) {
        return TagKey.of(RegistryKeys.BLOCK, ProviOriginsMain.identifier(identifierPath));
    }
}
