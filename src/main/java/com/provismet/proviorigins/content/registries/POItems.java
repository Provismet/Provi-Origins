package com.provismet.proviorigins.content.registries;

import org.jetbrains.annotations.Nullable;

import com.provismet.proviorigins.ProviOriginsMain;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Rarity;

public class POItems {
    private static Item registerItemIcon (String origin) {
        return Registry.register(Registries.ITEM, ProviOriginsMain.identifier(origin + "_icon"), new Item(new Item.Settings().maxCount(64).rarity(Rarity.EPIC)));
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

    public static final Item SOUL_LAMP = new Item(new Item.Settings().maxCount(1).rarity(Rarity.RARE));
    public static final Item SOLID_LANTERN = new Item(new Item.Settings().maxCount(1).rarity(Rarity.RARE));

    public static final BlockItem LILY_OF_THE_VOID = new BlockItem(POBlocks.LILY_OF_THE_VOID, new Item.Settings());

    public static final Item ICON_LILY_OF_THE_VOID = registerItemIcon("lily_of_the_void");
    public static final Item ICON_KRAKEN_OF_DECAY = registerItemIcon("kraken_of_decay");
    public static final Item ICON_JELLY_SCULK = registerItemIcon("jelly");
    public static final Item ICON_DRAKLING = registerItemIcon("drakling");
    public static final Item ICON_DRAKE = registerItemIcon("drake");
    public static final Item ICON_SPLINTER = registerItemIcon("splinter");
    public static final Item ICON_FAERIE_MOTH = registerItemIcon("fae_moth");
    public static final Item ICON_ALRAUNE = registerItemIcon("alraune");
    public static final Item ICON_HOMUNCULUS = registerItemIcon("homunculus");
    public static final Item ICON_CRYSTALLISER = registerItemIcon("crystalliser");

    public static void register () {
        register(SOUL_LAMP, "kraken_soul_lantern");
        register(SOLID_LANTERN, "solid_lantern");
        register(LILY_OF_THE_VOID, "lily_of_the_void", ItemGroups.NATURAL);
    }
}
