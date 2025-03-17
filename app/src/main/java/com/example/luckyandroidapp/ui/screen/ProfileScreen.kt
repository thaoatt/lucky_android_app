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
import androidx.navigation.compose.rememberNavController

@Composable
fun ProfileScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "User List", style = MaterialTheme.typography.headlineMedium)

        val navController = rememberNavController()

    }
}