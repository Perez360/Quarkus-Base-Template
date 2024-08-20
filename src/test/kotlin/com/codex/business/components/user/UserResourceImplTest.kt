package com.codex.business.components.user//package com.codex.business.components.user
//
//import com.codex.base.utils.keycloak.AccessTokenProvider
//import com.codex.business.components.user.boundary.http.UserResource
//import com.codex.business.components.user.dto.AddUserDTO
//import com.codex.business.components.user.dto.UserDTO
//import com.codex.business.components.user.repo.User
//import com.codex.business.components.user.service.UserService
//import io.quarkus.test.InjectMock
//import io.quarkus.test.junit.QuarkusTest
//import org.assertj.core.api.Assertions
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.mockito.junit.jupiter.MockitoExtension
//
//@QuarkusTest
//@ExtendWith(MockitoExtension::class)
//class UserResourceImplTest : AccessTokenProvider() {
//
//    @InjectMock
//    private lateinit var userService: UserService
//
//    @Mock
//    private lateinit var userResource: UserResource
//
//    @Test
//    fun testAddUser() {
//        // Arrange
//        val addUserDto = AddUserDTO()
//        // Set properties of addUserDto as needed
//        val user = User() // Assuming User has a constructor or builder
//        // Set properties of user as needed
//        val userDto = UserDTO() // Assuming UserDTO has a constructor or builder
//        // Set properties of userDto as needed
//        Mockito.`when`(userService.add(addUserDto)).thenReturn(user)
//
//        // Act
//        val result = userResource.addUser(addUserDto)
//
//        // Assert
//        Assertions.assertThat(result).isNotNull
//        Mockito.verify(userService, Mockito.atMostOnce()).add(addUserDto)
//    }
//}