package com.provismet.personalOrigins.originClasses.voidlily;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class VoidLily {
    public static final DamageSource VOID_POISON = new DamageSource("voidPoison").setBypassesArmor().setUsesMagic().setUnblockable();
    public static final DamageSource BAD_DIMENSION = new DamageSource("badDimension").setBypassesArmor();
    public static final DamageSource COMPOST = new DamageSource("compost").setBypassesArmor().setUsesMagic().setUnblockable().setBypassesProtection();
    
    public static final StatusEffect VOIDPOISON_EFFECT = new VoidPoison();

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

    public static Identifier identifier (String path) {
        return new Identifier("voidlily", path);
    }

    public static void register () {
        Registry.register(Registry.STATUS_EFFECT, identifier("void_poison"), VOIDPOISON_EFFECT);
    }
}
