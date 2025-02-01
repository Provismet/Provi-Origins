package com.provismet.proviorigins.originTypes.voidlily;

import com.provismet.datagen.proviorigins.constants.PowerNames;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public interface VoidLily {
    /*
     * Mimicry Value Cheat Sheet
     *  0 : Normal (Poison on Hit)
     *  1 : Fire Resist
     *  2 : Blindness on Hit
     *  3 : Saturation
     *  4 : Jump Boost
     *  5 : Regeneration
     *  6 : Night Vision
     *  7 : Weakness on Hit
     *  8 : Wither on Hit
     *  9 : Water Breathing
     * 10 : Thorns
     * 11 : Glow
     * 12 : Speed
     * 13 : Void Corruption on Hit
     * 14 : Armour Bonus
     * ## : Line-of-Sight Teleport
     */

    Identifier FIRE_RESIST_HUD = PowerNames.VoidLily.FLOWER_COPY_ALLIUM.withSuffixedPath("_hud");
    Identifier BLINDNESS_HUD = PowerNames.VoidLily.FLOWER_COPY_AZUREBLUET.withSuffixedPath("_hud");
    Identifier SATURATION_HUD = PowerNames.VoidLily.FLOWER_COPY_BLUEORCHID_DANDELION.withSuffixedPath("_hud");
    Identifier JUMP_BOOST_HUD = PowerNames.VoidLily.FLOWER_COPY_CORNFLOWER.withSuffixedPath("_hud");
    Identifier REGENERATION_HUD = PowerNames.VoidLily.FLOWER_COPY_OXEYE.withSuffixedPath("_hud");
    Identifier NIGHT_VISION_HUD = PowerNames.VoidLily.FLOWER_COPY_POPPY.withSuffixedPath("_hud");
    Identifier WEAKNESS_HUD = PowerNames.VoidLily.FLOWER_COPY_TULIPS.withSuffixedPath("_hud");
    Identifier WITHER_HUD = PowerNames.VoidLily.FLOWER_COPY_WITHER.withSuffixedPath("_hud");
    Identifier WATER_BREATHING_HUD = PowerNames.VoidLily.FLOWER_COPY_WATERBREATHING.withSuffixedPath("_hud");
    Identifier THORNS_HUD = PowerNames.VoidLily.FLOWER_COPY_CACTUS_ROSE_BERRY.withSuffixedPath("_hud");
    Identifier GLOW_HUD = PowerNames.VoidLily.FLOWER_COPY_GLOW.withSuffixedPath("_hud");
    Identifier SPEED_HUD = PowerNames.VoidLily.FLOWER_COPY_SUGARCANE.withSuffixedPath("_hud");
    Identifier VOID_CORRUPTION_HUD = PowerNames.VoidLily.FLOWER_COPY_LILY.withSuffixedPath("_hud");
    Identifier ARMOUR_HUD = PowerNames.VoidLily.FLOWER_COPY_WOOD.withSuffixedPath("_hud");

    static @Nullable Identifier getHudForIndex (int index) {
        return switch (index) {
            case 1 -> FIRE_RESIST_HUD;
            case 2 -> BLINDNESS_HUD;
            case 3 -> SATURATION_HUD;
            case 4 -> JUMP_BOOST_HUD;
            case 5 -> REGENERATION_HUD;
            case 6 -> NIGHT_VISION_HUD;
            case 7 -> WEAKNESS_HUD;
            case 8 -> WITHER_HUD;
            case 9 -> WATER_BREATHING_HUD;
            case 10 -> THORNS_HUD;
            case 11 -> GLOW_HUD;
            case 12 -> SPEED_HUD;
            case 13 -> VOID_CORRUPTION_HUD;
            case 14 -> ARMOUR_HUD;
            default -> null;
        };
    }
}
