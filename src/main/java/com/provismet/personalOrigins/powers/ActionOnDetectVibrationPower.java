package com.provismet.personalOrigins.powers;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.provismet.personalOrigins.extras.NoDelayVibrationListener;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.GameEventTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.GameEvent.Emitter;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import net.minecraft.world.event.listener.GameEventListener;
import net.minecraft.world.event.listener.VibrationListener;

@SuppressWarnings("rawtypes")
public class ActionOnDetectVibrationPower extends Power implements VibrationListener.Callback {
    private final int range;
    private final TagKey<GameEvent> acceptedEvents;
    private final Consumer<Pair<Entity, Entity>> bientityAction;
    private final Predicate<Pair<Entity, Entity>> bientityCondition;
    
    public final EntityGameEventHandler<VibrationListener> eventHandler;

    public ActionOnDetectVibrationPower(PowerType<?> type, LivingEntity entity, int range, TagKey<GameEvent> acceptedEvents, Consumer<Pair<Entity, Entity>> bientityAction, Predicate<Pair<Entity,Entity>> bientityCondition) {
        super(type, entity);
        this.range = range;
        this.acceptedEvents = acceptedEvents;
        this.bientityAction = bientityAction;
        this.bientityCondition = bientityCondition;

        this.eventHandler = new EntityGameEventHandler<VibrationListener>(new NoDelayVibrationListener(new EntityPositionSource(this.entity, 0), this.range, this, null, 0, 0));
        this.setTicking();
    }

    private boolean shouldExecuteFor (Entity other) {
        if (other == this.entity || other == null) return false;
        else if (this.bientityCondition == null) return true;
        return this.bientityCondition.test(new Pair<>(other, this.entity));
    }

    public void tick () {
        World world = this.entity.world;
        this.eventHandler.getListener().tick(world);
    }

    @Override
    public TagKey<GameEvent> getTag () {
        return this.acceptedEvents;
    }

    @Override
    public boolean accepts (ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, Emitter other) {
        return shouldExecuteFor(other.sourceEntity());
    }

    @Override
    public void accept (ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, Entity sourceEntity, float distance) {
        if (sourceEntity != null && sourceEntity instanceof LivingEntity) {
            if (shouldExecuteFor(sourceEntity)) {
                this.bientityAction.accept(new Pair<>(sourceEntity, this.entity));
            }
        }
        else if (entity != null && entity instanceof LivingEntity) {
            if (shouldExecuteFor(entity)) {
                this.bientityAction.accept(new Pair<>(entity, this.entity));
            }
        }
    }

    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("action_on_detect_vibration"),
            new SerializableData()
            .add("range", SerializableDataTypes.INT)
            .add("game_event_tag", SerializableDataTypes.GAME_EVENT_TAG, GameEventTags.WARDEN_CAN_LISTEN)
            .add(Powers.BIENTITY_ACTION, ApoliDataTypes.BIENTITY_ACTION)
            .add(Powers.BIENTITY_CONDITION, ApoliDataTypes.BIENTITY_CONDITION, null),
            data -> (type, player) -> new ActionOnDetectVibrationPower(type, player,
                data.getInt("range"),
                data.get("game_event_tag"),
                data.get(Powers.BIENTITY_ACTION),
                data.get(Powers.BIENTITY_CONDITION)))
            .allowCondition();
    }
}
