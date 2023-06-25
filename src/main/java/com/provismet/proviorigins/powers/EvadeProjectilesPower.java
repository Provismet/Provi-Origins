package com.provismet.proviorigins.powers;

import java.util.function.Consumer;
import java.util.function.Predicate;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.Pair;

public class EvadeProjectilesPower extends Power {
    private static final String PROJECTILE_ACTION_LABEL = "projectile_action";
    private static final String PROJECTILE_CONDITION_LABEL = "projectile_condition";

    private final Consumer<Entity> entityAction;
    private final Consumer<Pair<Entity,Entity>> bientityAction;
    private final Predicate<Pair<Entity,Entity>> bientityCondition;
    private final Consumer<Pair<Entity,Entity>> projectileAction;
    private final Predicate<Pair<Entity,Entity>> projectileCondition;

    public EvadeProjectilesPower(PowerType<?> type, LivingEntity entity, Consumer<Entity> entityAction, Consumer<Pair<Entity,Entity>> bientityAction, Predicate<Pair<Entity,Entity>> bientityCondition, Consumer<Pair<Entity,Entity>> projectileAction, Predicate<Pair<Entity,Entity>> projectileCondition) {
        super(type, entity);
        this.entityAction = entityAction;
        this.bientityAction = bientityAction;
        this.bientityCondition = bientityCondition;
        this.projectileAction = projectileAction;
        this.projectileCondition = projectileCondition;
    }
    
    public void executeAction (ProjectileEntity projectile) {
        if (this.entityAction != null) this.entityAction.accept(this.entity);

        if (projectile.getOwner() != null) {
            Pair<Entity,Entity> sourcePair = new Pair<Entity,Entity>(projectile.getOwner(), this.entity);
            if (this.bientityCondition == null || this.bientityCondition.test(sourcePair)) {
                if (this.bientityAction != null) this.bientityAction.accept(sourcePair);
            }
        }

        Pair<Entity,Entity> projectilePair = new Pair<Entity,Entity>(projectile, this.entity);
        if (this.projectileCondition == null || this.projectileCondition.test(projectilePair)) {
            if (this.projectileAction != null) this.projectileAction.accept(projectilePair);
        }
    }

    @SuppressWarnings("rawtypes")
    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("evade_projectiles"),
            new SerializableData()
                .add(Powers.ENTITY_ACTION, ApoliDataTypes.ENTITY_ACTION, null)
                .add(Powers.BIENTITY_ACTION, ApoliDataTypes.BIENTITY_ACTION, null)
                .add(Powers.BIENTITY_CONDITION, ApoliDataTypes.BIENTITY_CONDITION, null)
                .add(PROJECTILE_ACTION_LABEL, ApoliDataTypes.BIENTITY_ACTION, null)
                .add(PROJECTILE_CONDITION_LABEL, ApoliDataTypes.BIENTITY_CONDITION, null),
            data -> (type, player) -> new EvadeProjectilesPower(type, player,
                data.get(Powers.ENTITY_ACTION),
                data.get(Powers.BIENTITY_ACTION),
                data.get(Powers.BIENTITY_CONDITION),
                data.get(PROJECTILE_ACTION_LABEL),
                data.get(PROJECTILE_CONDITION_LABEL)
            )
        ).allowCondition();
    }
}
