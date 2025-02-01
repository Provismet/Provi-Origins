package com.provismet.proviorigins;

import com.provismet.proviorigins.registries.POBadgeFactories;
import com.provismet.proviorigins.registries.POBientityActionTypes;
import com.provismet.proviorigins.registries.POBientityConditionTypes;
import com.provismet.proviorigins.registries.POBlockConditionTypes;
import com.provismet.proviorigins.registries.POEntityActionTypes;
import com.provismet.proviorigins.registries.POEntityConditionTypes;
import com.provismet.proviorigins.registries.POPowerTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.provismet.proviorigins.content.registries.POBlocks;
import com.provismet.proviorigins.content.registries.POEntities;
import com.provismet.proviorigins.content.registries.POItems;
import com.provismet.proviorigins.content.registries.POParticles;
import com.provismet.proviorigins.content.registries.POSounds;
import com.provismet.proviorigins.content.registries.POStatusEffects;
import com.provismet.proviorigins.content.world.gen.WorldGen;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class ProviOriginsMain implements ModInitializer {
    public static final String MODID = "proviorigins";
    public static final Logger LOGGER = LoggerFactory.getLogger("Provi's Origins");

    public static Identifier identifier (String path) {
        return Identifier.of(MODID, path);
    }

    @Override
    public void onInitialize () {
        POEntities.register();
        POItems.register();
        POStatusEffects.init();
        POBlocks.register();
        POParticles.register();
        POSounds.register();

        WorldGen.generateWorldGen();

        POBadgeFactories.init();
        POPowerTypes.init();
        POBlockConditionTypes.init();
        POEntityActionTypes.init();
        POEntityConditionTypes.init();
        POBientityActionTypes.init();
        POBientityConditionTypes.init();

        LOGGER.info("Provi's Origins are live and ready.");
    }
}
