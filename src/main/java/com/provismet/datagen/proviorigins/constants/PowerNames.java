package com.provismet.datagen.proviorigins.constants;

import com.provismet.proviorigins.utility.OriginList;
import net.minecraft.util.Identifier;

/**
 * Utility class for the names of powers. This is used for easier handling of language files and referencing.
 */
public interface PowerNames {
    interface Common {
        Identifier AMPHIBIOUS = OriginList.COMMON.identifier("amphibious");
        Identifier ANGRY_GOLEMS = OriginList.COMMON.identifier("angry_golems");
        Identifier BRITTLE = OriginList.COMMON.identifier("brittle");
        Identifier DOUBLE_FIRE_DAMAGE = OriginList.COMMON.identifier("double_fire_damage");
        Identifier DOUBLE_JUMP = OriginList.COMMON.identifier("double_jump");
        Identifier DRIED_UP = OriginList.COMMON.identifier("dried_up");
        Identifier EXHAUST_MORE = OriginList.COMMON.identifier("exhaust_more");
        Identifier HEARTY = OriginList.COMMON.identifier("hearty");
        Identifier IGNORE_CORRUPTION = OriginList.COMMON.identifier("ignore_corruption");
        Identifier IGNORE_DARKNESS_PULSE = OriginList.COMMON.identifier("ignore_darkness_pulse");
        Identifier MEDIUM_TALLER = OriginList.COMMON.identifier("medium_taller");
        Identifier MORE_EXPERIENCE = OriginList.COMMON.identifier("more_experience");
        Identifier NO_BOWS = OriginList.COMMON.identifier("no_bows");
        Identifier NO_CRITS = OriginList.COMMON.identifier("no_crits");
        Identifier NO_FOOD = OriginList.COMMON.identifier("no_food");
        Identifier NO_GAME_EVENTS = OriginList.COMMON.identifier("no_game_events");
        Identifier NO_POTIONS = OriginList.COMMON.identifier("no_potions");
        Identifier NO_SPLASH_POTIONS = OriginList.COMMON.identifier("no_splash_potions");
        Identifier NO_TRADES = OriginList.COMMON.identifier("no_trades");
        Identifier PESCATARIAN = OriginList.COMMON.identifier("pescatarian");
        Identifier POOR_STRENGTH = OriginList.COMMON.identifier("poor_strength");
        Identifier SLOW_FALL = OriginList.COMMON.identifier("slow_fall");
        Identifier SLOW_SWIM = OriginList.COMMON.identifier("slow_swim");
        Identifier SMALLER = OriginList.COMMON.identifier("smaller");
        Identifier TALLER = OriginList.COMMON.identifier("taller");
    }

    interface Alraune {
        Identifier HIDDEN_PASSIVES = OriginList.ALRAUNE.identifier("hidden_passives");
        Identifier LIFE_SAP = OriginList.ALRAUNE.identifier("life_sap");
        Identifier LIFE_SAP_RECHARGE = OriginList.ALRAUNE.identifier("life_sap_recharge");
        Identifier LIFE_SAP_RESET_ON_DEATH = OriginList.ALRAUNE.identifier("life_sap_reset_on_death");
        Identifier PHOTOSYNTHESIS = OriginList.ALRAUNE.identifier("photosynthesis");
        Identifier PLAP_PLAP_GIVE_PITCHER_POD = OriginList.ALRAUNE.identifier("plap_plap_give_pitcher_pod");
        Identifier SUMMONED_AOE = OriginList.ALRAUNE.identifier("summoned_aoe");
        Identifier SUMMONED_AOE_UPGRADE = OriginList.ALRAUNE.identifier("summoned_aoe_upgrade");
        Identifier SUMMONED_PASSIVES = OriginList.ALRAUNE.identifier("summoned_passives");
        Identifier SUMMONED_PROJECTILE = OriginList.ALRAUNE.identifier("summoned_projectile");
        Identifier SUMMONED_PROJECTILE_UPGRADE = OriginList.ALRAUNE.identifier("summoned_projectile_upgrade");
        Identifier SUMMONED_PULL = OriginList.ALRAUNE.identifier("summoned_pull");
        Identifier SUMMONED_PULL_UPGRADE = OriginList.ALRAUNE.identifier("summoned_pull_upgrade");
        Identifier SUMMONED_PUSH = OriginList.ALRAUNE.identifier("summoned_push");
        Identifier SUMMONED_PUSH_UPGRADE = OriginList.ALRAUNE.identifier("summoned_push_upgrade");
        Identifier SUMMON_PLANT_AOE = OriginList.ALRAUNE.identifier("summon_plant_aoe");
        Identifier SUMMON_PLANT_PROJECTILE = OriginList.ALRAUNE.identifier("summon_plant_projectile");
        Identifier SUMMON_PLANT_PULL = OriginList.ALRAUNE.identifier("summon_plant_pull");
        Identifier SUMMON_PLANT_PUSH = OriginList.ALRAUNE.identifier("summon_plant_push");
        Identifier THROUGH_GRASS = OriginList.ALRAUNE.identifier("through_grass");
    }

    interface Crystalliser {
        Identifier CRYSTAL_ATTACK = OriginList.CRYSTALLISER.identifier("crystal_attack");
        Identifier CRYSTAL_ATTACK_UPGRADE = OriginList.CRYSTALLISER.identifier("crystal_attack_upgrade");
        Identifier CRYSTAL_CHARGING = OriginList.CRYSTALLISER.identifier("crystal_charging");
        Identifier CRYSTAL_PASSIVES = OriginList.CRYSTALLISER.identifier("crystal_passives");
        Identifier EXPLOSION_RESIST = OriginList.CRYSTALLISER.identifier("explosion_resist");
        Identifier SHARD_ABSORB = OriginList.CRYSTALLISER.identifier("shard_absorb");
        Identifier SUMMONER_PASSIVES = OriginList.CRYSTALLISER.identifier("summoner_passives");
        Identifier SUMMON_FOLLOWER = OriginList.CRYSTALLISER.identifier("summon_follower");
    }

    interface DecayKraken {
        Identifier DEATHS_VICE = OriginList.KRAKEN_OF_DECAY.identifier("deaths_vice");
        Identifier DEATHS_VICE_EMPTY = OriginList.KRAKEN_OF_DECAY.identifier("deaths_vice_empty");
        Identifier DEATHS_VICE_GAIN_ON_KILL = OriginList.KRAKEN_OF_DECAY.identifier("deaths_vice_gain_on_kill");
        Identifier DEATHS_VICE_LOW_VALUE_PENALTY = OriginList.KRAKEN_OF_DECAY.identifier("deaths_vice_low_value_penalty");
        Identifier DEATHS_VICE_PASSIVE_DRAIN_AND_GAIN = OriginList.KRAKEN_OF_DECAY.identifier("deaths_vice_passive_drain_and_gain");
        Identifier DEATHS_VICE_RECHARGE_RATE = OriginList.KRAKEN_OF_DECAY.identifier("deaths_vice_recharge_rate");
        Identifier DEATHS_VICE_RECHARGE_RESOURCE = OriginList.KRAKEN_OF_DECAY.identifier("deaths_vice_recharge_resource");
        Identifier DEATHS_VICE_RESET_ON_DEATH = OriginList.KRAKEN_OF_DECAY.identifier("deaths_vice_reset_on_death");
        Identifier DROP_INK_ON_DEATH = OriginList.KRAKEN_OF_DECAY.identifier("drop_ink_on_death");
        Identifier PROPULSION = OriginList.KRAKEN_OF_DECAY.identifier("propulsion");
        Identifier SLOW_ON_LAND = OriginList.KRAKEN_OF_DECAY.identifier("slow_on_land");
        Identifier SOULSAND_SPREADING = OriginList.KRAKEN_OF_DECAY.identifier("soulsand_spreading");
        Identifier SOUL_CONSUME = OriginList.KRAKEN_OF_DECAY.identifier("soul_consume");
        Identifier SOUL_STEAL = OriginList.KRAKEN_OF_DECAY.identifier("soul_steal");
        Identifier TENTACLE_GRAPPLE = OriginList.KRAKEN_OF_DECAY.identifier("tentacle_grapple");
        Identifier WATER_GLOW = OriginList.KRAKEN_OF_DECAY.identifier("water_glow");
        Identifier WATER_SENSITIVE = OriginList.KRAKEN_OF_DECAY.identifier("water_sensitive");
        Identifier WITHER_HIT = OriginList.KRAKEN_OF_DECAY.identifier("wither_hit");
    }

    interface Drakling {
        Identifier CHAIN_CRAFTING = OriginList.DRAKLING.identifier("chain_crafting");
        Identifier DRAGON_EVOLUTION = OriginList.DRAKLING.identifier("dragon_evolution");
        Identifier ENDERDRAGON_MANDATORY = OriginList.DRAKLING.identifier("enderdragon_mandatory");
        Identifier REMOVE_DRAGON_HEAD = OriginList.DRAKLING.identifier("remove_dragon_head");
        Identifier RIDABLE = OriginList.DRAKLING.identifier("ridable");
        Identifier SHORT_FLIGHT = OriginList.DRAKLING.identifier("short_flight");
        Identifier TRUE_FLIGHT = OriginList.DRAKLING.identifier("true_flight");
        Identifier WEAK_NATURAL_ARMOUR = OriginList.DRAKLING.identifier("weak_natural_armour");
    }

    interface FaeMoth {
        Identifier CRIT_PASSIVE = OriginList.FAERIE_MOTH.identifier("crit_passive");
        Identifier FAERIE_DUST = OriginList.FAERIE_MOTH.identifier("faerie_dust");
        Identifier FAERIE_DUST_CHARGING = OriginList.FAERIE_MOTH.identifier("faerie_dust_charging");
        Identifier FLUTTER = OriginList.FAERIE_MOTH.identifier("flutter");
        Identifier SHARE_BUFFS = OriginList.FAERIE_MOTH.identifier("share_buffs");
        Identifier STACKING_PASSIVE = OriginList.FAERIE_MOTH.identifier("stacking_passive");
    }

    interface JellySculk {
        Identifier BREAK_SCULK_FAST = OriginList.JELLY_SCULK.identifier("break_sculk_fast");
        Identifier BUFFS_ON_SCULK = OriginList.JELLY_SCULK.identifier("buffs_on_sculk");
        Identifier CATALYSE = OriginList.JELLY_SCULK.identifier("catalyse");
        Identifier CREATE_SENSOR = OriginList.JELLY_SCULK.identifier("create_sensor");
        Identifier DEBUFFS_OFF_SCULK = OriginList.JELLY_SCULK.identifier("debuffs_off_sculk");
        Identifier ENTITY_DETECT = OriginList.JELLY_SCULK.identifier("entity_detect");
        Identifier IS_DETECTED = OriginList.JELLY_SCULK.identifier("is_detected");
        Identifier SCOREBOARD_MANAGER = OriginList.JELLY_SCULK.identifier("scoreboard_manager");
        Identifier SPREAD_SCULK = OriginList.JELLY_SCULK.identifier("spread_sculk");
    }

    interface Splinter {
        Identifier ARCANE_GLOW = OriginList.SPLINTER.identifier("arcane_glow");
        Identifier CONCENTRATION = OriginList.SPLINTER.identifier("concentration");
        Identifier DISTORTION = OriginList.SPLINTER.identifier("distortion");
        Identifier DODGE_PROJECTILES = OriginList.SPLINTER.identifier("dodge_projectiles");
        Identifier FRAGMENT = OriginList.SPLINTER.identifier("fragment");
        Identifier MANA = OriginList.SPLINTER.identifier("mana");
        Identifier RESET_RESOURCES = OriginList.SPLINTER.identifier("reset_resources");
        Identifier RESET_RESOURCES_ON_DEATH = OriginList.SPLINTER.identifier("reset_resources_on_death");
        Identifier RESOURCE_RECHARGE = OriginList.SPLINTER.identifier("resource_recharge");
        Identifier SWAP = OriginList.SPLINTER.identifier("swap");
    }

    interface VoidLily {
        Identifier COMPOSTER_DEATH = OriginList.LILY_OF_THE_VOID.identifier("composter_death");
        Identifier DEATH_PARTICLES = OriginList.LILY_OF_THE_VOID.identifier("death_particles");
        Identifier FIX_REGEN = OriginList.LILY_OF_THE_VOID.identifier("fix_regen");
        Identifier FLOWER_COPY_ALLIUM = OriginList.LILY_OF_THE_VOID.identifier("flower_copy_allium");
        Identifier FLOWER_COPY_AZUREBLUET = OriginList.LILY_OF_THE_VOID.identifier("flower_copy_azurebluet");
        Identifier FLOWER_COPY_BLUEORCHID_DANDELION = OriginList.LILY_OF_THE_VOID.identifier("flower_copy_blueorchid_dandelion");
        Identifier FLOWER_COPY_CACTUS_ROSE_BERRY = OriginList.LILY_OF_THE_VOID.identifier("flower_copy_cactus_rose_berry");
        Identifier FLOWER_COPY_CHORUS = OriginList.LILY_OF_THE_VOID.identifier("flower_copy_chorus");
        Identifier FLOWER_COPY_CORNFLOWER = OriginList.LILY_OF_THE_VOID.identifier("flower_copy_cornflower");
        Identifier FLOWER_COPY_GLOW = OriginList.LILY_OF_THE_VOID.identifier("flower_copy_glow");
        Identifier FLOWER_COPY_LILY = OriginList.LILY_OF_THE_VOID.identifier("flower_copy_lily");
        Identifier FLOWER_COPY_OXEYE = OriginList.LILY_OF_THE_VOID.identifier("flower_copy_oxeye");
        Identifier FLOWER_COPY_POPPY = OriginList.LILY_OF_THE_VOID.identifier("flower_copy_poppy");
        Identifier FLOWER_COPY_SUGARCANE = OriginList.LILY_OF_THE_VOID.identifier("flower_copy_sugarcane");
        Identifier FLOWER_COPY_TULIPS = OriginList.LILY_OF_THE_VOID.identifier("flower_copy_tulips");
        Identifier FLOWER_COPY_WATERBREATHING = OriginList.LILY_OF_THE_VOID.identifier("flower_copy_waterbreathing");
        Identifier FLOWER_COPY_WITHER = OriginList.LILY_OF_THE_VOID.identifier("flower_copy_wither");
        Identifier FLOWER_COPY_WOOD = OriginList.LILY_OF_THE_VOID.identifier("flower_copy_wood");
        Identifier FLOWER_COPY__DRAIN = OriginList.LILY_OF_THE_VOID.identifier("flower_copy__drain");
        Identifier FLOWER_COPY__MANUAL_RESET = OriginList.LILY_OF_THE_VOID.identifier("flower_copy__manual_reset");
        Identifier FLOWER_COPY__RESET_ON_DEATH = OriginList.LILY_OF_THE_VOID.identifier("flower_copy__reset_on_death");
        Identifier FLOWER_COPY__RESOURCE = OriginList.LILY_OF_THE_VOID.identifier("flower_copy__resource");
        Identifier NETHER_AIR = OriginList.LILY_OF_THE_VOID.identifier("nether_air");
        Identifier PLAP_PLAP_GET_BONEMEALED = OriginList.LILY_OF_THE_VOID.identifier("plap_plap_get_bonemealed");
        Identifier POLLINATION = OriginList.LILY_OF_THE_VOID.identifier("pollination");
        Identifier VOID_GROWTH = OriginList.LILY_OF_THE_VOID.identifier("void_growth");
    }
}
