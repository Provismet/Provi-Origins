package com.provismet.datagen.proviorigins;

import com.provismet.datagen.proviorigins.constants.PowerNames;
import com.provismet.lilylib.datagen.provider.LilyLanguageProvider;
import com.provismet.proviorigins.content.PODamageTypes;
import com.provismet.proviorigins.content.registries.POBlocks;
import com.provismet.proviorigins.content.registries.POItems;
import com.provismet.proviorigins.content.registries.POStatusEffects;
import com.provismet.proviorigins.utility.KeyUtil;
import com.provismet.proviorigins.utility.OriginList;
import com.provismet.proviorigins.utility.PowerUtil;
import com.provismet.proviorigins.utility.tags.POItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class LanguageGenerator extends LilyLanguageProvider {
    protected LanguageGenerator (FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations (RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("category.proviorigins.keys", "Provi's Origins Extra Keys");
        translationBuilder.add(KeyUtil.ACTIVE_TERTIARY, "Active Skill (Tertiary)");
        translationBuilder.add(KeyUtil.ACTIVE_QUATERNARY, "Active Skill (Quaternary)");

        translationBuilder.add(POBlocks.LILY_OF_THE_VOID, "Lily of the Void");
        translationBuilder.add(POBlocks.POTTED_LILY_OF_THE_VOID, "Potted Lily of the Void");

        translationBuilder.add(POItems.SOLID_LANTERN, "Lantern");
        translationBuilder.add(POItems.SOUL_LAMP, "Soul Lantern");

        translationBuilder.add(POStatusEffects.VOID_CORRUPTION.value(), "Void Corruption");
        translationBuilder.add(POStatusEffects.UNTARGETABLE.value(), "Illusive");
        translationBuilder.add(POStatusEffects.SLEEP.value(), "Sleeping");
        translationBuilder.add(POStatusEffects.ALERT.value(), "Alert");

        addOrigin(translationBuilder, OriginList.LILY_OF_THE_VOID.fullName, "Lily of the Void", "Grown from a lily rooted upon the skybox, you are connected both to nature and to oblivion.");
        addOrigin(translationBuilder, OriginList.KRAKEN_OF_DECAY.fullName, "Kraken of Decay", "A vagrant kraken strays from death, still grasping onto its remnant power.");
        addOrigin(translationBuilder, OriginList.DRAKLING.fullName, "Drakling", "An emergent dragon, immature and yet brimming with potential.");
        addOrigin(translationBuilder, OriginList.DRAKE.fullName, "Drake", "The head of your predecessor has enhanced your growth, you alone soar as the ruler of these skies.");
        addOrigin(translationBuilder, OriginList.JELLY_SCULK.fullName, "Jelly Sculk", "A sculk sensor acted as a polyp, allowing a jellyfish of sculk to be born. Sensing its surroundings, it now dances through the air.");
        addOrigin(translationBuilder, OriginList.SPLINTER.fullName, "Splinter", "A fragile warrior, but a master of deception and fragmentation. The Splinter uses summoned clones of themself and trickery to survive.");
        addOrigin(translationBuilder, OriginList.FAERIE_MOTH.fullName, "Faerie Moth", "The fae live with nature, adapting to it. This creature chose the likeness of a moth.");
        addOrigin(translationBuilder, OriginList.ALRAUNE.fullName, "Alraune", "Blooming from the energy of the forest, you are a facet of nature itself: one whom grows and is protected by nature.");
        addOrigin(translationBuilder, OriginList.HOMUNCULUS.fullName, "Homunculus", "An artificial human, created by witchcraft and seen as a outlier by all of society.");
        addOrigin(translationBuilder, OriginList.CRYSTALLISER.fullName, "Crystallizer", "An amalgam of shards left behind from exploded End Crystals. After gaining sapience, you've retained a similar level of strength.");

        translationBuilder.add("power.proviorigins.common/kill_dragon.badge", "This power is upgraded upon gaining the Free The End advancement.");

        addPower(translationBuilder, PowerNames.Common.BRITTLE, "Brittle Body", "You have only 5 hearts.");
        addPower(translationBuilder, PowerNames.Common.AMPHIBIOUS, "Amphibious", "You can breathe in both air and water.");
        addPower(translationBuilder, PowerNames.Common.PESCATARIAN, "Pescatarian", "You can only eat fish.");
        addPower(translationBuilder, PowerNames.Common.TALLER, "Large Body", "You are taller than most with a height of 3 blocks.");
        addPower(translationBuilder, PowerNames.Common.SLOW_SWIM, "Poor Swimmer", "You swim very slowly.");
        addPower(translationBuilder, PowerNames.Common.DOUBLE_JUMP, "Floaty Body", "You can double jump.");
        addPower(translationBuilder, PowerNames.Common.NO_GAME_EVENTS, "Silent Steps", "None of your actions emit vibrations.");
        addPower(translationBuilder, PowerNames.Common.SLOW_FALL, "Slow Descent", "Sneaking in the air slows your descent.");
        addPower(translationBuilder, PowerNames.Common.EXHAUST_MORE, "Hyper Metabolism", "Your hunger depletes 50% faster than others.");
        addPower(translationBuilder, PowerNames.Common.DOUBLE_FIRE_DAMAGE, "Highly Flammable", "You take double damage from fire and lava.");
        addPower(translationBuilder, PowerNames.Common.NO_FOOD, "Pure Calcium", "You cannot eat food except for milk.");
        addPower(translationBuilder, PowerNames.Common.NO_POTIONS, "Irregular Chemistry", "You cannot drink potions.");
        addPower(translationBuilder, PowerNames.Common.NO_SPLASH_POTIONS, "Incompatible Alchemy", "You are unaffected by potion clouds, including splash and lingering potions.");
        addPower(translationBuilder, PowerNames.Common.NO_CRITS, "Imprecise", "You cannot perform critical hits with your attacks.");
        addPower(translationBuilder, PowerNames.Common.SMALLER, "Small Body", "You are smaller than most and have a height of 1.15 blocks.");
        addPower(translationBuilder, PowerNames.Common.POOR_STRENGTH, "Weak Melee", "Your melee hits deal 33% less damage.");
        addPower(translationBuilder, PowerNames.Common.HEARTY, "Hearty", "You have 2 additional hearts.");
        addPower(translationBuilder, PowerNames.Common.MEDIUM_TALLER, "Grown Body", "You are slightly taller than others with a height of 2.5 blocks.");
        addPower(translationBuilder, PowerNames.Common.DRIED_UP, "Dried Up", "Lose hydration when under the sun in hot biomes and always when in underground hot biomes.\nDrinking or standing in water restores hydration. You will catch fire after 30 seconds of losing hydration.");
        addPower(translationBuilder, PowerNames.Common.NO_TRADES, "Merchant Ban", "Villagers refuse to acknowledge you. You cannot trade with them.");
        addPower(translationBuilder, PowerNames.Common.ANGRY_GOLEMS, "Oppressive Iron", "Iron golems view you as hostile and will attack you on-sight.");
        addPower(translationBuilder, PowerNames.Common.MORE_EXPERIENCE, "Enhanced Intellect", "You gain x1.5 experience.");
        addPower(translationBuilder, PowerNames.Common.NO_BOWS, "Low Dexterity", "You are unable to use bows and crossbows.");

        addPower(translationBuilder, OriginList.LILY_OF_THE_VOID.identifier("floral_mimicry"), "Floral Mimicry", "You may gain mimicry of any compatible plant held in your main hand, consuming the plant in the process. Mimicries grant buffs and last for a duration, until death, or until overridden by another mimicry.");
        addPower(translationBuilder, PowerNames.VoidLily.FLOWER_COPY__MANUAL_RESET, "Born A Lily", "You have natural mimicry of lilies, inflicting poison upon struck entities whilst no other mimicry is active.\nAt any time you may forgo your current mimicry and return to this default state.");
        addPower(translationBuilder, PowerNames.VoidLily.NETHER_AIR, "Abyssal Collapse", "The fabric of the Nether disagrees with you, preventing you from breathing whilst there.");
        addPower(translationBuilder, PowerNames.VoidLily.POLLINATION, "Pollination", "Nearby bees pollinate you, granting saturation but blinding you in the process.");
        addPower(translationBuilder, PowerNames.VoidLily.VOID_GROWTH, "Grow Upon The Skybox", "Gain strength, speed, air, and saturation when in The End or within 4 blocks of Bedrock, portals, or 4 blocks of Crying Obsidian.");

        addPower(translationBuilder, PowerNames.DecayKraken.DEATHS_VICE, "Death's Vice", "Gain access to a resource called Death's Vice that drains slowly over time.");
        addPower(translationBuilder, PowerNames.DecayKraken.DEATHS_VICE_EMPTY, "Death Vision", "Suffer from darkness when Death's Vice reaches zero.");
        addPower(translationBuilder, PowerNames.DecayKraken.DEATHS_VICE_GAIN_ON_KILL, "Nothing Wasted", "Gain 0.5 bars of Death's Vice when killing an entity.");
        addPower(translationBuilder, PowerNames.DecayKraken.DEATHS_VICE_LOW_VALUE_PENALTY, "Over-Reliance", "Suffer weakness and slowness when Death's Vice is below 25%.");
        addPower(translationBuilder, PowerNames.DecayKraken.DEATHS_VICE_RECHARGE_RATE, "Longing Embrace", "Regain Death's Vice when within 5 blocks of any form of soul fire or when holding a soul lantern in your hand. This bonus is increased in the Nether and further increased in soulful biomes.", "For quality of life purposes, you may craft Soul Lanterns into a unique version that cannot be placed but grants the same held effects.");
        addPower(translationBuilder, PowerNames.DecayKraken.PROPULSION, "Ink Propulsion", "Expel a cloud of ink to boost yourself in the direction you're facing. This ability is much stronger in water.", "Costs 1 bar of Death's Vice.");
        addPower(translationBuilder, PowerNames.DecayKraken.SOUL_STEAL, "Soul Siphon", "Charge a short-ranged beam of soul energy, if it hits an entity inflict damage and heal yourself.", "Costs 1.5 bars of Death's Vice.");
        addPower(translationBuilder, PowerNames.DecayKraken.SOULSAND_SPREADING, "Soul Capture", "Killing an undead mob whilst standing on coarse dirt will convert it into soul sand.");
        addPower(translationBuilder, PowerNames.DecayKraken.WATER_GLOW, "Bioluminescence", "Gain a glow when submerged in water.");
        addPower(translationBuilder, PowerNames.DecayKraken.WATER_SENSITIVE, "Sodium Satiation", "Sources of freshwater are harmful to you and will drain Death's Vice over time before damaging your health.", "Freshwater is any Overworld body of water outside of an ocean or beach biome.", "Water Protection enchantments will delay this effect.");
        addPower(translationBuilder, PowerNames.DecayKraken.WITHER_HIT, "Touch of Decay", "When Death's Vice is above 6 bars, striking a target consumes half a bar to inflict Wither for 6 seconds.", "Costs 0.5 bars of Death's Vice.");
        addPower(translationBuilder, PowerNames.DecayKraken.SLOW_ON_LAND, "Suction Grip", "You move slightly slower on land.");
        addPower(translationBuilder, PowerNames.DecayKraken.TENTACLE_GRAPPLE, "Tentacle Grapple", "Extend a tentacle to grab an entity up to 12 blocks away, pulling them towards you and inflicting slow.", "Costs 1.5 bars of Death's Vice.");
        addPower(translationBuilder, PowerNames.DecayKraken.SOUL_CONSUME, "Soul Collector", "Consume an item that holds a soul. Death's Vice restores based on the strength of the soul held in the item.", "Weak souls grant 0.25 bars of Death's Vice.\nMedium souls grant 1 bar of Death's Vice.\nPerfect souls will refill your Death's Vice completely.");

        addPower(translationBuilder, PowerNames.Drakling.CHAIN_CRAFTING, "Forge Master", "You can craft chainmail armor.");
        addPower(translationBuilder, PowerNames.Drakling.DRAGON_EVOLUTION, "Becoming Complete", "Wear a dragon head to begin your evolution.");
        addPower(translationBuilder, PowerNames.Drakling.ENDERDRAGON_MANDATORY, "Draconic Pull", "The Ender Dragon beckons for you. Your flight stamina decreases rapidly when attempting to flee the dragon.");
        addPower(translationBuilder, PowerNames.Drakling.SHORT_FLIGHT, "Broken Wings", "You have natural Elytra wings, but your wings are imperfect, allowing flight only in short bursts.");
        addPower(translationBuilder, PowerNames.Drakling.TRUE_FLIGHT, "Perfected Wings", "You have natural Elytra wings, now perfected you can fly freely and boost yourself in the air.");
        addPower(translationBuilder, PowerNames.Drakling.WEAK_NATURAL_ARMOUR, "Molten Metal", "Your newfound strength allows you to wear stronger armor, but your bodily heat loosens the material and makes it weaker than normal.");
        addPower(translationBuilder, PowerNames.Drakling.RIDABLE, "Mountable", "Your strength allows you to carry other players. Other players may press [USE] on you to mount you; you may pick up other players by pressing [USE] on them whilst sneaking.", "You cannot carry players in combat with you nor players who already have passengers.", "%1$s to force dismount.");

        addPower(translationBuilder, PowerNames.JellySculk.BREAK_SCULK_FAST, "Dismantled", "You can break sculk very quickly, even with your bare hands.");
        addPower(translationBuilder, PowerNames.JellySculk.BUFFS_ON_SCULK, "Comforting Familia", "You gain buffs whilst standing on sculk.");
        addPower(translationBuilder, PowerNames.JellySculk.CREATE_SENSOR, "Spreading Your Senses", "You can spend experience to grow a sculk sensor on top of a sculk block.", "%1$s with an empty hand while sneaking to activate.");
        addPower(translationBuilder, PowerNames.JellySculk.DEBUFFS_OFF_SCULK, "Away From Home", "You are afflicted with darkness when not standing on sculk.");
        addPower(translationBuilder, PowerNames.JellySculk.ENTITY_DETECT, "Vibratory Sight", "You can only see entities around you through their vibrations. Sculk Sensors and Shriekers can also extend their signals to you, revealing entities they detect.", "Team members and tamed animals are always visible.");
        addPower(translationBuilder, PowerNames.JellySculk.SPREAD_SCULK, "Spreading Your Domain", "You can spend experience points to spread sculk to any sculk replaceable block.", "%1$s with an empty hand while sneaking to activate.");
        addPower(translationBuilder, PowerNames.JellySculk.CATALYSE, "Catalyzer", "Occasionally spread sculk veins when gaining experience.");

        addPower(translationBuilder, PowerNames.Splinter.ARCANE_GLOW, "Mana Glow", "You glow based on the amount of mana you currently have.");
        addPower(translationBuilder, PowerNames.Splinter.FRAGMENT, "Fragment", "Summon a clone of yourself at your current location. Summoning clones uses concentration instead of mana.", "Clones inherit your equipment and will fight for you.\nPress %1$s on a clone to 'sit' them like an animal.", "Costs 1 concentration.");
        addPower(translationBuilder, PowerNames.Splinter.RESOURCE_RECHARGE, "Arcane Energy", "You have access to a mana resource with which to cast spells and concentration to summon clones.");
        addPower(translationBuilder, PowerNames.Splinter.DISTORTION, "Illusory Presence", "Blind enemies within a 6 block radius, become untargetable, and create mirror images of yourself and your clones.", "Costs 2 bars of mana.");
        addPower(translationBuilder, PowerNames.Splinter.SWAP, "Misdirection", "Swap places with the clone you are looking at, or if sneaking swap places with the furthest clone within 32 blocks.", "Costs 2 bars of mana.");
        addPower(translationBuilder, PowerNames.Splinter.DODGE_PROJECTILES, "Evasive", "Dodge projectiles when sneaking.", "Costs 2 bars of mana.");

        addPower(translationBuilder, PowerNames.FaeMoth.FAERIE_DUST, "Faerie Dust", "Your moth-like body produces a magical powder when near appropriate light sources. Being in direct sunlight or too far from light will erode the powder.", "Artificial fire-related light sources such as torches, campfires, and lanterns allow you to produce Faerie Dust.", "Torches and lanterns held by yourself or other nearby players will also apply to this power.", "For quality of life purposes, you may craft Lanterns into a unique version that cannot be placed but grants the same held effects.");
        addPower(translationBuilder, PowerNames.FaeMoth.FLUTTER, "Flutter", "Use your wings to boost yourself into the air: you have mid-air jumps.", "Costs 1 bar of Faerie Dust.");
        addPower(translationBuilder, PowerNames.FaeMoth.CRIT_PASSIVE, "Hypnotic Powder", "Critical strikes with a sword will lace an opponent with a layer faerie dust. A target that has three layers on them at once will be put to sleep for a few seconds.", "Sleep is a new status effect that disables an entity's movement, strength, and vision.\nEntities with Sleep take double damage, but the effect is then immediately removed.", "Costs 1.5 bars of Faerie Dust per layer.");
        addPower(translationBuilder, PowerNames.FaeMoth.SHARE_BUFFS, "Blessings of the Fae", "Share speed, strength, and regeneration effects that you currently have with all allies in an 8 block radius.", "Costs 3.5 bars of Faerie Dust.");

        addPower(translationBuilder, PowerNames.Alraune.PHOTOSYNTHESIS, "Photosynthesis", "Slowly gain regain food and saturation when exposed to sunlight.");
        addPower(translationBuilder, PowerNames.Alraune.THROUGH_GRASS, "Unrestricted Herb", "Nature moves out of your way, you can strike through foliage when not sneaking.");
        addPower(translationBuilder, PowerNames.Alraune.LIFE_SAP, "Sap of Life", "Your connection to nature allows you to harness its power. Using this power allows you to transform flowers into placed turrets that aid you in battle.");
        addPower(translationBuilder, OriginList.ALRAUNE.identifier("summon_plant"), "Floral Summoning", "Using your connection to nature allows you to empower flowers into stationary Flower Sprites that damage and hinder nearby enemies.", "Costs 1 Sap of Life.", "Flower Sprites\n- Will take damage if not placed on plantable blocks such as dirt or grass.\n- Will die after 1200 ticks unless fed with a super fertilizer such as dragon breath.", "Flower Sprites have different requirements for different biomes.\n- Most biomes require the Sprite to be exposed to sunlight.\n- For biomes with denser foliage, such as forests, only exposure to the sky is required.\n- Some biomes, such as Lush Caves, will allow Flower Sprites to grow regardless.\n- Use bone meal on a Flower Sprite to bypass this limitation.");

        addPower(translationBuilder, PowerNames.Crystalliser.SUMMON_FOLLOWER, "Deadly Refraction", "Deplete your crystal shards to summon a full crystal above your head. The crystal follows you and attacks your enemies from up to 32 blocks away.", "Costs every bar of Crystal Shards.");
        addPower(translationBuilder, PowerNames.Crystalliser.CRYSTAL_CHARGING, "Shard Collector", "Gain crystal shards when dealing critical hits, taking attacks, and killing enemies.");
        addPower(translationBuilder, PowerNames.Crystalliser.SHARD_ABSORB, "Shard Absorption", "Consume some of your crystal shards and regain 2 hearts of health.", "Costs 1 bar of Crystal Shards.");
        addPower(translationBuilder, PowerNames.Crystalliser.EXPLOSION_RESIST, "Explosive Nature", "You take 75% less explosion damage.");

        this.addDeathMessage(translationBuilder, PODamageTypes.FRESHWATER, "%1$s ran out of salt", "%1$s lost too much salt whilst fighting %2$s", "%1$s lost too much salt trying to deal with %2$s using %3$s");
        this.addDeathMessage(translationBuilder, PODamageTypes.KRAKEN_SOUL_STEAL, "%1$s had their soul stolen by %2$s", "%1$s had their soul stolen by %2$s using %3$s");
        this.addDeathMessage(translationBuilder, PODamageTypes.VOID_CORRUPTION, "%1$s was corrupted by the void", "%1$s succumbed to the void whilst fighting %2$s", "%1$s succumbed to the void whilst fighting %2$s using %3$s");
        this.addDeathMessage(translationBuilder, PODamageTypes.BAD_DIMENSION, "%1$s was rejected by the world", "%1$s returned to the void with help from %2$s", "%1$s returned to the void with help from %2$s using %3$s");
        this.addDeathMessage(translationBuilder, PODamageTypes.COMPOST, "%1$s was composted", "%1$s was composted by %2$s", "%1$s was composted by %2$s using %3$s");
        this.addDeathMessage(translationBuilder, PODamageTypes.CRYSTAL_BEAM, "%1$s was shattered by %2$s", "%1$s was shattered by %2$s using %3$s");
        
        addSubtitle(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "mimic", "Lily mimics");
        addSubtitle(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "mimic_end", "Lily loses mimic");
        addSubtitle(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "teleport", "Lily teleports");
        
        addSubtitle(translationBuilder, OriginList.KRAKEN_OF_DECAY.dataName, "bide", "Kraken bides");
        addSubtitle(translationBuilder, OriginList.KRAKEN_OF_DECAY.dataName, "soul_steal", "Kraken grasps");
        addSubtitle(translationBuilder, OriginList.KRAKEN_OF_DECAY.dataName, "ink", "Kraken squirts");
        addSubtitle(translationBuilder, OriginList.KRAKEN_OF_DECAY.dataName, "salt", "Kraken dissolves");
        
        addSubtitle(translationBuilder, OriginList.DRAKLING.dataName, "evolve", "Drakling evolves");
        addSubtitle(translationBuilder, OriginList.DRAKLING.dataName, "birth", "Drake emerges");
        addSubtitle(translationBuilder, OriginList.DRAKLING.dataName, "flap", "Drake flaps");

        addTooltip(translationBuilder, OriginList.COMMON.dataName, "no_potions", "§8You are unaffected by potions.");

        addTooltip(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "grants_armour", "§8Consume to gain armor.");
        addTooltip(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "grants_blindness", "§8Consume to inflict blindness on struck entities.");
        addTooltip(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "grants_corruption", "§8Consume to enhance your latent poison with the void.");
        addTooltip(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "grants_fire_resistance", "§8Consume to gain fire resistance.");
        addTooltip(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "grants_glow", "§8Consume to gain a glow.");
        addTooltip(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "grants_jump_boost", "§8Consume to gain jump boost.");
        addTooltip(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "grants_night_vision", "§8Consume to gain night vision.");
        addTooltip(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "grants_regeneration", "§8Consume to gain regeneration.");
        addTooltip(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "grants_saturation", "§8Consume to gain saturation.");
        addTooltip(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "grants_speed", "§8Consume to gain speed.");
        addTooltip(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "grants_teleport", "§8Consume to perform line-of-sight teleportation.");
        addTooltip(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "grants_thorns", "§8Consume to gain thorns, damaging attackers.");
        addTooltip(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "grants_weakness", "§8Consume to inflict weakness on struck entities.");
        addTooltip(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "grants_wither", "§8Consume to inflict wither on struck entities.");
        addTooltip(translationBuilder, OriginList.LILY_OF_THE_VOID.dataName, "grants_water_breathing", "§8Consume to gain oxygen.");

        addTooltip(translationBuilder, OriginList.KRAKEN_OF_DECAY.dataName, "weak_soul", "§8Weak Soul");
        addTooltip(translationBuilder, OriginList.KRAKEN_OF_DECAY.dataName, "medium_soul", "§8Medium Soul");
        addTooltip(translationBuilder, OriginList.KRAKEN_OF_DECAY.dataName, "max_soul", "§8Perfect Soul");

        addTooltip(translationBuilder, OriginList.DRAKLING.dataName, "dragon_head", "§8Wear the flesh of your ancestors and gain their strength.");

        addTooltip(translationBuilder, OriginList.ALRAUNE.dataName, "summons_aoe", "§8Summons a flower that deals damage in an area.");
        addTooltip(translationBuilder, OriginList.ALRAUNE.dataName, "summons_pull", "§8Summons a flower that pulls in foes.");
        addTooltip(translationBuilder, OriginList.ALRAUNE.dataName, "summons_push", "§8Summons a flower that pushes foes away.");
        addTooltip(translationBuilder, OriginList.ALRAUNE.dataName, "summons_projectile", "§8Summons a flower that shoots arrows at foes.");
        addTooltip(translationBuilder, OriginList.ALRAUNE.dataName, "super_fertiliser", "§8A super effective fertilizer.");

        translationBuilder.add(POItemTags.HYDRATE_ON_USE, "Grants Hydration");
        translationBuilder.add(POItemTags.GRANTS_ARMOUR, "Void Lily: Armor");
        translationBuilder.add(POItemTags.GRANTS_BLINDNESS, "Void Lily: Blindness");
        translationBuilder.add(POItemTags.GRANTS_CORRUPTION, "Void Lily: Corruption");
        translationBuilder.add(POItemTags.GRANTS_FIRE_RESISTANCE, "void Lily: Fire Resistance");
        translationBuilder.add(POItemTags.GRANTS_GLOW, "Void Lily: Glow");
        translationBuilder.add(POItemTags.GRANTS_JUMP_BOOST, "Void Lily: Jump Boost");
        translationBuilder.add(POItemTags.GRANTS_NIGHT_VISION, "Void Lily: Night Vision");
        translationBuilder.add(POItemTags.GRANTS_REGENERATION, "Void Lily: Regeneration");
        translationBuilder.add(POItemTags.GRANTS_SATURATION, "Void Lily: Saturation");
        translationBuilder.add(POItemTags.GRANTS_SPEED, "Void Lily: Speed");
        translationBuilder.add(POItemTags.GRANTS_TELEPORT, "Void Lily: Teleport");
        translationBuilder.add(POItemTags.GRANTS_THORNS, "Void Lily: Thorns");
        translationBuilder.add(POItemTags.GRANTS_WATER_BREATHING, "Void Lily: Water Breathing");
        translationBuilder.add(POItemTags.GRANTS_WEAKNESS, "Void Lily: Weakness");
        translationBuilder.add(POItemTags.GRANTS_WITHER, "Void Lily: Wither");
        translationBuilder.add(POItemTags.SOUL_CARRY, "Decay Kraken: Soul Carry");
        translationBuilder.add(POItemTags.MAX_SOUL_CONSUME, "Decay Kraken: Large Souls");
        translationBuilder.add(POItemTags.MEDIUM_SOUL_CONSUME, "Decay Kraken: Medium Souls");
        translationBuilder.add(POItemTags.WEAK_SOUL_CONSUME, "Decay Kraken: Weak Souls");
        translationBuilder.add(POItemTags.LIGHT_SOURCES, "Fae Moth: Lanterns");
        translationBuilder.add(POItemTags.FERTILISER, "Alraune: Fertiliser");
        translationBuilder.add(POItemTags.SUMMON_AOE, "Alraune: AOE");
        translationBuilder.add(POItemTags.SUMMON_PROJECTILE, "Alraune: Projectile");
        translationBuilder.add(POItemTags.SUMMON_PULL, "Alraune: Pull");
        translationBuilder.add(POItemTags.SUMMON_PUSH, "Alraune: Push");
    }

    public static void addOrigin (TranslationBuilder translationBuilder, String origin, String name, String description) {
        StringBuilder builder = new StringBuilder();
        builder.append("origin.proviorigins.").append(origin);
        translationBuilder.add(builder + ".name", name);
        translationBuilder.add(builder + ".description", description);
        translationBuilder.add("item.proviorigins." + origin, name + " Origin Icon");
    }

    public static void addPower (TranslationBuilder translationBuilder, Identifier power, String name, String description) {
        LanguageGenerator.addPower(translationBuilder, power, name, description, new String[0]);
    }

    public static void addPower (TranslationBuilder translationBuilder, Identifier power, String name, String description, String... badges) {
        translationBuilder.add(PowerUtil.getNameTranslationKey(power), name);
        translationBuilder.add(PowerUtil.getDescriptionTranslationKey(power), description);

        if (badges.length == 0) return;
        if (badges.length == 1) translationBuilder.add(PowerUtil.getBadgeTranslationKey(power), badges[0]);
        else {
            for (int i = 0; i < badges.length; ++i) {
                translationBuilder.add(PowerUtil.getBadgeTranslationKey(power, i + 1), badges[i]);
            }
        }
    }

    public static void addSubtitle (TranslationBuilder translationBuilder, String origin, String title, String message) {
        translationBuilder.add("subtitles.proviorigins." + origin + "." + title, message);
    }
    
    public static void addTooltip (TranslationBuilder translationBuilder, String origin, String key, String message) {
        translationBuilder.add("tooltip.proviorigins." + origin + "." + key, message);
    }
}
