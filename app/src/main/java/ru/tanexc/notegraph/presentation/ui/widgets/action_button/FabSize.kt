package ru.tanexc.notegraph.presentation.ui.widgets.action_button

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class FabSize(
    val horizontalPadding: Dp,
    val spacerPadding: Dp,
    val iconSize: Dp,
    val shape: RoundedCornerShape
) {
    data object Small : FabSize(
        iconSize = 24.dp,
        spacerPadding = 4.dp,
        horizontalPadding = 8.dp,
        shape = RoundedCornerShape(12.dp)
    )

    data object Common : FabSize(
        iconSize = 24.dp,
        spacerPadding = 12.dp,
        horizontalPadding = 16.dp,
        shape = RoundedCornerShape(16.dp)
    )

    data object Large : FabSize(
        iconSize = FloatingActionButtonDefaults.LargeIconSize,
        spacerPadding = 20.dp,
        horizontalPadding = 24.dp,
        shape = RoundedCornerShape(28.dp)
    )
}