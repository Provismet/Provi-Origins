package com.provismet.datagen.proviorigins;

import com.provismet.lilylib.datagen.tag.LilyTagProviders;
import com.provismet.proviorigins.content.PODamageTypes;
import com.provismet.proviorigins.utility.tags.PODamageTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;

import java.util.concurrent.CompletableFuture;

public class DamageTypeTagGenerator extends LilyTagProviders.LilyDamageTypeTagProvider {
    public DamageTypeTagGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure (RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(PODamageTypeTags.ALWAYS_BLOCK)
            .add(PODamageTypes.KRAKEN_SOUL_STEAL.getKey());

        getOrCreateTagBuilder(PODamageTypeTags.DISABLES_SHIELDS)
            .add(PODamageTypes.KRAKEN_SOUL_STEAL.getKey());

        getOrCreateTagBuilder(DamageTypeTags.AVOIDS_GUARDIAN_THORNS)
            .add(PODamageTypes.CRYSTAL_BEAM.getKey());

        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
            .add(PODamageTypes.COMPOST.getKey())
            .add(PODamageTypes.BAD_DIMENSION.getKey())
            .add(PODamageTypes.VOID_CORRUPTION.getKey())
            .add(PODamageTypes.FRESHWATER.getKey())
            .add(PODamageTypes.KRAKEN_SOUL_STEAL.getKey());

        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_EFFECTS)
            .add(PODamageTypes.COMPOST.getKey());

        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS)
            .add(PODamageTypes.COMPOST.getKey())
            .add(PODamageTypes.CRYSTAL_BEAM.getKey());

        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_RESISTANCE)
            .add(PODamageTypes.COMPOST.getKey());

        getOrCreateTagBuilder(DamageTypeTags.IS_PROJECTILE)
            .add(PODamageTypes.CRYSTAL_BEAM.getKey())
            .add(PODamageTypes.KRAKEN_SOUL_STEAL.getKey());
    }
}
