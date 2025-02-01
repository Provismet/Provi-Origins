package com.provismet.datagen.proviorigins;

import com.provismet.datagen.proviorigins.constants.PowerNames;
import com.provismet.datagen.proviorigins.power.Alraune;
import com.provismet.datagen.proviorigins.power.Crystalliser;
import com.provismet.datagen.proviorigins.power.DecayKraken;
import com.provismet.datagen.proviorigins.power.Drakling;
import com.provismet.datagen.proviorigins.power.FaeMoth;
import com.provismet.datagen.proviorigins.power.JellySculk;
import com.provismet.datagen.proviorigins.power.Splinter;
import com.provismet.datagen.proviorigins.power.VoidLily;
import com.provismet.datagen.proviorigins.provider.POPowerProvider;
import com.provismet.proviorigins.actions.entity.DoubleJumpAction;
import com.provismet.proviorigins.conditions.entity.ClientServerEntityConditionType;
import com.provismet.proviorigins.conditions.entity.VelocityYEntityConditionType;
import com.provismet.proviorigins.content.PODamageTypes;
import com.provismet.proviorigins.powers.ModifyDarknessPulsePower;
import com.provismet.proviorigins.powers.OccludeVibrationsPower;
import com.provismet.proviorigins.powers.PreventCriticalHitPower;
import com.provismet.proviorigins.powers.PreventPotionCloudPower;
import com.provismet.proviorigins.utility.KeyUtil;
import com.provismet.proviorigins.utility.tags.POBiomeTags;
import com.provismet.proviorigins.utility.tags.POEntityTypeTags;
import com.provismet.proviorigins.utility.tags.POFluidTags;
import com.provismet.proviorigins.utility.tags.POItemTags;
import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import io.github.apace100.apoli.action.type.entity.AddVelocityEntityActionType;
import io.github.apace100.apoli.action.type.entity.ChangeResourceEntityActionType;
import io.github.apace100.apoli.action.type.entity.ExecuteCommandEntityActionType;
import io.github.apace100.apoli.action.type.entity.SetOnFireEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.IfElseListEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.SequenceEntityActionType;
import io.github.apace100.apoli.action.type.meta.IfElseListMetaActionType;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.condition.ItemCondition;
import io.github.apace100.apoli.condition.type.bientity.meta.TargetConditionBiEntityConditionType;
import io.github.apace100.apoli.condition.type.biome.InTagBiomeConditionType;
import io.github.apace100.apoli.condition.type.biome.TemperatureBiomeConditionType;
import io.github.apace100.apoli.condition.type.damage.InTagDamageConditionType;
import io.github.apace100.apoli.condition.type.damage.TypeDamageConditionType;
import io.github.apace100.apoli.condition.type.damage.meta.AnyOfDamageConditionType;
import io.github.apace100.apoli.condition.type.entity.BiomeEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.CreativeFlyingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.ExposedToSunEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.FallDistanceEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.FallFlyingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.FluidHeightEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.GameModeEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.InRainEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.InTagEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.OnBlockEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.ResourceEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.SneakingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.SubmergedInEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.AllOfEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.AnyOfEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.ConstantEntityConditionType;
import io.github.apace100.apoli.condition.type.item.FoodItemConditionType;
import io.github.apace100.apoli.condition.type.item.IngredientItemConditionType;
import io.github.apace100.apoli.condition.type.item.meta.AllOfItemConditionType;
import io.github.apace100.apoli.power.PowerReference;
import io.github.apace100.apoli.power.type.ActionOnItemUsePowerType;
import io.github.apace100.apoli.power.type.ActionOnKeyPressPowerType;
import io.github.apace100.apoli.power.type.ActionOverTimePowerType;
import io.github.apace100.apoli.power.type.AttributePowerType;
import io.github.apace100.apoli.power.type.ModifyDamageDealtPowerType;
import io.github.apace100.apoli.power.type.ModifyDamageTakenPowerType;
import io.github.apace100.apoli.power.type.ModifyExhaustionPowerType;
import io.github.apace100.apoli.power.type.ModifyExperiencePowerType;
import io.github.apace100.apoli.power.type.ModifyFallingPowerType;
import io.github.apace100.apoli.power.type.OverlayPowerType;
import io.github.apace100.apoli.power.type.PreventEntityUsePowerType;
import io.github.apace100.apoli.power.type.PreventGameEventPowerType;
import io.github.apace100.apoli.power.type.PreventItemUsePowerType;
import io.github.apace100.apoli.power.type.ResourcePowerType;
import io.github.apace100.apoli.power.type.StackingStatusEffectPowerType;
import io.github.apace100.apoli.util.AttributedEntityAttributeModifier;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.apoli.util.ResourceOperation;
import io.github.apace100.apoli.util.Space;
import io.github.apace100.apoli.util.modifier.Modifier;
import io.github.apace100.apoli.util.modifier.ModifierOperation;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.GameEventTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.biome.Biome;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class PowerGenerator extends POPowerProvider {
    public static final Vec3d VEC3D_HALF = new Vec3d(0.5, 0.5, 0.5);

    private static final TagKey<Item> IGNORE_DIET = TagKey.of(RegistryKeys.ITEM, Identifier.of("origins", "ignore_diet"));

    public PowerGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    protected void generate (PowerCollector collector) {
        generateCommon(collector);
        Alraune.build(collector);
        Crystalliser.build(collector);
        DecayKraken.build(collector);
        Drakling.build(collector);
        FaeMoth.build(collector);
        JellySculk.build(collector);
        Splinter.build(collector);
        VoidLily.build(collector);
    }

    public static NbtCompound createItemEntityNbt (Identifier id) {
        NbtCompound nbt = new NbtCompound();
        NbtCompound inner = new NbtCompound();
        inner.putString("id", id.toString());
        inner.putByte("Count", (byte)1);
        nbt.put("Item", inner);
        return nbt;
    }

    public static ItemCondition ingredientConditionFromTag (TagKey<Item> itemTag) {
        return new IngredientItemConditionType(Ingredient.fromTag(itemTag)).createCondition();
    }

    public static EntityCondition biomeEntityConditionFromTag (TagKey<Biome> biomeTag, boolean invert) {
        return new BiomeEntityConditionType(
            Optional.of(new InTagBiomeConditionType(biomeTag).createCondition()),
            Optional.empty(),
            Optional.empty()
        ).createCondition(invert);
    }

    private void generateCommon (PowerCollector collector) {
        collector.add(
            PowerNames.Common.AMPHIBIOUS,
            new StackingStatusEffectPowerType(
                List.of(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 40, 0, true, false, false)),
                -1, 1, 40, 10,
                Optional.of(new SubmergedInEntityConditionType(FluidTags.WATER).createCondition())
            )
        ).add(
            PowerNames.Common.ANGRY_GOLEMS,
            new ActionOverTimePowerType(
                Optional.of(new ExecuteCommandEntityActionType("data modify entity @e[limit=1,distance=..32,type=minecraft:iron_golem,sort=nearest] AngryAt set from entity @s UUID").createAction()),
                Optional.empty(),
                Optional.empty(),
                40,
                Optional.empty()
            )
        ).add(
            PowerNames.Common.BRITTLE,
            new AttributePowerType(
                List.of(
                    new AttributedEntityAttributeModifier(
                        EntityAttributes.GENERIC_MAX_HEALTH,
                        new EntityAttributeModifier(PowerNames.Common.BRITTLE, -10.0, EntityAttributeModifier.Operation.ADD_VALUE)
                    )
                ),
                true,
                Optional.empty()
            )
        ).add(
            PowerNames.Common.DOUBLE_FIRE_DAMAGE,
            new ModifyDamageTakenPowerType(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(new InTagDamageConditionType(DamageTypeTags.IS_FIRE).createCondition()),
                List.of(Modifier.of(ModifierOperation.MULTIPLY_BASE_ADDITIVE, 1)),
                Optional.empty()
            )
        ).add(
            PowerNames.Common.DOUBLE_JUMP,
            new MultiplePowerJsonBuilder()
                .add(
                    "jump_cooldown",
                    new ResourcePowerType(
                        Optional.empty(),
                        Optional.empty(),
                        HudRender.DONT_RENDER,
                        0, 1, 0
                    )
                )
                .add(
                    "double_jump",
                    new ActionOnKeyPressPowerType(
                        new SequenceEntityActionType(
                            List.of(
                                new DoubleJumpAction(0.75, true).createAction(),
                                new ChangeResourceEntityActionType(
                                    PowerReference.resource(PowerNames.Common.DOUBLE_JUMP.withSuffixedPath("_jump_cooldown")),
                                    ResourceOperation.SET,
                                    1
                                ).createAction()
                            )
                        ).createAction(),
                        HudRender.DONT_RENDER,
                        1,
                        KeyUtil.create("key.jump"),
                        Optional.of(
                            new AllOfEntityConditionType(
                                List.of(
                                    new ClientServerEntityConditionType(true, false).createCondition(),
                                    new ResourceEntityConditionType(
                                        PowerReference.resource(PowerNames.Common.DOUBLE_JUMP.withSuffixedPath("_jump_cooldown")),
                                        Comparison.EQUAL,
                                        0
                                    ).createCondition(),
                                    new OnBlockEntityConditionType(Optional.empty()).createCondition(true),
                                    new AnyOfEntityConditionType(
                                        List.of(
                                            new VelocityYEntityConditionType(Comparison.LESS_THAN, -0.1).createCondition(),
                                            new VelocityYEntityConditionType(Comparison.GREATER_THAN, 0.1).createCondition()
                                        )
                                    ).createCondition(),
                                    new CreativeFlyingEntityConditionType().createCondition(true),
                                    new FallFlyingEntityConditionType().createCondition(true),
                                    new SubmergedInEntityConditionType(POFluidTags.PREVENTS_DOUBLE_JUMP).createCondition(true)
                                )
                            ).createCondition()
                        )
                    )
                ).add(
                    "reset_cooldown_on_landing",
                    new ActionOverTimePowerType(
                        Optional.empty(),
                        Optional.of(
                            new ChangeResourceEntityActionType(
                                PowerReference.resource(PowerNames.Common.DOUBLE_JUMP.withSuffixedPath("_jump_cooldown")),
                                ResourceOperation.SET,
                                0
                            ).createAction()
                        ),
                        Optional.empty(),
                        5,
                        Optional.of(new OnBlockEntityConditionType(Optional.empty()).createCondition())
                    )
                )
        ).add(
            PowerNames.Common.DRIED_UP,
            new MultiplePowerJsonBuilder()
                .add(
                    "dry",
                    new ActionOverTimePowerType(
                        Optional.of(
                            new IfElseListEntityActionType(
                                List.of(
                                    new IfElseListMetaActionType.ConditionedAction<>(
                                        new ChangeResourceEntityActionType(
                                            PowerReference.resource(PowerNames.Common.DRIED_UP.withSuffixedPath("_hydrate")),
                                            ResourceOperation.SET,
                                            30
                                        ).createAction(),
                                        new AnyOfEntityConditionType(
                                            List.of(
                                                new GameModeEntityConditionType(GameMode.CREATIVE).createCondition(),
                                                new GameModeEntityConditionType(GameMode.SPECTATOR).createCondition()
                                            )
                                        ).createCondition()
                                    ),
                                    new IfElseListMetaActionType.ConditionedAction<>(
                                        new ChangeResourceEntityActionType(
                                            PowerReference.resource(PowerNames.Common.DRIED_UP.withSuffixedPath("_hydrate")),
                                            ResourceOperation.ADD,
                                            5
                                        ).createAction(),
                                        new AnyOfEntityConditionType(
                                            List.of(
                                                new FluidHeightEntityConditionType(
                                                    FluidTags.WATER,
                                                    Comparison.GREATER_THAN,
                                                    0
                                                ).createCondition(),
                                                new InRainEntityConditionType().createCondition()
                                            )
                                        ).createCondition()
                                    ),
                                    new IfElseListMetaActionType.ConditionedAction<>(
                                        new ChangeResourceEntityActionType(
                                            PowerReference.resource(PowerNames.Common.DRIED_UP.withSuffixedPath("_hydrate")),
                                            ResourceOperation.ADD,
                                            -1
                                        ).createAction(),
                                        new AnyOfEntityConditionType(
                                            List.of(
                                                new AllOfEntityConditionType(
                                                    List.of(
                                                        new BiomeEntityConditionType(
                                                            Optional.of(new TemperatureBiomeConditionType(Comparison.GREATER_THAN, 1.5f).createCondition()),
                                                            Optional.empty(),
                                                            Optional.empty()
                                                        ).createCondition(),
                                                        new ExposedToSunEntityConditionType().createCondition()
                                                    )
                                                ).createCondition(),
                                                new BiomeEntityConditionType(
                                                    Optional.of(new InTagBiomeConditionType(POBiomeTags.HOT_UNDERGROUND).createCondition()),
                                                    Optional.empty(),
                                                    Optional.empty()
                                                ).createCondition()
                                            )
                                        ).createCondition()
                                    ),
                                    new IfElseListMetaActionType.ConditionedAction<>(
                                        new ChangeResourceEntityActionType(
                                            PowerReference.resource(PowerNames.Common.DRIED_UP.withSuffixedPath("_hydrate")),
                                            ResourceOperation.ADD,
                                            1
                                        ).createAction(),
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
                )
                .add(
                    "burn",
                    new ActionOverTimePowerType(
                        Optional.of(new SetOnFireEntityActionType(5).createAction()),
                        Optional.empty(),
                        Optional.empty(),
                        20,
                        Optional.of(
                            new ResourceEntityConditionType(
                                PowerReference.resource(PowerNames.Common.DRIED_UP.withSuffixedPath("_hydrate")),
                                Comparison.LESS_THAN_OR_EQUAL,
                                0
                            ).createCondition()
                        )
                    )
                )
                .add(
                    "hydrate",
                    new ResourcePowerType(
                        Optional.empty(),
                        Optional.empty(),
                        HudRender.DONT_RENDER,
                        0, 30, 30
                    )
                )
                .add(
                    "drinking",
                    new ActionOnItemUsePowerType(
                        Optional.of(
                            new ChangeResourceEntityActionType(
                                PowerReference.resource(PowerNames.Common.DRIED_UP.withSuffixedPath("_hydrate")),
                                ResourceOperation.SET,
                                30
                            ).createAction()
                        ),
                        Optional.empty(),
                        Optional.of(
                            new IngredientItemConditionType(
                                Ingredient.fromTag(POItemTags.HYDRATE_ON_USE)
                            ).createCondition()
                        ),
                        ActionOnItemUsePowerType.TriggerType.FINISH,
                        0,
                        Optional.empty()
                    )
                )
                .add(
                    "overlay1",
                    new OverlayPowerType(
                        Identifier.ofVanilla("textures/misc/nausea.png"),
                        OverlayPowerType.DrawMode.NAUSEA,
                        OverlayPowerType.DrawPhase.BELOW_HUD,
                        0.3f,
                        1f,
                        0.41f,
                        0f,
                        true,
                        false,
                        0,
                        Optional.of(
                            new AllOfEntityConditionType(
                                List.of(
                                    new ResourceEntityConditionType(
                                        PowerReference.resource(PowerNames.Common.DRIED_UP.withSuffixedPath("_hydrate")),
                                        Comparison.LESS_THAN_OR_EQUAL,
                                        20
                                    ).createCondition(),
                                    new ResourceEntityConditionType(
                                        PowerReference.resource(PowerNames.Common.DRIED_UP.withSuffixedPath("_hydrate")),
                                        Comparison.GREATER_THAN,
                                        10
                                    ).createCondition()
                                )
                            ).createCondition()
                        )
                    )
                )
                .add(
                    "overlay2",
                    new OverlayPowerType(
                        Identifier.ofVanilla("textures/misc/nausea.png"),
                        OverlayPowerType.DrawMode.NAUSEA,
                        OverlayPowerType.DrawPhase.BELOW_HUD,
                        0.45f,
                        1f,
                        0.3f,
                        0f,
                        true,
                        false,
                        0,
                        Optional.of(
                            new AllOfEntityConditionType(
                                List.of(
                                    new ResourceEntityConditionType(
                                        PowerReference.resource(PowerNames.Common.DRIED_UP.withSuffixedPath("_hydrate")),
                                        Comparison.LESS_THAN_OR_EQUAL,
                                        10
                                    ).createCondition(),
                                    new ResourceEntityConditionType(
                                        PowerReference.resource(PowerNames.Common.DRIED_UP.withSuffixedPath("_hydrate")),
                                        Comparison.GREATER_THAN,
                                        5
                                    ).createCondition()
                                )
                            ).createCondition()
                        )
                    )
                )
                .add(
                    "overlay3",
                    new OverlayPowerType(
                        Identifier.ofVanilla("textures/misc/nausea.png"),
                        OverlayPowerType.DrawMode.NAUSEA,
                        OverlayPowerType.DrawPhase.BELOW_HUD,
                        0.6f,
                        1f,
                        0f,
                        0f,
                        true,
                        false,
                        0,
                        Optional.of(
                            new ResourceEntityConditionType(
                                PowerReference.resource(PowerNames.Common.DRIED_UP.withSuffixedPath("_hydrate")),
                                Comparison.LESS_THAN_OR_EQUAL,
                                5
                            ).createCondition()
                        )
                    )
                )
        ).add(
            PowerNames.Common.EXHAUST_MORE,
            new ModifyExhaustionPowerType(
                List.of(Modifier.of(ModifierOperation.MULTIPLY_BASE_ADDITIVE, 0.5)),
                Optional.empty()
            )
        ).add(
            PowerNames.Common.HEARTY,
            new AttributePowerType(
                List.of(
                    new AttributedEntityAttributeModifier(
                        EntityAttributes.GENERIC_MAX_HEALTH,
                        new EntityAttributeModifier(
                            PowerNames.Common.HEARTY,
                            4.0,
                            EntityAttributeModifier.Operation.ADD_VALUE
                        )
                    )
                ),
                true,
                Optional.empty()
            )
        ).add(
            PowerNames.Common.IGNORE_CORRUPTION,
            new ModifyDamageTakenPowerType(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(new TypeDamageConditionType(PODamageTypes.VOID_CORRUPTION.getKey()).createCondition()),
                List.of(Modifier.of(ModifierOperation.SET_TOTAL, 0)),
                Optional.empty()
            ),
            true
        ).add(
            PowerNames.Common.IGNORE_DARKNESS_PULSE,
            new ModifyDarknessPulsePower(0, 0, Optional.empty()),
            true
        ).add(
            PowerNames.Common.MEDIUM_TALLER,
            new AttributePowerType(
                List.of(
                    new AttributedEntityAttributeModifier(
                        EntityAttributes.GENERIC_SCALE,
                        new EntityAttributeModifier(
                            PowerNames.Common.MEDIUM_TALLER,
                            0.25,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                        )
                    )
                ),
                false,
                Optional.empty()
            )
        ).add(
            PowerNames.Common.MORE_EXPERIENCE,
            new ModifyExperiencePowerType(
                List.of(Modifier.of(ModifierOperation.MULTIPLY_BASE_ADDITIVE, 0.5)),
                Optional.empty()
            )
        ).add(
            PowerNames.Common.NO_BOWS,
            new PreventItemUsePowerType(
                Optional.of(new IngredientItemConditionType(Ingredient.fromTag(ConventionalItemTags.BOW_TOOLS)).createCondition()),
                Optional.empty()
            )
        ).add(
            PowerNames.Common.NO_CRITS,
            new PreventCriticalHitPower(Optional.empty())
        ).add(
            PowerNames.Common.NO_FOOD,
            new PreventItemUsePowerType(
                Optional.of(
                    new AllOfItemConditionType(
                        List.of(
                            new IngredientItemConditionType(Ingredient.fromTag(IGNORE_DIET)).createCondition(true),
                            new FoodItemConditionType().createCondition()
                        )
                    ).createCondition()
                ),
                Optional.empty()
            )
        ).add(
            PowerNames.Common.NO_GAME_EVENTS,
            new MultiplePowerJsonBuilder()
                .add(
                    "prevent_most",
                    new PreventGameEventPowerType(
                        Optional.empty(),
                        List.of(),
                        Optional.of(GameEventTags.VIBRATIONS),
                        Optional.empty()
                    )
                )
                .add(
                    "occulude",
                    new OccludeVibrationsPower(Optional.empty())
                )
        ).add(
            PowerNames.Common.NO_POTIONS,
            new PreventItemUsePowerType(
                Optional.of(new IngredientItemConditionType(Ingredient.ofItems(Items.POTION)).createCondition()),
                Optional.empty()
            )
        ).add(
            PowerNames.Common.NO_SPLASH_POTIONS,
            new PreventPotionCloudPower(Optional.empty())
        ).add(
            PowerNames.Common.NO_TRADES,
            new PreventEntityUsePowerType(
                Optional.empty(),
                Optional.of(
                    new TargetConditionBiEntityConditionType(
                        new InTagEntityConditionType(
                            POEntityTypeTags.CAN_TRADE
                        ).createCondition()
                    ).createCondition()
                ),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                EnumSet.allOf(Hand.class),
                Optional.empty()
            )
        ).add(
            PowerNames.Common.PESCATARIAN,
            new PreventItemUsePowerType(
                Optional.of(
                    new AllOfItemConditionType(
                        List.of(
                            new IngredientItemConditionType(Ingredient.fromTag(ItemTags.FISHES)).createCondition(true),
                            new IngredientItemConditionType(Ingredient.fromTag(IGNORE_DIET)).createCondition(true),
                            new FoodItemConditionType().createCondition()
                        )
                    ).createCondition()
                ),
                Optional.empty()
            )
        ).add(
            PowerNames.Common.POOR_STRENGTH,
            new ModifyDamageDealtPowerType(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(
                    new AnyOfDamageConditionType(
                        List.of(
                            new InTagDamageConditionType(DamageTypeTags.IS_PLAYER_ATTACK).createCondition(),
                            new TypeDamageConditionType(DamageTypes.MOB_ATTACK).createCondition(),
                            new TypeDamageConditionType(DamageTypes.MOB_ATTACK_NO_AGGRO).createCondition()
                        )
                    ).createCondition()
                ),
                List.of(Modifier.of(ModifierOperation.MULTIPLY_TOTAL_ADDITIVE, -2.0/3.0)),
                Optional.empty()
            )
        ).add(
            PowerNames.Common.SLOW_FALL,
            new MultiplePowerJsonBuilder()
                .add(
                    "slow_falling",
                    new ModifyFallingPowerType(
                        List.of(),
                        Optional.of(0.01),
                        false,
                        Optional.of(new SneakingEntityConditionType().createCondition())
                    )
                )
                .add(
                    "cancel_momentum",
                    new ActionOnKeyPressPowerType(
                        new AddVelocityEntityActionType(
                            Vec3d.ZERO.toVector3f(),
                            Space.WORLD,
                            true
                        ).createAction(),
                        HudRender.DONT_RENDER,
                        1,
                        KeyUtil.create("key.sneak"),
                        Optional.of(new FallDistanceEntityConditionType(Comparison.GREATER_THAN, 0).createCondition())
                    )
                )
        ).add(
            PowerNames.Common.SLOW_SWIM,
            new AttributePowerType(
                List.of(
                    new AttributedEntityAttributeModifier(
                        AdditionalEntityAttributes.WATER_SPEED,
                        new EntityAttributeModifier(
                            PowerNames.Common.SLOW_SWIM,
                            -0.5,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                        )
                    )
                ),
                false,
                Optional.empty()
            )
        ).add(
            PowerNames.Common.SMALLER,
            new AttributePowerType(
                List.of(
                    new AttributedEntityAttributeModifier(
                        EntityAttributes.GENERIC_SCALE,
                        new EntityAttributeModifier(
                            PowerNames.Common.SMALLER,
                            -0.26,
                            EntityAttributeModifier.Operation.ADD_VALUE
                        )
                    )
                ),
                false,
                Optional.empty()
            )
        ).add(
            PowerNames.Common.TALLER,
            new AttributePowerType(
                List.of(
                    new AttributedEntityAttributeModifier(
                        EntityAttributes.GENERIC_SCALE,
                        new EntityAttributeModifier(
                            PowerNames.Common.TALLER,
                            0.5,
                            EntityAttributeModifier.Operation.ADD_VALUE
                        )
                    )
                ),
                false,
                Optional.empty()
            )
        );
    }


}
