package com.provismet.datagen.proviorigins;

import com.provismet.proviorigins.content.registries.POItems;
import com.provismet.proviorigins.utility.OriginList;
import com.provismet.proviorigins.utility.tags.POItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends FabricTagProvider.ItemTagProvider {
    public ItemTagGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure (RegistryWrapper.WrapperLookup arg) {
        final TagKey<Item> hidden = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "hidden_from_recipe_viewers"));
        getOrCreateTagBuilder(hidden)
            .add(OriginList.LILY_OF_THE_VOID.getIconKey())
            .add(OriginList.KRAKEN_OF_DECAY.getIconKey())
            .add(OriginList.DRAKLING.getIconKey())
            .add(OriginList.DRAKE.getIconKey())
            .add(OriginList.SPLINTER.getIconKey())
            .add(OriginList.FAERIE_MOTH.getIconKey())
            .add(OriginList.ALRAUNE.getIconKey())
            .add(OriginList.HOMUNCULUS.getIconKey())
            .add(OriginList.CRYSTALLISER.getIconKey());

        getOrCreateTagBuilder(ItemTags.SMALL_FLOWERS)
            .add(POItems.LILY_OF_THE_VOID);

        getOrCreateTagBuilder(ItemTags.PIGLIN_REPELLENTS)
            .add(POItems.SOUL_LAMP);

        getOrCreateTagBuilder(POItemTags.HYDRATE_ON_USE)
            .add(Items.POTION);

        getOrCreateTagBuilder(POItemTags.GRANTS_ARMOUR)
            .addOptionalTag(ItemTags.LOGS_THAT_BURN);

        getOrCreateTagBuilder(POItemTags.GRANTS_BLINDNESS)
            .add(Items.AZURE_BLUET)
            .addOptional(Identifier.of("betterend", "murkweed"));

        getOrCreateTagBuilder(POItemTags.GRANTS_CORRUPTION)
            .add(Items.LILY_OF_THE_VALLEY)
            .add(POItems.LILY_OF_THE_VOID)
            .addOptional(Identifier.of("betterend", "end_lily_seed"))
            .addOptional(Identifier.of("betterend", "end_lotus_seed"));

        getOrCreateTagBuilder(POItemTags.GRANTS_FIRE_RESISTANCE)
            .add(Items.ALLIUM)
            .addOptional(Identifier.of("betternether", "magma_flower"));

        getOrCreateTagBuilder(POItemTags.GRANTS_GLOW)
            .add(Items.GLOW_BERRIES)
            .addOptional(Identifier.of("betterend", "glowing_pillar_leaves"))
            .addOptional(Identifier.of("betterend", "lumecorn_rod"))
            .addOptional(Identifier.of("betterend", "blue_vine_fur"));

        getOrCreateTagBuilder(POItemTags.GRANTS_JUMP_BOOST)
            .add(Items.CORNFLOWER);

        getOrCreateTagBuilder(POItemTags.GRANTS_NIGHT_VISION)
            .add(Items.POPPY)
            .add(Items.TORCHFLOWER);

        getOrCreateTagBuilder(POItemTags.GRANTS_REGENERATION)
            .add(Items.OXEYE_DAISY);

        getOrCreateTagBuilder(POItemTags.GRANTS_SATURATION)
            .add(Items.BLUE_ORCHID)
            .add(Items.DANDELION);

        getOrCreateTagBuilder(POItemTags.GRANTS_SPEED)
            .add(Items.SUGAR_CANE);

        getOrCreateTagBuilder(POItemTags.GRANTS_TELEPORT)
            .add(Items.CHORUS_FRUIT);

        getOrCreateTagBuilder(POItemTags.GRANTS_THORNS)
            .add(Items.CACTUS)
            .add(Items.ROSE_BUSH)
            .add(Items.SWEET_BERRIES)
            .addOptional(Identifier.of("betternether", "nether_cactus"))
            .addOptional(Identifier.of("betternether", "barrel_cactus"))
            .addOptional(Identifier.of("betterend", "needlegrass"))
            .addOptional(Identifier.of("betterend", "neon_cactus"));

        getOrCreateTagBuilder(POItemTags.GRANTS_WATER_BREATHING)
            .add(Items.KELP)
            .addOptional(Identifier.of("betterend", "charnia_red"))
            .addOptional(Identifier.of("betterend", "charnia_orange"))
            .addOptional(Identifier.of("betterend", "charnia_purple"))
            .addOptional(Identifier.of("betterend", "charnia_light_blue"))
            .addOptional(Identifier.of("betterend", "charnia_cyan"))
            .addOptional(Identifier.of("betterend", "charnia_green"));

        getOrCreateTagBuilder(POItemTags.GRANTS_WEAKNESS)
            .add(Items.RED_TULIP)
            .add(Items.ORANGE_TULIP)
            .add(Items.WHITE_TULIP)
            .add(Items.PINK_TULIP);

        getOrCreateTagBuilder(POItemTags.GRANTS_WITHER)
            .add(Items.WITHER_ROSE);

        getOrCreateTagBuilder(POItemTags.SOUL_CARRY)
            .add(Items.SOUL_LANTERN)
            .add(POItems.SOUL_LAMP);

        getOrCreateTagBuilder(POItemTags.MAX_SOUL_CONSUME)
            .add(Items.TOTEM_OF_UNDYING)
            .add(Items.WITHER_SKELETON_SKULL)
            .add(Items.NETHER_STAR);

        getOrCreateTagBuilder(POItemTags.MEDIUM_SOUL_CONSUME)
            .add(Items.SOUL_LANTERN)
            .add(Items.SCULK_CATALYST);

        getOrCreateTagBuilder(POItemTags.WEAK_SOUL_CONSUME)
            .add(Items.SOUL_TORCH);

        getOrCreateTagBuilder(POItemTags.LIGHT_SOURCES)
            .addOptionalTag(POItemTags.SOUL_CARRY)
            .add(Items.TORCH)
            .add(Items.SOUL_TORCH)
            .add(Items.LANTERN)
            .add(POItems.SOLID_LANTERN);

        getOrCreateTagBuilder(POItemTags.SUMMON_AOE)
            .add(Items.TORCHFLOWER)
            .add(Items.RED_TULIP)
            .add(Items.ORANGE_TULIP)
            .add(Items.WHITE_TULIP)
            .add(Items.PINK_TULIP)
            .add(Items.BLUE_ORCHID)
            .add(Items.OXEYE_DAISY)
            .addOptional(Identifier.of("betterend", "flammalix"))
            .addOptional(Identifier.of("betterend", "aeridium"));

        getOrCreateTagBuilder(POItemTags.SUMMON_PROJECTILE)
            .add(Items.WITHER_ROSE)
            .addOptionalTag(POItemTags.GRANTS_CORRUPTION);

        getOrCreateTagBuilder(POItemTags.SUMMON_PULL)
            .add(Items.PITCHER_PLANT)
            .add(Items.POPPY)
            .add(Items.AZURE_BLUET)
            .add(Items.SUNFLOWER)
            .add(Items.PEONY)
            .addOptional(Identifier.of("betterend", "tenanea_flowers"))
            .addOptional(Identifier.of("betterend", "twisted_umbrella_moss"));

        getOrCreateTagBuilder(POItemTags.SUMMON_PUSH)
            .add(Items.ROSE_BUSH)
            .add(Items.DANDELION)
            .add(Items.ALLIUM)
            .add(Items.CORNFLOWER)
            .add(Items.LILAC)
            .addOptional(Identifier.of("betterend", "blooming_cooksonia"))
            .addOptional(Identifier.of("betterend", "umbrella_moss"));

        getOrCreateTagBuilder(POItemTags.FERTILISER)
            .add(Items.DRAGON_BREATH);
    }
}
