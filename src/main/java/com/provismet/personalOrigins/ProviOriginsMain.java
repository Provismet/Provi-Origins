package com.provismet.personalOrigins;

import com.provismet.personalOrigins.powers.PowerFactories;
import com.provismet.personalOrigins.voidlily.VoidPoison;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ProviOriginsMain implements ModInitializer {
    // Kraken of Decay
    public static final Item soulLamp = new Item(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
    public static final DamageSource FRESHWATER = new DamageSource("freshwater").setBypassesArmor().setUnblockable();

    // Lily of the Void
    public static final DamageSource VOID_POISON = new DamageSource("voidPoison").setBypassesArmor().setUsesMagic().setUnblockable();
    public static final DamageSource BAD_DIMENSION = new DamageSource("badDimension").setBypassesArmor();
    public static final DamageSource COMPOST = new DamageSource("compost").setBypassesArmor().setUsesMagic().setUnblockable().setBypassesProtection();
    public static final StatusEffect VOIDPOISON_EFFECT = new VoidPoison();

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("decaykraken", "kraken_soul_lantern"), soulLamp);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("voidlily", "void_poison"), VOIDPOISON_EFFECT);

        PowerFactories.register();
    }
}
