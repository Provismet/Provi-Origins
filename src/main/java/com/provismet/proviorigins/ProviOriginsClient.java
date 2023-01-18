package com.provismet.proviorigins;

import com.provismet.proviorigins.extras.ModelLayerRegistry;
import com.provismet.proviorigins.extras.RendererRegistry;

import net.fabricmc.api.ClientModInitializer;

public class ProviOriginsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient () {
        ModelLayerRegistry.register();
        RendererRegistry.register();
    }
}
