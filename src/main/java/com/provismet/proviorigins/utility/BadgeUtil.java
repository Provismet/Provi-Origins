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

    static TooltipBadge info (Text text) {
        return new TooltipBadge(BadgeTextures.Baseline.INFO, text);
    }

    static TooltipBadge star (Text text) {
        return new TooltipBadge(BadgeTextures.Baseline.STAR, text);
    }

    static TooltipBadge active () {
        return BadgeUtil.active(Text.translatable("origins.gui.badge.active"));
    }

    static TooltipBadge active (Text text) {
        return new TooltipBadge(BadgeTextures.Baseline.ACTIVE, text);
    }
}
