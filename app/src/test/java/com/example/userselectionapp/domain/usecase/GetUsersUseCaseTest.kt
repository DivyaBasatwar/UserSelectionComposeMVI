package com.example.userselectionapp.domain.usecase

import com.example.userselectionapp.domain.model.User
import com.example.userselectionapp.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest {

    private val repository: UserRepository = mockk()
    private lateinit var useCase: GetUsersUseCase

    @Before
    fun setUp() {
        useCase = GetUsersUseCase(repository)
    }

    @Test
    fun `invoke returns users from repository`() = runTest {
        //Given
        val fakeUsers = flowOf(listOf(
            User(1, "Divya", "divya@gmail.com"),
            User(2, "Kashi", "kashi@gmail.com")
        ))

        coEvery { repository.getUsers() } returns fakeUsers

        //when
        val result = useCase()

        //then
        assertEquals(fakeUsers, result)
    }

}