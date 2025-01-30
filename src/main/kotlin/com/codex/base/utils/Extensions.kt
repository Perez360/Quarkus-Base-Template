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

    logger.info("Received Spec: {}", spec)

    if (spec.page < 1) {
        logger.info("Spec page is less than 1, setting page to 0")
        spec.page = 0
    } else {
        logger.info("Decrementing Spec page by 1")
        spec.page -= 1
    }

    logger.info("Resolved spec after page adjustments: $spec")

    val page: Page = Page.of(spec.page, spec.size)
    logger.info("Page object created: page number = ${spec.page}, page size = ${spec.size}")

    val sort = if (spec.sortOrder == SortOrder.DESC) Sort.descending(spec.sortBy)
    else Sort.ascending(spec.sortBy)

    logger.info("Sort order is ${spec.sortOrder}, sorting by: ${spec.sortBy}")

    val finalQueryParams = spec.toParameters().map()
        .filterValues(Objects::nonNull)

    logger.info("Query parameters after removing nulls: $finalQueryParams")

    if (finalQueryParams.isEmpty()) {
        logger.info("No query parameters, returning findAll with sort: ${spec.sortBy}:${spec.sortOrder} and page: $page")
        return this.findAll(sort).page(page)
    }

    logger.info("Removed null queryParams ::: $finalQueryParams")

    if (finalQueryParams.isEmpty()) {
        logger.info("No query parameters, returning findAll with sort: ${spec.sortBy}:${spec.sortOrder}, page: ${page.index} and size: ${page.size}")
        return this.findAll(sort).page(page)
    }

    val query = finalQueryParams.entries
        .stream()
        .map(spec::queryDefinition)
        .collect(Collectors.joining(" ${spec.operation} "))

    logger.info("Constructed query string: $query")
    logger.info("Final query params: $finalQueryParams")

    return this.find(query, sort, finalQueryParams).page(page)
}


fun <Entity : Any, Dto> PanacheQuery<Entity>.toPagedContent(mapFunc: (Entity) -> Dto): PagedContent<Dto> {

    return PagedContent(
        page = page().index,
        size = page().size,
        totalElements = count(),
        totalPages = pageCount(),
        hasNextPage = hasNextPage(),
        hasPreviousPage = hasPreviousPage(),
        isFirst = !hasPreviousPage(),
        isLast = !hasNextPage(),
        data = list().map(mapFunc)
    )
}



