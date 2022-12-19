package com.provismet.personalOrigins.powers;

import com.provismet.personalOrigins.ProviOriginsMain;

import net.minecraft.util.Identifier;

public class Powers {
    public static final String NAMESPACE = ProviOriginsMain.modid;

    // There are no "simple" powers in this mod, they would go here otherwise.

    public static Identifier identifier (String path) {
        return new Identifier(NAMESPACE, path);
    }
}
