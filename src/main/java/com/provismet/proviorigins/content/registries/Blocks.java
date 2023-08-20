package com.provismet.proviorigins.content.registries;

import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.content.Blocks.LilyOfTheVoidBlock;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public class Blocks {
    public static final LilyOfTheVoidBlock LILY_OF_THE_VOID = new LilyOfTheVoidBlock(FabricBlockSettings.create().breakInstantly().noCollision().mapColor(MapColor.DARK_GREEN).sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY));

    public static void register (Block block, String path) {
        Registry.register(Registries.BLOCK, ProviOriginsMain.identifier(path), block);
    }

    public static void register () {
        register(LILY_OF_THE_VOID, "lily_of_the_void");
    }
}
