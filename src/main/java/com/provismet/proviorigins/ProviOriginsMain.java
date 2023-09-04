package com.provismet.proviorigins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.provismet.proviorigins.actions.ActionFactories;
import com.provismet.proviorigins.conditions.bientity.BiEntityConditionFactories;
import com.provismet.proviorigins.conditions.entity.EntityConditionFactories;
import com.provismet.proviorigins.content.registries.Blocks;
import com.provismet.proviorigins.content.registries.Entities;
import com.provismet.proviorigins.content.registries.Items;
import com.provismet.proviorigins.content.registries.Particles;
import com.provismet.proviorigins.content.registries.Sounds;
import com.provismet.proviorigins.content.registries.StatusEffects;
import com.provismet.proviorigins.content.world.gen.WorldGen;
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
        Entities.register();
        Items.register();
        StatusEffects.register();
        Blocks.register();
        Particles.register();
        Sounds.register();

        WorldGen.generateWorldGen();

        PowerFactories.register();
        ActionFactories.register();
        EntityConditionFactories.register();
        BiEntityConditionFactories.register();

        LOGGER.info("Provi's Origins are live and ready.");
    }
}
