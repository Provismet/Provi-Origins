package com.provismet.personalOrigins;

import com.provismet.personalOrigins.originClasses.decaykraken.DecayKraken;
import com.provismet.personalOrigins.originClasses.voidlily.VoidLily;
import com.provismet.personalOrigins.powers.PowerFactories;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class ProviOriginsMain implements ModInitializer {
    public static final String modid = "proviorigins";

    public static Identifier identifier (String path) {
        return new Identifier(modid, path);
    }

    @Override
    public void onInitialize() {
        DecayKraken.register();
        VoidLily.register();

        PowerFactories.register();
    }
}
