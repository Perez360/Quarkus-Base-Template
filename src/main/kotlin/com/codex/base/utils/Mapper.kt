package com.codex.base.utils

import org.modelmapper.ModelMapper
import org.modelmapper.config.Configuration
import org.modelmapper.convention.MatchingStrategies


object Mapper {
    val mapper = ModelMapper().apply {
        configuration.isSkipNullEnabled = true
        configuration.matchingStrategy = MatchingStrategies.LOOSE
        configuration.fieldAccessLevel = Configuration.AccessLevel.PRIVATE
    }

    inline fun <S, reified T> convert(source: S): T = mapper.map(source, T::class.java)
}