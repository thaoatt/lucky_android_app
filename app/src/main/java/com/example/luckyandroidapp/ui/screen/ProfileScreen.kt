package com.example.luckyandroidapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.luckyandroidapp.R
import com.example.luckyandroidapp.ui.theme.gold
import com.example.luckyandroidapp.ui.theme.grayTextColor
import com.example.luckyandroidapp.ui.theme.primaryColor
import com.example.luckyandroidapp.ui.theme.textColor

@Preview
@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.gardenersworld.com%2Fplants%2Fdifferent-types-of-flowers-to-grow%2F&psig=AOvVaw09YhuN1pJKfvEaOrA0g-tO&ust=1742401175091000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCOjNmqyElIwDFQAAAAAdAAAAABAE",
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.img_profile_default),
                error = painterResource(id = R.drawable.img_profile_default)

            )
            Spacer(modifier = Modifier.width(12.dp))
            Text("User name", color = textColor, fontWeight = FontWeight.Bold, fontSize = 32.sp)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 12.dp, top = 4.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.img_telephone),
                modifier = Modifier.size(24.dp),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(6.dp))
            Text("Phone number", color = grayTextColor, fontSize = 14.sp)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 12.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.img_email),
                modifier = Modifier.size(24.dp),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(6.dp))
            Text("Email", color = grayTextColor, fontSize = 14.sp)
        }

        HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier.fillMaxWidth(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "$140.000",
                    color = textColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    "Wallet",
                    color = textColor,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp),
                    textAlign = TextAlign.Center,
                )
            }

            VerticalDivider(
                modifier = Modifier
                    .height(64.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "$140.000",
                    color = textColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    "Wallet",
                    color = textColor,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }

        HorizontalDivider(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp))
        ProfileItem(onClick = { /*TODO*/ }, text = "Thanh toán", icon = R.drawable.img_wallet, isLogout = false)
        ProfileItem(onClick = { /*TODO*/ }, text = "Cài đặt", icon = R.drawable.img_setting, isLogout = false)
        ProfileItem(onClick = { /*TODO*/ }, text = "Đăng xuất", icon = R.drawable.img_turn_off, isLogout = true)
    }

}

@Composable
fun ProfileItem(onClick: () -> Unit, text: String, icon: Int, isLogout: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text,
            color = if (isLogout) primaryColor else textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}