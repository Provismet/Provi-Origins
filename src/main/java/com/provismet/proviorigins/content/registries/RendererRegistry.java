package com.provismet.proviorigins.content.registries;

import com.provismet.proviorigins.content.entities.renderers.MinionEntityRenderer;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class RendererRegistry {
    public static void register () {
        EntityRendererRegistry.register(Entities.MINION, MinionEntityRenderer::new);
    }
}
