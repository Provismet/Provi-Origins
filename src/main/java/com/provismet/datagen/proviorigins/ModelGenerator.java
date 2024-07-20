package com.provismet.datagen.proviorigins;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.content.registries.POBlocks;
import com.provismet.proviorigins.utility.OriginList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureMap;

public class ModelGenerator extends FabricModelProvider {
    public ModelGenerator (FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels (BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerFlowerPotPlant(POBlocks.LILY_OF_THE_VOID, POBlocks.POTTED_LILY_OF_THE_VOID, BlockStateModelGenerator.TintType.NOT_TINTED);
    }

    @Override
    public void generateItemModels (ItemModelGenerator itemModelGenerator) {
        OriginList.ALL.forEach(entry -> registerIcon(itemModelGenerator, entry));
    }

    private static void registerIcon (ItemModelGenerator generator, OriginList.OriginEntry entry) {
        Models.GENERATED.upload(
            entry.getIconKey().getValue().withPrefixedPath("item/"),
            TextureMap.layer0(ProviOriginsMain.identifier("icons/" + entry.fullName)),
            generator.writer
        );
    }
}
