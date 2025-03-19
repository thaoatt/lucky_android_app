package com.example.luckyandroidapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.luckyandroidapp.R
import com.example.luckyandroidapp.ui.theme.grayTextColor
import com.example.luckyandroidapp.ui.theme.textColor
import java.nio.file.WatchEvent

@Composable
fun ProfileScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.gardenersworld.com%2Fplants%2Fdifferent-types-of-flowers-to-grow%2F&psig=AOvVaw09YhuN1pJKfvEaOrA0g-tO&ust=1742401175091000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCOjNmqyElIwDFQAAAAAdAAAAABAE",
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape),
                contentScale = ContentScale.Crop,

            )
            Spacer(modifier = Modifier.width(12.dp))
            Text("User name", color = textColor, fontWeight = FontWeight.Bold, fontSize = 32.sp)
        }
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 4.dp, top = 12.dp)
        ){
            Image(
                painter = painterResource(R.drawable.img_telephone),
                modifier = Modifier.size(24.dp),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(6.dp))
            Text("Phone number", color = grayTextColor, fontSize = 14.sp)
        }

        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 4.dp)
        ){
            Image(
                painter = painterResource(R.drawable.img_email),
                modifier = Modifier.size(24.dp),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(6.dp))
            Text("Email", color = grayTextColor, fontSize = 14.sp)
        }
    }

}