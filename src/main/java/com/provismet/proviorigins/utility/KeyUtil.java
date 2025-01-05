package com.provismet.proviorigins.utility;

import io.github.apace100.apoli.power.type.Active;

public interface KeyUtil {
    static Active.Key create (String name, boolean continuous) {
        Active.Key key = new Active.Key();
        key.key = name;
        key.continuous = continuous;
        return key;
    }

    static Active.Key create (String name) {
        return create(name, false);
    }
}
