package com.provismet.personalOrigins.originClasses.decaykraken;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class DecayKraken {
    public static final Item soulLamp = new Item(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
    
    public static final DamageSource FRESHWATER = new DamageSource("freshwater").setBypassesArmor().setUnblockable();

    public static Identifier identifier (String path) {
        return new Identifier("decaykraken", path);
    }

    public static void register () {
        Registry.register(Registry.ITEM, identifier("kraken_soul_lantern"), soulLamp);
    }
}
