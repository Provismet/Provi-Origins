package com.provismet.proviorigins.utility;

import com.provismet.proviorigins.ProviOriginsMain;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.List;

public abstract class OriginList {
    public static final OriginEntry COMMON = OriginEntry.of("common");
    public static final OriginEntry LILY_OF_THE_VOID = OriginEntry.of("lily_of_the_void", "voidlily");
    public static final OriginEntry KRAKEN_OF_DECAY = OriginEntry.of("kraken_of_decay", "decaykraken");
    public static final OriginEntry DRAKLING = OriginEntry.of("drakling", "drakling");
    public static final OriginEntry DRAKE = OriginEntry.of("drake", "drakling");
    public static final OriginEntry JELLY_SCULK = OriginEntry.of("jelly", "jellysculk");
    public static final OriginEntry SPLINTER = OriginEntry.of("splinter", "splinter");
    public static final OriginEntry FAERIE_MOTH = OriginEntry.of("fae_moth", "faemoth");
    public static final OriginEntry ALRAUNE = OriginEntry.of("alraune");
    public static final OriginEntry HOMUNCULUS = OriginEntry.of("homunculus");
    public static final OriginEntry CRYSTALLISER = OriginEntry.of("crystalliser");

    public static final List<OriginEntry> ALL = List.of(
        LILY_OF_THE_VOID,
        KRAKEN_OF_DECAY,
        DRAKLING, DRAKE,
        JELLY_SCULK,
        SPLINTER,
        FAERIE_MOTH,
        ALRAUNE,
        HOMUNCULUS,
        CRYSTALLISER
    );

    public static class OriginEntry {
        public final String fullName;
        public final String dataName;

        private OriginEntry (String fullName, String dataName) {
            this.fullName = fullName;
            this.dataName = dataName;
        }

        public RegistryKey<Item> getIconKey () {
            return RegistryKey.of(RegistryKeys.ITEM, ProviOriginsMain.identifier(this.fullName + "_icon"));
        }

        public String getDataPath (String path) {
            return String.format("%s/%s", this.dataName, path);
        }

        public Identifier getDataId (String path) {
            return ProviOriginsMain.identifier(this.getDataPath(path));
        }

        private static OriginEntry of (String fullName, String dataName) {
            return new OriginEntry(fullName, dataName);
        }

        private static OriginEntry of (String name) {
            return new OriginEntry(name, name);
        }
    }
}
