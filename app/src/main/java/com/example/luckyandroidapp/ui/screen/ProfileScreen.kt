package com.example.luckyandroidapp.ui.screen

import androidx.compose.runtime.Composable
import com.example.luckyandroidapp.viewmodels.ProfileViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = viewModel()) {
    val users by viewModel.users.collectAsState()

    LaunchedEffect(Unit) {
//        viewModel.fetchUsers()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "User List", style = MaterialTheme.typography.headlineMedium)

        LazyColumn {
            items(users) { user ->
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "user.name", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "user.email", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}