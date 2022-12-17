package com.provismet.personalOrigins.powers;

import net.minecraft.util.Identifier;

public class Powers {
    public static final String NAMESPACE = "proviorigins";

    // There are no "simple" powers in this mod, they would go here otherwise.

    public static Identifier identifier (String path) {
        return new Identifier(NAMESPACE, path);
    }
}
