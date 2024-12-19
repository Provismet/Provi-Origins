package com.provismet.datagen.proviorigins;

import com.provismet.lilylib.datagen.provider.LilyDamageTypeProvider;
import com.provismet.proviorigins.content.PODamageTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class DamageTypeGenerator extends LilyDamageTypeProvider {
    protected DamageTypeGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void generate (RegistryWrapper.WrapperLookup registries, DamageConsumer consumer) {
        consumer.add(PODamageTypes.BAD_DIMENSION);
        consumer.add(PODamageTypes.COMPOST);
        consumer.add(PODamageTypes.CRYSTAL_BEAM);
        consumer.add(PODamageTypes.FRESHWATER);
        consumer.add(PODamageTypes.KRAKEN_SOUL_STEAL);
        consumer.add(PODamageTypes.VOID_CORRUPTION);
    }
}
