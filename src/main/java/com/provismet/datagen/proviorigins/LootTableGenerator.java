package com.provismet.datagen.proviorigins;

import com.provismet.proviorigins.content.registries.POBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class LootTableGenerator extends FabricBlockLootTableProvider {
    protected LootTableGenerator (FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate () {
        addDrop(POBlocks.LILY_OF_THE_VOID);
        addPottedPlantDrops(POBlocks.POTTED_LILY_OF_THE_VOID);
    }
}
