package ru.tanexc.notegraph.domain.model

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

// АРБУС!!

data class ImagePiece(
    val documentId: String,
    val offset: IntOffset,
    val size: IntSize,
    val cornerRadius: Int = 0,
    val alpha: Float,
    val label: String?,
    val contentDescription: String?,
    val textStyle: TextStyle,
    val imageBitmap: ImageBitmap
): Domain, NotePiece {
    override fun asMap(): Map<String, Any> {
        TODO("Not yet implemented")
    }
}
