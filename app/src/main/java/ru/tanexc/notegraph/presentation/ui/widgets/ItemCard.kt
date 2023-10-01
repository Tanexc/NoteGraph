package ru.tanexc.notegraph.presentation.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    borderEnabled: Boolean,
    borderColor: Color = Color.Transparent,
    backgroundColor: Color = MaterialTheme.colors.secondary.copy(0.3f),
    content: @Composable () -> Unit

) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(16.dp),
                brush = if (borderEnabled) SolidColor(borderColor.copy(0.7f)) else SolidColor(Color.Transparent)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
    ) {
        content()
    }
}