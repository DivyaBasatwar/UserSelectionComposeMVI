package com.example.userselectionapp.presentation.userlist

import com.example.userselectionapp.domain.model.User
import com.example.userselectionapp.domain.usecase.GetUsersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserListViewModelTest {

    private val getUsersUseCase: GetUsersUseCase = mockk()
    private lateinit var viewModel: UserListViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `users loaded on init`() = runTest {
        //Given
        val fakeUsers = flowOf(listOf(
            User(1, "Divya", "divya@gmail.com"),
            User(2, "Kashi", "kashi@gmail.com")
        ))

        coEvery { getUsersUseCase() } returns fakeUsers

        //when
        viewModel = UserListViewModel(getUsersUseCase)

        //then
        assertEquals(2, viewModel.uiState.value.users.size)
    }

    @Test
    fun `toggleUserSelection selects and deselects user`() = runTest {
        //Given
        val users = flowOf(listOf(
            User(1, "Divya", "divya@gmail.com")
        )
        )

        coEvery { getUsersUseCase() } returns users

        viewModel = UserListViewModel(getUsersUseCase)

        // When - Select
        viewModel.toggleUserSelection(1)

        val selectedUser =
            viewModel.uiState.value.users.first { it.id == 1 }
        assertTrue(selectedUser.isSelected)

        // Deselect
        viewModel.toggleUserSelection(1)
        val deselectedUser =
            viewModel.uiState.value.users.first { it.id == 1 }
        assertFalse(deselectedUser.isSelected)
    }

}