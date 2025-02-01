package com.provismet.proviorigins.utility;

import com.provismet.proviorigins.utility.constants.BadgeTextures;
import io.github.apace100.origins.badge.CraftingRecipeBadge;
import io.github.apace100.origins.badge.KeybindBadge;
import io.github.apace100.origins.badge.TooltipBadge;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public interface BadgeUtil {
    static TooltipBadge cost (Text text) {
        return new TooltipBadge(BadgeTextures.COST, text);
    }

    static TooltipBadge cost (String translationKey) {
        return BadgeUtil.cost(Text.translatable(translationKey));
    }

    static TooltipBadge cost (Identifier powerId) {
        return BadgeUtil.cost(PowerUtil.getBadgeTranslationKey(powerId));
    }

    static TooltipBadge cost (Identifier powerId, int index) {
        return BadgeUtil.cost(PowerUtil.getBadgeTranslationKey(powerId, index));
    }

    static TooltipBadge dragon () {
        return BadgeUtil.dragon(Text.translatable("power.proviorigins.common/kill_dragon.badge"));
    }

    static TooltipBadge dragon (Text text) {
        return new TooltipBadge(BadgeTextures.DRAGON, text);
    }

    static TooltipBadge dragon (String translationKey) {
        return BadgeUtil.dragon(Text.translatable(translationKey));
    }

    static TooltipBadge dragon (Identifier powerId) {
        return BadgeUtil.dragon(PowerUtil.getBadgeTranslationKey(powerId));
    }

    static TooltipBadge dragon (Identifier powerId, int index) {
        return BadgeUtil.dragon(PowerUtil.getBadgeTranslationKey(powerId, index));
    }

    static TooltipBadge info (Text text) {
        return new TooltipBadge(BadgeTextures.Baseline.INFO, text);
    }

    static TooltipBadge info (String translationKey) {
        return BadgeUtil.info(Text.translatable(translationKey));
    }

    static TooltipBadge info (Identifier powerId) {
        return BadgeUtil.info(PowerUtil.getBadgeTranslationKey(powerId));
    }

    static TooltipBadge info (Identifier powerId, int index) {
        return BadgeUtil.info(PowerUtil.getBadgeTranslationKey(powerId, index));
    }

    static TooltipBadge star (Text text) {
        return new TooltipBadge(BadgeTextures.Baseline.STAR, text);
    }

    static TooltipBadge star (String translationKey) {
        return BadgeUtil.star(Text.translatable(translationKey));
    }

    static TooltipBadge star (Identifier powerId) {
        return BadgeUtil.star(PowerUtil.getBadgeTranslationKey(powerId));
    }

    static TooltipBadge star (Identifier powerId, int index) {
        return BadgeUtil.star(PowerUtil.getBadgeTranslationKey(powerId, index));
    }

    static KeybindBadge active () {
        return new KeybindBadge(BadgeTextures.Baseline.ACTIVE, "origins.gui.badge.active");
    }

    static TooltipBadge active (String translationKey) {
        return new TooltipBadge(BadgeTextures.Baseline.ACTIVE, Text.translatable(translationKey));
    }

    static TooltipBadge active (Identifier powerId) {
        return BadgeUtil.active(PowerUtil.getBadgeTranslationKey(powerId));
    }

    static TooltipBadge active (Identifier powerId, int index) {
        return BadgeUtil.active(PowerUtil.getBadgeTranslationKey(powerId, index));
    }

    static CraftingRecipeBadge crafting (RecipeEntry<CraftingRecipe> recipe) {
        return new CraftingRecipeBadge(
            BadgeTextures.Baseline.RECIPE,
            recipe,
            null,
            null
        );
    }
}
