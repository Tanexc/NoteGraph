package ru.tanexc.notegraph.domain.model.note

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import ru.tanexc.notegraph.core.util.asString
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
        fun empty(documentId: String = "",
                  offset: IntOffset = IntOffset(0,0),
                  size: IntSize = IntSize(264, 104),
                  cornerRadius: Int = 16,
                  alpha: Float = 1f,
                  label: String? = null,
                  contentDescription: String? = null,
                  textStyle: TextStyle = Typography.labelMedium,
                  imageBitmap: ImageBitmap? = null
        ) = ImagePiece(
            documentId = documentId,
            offset = offset,
            size = size,
            cornerRadius = cornerRadius,
            alpha = alpha,
            label = label,
            contentDescription = contentDescription,
            textStyle = textStyle,
            imageBitmap = imageBitmap
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
        imageBitmap = imageBitmap.asString()
    )
}
