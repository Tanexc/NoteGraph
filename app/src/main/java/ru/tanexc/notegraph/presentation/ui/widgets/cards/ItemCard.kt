package ru.tanexc.notegraph.presentation.ui.widgets.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    borderEnabled: Boolean,
    borderColor: Color = Color.Transparent,
    backgroundColor: Color = Color.Transparent,
    content: @Composable () -> Unit

) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(16.dp),
                brush = if (borderEnabled) SolidColor(borderColor.copy(0.7f)) else SolidColor(Color.Transparent)
            )
            .background(backgroundColor)
    ) {
        content()
    }
}