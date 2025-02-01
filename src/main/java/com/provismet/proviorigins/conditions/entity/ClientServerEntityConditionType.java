package com.provismet.proviorigins.conditions.entity;

import com.provismet.proviorigins.registries.POEntityConditionTypes;
import com.provismet.proviorigins.utility.constants.FieldNames;

import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.context.EntityConditionContext;
import io.github.apace100.apoli.condition.type.EntityConditionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import org.jetbrains.annotations.NotNull;

public class ClientServerEntityConditionType extends EntityConditionType {
    private final boolean server;
    private final boolean client;

    public static final TypedDataObjectFactory<ClientServerEntityConditionType> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add(FieldNames.SERVER, SerializableDataTypes.BOOLEAN, true)
            .add(FieldNames.CLIENT, SerializableDataTypes.BOOLEAN, true),
        data -> new ClientServerEntityConditionType(
            data.getBoolean(FieldNames.SERVER),
            data.getBoolean(FieldNames.CLIENT)
        ),
        (conditionType, data) -> data.instance()
            .set(FieldNames.SERVER, conditionType.server)
            .set(FieldNames.CLIENT, conditionType.client)
    );

    public ClientServerEntityConditionType (boolean server, boolean client) {
        this.server = server;
        this.client = client;
    }

    @Override
    public boolean test (EntityConditionContext context) {
        if (context.entity().getWorld().isClient) return this.client;
        return this.server;
    }

    @Override
    public @NotNull ConditionConfiguration<ClientServerEntityConditionType> getConfig () {
        return POEntityConditionTypes.CLIENT_SERVER;
    }
}
