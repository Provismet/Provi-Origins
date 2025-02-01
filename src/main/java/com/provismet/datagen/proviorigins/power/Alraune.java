package com.provismet.datagen.proviorigins.power;

import com.provismet.datagen.proviorigins.PowerGenerator;
import com.provismet.datagen.proviorigins.constants.PowerNames;
import com.provismet.datagen.proviorigins.provider.POPowerProvider;
import com.provismet.proviorigins.actions.bientity.FireProjectileAction;
import com.provismet.proviorigins.actions.entity.ActOnClosestEntityAction;
import com.provismet.proviorigins.actions.entity.ParticleRingAction;
import com.provismet.proviorigins.actions.entity.SetSummonMaxLifeAction;
import com.provismet.proviorigins.actions.entity.SummonMinionAction;
import com.provismet.proviorigins.conditions.bientity.CanSeeBiEntityConditionType;
import com.provismet.proviorigins.conditions.bientity.FriendlyBiEntityConditionType;
import com.provismet.proviorigins.conditions.block.CollisionBiEntityConditionType;
import com.provismet.proviorigins.content.particles.effects.FlowerParticleEffect;
import com.provismet.proviorigins.content.particles.effects.TelegraphParticleEffect;
import com.provismet.proviorigins.originTypes.alraune.FlowerMinion;
import com.provismet.proviorigins.powers.ActiveItemPower;
import com.provismet.proviorigins.utility.BadgeUtil;
import com.provismet.proviorigins.utility.KeyUtil;
import com.provismet.proviorigins.utility.OriginList;
import com.provismet.proviorigins.utility.PowerUtil;
import com.provismet.proviorigins.utility.constants.SpriteLocations;
import com.provismet.proviorigins.utility.tags.POBiomeTags;
import com.provismet.proviorigins.utility.tags.POBlockTags;
import com.provismet.proviorigins.utility.tags.POItemTags;
import io.github.apace100.apoli.action.BiEntityAction;
import io.github.apace100.apoli.action.EntityAction;
import io.github.apace100.apoli.action.type.bientity.AddVelocityBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.DamageBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.ActorActionBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.IfElseBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.RandomChanceBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.SequenceBiEntityActionType;
import io.github.apace100.apoli.action.type.bientity.meta.TargetActionBiEntityActionType;
import io.github.apace100.apoli.action.type.entity.ApplyEffectEntityActionType;
import io.github.apace100.apoli.action.type.entity.AreaOfEffectEntityActionType;
import io.github.apace100.apoli.action.type.entity.ChangeResourceEntityActionType;
import io.github.apace100.apoli.action.type.entity.FeedEntityActionType;
import io.github.apace100.apoli.action.type.entity.GrantPowerEntityActionType;
import io.github.apace100.apoli.action.type.entity.PlaySoundEntityActionType;
import io.github.apace100.apoli.action.type.entity.SpawnEntityEntityActionType;
import io.github.apace100.apoli.action.type.entity.SpawnParticlesEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.IfElseListEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.SequenceEntityActionType;
import io.github.apace100.apoli.action.type.item.ConsumeItemActionType;
import io.github.apace100.apoli.action.type.meta.IfElseListMetaActionType;
import io.github.apace100.apoli.condition.BiEntityCondition;
import io.github.apace100.apoli.condition.type.bientity.meta.ActorConditionBiEntityConditionType;
import io.github.apace100.apoli.condition.type.bientity.meta.AllOfBiEntityConditionType;
import io.github.apace100.apoli.condition.type.bientity.meta.TargetConditionBiEntityConditionType;
import io.github.apace100.apoli.condition.type.block.InTagBlockConditionType;
import io.github.apace100.apoli.condition.type.block.meta.AllOfBlockConditionType;
import io.github.apace100.apoli.condition.type.damage.InTagDamageConditionType;
import io.github.apace100.apoli.condition.type.entity.AdvancementEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.ExposedToSkyEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.ExposedToSunEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.FoodLevelEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.LivingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.OnBlockEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.ResourceEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.SaturationLevelEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.SneakingEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.AllOfEntityConditionType;
import io.github.apace100.apoli.condition.type.entity.meta.AnyOfEntityConditionType;
import io.github.apace100.apoli.condition.type.item.IngredientItemConditionType;
import io.github.apace100.apoli.power.PowerReference;
import io.github.apace100.apoli.power.type.ActionOnBeingUsedPowerType;
import io.github.apace100.apoli.power.type.ActionOnCallbackPowerType;
import io.github.apace100.apoli.power.type.ActionOverTimePowerType;
import io.github.apace100.apoli.power.type.DamageOverTimePowerType;
import io.github.apace100.apoli.power.type.ModifyDamageTakenPowerType;
import io.github.apace100.apoli.power.type.ParticlePowerType;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.power.type.PreventBlockSelectionPowerType;
import io.github.apace100.apoli.power.type.ResourcePowerType;
import io.github.apace100.apoli.power.type.TooltipPowerType;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.apoli.util.ResourceOperation;
import io.github.apace100.apoli.util.Shape;
import io.github.apace100.apoli.util.Space;
import io.github.apace100.apoli.util.modifier.Modifier;
import io.github.apace100.apoli.util.modifier.ModifierOperation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class Alraune {
    public static void build (POPowerProvider.PowerCollector collector) {
        Alraune.buildHiddenPassives(collector);
        Alraune.buildLifeSap(collector);
        Alraune.buildPhotosynthesis(collector);
        Alraune.buildPlapPlapGivePitcherPod(collector);
        Alraune.buildMinionTypes(collector);
        Alraune.buildSummonedPassives(collector);
        Alraune.buildMinionPowers(collector);
        Alraune.buildThroughGrass(collector);
    }

    private static POPowerProvider.MultiplePowerJsonBuilder createAlrauneSummon (FlowerMinion minion) {
        Identifier powerSource = OriginList.ALRAUNE.identifier("summon_plant_turret");
        POPowerProvider.MultiplePowerJsonBuilder summonPower = new POPowerProvider.MultiplePowerJsonBuilder();
        summonPower.add(
            "summon",
            new ActiveItemPower(
                1,
                HudRender.DONT_RENDER,
                KeyUtil.primary(),
                new SequenceEntityActionType(
                    List.of(
                        new SummonMinionAction(
                            minion.getTexture(),
                            false,
                            Vec3d.ZERO,
                            2f,
                            false,
                            1200,
                            Optional.of(
                                new SequenceBiEntityActionType(
                                    List.of(
                                        new TargetActionBiEntityActionType(
                                            new GrantPowerEntityActionType(
                                                PowerReference.of(PowerNames.Alraune.SUMMONED_PASSIVES),
                                                powerSource
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
                                                    PowerReference.of(minion.getUpgradedPower()),
                                                    powerSource
                                                ).createAction()
                                            ).createAction(),
                                            Optional.of(
                                                new TargetActionBiEntityActionType(
                                                    new GrantPowerEntityActionType(
                                                        PowerReference.of(minion.getBasePower()),
                                                        powerSource
                                                    ).createAction()
                                                ).createAction()
                                            )
                                        ).createAction()
                                    )
                                ).createAction()
                            )
                        ).createAction(),
                        Alraune.addLifeSap(-1)
                    )
                ).createAction(),
                new IngredientItemConditionType(
                    Ingredient.fromTag(minion.itemTag())
                ).createCondition(),
                Optional.empty(),
                1,
                true,
                Optional.of(
                    new ResourceEntityConditionType(
                        PowerReference.resource(PowerNames.Alraune.LIFE_SAP),
                        Comparison.GREATER_THAN,
                        0
                    ).createCondition()
                )
            )
        );

        summonPower.add(
            "tooltip",
            new TooltipPowerType(
                Optional.of(new IngredientItemConditionType(Ingredient.fromTag(minion.itemTag())).createCondition()),
                List.of(minion.getTooltip()),
                false,
                20,
                0,
                Optional.empty()
            )
        );

        return summonPower;
    }

    private static PowerType createAlrauneSummonPower (int interval, float radius, float step, Vector3f particleColour, boolean telegraph, boolean aoe, BiEntityAction action) {
        BiEntityCondition isValidTarget = new AllOfBiEntityConditionType(
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

        List<EntityAction> intervalActions = new ArrayList<>();
        intervalActions.add(
            new ParticleRingAction(
                radius,
                step,
                1,
                Space.LOCAL,
                0,
                true,
                new FlowerParticleEffect(particleColour, 0.75f),
                new Vector3f(0f, 0.25f, 0f)
            ).createAction()
        );
        if (telegraph) {
            intervalActions.add(
                new SpawnParticlesEntityActionType(
                    Optional.empty(),
                    new TelegraphParticleEffect(
                        particleColour,
                        0.75f,
                        radius,
                        interval
                    ),
                    new Vec3d(0, 0.1, 0),
                    Vec3d.ZERO,
                    false,
                    0,
                    1
                ).createAction()
            );
        }
        if (aoe) {
            intervalActions.add(
                new AreaOfEffectEntityActionType(
                    action,
                    Optional.of(isValidTarget),
                    Shape.SPHERE,
                    4,
                    false
                ).createAction()
            );
        }
        else {
            intervalActions.add(
                new ActOnClosestEntityAction(
                    radius,
                    action,
                    isValidTarget
                ).createAction()
            );
        }

        return new ActionOverTimePowerType(
            Optional.of(new SequenceEntityActionType(intervalActions).createAction()),
            Optional.empty(),
            Optional.empty(),
            interval,
            Optional.empty()
        );
    }

    private static PowerType createFlowerParticles (Vector3f colour, int frequency) {
        return Alraune.createFlowerParticles(colour, frequency, 0.1);
    }

    private static PowerType createFlowerParticles (Vector3f colour, int frequency, double yOffset) {
        return new ParticlePowerType(
            Optional.empty(),
            new FlowerParticleEffect(colour, 1),
            new Vec3d(0, yOffset, 0),
            new Vec3d(0.25, 0.25, 0.25),
            frequency,
            1,
            0,
            false,
            false,
            false,
            Optional.empty()
        );
    }

    private static EntityAction addLifeSap (int amount) {
        return new ChangeResourceEntityActionType(
            PowerReference.resource(PowerNames.Alraune.LIFE_SAP),
            ResourceOperation.ADD,
            amount
        ).createAction();
    }

    private static void buildHiddenPassives (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Alraune.HIDDEN_PASSIVES,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "particles1",
                    Alraune.createFlowerParticles(new Vector3f(0.71f, 0.84f, 0.96f), 6)
                ).add(
                    "particles2",
                    Alraune.createFlowerParticles(new Vector3f(0.95f, 0.33f, 0.35f), 7)
                ).add(
                    "particles3",
                    Alraune.createFlowerParticles(new Vector3f(0.98f, 0.71f, 0.3f), 8)
                ).add(
                    "fertiliser",
                    new TooltipPowerType(
                        Optional.of(PowerGenerator.ingredientConditionFromTag(POItemTags.FERTILISER)),
                        List.of(Text.translatable("tooltip.proviorigins.alraune.super_fertiliser")),
                        false,
                        20,
                        0,
                        Optional.empty()
                    )
                ),
            true
        );
    }

    private static void buildLifeSap (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Alraune.LIFE_SAP,
            new ResourcePowerType(
                Optional.empty(),
                Optional.empty(),
                new HudRender(
                    Optional.empty(),
                    SpriteLocations.ALRAUNE_LIFE_SAP_RESOURCE,
                    true,
                    false,
                    0,
                    0,
                    0
                ),
                0, LifeSap.MAX,
                LifeSap.START_VALUE
            )
        ).add(
            PowerNames.Alraune.LIFE_SAP_RECHARGE,
            new ActionOverTimePowerType(
                Optional.of(Alraune.addLifeSap(1)),
                Optional.empty(),
                Optional.empty(),
                500,
                Optional.empty()
            ),
            true
        ).add(
            PowerNames.Alraune.LIFE_SAP_RESET_ON_DEATH,
            new ActionOnCallbackPowerType(
                Optional.of(
                    new ChangeResourceEntityActionType(
                        PowerReference.resource(PowerNames.Alraune.LIFE_SAP),
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
        );
    }

    private static void buildPhotosynthesis (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Alraune.PHOTOSYNTHESIS,
            new ActionOverTimePowerType(
                Optional.of(
                    new IfElseListEntityActionType(
                        List.of(
                            new IfElseListMetaActionType.ConditionedAction<>(
                                new FeedEntityActionType(1, 0).createAction(),
                                new FoodLevelEntityConditionType(Comparison.GREATER_THAN, 20).createCondition()
                            ),
                            new IfElseListMetaActionType.ConditionedAction<>(
                                new FeedEntityActionType(1, 1).createAction(),
                                new SaturationLevelEntityConditionType(Comparison.LESS_THAN, 6).createCondition()
                            )
                        )
                    ).createAction()
                ),
                Optional.empty(),
                Optional.empty(),
                20,
                Optional.of(new ExposedToSunEntityConditionType().createCondition())
            )
        );
    }

    private static void buildPlapPlapGivePitcherPod (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Alraune.PLAP_PLAP_GIVE_PITCHER_POD,
            new ActionOnBeingUsedPowerType(
                Optional.of(
                    new SequenceBiEntityActionType(
                        List.of(
                            new TargetActionBiEntityActionType(
                                new SpawnParticlesEntityActionType(
                                    Optional.empty(),
                                    ParticleTypes.HAPPY_VILLAGER,
                                    Vec3d.ZERO,
                                    new Vec3d(0.5, 0.5, 0.5),
                                    false,
                                    0,
                                    12
                                ).createAction()
                            ).createAction(),
                            new TargetActionBiEntityActionType(
                                new PlaySoundEntityActionType(
                                    SoundEvents.ITEM_BONE_MEAL_USE,
                                    Optional.of(SoundCategory.PLAYERS),
                                    1,
                                    1
                                ).createAction()
                            ).createAction(),
                            new RandomChanceBiEntityActionType(
                                new ActorActionBiEntityActionType(
                                    new SpawnEntityEntityActionType(
                                        EntityType.ITEM,
                                        Optional.empty(),
                                        Optional.empty(),
                                        PowerGenerator.createItemEntityNbt(Identifier.ofVanilla("pitcher_plant"))
                                    ).createAction()
                                ).createAction(),
                                Optional.empty(),
                                0.404f
                            ).createAction()
                        )
                    ).createAction()
                ),
                Optional.empty(),
                Optional.empty(),
                Optional.of(
                    new IngredientItemConditionType(
                        Ingredient.ofItems(Items.BONE_MEAL)
                    ).createCondition()
                ),
                Optional.empty(),
                Optional.empty(),
                EnumSet.allOf(Hand.class),
                ActionResult.SUCCESS,
                1,
                Optional.empty()
            ),
            true
        );
    }

    private static void buildMinionTypes (POPowerProvider.PowerCollector collector) {
        Identifier summonPlant = OriginList.ALRAUNE.identifier("summon_plant");
        collector.add(
            PowerNames.Alraune.SUMMON_PLANT_AOE,
            Alraune.createAlrauneSummon(FlowerMinion.AOE),
            List.of(
                BadgeUtil.active(),
                BadgeUtil.cost(summonPlant, 1),
                BadgeUtil.info(summonPlant, 2),
                BadgeUtil.info(summonPlant, 3),
                BadgeUtil.dragon()
            ),
            PowerUtil.getNameTranslationKey(summonPlant),
            PowerUtil.getDescriptionTranslationKey(summonPlant)
        ).add(
            PowerNames.Alraune.SUMMON_PLANT_PROJECTILE,
            Alraune.createAlrauneSummon(FlowerMinion.PROJECTILE),
            true
        ).add(
            PowerNames.Alraune.SUMMON_PLANT_PULL,
            Alraune.createAlrauneSummon(FlowerMinion.PULL),
            true
        ).add(
            PowerNames.Alraune.SUMMON_PLANT_PUSH,
            Alraune.createAlrauneSummon(FlowerMinion.PUSH),
            true
        );
    }

    private static void buildSummonedPassives (POPowerProvider.PowerCollector collector) {
        // TODO: Add fungifloral to summon.
        collector.add(
            PowerNames.Alraune.SUMMONED_PASSIVES,
            new POPowerProvider.MultiplePowerJsonBuilder()
                .add(
                    "is_bonemealed",
                    new ResourcePowerType(
                        Optional.empty(),
                        Optional.empty(),
                        HudRender.DONT_RENDER,
                        0, 1,
                        0
                    )
                ).add(
                    "renewable",
                    new ActionOnBeingUsedPowerType(
                        Optional.of(
                            new SequenceBiEntityActionType(
                                List.of(
                                    new TargetActionBiEntityActionType(
                                        new SpawnParticlesEntityActionType(
                                            Optional.empty(),
                                            ParticleTypes.HAPPY_VILLAGER,
                                            Vec3d.ZERO,
                                            new Vec3d(0.5, 0.5, 0.5),
                                            false,
                                            0,
                                            12
                                        ).createAction()
                                    ).createAction(),
                                    new TargetActionBiEntityActionType(
                                        new PlaySoundEntityActionType(
                                            SoundEvents.ITEM_BONE_MEAL_USE,
                                            Optional.of(SoundCategory.PLAYERS),
                                            1,
                                            1
                                        ).createAction()
                                    ).createAction(),
                                    new TargetActionBiEntityActionType(
                                        new ChangeResourceEntityActionType(
                                            PowerReference.resource(PowerNames.Alraune.SUMMONED_PASSIVES.withSuffixedPath("_is_bonemealed")),
                                            ResourceOperation.SET,
                                            1
                                        ).createAction()
                                    ).createAction()
                                )
                            ).createAction()
                        ),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(
                            new IngredientItemConditionType(
                                Ingredient.ofItems(Items.BONE_MEAL)
                            ).createCondition()
                        ),
                        Optional.empty(),
                        Optional.empty(),
                        EnumSet.allOf(Hand.class),
                        ActionResult.SUCCESS,
                        0,
                        Optional.empty()
                    )
                ).add(
                    "flammable",
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
                    "need_sunlight",
                    new DamageOverTimePowerType(
                        DamageTypes.GENERIC,
                        Optional.empty(),
                        0,
                        4f,
                        4f,
                        30,
                        30,
                        Optional.of(
                            new AnyOfEntityConditionType(
                                List.of(
                                    new OnBlockEntityConditionType(
                                        Optional.of(new InTagBlockConditionType(BlockTags.DIRT).createCondition())
                                    ).createCondition(),
                                    new AllOfEntityConditionType(
                                        List.of(
                                            PowerGenerator.biomeEntityConditionFromTag(POBiomeTags.ALWAYS_GROW, true),
                                            new ResourceEntityConditionType(
                                                PowerReference.resource(PowerNames.Alraune.SUMMONED_PASSIVES.withSuffixedPath("_is_bonemealed")),
                                                Comparison.EQUAL,
                                                0
                                            ).createCondition(),
                                            new AnyOfEntityConditionType(
                                                List.of(
                                                    new AllOfEntityConditionType(
                                                        List.of(
                                                            PowerGenerator.biomeEntityConditionFromTag(POBiomeTags.NATURAL, true),
                                                            new ExposedToSunEntityConditionType().createCondition(true)
                                                        )
                                                    ).createCondition(),
                                                    new AllOfEntityConditionType(
                                                        List.of(
                                                            PowerGenerator.biomeEntityConditionFromTag(POBiomeTags.NATURAL, false),
                                                            new ExposedToSkyEntityConditionType().createCondition(true)
                                                        )
                                                    ).createCondition()
                                                )
                                            ).createCondition()
                                        )
                                    ).createCondition()
                                )
                            ).createCondition()
                        )
                    )
                ).add(
                    "increase_lifespan",
                    new ActionOnBeingUsedPowerType(
                        Optional.of(
                            new TargetActionBiEntityActionType(
                                new SequenceEntityActionType(
                                    List.of(
                                        new SpawnParticlesEntityActionType(
                                            Optional.empty(),
                                            ParticleTypes.HAPPY_VILLAGER,
                                            Vec3d.ZERO,
                                            new Vec3d(0.5, 0.5, 0.5),
                                            false,
                                            0,
                                            12
                                        ).createAction(),
                                        new PlaySoundEntityActionType(
                                            SoundEvents.ITEM_BONE_MEAL_USE,
                                            Optional.of(SoundCategory.PLAYERS),
                                            1,
                                            1
                                        ).createAction(),
                                        new SetSummonMaxLifeAction(-1).createAction()
                                    )
                                ).createAction()
                            ).createAction()
                        ),
                        Optional.empty(),
                        Optional.of(new ConsumeItemActionType(1).createAction()),
                        Optional.of(PowerGenerator.ingredientConditionFromTag(POItemTags.FERTILISER)),
                        Optional.empty(),
                        Optional.empty(),
                        EnumSet.allOf(Hand.class),
                        ActionResult.SUCCESS,
                        0,
                        Optional.empty()
                    )
                ).add(
                    "particles1",
                    Alraune.createFlowerParticles(new Vector3f(0.71f, 0.84f, 0.96f), 6, 0.5)
                ).add(
                    "particles2",
                    Alraune.createFlowerParticles(new Vector3f(0.95f, 0.33f, 0.35f), 6, 0.5)
                ).add(
                    "particles3",
                    Alraune.createFlowerParticles(new Vector3f(0.98f, 0.71f, 0.3f), 6, 0.5)
                ),
            true
        );
    }

    private static void buildMinionPowers (POPowerProvider.PowerCollector collector) {
        Function<Float,BiEntityAction> aoeDamage = damage -> new DamageBiEntityActionType(
            DamageTypes.INDIRECT_MAGIC,
            Optional.of(damage),
            List.of()
        ).createAction();

        Function<Float,BiEntityAction> projectile = speed -> new FireProjectileAction(
            1,
            speed,
            1,
            EntityType.ARROW,
            new NbtCompound(),
            Optional.empty()
        ).createAction();

        collector.add(
            PowerNames.Alraune.SUMMONED_AOE,
            Alraune.createAlrauneSummonPower(
                30,
                4f,
                3f,
                new Vector3f(0.8f, 0f, 0f),
                true,
                true,
                aoeDamage.apply(3f)
            )
        ).add(
            PowerNames.Alraune.SUMMONED_AOE_UPGRADE,
            Alraune.createAlrauneSummonPower(
                30,
                6f,
                3f,
                new Vector3f(0.8f, 0f, 0f),
                true,
                true,
                aoeDamage.apply(4f)
            )
        ).add(
            PowerNames.Alraune.SUMMONED_PROJECTILE,
            Alraune.createAlrauneSummonPower(
                40,
                16,
                1.5f,
                new Vector3f(0.8f, 0.25f, 0.8f),
                false,
                false,
                projectile.apply(1.5f)
            )
        ).add(
            PowerNames.Alraune.SUMMONED_PROJECTILE_UPGRADE,
            Alraune.createAlrauneSummonPower(
                40,
                16,
                1.5f,
                new Vector3f(0.8f, 0.25f, 0.8f),
                false,
                false,
                projectile.apply(1.75f)
            )
        ).add(
            PowerNames.Alraune.SUMMONED_PULL,
            Alraune.createAlrauneSummonPower(
                30,
                4f,
                3f,
                new Vector3f(0.8f, 0.5f, 0f),
                true,
                true,
                new AddVelocityBiEntityActionType(
                    new Vector3f(0, 0, -1),
                    AddVelocityBiEntityActionType.Reference.POSITION,
                    false
                ).createAction()
            )
        ).add(
            PowerNames.Alraune.SUMMONED_PULL_UPGRADE,
            Alraune.createAlrauneSummonPower(
                30,
                4f,
                3f,
                new Vector3f(0.8f, 0.5f, 0f),
                true,
                true,
                new SequenceBiEntityActionType(
                    List.of(
                        new AddVelocityBiEntityActionType(
                            new Vector3f(0, 0, -1),
                            AddVelocityBiEntityActionType.Reference.POSITION,
                            false
                        ).createAction(),
                        new TargetActionBiEntityActionType(
                            new ApplyEffectEntityActionType(
                                List.of(new StatusEffectInstance(StatusEffects.SLOWNESS, 30))
                            ).createAction()
                        ).createAction()
                    )
                ).createAction()
            )
        ).add(
            PowerNames.Alraune.SUMMONED_PUSH,
            Alraune.createAlrauneSummonPower(
                30,
                4f,
                3f,
                new Vector3f(0.5f, 0.8f, 0f),
                true,
                true,
                new AddVelocityBiEntityActionType(
                    new Vector3f(0, 0, 2),
                    AddVelocityBiEntityActionType.Reference.POSITION,
                    false
                ).createAction()
            )
        ).add(
            PowerNames.Alraune.SUMMONED_PUSH_UPGRADE,
            Alraune.createAlrauneSummonPower(
                30,
                6f,
                3f,
                new Vector3f(0.5f, 0.8f, 0f),
                true,
                true,
                new AddVelocityBiEntityActionType(
                    new Vector3f(0, 0, 2),
                    AddVelocityBiEntityActionType.Reference.POSITION,
                    false
                ).createAction()
            )
        );
    }

    private static void buildThroughGrass (POPowerProvider.PowerCollector collector) {
        collector.add(
            PowerNames.Alraune.THROUGH_GRASS,
            new PreventBlockSelectionPowerType(
                Optional.of(
                    new AllOfBlockConditionType(
                        List.of(
                            new InTagBlockConditionType(POBlockTags.FOLIAGE).createCondition(),
                            new CollisionBiEntityConditionType().createCondition(true)
                        )
                    ).createCondition()
                ),
                Optional.of(new SneakingEntityConditionType().createCondition(true))
            )
        );
    }

    interface LifeSap {
        int MAX = 5;
        int START_VALUE = MAX;
    }
}
