package com.codex.base.utils.serializers;

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

class GenericSerializer<T> : JsonSerializer<T>() {
    @Override
    override fun serialize(value: T, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject();
        gen.writeFieldName("type");
        gen.writeString(value!!::class.simpleName);

        gen.writeFieldName("value");
        gen.writeObject(value);

        gen.writeEndObject();
    }
}
