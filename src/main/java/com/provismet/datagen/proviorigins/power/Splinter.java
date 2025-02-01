package com.provismet.datagen.proviorigins.power;

import com.provismet.datagen.proviorigins.constants.PowerNames;
import com.provismet.datagen.proviorigins.provider.POPowerProvider;
import com.provismet.proviorigins.actions.bientity.SwapPositionsAction;
import com.provismet.proviorigins.actions.entity.ActOnFarthestEntityAction;
import com.provismet.proviorigins.actions.entity.SummonCloneAction;
import com.provismet.proviorigins.conditions.bientity.FriendlyBiEntityConditionType;
import com.provismet.proviorigins.content.registries.POEntities;
import com.provismet.proviorigins.content.registries.POStatusEffects;
import com.provismet.proviorigins.powers.EmissivePower;
import com.provismet.proviorigins.powers.EvadeProjectilesPower;
import com.provismet.proviorigins.powers.IllusionPower;
import com.provismet.proviorigins.utility.BadgeUtil;
import com.provismet.proviorigins.utility.KeyUtil;
import com.provismet.proviorigins.utility.PowerUtil;
import com.provismet.proviorigins.utility.constants.SpriteLocations;
import io.github.apace100.apoli.action.EntityAction;
import io.github.apace100.apoli.action.type.bientity.meta.ActorActionBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.SequenceBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.TargetActionBiEntityActionType;
import io.github.apace100.apoli.action.type.entity.ApplyEffectEntityActionType;
import io.github.apace100.apoli.action.type.entity.AreaOfEffectEntityActionType;
import io.github.apace100.apoli.action.type.entity.ChangeResourceEntityActionType;
import io.github.apace100.apoli.action.type.entity.GrantPowerEntityActionType;
import io.github.apace100.apoli.action.type.entity.RaycastEntityActionType;
import io.github.apace100.apoli.action.type.entity.TriggerCooldownEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.IfElseEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.SequenceEntityActionType;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.condition.type.bientity.OwnerBiEntityConditionType;
import io.github.apace100.apoli.condition.type.bientity.meta.AllOfBiEntityConditionType;
import io.github.apace100.apoli.condition.type.bientity.meta.TargetConditionBiEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.EntityTypeEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.PowerEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.ResourceEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.SneakingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.AllOfEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.AnyOfEntityConditionType;
import io.github.apace100.apoli.power.PowerReference;
import io.github.apace100.apoli.power.type.ActionOnCallbackPowerType;
import io.github.apace100.apoli.power.type.ActionOnKeyPressPowerType;
import io.github.apace100.apoli.power.type.ActionOverTimePowerType;
import io.github.apace100.apoli.power.type.CooldownPowerType;
import io.github.apace100.apoli.power.type.ResourcePowerType;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.apoli.util.ResourceOperation;
import io.github.apace100.apoli.util.Shape;
import io.github.apace100.apoli.util.Space;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.world.RaycastContext;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class Splinter {
    public static void build (POPowerProvider.PowerCollector collector) {
        Splinter.buildArcaneGlow(collector);
        Splinter.buildConcentration(collector);
        Splinter.buildDistortion(collector);
        Splinter.buildDodgeProjectiles(collector);
        Splinter.buildFragment(collector);
        Splinter.buildMana(collector);
        Splinter.buildResetResourcesOnDeath(collector);
        Splinter.buildResourceRecharge(collector);
        Splinter.buildSwap(collector);
    }

    private static EntityAction addMana (int amount) {
        return new ChangeResourceEntityActionType(
            PowerReference.resource(PowerNames.Splinter.MANA),
            ResourceOperation.ADD,
            amount
        ).createAction();
    }

    private static EntityCondition hasMana (int amount) {
        return new ResourceEntityConditionType(
            PowerReference.resource(PowerNames.Splinter.MANA),
            Comparison.GREATER_THAN_OR_EQUAL, amount
        ).createCondition();
    }

    private static EntityAction addConcentration (int amount) {
        return new ChangeResourceEntityActionType(
            PowerReference.resource(PowerNames.Splinter.CONCENTRATION),
            ResourceOperation.ADD,
            amount
        ).createAction();
    }

    private static void buildArcaneGlow (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Splinter.ARCANE_GLOW,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "mid_emit",
                    new EmissivePower(
                        7, 3,
                        Optional.of(Splinter.hasMana(Mana.GLOW_MID))
                    )
                ).add(
                    "high_emit",
                    new EmissivePower(
                        15, 7,
                        Optional.of(Splinter.hasMana(Mana.GLOW_HIGH))
                    )
                )
        );
    }

    private static void buildConcentration (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Splinter.CONCENTRATION,
            new ResourcePowerType(
                Optional.empty(),
                Optional.empty(),
                new HudRender(
                    Optional.empty(),
                    SpriteLocations.SPLINTER_CLONE_RESOURCE,
                    true,
                    false,
                    0, 0,
                    1
                ),
                Concentration.MIN, Concentration.MAX,
                Concentration.START_VALUE
            ),
            true
        );
    }

    private static void buildDistortion (POPowerProvider.PowerCollector collector) {
        String cooldown = "internal_cooldown";

        collector.add(
            PowerNames.Splinter.DISTORTION,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    cooldown,
                    new CooldownPowerType(
                        160,
                        HudRender.DONT_RENDER,
                        Optional.empty()
                    )
                ).add(
                    "activate_skill",
                    new ActionOnKeyPressPowerType(
                        new SequenceEntityActionType(
                            List.of(
                                new AreaOfEffectEntityActionType(
                                    new TargetActionBiEntityActionType(
                                        new ApplyEffectEntityActionType(
                                            List.of(new StatusEffectInstance(StatusEffects.BLINDNESS, 60))
                                        ).createAction()
                                    ).createAction(),
                                    Optional.of(
                                        new AllOfBiEntityConditionType(
                                            List.of(
                                                new FriendlyBiEntityConditionType().createCondition(true),
                                                new OwnerBiEntityConditionType().createCondition(true)
                                            )
                                        ).createCondition()
                                    ),
                                    Shape.CUBE,
                                    6,
                                    false
                                ).createAction(),
                                new ApplyEffectEntityActionType(
                                    List.of(new StatusEffectInstance(POStatusEffects.UNTARGETABLE, 80))
                                ).createAction(),
                                new TriggerCooldownEntityActionType(
                                    PowerReference.of(PowerUtil.subPower(PowerNames.Splinter.DISTORTION, cooldown))
                                ).createAction(),
                                Splinter.addMana(-Mana.COST_DISTORTION),
                                new AreaOfEffectEntityActionType(
                                    new TargetActionBiEntityActionType(
                                        new TriggerCooldownEntityActionType(
                                            PowerReference.of(PowerUtil.subPower(PowerNames.Splinter.DISTORTION, cooldown))
                                        ).createAction()
                                    ).createAction(),
                                    Optional.of(
                                        new AllOfBiEntityConditionType(
                                            List.of(
                                                new OwnerBiEntityConditionType().createCondition(),
                                                new TargetConditionBiEntityConditionType(
                                                    new EntityTypeEntityConditionType(POEntities.CLONE).createCondition()
                                                ).createCondition(),
                                                new TargetConditionBiEntityConditionType(
                                                    new PowerEntityConditionType(
                                                        PowerReference.of(PowerNames.Splinter.DISTORTION),
                                                        Optional.empty()
                                                    ).createCondition()
                                                ).createCondition()
                                            )
                                        ).createCondition()
                                    ),
                                    Shape.CUBE,
                                    32,
                                    false
                                ).createAction()
                            )
                        ).createAction(),
                        HudRender.DONT_RENDER,
                        1,
                        KeyUtil.secondary(),
                        Optional.of(Splinter.hasMana(Mana.COST_DISTORTION))
                    )
                ).add(
                    "illusion_spread",
                    new IllusionPower(
                        0.0125,
                        2,
                        "line",
                        Optional.of(PowerReference.of(PowerUtil.subPower(PowerNames.Splinter.DISTORTION, cooldown))),
                        Optional.of(
                            new ResourceEntityConditionType(
                                PowerReference.resource(PowerUtil.subPower(PowerNames.Splinter.DISTORTION, cooldown)),
                                Comparison.GREATER_THAN_OR_EQUAL, 1
                            ).createCondition()
                        )
                    )
                ),
            List.of(
                BadgeUtil.presetActive(KeyUtil.ACTIVE_SECONDARY),
                BadgeUtil.cost(PowerNames.Splinter.DISTORTION)
            )
        );
    }

    private static void buildDodgeProjectiles (POPowerProvider.PowerCollector collector) {
        String cooldown = "internal_cooldown";

        collector.add(
            PowerNames.Splinter.DODGE_PROJECTILES,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    cooldown,
                    new CooldownPowerType(
                        15,
                        HudRender.DONT_RENDER,
                        Optional.empty()
                    )
                ).add(
                    "dodge",
                    new EvadeProjectilesPower(
                        Optional.of(
                            new IfElseEntityActionType(
                                new ResourceEntityConditionType(
                                    PowerReference.resource(PowerUtil.subPower(PowerNames.Splinter.DODGE_PROJECTILES, cooldown)),
                                    Comparison.EQUAL, 0
                                ).createCondition(),
                                new SequenceEntityActionType(
                                    List.of(
                                        new TriggerCooldownEntityActionType(
                                            PowerReference.of(PowerUtil.subPower(PowerNames.Splinter.DODGE_PROJECTILES, cooldown))
                                        ).createAction(),
                                        Splinter.addMana(-Mana.COST_DODGE_PROJECTILES)
                                    )
                                ).createAction(),
                                Optional.empty()
                            ).createAction()
                        ),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(
                            new AnyOfEntityConditionType(
                                List.of(
                                    new ResourceEntityConditionType(
                                        PowerReference.resource(PowerUtil.subPower(PowerNames.Splinter.DODGE_PROJECTILES, cooldown)),
                                        Comparison.GREATER_THAN_OR_EQUAL, 1
                                    ).createCondition(),
                                    new AllOfEntityConditionType(
                                        List.of(
                                            new SneakingEntityConditionType().createCondition(),
                                            Splinter.hasMana(Mana.COST_DODGE_PROJECTILES)
                                        )
                                    ).createCondition()
                                )
                            ).createCondition()
                        )
                    )
                ),
            List.of(
                BadgeUtil.cost(PowerNames.Splinter.DODGE_PROJECTILES)
            )
        );
    }

    private static void buildFragment (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Splinter.FRAGMENT,
            new ActionOnKeyPressPowerType(
                new SequenceEntityActionType(
                    List.of(
                        new SummonCloneAction(
                            true,
                            true,
                            true,
                            true,
                            true,
                            Optional.of(
                                new TargetActionBiEntityActionType(
                                    new SequenceEntityActionType(
                                        List.of(
                                            new GrantPowerEntityActionType(
                                                PowerReference.of(PowerNames.Splinter.DISTORTION),
                                                PowerNames.Splinter.FRAGMENT
                                            ).createAction(),
                                            new GrantPowerEntityActionType(
                                                PowerReference.of(PowerNames.Common.BRITTLE),
                                                PowerNames.Splinter.FRAGMENT
                                            ).createAction()
                                        )
                                    ).createAction()
                                ).createAction()
                            )
                        ).createAction(),
                        Splinter.addConcentration(-1)
                    )
                ).createAction(),
                HudRender.DONT_RENDER,
                1,
                KeyUtil.primary(),
                Optional.of(
                    new ResourceEntityConditionType(
                        PowerReference.resource(PowerNames.Splinter.CONCENTRATION),
                        Comparison.GREATER_THAN_OR_EQUAL, 1
                    ).createCondition()
                )
            ),
            List.of(
                BadgeUtil.active(),
                BadgeUtil.presetActive(PowerNames.Splinter.FRAGMENT, 1, "key.use"),
                BadgeUtil.cost(PowerNames.Splinter.FRAGMENT, 2)
            )
        );
    }

    private static void buildMana (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Splinter.MANA,
            new ResourcePowerType(
                Optional.empty(),
                Optional.empty(),
                new HudRender(
                    Optional.empty(),
                    SpriteLocations.EXTRA_RESOURCES,
                    true,
                    true,
                    1, 1,
                    0
                ),
                Mana.MIN, Mana.MAX,
                Mana.START_VALUE
            ),
            true
        );
    }

    private static void buildResetResourcesOnDeath (POPowerProvider.PowerCollector collector) {
        Function<Identifier, EntityAction> reset = resource -> new ChangeResourceEntityActionType(
            PowerReference.resource(resource),
            ResourceOperation.SET,
            0
        ) .createAction();

        collector.add(
            PowerNames.Splinter.RESET_RESOURCES_ON_DEATH,
            new ActionOnCallbackPowerType(
                Optional.of(
                    new SequenceEntityActionType(
                        List.of(
                            reset.apply(PowerNames.Splinter.MANA),
                            reset.apply(PowerNames.Splinter.CONCENTRATION)
                        )
                    ).createAction()
                ),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
            ),
            true
        );
    }

    private static void buildResourceRecharge (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Splinter.RESOURCE_RECHARGE,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "recharge_mana",
                    new ActionOverTimePowerType(
                        Optional.of(Splinter.addMana(1)),
                        Optional.empty(),
                        Optional.empty(),
                        Mana.RECHARGE_INTERVAL,
                        Optional.empty()
                    )
                ).add(
                    "recharge_concentration",
                    new ActionOverTimePowerType(
                        Optional.of(Splinter.addConcentration(1)),
                        Optional.empty(),
                        Optional.empty(),
                        Concentration.RECHARGE_INTERVAL,
                        Optional.empty()
                    )
                )
        );
    }

    private static void buildSwap (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Splinter.SWAP,
            new ActionOnKeyPressPowerType(
                new IfElseEntityActionType(
                    new SneakingEntityConditionType().createCondition(),
                    new ActOnFarthestEntityAction(
                        32,
                        new SequenceBiEntityActionType(
                            List.of(
                                new SwapPositionsAction(true, true).createAction(),
                                new ActorActionBiEntityActionType(Splinter.addConcentration(-Mana.COST_SWAP)).createAction()
                            )
                        ).createAction(),
                        new AllOfBiEntityConditionType(
                            List.of(
                                new OwnerBiEntityConditionType().createCondition(),
                                new TargetConditionBiEntityConditionType(
                                    new EntityTypeEntityConditionType(POEntities.CLONE).createCondition()
                                ).createCondition()
                            )
                        ).createCondition()
                    ).createAction(),
                    Optional.of(
                        new RaycastEntityActionType(
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.of(
                                new SequenceBiEntityActionType(
                                    List.of(
                                        new SwapPositionsAction(true, true).createAction(),
                                        new ActorActionBiEntityActionType(Splinter.addMana(-Mana.COST_SWAP)).createAction()
                                    )
                                ).createAction()
                            ),
                            Optional.empty(),
                            Optional.of(
                                new AllOfBiEntityConditionType(
                                    List.of(
                                        new OwnerBiEntityConditionType().createCondition(),
                                        new TargetConditionBiEntityConditionType(
                                            new EntityTypeEntityConditionType(POEntities.CLONE).createCondition()
                                        ).createCondition()
                                    )
                                ).createCondition()
                            ),
                            RaycastContext.ShapeType.OUTLINE,
                            RaycastContext.FluidHandling.ANY,
                            Optional.empty(),
                            Space.WORLD,
                            Optional.empty(),
                            Optional.empty(),
                            Optional.of(32.0),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            1,
                            false,
                            true,
                            false
                        ).createAction()
                    )
                ).createAction(),
                HudRender.DONT_RENDER,
                1,
                KeyUtil.tertiary(),
                Optional.of(Splinter.hasMana(Mana.COST_SWAP))
            ),
            List.of(
                BadgeUtil.active(),
                BadgeUtil.cost(PowerNames.Splinter.SWAP)
            )
        );
    }

    public interface Mana {
        int MIN = 0;
        int MAX = 119;
        int START_VALUE = MAX;

        int RECHARGE_INTERVAL = 10;

        int SINGLE_BAR = MAX / 7;

        int GLOW_MID = 40;
        int GLOW_HIGH = 80;

        int COST_DISTORTION = SINGLE_BAR * 2;
        int COST_DODGE_PROJECTILES = SINGLE_BAR * 2;
        int COST_SWAP = SINGLE_BAR * 2;
    }

    public interface Concentration {
        int MIN = 0;
        int MAX = 2;
        int START_VALUE = MAX;

        int RECHARGE_INTERVAL = 600;
    }
}
