package com.provismet.datagen.proviorigins.power;

import com.provismet.datagen.proviorigins.PowerGenerator;
import com.provismet.datagen.proviorigins.constants.PowerNames;
import com.provismet.datagen.proviorigins.provider.POPowerProvider;
import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.actions.entity.RaycastTeleportAction;
import com.provismet.proviorigins.conditions.entity.RegenGameruleEntityConditionType;
import com.provismet.proviorigins.content.PODamageTypes;
import com.provismet.proviorigins.content.particles.effects.FlowerParticleEffect;
import com.provismet.proviorigins.content.registries.POBlocks;
import com.provismet.proviorigins.content.registries.POParticles;
import com.provismet.proviorigins.content.registries.POSounds;
import com.provismet.proviorigins.content.registries.POStatusEffects;
import com.provismet.proviorigins.powers.ActiveItemPower;
import com.provismet.proviorigins.powers.EmissivePower;
import com.provismet.proviorigins.powers.PreventBreathingPower;
import com.provismet.proviorigins.utility.KeyUtil;
import com.provismet.proviorigins.utility.OriginList;
import com.provismet.proviorigins.utility.PowerUtil;
import com.provismet.proviorigins.utility.constants.SpriteLocations;
import com.provismet.proviorigins.utility.tags.POBlockTags;
import com.provismet.proviorigins.utility.tags.POItemTags;
import io.github.apace100.apoli.action.EntityAction;
import io.github.apace100.apoli.action.type.bientity.meta.ActorActionBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.RandomChanceBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.SequenceBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.TargetActionBiEntityActionType;
import io.github.apace100.apoli.action.type.block.ModifyBlockStateBlockActionType;
import io.github.apace100.apoli.action.type.entity.ApplyEffectEntityActionType;
import io.github.apace100.apoli.action.type.entity.AreaOfEffectEntityActionType;
import io.github.apace100.apoli.action.type.entity.BlockActionAtEntityActionType;
import io.github.apace100.apoli.action.type.entity.ChangeResourceEntityActionType;
import io.github.apace100.apoli.action.type.entity.DamageEntityActionType;
import io.github.apace100.apoli.action.type.entity.EmitGameEventEntityActionType;
import io.github.apace100.apoli.action.type.entity.EquippedItemActionEntityActionType;
import io.github.apace100.apoli.action.type.entity.ExhaustEntityActionType;
import io.github.apace100.apoli.action.type.entity.HealEntityActionType;
import io.github.apace100.apoli.action.type.entity.PlaySoundEntityActionType;
import io.github.apace100.apoli.action.type.entity.RaycastEntityActionType;
import io.github.apace100.apoli.action.type.entity.SpawnEntityEntityActionType;
import io.github.apace100.apoli.action.type.entity.SpawnParticlesEntityActionType;
import io.github.apace100.apoli.action.type.entity.SwingHandEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.IfElseEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.IfElseListEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.SequenceEntityActionType;
import io.github.apace100.apoli.action.type.item.ConsumeItemActionType;
import io.github.apace100.apoli.action.type.meta.IfElseListMetaActionType;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.condition.type.bientity.meta.TargetConditionBiEntityConditionType;
import io.github.apace100.apoli.condition.type.block.BlockBlockConditionType;
import io.github.apace100.apoli.condition.type.block.InTagBlockConditionType;
import io.github.apace100.apoli.condition.type.damage.AttackerDamageConditionType;
import io.github.apace100.apoli.condition.type.damage.TypeDamageConditionType;
import io.github.apace100.apoli.condition.type.damage.meta.AllOfDamageConditionType;
import io.github.apace100.apoli.condition.type.entity.BlockInRadiusEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.DimensionEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.EntityTypeEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.EquippedItemEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.ExistsEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.FoodLevelEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.InBlockEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.PowerActiveEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.RelativeHealthEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.ResourceEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.AllOfEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.AnyOfEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.ConstantEntityConditionType;
import io.github.apace100.apoli.condition.type.item.IngredientItemConditionType;
import io.github.apace100.apoli.power.PowerReference;
import io.github.apace100.apoli.power.type.ActionOnBeingUsedPowerType;
import io.github.apace100.apoli.power.type.ActionOnCallbackPowerType;
import io.github.apace100.apoli.power.type.ActionOnHitPowerType;
import io.github.apace100.apoli.power.type.ActionOnKeyPressPowerType;
import io.github.apace100.apoli.power.type.ActionOverTimePowerType;
import io.github.apace100.apoli.power.type.ActionWhenDamageTakenPowerType;
import io.github.apace100.apoli.power.type.ActionWhenHitPowerType;
import io.github.apace100.apoli.power.type.ConditionedAttributePowerType;
import io.github.apace100.apoli.power.type.DisableRegenPowerType;
import io.github.apace100.apoli.power.type.NightVisionPowerType;
import io.github.apace100.apoli.power.type.ParticlePowerType;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.power.type.ResourcePowerType;
import io.github.apace100.apoli.power.type.SelfGlowPowerType;
import io.github.apace100.apoli.power.type.StackingStatusEffectPowerType;
import io.github.apace100.apoli.power.type.TooltipPowerType;
import io.github.apace100.apoli.util.AttributedEntityAttributeModifier;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.apoli.util.ResourceOperation;
import io.github.apace100.apoli.util.Shape;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.joml.Vector3f;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public abstract class VoidLily {
    public static void build (POPowerProvider.PowerCollector collector) {
        VoidLily.buildComposterDeath(collector);
        VoidLily.buildDeathParticles(collector);
        VoidLily.buildFixRegen(collector);
        VoidLily.buildFloralMimicryMechanics(collector);
        VoidLily.buildFloralMimicryActives(collector);
        VoidLily.buildNetherAir(collector);
        VoidLily.buildPlapPlapGetBonemealed(collector);
        VoidLily.buildPollination(collector);
        VoidLily.buildVoidGrowth(collector);
    }

    private static void buildComposterDeath (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.VoidLily.COMPOSTER_DEATH,
            new ActionOverTimePowerType(
                Optional.empty(),
                Optional.of(
                    new SequenceEntityActionType(
                        List.of(
                            new BlockActionAtEntityActionType(
                                new ModifyBlockStateBlockActionType(
                                    "level",
                                    ResourceOperation.SET,
                                    Optional.of(7),
                                    Optional.empty(),
                                    Optional.empty(),
                                    false
                                ).createAction()
                            ).createAction(),
                            new DamageEntityActionType(
                                PODamageTypes.COMPOST.getKey(),
                                Optional.of(1000f),
                                List.of()
                            ).createAction()
                        )
                    ).createAction()
                ),
                Optional.empty(),
                20,
                Optional.of(
                    new InBlockEntityConditionType(
                        new BlockBlockConditionType(Blocks.COMPOSTER).createCondition()
                    ).createCondition()
                )
            ),
            true
        );
    }

    private static void buildDeathParticles (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.VoidLily.DEATH_PARTICLES,
            new ActionWhenDamageTakenPowerType(
                new SpawnParticlesEntityActionType(
                    Optional.empty(),
                    new BlockStateParticleEffect(
                        ParticleTypes.BLOCK,
                        POBlocks.LILY_OF_THE_VOID.getDefaultState()
                    ),
                    Vec3d.ZERO,
                    Vec3d.ZERO,
                    false,
                    5f,
                    32
                ).createAction(),
                Optional.empty(),
                HudRender.DONT_RENDER,
                1,
                Optional.of(
                    new RelativeHealthEntityConditionType(
                        Comparison.LESS_THAN_OR_EQUAL,
                        0f
                    ).createCondition()
                )
            ),
            true
        );
    }

    private static void buildFixRegen (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.VoidLily.FIX_REGEN,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "no_regen",
                    new DisableRegenPowerType(Optional.empty())
                )
                .add(
                    "heal_when_full",
                    new ActionOverTimePowerType(
                        Optional.of(
                            new SequenceEntityActionType(
                                List.of(
                                    new HealEntityActionType(1f).createAction(),
                                    new ExhaustEntityActionType(6f).createAction()
                                )
                            ).createAction()
                        ),
                        Optional.empty(),
                        Optional.empty(),
                        80,
                        Optional.of(
                            new AllOfEntityConditionType(
                                List.of(
                                    new FoodLevelEntityConditionType(
                                        Comparison.GREATER_THAN_OR_EQUAL,
                                        17
                                    ).createCondition(),
                                    new RelativeHealthEntityConditionType(
                                        Comparison.LESS_THAN,
                                        1f
                                    ).createCondition(),
                                    new RegenGameruleEntityConditionType().createCondition()
                                )
                            ).createCondition()
                        )
                    )
                ),
            true
        );
    }

    private static void buildFloralMimicryMechanics (POPowerProvider.PowerCollector collector) {

        collector.add(
            PowerNames.VoidLily.FLOWER_COPY__DRAIN,
            new ActionOverTimePowerType(
                Optional.of(
                    new IfElseListEntityActionType(
                        IntStream.rangeClosed(1, 14)
                            .mapToObj(VoidLily::createMimicryDrain)
                            .toList()
                    ).createAction()
                ),
                Optional.empty(),
                Optional.empty(),
                20,
                Optional.empty()
            ),
            true
        ).add(
            PowerNames.VoidLily.FLOWER_COPY__MANUAL_RESET,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "passive",
                    new ActionOnHitPowerType(
                        Optional.of(
                            new TargetActionBiEntityActionType(
                                new ApplyEffectEntityActionType(
                                    List.of(new StatusEffectInstance(StatusEffects.POISON, 120))
                                ).createAction()
                            ).createAction()
                        ),
                        Optional.empty(),
                        Optional.empty(),
                        HudRender.DONT_RENDER,
                        1,
                        Optional.of(VoidLily.createMimicryCondition(0))
                    )
                )
                .add(
                    "consume",
                    new ActionOnKeyPressPowerType(
                        new SequenceEntityActionType(
                            List.of(
                                new ChangeResourceEntityActionType(
                                    PowerReference.resource(PowerNames.VoidLily.FLOWER_COPY__RESOURCE),
                                    ResourceOperation.SET,
                                    0
                                ).createAction(),
                                new SwingHandEntityActionType(Hand.MAIN_HAND).createAction()
                            )
                        ).createAction(),
                        HudRender.DONT_RENDER,
                        10,
                        KeyUtil.secondary(),
                        Optional.empty()
                    )
                )
                .add(
                    "hud",
                    new ResourcePowerType(
                        Optional.empty(),
                        Optional.empty(),
                        new HudRender(
                            Optional.of(
                                new ResourceEntityConditionType(
                                    PowerReference.resource(PowerNames.VoidLily.FLOWER_COPY__RESOURCE),
                                    Comparison.EQUAL,
                                    0
                                ).createCondition()
                            ),
                            SpriteLocations.VOIDLILY_EXTRA_RESOURCES,
                            true,
                            false,
                            0,
                            0,
                            0
                        ),
                        0,
                        1,
                        0
                    )
                )
        ).add(
            PowerNames.VoidLily.FLOWER_COPY__RESET_ON_DEATH,
            new ActionOnCallbackPowerType(
                Optional.of(
                    new ChangeResourceEntityActionType(
                        PowerReference.resource(PowerNames.VoidLily.FLOWER_COPY__RESOURCE),
                        ResourceOperation.SET,
                        0
                    ).createAction()
                ),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
            ),
            true
        ).add(
            PowerNames.VoidLily.FLOWER_COPY__RESOURCE,
            new ResourcePowerType(
                Optional.of(
                    new SequenceEntityActionType(
                        List.of(
                            new PlaySoundEntityActionType(
                                POSounds.LILY_MIMIC_END,
                                Optional.of(SoundCategory.PLAYERS),
                                1,
                                2
                            ).createAction(),
                            new EmitGameEventEntityActionType(GameEvent.EQUIP).createAction()
                        )
                    ).createAction()
                ),
                Optional.empty(),
                HudRender.DONT_RENDER,
                0,
                14,
                0
            ),
            true
        );
    }

    private static void buildFloralMimicryActives (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.VoidLily.FLOWER_COPY_ALLIUM,
            VoidLily.floralMimicryBuff(
                1,
                POItemTags.GRANTS_FIRE_RESISTANCE,
                new Vector3f(0.808f, 0.588f, 0.918f),
                600,
                StatusEffects.FIRE_RESISTANCE,
                0,
                Text.translatable("tooltip.proviorigins.voidlily.grants_fire_resistance")
            ),
            true
        ).add(
            PowerNames.VoidLily.FLOWER_COPY_AZUREBLUET,
            VoidLily.floralMimicryDebuff(
                2,
                POItemTags.GRANTS_BLINDNESS,
                new Vector3f(0.898f, 0.898f, 0.898f),
                300,
                StatusEffects.BLINDNESS,
                0,
                20,
                Text.translatable("tooltip.proviorigins.voidlily.grants_blindness")
            ),
            true
        ).add(
            PowerNames.VoidLily.FLOWER_COPY_BLUEORCHID_DANDELION,
            VoidLily.floralMimicryBuff(
                3,
                POItemTags.GRANTS_SATURATION,
                new Vector3f(0.878f, 0.647f, 0.427f),
                30,
                StatusEffects.SATURATION,
                0,
                Text.translatable("tooltip.proviorigins.voidlily.grants_saturation")
            ),
            true
        ).add(
            PowerNames.VoidLily.FLOWER_COPY_CORNFLOWER,
            VoidLily.floralMimicryBuff(
                4,
                POItemTags.GRANTS_JUMP_BOOST,
                new Vector3f(0.314f, 0.365f, 0.827f),
                600,
                StatusEffects.JUMP_BOOST,
                1,
                Text.translatable("tooltip.proviorigins.voidlily.grants_jump_boost")
            ),
            true
        ).add(
            PowerNames.VoidLily.FLOWER_COPY_OXEYE,
            VoidLily.floralMimicry(
                5,
                POItemTags.GRANTS_REGENERATION,
                new Vector3f(0.898f, 0.898f, 0.898f),
                90,
                new ActionOverTimePowerType(
                    Optional.of(new HealEntityActionType(1f).createAction()),
                    Optional.empty(),
                    Optional.empty(),
                    50,
                    Optional.of(VoidLily.createMimicryCondition(5))
                ),
                Text.translatable("tooltip.proviorigins.voidlily.grants_regeneration")
            ),
            true
        ).add(
            PowerNames.VoidLily.FLOWER_COPY_POPPY,
            VoidLily.floralMimicry(
                6,
                POItemTags.GRANTS_NIGHT_VISION,
                new Vector3f(0.847f, 0f, 0f),
                600,
                new NightVisionPowerType(1f, Optional.of(VoidLily.createMimicryCondition(6))),
                Text.translatable("tooltip.proviorigins.voidlily.grants_night_vision")
            ),
            true
        ).add(
            PowerNames.VoidLily.FLOWER_COPY_TULIPS,
            VoidLily.floralMimicryDebuff(
                7,
                POItemTags.GRANTS_WEAKNESS,
                new Vector3f(1f, 0.431f, 1f),
                300,
                StatusEffects.WEAKNESS,
                0,
                0,
                Text.translatable("tooltip.proviorigins.voidlily.grants_weakness")
            ),
            true
        ).add(
            PowerNames.VoidLily.FLOWER_COPY_WITHER,
            VoidLily.floralMimicryDebuff(
                8,
                POItemTags.GRANTS_WITHER,
                new Vector3f(0.2f, 0.2f, 0.2f),
                600,
                StatusEffects.WITHER,
                0,
                120,
                Text.translatable("tooltip.proviorigins.voidlily.grants_wither")
            ),
            true
        ).add(
            PowerNames.VoidLily.FLOWER_COPY_WATERBREATHING,
            VoidLily.floralMimicryBuff(
                9,
                POItemTags.GRANTS_WATER_BREATHING,
                new Vector3f(0.965f, 0.965f, 0.965f),
                300,
                StatusEffects.WATER_BREATHING,
                0,
                Text.translatable("tooltip.proviorigins.voidlily.grants_water_breathing")
            ),
            true
        ).add(
            PowerNames.VoidLily.FLOWER_COPY_CACTUS_ROSE_BERRY,
            VoidLily.floralMimicry(
                10,
                POItemTags.GRANTS_THORNS,
                List.of(new Vector3f(0.498f, 0.792f, 0.498f), new Vector3f(0.875f, 0.118f, 0.141f)),
                300,
                new ActionWhenHitPowerType(
                    Optional.of(
                        new ActorActionBiEntityActionType(
                            new DamageEntityActionType(
                                DamageTypes.THORNS,
                                Optional.of(2f),
                                List.of()
                            ).createAction()
                        ).createAction()
                    ),
                    Optional.empty(),
                    Optional.of(
                        new AllOfDamageConditionType(
                            List.of(
                                new AttackerDamageConditionType(
                                    Optional.of(new ExistsEntityConditionType().createCondition())
                                ).createCondition(),
                                new TypeDamageConditionType(DamageTypes.THORNS).createCondition(true)
                            )
                        ).createCondition()
                    ),
                    HudRender.DONT_RENDER,
                    1,
                    Optional.of(
                        new ResourceEntityConditionType(
                            PowerReference.resource(PowerNames.VoidLily.FLOWER_COPY__RESOURCE),
                            Comparison.EQUAL,
                            10
                        ).createCondition()
                    )
                ),
                Text.translatable("tooltip.proviorigins.voidlily.grants_thorns")
            ),
            true
        ).add(
            PowerNames.VoidLily.FLOWER_COPY_GLOW,
            VoidLily.floralMimicry(
                11,
                POItemTags.GRANTS_GLOW,
                new Vector3f(1f, 0.863f, 0.569f),
                600,
                new SelfGlowPowerType(
                    Optional.empty(),
                    Optional.empty(),
                    true,
                    1f, 1f, 1f,
                    Optional.of(VoidLily.createMimicryCondition(11))
                ),
                Text.translatable("tooltip.proviorigins.voidlily.grants_glow")
            ).add(
                "passive2",
                new EmissivePower(
                    15, 15,
                    Optional.of(VoidLily.createMimicryCondition(11))
                )
            ),
            true
        ).add(
            PowerNames.VoidLily.FLOWER_COPY_SUGARCANE,
            VoidLily.floralMimicryBuff(
                12,
                POItemTags.GRANTS_SPEED,
                new Vector3f(0.2f, 0.655f, 0.243f),
                180,
                StatusEffects.SPEED,
                0,
                Text.translatable("tooltip.proviorigins.voidlily.grants_speed")
            ),
            true
        ).add(
            PowerNames.VoidLily.FLOWER_COPY_LILY,
            VoidLily.floralMimicryDebuff(
                13,
                POItemTags.GRANTS_CORRUPTION,
                List.of(new Vector3f(0.965f, 0.965f, 0.965f), new Vector3f(0.494f, 0.051f, 0.706f)),
                300,
                POStatusEffects.VOID_CORRUPTION,
                0,
                120,
                Text.translatable("tooltip.proviorigins.voidlily.grants_corruption")
            ),
            PowerUtil.getNameTranslationKey(OriginList.LILY_OF_THE_VOID.identifier("floral_mimicry")),
            PowerUtil.getDescriptionTranslationKey(OriginList.LILY_OF_THE_VOID.identifier("floral_mimicry"))
        ).add(
            PowerNames.VoidLily.FLOWER_COPY_WOOD,
            VoidLily.floralMimicry(
                14,
                POItemTags.GRANTS_ARMOUR,
                new Vector3f(0.659f, 0.251f, 0.02f),
                90,
                new ConditionedAttributePowerType(
                    List.of(
                        new AttributedEntityAttributeModifier(
                            EntityAttributes.GENERIC_ARMOR,
                            new EntityAttributeModifier(
                                PowerNames.VoidLily.FLOWER_COPY_WOOD,
                                4.0,
                                EntityAttributeModifier.Operation.ADD_VALUE
                            )
                        )
                    ),
                    false,
                    20,
                    Optional.of(VoidLily.createMimicryCondition(14))
                ),
                Text.translatable("tooltip.proviorigins.voidlily.grants_armour")
            ),
            true
        ).add(
            PowerNames.VoidLily.FLOWER_COPY_CHORUS,
            VoidLily.floralMimicryActive(
                POItemTags.GRANTS_TELEPORT,
                new RaycastTeleportAction(
                    80,
                    "on_top",
                    false,
                    false,
                    RaycastContext.ShapeType.COLLIDER,
                    RaycastContext.FluidHandling.NONE,
                    Optional.of(
                        new SequenceEntityActionType(
                            List.of(
                                new SpawnParticlesEntityActionType(
                                    Optional.empty(),
                                    ParticleTypes.PORTAL,
                                    new Vec3d(0, 0.5, 0),
                                    Vec3d.ZERO,
                                    false,
                                    0,
                                    32
                                ).createAction(),
                                new PlaySoundEntityActionType(
                                    POSounds.LILY_TELEPORT,
                                    Optional.of(SoundCategory.PLAYERS),
                                    1, 1
                                ).createAction(),
                                new IfElseEntityActionType(
                                    new EquippedItemEntityConditionType(
                                        PowerGenerator.ingredientConditionFromTag(POItemTags.GRANTS_TELEPORT),
                                        AttributeModifierSlot.MAINHAND
                                    ).createCondition(),
                                    new EquippedItemActionEntityActionType(
                                        AttributeModifierSlot.MAINHAND,
                                        new ConsumeItemActionType(1).createAction()
                                    ).createAction(),
                                    Optional.of(
                                        new EquippedItemActionEntityActionType(
                                            AttributeModifierSlot.OFFHAND,
                                            new ConsumeItemActionType(1).createAction()
                                        ).createAction()
                                    )
                                ).createAction()
                            )
                        ).createAction()
                    ),
                    Optional.empty()
                ).createAction(),
                Text.translatable("tooltip.proviorigins.voidlily.grants_teleport"),
                false
            ),
            true
        );
    }

    private static void buildNetherAir (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.VoidLily.NETHER_AIR,
            new PreventBreathingPower(
                PODamageTypes.BAD_DIMENSION.getKey(),
                true,
                Optional.of(new DimensionEntityConditionType(World.NETHER).createCondition())
            )
        );
    }

    private static void buildPlapPlapGetBonemealed (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.VoidLily.PLAP_PLAP_GET_BONEMEALED,
            new ActionOnBeingUsedPowerType(
                Optional.of(
                    new SequenceBiEntityActionType(
                        List.of(
                            new TargetActionBiEntityActionType(
                                new SpawnParticlesEntityActionType(
                                    Optional.empty(),
                                    ParticleTypes.HAPPY_VILLAGER,
                                    Vec3d.ZERO,
                                    Vec3d.ZERO,
                                    false,
                                    0,
                                    12
                                ).createAction()
                            ).createAction(),
                            new TargetActionBiEntityActionType(
                                new PlaySoundEntityActionType(
                                    SoundEvents.ITEM_BONE_MEAL_USE,
                                    Optional.of(SoundCategory.PLAYERS),
                                    1, 1
                                ).createAction()
                            ).createAction(),
                            new RandomChanceBiEntityActionType(
                                new ActorActionBiEntityActionType(
                                    new SpawnEntityEntityActionType(
                                        EntityType.ITEM,
                                        Optional.empty(),
                                        Optional.empty(),
                                        PowerGenerator.createItemEntityNbt(ProviOriginsMain.identifier("lily_of_the_void"))
                                    ).createAction()
                                ).createAction(),
                                Optional.empty(),
                                0.404f
                            ).createAction()
                        )
                    ).createAction()
                ),
                Optional.empty(),
                Optional.of(
                    new ConsumeItemActionType(1).createAction()
                ),
                Optional.of(
                    new IngredientItemConditionType(Ingredient.ofItems(Items.BONE_MEAL)).createCondition()
                ),
                Optional.empty(),
                Optional.empty(),
                EnumSet.allOf(Hand.class),
                ActionResult.SUCCESS,
                0,
                Optional.empty()
            ),
            true
        );
    }

    private static void buildPollination (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.VoidLily.POLLINATION,
            new ActionOverTimePowerType(
                Optional.of(
                    new AreaOfEffectEntityActionType(
                        new ActorActionBiEntityActionType(
                            new ApplyEffectEntityActionType(
                                List.of(
                                    new StatusEffectInstance(StatusEffects.SATURATION, 310, 0, true, true),
                                    new StatusEffectInstance(StatusEffects.BLINDNESS, 40, 0, true, true)
                                )
                            ).createAction()
                        ).createAction(),
                        Optional.of(
                            new TargetConditionBiEntityConditionType(
                                new EntityTypeEntityConditionType(
                                    EntityType.BEE
                                ).createCondition()
                            ).createCondition()
                        ),
                        Shape.SPHERE,
                        3,
                        false
                    ).createAction()
                ),
                Optional.empty(),
                Optional.empty(),
                10,
                Optional.empty()
            )
        );
    }

    private static void buildVoidGrowth (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.VoidLily.VOID_GROWTH,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "growth",
                    new StackingStatusEffectPowerType(
                        List.of(
                            new StatusEffectInstance(StatusEffects.STRENGTH, 30, 0, true, false, true),
                            new StatusEffectInstance(StatusEffects.WATER_BREATHING, 30, 0, true, false, true),
                            new StatusEffectInstance(StatusEffects.SPEED, 30, 0, true, false, true),
                            new StatusEffectInstance(StatusEffects.SATURATION, 30, 0, true, false, true)
                        ),
                        -1,
                        1,
                        30,
                        20,
                        Optional.of(
                            new AnyOfEntityConditionType(
                                List.of(
                                    new BlockInRadiusEntityConditionType(
                                        new InTagBlockConditionType(POBlockTags.VOID_TOUCHING).createCondition(),
                                        Shape.CUBE,
                                        Comparison.GREATER_THAN_OR_EQUAL,
                                        1,
                                        4
                                    ).createCondition(),
                                    new BlockInRadiusEntityConditionType(
                                        new InTagBlockConditionType(POBlockTags.WEAKLY_VOID_TOUCHING).createCondition(),
                                        Shape.CUBE,
                                        Comparison.GREATER_THAN_OR_EQUAL,
                                        4,
                                        4
                                    ).createCondition(),
                                    new DimensionEntityConditionType(World.END).createCondition()
                                )
                            ).createCondition()
                        )
                    )
                )
                .add(
                    "particles",
                    new ParticlePowerType(
                        Optional.empty(),
                        POParticles.LILY_PETAL,
                        new Vec3d(0.45, 0.5, 0.45),
                        Vec3d.ZERO,
                        8,
                        1,
                        0,
                        false,
                        false,
                        false,
                        Optional.of(
                            new PowerActiveEntityConditionType(
                                PowerReference.of(PowerNames.VoidLily.VOID_GROWTH.withSuffixedPath("_growth"))
                            ).createCondition()
                        )
                    )
                )
        );
    }

    private static POPowerProvider.MultiplePowerJsonBuilder floralMimicryDebuff (int index, TagKey<Item> itemTag, Vector3f particleColour, int duration, RegistryEntry<StatusEffect> statusEffect, int effectAmplifer, int effectDuration, Text tooltip) {
        return VoidLily.floralMimicryDebuff(index, itemTag, List.of(particleColour), duration, statusEffect, effectAmplifer, effectDuration, tooltip);
    }

    private static POPowerProvider.MultiplePowerJsonBuilder floralMimicryDebuff (int index, TagKey<Item> itemTag, List<Vector3f> particleColours, int duration, RegistryEntry<StatusEffect> statusEffect, int effectAmplifer, int effectDuration, Text tooltip) {
        PowerType passive = new ActionOnHitPowerType(
            Optional.of(
                new TargetActionBiEntityActionType(
                    new ApplyEffectEntityActionType(
                        List.of(
                            new StatusEffectInstance(
                                statusEffect,
                                effectDuration,
                                effectAmplifer
                            )
                        )
                    ).createAction()
                ).createAction()
            ),
            Optional.empty(),
            Optional.empty(),
            HudRender.DONT_RENDER,
            1,
            Optional.of(createMimicryCondition(index))
        );

        return VoidLily.floralMimicry(index, itemTag, particleColours, duration, passive, tooltip);
    }

    private static POPowerProvider.MultiplePowerJsonBuilder floralMimicryBuff (int index, TagKey<Item> itemTag, Vector3f particleColour, int duration, RegistryEntry<StatusEffect> statusEffect, int effectAmplifier, Text tooltip) {
        return VoidLily.floralMimicryBuff(index, itemTag, List.of(particleColour), duration, statusEffect, effectAmplifier, tooltip);
    }

    private static POPowerProvider.MultiplePowerJsonBuilder floralMimicryBuff (int index, TagKey<Item> itemTag, List<Vector3f> particleColours, int duration, RegistryEntry<StatusEffect> statusEffect, int effectAmplifier, Text tooltip) {
        PowerType passive = new StackingStatusEffectPowerType(
            List.of(new StatusEffectInstance(statusEffect, 100, effectAmplifier, true, false, false)),
            -1,
            1,
            100,
            10,
            Optional.of(VoidLily.createMimicryCondition(index))
        );

        return VoidLily.floralMimicry(index, itemTag, particleColours, duration, passive, tooltip);
    }

    private static POPowerProvider.MultiplePowerJsonBuilder floralMimicry (int index, TagKey<Item> itemTag, Vector3f particleColour, int duration, PowerType passive, Text tooltip) {
        return VoidLily.floralMimicry(index, itemTag, List.of(particleColour), duration, passive, tooltip);
    }

    private static POPowerProvider.MultiplePowerJsonBuilder floralMimicry (int index, TagKey<Item> itemTag, List<Vector3f> particleColours, int duration, PowerType passive, Text tooltip) {
        POPowerProvider.MultiplePowerJsonBuilder mimicry = new POPowerProvider.MultiplePowerJsonBuilder();
        mimicry.add("passive", passive);

        for (int i = 0; i < particleColours.size(); ++i) {
            Vector3f colour = particleColours.get(i);
            mimicry.add(
                i == 0 ? "particles" : "particles" + (i + 1),
                new ParticlePowerType(
                    Optional.empty(),
                    new FlowerParticleEffect(colour, 1f),
                    new Vec3d(0.5, 0.5, 0.5),
                    Vec3d.ZERO,
                    4 * particleColours.size(),
                    1,
                    0,
                    false,
                    false,
                    false,
                    Optional.of(VoidLily.createMimicryCondition(index))
                )
            );
        }

        mimicry.add(
            "consume",
            new ActiveItemPower(
                1,
                HudRender.DONT_RENDER,
                KeyUtil.primary(),
                new SequenceEntityActionType(
                    List.of(
                        new ChangeResourceEntityActionType(
                            PowerReference.resource(PowerNames.VoidLily.FLOWER_COPY__RESOURCE),
                            ResourceOperation.SET,
                            index
                        ).createAction(),
                        new ChangeResourceEntityActionType(
                            PowerReference.resource(com.provismet.proviorigins.originTypes.voidlily.VoidLily.getHudForIndex(index)),
                            ResourceOperation.SET,
                            duration
                        ).createAction(),
                        new PlaySoundEntityActionType(
                            POSounds.LILY_MIMIC,
                            Optional.of(SoundCategory.PLAYERS),
                            1,
                            2
                        ).createAction(),
                        new EmitGameEventEntityActionType(GameEvent.EQUIP).createAction()
                    )
                ).createAction(),
                new IngredientItemConditionType(Ingredient.fromTag(itemTag)).createCondition(),
                Optional.empty(),
                1,
                true,
                Optional.empty()
            )
        );

        mimicry.add(
            "tooltip",
            new TooltipPowerType(
                Optional.of(new IngredientItemConditionType(Ingredient.fromTag(itemTag)).createCondition()),
                List.of(tooltip),
                false,
                20,
                0,
                Optional.of(VoidLily.createMimicryCondition(index))
            )
        );

        mimicry.add(
            "hud",
            new ResourcePowerType(
                Optional.of(
                    new SequenceEntityActionType(
                        List.of(
                            new ChangeResourceEntityActionType(
                                PowerReference.resource(PowerNames.VoidLily.FLOWER_COPY__RESOURCE),
                                ResourceOperation.SET,
                                0
                            ).createAction(),
                            new ChangeResourceEntityActionType(
                                PowerReference.resource(com.provismet.proviorigins.originTypes.voidlily.VoidLily.getHudForIndex(index)),
                                ResourceOperation.SET,
                                duration
                            ).createAction()
                        )
                    ).createAction()
                ),
                Optional.empty(),
                new HudRender(
                    Optional.of(VoidLily.createMimicryCondition(index)),
                    SpriteLocations.VOIDLILY_EXTRA_RESOURCES,
                    true,
                    false,
                    index,
                    index,
                    0
                ),
                0,
                duration,
                duration
            )
        );

        return mimicry;
    }

    private static POPowerProvider.MultiplePowerJsonBuilder floralMimicryActive (TagKey<Item> itemTag, EntityAction activePower, Text tooltip, boolean consumeItem) {
        return new POPowerProvider.MultiplePowerJsonBuilder()
            .add(
                "consume",
                new ActiveItemPower(
                    1,
                    HudRender.DONT_RENDER,
                    KeyUtil.primary(),
                    new SequenceEntityActionType(
                        List.of(
                            new EmitGameEventEntityActionType(GameEvent.EQUIP).createAction(),
                            activePower
                        )
                    ).createAction(),
                    PowerGenerator.ingredientConditionFromTag(itemTag),
                    consumeItem ? Optional.empty() : Optional.of(new ConstantEntityConditionType(false).createCondition()),
                    consumeItem ? 1 : 0,
                    true,
                    Optional.empty()
                )
            ).add(
                "tooltip",
                new TooltipPowerType(
                    Optional.of(PowerGenerator.ingredientConditionFromTag(itemTag)),
                    List.of(tooltip),
                    false,
                    20,
                    0,
                    Optional.empty()
                )
            );
    }

    private static IfElseListMetaActionType.ConditionedAction<EntityAction, EntityCondition> createMimicryDrain (int index) {
        return new IfElseListMetaActionType.ConditionedAction<>(
            new ChangeResourceEntityActionType(
                PowerReference.resource(com.provismet.proviorigins.originTypes.voidlily.VoidLily.getHudForIndex(index)),
                ResourceOperation.ADD,
                -1
            ).createAction(),
            createMimicryCondition(index)
        );
    }

    private static EntityCondition createMimicryCondition (int index) {
        return new ResourceEntityConditionType(
            PowerReference.resource(PowerNames.VoidLily.FLOWER_COPY__RESOURCE),
            Comparison.EQUAL,
            index
        ).createCondition();
    }
}
