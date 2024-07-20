package com.provismet.proviorigins.content.entities;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import com.provismet.proviorigins.extras.ExtraTameable;
import com.provismet.proviorigins.extras.Temporary;

import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.ai.goal.BowAttackGoal;
import net.minecraft.entity.ai.goal.CrossbowAttackGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.PersistentProjectileEntity.PickupPermission;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.EntityView;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

/*
 * Clones are "tamed" entities that are summoned by a player, as much as is possible, they have the same appearance of the original player.
 * They are hostile because otherwise I have to implement my own version of BowAttackGoal.
 */
public class CloneEntity extends HostileEntity implements ExtraTameable, CrossbowUser, Temporary {
    private static final double COMBAT_SPEED = 1.35;
    private static final float SHOOTING_RANGE = 32f;

    private boolean canSit;
    private boolean followOwner;
    private boolean canAttack;
    private int maxTicks;

    private final MeleeAttackGoal MELEE_ATTACK = new MeleeAttackGoal(this, COMBAT_SPEED, false);
    private final BowAttackGoal<CloneEntity> RANGED_ATTACK = new BowAttackGoal<CloneEntity>(this, COMBAT_SPEED, 20, SHOOTING_RANGE);
    private final CrossbowAttackGoal<CloneEntity> CROSSBOW_ATTACK = new CrossbowAttackGoal<CloneEntity>(this, COMBAT_SPEED, SHOOTING_RANGE);

    private static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(CloneEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private static final TrackedData<Boolean> SITTING = DataTracker.registerData(CloneEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> CHARGING = DataTracker.registerData(CloneEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public CloneEntity (EntityType<? extends CloneEntity> entityType, World world) {
        super(entityType, world);
        this.updateWeaponGoals();
        this.experiencePoints = 0;
        this.maxTicks = 1200;
    }
    
    @Override
    public EntityData initialize (ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt) {
        EntityData data = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        this.updateWeaponGoals();
        this.setCanPickUpLoot(false);

        if (this.getScoreboardTeam() == null && !(this.getOwner() == null) && this.getOwner().getScoreboardTeam() != null && this.getOwner().getScoreboardTeam() instanceof Team team) {
            this.getServer().getScoreboard().addPlayerToTeam(this.getEntityName(), team);
        }

        return data;
    }

    @Override
    protected void initDataTracker () {
        super.initDataTracker();
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
        this.dataTracker.startTracking(SITTING, false);
        this.dataTracker.startTracking(CHARGING, false);
    }

    @Override
    protected void initGoals () {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new FollowOwnerGoal(this));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 16f));
        this.goalSelector.add(5, new LookAtEntityGoal(this, AnimalEntity.class, 16f));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.targetSelector.add(0, new DefendOwnerGoal(this));
        this.targetSelector.add(1, new FightForOwnerGoal(this));
        this.targetSelector.add(2, new RevengeGoal(this, new Class[0]));
    }

    public static DefaultAttributeContainer.Builder createCloneAttributes () {
        DefaultAttributeContainer.Builder attributes = HostileEntity.createMobAttributes();
        attributes.add(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        attributes.add(EntityAttributes.GENERIC_MAX_HEALTH, 20);
        attributes.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35f);
        attributes.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64);
        return attributes;
    }

    @Override
    protected boolean isDisallowedInPeaceful () {
        return false;
    }

    @Override
    public SoundCategory getSoundCategory () {
        return SoundCategory.PLAYERS;
    }

    @Override
    public boolean canPickupItem (ItemStack stack) {
        return false;
    }
    
    @Override
    public boolean shouldRenderName () {
        return true;
    }

    @Override
    public void tick () {
        super.tick();
        if (this.getOwner() == null || this.getOwner().getWorld().getRegistryKey() != this.getWorld().getRegistryKey() || (this.age > this.maxTicks && this.maxTicks > 0)) {
            this.discard();
        }
        else if (this.age == this.maxTicks - 1) {
            for (int i = 0; i < 20; ++i) {
                double velX = this.random.nextGaussian() * 0.02;
                double velY = this.random.nextGaussian() * 0.02;
                double velZ = this.random.nextGaussian() * 0.02;
                this.getWorld().addParticle(ParticleTypes.POOF, this.getParticleX(1.0), this.getRandomBodyY(), this.getParticleZ(1.0), velX, velY, velZ);
            }
        }
    }

    @Override
    public boolean isOwned () {
        return this.dataTracker.get(OWNER_UUID).isPresent();
    }

    @Nullable
    @Override
    public void setOwnerUUID (UUID uuid) {
        if (uuid == null) this.dataTracker.set(OWNER_UUID, Optional.empty());
        else this.dataTracker.set(OWNER_UUID, Optional.of(uuid));
    }

    @Nullable
    @Override
    public void setOwner (LivingEntity owner) {
        if (!(owner instanceof PlayerEntity)) return;

        ExtraTameable.super.setOwner(owner);
    }

    @Nullable
    @Override
    public UUID getOwnerUuid () {
        Optional<UUID> uuid = this.dataTracker.get(OWNER_UUID);
        if (uuid.isEmpty()) return null;
        else return uuid.get();
    }

    @Nullable
    @Override
    public PlayerEntity getOwner () {
        UUID uuid = this.getOwnerUuid();
        if (uuid == null) return null;
        else {
            return this.getWorld().getPlayerByUuid(uuid);
        }
    }

    @Override
    public void attack (LivingEntity target, float pullProgress) {
        if (this.isHolding(Items.CROSSBOW)) {
            this.shoot(this, 1.6f);
            return;
        }

        ItemStack arrowType = this.getProjectileType(this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW)));
        PersistentProjectileEntity persistentProjectileEntity = ProjectileUtil.createArrowProjectile(this, arrowType, pullProgress);
        persistentProjectileEntity.pickupType = PickupPermission.DISALLOWED;

        double xDirection = target.getX() - this.getX();
        double yDirection = target.getBodyY(0.3333333333333333) - persistentProjectileEntity.getY();
        double zDirection = target.getZ() - this.getZ();

        double g = Math.sqrt(xDirection * xDirection + zDirection * zDirection);
        persistentProjectileEntity.setVelocity(xDirection, yDirection + g * (double)0.2f, zDirection, 1.6f, 14 - this.getWorld().getDifficulty().getId() * 4);

        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f / (this.getWorld().getRandom().nextFloat() * 0.4f + 1.2f) + pullProgress * 0.5f);
        this.getWorld().spawnEntity(persistentProjectileEntity);
    }

    public boolean isCharging () {
        return this.dataTracker.get(CHARGING);
    }

    @Override
    public void setCharging (boolean isCharging) {
        this.dataTracker.set(CHARGING, isCharging);
    }

    @Override
    public void shoot (LivingEntity target, ItemStack crossbow, ProjectileEntity projectile, float multiShotSpray) {
        projectile.setOwner(this.getOwner());
        if (projectile instanceof PersistentProjectileEntity persistent) {
            persistent.pickupType = PickupPermission.DISALLOWED;
        }
        this.shoot(this, target, projectile, multiShotSpray, 1.6f);
    }

    @Override
    public void postShoot () {
        // Do nothing.
    }

    public void updateWeaponGoals () {
        final int WEAPON_GOAL_PRIORITY = 1;

        if (this.getWorld() == null || this.getWorld().isClient()) return;

        this.goalSelector.remove(MELEE_ATTACK);
        this.goalSelector.remove(RANGED_ATTACK);
        this.goalSelector.remove(CROSSBOW_ATTACK);

        if (this.isHolding(Items.BOW)) this.goalSelector.add(WEAPON_GOAL_PRIORITY, RANGED_ATTACK);
        else if (this.isHolding(Items.CROSSBOW)) this.goalSelector.add(WEAPON_GOAL_PRIORITY, CROSSBOW_ATTACK);
        else this.goalSelector.add(WEAPON_GOAL_PRIORITY, MELEE_ATTACK);
    }

    @Override
    public void readCustomDataFromNbt (NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.updateWeaponGoals();

        UUID uuid;
        if (nbt.containsUuid("Owner")) {
            uuid = nbt.getUuid("Owner");
        }
        else {
            String ownerName = nbt.getString("Owner");
            uuid = ServerConfigHandler.getPlayerUuidByName(this.getServer(), ownerName);
        }

        if (uuid != null) this.setOwnerUUID(uuid);
    }

    @Override
    public void writeCustomDataToNbt (NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        if (this.isOwned()) nbt.putUuid("Owner", this.getOwnerUuid());
    }

    @Override
    public ActionResult interactMob (PlayerEntity player, Hand hand) {
        if (player == this.getOwner()) {
            if (this.getWorld().isClient()) return ActionResult.SUCCESS;

            if (this.canSit) {
                this.toggleSitting();
                return ActionResult.success(false);
            }
        }
        return ActionResult.PASS;
    }

    protected PersistentProjectileEntity createArrowProjectile (ItemStack arrow, float damageModifier) {
        PersistentProjectileEntity projectile = ProjectileUtil.createArrowProjectile(this, arrow, damageModifier);
        projectile.pickupType = PickupPermission.DISALLOWED;
        return projectile;
    }

    @Override
    public EntityGroup getGroup() {
        if (this.getOwner() != null) return this.getOwner().getGroup();
        else return super.getGroup();
    }

    @Override
    public boolean canTarget (LivingEntity target) {
        if (this.getOwner() == null || !target.canTakeDamage() || target == this.getOwner() || this.isSitting() || !this.canAttack ||
            (this.getScoreboardTeam() != null && target.getScoreboardTeam() != null && this.getScoreboardTeam().isEqual(target.getScoreboardTeam())))
                return false;
        else if (target instanceof Tameable tameable) {
            if (tameable.getOwner() == this.getOwner()) return false;
        }
        return true;
    }

    public void setCanSit (boolean value) {
        this.canSit = value;
    }

    public void setFollowOwner (boolean value) {
        this.followOwner = value;
    }

    public void setCanAttack (boolean value) {
        this.canAttack = value;
    }

    public boolean isSitting () {
        return this.dataTracker.get(SITTING);
    }

    public void toggleSitting () {
        this.dataTracker.set(SITTING, !this.isSitting());
    }

    public void setSitting (boolean isSat) {
        this.dataTracker.set(SITTING, isSat);
    }

    @Override
    public EntityView method_48926 () {
        return getWorld();
    }

    @Override
    public void setMaxLifetime (int ticks) {
        this.maxTicks = ticks;
    }

    @Override
    public boolean canUsePortals () {
        return false;
    }
    
    protected abstract static class CloneGoal extends Goal {
        protected final CloneEntity clone;
        protected PlayerEntity owner;

        protected CloneGoal (CloneEntity clone) {
            this.clone = clone;
        }

        @Override
        public boolean canStart() {
            if (this.owner == null) owner = this.clone.getOwner();
            return !this.clone.isSitting() && this.owner != null;
        }
    }

    protected abstract static class AssistOwnerGoal extends TrackTargetGoal {
        protected final CloneEntity clone;
        protected PlayerEntity owner;
        protected int timer;

        protected AssistOwnerGoal (CloneEntity clone) {
            super(clone, false);
            this.timer = 0;
            this.clone = clone;
            this.setControls(EnumSet.of(Goal.Control.TARGET));
        }

        @Override
        public boolean canStart () {
            if (owner == null) owner = this.clone.getOwner();
            return owner != null && !this.clone.isSitting() && this.clone.canAttack;
        }
    }

    protected static class DefendOwnerGoal extends AssistOwnerGoal {
        public DefendOwnerGoal (CloneEntity clone) {
            super(clone);
        }

        @Override
        public boolean canStart () {
            if (super.canStart() && this.owner.getAttacker() != null && this.clone.canTarget(this.owner.getAttacker()) && this.timer != this.owner.getLastAttackedTime()) {
                this.target = this.owner.getAttacker();
                this.timer = this.owner.getLastAttackedTime();
                return true;
            }
            return false;
        }
    }

    protected static class FightForOwnerGoal extends AssistOwnerGoal {
        public FightForOwnerGoal (CloneEntity clone) {
            super(clone);
        }

        @Override
        public boolean canStart () {
            if (super.canStart() && this.owner.getAttacking() != null && this.clone.canTarget(this.owner.getAttacking()) && this.timer != this.owner.getLastAttackTime()) {
                this.target = this.owner.getAttacking();
                this.timer = this.owner.getLastAttackTime();
                return true;
            }
            return false;
        }
    }

    protected static class FollowOwnerGoal extends CloneGoal {
        private static final double MAX_DISTANCE = 32;
        private static final double MIN_DISTANCE = 6;

        private final double speed;

        private int tickTimer = 0;

        public FollowOwnerGoal (CloneEntity clone, double speed) {
            super(clone);
            this.speed = speed;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        public FollowOwnerGoal (CloneEntity clone) {
            this(clone, 1.0);
        }

        @Override
        public boolean canStart () {
            return super.canStart() && this.clone.followOwner && this.clone.distanceTo(this.owner) >= MAX_DISTANCE;
        }
        
        @Override
        public void start () {
            this.clone.getNavigation().startMovingTo(this.owner, this.speed);
        }

        @Override
        public void stop () {
            this.clone.getNavigation().stop();
        }

        @Override
        public boolean shouldContinue () {
            return super.canStart() && this.clone.distanceTo(this.owner) >= MIN_DISTANCE;
        }

        @Override
        public void tick () {
            this.clone.getLookControl().lookAt(this.owner, 10f, this.clone.getMaxLookPitchChange());

            if (this.clone.isLeashed() || this.clone.hasVehicle() || --this.tickTimer > 0) return;
            this.tickTimer = this.getTickCount(10);
            this.clone.getNavigation().startMovingTo(this.owner, this.speed);
        }
    }
}
