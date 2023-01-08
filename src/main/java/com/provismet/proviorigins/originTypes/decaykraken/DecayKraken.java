package com.provismet.proviorigins.originTypes.decaykraken;

import com.provismet.proviorigins.ProviOriginsMain;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class DecayKraken {
    public static final Item SOUL_LAMP = new Item(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
    
    public static final DamageSource FRESHWATER = new DamageSource("freshwater").setBypassesArmor().setUnblockable();

    public static void register () {
        Registry.register(Registry.ITEM, ProviOriginsMain.identifier("kraken_soul_lantern"), SOUL_LAMP);
    }
}
