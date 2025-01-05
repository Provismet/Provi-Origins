package com.provismet.proviorigins.utility.constants;

import com.provismet.proviorigins.ProviOriginsMain;
import io.github.apace100.origins.Origins;
import net.minecraft.util.Identifier;

public interface BadgeTextures {
    Identifier COST = ProviOriginsMain.identifier("textures/gui/badge/cost.png");
    Identifier DRAGON = ProviOriginsMain.identifier("textures/gui/badge/dragon.png");

    abstract class Baseline {
        public static final Identifier ACTIVE = Origins.identifier("textures/gui/badge/active.png");
        public static final Identifier ARROW_UP = Origins.identifier("textures/gui/badge/arrow_up.png");
        public static final Identifier RECIPE = Origins.identifier("textures/gui/badge/recipe.png");
        public static final Identifier INFO = Origins.identifier("textures/gui/badge/info.png");
        public static final Identifier STAR = Origins.identifier("textures/gui/badge/star.png");
        public static final Identifier TOGGLE = Origins.identifier("textures/gui/badge/toggle.png");
    }
}
