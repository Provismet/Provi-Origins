package com.provismet.datagen.proviorigins.power;

import com.provismet.datagen.proviorigins.constants.PowerNames;
import com.provismet.datagen.proviorigins.provider.POPowerProvider;
import com.provismet.proviorigins.actions.entity.ParticleRingAction;
import com.provismet.proviorigins.content.PODamageTypes;
import com.provismet.proviorigins.content.registries.POItems;
import com.provismet.proviorigins.content.registries.POSounds;
import com.provismet.proviorigins.powers.ActiveItemPower;
import com.provismet.proviorigins.powers.EmissivePower;
import com.provismet.proviorigins.utility.BadgeUtil;
import com.provismet.proviorigins.utility.KeyUtil;
import com.provismet.proviorigins.utility.OriginList;
import com.provismet.proviorigins.utility.constants.SpriteLocations;
import com.provismet.proviorigins.utility.tags.POBiomeTags;
import com.provismet.proviorigins.utility.tags.POBlockTags;
import com.provismet.proviorigins.utility.tags.POItemTags;
import io.github.apace100.apoli.action.EntityAction;
import io.github.apace100.apoli.action.type.bientity.AddVelocityBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.DamageBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.ActorActionBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.SequenceBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.TargetActionBiEntityActionType;
import io.github.apace100.apoli.action.type.entity.AddVelocityEntityActionType;
import io.github.apace100.apoli.action.type.entity.ApplyEffectEntityActionType;
import io.github.apace100.apoli.action.type.entity.AreaOfEffectEntityActionType;
import io.github.apace100.apoli.action.type.entity.ChangeResourceEntityActionType;
import io.github.apace100.apoli.action.type.entity.EmitGameEventEntityActionType;
import io.github.apace100.apoli.action.type.entity.ExecuteCommandEntityActionType;
import io.github.apace100.apoli.action.type.entity.HealEntityActionType;
import io.github.apace100.apoli.action.type.entity.PlaySoundEntityActionType;
import io.github.apace100.apoli.action.type.entity.RaycastEntityActionType;
import io.github.apace100.apoli.action.type.entity.SpawnParticlesEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.IfElseEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.IfElseListEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.SequenceEntityActionType;
import io.github.apace100.apoli.action.type.meta.IfElseListMetaActionType;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.condition.type.bientity.meta.TargetConditionBiEntityConditionType;
import io.github.apace100.apoli.condition.type.biome.InTagBiomeConditionType;
import io.github.apace100.apoli.condition.type.block.BlockBlockConditionType;
import io.github.apace100.apoli.condition.type.block.BlockStateBlockConditionType;
import io.github.apace100.apoli.condition.type.block.InTagBlockConditionType;
import io.github.apace100.apoli.condition.type.block.meta.AllOfBlockConditionType;
import io.github.apace100.apoli.condition.type.block.meta.AnyOfBlockConditionType;
import io.github.apace100.apoli.condition.type.damage.InTagDamageConditionType;
import io.github.apace100.apoli.condition.type.entity.BiomeEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.BlockInRadiusEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.DimensionEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.EquippedItemEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.FallFlyingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.FluidHeightEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.InTagEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.LivingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.OnBlockEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.PowerActiveEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.RelativeHealthEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.ResourceEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.SubmergedInEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.AllOfEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.ConstantEntityConditionType;
import io.github.apace100.apoli.condition.type.item.IngredientItemConditionType;
import io.github.apace100.apoli.power.PowerReference;
import io.github.apace100.apoli.power.type.ActionOnCallbackPowerType;
import io.github.apace100.apoli.power.type.ActionOnHitPowerType;
import io.github.apace100.apoli.power.type.ActionOnKeyPressPowerType;
import io.github.apace100.apoli.power.type.ActionOverTimePowerType;
import io.github.apace100.apoli.power.type.ActionWhenDamageTakenPowerType;
import io.github.apace100.apoli.power.type.AttributePowerType;
import io.github.apace100.apoli.power.type.DamageOverTimePowerType;
import io.github.apace100.apoli.power.type.OverlayPowerType;
import io.github.apace100.apoli.power.type.ParticlePowerType;
import io.github.apace100.apoli.power.type.RecipePowerType;
import io.github.apace100.apoli.power.type.ResourcePowerType;
import io.github.apace100.apoli.power.type.SelfActionOnKillPowerType;
import io.github.apace100.apoli.power.type.StackingStatusEffectPowerType;
import io.github.apace100.apoli.power.type.TooltipPowerType;
import io.github.apace100.apoli.util.AttributedEntityAttributeModifier;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.apoli.util.ResourceOperation;
import io.github.apace100.apoli.util.Shape;
import io.github.apace100.apoli.util.Space;
import io.github.apace100.origins.registry.ModEnchantments;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.joml.Vector3f;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class DecayKraken {
    private static final Vec3d VEC3D_HALF = new Vec3d(0.5, 0.5, 0.5);

    public static void build (POPowerProvider.PowerCollector collector) {
        DecayKraken.buildDeathsVice(collector);
        DecayKraken.buildDropInkOnDeath(collector);
        DecayKraken.buildPropulsion(collector);
        DecayKraken.buildSlowOnLand(collector);
        DecayKraken.buildSoulConsume(collector);
        DecayKraken.buildSoulSteal(collector);
        DecayKraken.buildSoulSandSpreading(collector);
        DecayKraken.buildTentacleGrapple(collector);
        DecayKraken.buildWaterGlow(collector);
        DecayKraken.buildWaterSensitive(collector);
        DecayKraken.buildWitherHit(collector);
    }

    private static EntityAction addDeathsVice (int amount) {
        return new ChangeResourceEntityActionType(
            PowerReference.resource(PowerNames.DecayKraken.DEATHS_VICE),
            ResourceOperation.ADD,
            amount
        ).createAction();
    }

    private static EntityCondition compareDeathsVice (Comparison comparison, int amount) {
        return new ResourceEntityConditionType(
            PowerReference.resource(PowerNames.DecayKraken.DEATHS_VICE),
            comparison,
            amount
        ).createCondition();
    }

    private static IfElseListMetaActionType.ConditionedAction<EntityAction, EntityCondition> soulStealBideSound (int count) {
        return new IfElseListMetaActionType.ConditionedAction<>(
            new PlaySoundEntityActionType(
                POSounds.KRAKEN_BIDE,
                Optional.of(SoundCategory.PLAYERS),
                0.7f, 1f + 0.25f * count
            ).createAction(),
            new ResourceEntityConditionType(
                PowerReference.resource(PowerNames.DecayKraken.SOUL_STEAL.withSuffixedPath("_charging")),
                Comparison.EQUAL,
                count
            ).createCondition()
        );
    }

    private static void buildDeathsVice (POPowerProvider.PowerCollector collector) {
        CraftingRecipe specialLantern = new ShapelessRecipe(
            "",
            CraftingRecipeCategory.EQUIPMENT,
            POItems.SOUL_LAMP.getDefaultStack(),
            DefaultedList.ofSize(1, Ingredient.ofItems(Items.SOUL_LANTERN)
            )
        );

        Function<Integer, EntityAction> setRechargeResource = value -> new ChangeResourceEntityActionType(
            PowerReference.resource(PowerNames.DecayKraken.DEATHS_VICE_RECHARGE_RESOURCE),
            ResourceOperation.SET,
            value
        ).createAction();

        Function<Integer, EntityCondition> checkRechargeResource = value -> new ResourceEntityConditionType(
            PowerReference.resource(PowerNames.DecayKraken.DEATHS_VICE_RECHARGE_RESOURCE),
            Comparison.EQUAL,
            value
        ).createCondition();

        collector.add(
            PowerNames.DecayKraken.DEATHS_VICE,
            new ResourcePowerType(
                Optional.empty(),
                Optional.empty(),
                new HudRender(
                    Optional.empty(),
                    SpriteLocations.EXTRA_RESOURCES,
                    true,
                    false,
                    0,
                    0,
                    0
                ),
                0, DeathsVice.MAX,
                DeathsVice.START_VALUE
            )
        ).add(
            PowerNames.DecayKraken.DEATHS_VICE_EMPTY,
            new StackingStatusEffectPowerType(
                List.of(
                    new StatusEffectInstance(
                        StatusEffects.DARKNESS,
                        90,
                        0,
                        true,
                        false,
                        true
                    )
                ),
                -1, 1,
                90,
                10,
                Optional.of(compareDeathsVice(Comparison.EQUAL, 0))
            )
        ).add(
            PowerNames.DecayKraken.DEATHS_VICE_GAIN_ON_KILL,
            new SelfActionOnKillPowerType(
                addDeathsVice(DeathsVice.GAIN_ON_KILL),
                Optional.empty(),
                Optional.empty(),
                HudRender.DONT_RENDER,
                1,
                Optional.empty()
            )
        ).add(
            PowerNames.DecayKraken.DEATHS_VICE_LOW_VALUE_PENALTY,
            new StackingStatusEffectPowerType(
                List.of(
                    new StatusEffectInstance(
                        StatusEffects.WEAKNESS,
                        30,
                        0,
                        true,
                        true,
                        true
                    ),
                    new StatusEffectInstance(
                        StatusEffects.SLOWNESS,
                        30,
                        0,
                        true,
                        true,
                        true
                    )
                ),
                -1 ,1,
                30,
                10,
                Optional.of(compareDeathsVice(Comparison.LESS_THAN, DeathsVice.PENALTY_THRESHOLD))
            )
        ).add(
            PowerNames.DecayKraken.DEATHS_VICE_PASSIVE_DRAIN_AND_GAIN,
            new ActionOverTimePowerType(
                Optional.of(
                    new IfElseListEntityActionType(
                        List.of(
                            new IfElseListMetaActionType.ConditionedAction<>(
                                addDeathsVice(-3),
                                checkRechargeResource.apply(0)
                            ),
                            new IfElseListMetaActionType.ConditionedAction<>(
                                addDeathsVice(3),
                                checkRechargeResource.apply(1)
                            ),
                            new IfElseListMetaActionType.ConditionedAction<>(
                                addDeathsVice(8),
                                checkRechargeResource.apply(2)
                            ),
                            new IfElseListMetaActionType.ConditionedAction<>(
                                addDeathsVice(16),
                                checkRechargeResource.apply(3)
                            ),
                            new IfElseListMetaActionType.ConditionedAction<>(
                                addDeathsVice(38),
                                checkRechargeResource.apply(4)
                            )
                        )
                    ).createAction()
                ),
                Optional.empty(),
                Optional.empty(),
                20,
                Optional.empty()
            ),
            true
        ).add(
            PowerNames.DecayKraken.DEATHS_VICE_RECHARGE_RATE,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "special_lantern",
                    new RecipePowerType(specialLantern, 0)
                ).add(
                    "resource_adjust",
                    new ActionOverTimePowerType(
                        Optional.of(
                            new IfElseListEntityActionType(
                                List.of(
                                    new IfElseListMetaActionType.ConditionedAction<>(
                                        new IfElseListEntityActionType(
                                            List.of(
                                                new IfElseListMetaActionType.ConditionedAction<>(
                                                    setRechargeResource.apply(2),
                                                    new DimensionEntityConditionType(World.NETHER).createCondition(true)
                                                ),
                                                new IfElseListMetaActionType.ConditionedAction<>(
                                                    setRechargeResource.apply(4),
                                                    new BiomeEntityConditionType(
                                                        Optional.of(new InTagBiomeConditionType(POBiomeTags.SOUL_COLLECTOR).createCondition()),
                                                        Optional.empty(),
                                                        Optional.empty()
                                                    ).createCondition()
                                                ),
                                                new IfElseListMetaActionType.ConditionedAction<>(
                                                    setRechargeResource.apply(3),
                                                    new ConstantEntityConditionType(true).createCondition()
                                                )
                                            )
                                        ).createAction(),
                                        new BlockInRadiusEntityConditionType(
                                            new AnyOfBlockConditionType(
                                                List.of(
                                                    new InTagBlockConditionType(POBlockTags.SOUL_FIRES).createCondition(),
                                                    new AllOfBlockConditionType(
                                                        List.of(
                                                            new InTagBlockConditionType(POBlockTags.SOUL_FIRES_WITH_LIT_BLOCKSTATE).createCondition(),
                                                            new BlockStateBlockConditionType(
                                                                "lit",
                                                                null,
                                                                null,
                                                                true,
                                                                null
                                                            ).createCondition()
                                                        )
                                                    ).createCondition()
                                                )
                                            ).createCondition(),
                                            Shape.CUBE,
                                            Comparison.GREATER_THAN, 0,
                                            5
                                        ).createCondition()
                                    ),
                                    new IfElseListMetaActionType.ConditionedAction<>(
                                        new IfElseListEntityActionType(
                                            List.of(
                                                new IfElseListMetaActionType.ConditionedAction<>(
                                                    setRechargeResource.apply(1),
                                                    new DimensionEntityConditionType(World.NETHER).createCondition(true)
                                                ),
                                                new IfElseListMetaActionType.ConditionedAction<>(
                                                    setRechargeResource.apply(3),
                                                    new BiomeEntityConditionType(
                                                        Optional.of(new InTagBiomeConditionType(POBiomeTags.SOUL_COLLECTOR).createCondition()),
                                                        Optional.empty(),
                                                        Optional.empty()
                                                    ).createCondition()
                                                ),
                                                new IfElseListMetaActionType.ConditionedAction<>(
                                                    setRechargeResource.apply(2),
                                                    new ConstantEntityConditionType(true).createCondition()
                                                )
                                            )
                                        ).createAction(),
                                        new EquippedItemEntityConditionType(
                                            new IngredientItemConditionType(
                                                Ingredient.fromTag(POItemTags.SOUL_CARRY)
                                            ).createCondition(),
                                            AttributeModifierSlot.HAND
                                        ).createCondition()
                                    ),
                                    new IfElseListMetaActionType.ConditionedAction<>(
                                        setRechargeResource.apply(0),
                                        new ConstantEntityConditionType(true).createCondition()
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
            List.of(
                BadgeUtil.info(PowerNames.DecayKraken.DEATHS_VICE_RECHARGE_RATE),
                BadgeUtil.crafting(new RecipeEntry<>(OriginList.KRAKEN_OF_DECAY.identifier("qol_lantern"), specialLantern))
            )
        ).add(
            PowerNames.DecayKraken.DEATHS_VICE_RECHARGE_RESOURCE,
            new ResourcePowerType(
                Optional.empty(),
                Optional.empty(),
                HudRender.DONT_RENDER,
                0, 4,
                0
            ),
            true
        ).add(
            PowerNames.DecayKraken.DEATHS_VICE_RESET_ON_DEATH,
            new ActionOnCallbackPowerType(
                Optional.of(
                    new ChangeResourceEntityActionType(
                        PowerReference.resource(PowerNames.DecayKraken.DEATHS_VICE),
                        ResourceOperation.SET,
                        DeathsVice.START_VALUE
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

    private static void buildDropInkOnDeath (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.DecayKraken.DROP_INK_ON_DEATH,
            new ActionWhenDamageTakenPowerType(
                new IfElseEntityActionType(
                    new SubmergedInEntityConditionType(FluidTags.WATER).createCondition(),
                    new ExecuteCommandEntityActionType("summon minecraft:item ~ ~ ~ {Item:{id:\"minecraft:glow_ink_sac\",Count:1b}}").createAction(),
                    Optional.of(
                        new ExecuteCommandEntityActionType("summon minecraft:item ~ ~ ~ {Item:{id:\"minecraft:ink_sac\",Count:1b}}").createAction()
                    )
                ).createAction(),
                Optional.empty(),
                HudRender.DONT_RENDER,
                1,
                Optional.of(
                    new RelativeHealthEntityConditionType(
                        Comparison.LESS_THAN_OR_EQUAL,
                        0
                    ).createCondition()
                )
            ),
            true
        );
    }

    private static void buildPropulsion (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.DecayKraken.PROPULSION,
            new ActionOnKeyPressPowerType(
                new SequenceEntityActionType(
                    List.of(
                        new SpawnParticlesEntityActionType(
                            Optional.empty(),
                            ParticleTypes.SQUID_INK,
                            VEC3D_HALF,
                            VEC3D_HALF,
                            false,
                            0.25f,
                            30
                        ).createAction(),
                        new PlaySoundEntityActionType(
                            POSounds.KRAKEN_INK,
                            Optional.of(SoundCategory.PLAYERS),
                            1, 1
                        ).createAction(),
                        new EmitGameEventEntityActionType(GameEvent.SPLASH).createAction(),
                        new AreaOfEffectEntityActionType(
                            new TargetActionBiEntityActionType(
                                new ApplyEffectEntityActionType(
                                    List.of(new StatusEffectInstance(StatusEffects.BLINDNESS, 30))
                                ).createAction()
                            ).createAction(),
                            Optional.empty(),
                            Shape.SPHERE,
                            3,
                            false
                        ).createAction(),
                        new IfElseListEntityActionType(
                            List.of(
                                new IfElseListMetaActionType.ConditionedAction<>(
                                    new AddVelocityEntityActionType(
                                        new Vector3f(0f, 0f, 0.1f),
                                        Space.LOCAL,
                                        false
                                    ).createAction(),
                                    new FallFlyingEntityConditionType().createCondition()
                                ),
                                new IfElseListMetaActionType.ConditionedAction<>(
                                    new SequenceEntityActionType(
                                        List.of(
                                            new AddVelocityEntityActionType(
                                                new Vector3f(0f, 0f, 1.5f),
                                                Space.LOCAL,
                                                false
                                            ).createAction(),
                                            new ParticleRingAction(
                                                4,
                                                10,
                                                1,
                                                Space.LOCAL,
                                                0,
                                                false,
                                                ParticleTypes.SQUID_INK,
                                                new Vector3f(0, 1, -0.5f)
                                            ).createAction()
                                        )
                                    ).createAction(),
                                    new SubmergedInEntityConditionType(FluidTags.WATER).createCondition()
                                ),
                                new IfElseListMetaActionType.ConditionedAction<>(
                                    new AddVelocityEntityActionType(
                                        new Vector3f(0f, 0f, 0.5f),
                                        Space.LOCAL,
                                        false
                                    ).createAction(),
                                    new ConstantEntityConditionType(true).createCondition()
                                )
                            )
                        ).createAction(),
                        addDeathsVice(-DeathsVice.COST_PROPULSION)
                    )
                ).createAction(),
                HudRender.DONT_RENDER,
                1,
                KeyUtil.primary(),
                Optional.of(compareDeathsVice(Comparison.GREATER_THAN_OR_EQUAL, DeathsVice.COST_PROPULSION))
            ),
            List.of(
                BadgeUtil.active(),
                BadgeUtil.cost(PowerNames.DecayKraken.PROPULSION)
            )
        );
    }

    private static void buildSlowOnLand (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.DecayKraken.SLOW_ON_LAND,
            new AttributePowerType(
                List.of(
                    new AttributedEntityAttributeModifier(
                        EntityAttributes.GENERIC_MOVEMENT_SPEED,
                        new EntityAttributeModifier(
                            PowerNames.DecayKraken.SLOW_ON_LAND,
                            -0.2,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                        )
                    )
                ),
                false,
                Optional.of(
                    new FluidHeightEntityConditionType(
                        FluidTags.WATER,
                        Comparison.LESS_THAN_OR_EQUAL,
                        0
                    ).createCondition()
                )
            )
        );
    }

    private static void buildSoulConsume (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.DecayKraken.SOUL_CONSUME,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "max_souls",
                    new ActiveItemPower(
                        1,
                        HudRender.DONT_RENDER,
                        KeyUtil.quaternary(),
                        addDeathsVice(DeathsVice.MAX),
                        new IngredientItemConditionType(Ingredient.fromTag(POItemTags.MAX_SOUL_CONSUME)).createCondition(),
                        Optional.empty(),
                        1,
                        true,
                        Optional.of(compareDeathsVice(Comparison.LESS_THAN, DeathsVice.MAX))
                    )
                ).add(
                    "medium_souls",
                    new ActiveItemPower(
                        1,
                        HudRender.DONT_RENDER,
                        KeyUtil.quaternary(),
                        addDeathsVice(DeathsVice.GAIN_MEDIUM),
                        new IngredientItemConditionType(Ingredient.fromTag(POItemTags.MEDIUM_SOUL_CONSUME)).createCondition(),
                        Optional.empty(),
                        1,
                        true,
                        Optional.of(compareDeathsVice(Comparison.LESS_THAN, DeathsVice.MAX))
                    )
                ).add(
                    "weak_souls",
                    new ActiveItemPower(
                        1,
                        HudRender.DONT_RENDER,
                        KeyUtil.quaternary(),
                        addDeathsVice(DeathsVice.GAIN_WEAK),
                        new IngredientItemConditionType(Ingredient.fromTag(POItemTags.WEAK_SOUL_CONSUME)).createCondition(),
                        Optional.empty(),
                        1,
                        true,
                        Optional.of(compareDeathsVice(Comparison.LESS_THAN, DeathsVice.MAX))
                    )
                ).add(
                    "max_tooltip",
                    new TooltipPowerType(
                        Optional.of(new IngredientItemConditionType(Ingredient.fromTag(POItemTags.MAX_SOUL_CONSUME)).createCondition()),
                        List.of(Text.translatable("tooltip.proviorigins.decaykraken.max_soul")),
                        false,
                        20,
                        0,
                        Optional.empty()
                    )
                ).add(
                    "medium_tooltip",
                    new TooltipPowerType(
                        Optional.of(new IngredientItemConditionType(Ingredient.fromTag(POItemTags.MEDIUM_SOUL_CONSUME)).createCondition()),
                        List.of(Text.translatable("tooltip.proviorigins.decaykraken.medium_soul")),
                        false,
                        20,
                        0,
                        Optional.empty()
                    )
                ).add(
                    "weak_tooltip",
                    new TooltipPowerType(
                        Optional.of(new IngredientItemConditionType(Ingredient.fromTag(POItemTags.WEAK_SOUL_CONSUME)).createCondition()),
                        List.of(Text.translatable("tooltip.proviorigins.decaykraken.weak_soul")),
                        false,
                        20,
                        0,
                        Optional.empty()
                    )
                ),
            List.of(
                BadgeUtil.active(),
                BadgeUtil.cost(PowerNames.DecayKraken.SOUL_CONSUME)
            )
        );
    }

    private static void buildSoulSteal (POPowerProvider.PowerCollector collector) {
        Identifier soulStealCharging = PowerNames.DecayKraken.SOUL_STEAL.withSuffixedPath("_charging");

        collector.add(
            PowerNames.DecayKraken.SOUL_STEAL,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "activate_laser",
                    new ActionOnKeyPressPowerType(
                        new SequenceEntityActionType(
                            List.of(
                                new ChangeResourceEntityActionType(
                                    PowerReference.resource(soulStealCharging),
                                    ResourceOperation.SET,
                                    1
                                ).createAction(),
                                addDeathsVice(-DeathsVice.COST_SOUL_STEAL)
                            )
                        ).createAction(),
                        HudRender.DONT_RENDER,
                        1,
                        KeyUtil.secondary(),
                        Optional.of(compareDeathsVice(Comparison.GREATER_THAN_OR_EQUAL, DeathsVice.COST_SOUL_STEAL))
                    )
                ).add(
                    "charging",
                    new ResourcePowerType(
                        Optional.empty(),
                        Optional.of(
                            new RaycastEntityActionType(
                                Optional.of(
                                    new ChangeResourceEntityActionType(
                                        PowerReference.resource(soulStealCharging),
                                        ResourceOperation.SET,
                                        0
                                    ).createAction()
                                ),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.of(
                                    new SequenceBiEntityActionType(
                                        List.of(
                                            new ActorActionBiEntityActionType(
                                                new HealEntityActionType(5f).createAction()
                                            ).createAction(),
                                            new DamageBiEntityActionType(
                                                PODamageTypes.KRAKEN_SOUL_STEAL.getKey(),
                                                Optional.of(4f),
                                                List.of()
                                            ).createAction(),
                                            new TargetActionBiEntityActionType(
                                                new SpawnParticlesEntityActionType(
                                                    Optional.empty(),
                                                    ParticleTypes.SCULK_SOUL,
                                                    VEC3D_HALF,
                                                    VEC3D_HALF,
                                                    false,
                                                    0,
                                                    3
                                                ).createAction()
                                            ).createAction(),
                                            new TargetActionBiEntityActionType(
                                                new PlaySoundEntityActionType(
                                                    POSounds.KRAKEN_SOUL_STEAL,
                                                    Optional.of(SoundCategory.PLAYERS),
                                                    1, 1
                                                ).createAction()
                                            ).createAction()
                                        )
                                    ).createAction()
                                ),
                                Optional.empty(),
                                Optional.of(
                                    new TargetConditionBiEntityConditionType(
                                        new LivingEntityConditionType().createCondition()
                                    ).createCondition()
                                ),
                                RaycastContext.ShapeType.OUTLINE,
                                RaycastContext.FluidHandling.ANY,
                                Optional.empty(),
                                Space.WORLD,
                                Optional.empty(),
                                Optional.empty(),
                                Optional.of(8.0),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                1,
                                false,
                                true,
                                true
                            ).createAction()
                        ),
                        HudRender.DONT_RENDER,
                        0, 5,
                        0
                    )
                ).add(
                    "apply_charge",
                    new ActionOverTimePowerType(
                        Optional.of(
                            new SequenceEntityActionType(
                                List.of(
                                    new IfElseListEntityActionType(
                                        List.of(
                                            soulStealBideSound(1),
                                            soulStealBideSound(2),
                                            soulStealBideSound(3),
                                            soulStealBideSound(4)
                                        )
                                    ).createAction(),
                                    new RaycastEntityActionType(
                                        Optional.empty(),
                                        Optional.empty(),
                                        Optional.empty(),
                                        Optional.empty(),
                                        Optional.empty(),
                                        Optional.empty(),
                                        RaycastContext.ShapeType.OUTLINE,
                                        RaycastContext.FluidHandling.ANY,
                                        Optional.empty(),
                                        Space.LOCAL,
                                        Optional.empty(),
                                        Optional.empty(),
                                        Optional.of(8.0),
                                        Optional.empty(),
                                        Optional.of("particle proviorigins:soul_circle"),
                                        Optional.empty(),
                                        0.333,
                                        false,
                                        true,
                                        true
                                    ).createAction(),
                                    new ChangeResourceEntityActionType(
                                        PowerReference.resource(soulStealCharging),
                                        ResourceOperation.SET,
                                        1
                                    ).createAction()
                                )
                            ).createAction()
                        ),
                        Optional.empty(),
                        Optional.empty(),
                        10,
                        Optional.of(
                            new ResourceEntityConditionType(
                                PowerReference.resource(soulStealCharging),
                                Comparison.GREATER_THAN,
                                0
                            ).createCondition()
                        )
                    )
                ),
            List.of(
                BadgeUtil.active(),
                BadgeUtil.cost(PowerNames.DecayKraken.SOUL_STEAL)
            )
        );
    }

    private static void buildSoulSandSpreading (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.DecayKraken.SOULSAND_SPREADING,
            new SelfActionOnKillPowerType(
                new ExecuteCommandEntityActionType("setblock ~ ~-1 ~ minecraft:soul_sand").createAction(),
                Optional.of(new InTagEntityConditionType(EntityTypeTags.UNDEAD).createCondition()),
                Optional.empty(),
                HudRender.DONT_RENDER,
                1,
                Optional.of(
                    new OnBlockEntityConditionType(
                        Optional.of(new BlockBlockConditionType(Blocks.COARSE_DIRT).createCondition())
                    ).createCondition()
                )
            )
        );
    }

    private static void buildTentacleGrapple (POPowerProvider.PowerCollector collector) {
        Function<Boolean, EntityAction> pullAction = inWater -> new RaycastEntityActionType(
            Optional.empty(),
            Optional.of(
                new RaycastEntityActionType(
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    RaycastContext.ShapeType.OUTLINE,
                    RaycastContext.FluidHandling.NONE,
                    Optional.empty(), Space.LOCAL,
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of(12.0),
                    Optional.empty(),
                    Optional.of("particle minecraft:sonic_boom"),
                    Optional.empty(),
                    1, false,
                    true, true
                ).createAction()
            ),
            Optional.empty(),
            Optional.of(
                new SequenceBiEntityActionType(
                    List.of(
                        new AddVelocityBiEntityActionType(
                            new Vector3f(0f, 0f, -2f),
                            AddVelocityBiEntityActionType.Reference.POSITION,
                            false
                        ).createAction(),
                        new TargetActionBiEntityActionType(
                            new ApplyEffectEntityActionType(
                                List.of(new StatusEffectInstance(StatusEffects.SLOWNESS, 120, inWater ? 0 : 1))
                            ).createAction()
                        ).createAction()
                    )
                ).createAction()
            ),
            Optional.empty(),
            Optional.empty(),
            RaycastContext.ShapeType.COLLIDER,
            RaycastContext.FluidHandling.NONE,
            Optional.empty(),
            Space.LOCAL,
            Optional.empty(),
            Optional.empty(),
            Optional.of(12.0),
            Optional.empty(),
            Optional.of(inWater ? "particle minecraft:glow_squid_ink" : "particle minecraft:squid_ink"),
            Optional.empty(),
            0.333,
            false,
            true,
            true
        ).createAction();

        collector.add(
            PowerNames.DecayKraken.TENTACLE_GRAPPLE,
            new ActionOnKeyPressPowerType(
                new SequenceEntityActionType(
                    List.of(
                        new IfElseEntityActionType(
                            new SubmergedInEntityConditionType(FluidTags.WATER).createCondition(),
                            pullAction.apply(true),
                            Optional.of(pullAction.apply(false))
                        ).createAction(),
                        addDeathsVice(-DeathsVice.COST_TENTACLE_GRAPPLE)
                    )
                ).createAction(),
                HudRender.DONT_RENDER,
                1,
                KeyUtil.tertiary(),
                Optional.of(compareDeathsVice(Comparison.GREATER_THAN_OR_EQUAL, DeathsVice.COST_TENTACLE_GRAPPLE))
            ),
            List.of(
                BadgeUtil.active(),
                BadgeUtil.cost(PowerNames.DecayKraken.TENTACLE_GRAPPLE)
            )
        );
    }

    private static void buildWaterGlow (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.DecayKraken.WATER_GLOW,
            new EmissivePower(
                15, 15,
                Optional.of(new SubmergedInEntityConditionType(FluidTags.WATER).createCondition())
            )
        );
    }

    private static void buildWaterSensitive (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.DecayKraken.WATER_SENSITIVE,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "fresh_damage",
                    new DamageOverTimePowerType(
                        PODamageTypes.FRESHWATER.getKey(),
                        Optional.of(ModEnchantments.WATER_PROTECTION),
                        0.4f,
                        3f, 3f,
                        20,
                        30,
                        Optional.of(
                            new AllOfEntityConditionType(
                                List.of(
                                    new BiomeEntityConditionType(
                                        Optional.of(
                                            new InTagBiomeConditionType(POBiomeTags.HAS_SALTWATER).createCondition(true)
                                        ),
                                        Optional.empty(),
                                        Optional.empty()
                                    ).createCondition(),
                                    new FluidHeightEntityConditionType(
                                        FluidTags.WATER,
                                        Comparison.GREATER_THAN,
                                        0
                                    ).createCondition()
                                )
                            ).createCondition()
                        )
                    )
                ).add(
                    "warning_overlay",
                    new OverlayPowerType(
                        Identifier.ofVanilla("textures/misc/nausea.png"),
                        OverlayPowerType.DrawMode.NAUSEA,
                        OverlayPowerType.DrawPhase.BELOW_HUD,
                        0.6f,
                        0.3f, 0.7f, 0.3f,
                        true,
                        false,
                        0,
                        Optional.of(
                            new PowerActiveEntityConditionType(
                                PowerReference.of(PowerNames.DecayKraken.WATER_SENSITIVE.withSuffixedPath("_fresh_damage"))
                            ).createCondition()
                        )
                    )
                ),
            List.of(
                BadgeUtil.info(PowerNames.DecayKraken.WATER_SENSITIVE, 1),
                BadgeUtil.star(PowerNames.DecayKraken.WATER_SENSITIVE, 2)
            )
        );
    }

    private static void buildWitherHit (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.DecayKraken.WITHER_HIT,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "particle_effect",
                    new ParticlePowerType(
                        Optional.empty(),
                        ParticleTypes.SCULK_SOUL,
                        VEC3D_HALF,
                        VEC3D_HALF,
                        6,
                        1,
                        0,
                        false,
                        false,
                        false,
                        Optional.of(compareDeathsVice(Comparison.GREATER_THAN_OR_EQUAL, DeathsVice.MAX - DeathsVice.SINGLE_BAR))
                    )
                ).add(
                    "apply_wither",
                    new ActionOnHitPowerType(
                        Optional.of(
                            new SequenceBiEntityActionType(
                                List.of(
                                    new TargetActionBiEntityActionType(
                                        new ApplyEffectEntityActionType(
                                            List.of(new StatusEffectInstance(StatusEffects.WITHER, 120, 1))
                                        ).createAction()
                                    ).createAction(),
                                    new ActorActionBiEntityActionType(
                                        addDeathsVice(-DeathsVice.COST_WITHER_HIT)
                                    ).createAction()
                                )
                            ).createAction()
                        ),
                        Optional.empty(),
                        Optional.of(
                            new InTagDamageConditionType(
                                DamageTypeTags.IS_PLAYER_ATTACK
                            ).createCondition()
                        ),
                        HudRender.DONT_RENDER,
                        1,
                        Optional.of(
                            compareDeathsVice(Comparison.GREATER_THAN_OR_EQUAL, DeathsVice.MAX - DeathsVice.SINGLE_BAR)
                        )
                    )
                ),
            List.of(
                BadgeUtil.cost(PowerNames.DecayKraken.WITHER_HIT)
            )
        );
    }

    interface DeathsVice {
        int MAX = 1200;
        int START_VALUE = MAX / 600;
        int SINGLE_BAR = 1200 / 7;
        int HALF_BAR = SINGLE_BAR / 2;
        int QUARTER_BAR = SINGLE_BAR / 4;

        int GAIN_ON_KILL = HALF_BAR;
        int PENALTY_THRESHOLD = SINGLE_BAR + HALF_BAR;

        // Abilities
        int COST_PROPULSION = SINGLE_BAR;
        int COST_SOUL_STEAL = SINGLE_BAR + HALF_BAR;
        int COST_TENTACLE_GRAPPLE = SINGLE_BAR + HALF_BAR;
        int COST_WITHER_HIT = HALF_BAR + 1;

        // Gain
        int GAIN_MEDIUM = SINGLE_BAR;
        int GAIN_WEAK = QUARTER_BAR;
    }
}
