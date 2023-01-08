package com.provismet.proviorigins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.provismet.proviorigins.actions.ActionFactories;
import com.provismet.proviorigins.conditions.bientity.BiEntityConditionFactories;
import com.provismet.proviorigins.originTypes.decaykraken.DecayKraken;
import com.provismet.proviorigins.originTypes.jellysculk.JellySculk;
import com.provismet.proviorigins.originTypes.voidlily.VoidLily;
import com.provismet.proviorigins.powers.PowerFactories;

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
