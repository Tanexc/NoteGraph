package ru.tanexc.notegraph.data.firebase.entity

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
import ru.tanexc.notegraph.domain.interfaces.data_presenter.FirebaseEntity
import ru.tanexc.notegraph.domain.model.note.TextPiece

data class TextPieceEntity(
    val documentId: String = "",
    val offsetX: Int = 0,
    val offsetY: Int = 0,
    val width: Int = 0,
    val height: Int = 0,
    val background: Int = 0,
    val cornerRadius: Int = 0,
    val label: String? = null,
    val text: String = "",
    val fontSize: Int = 0,
    val lineHeight: Int = 0,
    val letterSpacing: Int = 0,
): FirebaseEntity {
    override fun asDomain(): TextPiece = TextPiece(
        documentId = documentId,
        offset = IntOffset(offsetX, offsetY),
        size = IntSize(width, height),
        background = Color(background),
        label = label,
        text = text,
        cornerRadius = cornerRadius,
        textStyle = TextStyle(
            fontSize = fontSize.sp,
            lineHeight = lineHeight.sp,
            letterSpacing = letterSpacing.sp
        )
    )

    override fun asMap(): Map<String, Any> = mapOf(
        "documentId" to documentId,
        "offsetX" to offsetX,
        "offsetY" to offsetY,
        "width" to width,
        "height" to height,
        "cornerRadius" to cornerRadius,
        "label" to (label?: ""),
        "fontSize" to fontSize,
        "lineHeight" to lineHeight,
        "letterSpacing" to letterSpacing,
        "text" to text,
        "background" to background
    )
}