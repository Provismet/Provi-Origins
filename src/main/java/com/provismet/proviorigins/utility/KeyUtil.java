package com.provismet.proviorigins.utility;

import io.github.apace100.apoli.util.keybinding.KeyBindingReference;

public interface KeyUtil {
    String ACTIVE_PRIMARY = "key.origins.primary_active";
    String ACTIVE_SECONDARY = "key.origins.secondary_active";
    String ACTIVE_TERTIARY = "key.proviorigins.tertiary_active";
    String ACTIVE_QUATERNARY = "key.proviorigins.quaternary_active";

    static KeyBindingReference create (String name, boolean continuous) {
        return new KeyBindingReference(name, continuous);
    }

    static KeyBindingReference create (String name) {
        return create(name, false);
    }

    static KeyBindingReference primary () {
        return KeyUtil.primary(false);
    }

    static KeyBindingReference primary (boolean continuous) {
        return KeyUtil.create(ACTIVE_PRIMARY, continuous);
    }

    static KeyBindingReference secondary () {
        return KeyUtil.secondary(false);
    }

    static KeyBindingReference secondary (boolean continuous) {
        return KeyUtil.create(ACTIVE_SECONDARY, continuous);
    }

    static KeyBindingReference tertiary () {
        return KeyUtil.tertiary(false);
    }

    static KeyBindingReference tertiary (boolean continuous) {
        return KeyUtil.create(ACTIVE_TERTIARY, continuous);
    }

    static KeyBindingReference quaternary () {
        return KeyUtil.quaternary(false);
    }

    static KeyBindingReference quaternary (boolean continuous) {
        return KeyUtil.create(ACTIVE_QUATERNARY, continuous);
    }

    static KeyBindingReference sneak () {
        return KeyUtil.sneak(false);
    }

    static KeyBindingReference sneak (boolean continuous) {
        return KeyUtil.create("key.sneak", continuous);
    }
}
