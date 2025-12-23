package com.example.userselectionapp.presentation.navigation

sealed class Screen(val route: String) {
    object UserList : Screen("user_list")

    object UserDetail : Screen("user_details/{userId}") {
        fun createRoute(userId: Int): String {
            return "user_details/$userId"
        }
    }
}