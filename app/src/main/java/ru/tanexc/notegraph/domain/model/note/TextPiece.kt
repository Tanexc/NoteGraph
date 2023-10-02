package ru.tanexc.notegraph.domain.model.note

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import ru.tanexc.notegraph.core.util.asMap
import ru.tanexc.notegraph.data.firebase.entity.TextPieceEntity
import ru.tanexc.notegraph.domain.interfaces.data_presenter.Domain
import ru.tanexc.notegraph.domain.interfaces.data_presenter.FirebaseEntity
import ru.tanexc.notegraph.domain.interfaces.data_presenter.NotePiece

data class TextPiece(
    val documentId: String,
    val offset: IntOffset,
    val size: IntSize = IntSize(164, 64),
    val background: Color = Color.Transparent,
    val cornerRadius: Int = 0,
    val label: String?,
    val text: String,
    val textStyle: TextStyle,
): Domain, NotePiece {
    override fun asFirebaseEntity(): TextPieceEntity = TextPieceEntity(
        documentId = documentId,
        offsetX = offset.x,
        offsetY = offset.y,
        width = size.width,
        height = size.height,
        cornerRadius = cornerRadius,
        label = label,
        fontSize = textStyle.fontSize.value.toInt(),
        lineHeight = textStyle.lineHeight.value.toInt(),
        letterSpacing = textStyle.letterSpacing.value.toInt(),
        text = text,
        background = background.value.toInt()
    )

}