package com.codex.base.utils

import com.codex.base.enums.SortOrder
import com.codex.base.shared.PagedContent
import com.codex.base.shared.Spec
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.stream.Collectors

inline fun <reified T : PanacheEntityBase, E : Any> PanacheRepositoryBase<T, E>.search(spec: Spec): PanacheQuery<T> {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    logger.info("Spec:: {}", spec)

    val page: Page = Page.of(spec.page!!, spec.size!!)

    var sort = Sort.ascending(spec.sortBy)

    if (spec.sortOrder == SortOrder.DESC) sort = Sort.descending(spec.sortBy)

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

inline fun <S : Any, reified D> PanacheQuery<S>.toPagedContent(noinline mapFunction: (S) -> D): PagedContent<D> {

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

