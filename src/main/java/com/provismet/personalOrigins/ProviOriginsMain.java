package com.provismet.personalOrigins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.provismet.personalOrigins.actions.ActionFactories;
import com.provismet.personalOrigins.conditions.bientity.BiEntityConditionFactories;
import com.provismet.personalOrigins.originClasses.decaykraken.DecayKraken;
import com.provismet.personalOrigins.originClasses.jellysculk.JellySculk;
import com.provismet.personalOrigins.originClasses.voidlily.VoidLily;
import com.provismet.personalOrigins.powers.PowerFactories;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class ProviOriginsMain implements ModInitializer {
    public static final String modid = "proviorigins";
    public static final Logger LOGGER = LoggerFactory.getLogger("Provi's Origins");

    public static Identifier identifier (String path) {
        return new Identifier(modid, path);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Provi's Origins are live and ready.");

        DecayKraken.register();
        VoidLily.register();
        JellySculk.register();

        PowerFactories.register();
        ActionFactories.register();
        BiEntityConditionFactories.register();
    }
}
