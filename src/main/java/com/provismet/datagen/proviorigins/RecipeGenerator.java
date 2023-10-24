package com.provismet.datagen.proviorigins;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate (RecipeExporter exporter) {
        createSimpleShapeless(exporter, com.provismet.proviorigins.content.registries.Items.LILY_OF_THE_VOID, Items.PURPLE_DYE, 1, RecipeCategory.MISC);
        createSimpleShapeless(exporter, com.provismet.proviorigins.content.registries.Items.SOLID_LANTERN, Items.LANTERN, 1, RecipeCategory.DECORATIONS);
        createSimpleShapeless(exporter, com.provismet.proviorigins.content.registries.Items.SOUL_LAMP, Items.SOUL_LANTERN, 1, RecipeCategory.DECORATIONS);
    }

    private static void createSimpleShapeless (RecipeExporter exporter, Item inputItem, Item outputItem, int count, RecipeCategory category) {
        ShapelessRecipeJsonBuilder.create(category, outputItem, count)
            .input(inputItem)
            .criterion(FabricRecipeProvider.hasItem(outputItem), FabricRecipeProvider.conditionsFromItem(outputItem))
            .criterion(FabricRecipeProvider.hasItem(inputItem), FabricRecipeProvider.conditionsFromItem(inputItem))
            .offerTo(exporter);
    }
}
