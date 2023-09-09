package com.provismet.proviorigins.extras;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;

public interface ExtraTameable extends Tameable {
    @Nullable
    public void setOwnerUUID (UUID uuid);

    @Nullable
    default public void setOwner (LivingEntity owner) {
        if (owner == null) {
            this.setOwnerUUID(null);
        }
        else {
            UUID uuid = owner.getUuid();
            this.setOwnerUUID(uuid);
        }
    }

    public boolean isOwned ();
}
