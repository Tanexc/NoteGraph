package ru.tanexc.notegraph.domain.model.note

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import ru.tanexc.notegraph.data.firebase.entity.ImagePieceEntity
import ru.tanexc.notegraph.domain.interfaces.data_presenter.Domain
import ru.tanexc.notegraph.domain.interfaces.data_presenter.NotePiece
import ru.tanexc.notegraph.presentation.ui.theme.Typography

// АРБУС!!

data class ImagePiece(
    override val documentId: String,
    val offset: IntOffset,
    val size: IntSize,
    val cornerRadius: Int = 0,
    val alpha: Float,
    val label: String?,
    val contentDescription: String?,
    val textStyle: TextStyle,
    val imageBitmap: ImageBitmap?
) : Domain, NotePiece {

    companion object {
        fun empty() = ImagePiece(
            documentId = "",
            offset = IntOffset(0,0),
            size = IntSize(264, 104),
            cornerRadius = 16,
            alpha = 1f,
            label = null,
            contentDescription = null,
            textStyle = Typography.labelMedium,
            imageBitmap = null
        )
    }

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
        fontSize = textStyle.fontSize.value,
        lineHeight = textStyle.lineHeight.value,
        letterSpacing = textStyle.letterSpacing.value,
        imageBitmap = imageBitmap
    )
}
