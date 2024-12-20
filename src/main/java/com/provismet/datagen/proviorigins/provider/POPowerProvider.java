package com.provismet.datagen.proviorigins.provider;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.calio.data.SerializableData;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class POPowerProvider implements DataProvider {
    protected final FabricDataOutput output;
    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;

    public POPowerProvider (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        this.output = output;
        this.registryLookup = registryLookup;
    }

    @Override
    public CompletableFuture<?> run (DataWriter writer) {
        return this.registryLookup.thenCompose(wrapperLookup -> {
            PowerCollector collector = new PowerCollector();
            this.generate(collector);

            List<CompletableFuture<?>> futures = new ArrayList<>();

            for (Map.Entry<Identifier, PowerType> entry : collector.mappedPowers.entrySet()) {
                TypedDataObjectFactory<PowerType> factory = (TypedDataObjectFactory<PowerType>)entry.getValue().getConfig().dataFactory();
                SerializableData.Instance dataInstance = factory.toData(entry.getValue());

                JsonObject baseObject = new JsonObject();
                baseObject.addProperty("type", entry.getValue().getConfig().id().toString());

                futures.add(DataProvider.writeToPath(
                    writer,
                    dataInstance.serializableData().encode(
                        dataInstance,
                        wrapperLookup.getOps(JsonOps.INSTANCE),
                        JsonOps.INSTANCE.mapBuilder()
                    ).build(baseObject).getOrThrow(),
                    this.getFilepath(entry.getKey())
                ));
            }

            return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
        });
    }

    protected abstract void generate (PowerCollector collector);

    @Override
    public String getName () {
        return "Provi Origins Power Generator";
    }

    private Path getFilepath (Identifier path) {
        return this.output.resolvePath(DataOutput.OutputType.DATA_PACK).resolve(path.getNamespace()).resolve(path.getPath());
    }

    protected static class PowerCollector {
        private final Map<Identifier, PowerType> mappedPowers = new HashMap<>();

        /**
         * Adds an instance of a PowerType to the collector, the identifier is used to determine the path.
         *
         * @param id Identifier for the power.
         * @param powerType The power to write.
         * @return This collector.
         */
        public PowerCollector add (Identifier id, PowerType powerType) {
            if (!id.getPath().startsWith("powers/")) id = id.withPrefixedPath("powers/");
            if (!id.getPath().endsWith(".json")) id = id.withSuffixedPath(".json");

            this.mappedPowers.put(id, powerType);
            return this;
        }
    }
}
