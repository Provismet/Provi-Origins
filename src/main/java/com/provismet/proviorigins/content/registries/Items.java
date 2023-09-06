package com.provismet.proviorigins.content.registries;

import org.jetbrains.annotations.Nullable;

import com.provismet.proviorigins.ProviOriginsMain;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Rarity;

public class Items {
    private static Item registerItemIcon (String origin) {
        return Registry.register(Registries.ITEM, ProviOriginsMain.identifier(origin + "_icon"), new Item(new FabricItemSettings().maxCount(64).rarity(Rarity.EPIC)));
    }

    private static void register (Item item, String path) {
        register(item, path, null);
    }

    private static void register (Item item, String path, @Nullable RegistryKey<ItemGroup> itemGroup) {
        Registry.register(Registries.ITEM, ProviOriginsMain.identifier(path), item);
        if (itemGroup != null) {
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> {
                content.add(item);
            });
        }
    }

    public static final Item SOUL_LAMP = new Item(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE));
    public static final Item SOLID_LANTERN = new Item(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE));

    public static final BlockItem LILY_OF_THE_VOID = new BlockItem(Blocks.LILY_OF_THE_VOID, new FabricItemSettings());

    public static void register () {
        register(SOUL_LAMP, "kraken_soul_lantern");
        register(SOLID_LANTERN, "solid_lantern");
        register(LILY_OF_THE_VOID, "lily_of_the_void", ItemGroups.NATURAL);

        registerItemIcon("lily_of_the_void");
        registerItemIcon("kraken_of_decay");
        registerItemIcon("jelly");
        registerItemIcon("drakling");
        registerItemIcon("drake");
        registerItemIcon("splinter");
        registerItemIcon("fae_moth");
        registerItemIcon("alraune");
        registerItemIcon("homunculus");
        registerItemIcon("crystalliser");
    }
}
