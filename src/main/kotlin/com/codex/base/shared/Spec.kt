package com.codex.base.shared

import com.codex.base.enums.Operation
import com.codex.base.enums.SortOrder
import io.quarkus.panache.common.Parameters
import jakarta.ws.rs.DefaultValue
import jakarta.ws.rs.QueryParam

/*
* Use <var> keyword to define query params
*/
abstract class Spec {

    @DefaultValue(value = "0")
    @QueryParam(value = "page")
    var page: Int = 0

    @DefaultValue(value = "50")
    @QueryParam(value = "size")
    var size: Int = 50

    @QueryParam(value = "sortBy")
    open var sortBy: String? = null

    @DefaultValue(value = "ASC")
    @QueryParam(value = "sortOrder")
    var sortOrder: SortOrder? = SortOrder.DESC

    @DefaultValue(value = "AND")
    @QueryParam(value = "operation")
    var operation: Operation? = Operation.AND

    abstract fun toParameters(): Parameters

    abstract fun queryDefinition(entry: Map.Entry<String, Any?>): String

    override fun toString(): String {
        return "Spec(" +
                "page=$page, " +
                "size=$size, " +
                "sortBy=$sortBy, " +
                "sortOrder=$sortOrder, " +
                "operation=$operation" +
                ")"
    }
}
