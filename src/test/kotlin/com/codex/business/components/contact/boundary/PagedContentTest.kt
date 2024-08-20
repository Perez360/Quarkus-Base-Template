//package com.codex.business.components.contact.boundary
//
//import com.codex.base.utils.Mapper
//import com.codex.base.utils.toPagedContent
//import com.codex.business.components.contact.dto.ContactDTO
//import com.codex.business.components.contact.repo.Contact
//import com.codex.business.mockedContact
//import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
//import io.quarkus.panache.common.Page
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.Mockito.mock
//import org.mockito.Mockito.`when`
//import org.mockito.junit.jupiter.MockitoExtension
//
//@ExtendWith(MockitoExtension::class)
//class PagedContentTest {
//
//    @Test
//    fun `test toPagedContent`() {
//        // Arrange
//        val mockQuery = mock<PanacheQuery<Contact>>()
//        val entities = listOf(mockedContact())
//
//        `when`(mockQuery.page()).thenReturn(Page.of(0, 10))
//        `when`(mockQuery.count()).thenReturn(3L)
//        `when`(mockQuery.pageCount()).thenReturn(1)
//        `when`(mockQuery.hasNextPage()).thenReturn(false)
//        `when`(mockQuery.hasPreviousPage()).thenReturn(false)
//        `when`(mockQuery.list()).thenReturn(entities)
//
//        // Act
//        val pagedContent = mockQuery.toPagedContent<Contact, ContactDTO>(Mapper::convert)
//
//        // Assert
//        assertEquals(false, pagedContent.hasNextPage)
//        assertEquals(false, pagedContent.hasPreviousPage)
//        assertEquals(true, pagedContent.isFirst)
//        assertEquals(true, pagedContent.isLast)
////        assertEquals(DTO(1), pagedContent.data[0])
////        assertEquals(DTO(2), pagedContent.data[1])
////        assertEquals(DTO(3), pagedContent.data[2])
//    }
//}
