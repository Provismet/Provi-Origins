package com.provismet.datagen.proviorigins.power;

import com.provismet.datagen.proviorigins.PowerGenerator;
import com.provismet.datagen.proviorigins.constants.PowerNames;
import com.provismet.datagen.proviorigins.provider.POPowerProvider;
import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.actions.entity.ParticleRingAction;
import com.provismet.proviorigins.content.registries.POSounds;
import com.provismet.proviorigins.powers.ModifyPassengerHeightPower;
import com.provismet.proviorigins.utility.BadgeUtil;
import com.provismet.proviorigins.utility.KeyUtil;
import com.provismet.proviorigins.utility.OriginList;
import com.provismet.proviorigins.registries.POLootFunctions;
import io.github.apace100.apoli.action.EntityAction;
import io.github.apace100.apoli.action.type.EntityActionType;
import io.github.apace100.apoli.action.type.bientity.MountBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.InvertBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.SequenceBiEntityActionType;
import io.github.apace100.apoli.action.type.entity.AddVelocityEntityActionType;
import io.github.apace100.apoli.action.type.entity.ApplyEffectEntityActionType;
import io.github.apace100.apoli.action.type.entity.ChangeResourceEntityActionType;
import io.github.apace100.apoli.action.type.entity.DismountEntityActionType;
import io.github.apace100.apoli.action.type.entity.EmitGameEventEntityActionType;
import io.github.apace100.apoli.action.type.entity.EquippedItemActionEntityActionType;
import io.github.apace100.apoli.action.type.entity.ExecuteCommandEntityActionType;
import io.github.apace100.apoli.action.type.entity.ModifyInventoryEntityActionType;
import io.github.apace100.apoli.action.type.entity.PassengerActionEntityActionType;
import io.github.apace100.apoli.action.type.entity.PlaySoundEntityActionType;
import io.github.apace100.apoli.action.type.entity.RevokePowerEntityActionType;
import io.github.apace100.apoli.action.type.entity.SpawnParticlesEntityActionType;
import io.github.apace100.apoli.action.type.entity.TriggerCooldownEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.IfElseEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.IfElseListEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.NothingEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.SequenceEntityActionType;
import io.github.apace100.apoli.action.type.item.ConsumeItemActionType;
import io.github.apace100.apoli.action.type.item.ModifyItemActionType;
import io.github.apace100.apoli.action.type.meta.IfElseListMetaActionType;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.condition.type.bientity.AttackerBiEntityConditionType;
import io.github.apace100.apoli.condition.type.bientity.RidingRecursiveBiEntityConditionType;
import io.github.apace100.apoli.condition.type.bientity.meta.ActorConditionBiEntityConditionType;
import io.github.apace100.apoli.condition.type.bientity.meta.AllOfBiEntityConditionType;
import io.github.apace100.apoli.condition.type.bientity.meta.InvertBiEntityConditionType;
import io.github.apace100.apoli.condition.type.bientity.meta.TargetConditionBiEntityConditionType;
import io.github.apace100.apoli.condition.type.bientity.meta.UndirectedBiEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.CommandEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.DimensionEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.EntityTypeEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.EquippedItemEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.FallFlyingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.PassengerEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.ResourceEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.RidingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.SneakingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.AllOfEntityConditionType;
import io.github.apace100.apoli.condition.type.item.EmptyItemConditionType;
import io.github.apace100.apoli.condition.type.item.IngredientItemConditionType;
import io.github.apace100.apoli.power.PowerReference;
import io.github.apace100.apoli.power.type.ActionOnBeingUsedPowerType;
import io.github.apace100.apoli.power.type.ActionOnCallbackPowerType;
import io.github.apace100.apoli.power.type.ActionOnEntityUsePowerType;
import io.github.apace100.apoli.power.type.ActionOnKeyPressPowerType;
import io.github.apace100.apoli.power.type.ActionOverTimePowerType;
import io.github.apace100.apoli.power.type.AttributePowerType;
import io.github.apace100.apoli.power.type.ElytraFlightPowerType;
import io.github.apace100.apoli.power.type.ModifyDamageDealtPowerType;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.power.type.PreventElytraFlightPowerType;
import io.github.apace100.apoli.power.type.PreventEntityUsePowerType;
import io.github.apace100.apoli.power.type.RecipePowerType;
import io.github.apace100.apoli.power.type.ResourcePowerType;
import io.github.apace100.apoli.power.type.TooltipPowerType;
import io.github.apace100.apoli.util.AttributedEntityAttributeModifier;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.apoli.util.InventoryUtil;
import io.github.apace100.apoli.util.ResourceOperation;
import io.github.apace100.apoli.util.Space;
import io.github.apace100.apoli.util.modifier.Modifier;
import io.github.apace100.apoli.util.modifier.ModifierOperation;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.inventory.SlotRanges;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RawShapedRecipe;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.joml.Vector3f;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public abstract class Drakling {
    private static final String FLIGHT_BAR_LABEL = "flight_bar";
    private static final Identifier FLIGHT_BAR = PowerNames.Drakling.SHORT_FLIGHT.withSuffixedPath("_" + FLIGHT_BAR_LABEL);

    public static void build (POPowerProvider.PowerCollector collector) {
        Drakling.buildChainCrafting(collector);
        Drakling.buildDragonEvolution(collector);
        Drakling.buildEnderdragonMandatory(collector);
        Drakling.buildRemoveDragonHead(collector);
        Drakling.buildRidable(collector);
        Drakling.buildFlight(collector);
        Drakling.buildWeakNaturalArmour(collector);
    }

    private static EntityAction changeFlightBar (int amount) {
        return new ChangeResourceEntityActionType(
            PowerReference.resource(FLIGHT_BAR),
            ResourceOperation.ADD,
            amount
        ).createAction();
    }

    private static void buildChainCrafting (POPowerProvider.PowerCollector collector) {
        Map<Character, Ingredient> ingredients = Map.of(
            'c', Ingredient.ofItems(Items.CHAIN),
            'n', Ingredient.ofItems(Items.IRON_NUGGET)
        );

        Map<Character, Ingredient> nuggetIngredient = Map.of(
            'n', Ingredient.ofItems(Items.IRON_NUGGET)
        );

        collector.add(
            PowerNames.Drakling.CHAIN_CRAFTING,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "head",
                    new RecipePowerType(
                        new ShapedRecipe(
                            "",
                            CraftingRecipeCategory.EQUIPMENT,
                            RawShapedRecipe.create(
                                ingredients,
                                List.of(
                                    "cnc",
                                    "n n"
                                )
                            ),
                            Items.CHAINMAIL_HELMET.getDefaultStack()
                        ),
                        0
                    )
                ).add(
                    "body",
                    new RecipePowerType(
                        new ShapedRecipe(
                            "",
                            CraftingRecipeCategory.EQUIPMENT,
                            RawShapedRecipe.create(
                                ingredients,
                                List.of(
                                    "c c",
                                    "nnn",
                                    "ccc"
                                )
                            ),
                            Items.CHAINMAIL_CHESTPLATE.getDefaultStack()
                        ),
                        0
                    )
                ).add(
                    "legs",
                    new RecipePowerType(
                        new ShapedRecipe(
                            "",
                            CraftingRecipeCategory.EQUIPMENT,
                            RawShapedRecipe.create(
                                ingredients,
                                List.of(
                                    "ccc",
                                    "n n",
                                    "n n"
                                )
                            ),
                            Items.CHAINMAIL_LEGGINGS.getDefaultStack()
                        ),
                        0
                    )
                ).add(
                    "boots",
                    new RecipePowerType(
                        new ShapedRecipe(
                            "",
                            CraftingRecipeCategory.EQUIPMENT,
                            RawShapedRecipe.create(
                                nuggetIngredient,
                                List.of(
                                    "n n",
                                    "n n"
                                )
                            ),
                            Items.CHAINMAIL_BOOTS.getDefaultStack()
                        ),
                        0
                    )
                )
        );
    }

    private static void buildDragonEvolution (POPowerProvider.PowerCollector collector) {
        Identifier evolutionBar = PowerNames.Drakling.DRAGON_EVOLUTION.withSuffixedPath("_evolution_bar");
        BiFunction<Integer, EntityActionType, IfElseListMetaActionType.ConditionedAction<EntityAction, EntityCondition>> createEvolutionStep =
            (step, action) -> new IfElseListMetaActionType.ConditionedAction<>(
                action.createAction(),
                new ResourceEntityConditionType(
                    PowerReference.resource(evolutionBar),
                    Comparison.EQUAL,
                    step
                ).createCondition()
            );
        BiFunction<Integer, String, IfElseListMetaActionType.ConditionedAction<EntityAction, EntityCondition>> basicTextEvolutionStep =
            (step, command) -> createEvolutionStep.apply(
                step,
                new SequenceEntityActionType(
                    List.of(
                        new PlaySoundEntityActionType(
                            POSounds.DRAKLING_EVOLVE,
                            Optional.of(SoundCategory.PLAYERS),
                            1, 1
                        ).createAction(),
                        new SpawnParticlesEntityActionType(
                            Optional.empty(),
                            ParticleTypes.TOTEM_OF_UNDYING,
                            PowerGenerator.VEC3D_HALF,
                            PowerGenerator.VEC3D_HALF,
                            true,
                            1,
                            50
                        ).createAction(),
                        new ExecuteCommandEntityActionType(command).createAction()
                    )
                )
            );

        collector.add(
            PowerNames.Drakling.DRAGON_EVOLUTION,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "tooltip",
                    new TooltipPowerType(
                        Optional.of(
                            new IngredientItemConditionType(
                                Ingredient.ofItems(Items.DRAGON_HEAD)
                            ).createCondition()
                        ),
                        List.of(Text.translatable("tooltip.proviorigins.drakling.dragon_head")),
                        false,
                        20,
                        0,
                        Optional.empty()
                    )
                ).add(
                    "evolution_bar",
                    new ResourcePowerType(
                        Optional.empty(),
                        Optional.of(
                            new SequenceEntityActionType(
                                List.of(
                                    new ExecuteCommandEntityActionType(
                                        "tellraw @p {\"text\": \"Drakling \", \"extra\": [{\"selector\": \"@s\", \"color\": \"red\"}, {\"text\": \" has evolved into a Drake.\"}]}"
                                    ).createAction(),
                                    new ExecuteCommandEntityActionType(
                                        "origin set @s origins:origin proviorigins:drake"
                                    ).createAction()
                                )
                            ).createAction()
                        ),
                        HudRender.DONT_RENDER,
                        0, 30,
                        0
                    )
                ).add(
                    "trigger",
                    new ActionOverTimePowerType(
                        Optional.of(
                            new SequenceEntityActionType(
                                List.of(
                                    new ChangeResourceEntityActionType(
                                        PowerReference.resource(evolutionBar),
                                        ResourceOperation.ADD,
                                        1
                                    ).createAction(),
                                    new IfElseListEntityActionType(
                                        List.of(
                                            new IfElseListMetaActionType.ConditionedAction<>(
                                                new NothingEntityActionType().createAction(),
                                                new EquippedItemEntityConditionType(
                                                    new IngredientItemConditionType(
                                                        Ingredient.ofItems(Items.DRAGON_HEAD)
                                                    ).createCondition(),
                                                    AttributeModifierSlot.HEAD
                                                ).createCondition(true)
                                            ),
                                            createEvolutionStep.apply(
                                                1,
                                                new SequenceEntityActionType(
                                                    List.of(
                                                        new TriggerCooldownEntityActionType(PowerReference.of(evolutionBar)).createAction(),
                                                        new ModifyInventoryEntityActionType(
                                                            InventoryUtil.InventoryType.INVENTORY,
                                                            InventoryUtil.ProcessMode.STACKS,
                                                            Optional.empty(),
                                                            new ModifyItemActionType(POLootFunctions.APPLY_CURSES).createAction(),
                                                            Optional.empty(),
                                                            Optional.empty(),
                                                            List.of(SlotRanges.fromName("armor.head")),
                                                            Optional.empty()
                                                        ).createAction(),
                                                        new ApplyEffectEntityActionType(
                                                            List.of(
                                                                new StatusEffectInstance(
                                                                    StatusEffects.RESISTANCE,
                                                                    800,
                                                                    6,
                                                                    true,
                                                                    false,
                                                                    false
                                                                ),
                                                                new StatusEffectInstance(
                                                                    StatusEffects.SLOWNESS,
                                                                    600,
                                                                    6,
                                                                    true,
                                                                    false,
                                                                    false
                                                                ),
                                                                new StatusEffectInstance(
                                                                    StatusEffects.BLINDNESS,
                                                                    600,
                                                                    6,
                                                                    true,
                                                                    false,
                                                                    false
                                                                ),
                                                                new StatusEffectInstance(
                                                                    StatusEffects.MINING_FATIGUE,
                                                                    600,
                                                                    6,
                                                                    true,
                                                                    false,
                                                                    false
                                                                ),
                                                                new StatusEffectInstance(
                                                                    StatusEffects.WEAKNESS,
                                                                    600,
                                                                    6,
                                                                    true,
                                                                    false,
                                                                    false
                                                                ),
                                                                new StatusEffectInstance(
                                                                    StatusEffects.REGENERATION,
                                                                    800,
                                                                    6,
                                                                    true,
                                                                    false,
                                                                    false
                                                                ),
                                                                new StatusEffectInstance(
                                                                    StatusEffects.LEVITATION,
                                                                    600,
                                                                    6,
                                                                    true,
                                                                    false,
                                                                    false
                                                                )
                                                            )
                                                        ).createAction()
                                                    )
                                                )
                                            ),
                                            createEvolutionStep.apply(
                                                2,
                                                new RevokePowerEntityActionType(
                                                    PowerReference.of(PowerNames.Drakling.SHORT_FLIGHT),
                                                    OriginList.DRAKLING.identifier("drakling")
                                                )
                                            ),
                                            basicTextEvolutionStep.apply(5, "title @s title {\"text\":\"You shall be reborn...\", \"bold\":true, \"color\":\"red\"}"),
                                            basicTextEvolutionStep.apply(10, "title @s title {\"text\":\"Our bones...\", \"bold\":true, \"color\":\"red\"}"),
                                            basicTextEvolutionStep.apply(15, "title @s title {\"text\":\"Our flesh...\", \"bold\":true, \"color\":\"red\"}"),
                                            basicTextEvolutionStep.apply(20, "title @s title {\"text\":\"They are melding onto you.\", \"bold\":true, \"color\":\"red\"}"),
                                            basicTextEvolutionStep.apply(25, "title @s title {\"text\":\"Now take our power...\", \"bold\":true, \"color\":\"red\"}"),
                                            createEvolutionStep.apply(
                                                28,
                                                new SequenceEntityActionType(
                                                    List.of(
                                                        new PlaySoundEntityActionType(
                                                            POSounds.DRAKLING_BIRTH,
                                                            Optional.of(SoundCategory.PLAYERS),
                                                            1, 1
                                                        ).createAction(),
                                                        new ExecuteCommandEntityActionType(
                                                            "title @s title {\"text\":\"AND FLY.\", \"bold\":true, \"color\":\"red\"}"
                                                        ).createAction()
                                                    )
                                                )
                                            )
                                        )
                                    ).createAction()
                                )
                            ).createAction()
                        ),
                        Optional.empty(),
                        Optional.empty(),
                        20,
                        Optional.of(
                            new EquippedItemEntityConditionType(
                                new IngredientItemConditionType(
                                    Ingredient.ofItems(Items.DRAGON_HEAD)
                                ).createCondition(),
                                AttributeModifierSlot.HEAD
                            ).createCondition()
                        )
                    )
                )
        );
    }

    private static void buildEnderdragonMandatory (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Drakling.ENDERDRAGON_MANDATORY,
            new ActionOverTimePowerType(
                Optional.of(Drakling.changeFlightBar(-11)),
                Optional.empty(),
                Optional.empty(),
                20,
                Optional.of(
                    new AllOfEntityConditionType(
                        List.of(
                            new DimensionEntityConditionType(World.END).createCondition(),
                            new CommandEntityConditionType(
                                "execute if entity @e[type=minecraft:ender_dragon,distance=128..]",
                                Comparison.GREATER_THAN_OR_EQUAL,
                                1
                            ).createCondition()
                        )
                    ).createCondition()
                )
            )
        );
    }

    private static void buildRemoveDragonHead (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Drakling.REMOVE_DRAGON_HEAD,
            new ActionOnCallbackPowerType(
                Optional.empty(),
                Optional.empty(),
                Optional.of(
                    new IfElseEntityActionType(
                        new EquippedItemEntityConditionType(
                            new IngredientItemConditionType(
                                Ingredient.ofItems(Items.DRAGON_HEAD)
                            ).createCondition(),
                            AttributeModifierSlot.HEAD
                        ).createCondition(),
                        new EquippedItemActionEntityActionType(
                            AttributeModifierSlot.HEAD,
                            new ConsumeItemActionType(1).createAction()
                        ).createAction(),
                        Optional.empty()
                    ).createAction()
                ),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
            ),
            true
        );
    }

    private static void buildRidable (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Drakling.RIDABLE,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "force_mount",
                    new ActionOnEntityUsePowerType(
                        Optional.of(
                            new InvertBiEntityActionType(
                                new MountBiEntityActionType().createAction()
                            ).createAction()
                        ),
                        Optional.of(
                            new AllOfBiEntityConditionType(
                                List.of(
                                    new TargetConditionBiEntityConditionType(
                                        new AllOfEntityConditionType(
                                            List.of(
                                                new EntityTypeEntityConditionType(EntityType.PLAYER).createCondition(),
                                                new PassengerEntityConditionType(
                                                    Optional.empty(),
                                                    Comparison.EQUAL,
                                                    0
                                                ).createCondition()
                                            )
                                        ).createCondition()
                                    ).createCondition(),
                                    new UndirectedBiEntityConditionType(
                                        new AttackerBiEntityConditionType().createCondition()
                                    ).createCondition(true)
                                )
                            ).createCondition()
                        ),
                        Optional.empty(),
                        Optional.of(
                            new EmptyItemConditionType().createCondition()
                        ),
                        Optional.empty(),
                        Optional.empty(),
                        EnumSet.allOf(Hand.class),
                        ActionResult.SUCCESS,
                        0,
                        Optional.of(
                            new AllOfEntityConditionType(
                                List.of(
                                    new SneakingEntityConditionType().createCondition(),
                                    new RidingEntityConditionType(Optional.empty()).createCondition(true)
                                )
                            ).createCondition()
                        )
                    )
                ).add(
                    "mount",
                    new ActionOnBeingUsedPowerType(
                        Optional.of(new MountBiEntityActionType().createAction()),
                        Optional.of(
                            new AllOfBiEntityConditionType(
                                List.of(
                                    new ActorConditionBiEntityConditionType(
                                        new PassengerEntityConditionType(
                                            Optional.empty(),
                                            Comparison.EQUAL, 0
                                        ).createCondition()
                                    ).createCondition(),
                                    new UndirectedBiEntityConditionType(
                                        new AttackerBiEntityConditionType().createCondition()
                                    ).createCondition(true)
                                )
                            ).createCondition()
                        ),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        EnumSet.allOf(Hand.class),
                        ActionResult.SUCCESS,
                        0,
                        Optional.of(new RidingEntityConditionType(Optional.empty()).createCondition(true))
                    )
                ).add(
                    "end_ride",
                    new ActionOnKeyPressPowerType(
                        new PassengerActionEntityActionType(
                            Optional.of(new DismountEntityActionType().createAction()),
                            Optional.empty(),
                            Optional.empty(),
                            false
                        ).createAction(),
                        HudRender.DONT_RENDER,
                        1,
                        KeyUtil.sneak(),
                        Optional.empty()
                    )
                ).add(
                    "cannot_use_passenger",
                    new PreventEntityUsePowerType(
                        Optional.empty(),
                        Optional.of(
                            new InvertBiEntityConditionType(
                                new RidingRecursiveBiEntityConditionType().createCondition()
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
                    "cannot_hurt_passenger",
                    new ModifyDamageDealtPowerType(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(
                            new InvertBiEntityConditionType(
                                new RidingRecursiveBiEntityConditionType().createCondition()
                            ).createCondition()
                        ),
                        Optional.empty(),
                        List.of(Modifier.of(ModifierOperation.SET_TOTAL, 0)),
                        Optional.empty()
                    )
                ).add(
                    "adjust_passenger",
                    new ModifyPassengerHeightPower(
                        0.9,
                        0,
                        Optional.empty()
                    )
                ),
            List.of(
                BadgeUtil.info(PowerNames.Drakling.RIDABLE, 1),
                BadgeUtil.presetActive(PowerNames.Drakling.RIDABLE, 2, "key.sneak")
            )
        );
    }

    private static void buildFlight (POPowerProvider.PowerCollector collector) {
        PowerType elytra = new ElytraFlightPowerType(
            Optional.of(ProviOriginsMain.identifier("textures/entity/drakling_wings.png")),
            true,
            Optional.empty()
        );

        collector.add(
            PowerNames.Drakling.SHORT_FLIGHT,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    FLIGHT_BAR_LABEL,
                    new ResourcePowerType(
                        Optional.empty(),
                        Optional.empty(),
                        new HudRender(
                            Optional.empty(),
                            HudRender.DEFAULT_SPRITE,
                            true,
                            false,
                            4, 4,
                            0
                        ),
                        0, 45,
                        45
                    )
                ).add(
                    "flight_drain",
                    new ActionOverTimePowerType(
                        Optional.of(
                            new IfElseEntityActionType(
                                new FallFlyingEntityConditionType().createCondition(),
                                Drakling.changeFlightBar(-1),
                                Optional.of(Drakling.changeFlightBar(1))
                            ).createAction()
                        ),
                        Optional.empty(),
                        Optional.empty(),
                        20,
                        Optional.empty()
                    )
                ).add(
                    "elytra",
                    elytra
                ).add(
                    "locked_elyta",
                    new PreventElytraFlightPowerType(
                        Optional.empty(),
                        Optional.of(
                            new ResourceEntityConditionType(
                                PowerReference.resource(FLIGHT_BAR),
                                Comparison.EQUAL,
                                0
                            ).createCondition()
                        )
                    )
                )
        );

        collector.add(
            PowerNames.Drakling.TRUE_FLIGHT,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "elytra",
                    elytra
                ).add(
                    "boost",
                    new ActionOnKeyPressPowerType(
                        new SequenceEntityActionType(
                            List.of(
                                new PlaySoundEntityActionType(
                                    POSounds.DRAKLING_FLAP,
                                    Optional.of(SoundCategory.PLAYERS),
                                    1, 1
                                ).createAction(),
                                new EmitGameEventEntityActionType(GameEvent.FLAP).createAction(),
                                new ParticleRingAction(
                                    5,
                                    20,
                                    1,
                                    Space.LOCAL,
                                    0,
                                    false,
                                    ParticleTypes.EXPLOSION,
                                    new Vector3f(0f, 0f, 0f)
                                ).createAction(),
                                new AddVelocityEntityActionType(
                                    new Vector3f(0f, 0f, 2f),
                                    Space.LOCAL,
                                    false
                                ).createAction()
                            )
                        ).createAction(),
                        new HudRender(
                            Optional.empty(),
                            HudRender.DEFAULT_SPRITE,
                            true,
                            false,
                            4, 4,
                            0
                        ),
                        150,
                        KeyUtil.primary(),
                        Optional.of(new FallFlyingEntityConditionType().createCondition())
                    )
                )
        );
    }

    private static void buildWeakNaturalArmour (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Drakling.WEAK_NATURAL_ARMOUR,
            new AttributePowerType(
                List.of(
                    new AttributedEntityAttributeModifier(
                        EntityAttributes.GENERIC_ARMOR,
                        new EntityAttributeModifier(
                            PowerNames.Drakling.WEAK_NATURAL_ARMOUR,
                            -0.25,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                        )
                    )
                ),
                false,
                Optional.empty()
            )
        );
    }
}
