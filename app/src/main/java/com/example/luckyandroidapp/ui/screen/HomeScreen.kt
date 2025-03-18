package com.example.luckyandroidapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.luckyandroidapp.R
import com.example.luckyandroidapp.ui.theme.gold
import com.example.luckyandroidapp.ui.theme.grayTextColor
import com.example.luckyandroidapp.ui.theme.textColor

val imageGiftList = List(6) { R.drawable.img_ip_1 to 20 ; R.drawable.img_ip_2 to 10 ; R.drawable.img_ip_3 to 10; R.drawable.img_ip_4 to 30; R.drawable.img_ip_5 to 20; R.drawable.img_ip_6 to 10 }

fun getRandomImageGift(): Int {
    val weightList = imageGiftList.flatMap { (item, weight) -> List(weight) { item } }
    return weightList.random()
}
@Composable
fun HomeScreen() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.open_box_lottie))
    val progress by animateLottieCompositionAsState(composition)
    var freeTurn by remember { mutableStateOf(1) }
    var isShowDialog by remember { mutableStateOf(false) }
    val images = List(6) { R.raw.orange_gift_box_lottie }
    val weightList = imageGiftList.flatMap { (item, weight) -> List(weight) { item } }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        if (isShowDialog) {
            UnboxingAlertDialog(onClick = {
                isShowDialog = false
            }, imgResource = weightList.random())
        }
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

        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(id = R.drawable.img_congraduations),
            contentDescription = null
        )

        Text(
            "Đập hộp may mắn\nNhận quà liền tay",
            color = gold,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.snacker_comic_font)),
            modifier = Modifier
                .padding(top = 160.dp)
                .fillMaxWidth(),
            lineHeight = 40.sp
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Chia thành 3 cột
                modifier = Modifier.fillMaxWidth()
            ) {
                items(images.size) { index ->
                    LottieItem(images[index], onClick = {
                        if (freeTurn >= 1) freeTurn -= 1
                        isShowDialog = true
                    })
                }
            }

            Box(
                modifier = Modifier
                    .padding(bottom = 24.dp, top = 20.dp)
                    .background(Color.White.copy(alpha = 0.8f), shape = RoundedCornerShape(16.dp))
                    .clickable {
                        if (freeTurn < 0) {
                            // open google pay
                        }
                    }
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 6.dp, horizontal = 12.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_carts),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        if (freeTurn >= 1) "Bạn có $freeTurn lượt mở hộp" else "Thêm lượt đập hộp",
                        color = Color.Black,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

    }
}

@Composable
fun UnboxingAlertDialog(onClick: () -> Unit, imgResource: Int) {
    AlertDialog(
        onDismissRequest = { onClick() },
        shape = RoundedCornerShape(20.dp),
        buttons = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.White, shape = RoundedCornerShape(20.dp))
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(12.dp)
                            .size(28.dp)
                            .clickable {
                                onClick()
                            }
                    )
                    Text(
                        "Chúc mừng bạn đã nhận được",
                        color = Color.Black,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                            .fillMaxWidth()
                    )

                    Image(
                        painter = painterResource(id = imgResource),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        "MẢNH GHÉP 1",
                        color = textColor,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                            .fillMaxWidth()
                    )

                    Text(
                        "Thu thập đủ 6 mảnh ghép để nhận quà",
                        color = grayTextColor,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }, backgroundColor = colorResource(id = R.color.white)
    )

}

@Composable
fun LottieItem(animationRes: Int, onClick: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes))
    val progress by animateLottieCompositionAsState(
        composition = composition, iterations = LottieConstants.IterateForever // Lặp vô hạn
    )

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable(onClick = {
                onClick()
            })
    )
}
