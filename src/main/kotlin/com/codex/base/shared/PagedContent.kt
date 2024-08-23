package com.codex.base.shared

data class PagedContent<T>(
    val totalElements: Long = 0,
    val totalPages: Int = 0,
    val page: Int = 0,
    val size: Int = 0,
    val hasNextPage: Boolean = false,
    val hasPreviousPage: Boolean = false,
    val isFirst: Boolean = false,
    val isLast: Boolean = false,
    var data: List<T> = listOf()
)