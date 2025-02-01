package com.provismet.proviorigins.originTypes.alraune;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.utility.OriginList;
import com.provismet.proviorigins.utility.tags.POItemTags;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public record FlowerMinion (String name, TagKey<Item> itemTag) {
    public static final FlowerMinion AOE = new FlowerMinion("aoe", POItemTags.SUMMON_AOE);
    public static final FlowerMinion PUSH = new FlowerMinion("push", POItemTags.SUMMON_PUSH);
    public static final FlowerMinion PULL = new FlowerMinion("pull", POItemTags.SUMMON_PULL);
    public static final FlowerMinion PROJECTILE = new FlowerMinion("projectile", POItemTags.SUMMON_PROJECTILE);

    public Text getTooltip () {
        return Text.translatable("tooltip.proviorigins.alraune.summons_" + this.name);
    }

    public Identifier getBasePower () {
        return OriginList.ALRAUNE.identifier("summoned_" + this.name);
    }

    public Identifier getUpgradedPower () {
        return OriginList.ALRAUNE.identifier("summoned_" + this.name + "_upgrade");
    }

    public Identifier getTexture () {
        return ProviOriginsMain.identifier("textures/entity/" + this.name + "_minion.png");
    }
}
