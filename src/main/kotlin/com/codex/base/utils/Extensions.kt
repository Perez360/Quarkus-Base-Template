package com.codex.base.utils

import com.codex.base.enums.SortOrder
import com.codex.base.shared.PagedContent
import com.codex.base.shared.Spec
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import io.quarkus.logging.Log
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.stream.Collectors

interface CustomPanacheRepositoryBase<Entity : Any, Id : Any> : PanacheRepositoryBase<Entity, Id> {
    fun search(spec: Spec): PanacheQuery<Entity> {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)

        logger.info("Spec:: {}", spec)

        val page: Page = Page.of(spec.page!!, spec.size!!)

        val sort = if (spec.sortOrder == SortOrder.DESC)
            Sort.descending(spec.sortBy) else Sort.ascending(spec.sortBy)

        val finalQueryParams = spec.toParameters().map()
            .filterValues(Objects::nonNull)

        logger.info("Removed null queryParams ::: $finalQueryParams")

        if (finalQueryParams.isEmpty()) return this.findAll(sort).page(page)

        val query = finalQueryParams.entries
            .stream()
            .map(spec::queryDefinition)
            .collect(Collectors.joining(" ${spec.operator} "))

        logger.info("query ::: $query")
        logger.info("Final query params ::: $finalQueryParams")

        return this.find(query, sort, finalQueryParams).page(page)
    }

}

inline fun <Entity : Any, reified DTO> PanacheQuery<Entity>.toPagedContent(noinline mapFunction: (Entity) -> DTO): PagedContent<DTO> {

    return PagedContent(
        page = page().index,
        size = page().size,
        totalElements = count(),
        totalPages = pageCount(),
        hasNextPage = hasNextPage(),
        hasPreviousPage = hasPreviousPage(),
        isFirst = !hasPreviousPage(),
        isLast = !hasNextPage(),
        data = list().map(mapFunction)
    )
}



