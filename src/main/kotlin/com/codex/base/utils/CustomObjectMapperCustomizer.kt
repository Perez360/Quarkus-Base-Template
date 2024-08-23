package com.codex.base.utils

import com.codex.base.utils.serializers.GenericSerializer
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import io.quarkus.jackson.ObjectMapperCustomizer
import jakarta.inject.Singleton


@Singleton
class CustomObjectMapperCustomizer : ObjectMapperCustomizer {

    override fun customize(mapper: ObjectMapper) {
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
            .disable(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .registerModules(SimpleModule().apply { addSerializer(Any::class.java, GenericSerializer()) })
            .findAndRegisterModules()
    }
}