package com.provismet.proviorigins.content.registries;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

public class BlockRenderLayers {
    private static void registerCutout (Block block) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
    }

    public static void register () {
        registerCutout(Blocks.LILY_OF_THE_VOID);
        registerCutout(Blocks.POTTED_LILY_OF_THE_VOID);
    }
}
