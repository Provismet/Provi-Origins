package com.provismet.datagen.proviorigins;

import com.provismet.proviorigins.content.registries.POBlocks;
import com.provismet.proviorigins.content.registries.POItems;
import com.provismet.proviorigins.content.registries.POStatusEffects;
import com.provismet.proviorigins.utility.OriginList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import org.jetbrains.annotations.Nullable;

public class LanguageGenerator extends FabricLanguageProvider {
    protected LanguageGenerator (FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations (TranslationBuilder translationBuilder) {
        translationBuilder.add("category.proviorigins.keys", "Provi's Origins Extra Keys");
        translationBuilder.add("key.proviorigins.tertiary_active", "Active Skill (Tertiary)");
        translationBuilder.add("key.proviorigins.quaternary_active", "Active Skill (Quaternary)");

        translationBuilder.add(POBlocks.LILY_OF_THE_VOID, "Lily of the Void");
        translationBuilder.add(POBlocks.POTTED_LILY_OF_THE_VOID, "Potted Lily of the Void");

        translationBuilder.add(POItems.SOLID_LANTERN, "Lantern");
        translationBuilder.add(POItems.SOUL_LAMP, "Soul Lantern");

        translationBuilder.add(POStatusEffects.VOID_CORRUPTION, "Void Corruption");
        translationBuilder.add(POStatusEffects.UNTARGETABLE, "Illusive");
        translationBuilder.add(POStatusEffects.SLEEP, "Sleeping");
        translationBuilder.add(POStatusEffects.ALERT, "Alert");

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

        addPower(translationBuilder, OriginList.COMMON, "brittle", "Brittle Body", "You have only 5 hearts.");
        addPower(translationBuilder, OriginList.COMMON, "amphibious", "Amphibious", "You can breathe in both air and water.");
        addPower(translationBuilder, OriginList.COMMON, "pescatarian", "Pescatarian", "You can only eat fish.");
        addPower(translationBuilder, OriginList.COMMON, "taller", "Large Body", "You are taller than most with a height of 3 blocks.");
        addPower(translationBuilder, OriginList.COMMON, "slow_swim", "Poor Swimmer", "You swim very slowly.");
        addPower(translationBuilder, OriginList.COMMON, "double_jump", "Floaty Body", "You can double jump.");
        addPower(translationBuilder, OriginList.COMMON, "no_game_events", "Silent Steps", "None of your actions emit vibrations.");
        addPower(translationBuilder, OriginList.COMMON, "slow_fall", "Slow Descent", "Sneaking in the air slows your descent.");
        addPower(translationBuilder, OriginList.COMMON, "exhaust_more", "Hyper Metabolism", "Your hunger depletes 50% faster than others.");
        addPower(translationBuilder, OriginList.COMMON, "double_fire_damage", "Highly Flammable", "You take double damage from fire and lava.");
        addPower(translationBuilder, OriginList.COMMON, "no_food", "Pure Calcium", "You cannot eat food except for milk.");
        addPower(translationBuilder, OriginList.COMMON, "no_potions", "Irregular Chemistry", "You cannot drink potions.");
        addPower(translationBuilder, OriginList.COMMON, "no_splash_potions", "Incompatible Alchemy", "You are unaffected by potion clouds, including splash and lingering potions.");
        addPower(translationBuilder, OriginList.COMMON, "no_crits", "Imprecise", "You cannot perform critical hits with your attacks.");
        addPower(translationBuilder, OriginList.COMMON, "smaller", "Small Body", "You are smaller than most and have a height of 1.15 blocks.");
        addPower(translationBuilder, OriginList.COMMON, "poor_strength", "Weak Melee", "Your melee hits deal 33% less damage.");
        addPower(translationBuilder, OriginList.COMMON, "hearty", "Hearty", "You have 2 additional hearts.");
        addPower(translationBuilder, OriginList.COMMON, "medium_taller", "Grown Body", "You are slightly taller than others with a height of 2.5 blocks.");
        addPower(translationBuilder, OriginList.COMMON, "dried_up", "Dried Up", "Lose hydration when under the sun in hot biomes and always when in underground hot biomes.\nDrinking or standing in water restores hydration. You will catch fire after 30 seconds of losing hydration.");
        addPower(translationBuilder, OriginList.COMMON, "no_trades", "Merchant Ban", "Villagers refuse to acknowledge you. You cannot trade with them.");
        addPower(translationBuilder, OriginList.COMMON, "angry_golems", "Oppressive Iron", "Iron golems view you as hostile and will attack you on-sight.");
        addPower(translationBuilder, OriginList.COMMON, "more_experience", "Enhanced Intellect", "You gain x1.5 experience.");
        addPower(translationBuilder, OriginList.COMMON, "no_bows", "Low Dexterity", "You are unable to use bows and crossbows.");

        addPower(translationBuilder, OriginList.LILY_OF_THE_VOID, "floral_mimicry", "Floral Mimicry", "You may gain mimicry of any compatible plant held in your main hand, consuming the plant in the process. Mimicries grant buffs and last for a duration, until death, or until overridden by another mimicry.");
        addPower(translationBuilder, OriginList.LILY_OF_THE_VOID, "flower_copy__manual_reset", "Born A Lily", "You have natural mimicry of lilies, inflicting poison upon struck entities whilst no other mimicry is active.\nAt any time you may forgo your current mimicry and return to this default state.");
        addPower(translationBuilder, OriginList.LILY_OF_THE_VOID, "nether_air", "Abyssal Collapse", "The fabric of the Nether disagrees with you, preventing you from breathing whilst there.");
        addPower(translationBuilder, OriginList.LILY_OF_THE_VOID, "pollination", "Pollination", "Nearby bees pollinate you, granting saturation but blinding you in the process.");
        addPower(translationBuilder, OriginList.LILY_OF_THE_VOID, "void_growth", "Grow Upon The Skybox", "Gain strength, speed, air, and saturation when in The End or within 4 blocks of Bedrock, portals, or 4 blocks of Crying Obsidian.");

        addPower(translationBuilder, OriginList.KRAKEN_OF_DECAY, "evolved", "Embodiment of Decay", "Slaying the Wither threefold has enhanced your connection to death, granting you access to new skills and an immunity to freshwater.");
        addPower(translationBuilder, OriginList.KRAKEN_OF_DECAY, "deaths_vice", "Death's Vice", "Gain access to a resource called Death's Vice that drains slowly over time.");
        addPower(translationBuilder, OriginList.KRAKEN_OF_DECAY, "deaths_vice_empty", "Death Vision", "Suffer from darkness when Death's Vice reaches zero.");
        addPower(translationBuilder, OriginList.KRAKEN_OF_DECAY, "deaths_vice_gain_on_kill", "Nothing Wasted", "Gain 0.5 bars of Death's Vice when killing an entity.");
        addPower(translationBuilder, OriginList.KRAKEN_OF_DECAY, "deaths_vice_low_value_penalty", "Over-Reliance", "Suffer weakness and slowness when Death's Vice is below 25%.");
        addPower(translationBuilder, OriginList.KRAKEN_OF_DECAY, "deaths_vice_recharge_rate", "Longing Embrace", "Regain Death's Vice when within 5 blocks of any form of soul fire or when holding a soul lantern in your hand. This bonus is increased in the Nether and further increased in soulful biomes.", "For quality of life purposes, you may craft Soul Lanterns into a unique version that cannot be placed but grants the same held effects.");
        addPower(translationBuilder, OriginList.KRAKEN_OF_DECAY, "propulsion", "Ink Propulsion", "Expel a cloud of ink to boost yourself in the direction you're facing. This ability is much stronger in water.", "Costs 1 bar of Death's Vice.");
        addPower(translationBuilder, OriginList.KRAKEN_OF_DECAY, "soul_steal", "Soul Siphon", "Charge a short-ranged beam of soul energy, if it hits an entity inflict damage and heal yourself.", "Costs 1.5 bars of Death's Vice.");
        addPower(translationBuilder, OriginList.KRAKEN_OF_DECAY, "soulsand_spreading", "Soul Capture", "Killing an undead mob whilst standing on coarse dirt will convert it into soul sand.");
        addPower(translationBuilder, OriginList.KRAKEN_OF_DECAY, "water_glow", "Bioluminescence", "Gain a glow when submerged in water.");
        addPower(translationBuilder, OriginList.KRAKEN_OF_DECAY, "water_sensitive", "Sodium Satiation", "Sources of freshwater are harmful to you and will drain Death's Vice over time before damaging your health.", "Freshwater is any Overworld body of water outside of an ocean or beach biome.", "Water Protection enchantments will delay this effect.");
        addPower(translationBuilder, OriginList.KRAKEN_OF_DECAY, "wither_hit", "Touch of Decay", "When Death's Vice is above 6 bars, striking a target consumes half a bar to inflict Wither for 6 seconds.", "Costs 0.5 bars of Death's Vice.");
        addPower(translationBuilder, OriginList.KRAKEN_OF_DECAY, "slow_on_land", "Suction Grip", "You move slightly slower on land.");
        addPower(translationBuilder, OriginList.KRAKEN_OF_DECAY, "tentacle_grapple", "Tentacle Grapple", "Extend a tentacle to grab an entity up to 12 blocks away, pulling them towards you and inflicting slow.", "Costs 1.5 bars of Death's Vice.");
        addPower(translationBuilder, OriginList.KRAKEN_OF_DECAY, "soul_consume", "Soul Collector", "Consume an item that holds a soul. Death's Vice restores based on the strength of the soul held in the item.", "Weak souls grant 0.25 bars of Death's Vice.\nMedium souls grant 1 bar of Death's Vice.\nPerfect souls will refill your Death's Vice completely.");

        addPower(translationBuilder, OriginList.DRAKLING, "chain_crafting", "Forge Master", "You can craft chainmail armor.");
        addPower(translationBuilder, OriginList.DRAKLING, "dragon_evolution", "Becoming Complete", "Wear a dragon head to begin your evolution.");
        addPower(translationBuilder, OriginList.DRAKLING, "enderdragon_mandatory", "Draconic Pull", "The Ender Dragon beckons for you. Your flight stamina decreases rapidly when attempting to flee the dragon.");
        addPower(translationBuilder, OriginList.DRAKLING, "short_flight", "Broken Wings", "You have natural Elytra wings, but your wings are imperfect, allowing flight only in short bursts.");
        addPower(translationBuilder, OriginList.DRAKLING, "true_flight", "Perfected Wings", "You have natural Elytra wings, now perfected you can fly freely and boost yourself in the air.");
        addPower(translationBuilder, OriginList.DRAKLING, "weak_natural_armour", "Molten Metal", "Your newfound strength allows you to wear stronger armor, but your bodily heat loosens the material and makes it weaker than normal.");
        addPower(translationBuilder, OriginList.DRAKLING, "ridable", "Mountable", "Your strength allows you to carry other players. Other players may press [USE] on you to mount you; you may pick up other players by pressing [USE] on them whilst sneaking.", "You cannot carry players in combat with you nor players who already have passengers.", "[SNEAK] to force dismount.");

        addPower(translationBuilder, OriginList.JELLY_SCULK, "break_sculk_fast", "Dismantled", "You can break sculk very quickly, even with your bare hands.");
        addPower(translationBuilder, OriginList.JELLY_SCULK, "buffs_on_sculk", "Comforting Familia", "You gain buffs whilst standing on sculk.");
        addPower(translationBuilder, OriginList.JELLY_SCULK, "create_sensor", "Spreading Your Senses", "You can spend experience to grow a sculk sensor on top of a sculk block.", "[USE] with an empty hand while sneaking to activate.");
        addPower(translationBuilder, OriginList.JELLY_SCULK, "debuffs_off_sculk", "Away From Home", "You are afflicted with darkness when not standing on sculk.");
        addPower(translationBuilder, OriginList.JELLY_SCULK, "entity_detect", "Vibratory Sight", "You can only see entities around you through their vibrations. Sculk Sensors and Shriekers can also extend their signals to you, revealing entities they detect.", "Team members and tamed animals are always visible.");
        addPower(translationBuilder, OriginList.JELLY_SCULK, "spread_sculk", "Spreading Your Domain", "You can spend experience points to spread sculk to any sculk replaceable block.", "[USE] with an empty hand while sneaking to activate.");
        addPower(translationBuilder, OriginList.JELLY_SCULK, "catalyse", "Catalyzer", "Occasionally spread sculk veins when gaining experience.");

        addPower(translationBuilder, OriginList.SPLINTER, "arcane_glow", "Mana Glow", "You glow based on the amount of mana you currently have.");
        addPower(translationBuilder, OriginList.SPLINTER, "fragment", "Fragment", "Summon a clone of yourself at your current location. Summoning clones uses concentration instead of mana.", "Clones inherit your equipment and will fight for you.\nPress [USE] on a clone to 'sit' them like an animal.", "Costs 1 concentration.");
        addPower(translationBuilder, OriginList.SPLINTER, "resource_recharge", "Arcane Energy", "You have access to a mana resource with which to cast spells and concentration to summon clones.");
        addPower(translationBuilder, OriginList.SPLINTER, "distortion", "Illusory Presence", "Blind enemies within a 6 block radius, become untargetable, and create mirror images of yourself and your clones.", "Costs 2 bars of mana.");
        addPower(translationBuilder, OriginList.SPLINTER, "swap", "Misdirection", "Swap places with the clone you are looking at, or if sneaking swap places with the furthest clone within 32 blocks.", "Costs 2 bars of mana.");
        addPower(translationBuilder, OriginList.SPLINTER, "dodge_projectiles", "Evasive", "Dodge projectiles when sneaking.", "Costs 2 bars of mana.");

        addPower(translationBuilder, OriginList.FAERIE_MOTH, "faerie_dust", "Faerie Dust", "Your moth-like body produces a magical powder when near appropriate light sources. Being in direct sunlight or too far from light will erode the powder.", "Artificial fire-related light sources such as torches, campfires, and lanterns allow you to produce Faerie Dust.", "Torches and lanterns held by yourself or other nearby players will also apply to this power.", "For quality of life purposes, you may craft Lanterns into a unique version that cannot be placed but grants the same held effects.");
        addPower(translationBuilder, OriginList.FAERIE_MOTH, "flutter", "Flutter", "Use your wings to boost yourself into the air: you have mid-air jumps.", "Costs 1 bar of Faerie Dust.");
        addPower(translationBuilder, OriginList.FAERIE_MOTH, "crit_passive", "Hypnotic Powder", "Critical strikes with a sword will lace an opponent with a layer faerie dust. A target that has three layers on them at once will be put to sleep for a few seconds.", "Sleep is a new status effect that disables an entity's movement, strength, and vision.\nEntities with Sleep take double damage, but the effect is then immediately removed.", "Costs 1.5 bars of Faerie Dust per layer.");
        addPower(translationBuilder, OriginList.FAERIE_MOTH, "share_buffs", "Blessings of the Fae", "Share speed, strength, and regeneration effects that you currently have with all allies in an 8 block radius.", "Costs 3.5 bars of Faerie Dust.");

        addPower(translationBuilder, OriginList.ALRAUNE, "photosynthesis", "Photosynthesis", "Slowly gain regain food and saturation when exposed to sunlight.");
        addPower(translationBuilder, OriginList.ALRAUNE, "through_grass", "Unrestricted Herb", "Nature moves out of your way, you can strike through foliage when not sneaking.");
        addPower(translationBuilder, OriginList.ALRAUNE, "life_sap", "Sap of Life", "Your connection to nature allows you to harness its power. Using this power allows you to transform flowers into placed turrets that aid you in battle.");
        addPower(translationBuilder, OriginList.ALRAUNE, "summon_plant", "Floral Summoning", "Using your connection to nature allows you to empower flowers into stationary Flower Sprites that damage and hinder nearby enemies.", "Costs 1 Sap of Life.", "Flower Sprites\n- Will take damage if not placed on plantable blocks such as dirt or grass.\n- Will die after 1200 ticks unless fed with a super fertilizer such as dragon breath.", "Flower Sprites have different requirements for different biomes.\n- Most biomes require the Sprite to be exposed to sunlight.\n- For biomes with denser foliage, such as forests, only exposure to the sky is required.\n- Some biomes, such as Lush Caves, will allow Flower Sprites to grow regardless.\n- Use bone meal on a Flower Sprite to bypass this limitation.");

        addPower(translationBuilder, OriginList.CRYSTALLISER, "summon_follower", "Deadly Refraction", "Deplete your crystal shards to summon a full crystal above your head. The crystal follows you and attacks your enemies from up to 32 blocks away.", "Costs every bar of Crystal Shards.");
        addPower(translationBuilder, OriginList.CRYSTALLISER, "crystal_charging", "Shard Collector", "Gain crystal shards when dealing critical hits, taking attacks, and killing enemies.");
        addPower(translationBuilder, OriginList.CRYSTALLISER, "shard_absorb", "Shard Absorption", "Consume some of your crystal shards and regain 2 hearts of health.", "Costs 1 bar of Crystal Shards.");
        addPower(translationBuilder, OriginList.CRYSTALLISER, "explosion_resist", "Explosive Nature", "You take 75% less explosion damage.");

        addDeathMessage(translationBuilder, "freshwater", "%1$s ran out of salt", "%1$s lost too much salt whilst fighting %2$s", "%1$s lost too much salt trying to deal with %2$s's %3$s");
        addDeathMessage(translationBuilder, "kraken_soul_steal", "%1$s had their soul stolen by %2$s", null, "%1$s had their soul stolen by %2$s using %3$s");
        addDeathMessage(translationBuilder, "void_corrupted", "%1$s was corrupted by the void", "%1$s succumbed to the void whilst fighting %2$s", "%1$s succumbed to the void whilst fighting %2$s using %3$s");
        addDeathMessage(translationBuilder, "bad_dimension", "%1$s was rejected by the world", "%1$s returned to the void with help from %2$s", "%1$s returned to the void with help from %2$s using %3$s");
        addDeathMessage(translationBuilder, "compost", "%1$s was composted", "%1$s was composted by %2$s", "%1$s was composted by %2$s using %3$s");
        addDeathMessage(translationBuilder, "crystal_beam", "%1$s was shattered by %2$s", null, "%1$s was shattered by %2$s using %3$s");
        
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
    }

    public static void addOrigin (TranslationBuilder translationBuilder, String origin, String name, String description) {
        StringBuilder builder = new StringBuilder();
        builder.append("origin.proviorigins.").append(origin);
        translationBuilder.add(builder + ".name", name);
        translationBuilder.add(builder + ".description", description);
        translationBuilder.add("item.proviorigins." + origin, name + " Origin Icon");
    }

    public static void addPower (TranslationBuilder translationBuilder, String origin, String power, String name, String description) {
        addPower(translationBuilder, origin, power, name, description, new String[0]);
    }

    public static void addPower (TranslationBuilder translationBuilder, OriginList.OriginEntry origin, String power, String name, String description) {
        addPower(translationBuilder, origin.dataName, power, name, description);
    }

    public static void addPower (TranslationBuilder translationBuilder, OriginList.OriginEntry origin, String power, String name, String description, String... badges) {
        addPower(translationBuilder, origin.dataName, power, name, description, badges);
    }

    public static void addPower (TranslationBuilder translationBuilder, String origin, String power, String name, String description, String... badges) {
        StringBuilder builder = new StringBuilder();
        builder.append("power.proviorigins.").append(origin).append("/").append(power);
        translationBuilder.add(builder + ".name", name);
        translationBuilder.add(builder + ".description", description);

        if (badges.length == 1) {
            translationBuilder.add(builder + ".badge", badges[0]);
        }
        else {
            for (int i = 1; i <= badges.length; ++i) {
                translationBuilder.add(builder + ".badge." + i, badges[i - 1]);
            }
        }
    }

    public static void addDeathMessage (TranslationBuilder translationBuilder, String msgId, String normal, @Nullable String player, @Nullable String item) {
        translationBuilder.add("death.attack." + msgId, normal);
        if (player != null)
            translationBuilder.add("death.attack." + msgId + ".player", player);
        if (item != null) {
            translationBuilder.add("death.attack." + msgId + ".item", item);
        }
    }

    public static void addSubtitle (TranslationBuilder translationBuilder, String origin, String title, String message) {
        translationBuilder.add("subtitles.proviorigins." + origin + "." + title, message);
    }
    
    public static void addTooltip (TranslationBuilder translationBuilder, String origin, String key, String message) {
        translationBuilder.add("tooltip.proviorigins." + origin + "." + key, message);
    }
}
