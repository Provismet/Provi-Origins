package com.provismet.datagen.proviorigins.power;

import com.provismet.datagen.proviorigins.PowerGenerator;
import com.provismet.datagen.proviorigins.constants.PowerNames;
import com.provismet.datagen.proviorigins.provider.POPowerProvider;
import com.provismet.proviorigins.actions.bientity.StatusTransferAction;
import com.provismet.proviorigins.actions.entity.DoubleJumpAction;
import com.provismet.proviorigins.actions.entity.ParticleRingAction;
import com.provismet.proviorigins.conditions.bientity.FriendlyBiEntityConditionType;
import com.provismet.proviorigins.conditions.entity.ClientServerEntityConditionType;
import com.provismet.proviorigins.conditions.entity.EntityInRadiusEntityConditionType;
import com.provismet.proviorigins.content.registries.POItems;
import com.provismet.proviorigins.content.registries.POParticles;
import com.provismet.proviorigins.content.registries.POStatusEffects;
import com.provismet.proviorigins.powers.ActionOnCriticalHitPower;
import com.provismet.proviorigins.utility.BadgeUtil;
import com.provismet.proviorigins.utility.KeyUtil;
import com.provismet.proviorigins.utility.OriginList;
import com.provismet.proviorigins.utility.constants.SpriteLocations;
import com.provismet.proviorigins.utility.tags.POBlockTags;
import com.provismet.proviorigins.utility.tags.POFluidTags;
import com.provismet.proviorigins.utility.tags.POItemTags;
import io.github.apace100.apoli.action.EntityAction;
import io.github.apace100.apoli.action.type.bientity.meta.ActorActionBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.SequenceBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.TargetActionBiEntityActionType;
import io.github.apace100.apoli.action.type.entity.ApplyEffectEntityActionType;
import io.github.apace100.apoli.action.type.entity.AreaOfEffectEntityActionType;
import io.github.apace100.apoli.action.type.entity.ChangeResourceEntityActionType;
import io.github.apace100.apoli.action.type.entity.GrantPowerEntityActionType;
import io.github.apace100.apoli.action.type.entity.RevokePowerEntityActionType;
import io.github.apace100.apoli.action.type.entity.SpawnParticlesEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.IfElseEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.IfElseListEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.SequenceEntityActionType;
import io.github.apace100.apoli.action.type.meta.IfElseListMetaActionType;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.condition.type.bientity.meta.TargetConditionBiEntityConditionType;
import io.github.apace100.apoli.condition.type.block.BlockStateBlockConditionType;
import io.github.apace100.apoli.condition.type.block.InTagBlockConditionType;
import io.github.apace100.apoli.condition.type.block.meta.AllOfBlockConditionType;
import io.github.apace100.apoli.condition.type.block.meta.AnyOfBlockConditionType;
import io.github.apace100.apoli.condition.type.entity.BlockInRadiusEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.CreativeFlyingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.EquippedItemEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.ExposedToSunEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.FallFlyingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.LivingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.OnBlockEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.PowerEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.ResourceEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.StatusEffectEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.SubmergedInEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.AllOfEntityConditionType;
import io.github.apace100.apoli.power.PowerReference;
import io.github.apace100.apoli.power.type.ActionOnKeyPressPowerType;
import io.github.apace100.apoli.power.type.ActionOverTimePowerType;
import io.github.apace100.apoli.power.type.RecipePowerType;
import io.github.apace100.apoli.power.type.ResourcePowerType;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.apoli.util.ResourceOperation;
import io.github.apace100.apoli.util.Shape;
import io.github.apace100.apoli.util.Space;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

import java.util.List;
import java.util.Optional;

public abstract class FaeMoth {
    private static final Identifier STACKING_PASSIVE_STACKS = PowerNames.FaeMoth.STACKING_PASSIVE.withSuffixedPath("_stacks");

    public static void build (POPowerProvider.PowerCollector collector) {
        FaeMoth.buildCritPassive(collector);
        FaeMoth.buildFaerieDust(collector);
        FaeMoth.buildFlutter(collector);
        FaeMoth.buildShareBuffs(collector);
        FaeMoth.buildStackingPassive(collector);
    }

    private static EntityCondition compareFaerieDust (Comparison comparison, int compareTo) {
        return new ResourceEntityConditionType(
            PowerReference.resource(PowerNames.FaeMoth.FAERIE_DUST),
            comparison,
            compareTo
        ).createCondition();
    }

    private static EntityAction addFaerieDust (int amount) {
        return new ChangeResourceEntityActionType(
            PowerReference.resource(PowerNames.FaeMoth.FAERIE_DUST),
            ResourceOperation.ADD,
            amount
        ).createAction();
    }

    private static void buildCritPassive (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.FaeMoth.CRIT_PASSIVE,
            new ActionOnCriticalHitPower(
                new SequenceBiEntityActionType(
                    List.of(
                        new TargetActionBiEntityActionType(
                            new IfElseEntityActionType(
                                new AllOfEntityConditionType(
                                    List.of(
                                        new LivingEntityConditionType().createCondition(),
                                        new PowerEntityConditionType(
                                            PowerReference.of(PowerNames.FaeMoth.STACKING_PASSIVE),
                                            Optional.empty()
                                        ).createCondition()
                                    )
                                ).createCondition(),
                                new SequenceEntityActionType(
                                    List.of(
                                        new ChangeResourceEntityActionType(
                                            PowerReference.resource(PowerNames.FaeMoth.STACKING_PASSIVE.withSuffixedPath("_stacks")),
                                            ResourceOperation.ADD,
                                            1
                                        ).createAction(),
                                        new ChangeResourceEntityActionType(
                                            PowerReference.resource(PowerNames.FaeMoth.STACKING_PASSIVE.withSuffixedPath("_countdown")),
                                            ResourceOperation.SET,
                                            0
                                        ).createAction()
                                    )
                                ).createAction(),
                                Optional.of(
                                    new GrantPowerEntityActionType(
                                        PowerReference.of(PowerNames.FaeMoth.STACKING_PASSIVE),
                                        OriginList.FAERIE_MOTH.originIdentifier()
                                    ).createAction()
                                )
                            ).createAction()
                        ).createAction(),
                        new ActorActionBiEntityActionType(
                            FaeMoth.addFaerieDust(-FaerieDust.COST_CRIT)
                        ).createAction()
                    )
                ).createAction(),
                Optional.of(
                    new TargetConditionBiEntityConditionType(
                        new AllOfEntityConditionType(
                            List.of(
                                new StatusEffectEntityConditionType(
                                    POStatusEffects.SLEEP,
                                    0,
                                    Integer.MAX_VALUE,
                                    -1,
                                    Integer.MAX_VALUE
                                ).createCondition(true),
                                new StatusEffectEntityConditionType(
                                    POStatusEffects.ALERT,
                                    0,
                                    Integer.MAX_VALUE,
                                    -1,
                                    Integer.MAX_VALUE
                                ).createCondition(true)
                            )
                        ).createCondition()
                    ).createCondition()
                ),
                Optional.of(
                    new AllOfEntityConditionType(
                        List.of(
                            FaeMoth.compareFaerieDust(
                                Comparison.GREATER_THAN_OR_EQUAL,
                                FaerieDust.COST_CRIT
                            ),
                            new EquippedItemEntityConditionType(
                                PowerGenerator.ingredientConditionFromTag(ItemTags.SWORDS),
                                AttributeModifierSlot.MAINHAND
                            ).createCondition()
                        )
                    ).createCondition()
                )
            ),
            List.of(
                BadgeUtil.info(PowerNames.FaeMoth.CRIT_PASSIVE, 1),
                BadgeUtil.cost(PowerNames.FaeMoth.CRIT_PASSIVE, 2)
            )
        );
    }

    private static void buildFaerieDust (POPowerProvider.PowerCollector collector) {
        ShapelessRecipe lanternRecipe = new ShapelessRecipe(
            "",
            CraftingRecipeCategory.MISC,
            POItems.SOLID_LANTERN.getDefaultStack(),
            DefaultedList.copyOf(
                Ingredient.ofItems(Items.LANTERN),
                Ingredient.ofItems(Items.LANTERN)
            )
        );

        collector.add(
            PowerNames.FaeMoth.FAERIE_DUST,
            new ResourcePowerType(
                Optional.empty(),
                Optional.empty(),
                new HudRender(
                    Optional.empty(),
                    SpriteLocations.EXTRA_RESOURCES,
                    true,
                    false,
                    2, 2,
                    0
                ),
                FaerieDust.MIN,
                FaerieDust.MAX,
                FaerieDust.START_VALUE
            ),
            List.of(
                BadgeUtil.info(PowerNames.FaeMoth.FAERIE_DUST, 1),
                BadgeUtil.info(PowerNames.FaeMoth.FAERIE_DUST, 2),
                BadgeUtil.info(PowerNames.FaeMoth.FAERIE_DUST, 3),
                BadgeUtil.crafting(
                    new RecipeEntry<>(
                        OriginList.FAERIE_MOTH.identifier("qol_lantern_example"),
                        lanternRecipe
                    )
                )
            )
        );

        collector.add(
            PowerNames.FaeMoth.FAERIE_DUST_CHARGING,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "special_lantern",
                    new RecipePowerType(
                        lanternRecipe,
                        0
                    )
                ).add(
                    "charging",
                    new ActionOverTimePowerType(
                        Optional.of(
                            new IfElseListEntityActionType(
                                List.of(
                                    new IfElseListMetaActionType.ConditionedAction<>(
                                        FaeMoth.addFaerieDust(FaerieDust.PASSIVE_DRAIN),
                                        new ExposedToSunEntityConditionType().createCondition()
                                    ),
                                    new IfElseListMetaActionType.ConditionedAction<>(
                                        FaeMoth.addFaerieDust(FaerieDust.PASSIVE_GAIN_FROM_BLOCKS),
                                        new BlockInRadiusEntityConditionType(
                                            new AnyOfBlockConditionType(
                                                List.of(
                                                    new InTagBlockConditionType(POBlockTags.LARGE_FIRES).createCondition(),
                                                    new AllOfBlockConditionType(
                                                        List.of(
                                                            new InTagBlockConditionType(POBlockTags.LARGE_FIRES_WITH_LIT_BLOCKSTATE).createCondition(),
                                                            new BlockStateBlockConditionType(
                                                                "lit",
                                                                null,
                                                                null,
                                                                true,
                                                                null
                                                            ).createCondition()
                                                        )
                                                    ).createCondition(),
                                                    new AllOfBlockConditionType(
                                                        List.of(
                                                            new InTagBlockConditionType(POBlockTags.LARGE_FIRES_WITH_FIRE_BLOCKSTATE).createCondition(),
                                                            new BlockStateBlockConditionType(
                                                                "fire",
                                                                null,
                                                                null,
                                                                true,
                                                                null
                                                            ).createCondition()
                                                        )
                                                    ).createCondition()
                                                )
                                            ).createCondition(),
                                            Shape.SPHERE,
                                            Comparison.GREATER_THAN, 0,
                                            4
                                        ).createCondition()
                                    ),
                                    new IfElseListMetaActionType.ConditionedAction<>(
                                        FaeMoth.addFaerieDust(FaerieDust.PASSIVE_GAIN_FROM_LANTERN),
                                        new EntityInRadiusEntityConditionType(
                                            4,
                                            Comparison.GREATER_THAN, 0,
                                            true,
                                            Optional.of(
                                                new EquippedItemEntityConditionType(
                                                    PowerGenerator.ingredientConditionFromTag(POItemTags.LIGHT_SOURCES),
                                                    AttributeModifierSlot.HAND
                                                ).createCondition()
                                            ),
                                            Optional.empty()
                                        ).createCondition()
                                    )
                                )
                            ).createAction()
                        ),
                        Optional.empty(),
                        Optional.empty(),
                        20,
                        Optional.empty()
                    )
                ),
            true
        );
    }

    private static void buildFlutter (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.FaeMoth.FLUTTER,
            new ActionOnKeyPressPowerType(
                new SequenceEntityActionType(
                    List.of(
                        new DoubleJumpAction(0.5, true).createAction(),
                        FaeMoth.addFaerieDust(-FaerieDust.COST_FLUTTER)
                    )
                ).createAction(),
                HudRender.DONT_RENDER,
                1,
                KeyUtil.create("key.jump"),
                Optional.of(
                    new AllOfEntityConditionType(
                        List.of(
                            new ClientServerEntityConditionType(true, false).createCondition(),
                            FaeMoth.compareFaerieDust(Comparison.GREATER_THAN_OR_EQUAL, FaerieDust.COST_FLUTTER),
                            new OnBlockEntityConditionType(Optional.empty()).createCondition(true),
                            new CreativeFlyingEntityConditionType().createCondition(true),
                            new FallFlyingEntityConditionType().createCondition(true),
                            new SubmergedInEntityConditionType(POFluidTags.PREVENTS_DOUBLE_JUMP).createCondition(true)
                        )
                    ).createCondition()
                )
            ),
            List.of(
                BadgeUtil.active(),
                BadgeUtil.cost(PowerNames.FaeMoth.FLUTTER)
            )
        );
    }

    private static void buildShareBuffs (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.FaeMoth.SHARE_BUFFS,
            new ActionOnKeyPressPowerType(
                new SequenceEntityActionType(
                    List.of(
                        new SpawnParticlesEntityActionType(
                            Optional.empty(),
                            ParticleTypes.FIREWORK,
                            new Vec3d(0, 0.3 ,0),
                            new Vec3d(8, 0.4, 8),
                            true,
                            0,
                            30
                        ).createAction(),
                        new SpawnParticlesEntityActionType(
                            Optional.empty(),
                            POParticles.MAGIC_CIRCLE,
                            new Vec3d(0, 0.01, 0),
                            Vec3d.ZERO,
                            true,
                            0,
                            1
                        ).createAction(),
                        new AreaOfEffectEntityActionType(
                            new StatusTransferAction(
                                List.of(),
                                List.of(
                                    StatusEffects.SPEED.getIdAsString(),
                                    StatusEffects.REGENERATION.getIdAsString(),
                                    StatusEffects.STRENGTH.getIdAsString()
                                ),
                                false,
                                1, 1
                            ).createAction(),
                            Optional.of(new FriendlyBiEntityConditionType().createCondition()),
                            Shape.SPHERE,
                            8,
                            false
                        ).createAction(),
                        FaeMoth.addFaerieDust(-FaerieDust.COST_SHARE_BUFFS)
                    )
                ).createAction(),
                HudRender.DONT_RENDER,
                1,
                KeyUtil.primary(),
                Optional.of(FaeMoth.compareFaerieDust(Comparison.GREATER_THAN_OR_EQUAL, FaerieDust.COST_SHARE_BUFFS))
            ),
            List.of(
                BadgeUtil.active(),
                BadgeUtil.cost(PowerNames.FaeMoth.SHARE_BUFFS)
            )
        );
    }

    private static void buildStackingPassive (POPowerProvider.PowerCollector collector) {
        String countdownName = "countdown";
        Identifier countdown = PowerNames.FaeMoth.STACKING_PASSIVE.withSuffixedPath("_" + countdownName);

        collector.add(
            PowerNames.FaeMoth.STACKING_PASSIVE,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "stacks",
                    new ResourcePowerType(
                        Optional.of(
                            new RevokePowerEntityActionType(
                                PowerReference.of(PowerNames.FaeMoth.STACKING_PASSIVE),
                                OriginList.FAERIE_MOTH.originIdentifier()
                            ).createAction()
                        ),
                        Optional.of(
                            new SequenceEntityActionType(
                                List.of(
                                    new ApplyEffectEntityActionType(
                                        List.of(
                                            new StatusEffectInstance(
                                                POStatusEffects.SLEEP,
                                                100
                                            )
                                        )
                                    ).createAction(),
                                    new ParticleRingAction(
                                        2,
                                        3f,
                                        1,
                                        Space.LOCAL,
                                        0,
                                        true,
                                        new DustParticleEffect(new Vector3f(1f, 0f, 0f), 1f),
                                        new Vector3f(0f, 0f, 0f)
                                    ).createAction(),
                                    new RevokePowerEntityActionType(
                                        PowerReference.of(PowerNames.FaeMoth.STACKING_PASSIVE),
                                        OriginList.FAERIE_MOTH.originIdentifier()
                                    ).createAction()
                                )
                            ).createAction()
                        ),
                        HudRender.DONT_RENDER,
                        0, 3,
                        1
                    )
                ).add(
                    "countdown",
                    new ResourcePowerType(
                        Optional.empty(),
                        Optional.of(
                            new ChangeResourceEntityActionType(
                                PowerReference.resource(STACKING_PASSIVE_STACKS),
                                ResourceOperation.ADD,
                                -1
                            ).createAction()
                        ),
                        HudRender.DONT_RENDER,
                        0, 3,
                        0
                    )
                ).add(
                    "apply_countdown",
                    new ActionOverTimePowerType(
                        Optional.of(
                            new ChangeResourceEntityActionType(
                                PowerReference.resource(countdown),
                                ResourceOperation.ADD,
                                1
                            ).createAction()
                        ),
                        Optional.empty(),
                        Optional.of(
                            new ChangeResourceEntityActionType(
                                PowerReference.resource(countdown),
                                ResourceOperation.SET,
                                0
                            ).createAction()
                        ),
                        25,
                        Optional.of(
                            new ResourceEntityConditionType(
                                PowerReference.resource(countdown),
                                Comparison.LESS_THAN,
                                3
                            ).createCondition()
                        )
                    )
                ).add(
                    "first_ring",
                    new ActionOverTimePowerType(
                        Optional.of(
                            new ParticleRingAction(
                                1,
                                5,
                                1,
                                Space.LOCAL,
                                0,
                                true,
                                new DustParticleEffect(new Vector3f(0.196f, 1f, 0.196f), 1f),
                                new Vector3f(0f, 0f, 0f)
                            ).createAction()
                        ),
                        Optional.empty(),
                        Optional.empty(),
                        5,
                        Optional.of(
                            new ResourceEntityConditionType(
                                PowerReference.resource(STACKING_PASSIVE_STACKS),
                                Comparison.GREATER_THAN_OR_EQUAL,
                                1
                            ).createCondition()
                        )
                    )
                ).add(
                    "second_ring",
                    new ActionOverTimePowerType(
                        Optional.of(
                            new ParticleRingAction(
                                1.5f,
                                4,
                                1,
                                Space.LOCAL,
                                0,
                                true,
                                new DustParticleEffect(new Vector3f(1f, 0.639f, 0.f), 1f),
                                new Vector3f(0f, 0f, 0f)
                            ).createAction()
                        ),
                        Optional.empty(),
                        Optional.empty(),
                        5,
                        Optional.of(
                            new ResourceEntityConditionType(
                                PowerReference.resource(STACKING_PASSIVE_STACKS),
                                Comparison.GREATER_THAN_OR_EQUAL,
                                2
                            ).createCondition()
                        )
                    )
                ),
            true
        );
    }

    interface FaerieDust {
        int MIN = 0;
        int MAX = 120;
        int START_VALUE = MAX;

        // Ability Costs
        int COST_CRIT = 26;
        int COST_FLUTTER = 17;
        int COST_SHARE_BUFFS = 60;

        // Passive Over Time
        int PASSIVE_DRAIN = -2;
        int PASSIVE_GAIN_FROM_BLOCKS = 6;
        int PASSIVE_GAIN_FROM_LANTERN = 2;
    }
}
