package com.provismet.proviorigins.content;

import com.provismet.proviorigins.ProviOriginsMain;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class Items {
    private static Item registerItemIcon (String origin) {
        return Registry.register(Registries.ITEM, ProviOriginsMain.identifier(origin), new Item(new FabricItemSettings().maxCount(64).rarity(Rarity.EPIC)));
    }

    public static final Item SOUL_LAMP = new Item(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE));
    public static final Item SOLID_LANTERN = new Item(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE));

    public static void register () {
        Registry.register(Registries.ITEM, ProviOriginsMain.identifier("kraken_soul_lantern"), SOUL_LAMP);
        Registry.register(Registries.ITEM, ProviOriginsMain.identifier("solid_lantern"), SOLID_LANTERN);
        registerItemIcon("lily_of_the_void");
        registerItemIcon("kraken_of_decay");
        registerItemIcon("jelly");
        registerItemIcon("drakling");
        registerItemIcon("drake");
        registerItemIcon("splinter");
        registerItemIcon("fae_moth");
    }
}
