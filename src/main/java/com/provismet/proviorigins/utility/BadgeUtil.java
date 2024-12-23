package com.provismet.proviorigins.utility;

import com.provismet.proviorigins.utility.constants.BadgeTextures;
import io.github.apace100.origins.badge.TooltipBadge;
import net.minecraft.text.Text;

public interface BadgeUtil {
    static TooltipBadge cost (Text text) {
        return new TooltipBadge(BadgeTextures.COST, text);
    }

    static TooltipBadge dragon (Text text) {
        return new TooltipBadge(BadgeTextures.DRAGON, text);
    }
}
