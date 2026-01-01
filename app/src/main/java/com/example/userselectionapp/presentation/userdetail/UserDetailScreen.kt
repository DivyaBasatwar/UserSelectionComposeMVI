package com.example.userselectionapp.presentation.userdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun UserDetailScreen(
    viewModel: UserDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.processIntent(UserDetailsIntent.LoadUser)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp, 80.dp)
    ) {
        state.user?.let {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = it.email,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
