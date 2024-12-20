package com.provismet.datagen.proviorigins.provider;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.origins.badge.Badge;
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

/**
 * Data Generator for Origins/Apoli powers.
 *
 * <p><b>This generator is a work in progress! It does not yet support {@code origins:multiple} and Apoli is still in alpha! </b></p>
 */
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

            for (Map.Entry<Identifier, PowerContainer> entry : collector.mappedPowers.entrySet()) {
                futures.add(DataProvider.writeToPath(
                    writer,
                    entry.getValue().constructJSON(wrapperLookup),
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
        private final Map<Identifier, PowerContainer> mappedPowers = new HashMap<>();

        /**
         * Adds an instance of a PowerType to the collector, the identifier is used to determine the path.
         *
         * @param id Identifier for the power.
         * @param powerType The power to write.
         * @return This collector.
         */
        public PowerCollector add (Identifier id, PowerType powerType) {
            return this.add(id, powerType, false, List.of());
        }

        public PowerCollector add (Identifier id, PowerType powerType, List<Badge> badges) {
            return this.add(id, powerType, false, badges);
        }

        public PowerCollector add (Identifier id, PowerType powerType, boolean hidden) {
            return this.add(id, powerType, hidden, List.of());
        }

        public PowerCollector add (Identifier id, PowerType powerType, boolean hidden, List<Badge> badges) {
            if (!id.getPath().startsWith("powers/")) id = id.withPrefixedPath("powers/");
            if (!id.getPath().endsWith(".json")) id = id.withSuffixedPath(".json");

            this.mappedPowers.put(id, new PowerContainer(powerType, hidden, badges));
            return this;
        }
    }

    private record PowerContainer (PowerType powerType, boolean hidden, List<Badge> badges) {
        public JsonElement constructJSON (RegistryWrapper.WrapperLookup wrapperLookup) {
            JsonObject json = new JsonObject();

            if (this.hidden) json.addProperty("hidden", true);

            json.addProperty("type", this.powerType.getConfig().id().toString());

            if (!this.badges.isEmpty()) {
                JsonArray badgeArray = new JsonArray();

                for (Badge badge : badges) {
                    JsonObject badgeJson = new JsonObject();
                    badgeJson.addProperty("type", badge.getBadgeFactory().id().toString());

                    SerializableData.Instance badgeInstance = badge.getBadgeFactory().toData(badge);
                    badgeArray.add(
                        badgeInstance.serializableData()
                            .encode(
                                badgeInstance,
                                wrapperLookup.getOps(JsonOps.INSTANCE),
                                JsonOps.INSTANCE.mapBuilder()
                            )
                            .build(badgeJson)
                            .getOrThrow()
                    );
                }

                json.add("badges", badgeArray);
            }

            TypedDataObjectFactory<PowerType> factory = (TypedDataObjectFactory<PowerType>)this.powerType.getConfig().dataFactory();
            SerializableData.Instance dataInstance = factory.toData(this.powerType);

            return dataInstance.serializableData()
                .encode(
                    dataInstance,
                    wrapperLookup.getOps(JsonOps.INSTANCE),
                    JsonOps.INSTANCE.mapBuilder()
                )
                .build(json)
                .getOrThrow();
        }
    }
}
