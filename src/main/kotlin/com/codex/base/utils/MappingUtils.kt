package com.codex.base.utils

import org.modelmapper.ModelMapper
import org.modelmapper.config.Configuration
import org.modelmapper.convention.MatchingStrategies


class MapperDto : ModelMapper() {
    init {
        configuration.setSkipNullEnabled(true)
        configuration.setMatchingStrategy(MatchingStrategies.LOOSE)
        configuration.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
        configuration.setFieldMatchingEnabled(true)
        configuration.setSkipNullEnabled(true)
    }
}

object Mapper {
    val mapper = MapperDto()

    inline fun <S, reified T> convert(source: S): T = mapper.map(source, T::class.java)
}