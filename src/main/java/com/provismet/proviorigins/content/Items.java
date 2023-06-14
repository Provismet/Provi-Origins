package com.provismet.proviorigins.content;

import com.provismet.proviorigins.ProviOriginsMain;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class Items {
    public static final Item SOUL_LAMP = new Item(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE));

    public static void register () {
        Registry.register(Registries.ITEM, ProviOriginsMain.identifier("kraken_soul_lantern"), SOUL_LAMP);
    }
}
