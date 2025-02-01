package com.provismet.proviorigins;

import com.provismet.proviorigins.utility.KeyUtil;
import org.lwjgl.glfw.GLFW;

import com.provismet.proviorigins.content.registries.POBlockRenderLayers;
import com.provismet.proviorigins.content.registries.POModelLayerRegistry;
import com.provismet.proviorigins.content.registries.POParticleFactories;
import com.provismet.proviorigins.content.registries.RendererRegistry;
import com.provismet.proviorigins.extras.SleepFogModifier;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.BackgroundRenderer;

public class ProviOriginsClient implements ClientModInitializer {
    public static final String KEY_CATEGORY = "category.proviorigins.keys";

    public static KeyBinding tertiaryActive = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        KeyUtil.ACTIVE_TERTIARY,
        GLFW.GLFW_KEY_UNKNOWN,
        KEY_CATEGORY
    ));

    public static KeyBinding quaternaryActive = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        KeyUtil.ACTIVE_QUATERNARY,
        GLFW.GLFW_KEY_UNKNOWN,
        KEY_CATEGORY
    ));

    @Override
    public void onInitializeClient () {
        POModelLayerRegistry.register();
        RendererRegistry.register();
        POBlockRenderLayers.register();

        BackgroundRenderer.FOG_MODIFIERS.addFirst(new SleepFogModifier());

        POParticleFactories.register();
    }
}
