package com.provismet.proviorigins;

import org.lwjgl.glfw.GLFW;

import com.provismet.proviorigins.content.registries.BlockRenderLayers;
import com.provismet.proviorigins.content.registries.ModelLayerRegistry;
import com.provismet.proviorigins.content.registries.ParticleFactories;
import com.provismet.proviorigins.content.registries.RendererRegistry;
import com.provismet.proviorigins.extras.SleepFogModifier;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.BackgroundRenderer;

public class ProviOriginsClient implements ClientModInitializer {
    public static KeyBinding tertiaryActive = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.proviorigins.tertiary_active",
        GLFW.GLFW_KEY_UNKNOWN,
        "category.proviorigins.keys"
    ));

    public static KeyBinding quaternaryActive = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.proviorigins.quaternary_active",
        GLFW.GLFW_KEY_UNKNOWN,
        "category.proviorigins.keys"
    ));

    @Override
    public void onInitializeClient () {
        ModelLayerRegistry.register();
        RendererRegistry.register();
        BlockRenderLayers.register();

        BackgroundRenderer.FOG_MODIFIERS.add(0, new SleepFogModifier());

        ParticleFactories.register();
    }
}
