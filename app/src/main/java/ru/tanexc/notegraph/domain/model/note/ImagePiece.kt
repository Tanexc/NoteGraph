package ru.tanexc.notegraph.domain.model.note

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import ru.tanexc.notegraph.data.firebase.entity.ImagePieceEntity
import ru.tanexc.notegraph.domain.interfaces.data_presenter.Domain
import ru.tanexc.notegraph.domain.interfaces.data_presenter.NotePiece

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
) : Domain, NotePiece {
    override fun asFirebaseEntity(): ImagePieceEntity = ImagePieceEntity(
        documentId = documentId,
        offsetX = offset.x,
        offsetY = offset.y,
        width = size.width,
        height = size.height,
        cornerRadius = cornerRadius,
        alpha = alpha,
        label = label,
        contentDescription = contentDescription,
        fontSize = textStyle.fontSize.value.toInt(),
        lineHeight = textStyle.lineHeight.value.toInt(),
        letterSpacing = textStyle.letterSpacing.value.toInt(),
        imageBitmap = imageBitmap
    )
}
