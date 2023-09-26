package ru.tanexc.notegraph.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

data class TextPiece(
    val documentId: String,
    val offset: IntOffset,
    val size: DpSize = DpSize(164.dp, 64.dp),
    val background: Color = Color.Transparent,
    val cornerRadius: Dp = 0.dp,
    val text: String,
    val textStyle: TextStyle
): Domain {
    override fun asMap(): Map<String, Any> {
        TODO("Not yet implemented")
    }
}