package com.provismet.proviorigins.content.entities;

import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.extras.ExtraTameable;
import com.provismet.proviorigins.extras.Temporary;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EntityView;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class MinionEntity extends MobEntity implements ExtraTameable, Temporary {
    public static final Identifier TEMPLATE_TEXTURE = ProviOriginsMain.identifier("textures/entity/minion_template.png");

    private static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(MinionEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private static final TrackedData<String> TEXTURE_NAMESPACE = DataTracker.registerData(MinionEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<String> TEXTURE_PATH = DataTracker.registerData(MinionEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Boolean> FOLLOW_OWNER = DataTracker.registerData(MinionEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Float> FOLLOW_OWNER_OFFSET_X = DataTracker.registerData(MinionEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> FOLLOW_OWNER_OFFSET_Y = DataTracker.registerData(MinionEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> FOLLOW_OWNER_OFFSET_Z = DataTracker.registerData(MinionEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> SCALE = DataTracker.registerData(MinionEntity.class, TrackedDataHandlerRegistry.FLOAT);

    protected int maxLifeTime;

    public MinionEntity (EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        this.setCanPickUpLoot(false);
        this.setPersistent();
        this.setCustomNameVisible(false);
        this.maxLifeTime = 1200; // Default value, but will be immediately overridden by SummonMinionAction.
    }

    @Override
    public EntityView method_48926 () {
        return this.getWorld();
    }
    
    @Override
    protected void initDataTracker () {
        super.initDataTracker();
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
        this.dataTracker.startTracking(TEXTURE_NAMESPACE, TEMPLATE_TEXTURE.getNamespace());
        this.dataTracker.startTracking(TEXTURE_PATH, TEMPLATE_TEXTURE.getPath());
        this.dataTracker.startTracking(FOLLOW_OWNER, false);
        this.dataTracker.startTracking(FOLLOW_OWNER_OFFSET_X, 0f);
        this.dataTracker.startTracking(FOLLOW_OWNER_OFFSET_Y, 0f);
        this.dataTracker.startTracking(FOLLOW_OWNER_OFFSET_Z, 0f);
        this.dataTracker.startTracking(SCALE, 1f);
    }

    @Override
    public EntityData initialize (ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt) {
        EntityData data = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);

        if (this.getScoreboardTeam() == null && this.getOwner() != null && this.getOwner().getScoreboardTeam() != null && this.getOwner().getScoreboardTeam() instanceof Team team) {
            this.getServer().getScoreboard().addPlayerToTeam(this.getEntityName(), team);
        }

        return data;
    }

    public static DefaultAttributeContainer.Builder createMinionAttributes () {
        DefaultAttributeContainer.Builder attributes = HostileEntity.createMobAttributes();
        attributes.add(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        attributes.add(EntityAttributes.GENERIC_MAX_HEALTH, 20);
        attributes.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0);
        attributes.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 0);
        return attributes;
    }

    @Override
    public void tick () {
        super.tick();
        if (this.age > this.maxLifeTime && this.maxLifeTime > 0) {
            this.kill();
        }
        else if (this.shouldFollowOwner()) {
            LivingEntity owner = this.getOwner();
            if (owner == null || owner.getWorld().getRegistryKey() != this.getWorld().getRegistryKey()) {
                this.kill();
                return;
            }

            this.setYaw(owner.getHeadYaw());
            this.setHeadYaw(owner.getHeadYaw());
            this.setPitch(owner.getPitch());
            
            Vector3f offsets = this.getFollowOwnerOffset();
            offsets.rotateY(-owner.getHeadYaw() / MathHelper.DEGREES_PER_RADIAN);
            this.setPos(owner.getX() + offsets.x, owner.getY() + offsets.y, owner.getZ() + offsets.z);
        }
    }

    @Nullable
    @Override
    public void setOwnerUUID (UUID uuid) {
        if (uuid == null) this.dataTracker.set(OWNER_UUID, Optional.empty());
        else this.dataTracker.set(OWNER_UUID, Optional.of(uuid));
    }

    @Nullable
    @Override
    public UUID getOwnerUuid () {
        Optional<UUID> uuid = this.dataTracker.get(OWNER_UUID);
        if (uuid.isEmpty()) return null;
        else return uuid.get();
    }

    @Override
    public boolean isOwned () {
        return this.dataTracker.get(OWNER_UUID).isPresent();
    }

    public void setTexture (Identifier resourceLocation) {
        this.dataTracker.set(TEXTURE_NAMESPACE, resourceLocation.getNamespace());
        this.dataTracker.set(TEXTURE_PATH, resourceLocation.getPath());
    }

    public void setTexture (String resourceLocation) {
        try {
            this.setTexture(Identifier.tryParse(resourceLocation));            
        } catch (Exception e) {
            ProviOriginsMain.LOGGER.error("Failed to apply texture " + resourceLocation + " to MinionEntity.", e);
        }
    }

    public Identifier getTexture () {
        return Identifier.of(this.dataTracker.get(TEXTURE_NAMESPACE), this.dataTracker.get(TEXTURE_PATH));
    }

    public void setScale (float amount) {
        this.dataTracker.set(SCALE, amount);
    }

    public float getScale () {
        return this.dataTracker.get(SCALE);
    }

    public void setFollowOwner (boolean shouldFollow) {
        this.dataTracker.set(FOLLOW_OWNER, shouldFollow);
        this.setNoGravity(shouldFollow);
    }

    public boolean shouldFollowOwner () {
        return this.dataTracker.get(FOLLOW_OWNER);
    }

    public void setFollowOwnerOffset (Vec3d offset) {
        this.setFollowOwnerOffset((float)offset.x, (float)offset.y, (float)offset.z);
    }

    public void setFollowOwnerOffset (float x, float y, float z) {
        this.dataTracker.set(FOLLOW_OWNER_OFFSET_X, x);
        this.dataTracker.set(FOLLOW_OWNER_OFFSET_Y, y);
        this.dataTracker.set(FOLLOW_OWNER_OFFSET_Z, z);
    }

    public Vector3f getFollowOwnerOffset () {
        return new Vector3f(
            this.dataTracker.get(FOLLOW_OWNER_OFFSET_X),
            this.dataTracker.get(FOLLOW_OWNER_OFFSET_Y),
            this.dataTracker.get(FOLLOW_OWNER_OFFSET_Z)
        );
    }

    @Override
    public void onTrackedDataSet (TrackedData<?> data) {
        if (SCALE.equals(data)) this.calculateDimensions();
        super.onTrackedDataSet(data);
    }

    @Override
    public EntityDimensions getDimensions (EntityPose pose) {
        return super.getDimensions(pose).scaled(this.getScale());
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.5f * dimensions.height;
    }

    @Override
    public void readCustomDataFromNbt (NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setPersistent();
        this.setCanPickUpLoot(false);
        this.setCustomNameVisible(false);

        UUID uuid;
        if (nbt.containsUuid("Owner")) {
            uuid = nbt.getUuid("Owner");
        }
        else {
            String ownerName = nbt.getString("Owner");
            uuid = ServerConfigHandler.getPlayerUuidByName(this.getServer(), ownerName);
        }

        if (uuid != null) this.setOwnerUUID(uuid);

        if (nbt.contains("FollowOwner")) {
            this.setFollowOwner(nbt.getBoolean("FollowOwner"));
            
            float xOffset = 0f;
            float yOffset = 0f;
            float zOffset = 0f;

            if (nbt.contains("FollowOffset")) {
                NbtCompound offset = nbt.getCompound("FollowOffset");
                xOffset = offset.getFloat("X");
                yOffset = offset.getFloat("Y");
                zOffset = offset.getFloat("Z");
            }

            this.setFollowOwnerOffset(xOffset, yOffset, zOffset);
        }
        else this.setFollowOwner(false);

        if (nbt.contains("Texture")) {
            NbtCompound compound = nbt.getCompound("Texture");
            this.setTexture(Identifier.of(compound.getString("Namespace"), compound.getString("Path")));
        }

        if (nbt.contains("Scale")) this.setScale(nbt.getFloat("Scale"));
        if (nbt.contains("MaxLifetime")) this.setMaxLifetime(nbt.getInt("MaxLifetime"));
    }

    @Override
    public void writeCustomDataToNbt (NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        if (this.isOwned()) nbt.putUuid("Owner", this.getOwnerUuid());

        nbt.putBoolean("FollowOwner", this.shouldFollowOwner());
        if (this.shouldFollowOwner()) {
            NbtCompound offset = new NbtCompound();
            Vector3f offsetVector = this.getFollowOwnerOffset();
            offset.putFloat("X", offsetVector.x);
            offset.putFloat("Y", offsetVector.y);
            offset.putFloat("Z", offsetVector.z);

            nbt.put("FollowOffset", offset);
        }

        nbt.putFloat("Scale", this.getScale());
        nbt.putInt("MaxLifetime", this.maxLifeTime);

        NbtCompound texture = new NbtCompound();
        texture.putString("Namespace", this.getTexture().getNamespace());
        texture.putString("Path", this.getTexture().getPath());
        nbt.put("Texture", texture);
    }

    @Override
    public boolean isPushable () {
        return false;
    }

    @Override
    public void takeKnockback (double strength, double x, double z) {
        // Do nothing. Minions should not take knockback.
    }

    @Override
    public void setMaxLifetime (int ticks) {
        this.maxLifeTime = ticks;
    }

    @Override
    public boolean canUsePortals () {
        return false;
    }

    @Override
    public boolean canHit () {
        if (this.shouldFollowOwner()) return false;
        return super.canHit();
    }
}
