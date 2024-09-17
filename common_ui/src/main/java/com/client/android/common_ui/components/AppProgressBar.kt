package com.client.android.common_ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.client.android.common_ui.Blue

@Composable
fun AppProgressBar(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier.width(48.dp).height(48.dp),
        color = Blue
    )
}
