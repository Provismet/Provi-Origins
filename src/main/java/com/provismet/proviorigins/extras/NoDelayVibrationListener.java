package com.provismet.proviorigins.extras;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.VibrationListener;

public class NoDelayVibrationListener extends VibrationListener {
    public NoDelayVibrationListener (PositionSource positionSource, int range, Callback callback, Vibration vibration, float distance, int delay) {
        super(positionSource, range, callback, vibration, distance, delay);
    }

    @Override
    public void tick (World world) {
        if (world instanceof ServerWorld) {
            if (this.delay > 0) this.delay = 0;
            super.tick(world);
        }
    }
}
