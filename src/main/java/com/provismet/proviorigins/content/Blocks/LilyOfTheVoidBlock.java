package com.provismet.proviorigins.content.blocks;

import com.provismet.proviorigins.content.DamageTypes;
import com.provismet.proviorigins.content.registries.POParticles;
import com.provismet.proviorigins.content.registries.POStatusEffects;

import com.provismet.proviorigins.utility.tags.POBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class LilyOfTheVoidBlock extends FlowerBlock {
    public LilyOfTheVoidBlock (Settings settings) {
        super(POStatusEffects.VOID_CORRUPTION, 8, settings);
    }
    
    @Override
    protected boolean canPlantOnTop (BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(POBlockTags.VOID_PLANTABLE);
    }

    @Override
    public void randomDisplayTick (BlockState state, World world, BlockPos pos, Random random) {
        VoxelShape voxelShape = this.getOutlineShape(state, world, pos, ShapeContext.absent());
        Vec3d vec3d = voxelShape.getBoundingBox().getCenter();
        double x = (double)pos.getX() + vec3d.x;
        double z = (double)pos.getZ() + vec3d.z;
        
        if (random.nextDouble() < 0.3) {

            world.addParticle(POParticles.LILY_PETAL,
                x + random.nextDouble() / 5.0,
                (double)pos.getY() + (0.5 - random.nextDouble()),
                z + random.nextDouble() / 5.0,
                (random.nextDouble() - 0.5) / 16.0,
                (random.nextDouble() - 0.5) / 16.0,
                (random.nextDouble() - 0.5) / 16.0
            );
        }
    }

    @Override
    public void onEntityCollision (BlockState state, World world, BlockPos pos, Entity entity) {
        if (world.isClient || world.getDifficulty() == Difficulty.PEACEFUL) return;

        if (entity instanceof LivingEntity living && !living.isInvulnerableTo(living.getDamageSources().create(DamageTypes.VOID_CORRUPTION))) {
            living.addStatusEffect(new StatusEffectInstance(POStatusEffects.VOID_CORRUPTION, 50));
        }
    }
}
