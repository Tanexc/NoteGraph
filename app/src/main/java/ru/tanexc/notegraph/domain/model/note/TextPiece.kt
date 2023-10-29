package ru.tanexc.notegraph.domain.model.note

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import ru.tanexc.notegraph.data.firebase.entity.TextPieceEntity
import ru.tanexc.notegraph.domain.interfaces.data_presenter.Domain
import ru.tanexc.notegraph.domain.interfaces.data_presenter.NotePiece
import ru.tanexc.notegraph.presentation.ui.theme.Typography

data class TextPiece(
    override val documentId: String,
    val offset: IntOffset,
    val size: IntSize = IntSize(164, 64),
    val background: Color = Color.Transparent,
    val cornerRadius: Int = 0,
    val label: String?,
    val text: String,
    val textStyle: TextStyle,
) : Domain, NotePiece {


    companion object {
        fun empty(
            documentId: String = "",
            offset: IntOffset = IntOffset(0, 0),
            size: IntSize = IntSize(240, 320),
            cornerRadius: Int = 16,
            label: String? = null,
            text: String = "",
            textStyle: TextStyle = Typography.labelMedium
        ): TextPiece = TextPiece(
            documentId = documentId,
            offset = offset,
            size = size,
            cornerRadius = cornerRadius,
            label = label,
            text = text,
            textStyle = textStyle
        )
    }


    override fun asFirebaseEntity(): TextPieceEntity = TextPieceEntity(
        documentId = documentId,
        offsetX = offset.x,
        offsetY = offset.y,
        width = size.width,
        height = size.height,
        cornerRadius = cornerRadius,
        label = label,
        fontSize = textStyle.fontSize.value,
        lineHeight = textStyle.lineHeight.value,
        letterSpacing = textStyle.letterSpacing.value,
        text = text,
        background = background.toArgb()
    )

}