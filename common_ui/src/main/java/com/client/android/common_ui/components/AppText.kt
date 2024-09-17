package com.client.android.common_ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun AppText(
    message: String?,
    style: TextStyle,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    color: Color = Color.Unspecified
) {
    if (message?.isNotEmpty() == true) {
        Text(
            text = message,
            style = style,
            maxLines = maxLines,
            overflow = overflow,
            modifier = modifier,
            color = color
        )
    }
}
