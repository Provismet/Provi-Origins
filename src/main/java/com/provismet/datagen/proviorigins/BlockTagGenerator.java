package com.provismet.datagen.proviorigins;

import com.provismet.proviorigins.content.registries.POBlocks;
import com.provismet.proviorigins.utility.tags.POBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends FabricTagProvider.BlockTagProvider {
    public BlockTagGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure (RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS)
            .add(POBlocks.LILY_OF_THE_VOID);

        getOrCreateTagBuilder(POBlockTags.VOID_PLANTABLE)
            .addOptionalTag(POBlockTags.VOID_TOUCHING)
            .addOptionalTag(POBlockTags.WEAKLY_VOID_TOUCHING)
            .addOptional(Identifier.of("betterend", "shadow_grass"))
            .addOptional(Identifier.of("betterend", "chorus_nylium"))
            .addOptional(Identifier.of("betterend", "pink_moss"));

        getOrCreateTagBuilder(POBlockTags.VOID_TOUCHING)
            .add(Blocks.BEDROCK)
            .add(Blocks.RESPAWN_ANCHOR)
            .addOptionalTag(BlockTags.PORTALS);

        getOrCreateTagBuilder(POBlockTags.WEAKLY_VOID_TOUCHING)
            .add(Blocks.CRYING_OBSIDIAN)
            .addOptional(Identifier.of("betternether", "weeping_obsidian"))
            .addOptional(Identifier.of("betternether", "blue_crying_obsidian"))
            .addOptional(Identifier.of("betternether", "blue_weeping_obsidian"));

        getOrCreateTagBuilder(POBlockTags.SOUL_FIRES)
            .add(Blocks.SOUL_TORCH)
            .add(Blocks.SOUL_WALL_TORCH)
            .add(Blocks.SOUL_LANTERN)
            .add(Blocks.SOUL_FIRE);

        getOrCreateTagBuilder(POBlockTags.SOUL_FIRES_WITH_FIRE_BLOCKSTATE)
            .addOptional(Identifier.of("betternether", "cincinnasite_fire_bowl_soul"))
            .addOptional(Identifier.of("betternether", "bricks_fire_bowl_soul"))
            .addOptional(Identifier.of("betternether", "netherite_fire_bowl_soul"));

        getOrCreateTagBuilder(POBlockTags.SOUL_FIRES_WITH_LIT_BLOCKSTATE)
            .add(Blocks.SOUL_CAMPFIRE);

        getOrCreateTagBuilder(POBlockTags.SCULK)
            .add(Blocks.SCULK)
            .add(Blocks.SCULK_SENSOR)
            .add(Blocks.SCULK_CATALYST)
            .add(Blocks.SCULK_VEIN)
            .add(Blocks.SCULK_SHRIEKER);

        getOrCreateTagBuilder(POBlockTags.LARGE_FIRES)
            .add(Blocks.LANTERN)
            .add(Blocks.SOUL_LANTERN)
            .add(Blocks.FIRE)
            .add(Blocks.SOUL_FIRE);

        getOrCreateTagBuilder(POBlockTags.LARGE_FIRES_WITH_FIRE_BLOCKSTATE)
            .addOptionalTag(POBlockTags.SOUL_FIRES_WITH_FIRE_BLOCKSTATE)
            .addOptional(Identifier.of("betternether", "cincinnasite_fire_bowl"))
            .addOptional(Identifier.of("betternether", "bricks_fire_bowl"))
            .addOptional(Identifier.of("betternether", "netherite_fire_bowl"));

        getOrCreateTagBuilder(POBlockTags.LARGE_FIRES_WITH_LIT_BLOCKSTATE)
            .addOptionalTag(POBlockTags.SOUL_FIRES_WITH_LIT_BLOCKSTATE)
            .add(Blocks.CAMPFIRE);

        getOrCreateTagBuilder(POBlockTags.TORCHES)
            .add(Blocks.TORCH)
            .add(Blocks.WALL_TORCH)
            .add(Blocks.SOUL_TORCH)
            .add(Blocks.SOUL_WALL_TORCH)
            .addOptionalTag(BlockTags.CANDLES)
            .addOptional(Identifier.of("betternether", "willow_torch"));

        getOrCreateTagBuilder(POBlockTags.FOLIAGE)
            .addOptionalTag(BlockTags.FLOWERS)
            .addOptionalTag(BlockTags.SAPLINGS); // Missing Apoli
    }
}
