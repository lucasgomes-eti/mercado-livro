package eti.lucasgomes.mercadolivro.extensions

import eti.lucasgomes.mercadolivro.controller.response.PageResponse
import org.springframework.data.domain.Page

fun <T> Page<T>.toPageResponse(): PageResponse<T> {
    return PageResponse(
        items = content,
        totalPages = totalPages,
        totalItems = totalElements,
        currentPage = number,
        lastPage = isLast
    )
}