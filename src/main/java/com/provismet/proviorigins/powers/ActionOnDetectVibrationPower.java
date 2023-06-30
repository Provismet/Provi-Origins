package com.provismet.proviorigins.powers;

import java.util.function.Consumer;
import java.util.function.Predicate;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.tag.GameEventTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.Vibrations;
import net.minecraft.world.event.GameEvent.Emitter;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.EntityGameEventHandler;

@SuppressWarnings("rawtypes")
public class ActionOnDetectVibrationPower extends Power implements Vibrations {
    private static final String RANGE_LABEL = "range";
    private static final String GAME_EVENT_LABEL = "game_event_tag";
    private static final String MAX_DELAY_LABEL = "max_delay";
    private static final String MIN_DELAY_LABEL = "min_delay";

    private final int range;
    private final int maxDelay;
    private final int minDelay;
    private final TagKey<GameEvent> acceptedEvents;
    private final Consumer<Pair<Entity, Entity>> bientityAction;
    private final Predicate<Pair<Entity, Entity>> bientityCondition;
    
    private final Callback callback;
    private final ListenerData listenerData;

    public final EntityGameEventHandler<VibrationListener> eventHandler;

    public ActionOnDetectVibrationPower(PowerType<?> type, LivingEntity entity, int range, int minDelay, int maxDelay, TagKey<GameEvent> acceptedEvents, Consumer<Pair<Entity, Entity>> bientityAction, Predicate<Pair<Entity,Entity>> bientityCondition) {
        super(type, entity);
        this.range = range;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.acceptedEvents = acceptedEvents;
        this.bientityAction = bientityAction;
        this.bientityCondition = bientityCondition;

        this.eventHandler = new EntityGameEventHandler<VibrationListener>(new VibrationListener(this));
        this.callback = new VibrationCallback();
        this.listenerData = new ListenerData();

        this.setTicking();
    }

    private boolean shouldExecuteFor (Entity other) {
        if (other == this.entity || other == null) return false;
        else if (this.bientityCondition == null) return true;
        return this.bientityCondition.test(new Pair<>(other, this.entity));
    }

    public void tick () {
        Ticker.tick(this.entity.getWorld(), this.listenerData, this.callback);
        if (this.listenerData.getDelay() > this.maxDelay) this.listenerData.setDelay(this.maxDelay);
    }

     @Override
    public ListenerData getVibrationListenerData () {
        return this.listenerData;
    }

    @Override
    public Callback getVibrationCallback() {
        return this.callback;
    }

    public static PowerFactory createPowerFactory () {
        return new PowerFactory<>(Powers.identifier("action_on_detect_vibration"),
            new SerializableData()
            .add(RANGE_LABEL, SerializableDataTypes.INT)
            .add(MIN_DELAY_LABEL, SerializableDataTypes.INT, 0)
            .add(MAX_DELAY_LABEL, SerializableDataTypes.INT, 0)
            .add(GAME_EVENT_LABEL, SerializableDataTypes.GAME_EVENT_TAG, GameEventTags.WARDEN_CAN_LISTEN)
            .add(Powers.BIENTITY_ACTION, ApoliDataTypes.BIENTITY_ACTION)
            .add(Powers.BIENTITY_CONDITION, ApoliDataTypes.BIENTITY_CONDITION, null),
            data -> (type, player) -> new ActionOnDetectVibrationPower(type, player,
                data.getInt(RANGE_LABEL),
                data.getInt(MIN_DELAY_LABEL),
                data.getInt(MAX_DELAY_LABEL),
                data.get(GAME_EVENT_LABEL),
                data.get(Powers.BIENTITY_ACTION),
                data.get(Powers.BIENTITY_CONDITION)))
            .allowCondition();
    }

    class VibrationCallback implements Vibrations.Callback {
        VibrationCallback () {}

        @Override
        public int getDelay (float distance) {
            return Math.max(Vibrations.Callback.super.getDelay(distance), minDelay);
        }

        @Override
        public TagKey<GameEvent> getTag () {
            return acceptedEvents;
        }

        @Override
        public int getRange () {
            return range;
        }

        @Override
        public PositionSource getPositionSource () {
            return new EntityPositionSource(entity, entity.getStandingEyeHeight() / 2);
        }

        @Override
        public boolean accepts (ServerWorld world, BlockPos pos, GameEvent event, Emitter other) {
            return shouldExecuteFor(other.sourceEntity());
        }

        @Override
        public void accept (ServerWorld world, BlockPos pos, GameEvent event, Entity entity, Entity sourceEntity, float distance) {
            if (sourceEntity != null && sourceEntity instanceof LivingEntity) {
                if (shouldExecuteFor(sourceEntity)) {
                    bientityAction.accept(new Pair<>(sourceEntity, entity));
                }
            }
            else if (entity != null && entity instanceof LivingEntity) {
                if (shouldExecuteFor(entity)) {
                    bientityAction.accept(new Pair<>(entity, entity));
                }
            }
        }
    }
}
