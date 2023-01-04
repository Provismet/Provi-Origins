package com.provismet.personalOrigins.powers;

import com.provismet.personalOrigins.ProviOriginsMain;

import net.minecraft.util.Identifier;

public class Powers {
    public static final String NAMESPACE = ProviOriginsMain.modid;

    // Generic field names
    public static final String ENTITY_ACTION = "entity_action";
    public static final String ENTITY_CONDITION = "entity_condition";
    public static final String BIENTITY_ACTION = "bientity_action";
    public static final String BIENTITY_CONDITION = "bientity_condition";
    public static final String ITEM_ACTION = "item_action";
    public static final String ITEM_CONDITION = "item_condition";
    public static final String KEY = "key";
    public static final String COOLDOWN = "cooldown";
    public static final String HUD_RENDER = "hud_render";

    // There are no "simple" powers in this mod, they would go here otherwise.

    public static Identifier identifier (String path) {
        return new Identifier(NAMESPACE, path);
    }
}
