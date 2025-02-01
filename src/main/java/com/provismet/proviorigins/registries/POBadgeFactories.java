package com.provismet.proviorigins.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.badge.PresetKeybindBadge;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.apace100.origins.badge.BadgeFactory;
import io.github.apace100.origins.badge.BadgeManager;

public interface POBadgeFactories {
    BadgeFactory PRESET_KEYBIND = new BadgeFactory(
        ProviOriginsMain.identifier("preset_keybind"),
        new SerializableData()
            .add("sprite", SerializableDataTypes.IDENTIFIER)
            .add("translation", SerializableDataTypes.STRING)
            .add("keybind", SerializableDataTypes.STRING),
        PresetKeybindBadge::new
    );

    static void init () {
        BadgeManager.register(PRESET_KEYBIND);
    }
}
