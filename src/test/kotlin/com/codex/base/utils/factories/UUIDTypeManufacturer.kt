package com.codex.base.utils.factories
//
//import uk.co.jemos.podam.api.AttributeMetadata
//import uk.co.jemos.podam.api.DataProviderStrategy
//import uk.co.jemos.podam.common.ManufacturingContext
//import uk.co.jemos.podam.typeManufacturers.TypeManufacturer
//import java.lang.reflect.Type
//import java.util.*
//
///**
// * Used to generate date ahead of today
// * */
//
//class UUIDTypeManufacturer : TypeManufacturer<UUID> {
//    override fun getType(p0: DataProviderStrategy?, p1: AttributeMetadata?, p2: MutableMap<String, Type>?): UUID {
//        return UUID.randomUUID()
//    }
//}