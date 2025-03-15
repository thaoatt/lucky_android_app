package com.example.luckyandroidapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.example.luckyandroidapp.ui.theme.primaryColor
import com.example.luckyandroidapp.ui.theme.redOrange

@Composable
@Preview
fun LoginScreen () {
    Scaffold { padding ->
        Column (Modifier.background(brush = Brush.linearGradient(
            colors = listOf(redOrange, primaryColor),
            start = Offset(0f, 0f),
            end = Offset(0f, 1000f)
        )).padding(padding).fillMaxSize()){

        }
    }
}