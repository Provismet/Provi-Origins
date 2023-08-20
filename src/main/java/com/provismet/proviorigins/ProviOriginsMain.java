package com.provismet.proviorigins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.provismet.proviorigins.actions.ActionFactories;
import com.provismet.proviorigins.conditions.bientity.BiEntityConditionFactories;
import com.provismet.proviorigins.conditions.entity.EntityConditionFactories;
import com.provismet.proviorigins.content.registries.Blocks;
import com.provismet.proviorigins.content.registries.Items;
import com.provismet.proviorigins.content.registries.StatusEffects;
import com.provismet.proviorigins.originTypes.splinter.Splinter;
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
        Splinter.register();
        Items.register();
        StatusEffects.register();
        Blocks.register();

        PowerFactories.register();
        ActionFactories.register();
        EntityConditionFactories.register();
        BiEntityConditionFactories.register();

        LOGGER.info("Provi's Origins are live and ready.");
    }
}
