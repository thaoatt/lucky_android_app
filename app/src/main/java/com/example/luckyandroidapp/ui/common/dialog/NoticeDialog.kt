package com.example.luckyandroidapp.ui.common.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.luckyandroidapp.ui.theme.gold
import com.example.luckyandroidapp.ui.theme.redOrange

@Composable
fun NoticeDialog(
    title: String,
    message: String,
    isShowPositiveButton: Boolean = true,
    isShowNegativeButton: Boolean = true,
    textPositiveButton: String = "OK",
    textNegativeButton: String = "Cancel",
    onClickCloseButton: () -> Unit,
    onClickAcceptButton: () -> Unit
) {
    AlertDialog(onDismissRequest = { onClickCloseButton() }, shape = RoundedCornerShape(16.dp),
        buttons = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { onClickCloseButton() },
                        modifier = Modifier.fillMaxWidth(0.5f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = redOrange)
                    ) {
                        Text(text = textNegativeButton)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = { onClickAcceptButton() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = redOrange)
                    ) {
                        Text(text = textPositiveButton)
                    }
                }
            }
        })
}