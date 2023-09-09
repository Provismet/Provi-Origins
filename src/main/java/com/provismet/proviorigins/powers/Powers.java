package com.provismet.proviorigins.powers;

import com.provismet.proviorigins.ProviOriginsMain;

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
    public static final String PROJECTILE_ACTION = "projectile_action";
    public static final String KEY = "key";
    public static final String COOLDOWN = "cooldown";
    public static final String HUD_RENDER = "hud_render";
    public static final String RESOURCE = "resource";
    public static final String DISTANCE = "distance";
    public static final String RADIUS = "radius";
    public static final String COMPARISON = "comparison";
    public static final String COMPARE_TO = "compare_to";
    public static final String INTERVAL = "interval";
    public static final String CLIENT = "client";
    public static final String SERVER = "server";
    public static final String COUNT = "count";
    public static final String PARTICLE = "particle";
    public static final String OFFSET_X = "offset_x";
    public static final String OFFSET_Y = "offset_y";
    public static final String OFFSET_Z = "offset_z";
    public static final String AMOUNT = "amount";

    // There are no "simple" powers in this mod, they would go here otherwise.

    public static Identifier identifier (String path) {
        return new Identifier(NAMESPACE, path);
    }
}
