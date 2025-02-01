package com.provismet.datagen.proviorigins.provider;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.power.type.meta.MultiplePowerType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.origins.badge.Badge;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Data Generator for Origins/Apoli powers.
 *
 * <p><b>This generator should be considered experimental. It works(tm), but is not perfect.</b></p>
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

    private static JsonElement createJSON (SerializableData.Instance data, RegistryWrapper.WrapperLookup lookup, JsonObject originalElement) {
        return data.serializableData().encode(data, lookup.getOps(JsonOps.INSTANCE), JsonOps.INSTANCE.mapBuilder()).build(originalElement).getOrThrow();
    }

    public static class PowerCollector {
        private final Map<Identifier, PowerContainer> mappedPowers = new HashMap<>();

        /**
         * Adds an instance of a PowerType to the collector, the identifier is used to determine the path.
         *
         * @param id Identifier for the power.
         * @param powerType The power to write.
         * @return This collector.
         */
        public PowerCollector add (Identifier id, PowerType powerType) {
            return this.add(id, powerType, false, List.of(), null, null);
        }

        public PowerCollector add (Identifier id, PowerType powerType, List<Badge> badges) {
            return this.add(id, powerType, false, badges, null, null);
        }

        public PowerCollector add (Identifier id, PowerType powerType, boolean hidden) {
            return this.add(id, powerType, hidden, List.of(), null, null);
        }

        public PowerCollector add (Identifier id, PowerType powerType, String name, String description) {
            return this.add(id, powerType, false, List.of(), name, description);
        }

        public PowerCollector add (Identifier id, PowerType powerType, List<Badge> badges, String name, String description) {
            return this.add(id, powerType, false, badges, name, description);
        }

        public PowerCollector add (Identifier id, PowerType powerType, boolean hidden, List<Badge> badges, String name, String description) {
            return this.add(id, new StandardPower(powerType), hidden, badges, name, description);
        }

        public PowerCollector add (Identifier id, PowerJson jsonProvider) {
            return this.add(id, jsonProvider, false, List.of(), null, null);
        }

        public PowerCollector add (Identifier id, PowerJson jsonProvider, List<Badge> badges) {
            return this.add(id, jsonProvider, false, badges, null, null);
        }

        public PowerCollector add (Identifier id, PowerJson jsonProvider, boolean hidden) {
            return this.add(id, jsonProvider, hidden, List.of(), null, null);
        }

        public PowerCollector add (Identifier id, PowerJson jsonProvider, String name, String description) {
            return this.add(id, jsonProvider, false, List.of(), name, description);
        }

        public PowerCollector add (Identifier id, PowerJson jsonProvider, List<Badge> badges, String name, String description) {
            return this.add(id, jsonProvider, false, badges, name, description);
        }

        public PowerCollector add (Identifier id, PowerJson jsonProvider, boolean hidden, List<Badge> badges, String name, String description) {
            if (!id.getPath().startsWith("powers/")) id = id.withPrefixedPath("powers/");
            if (!id.getPath().endsWith(".json")) id = id.withSuffixedPath(".json");

            this.mappedPowers.put(id, new PowerContainer(jsonProvider, hidden, badges, name, description));
            return this;
        }
    }

    public interface PowerJson {
        JsonElement build (RegistryWrapper.WrapperLookup wrapperLookup, JsonObject baseElement);
        PowerType getType ();
    }

    public record StandardPower (PowerType powerType) implements PowerJson {
        @Override
        public JsonElement build (RegistryWrapper.WrapperLookup wrapperLookup, JsonObject baseElement) {
            TypedDataObjectFactory<PowerType> factory = (TypedDataObjectFactory<PowerType>)this.powerType.getConfig().dataFactory();
            SerializableData.Instance dataInstance = factory.toData(this.powerType);
            return POPowerProvider.createJSON(dataInstance, wrapperLookup, baseElement);
        }

        @Override
        public PowerType getType () {
            return this.powerType;
        }
    }

    public static class MultiplePowerJsonBuilder implements PowerJson {
        private final List<Pair<String, JsonObject>> json = new ArrayList<>();
        private final List<Pair<String, PowerType>> powers = new ArrayList<>();
        private final PowerType multipleType = new MultiplePowerType();

        @Override
        public JsonElement build (RegistryWrapper.WrapperLookup wrapperLookup, JsonObject baseElement) {
            for (Pair<String, PowerType> power : this.powers) {
                String name = power.getLeft();
                PowerType type = power.getRight();

                TypedDataObjectFactory<PowerType> factory = (TypedDataObjectFactory<PowerType>)type.getConfig().dataFactory();
                SerializableData.Instance dataInstance = factory.toData(type);
                JsonObject newPower = new JsonObject();
                newPower.addProperty("type", type.getConfig().id().toString());
                baseElement.add(name, POPowerProvider.createJSON(dataInstance, wrapperLookup, newPower));
            }
            for (Pair<String, JsonObject> jsonPair : this.json) {
                String name = jsonPair.getLeft();
                JsonObject jsonObject = jsonPair.getRight();
                baseElement.add(name, jsonObject);
            }
            return baseElement;
        }

        @Override
        public PowerType getType () {
            return this.multipleType;
        }

        public MultiplePowerJsonBuilder add (String name, PowerType powerType) {
            this.powers.add(new Pair<>(name, powerType));
            return this;
        }

        public MultiplePowerJsonBuilder add (String name, JsonObject json) {
            this.json.add(new Pair<>(name, json));
            return this;
        }
    }

    private record PowerContainer (PowerJson power, boolean hidden, List<Badge> badges, String name, String description) {
        public JsonElement constructJSON (RegistryWrapper.WrapperLookup wrapperLookup) {
            JsonObject json = new JsonObject();

            if (this.hidden) json.addProperty("hidden", true);
            if (this.name != null) {
                JsonObject nameObject = new JsonObject();
                nameObject.addProperty("translate", this.name);
                json.add("name", nameObject);
            }
            if (this.description != null) {
                JsonObject descriptionObject = new JsonObject();
                descriptionObject.addProperty("translate", this.description);
                json.add("description", descriptionObject);
            }

            json.addProperty("type", this.power.getType().getConfig().id().toString());

            if (!this.badges.isEmpty()) {
                JsonArray badgeArray = new JsonArray();

                for (Badge badge : badges) {
                    JsonObject badgeJson = new JsonObject();
                    badgeJson.addProperty("type", badge.getBadgeFactory().id().toString());

                    SerializableData.Instance badgeInstance = badge.getBadgeFactory().toData(badge);
                    badgeArray.add(POPowerProvider.createJSON(badgeInstance, wrapperLookup, badgeJson));
                }

                json.add("badges", badgeArray);
            }

            return this.power.build(wrapperLookup, json);
        }
    }
}
