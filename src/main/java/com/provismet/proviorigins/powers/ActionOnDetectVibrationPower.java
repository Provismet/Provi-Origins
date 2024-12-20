package com.provismet.proviorigins.powers;

import java.util.Optional;

import com.provismet.proviorigins.registries.POPowerTypes;
import com.provismet.proviorigins.utility.ConditionUtil;
import com.provismet.proviorigins.utility.constants.FieldNames;
import io.github.apace100.apoli.action.BiEntityAction;
import io.github.apace100.apoli.condition.BiEntityCondition;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.GameEventTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.Vibrations;
import net.minecraft.world.event.GameEvent.Emitter;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("rawtypes")
public class ActionOnDetectVibrationPower extends PowerType implements Vibrations {
    private static final String RANGE_LABEL = "range";
    private static final String GAME_EVENT_LABEL = "game_event_tag";
    private static final String MAX_DELAY_LABEL = "max_delay";
    private static final String MIN_DELAY_LABEL = "min_delay";

    private final int range;
    private final int maxDelay;
    private final int minDelay;
    private final TagKey<GameEvent> acceptedEvents;
    private final BiEntityAction bientityAction;
    private final Optional<BiEntityCondition> bientityCondition;
    
    private final Callback callback;
    private final ListenerData listenerData;

    public final EntityGameEventHandler<VibrationListener> eventHandler;

    public ActionOnDetectVibrationPower(int range, int minDelay, int maxDelay, TagKey<GameEvent> acceptedEvents, BiEntityAction bientityAction, Optional<BiEntityCondition> bientityCondition, Optional<EntityCondition> condition) {
        super(condition);
        this.range = range;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.acceptedEvents = acceptedEvents;
        this.bientityAction = bientityAction;
        this.bientityCondition = bientityCondition;

        this.eventHandler = new EntityGameEventHandler<>(new VibrationListener(this));
        this.callback = new VibrationCallback();
        this.listenerData = new ListenerData();

        this.setTicking();
    }

    public static final TypedDataObjectFactory<ActionOnDetectVibrationPower> DATA_FACTORY = PowerType.createConditionedDataFactory(
        new SerializableData()
            .add(RANGE_LABEL, SerializableDataTypes.INT)
            .add(MIN_DELAY_LABEL, SerializableDataTypes.INT, 0)
            .add(MAX_DELAY_LABEL, SerializableDataTypes.INT, 0)
            .add(GAME_EVENT_LABEL, SerializableDataTypes.GAME_EVENT_TAG, GameEventTags.WARDEN_CAN_LISTEN)
            .add(FieldNames.BIENTITY_ACTION, BiEntityAction.DATA_TYPE)
            .add(FieldNames.BIENTITY_CONDITION, BiEntityCondition.DATA_TYPE.optional(), Optional.empty()),
        (data, condition) -> new ActionOnDetectVibrationPower(
            data.getInt(RANGE_LABEL),
            data.getInt(MIN_DELAY_LABEL),
            data.getInt(MAX_DELAY_LABEL),
            data.get(GAME_EVENT_LABEL),
            data.get(FieldNames.BIENTITY_ACTION),
            data.get(FieldNames.BIENTITY_CONDITION),
            condition
        ),
        (powerType, data) -> data.instance()
            .set(RANGE_LABEL, powerType.range)
            .set(MIN_DELAY_LABEL, powerType.minDelay)
            .set(MAX_DELAY_LABEL, powerType.maxDelay)
            .set(GAME_EVENT_LABEL, powerType.acceptedEvents)
            .set(FieldNames.BIENTITY_ACTION, powerType.bientityAction)
            .set(FieldNames.BIENTITY_CONDITION, powerType.bientityCondition)
    );

    private boolean shouldExecuteFor (Entity other) {
        if (other == this.getHolder() || other == null) return false;
        return ConditionUtil.emptyOrTest(this.bientityCondition, other, this.getHolder());
    }

    @Override
    public void serverTick () {
        super.serverTick();
        Ticker.tick(this.getHolder().getWorld(), this.listenerData, this.callback);
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

    @Override
    public @NotNull PowerConfiguration<?> getConfig () {
        return POPowerTypes.ACTION_ON_DETECT_VIBRATION;
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
            return new EntityPositionSource(getHolder(), getHolder().getStandingEyeHeight() / 2);
        }

        @Override
        public boolean accepts (ServerWorld world, BlockPos pos, RegistryEntry<GameEvent> event, Emitter other) {
            return shouldExecuteFor(other.sourceEntity());
        }

        @Override
        public void accept (ServerWorld world, BlockPos pos, RegistryEntry<GameEvent> event, Entity entity, Entity sourceEntity, float distance) {
            if (sourceEntity instanceof LivingEntity) {
                if (shouldExecuteFor(sourceEntity)) {
                    bientityAction.execute(sourceEntity, entity);
                }
            }
            else if (entity instanceof LivingEntity) {
                if (shouldExecuteFor(entity)) {
                    bientityAction.execute(entity, entity);
                }
            }
        }
    }
}
