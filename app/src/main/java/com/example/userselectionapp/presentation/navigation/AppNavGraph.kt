package com.example.userselectionapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.userselectionapp.data.repository.UserRepositoryImpl
import com.example.userselectionapp.data.source.FakeDataSource
import com.example.userselectionapp.domain.usecase.GetUsersUseCase
import com.example.userselectionapp.presentation.userdetail.UserDetailScreen
import com.example.userselectionapp.presentation.userlist.UserListScreen
import com.example.userselectionapp.presentation.userlist.UserListViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "user_list"
    ) {
        composable(Screen.UserList.route) {
            // MANUAL DEPENDENCY CREATION (TEMP) we have replaced all this with DI using hilt
//            val dataSource = FakeDataSource()
//            val repository = UserRepositoryImpl(dataSource)
//            val getUsersUseCase = GetUsersUseCase(repository)
//            val factory = UserListViewModelFactory(getUsersUseCase)
//            val viewModel: UserListViewModel = viewModel(factory = factory)

            val viewModel: UserListViewModel = hiltViewModel()    //replaced above dependency chaining with this simple hiltViewModel()

            UserListScreen(
                viewModel = viewModel,
                onUserClick = { userId ->
                    navController.navigate(
                        Screen.UserDetail.createRoute(userId)
                    )
                }
            )
        }

        composable(
            Screen.UserDetail.route,
            arguments = listOf(
                navArgument("userId") {type = NavType.IntType}
            )
        ) { backStackEntry ->

            backStackEntry.arguments?.getInt("userId") ?: return@composable
            UserDetailScreen()

        }
    }
}