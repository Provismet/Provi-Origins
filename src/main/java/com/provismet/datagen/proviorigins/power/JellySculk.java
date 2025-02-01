package com.provismet.datagen.proviorigins.power;

import com.provismet.datagen.proviorigins.constants.PowerNames;
import com.provismet.datagen.proviorigins.provider.POPowerProvider;
import com.provismet.proviorigins.conditions.bientity.TeammateBiEntityConditionType;
import com.provismet.proviorigins.powers.ActionOnDetectVibrationPower;
import com.provismet.proviorigins.powers.ActionOnGainExpPower;
import com.provismet.proviorigins.utility.BadgeUtil;
import com.provismet.proviorigins.utility.PowerUtil;
import com.provismet.proviorigins.utility.tags.POBlockTags;
import com.provismet.proviorigins.utility.tags.POEntityTypeTags;
import io.github.apace100.apoli.action.EntityAction;
import io.github.apace100.apoli.action.type.bientity.meta.ActorActionBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.TargetActionBiEntityActionType;
import io.github.apace100.apoli.action.type.block.AddBlockBlockActionType;
import io.github.apace100.apoli.action.type.block.AreaOfEffectBlockActionType;
import io.github.apace100.apoli.action.type.block.SetBlockBlockActionType;
import io.github.apace100.apoli.action.type.block.meta.DelayBlockActionType;
import io.github.apace100.apoli.action.type.block.meta.IfElseListBlockActionType;
import io.github.apace100.apoli.action.type.block.meta.OffsetBlockActionType;
import io.github.apace100.apoli.action.type.block.meta.RandomChanceBlockActionType;
import io.github.apace100.apoli.action.type.entity.BlockActionAtEntityActionType;
import io.github.apace100.apoli.action.type.entity.ExecuteCommandEntityActionType;
import io.github.apace100.apoli.action.type.entity.PlaySoundEntityActionType;
import io.github.apace100.apoli.action.type.entity.SpawnParticlesEntityActionType;
import io.github.apace100.apoli.action.type.entity.TriggerCooldownEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.SequenceEntityActionType;
import io.github.apace100.apoli.action.type.meta.IfElseListMetaActionType;
import io.github.apace100.apoli.condition.type.BiEntityConditionType;
import io.github.apace100.apoli.condition.type.BlockConditionType;
import io.github.apace100.apoli.condition.type.bientity.DistanceBiEntityConditionType;
import io.github.apace100.apoli.condition.type.bientity.OwnerBiEntityConditionType;
import io.github.apace100.apoli.condition.type.bientity.meta.ActorConditionBiEntityConditionType;
import io.github.apace100.apoli.condition.type.bientity.meta.AllOfBiEntityConditionType;
import io.github.apace100.apoli.condition.type.bientity.meta.UndirectedBiEntityConditionType;
import io.github.apace100.apoli.condition.type.block.BlockBlockConditionType;
import io.github.apace100.apoli.condition.type.block.InTagBlockConditionType;
import io.github.apace100.apoli.condition.type.block.meta.AllOfBlockConditionType;
import io.github.apace100.apoli.condition.type.block.meta.AnyOfBlockConditionType;
import io.github.apace100.apoli.condition.type.block.meta.OffsetBlockConditionType;
import io.github.apace100.apoli.condition.type.entity.InBlockEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.InTagEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.LivingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.OnBlockEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.PowerEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.ResourceEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.SneakingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.XpPointsEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.AllOfEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.AnyOfEntityConditionType;
import io.github.apace100.apoli.condition.type.item.EmptyItemConditionType;
import io.github.apace100.apoli.power.PowerReference;
import io.github.apace100.apoli.power.type.ActionOnBlockUsePowerType;
import io.github.apace100.apoli.power.type.ActionOnCallbackPowerType;
import io.github.apace100.apoli.power.type.ActionOnHitPowerType;
import io.github.apace100.apoli.power.type.ActionWhenHitPowerType;
import io.github.apace100.apoli.power.type.CooldownPowerType;
import io.github.apace100.apoli.power.type.EntityGlowPowerType;
import io.github.apace100.apoli.power.type.ModifyBreakSpeedPowerType;
import io.github.apace100.apoli.power.type.PreventEntityRenderPowerType;
import io.github.apace100.apoli.power.type.StackingStatusEffectPowerType;
import io.github.apace100.apoli.power.type.meta.DummyPowerType;
import io.github.apace100.apoli.util.BlockUsagePhase;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.apoli.util.Shape;
import io.github.apace100.apoli.util.modifier.Modifier;
import io.github.apace100.apoli.util.modifier.ModifierOperation;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.SculkChargeParticleEffect;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.GameEventTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public abstract class JellySculk {
    private static final Identifier SPREAD_SCULK_TIMER = PowerUtil.subPower(PowerNames.JellySculk.SPREAD_SCULK, "timer");

    public static void build (POPowerProvider.PowerCollector collector) {
        JellySculk.buildBreakSculkFast(collector);
        JellySculk.buildBuffsOnSculk(collector);
        JellySculk.buildCatalyse(collector);
        JellySculk.buildCreateSensor(collector);
        JellySculk.buildDebuffsOffSculk(collector);
        JellySculk.buildEntityDetect(collector);
        JellySculk.buildIsDetected(collector);
        JellySculk.buildScoreboardManager(collector);
        JellySculk.buildSpreadSculk(collector);
    }

    private static EntityAction subtractExp (int amount) {
        return new ExecuteCommandEntityActionType(
            "experience add @s -%d points".formatted(amount)
        ).createAction();
    }

    private static void buildBreakSculkFast (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.JellySculk.BREAK_SCULK_FAST,
            new ModifyBreakSpeedPowerType(
                Optional.of(
                    new InTagBlockConditionType(POBlockTags.SCULK).createCondition()
                ),
                null,
                List.of(
                    Modifier.of(
                        ModifierOperation.MULTIPLY_BASE_MULTIPLICATIVE,
                        10
                    )
                ),
                Optional.empty()
            )
        );
    }

    private static void buildBuffsOnSculk (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.JellySculk.BUFFS_ON_SCULK,
            new StackingStatusEffectPowerType(
                List.of(
                    new StatusEffectInstance(
                        StatusEffects.SPEED,
                        100,
                        0,
                        true,
                        false,
                        false
                    )
                ),
                -1, 5,
                20,
                20,
                Optional.of(
                    new AnyOfEntityConditionType(
                        List.of(
                            new OnBlockEntityConditionType(
                                Optional.of(new InTagBlockConditionType(POBlockTags.SCULK).createCondition())
                            ).createCondition(),
                            new InBlockEntityConditionType(
                                new InTagBlockConditionType(POBlockTags.SCULK).createCondition()
                            ).createCondition()
                        )
                    ).createCondition()
                )
            )
        );
    }

    private static void buildCatalyse (POPowerProvider.PowerCollector collector) {
        BlockState downwardsVein = Blocks.SCULK_VEIN.getDefaultState().with(Properties.DOWN, true);

        collector.add(
            PowerNames.JellySculk.CATALYSE,
            new ActionOnGainExpPower(
                new SequenceEntityActionType(
                    List.of(
                        new BlockActionAtEntityActionType(
                            new AreaOfEffectBlockActionType(
                                new RandomChanceBlockActionType(
                                    new AddBlockBlockActionType(downwardsVein).createAction(),
                                    Optional.empty(),
                                    0.2f
                                ).createAction(),
                                Optional.of(
                                    new AllOfBlockConditionType(
                                        List.of(
                                            new InTagBlockConditionType(BlockTags.SCULK_REPLACEABLE).createCondition(),
                                            new OffsetBlockConditionType(
                                                new InTagBlockConditionType(BlockTags.AIR).createCondition(),
                                                new Vec3i(0, 1, 0)
                                            ).createCondition()
                                        )
                                    ).createCondition()
                                ),
                                Shape.SPHERE,
                                4
                            ).createAction()
                        ).createAction(),
                        new SpawnParticlesEntityActionType(
                            Optional.empty(),
                            new SculkChargeParticleEffect(0f),
                            new Vec3d(0, 0.15, 0),
                            new Vec3d(0.5, 0, 0.5),
                            false,
                            0,
                            3
                        ).createAction()
                    )
                ).createAction(),
                Comparison.GREATER_THAN_OR_EQUAL, 1,
                Optional.empty()
            )
        );
    }

    private static void buildCreateSensor (POPowerProvider.PowerCollector collector) {
        BlockConditionType isSculk = new BlockBlockConditionType(Blocks.SCULK);
        BlockConditionType isVein = new BlockBlockConditionType(Blocks.SCULK_VEIN);

        collector.add(
            PowerNames.JellySculk.CREATE_SENSOR,
            new ActionOnBlockUsePowerType(
                Optional.of(
                    new SequenceEntityActionType(
                        List.of(
                            new TriggerCooldownEntityActionType(
                                PowerReference.of(SPREAD_SCULK_TIMER)
                            ).createAction(),
                            JellySculk.subtractExp(COSTS.SENSOR),
                            new PlaySoundEntityActionType(
                                SoundEvents.BLOCK_SCULK_SENSOR_PLACE,
                                Optional.of(SoundCategory.PLAYERS),
                                1, 1
                            ).createAction()
                        )
                    ).createAction()
                ),
                Optional.of(
                    new IfElseListBlockActionType(
                        List.of(
                            new IfElseListMetaActionType.ConditionedAction<>(
                                new OffsetBlockActionType(
                                    new SetBlockBlockActionType(Blocks.SCULK_SENSOR.getDefaultState()).createAction(),
                                    new Vec3i(0, 1, 0)
                                ).createAction(),
                                isSculk.createCondition()
                            ),
                            new IfElseListMetaActionType.ConditionedAction<>(
                                new SetBlockBlockActionType(Blocks.SCULK_SENSOR.getDefaultState()).createAction(),
                                isVein.createCondition()
                            )
                        )
                    ).createAction()
                ),
                Optional.of(
                    new AllOfBlockConditionType(
                        List.of(
                            new AnyOfBlockConditionType(
                                List.of(
                                    isSculk.createCondition(),
                                    isVein.createCondition()
                                )
                            ).createCondition(),
                            new OffsetBlockConditionType(
                                new AnyOfBlockConditionType(
                                    List.of(
                                        new InTagBlockConditionType(BlockTags.AIR).createCondition(),
                                        isSculk.createCondition()
                                    )
                                ).createCondition(),
                                new Vec3i(0, 1, 0)
                            ).createCondition()
                        )
                    ).createCondition()
                ),
                EnumSet.allOf(BlockUsagePhase.class),
                EnumSet.allOf(Direction.class),
                Optional.empty(),
                Optional.of(new EmptyItemConditionType().createCondition()),
                Optional.empty(),
                Optional.empty(),
                EnumSet.of(Hand.MAIN_HAND),
                ActionResult.SUCCESS,
                0,
                Optional.of(
                    new AllOfEntityConditionType(
                        List.of(
                            new SneakingEntityConditionType().createCondition(),
                            new XpPointsEntityConditionType(
                                Comparison.GREATER_THAN_OR_EQUAL, COSTS.SENSOR
                            ).createCondition(),
                            new ResourceEntityConditionType(
                                PowerReference.resource(SPREAD_SCULK_TIMER),
                                Comparison.EQUAL, 0
                            ).createCondition()
                        )
                    ).createCondition()
                )
            ),
            List.of(
                BadgeUtil.presetActive(PowerNames.JellySculk.CREATE_SENSOR, "key.use")
            )
        );
    }

    private static void buildDebuffsOffSculk (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.JellySculk.DEBUFFS_OFF_SCULK,
            new StackingStatusEffectPowerType(
                List.of(
                    new StatusEffectInstance(
                        StatusEffects.DARKNESS,
                        100,
                        0,
                        true,
                        false,
                        false
                    )
                ),
                -10, 5,
                20,
                20,
                Optional.of(
                    new AllOfEntityConditionType(
                        List.of(
                            new OnBlockEntityConditionType(
                                Optional.of(
                                    new InTagBlockConditionType(POBlockTags.SCULK).createCondition()
                                )
                            ).createCondition(true),
                            new InBlockEntityConditionType(
                                new InTagBlockConditionType(POBlockTags.SCULK).createCondition()
                            ).createCondition(true)
                        )
                    ).createCondition()
                )
            )
        );
    }

    private static void buildEntityDetect (POPowerProvider.PowerCollector collector) {
        int glowDistance = 112;
        BiEntityConditionType canSeeGlow = new DistanceBiEntityConditionType(Comparison.LESS_THAN_OR_EQUAL, glowDistance);
        EntityAction resetCounter = new ExecuteCommandEntityActionType("scoreboard players set @s jellysculk_counter 0").createAction();

        collector.add(
            PowerNames.JellySculk.ENTITY_DETECT,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "tamed_sight",
                    new EntityGlowPowerType(
                        Optional.empty(),
                        Optional.of(
                            new AllOfBiEntityConditionType(
                                List.of(
                                    new OwnerBiEntityConditionType().createCondition(),
                                    canSeeGlow.createCondition()
                                )
                            ).createCondition()
                        ),
                        true,
                        0f, 1f, 0f,
                        Optional.empty()
                    )
                ).add(
                    "detection",
                    new ActionOnDetectVibrationPower(
                        24,
                        0, 0,
                        GameEventTags.WARDEN_CAN_LISTEN,
                        new ActorActionBiEntityActionType(resetCounter).createAction(),
                        Optional.of(
                            new AllOfBiEntityConditionType(
                                List.of(
                                    new UndirectedBiEntityConditionType(
                                        new OwnerBiEntityConditionType().createCondition()
                                    ).createCondition(true),
                                    new ActorConditionBiEntityConditionType(
                                        new InTagEntityConditionType(POEntityTypeTags.BYPASSES_DETECTION_CHECK).createCondition()
                                    ).createCondition(true)
                                )
                            ).createCondition()
                        ),
                        Optional.empty()
                    )
                ).add(
                    "on_hit",
                    new ActionOnHitPowerType(
                        Optional.of(new TargetActionBiEntityActionType(resetCounter).createAction()),
                        Optional.empty(),
                        Optional.empty(),
                        HudRender.DONT_RENDER,
                        1,
                        Optional.empty()
                    )
                ).add(
                    "when_hit",
                    new ActionWhenHitPowerType(
                        Optional.of(new ActorActionBiEntityActionType(resetCounter).createAction()),
                        Optional.empty(),
                        Optional.empty(),
                        HudRender.DONT_RENDER,
                        1,
                        Optional.empty()
                    )
                ).add(
                    "scoreboard_vision",
                    new EntityGlowPowerType(
                        Optional.of(
                            new AllOfEntityConditionType(
                                List.of(
                                    new LivingEntityConditionType().createCondition(),
                                    new InTagEntityConditionType(POEntityTypeTags.ALWAYS_VISIBLE).createCondition(true)
                                )
                            ).createCondition()
                        ),
                        Optional.of(
                            new AllOfBiEntityConditionType(
                                List.of(
                                    new OwnerBiEntityConditionType().createCondition(true),
                                    canSeeGlow.createCondition()
                                )
                            ).createCondition()
                        ),
                        true,
                        1f, 1f, 1f,
                        Optional.empty()
                    )
                ).add(
                    "cannot_see_others",
                    new PreventEntityRenderPowerType(
                        Optional.of(
                            new AllOfEntityConditionType(
                                List.of(
                                    new LivingEntityConditionType().createCondition(),
                                    new PowerEntityConditionType(
                                        PowerReference.of(PowerNames.JellySculk.IS_DETECTED),
                                        Optional.empty()
                                    ).createCondition(true),
                                    new InTagEntityConditionType(POEntityTypeTags.BYPASSES_DETECTION_CHECK).createCondition(true)
                                )
                            ).createCondition()
                        ),
                        Optional.of(
                            new AllOfBiEntityConditionType(
                                List.of(
                                    new OwnerBiEntityConditionType().createCondition(true),
                                    new DistanceBiEntityConditionType(Comparison.GREATER_THAN_OR_EQUAL, 0.1).createCondition(),
                                    new TeammateBiEntityConditionType().createCondition(true)
                                )
                            ).createCondition()
                        ),
                        Optional.empty()
                    )
                ),
            List.of(
                BadgeUtil.info(PowerNames.JellySculk.ENTITY_DETECT)
            )
        );
    }

    private static void buildIsDetected (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.JellySculk.IS_DETECTED,
            new DummyPowerType(Optional.empty())
        );
    }

    private static void buildScoreboardManager (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.JellySculk.SCOREBOARD_MANAGER,
            new ActionOnCallbackPowerType(
                Optional.empty(),
                Optional.empty(),
                Optional.of(
                    new ExecuteCommandEntityActionType("scoreboard objectives add jellysculk_counter dummy").createAction()
                ),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
            ),
            true
        );
    }

    private static void buildSpreadSculk (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.JellySculk.SPREAD_SCULK,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "spread_sculk",
                    new ActionOnBlockUsePowerType(
                        Optional.of(
                            new SequenceEntityActionType(
                                List.of(
                                    new TriggerCooldownEntityActionType(PowerReference.of(SPREAD_SCULK_TIMER)).createAction(),
                                    JellySculk.subtractExp(COSTS.SCULK),
                                    new PlaySoundEntityActionType(
                                        SoundEvents.BLOCK_SCULK_SPREAD,
                                        Optional.of(SoundCategory.PLAYERS),
                                        1, 1
                                    ).createAction()
                                )
                            ).createAction()
                        ),
                        Optional.of(
                            new DelayBlockActionType(
                                new SetBlockBlockActionType(Blocks.SCULK.getDefaultState()).createAction(),
                                1
                            ).createAction()
                        ),
                        Optional.of(new InTagBlockConditionType(BlockTags.SCULK_REPLACEABLE).createCondition()),
                        EnumSet.allOf(BlockUsagePhase.class),
                        EnumSet.allOf(Direction.class),
                        Optional.empty(),
                        Optional.of(new EmptyItemConditionType().createCondition()),
                        Optional.empty(),
                        Optional.empty(),
                        EnumSet.of(Hand.MAIN_HAND),
                        ActionResult.SUCCESS,
                        0,
                        Optional.of(
                            new AllOfEntityConditionType(
                                List.of(
                                    new SneakingEntityConditionType().createCondition(),
                                    new XpPointsEntityConditionType(Comparison.GREATER_THAN_OR_EQUAL, COSTS.SCULK).createCondition()
                                )
                            ).createCondition()
                        )
                    )
                ).add(
                    "timer",
                    new CooldownPowerType(30, HudRender.DONT_RENDER, Optional.empty())
                ),
            List.of(
                BadgeUtil.presetActive(PowerNames.JellySculk.SPREAD_SCULK, "key.use")
            )
        );
    }

    interface COSTS {
        int SENSOR = 10;
        int SCULK = 2;
    }
}
