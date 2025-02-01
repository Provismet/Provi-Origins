package com.provismet.proviorigins.utility;

import net.minecraft.util.Identifier;

public interface PowerUtil {
    static String getTranslationKey (Identifier identifier) {
        return "power." + identifier.toTranslationKey().replace('/', '.');
    }

    static String getNameTranslationKey (Identifier identifier) {
        return PowerUtil.getTranslationKey(identifier) + ".name";
    }

    static String getDescriptionTranslationKey (Identifier identifier) {
        return PowerUtil.getTranslationKey(identifier) + ".description";
    }

    static String getBadgeTranslationKey (Identifier identifier) {
        return PowerUtil.getTranslationKey(identifier) + ".badge";
    }

    static String getBadgeTranslationKey (Identifier identifier, int badgeNumber) {
        return PowerUtil.getBadgeTranslationKey(identifier) + "." + badgeNumber;
    }

    static Identifier subPower (Identifier power, String subPower) {
        return power.withSuffixedPath("_" + subPower);
    }
}
