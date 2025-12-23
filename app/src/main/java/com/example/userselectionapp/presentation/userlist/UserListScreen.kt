package com.example.userselectionapp.presentation.userlist

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.userselectionapp.domain.model.User

@Composable
fun UserListScreen(
    viewModel: UserListViewModel,
    onUserClick : (Int) -> Unit,
){
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    // one time events
    LaunchedEffect(Unit) {
        viewModel.events.collect { message ->
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 72.dp, top = 20.dp)
        ) {
            Header()

            SearchBar { query ->
                viewModel.onSearchQueryChanged(query)
            }

            if(!uiState.isLoading){
                UserList(
                    users = uiState.users,
                    onItemClick = { userId ->
                        onUserClick(userId)
                    },
                    onCheckToggle = { userId ->
                        viewModel.toggleUserSelection(userId)
                    }
                )
            }
        }

        if(uiState.isLoading){
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        SubmitButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = {
                viewModel.submitSelectedUsers()
            }
        )
    }
}

@Composable
fun SubmitButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Submit")
    }
}

@Composable
fun UserList(
    users: List<User>,
    onItemClick: (Int) -> Unit,
    onCheckToggle: (Int) -> Unit) {

    LazyColumn {
        items(users) { user ->
            UserRow(
                user = user,
                onCheckChange = { onCheckToggle(user.id) },
                onClick = { onItemClick(user.id) }
            )
        }
    }
}

@Composable
fun UserRow(
    user: User,
    onCheckChange: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{
                onClick()
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = user.isSelected,
            onCheckedChange = { onCheckChange() }
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = user.name,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = user.email,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchText,
        onValueChange = {
            searchText = it
            onSearch(searchText)
        },
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "searchIcon", tint = Color.LightGray) },
        label = { Text("Search users") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        textStyle = TextStyle(color = Color.Gray)

    )
}

@Composable
fun Header() {
    Text(
        text = "Select Users",
        modifier = Modifier.padding(16.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
}
