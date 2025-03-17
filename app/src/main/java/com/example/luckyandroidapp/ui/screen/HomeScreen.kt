package com.example.luckyandroidapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.luckyandroidapp.R

@Composable
fun HomeScreen() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.open_box_lottie))
    val progress by animateLottieCompositionAsState(composition)

    val images = List(6) { R.raw.orange_gift_box_lottie }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_lucky_money_background), // Thay bằng ảnh trong drawable
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f)) // Bóng mờ 50%
        )
//        LottieAnimation(
//            composition = composition,
//            progress = progress,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Chia thành 3 cột
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(images.size) { index ->
//                Image(
//                    painter = painterResource(id = images[index]),
//                    contentDescription = "Gift Image",
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .size(100.dp), // Điều chỉnh kích thước ảnh
//                    contentScale = ContentScale.Fit
//                )

                LottieItem(images[index], onClick = {

                })
            }
        }
    }

}

@Composable
fun LottieItem(animationRes: Int, onClick: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever // Lặp vô hạn
    )

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable(onClick = {
                onClick()
            })
    )
}
