package com.example.luckyandroidapp.ui.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.luckyandroidapp.model.GiftModel
import com.example.luckyandroidapp.ui.theme.grayTextColor
import com.example.luckyandroidapp.ui.theme.textColor
import com.example.luckyandroidapp.ui.theme.white
import com.example.luckyandroidapp.utils.pref
import com.example.luckyandroidapp.utils.receivedGiftList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun HistoryScreen() {
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val listType = object : TypeToken<List<GiftModel>>() {}.type

    var receivedGiftList by rememberSaveable {
        mutableStateOf(mutableListOf<GiftModel>())
    }
    if (context.pref.receivedGiftList.isNotBlank()) receivedGiftList.addAll(
        Gson().fromJson(
            context.pref.receivedGiftList,
            listType
        )
    )

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        itemsIndexed(receivedGiftList.reversed()) { index, item ->
            HistoryItem(item)
            if (index < receivedGiftList.size - 1) Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            )
        }
    }
}

@Composable
fun HistoryItem(model: GiftModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = white)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // Title
            Text(
                "CHÚC MỪNG NHÉ!!!",
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            // Content
            Text(
                if (model.isMainGift) "Bạn đã nhận được MẢNH GHÉP ${model.id}" else "Bạn nhận được 1 món quà khác. Liên hệ với chúng tôi để nhận quà",
                color = textColor,
                fontSize = 14.sp
            )
        }

        Image(
            painter = painterResource(id = model.image),
            modifier = Modifier
                .height(60.dp)
                .width(45.dp),
            contentDescription = null,
            alignment = Alignment.BottomEnd,
        )
    }
}