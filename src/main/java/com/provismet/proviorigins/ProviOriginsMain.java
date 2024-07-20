package com.provismet.proviorigins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.provismet.proviorigins.actions.ActionFactories;
import com.provismet.proviorigins.conditions.bientity.BiEntityConditionFactories;
import com.provismet.proviorigins.conditions.block.BlockConditionFactories;
import com.provismet.proviorigins.conditions.entity.EntityConditionFactories;
import com.provismet.proviorigins.content.registries.POBlocks;
import com.provismet.proviorigins.content.registries.POEntities;
import com.provismet.proviorigins.content.registries.POItems;
import com.provismet.proviorigins.content.registries.POParticles;
import com.provismet.proviorigins.content.registries.POSounds;
import com.provismet.proviorigins.content.registries.POStatusEffects;
import com.provismet.proviorigins.content.world.gen.WorldGen;
import com.provismet.proviorigins.powers.PowerFactories;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class ProviOriginsMain implements ModInitializer {
    public static final String MODID = "proviorigins";
    public static final Logger LOGGER = LoggerFactory.getLogger("Provi's Origins");

    public static Identifier identifier (String path) {
        return new Identifier(MODID, path);
    }

    @Override
    public void onInitialize () {
        POEntities.register();
        POItems.register();
        POStatusEffects.register();
        POBlocks.register();
        POParticles.register();
        POSounds.register();

        WorldGen.generateWorldGen();

        PowerFactories.register();
        ActionFactories.register();
        EntityConditionFactories.register();
        BiEntityConditionFactories.register();
        BlockConditionFactories.register();

        LOGGER.info("Provi's Origins are live and ready.");
    }
}
