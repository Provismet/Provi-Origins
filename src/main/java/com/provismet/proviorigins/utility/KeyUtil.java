package com.provismet.proviorigins.utility;

import io.github.apace100.apoli.power.type.Active;

public interface KeyUtil {
    String ACTIVE_PRIMARY = "key.origins.primary_active";
    String ACTIVE_SECONDARY = "key.origins.secondary_active";
    String ACTIVE_TERTIARY = "key.proviorigins.tertiary_active";
    String ACTIVE_QUATERNARY = "key.proviorigins.quaternary_active";

    static Active.Key create (String name, boolean continuous) {
        Active.Key key = new Active.Key();
        key.key = name;
        key.continuous = continuous;
        return key;
    }

    static Active.Key create (String name) {
        return create(name, false);
    }

    static Active.Key primary () {
        return KeyUtil.primary(false);
    }

    static Active.Key primary (boolean continuous) {
        return KeyUtil.create(ACTIVE_PRIMARY, continuous);
    }

    static Active.Key secondary () {
        return KeyUtil.secondary(false);
    }

    static Active.Key secondary (boolean continuous) {
        return KeyUtil.create(ACTIVE_SECONDARY, continuous);
    }

    static Active.Key tertiary () {
        return KeyUtil.tertiary(false);
    }

    static Active.Key tertiary (boolean continuous) {
        return KeyUtil.create(ACTIVE_TERTIARY, continuous);
    }

    static Active.Key quaternary () {
        return KeyUtil.quaternary(false);
    }

    static Active.Key quaternary (boolean continuous) {
        return KeyUtil.create(ACTIVE_QUATERNARY, continuous);
    }
}
