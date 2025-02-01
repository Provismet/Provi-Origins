package com.provismet.datagen.proviorigins.power;

import com.provismet.datagen.proviorigins.constants.PowerNames;
import com.provismet.datagen.proviorigins.provider.POPowerProvider;
import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.actions.bientity.ParticleBeamAction;
import com.provismet.proviorigins.actions.entity.ActOnClosestEntityAction;
import com.provismet.proviorigins.actions.entity.ActOnFarthestEntityAction;
import com.provismet.proviorigins.actions.entity.ActOnOwnerAction;
import com.provismet.proviorigins.actions.entity.SummonMinionAction;
import com.provismet.proviorigins.conditions.bientity.CanSeeBiEntityConditionType;
import com.provismet.proviorigins.conditions.bientity.FriendlyBiEntityConditionType;
import com.provismet.proviorigins.content.PODamageTypes;
import com.provismet.proviorigins.content.particles.effects.CrystalParticleEffect;
import com.provismet.proviorigins.content.particles.effects.TelegraphParticleEffect;
import com.provismet.proviorigins.powers.ActionOnCriticalHitPower;
import com.provismet.proviorigins.powers.PreventPortalsPower;
import com.provismet.proviorigins.utility.BadgeUtil;
import com.provismet.proviorigins.utility.KeyUtil;
import com.provismet.proviorigins.utility.constants.SpriteLocations;
import com.provismet.proviorigins.utility.tags.POEntityTypeTags;
import io.github.apace100.apoli.action.BiEntityAction;
import io.github.apace100.apoli.action.type.bientity.DamageBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.ActorActionBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.IfElseBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.SequenceBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.TargetActionBiEntityActionType;
import io.github.apace100.apoli.action.type.entity.ChangeResourceEntityActionType;
import io.github.apace100.apoli.action.type.entity.GrantPowerEntityActionType;
import io.github.apace100.apoli.action.type.entity.HealEntityActionType;
import io.github.apace100.apoli.action.type.entity.SetOnFireEntityActionType;
import io.github.apace100.apoli.action.type.entity.SpawnParticlesEntityActionType;
import io.github.apace100.apoli.action.type.entity.TriggerCooldownEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.SequenceEntityActionType;
import io.github.apace100.apoli.condition.BiEntityCondition;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.condition.type.bientity.meta.ActorConditionBiEntityConditionType;
import io.github.apace100.apoli.condition.type.bientity.meta.AllOfBiEntityConditionType;
import io.github.apace100.apoli.condition.type.bientity.meta.TargetConditionBiEntityConditionType;
import io.github.apace100.apoli.condition.type.damage.InTagDamageConditionType;
import io.github.apace100.apoli.condition.type.entity.AdvancementEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.InTagEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.LivingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.MovingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.OnBlockEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.ResourceEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.SneakingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.AllOfEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.AnyOfEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.ConstantEntityConditionType;
import io.github.apace100.apoli.power.PowerReference;
import io.github.apace100.apoli.power.type.ActionOnKeyPressPowerType;
import io.github.apace100.apoli.power.type.ActionOnLandPowerType;
import io.github.apace100.apoli.power.type.ActionOverTimePowerType;
import io.github.apace100.apoli.power.type.CooldownPowerType;
import io.github.apace100.apoli.power.type.ModelColorPowerType;
import io.github.apace100.apoli.power.type.ModifyDamageTakenPowerType;
import io.github.apace100.apoli.power.type.OverlayPowerType;
import io.github.apace100.apoli.power.type.ParticlePowerType;
import io.github.apace100.apoli.power.type.ResourcePowerType;
import io.github.apace100.apoli.power.type.SelfActionOnKillPowerType;
import io.github.apace100.apoli.power.type.StackingStatusEffectPowerType;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.apoli.util.ResourceOperation;
import io.github.apace100.apoli.util.modifier.Modifier;
import io.github.apace100.apoli.util.modifier.ModifierOperation;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Crystalliser {
    private static final int MAX_SHARDS = 140;
    private static final String SHARD_PREFIX = "shards";
    private static final Identifier CRYSTAL_CHARGING_SHARDS = PowerNames.Crystalliser.CRYSTAL_CHARGING.withSuffixedPath("_" + SHARD_PREFIX);
    private static final Identifier SUMMONER_PASSIVES_RESOURCE_CONDITION = PowerNames.Crystalliser.SUMMONER_PASSIVES.withSuffixedPath("_resource_condition");

    public static void build (POPowerProvider.PowerCollector collector) {
        Crystalliser.buildCrystalAttack(collector);
        Crystalliser.buildCrystalCharging(collector);
        Crystalliser.buildCrystalPassives(collector);
        Crystalliser.buildExplosionResist(collector);
        Crystalliser.buildShardAbsorb(collector);
        Crystalliser.buildSummonFollower(collector);
        Crystalliser.buildSummonerPassives(collector);
    }

    private static EntityCondition hasMaxShards () {
        return new ResourceEntityConditionType(
            PowerReference.resource(CRYSTAL_CHARGING_SHARDS),
            Comparison.EQUAL,
            MAX_SHARDS
        ).createCondition();
    }

    private static EntityCondition compareSummonPassiveResource (Comparison comparison, int compareTo) {
        return new ResourceEntityConditionType(
            PowerReference.resource(SUMMONER_PASSIVES_RESOURCE_CONDITION),
            comparison,
            compareTo
        ).createCondition();
    }

    private static List<BiEntityAction> createBeam (float damage, Vector3f colour, List<BiEntityAction> extra) {
        List<BiEntityAction> actions = new ArrayList<>();
        actions.add(new ParticleBeamAction(0.5, new CrystalParticleEffect(colour, 1), 0, 1, 0.25, 1.25).createAction());
        actions.add(new DamageBiEntityActionType(PODamageTypes.CRYSTAL_BEAM.getKey(), Optional.of(damage), List.of()).createAction());
        actions.addAll(extra);

        return actions;
    }

    private static void buildCrystalAttack (POPowerProvider.PowerCollector collector) {
        BiEntityCondition beamCondition = new AllOfBiEntityConditionType(
            List.of(
                new TargetConditionBiEntityConditionType(
                    new LivingEntityConditionType().createCondition()
                ).createCondition(),
                new FriendlyBiEntityConditionType().createCondition(true),
                new CanSeeBiEntityConditionType(
                    RaycastContext.ShapeType.COLLIDER,
                    RaycastContext.FluidHandling.NONE
                ).createCondition()
            )
        ).createCondition();

        collector.add(
            PowerNames.Crystalliser.CRYSTAL_ATTACK,
            new ActionOverTimePowerType(
                Optional.of(
                    new ActOnClosestEntityAction(
                        32,
                        new SequenceBiEntityActionType(
                            Crystalliser.createBeam(4f, new Vector3f(0f, 0.3f, 0.7f), List.of())
                        ).createAction(),
                        beamCondition
                    ).createAction()
                ),
                Optional.empty(),
                Optional.empty(),
                10,
                Optional.empty()
            )
        );

        collector.add(
            PowerNames.Crystalliser.CRYSTAL_ATTACK_UPGRADE,
            new ActionOverTimePowerType(
                Optional.of(
                    new SequenceEntityActionType(
                        List.of(
                            new ActOnClosestEntityAction(
                                32,
                                new SequenceBiEntityActionType(
                                    Crystalliser.createBeam(7f, new Vector3f(0f, 0.3f, 0.7f), List.of())
                                ).createAction(),
                                beamCondition
                            ).createAction(),
                            new ActOnFarthestEntityAction(
                                32,
                                new SequenceBiEntityActionType(
                                    Crystalliser.createBeam(3f, new Vector3f(0.7f, 0.3f, 0f),
                                        List.of(
                                            new TargetActionBiEntityActionType(
                                                new SetOnFireEntityActionType(2).createAction()
                                            ).createAction()
                                        )
                                    )
                                ).createAction(),
                                beamCondition
                            ).createAction()
                        )
                    ).createAction()
                ),
                Optional.empty(),
                Optional.empty(),
                10,
                Optional.empty()
            )
        );
    }

    private static void buildCrystalCharging (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Crystalliser.CRYSTAL_CHARGING,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    SHARD_PREFIX,
                    new ResourcePowerType(
                        Optional.empty(),
                        Optional.empty(),
                        new HudRender(
                            Optional.of(Crystalliser.compareSummonPassiveResource(Comparison.EQUAL, 0)),
                            SpriteLocations.EXTRA_RESOURCES,
                            true,
                            false,
                            3,
                            3,
                            0
                        ),
                        0,
                        MAX_SHARDS,
                        0
                    )
                ).add(
                    "attacking",
                    new ActionOnCriticalHitPower(
                        new IfElseBiEntityActionType(
                            new TargetConditionBiEntityConditionType(
                                new InTagEntityConditionType(POEntityTypeTags.GRANTS_EXTRA_SHARDS).createCondition()
                            ).createCondition(),
                            new ActorActionBiEntityActionType(
                                new ChangeResourceEntityActionType(
                                    PowerReference.resource(CRYSTAL_CHARGING_SHARDS),
                                    ResourceOperation.ADD,
                                    10
                                ).createAction()
                            ).createAction(),
                            Optional.of(
                                new ActorActionBiEntityActionType(
                                    new ChangeResourceEntityActionType(
                                        PowerReference.resource(CRYSTAL_CHARGING_SHARDS),
                                        ResourceOperation.ADD,
                                        7
                                    ).createAction()
                                ).createAction()
                            )
                        ).createAction(),
                        Optional.of(
                            new TargetConditionBiEntityConditionType(
                                new LivingEntityConditionType().createCondition()
                            ).createCondition()
                        ),
                        Optional.of(Crystalliser.compareSummonPassiveResource(Comparison.EQUAL, 0))
                    )
                )
        );
    }

    private static void buildCrystalPassives (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Crystalliser.CRYSTAL_PASSIVES,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "translucent",
                    new ModelColorPowerType(1f, 1f, 1f, 0.65f, Optional.empty())
                ).add(
                    "life_steal",
                    new SelfActionOnKillPowerType(
                        new ActOnOwnerAction(
                            new TargetActionBiEntityActionType(
                                new HealEntityActionType(2f).createAction()
                            ).createAction(),
                            Optional.empty()
                        ).createAction(),
                        Optional.empty(),
                        Optional.empty(),
                        HudRender.DONT_RENDER,
                        1,
                        Optional.empty()
                    )
                )
        );
    }

    private static void buildExplosionResist (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Crystalliser.EXPLOSION_RESIST,
            new ModifyDamageTakenPowerType(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(new InTagDamageConditionType(DamageTypeTags.IS_EXPLOSION).createCondition()),
                List.of(Modifier.of(ModifierOperation.MULTIPLY_TOTAL_ADDITIVE, -0.75)),
                Optional.empty()
            )
        );
    }

    private static void buildShardAbsorb (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Crystalliser.SHARD_ABSORB,
            new ActionOnKeyPressPowerType(
                new SequenceEntityActionType(
                    List.of(
                        new HealEntityActionType(4f).createAction(),
                        new ChangeResourceEntityActionType(
                            PowerReference.resource(CRYSTAL_CHARGING_SHARDS),
                            ResourceOperation.ADD,
                            -20
                        ).createAction()
                    )
                ).createAction(),
                HudRender.DONT_RENDER,
                1,
                KeyUtil.secondary(),
                Optional.of(
                    new ResourceEntityConditionType(
                        PowerReference.resource(CRYSTAL_CHARGING_SHARDS),
                        Comparison.GREATER_THAN_OR_EQUAL,
                        20
                    ).createCondition()
                )
            ),
            List.of(
                BadgeUtil.active(),
                BadgeUtil.cost(PowerNames.Crystalliser.SHARD_ABSORB)
            )
        );
    }

    private static void buildSummonFollower (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Crystalliser.SUMMON_FOLLOWER,
            new ActionOnKeyPressPowerType(
                new SequenceEntityActionType(
                    List.of(
                        new SummonMinionAction(
                            ProviOriginsMain.identifier("textures/entity/crystal.png"),
                            true,
                            new Vec3d(0, 2.5, 0),
                            1f,
                            true,
                            500,
                            Optional.of(
                                new SequenceBiEntityActionType(
                                    List.of(
                                        new TargetActionBiEntityActionType(
                                            new GrantPowerEntityActionType(
                                                PowerReference.of(PowerNames.Crystalliser.CRYSTAL_PASSIVES),
                                                PowerNames.Crystalliser.SUMMON_FOLLOWER
                                            ).createAction()
                                        ).createAction(),
                                        new IfElseBiEntityActionType(
                                            new ActorConditionBiEntityConditionType(
                                                new AdvancementEntityConditionType(
                                                    Identifier.ofVanilla("end/kill_dragon")
                                                ).createCondition()
                                            ).createCondition(),
                                            new TargetActionBiEntityActionType(
                                                new GrantPowerEntityActionType(
                                                    PowerReference.of(PowerNames.Crystalliser.CRYSTAL_ATTACK_UPGRADE),
                                                    PowerNames.Crystalliser.SUMMON_FOLLOWER
                                                ).createAction()
                                            ).createAction(),
                                            Optional.of(
                                                new TargetActionBiEntityActionType(
                                                    new GrantPowerEntityActionType(
                                                        PowerReference.of(PowerNames.Crystalliser.CRYSTAL_ATTACK),
                                                        PowerNames.Crystalliser.SUMMON_FOLLOWER
                                                    ).createAction()
                                                ).createAction()
                                            )
                                        ).createAction()
                                    )
                                ).createAction()
                            )
                        ).createAction(),
                        new TriggerCooldownEntityActionType(
                            PowerReference.of(SUMMONER_PASSIVES_RESOURCE_CONDITION)
                        ).createAction(),
                        new ChangeResourceEntityActionType(
                            PowerReference.resource(CRYSTAL_CHARGING_SHARDS),
                            ResourceOperation.SET,
                            0
                        ).createAction()
                    )
                ).createAction(),
                HudRender.DONT_RENDER,
                1,
                KeyUtil.primary(),
                Optional.of(
                    new AllOfEntityConditionType(
                        List.of(
                            Crystalliser.compareSummonPassiveResource(Comparison.EQUAL, 0),
                            Crystalliser.hasMaxShards()
                        )
                    ).createCondition()
                )
            ),
            List.of(
                BadgeUtil.active(),
                BadgeUtil.cost(PowerNames.Crystalliser.SUMMON_FOLLOWER),
                BadgeUtil.dragon()
            )
        );
    }

    private static void buildSummonerPassives (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Crystalliser.SUMMONER_PASSIVES,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "resource_condition",
                    new CooldownPowerType(
                        500,
                        new HudRender(
                            Optional.of(new ConstantEntityConditionType(true).createCondition()),
                            SpriteLocations.EXTRA_RESOURCES,
                            true,
                            true,
                            3,
                            3,
                            0
                        )
                    )
                ).add(
                    "weakened",
                    new StackingStatusEffectPowerType(
                        List.of(
                            new StatusEffectInstance(StatusEffects.WEAKNESS, 15, 0, true, false, true),
                            new StatusEffectInstance(StatusEffects.SLOWNESS, 15, 2, true, false, true)
                        ),
                        -2, 2,
                        10,
                        10,
                        Optional.of(
                            new AllOfEntityConditionType(
                                List.of(
                                    Crystalliser.compareSummonPassiveResource(Comparison.GREATER_THAN, 0),
                                    new AdvancementEntityConditionType(
                                        Identifier.ofVanilla("end/kill_dragon")
                                    ).createCondition(true)
                                )
                            ).createCondition()
                        )
                    )
                ).add(
                    "altered",
                    new StackingStatusEffectPowerType(
                        List.of(
                            new StatusEffectInstance(StatusEffects.WEAKNESS, 15, 0, true, false, true),
                            new StatusEffectInstance(StatusEffects.SLOWNESS, 15, 2, true, false, true),
                            new StatusEffectInstance(StatusEffects.RESISTANCE, 15, 0, true, true, true)
                        ),
                        -2, 2,
                        10,
                        10,
                        Optional.of(
                            new AllOfEntityConditionType(
                                List.of(
                                    Crystalliser.compareSummonPassiveResource(Comparison.GREATER_THAN, 0),
                                    new AdvancementEntityConditionType(
                                        Identifier.ofVanilla("end/kill_dragon")
                                    ).createCondition()
                                )
                            ).createCondition()
                        )
                    )
                ).add(
                    "overlay",
                    new OverlayPowerType(
                        ProviOriginsMain.identifier("textures/misc/crystal.png"),
                        OverlayPowerType.DrawMode.NAUSEA,
                        OverlayPowerType.DrawPhase.BELOW_HUD,
                        0.6f,
                        0.7f, 0f, 0.7f,
                        true,
                        false,
                        0,
                        Optional.of(Crystalliser.compareSummonPassiveResource(Comparison.GREATER_THAN, 0))
                    )
                ).add(
                    "particle_aura",
                    new ParticlePowerType(
                        Optional.empty(),
                        new TelegraphParticleEffect(new Vector3f(0.8f, 0, 0.8f), 0.6f, 0.5f, 20),
                        Vec3d.ZERO,
                        new Vec3d(0, 0.1, 0),
                        5,
                        1,
                        0,
                        true,
                        false,
                        false,
                        Optional.of(
                            new AllOfEntityConditionType(
                                List.of(
                                    new AnyOfEntityConditionType(
                                        List.of(
                                            Crystalliser.compareSummonPassiveResource(Comparison.GREATER_THAN, 0),
                                            Crystalliser.hasMaxShards()
                                        )
                                    ).createCondition(),
                                    new MovingEntityConditionType(true, true).createCondition(),
                                    new OnBlockEntityConditionType(Optional.empty()).createCondition(),
                                    new SneakingEntityConditionType().createCondition(true)
                                )
                            ).createCondition()
                        )
                    )
                ).add(
                    "particle_landing",
                    new ActionOnLandPowerType(
                        new SpawnParticlesEntityActionType(
                            Optional.empty(),
                            new TelegraphParticleEffect(new Vector3f(0.8f, 0, 0.8f), 0.6f, 0.5f, 20),
                            new Vec3d(0, 0.1, 0),
                            Vec3d.ZERO,
                            false,
                            0,
                            1
                        ).createAction(),
                        Optional.of(
                            new AnyOfEntityConditionType(
                                List.of(
                                    Crystalliser.compareSummonPassiveResource(Comparison.GREATER_THAN, 0),
                                    Crystalliser.hasMaxShards()
                                )
                            ).createCondition()
                        )
                    )
                ).add(
                    "no_portals",
                    new PreventPortalsPower(
                        Optional.of(Crystalliser.compareSummonPassiveResource(Comparison.GREATER_THAN, 0))
                    )
                ),
            true
        );
    }
}
