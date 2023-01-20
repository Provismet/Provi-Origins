package com.provismet.proviorigins;

import org.lwjgl.glfw.GLFW;

import com.provismet.proviorigins.extras.ModelLayerRegistry;
import com.provismet.proviorigins.extras.RendererRegistry;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;

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
    }
}
