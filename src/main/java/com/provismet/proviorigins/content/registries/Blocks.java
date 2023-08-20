package com.provismet.proviorigins.content.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.content.Blocks.LilyOfTheVoidBlock;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class Blocks {
    public static final LilyOfTheVoidBlock LILY_OF_THE_VOID = new LilyOfTheVoidBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.LILY_OF_THE_VALLEY));
    public static final Block POTTED_LILY_OF_THE_VOID = new FlowerPotBlock(LILY_OF_THE_VOID, FabricBlockSettings.copy(net.minecraft.block.Blocks.POTTED_LILY_OF_THE_VALLEY));

    public static void register (Block block, String path) {
        Registry.register(Registries.BLOCK, ProviOriginsMain.identifier(path), block);
    }

    public static void register () {
        register(LILY_OF_THE_VOID, "lily_of_the_void");
        register(POTTED_LILY_OF_THE_VOID, "potted_lily_of_the_void");
    }
}
