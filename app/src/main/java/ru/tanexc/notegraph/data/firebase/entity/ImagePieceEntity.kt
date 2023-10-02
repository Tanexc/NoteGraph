package ru.tanexc.notegraph.data.firebase.entity

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tanexc.notegraph.domain.interfaces.data_presenter.FirebaseEntity
import ru.tanexc.notegraph.domain.model.note.ImagePiece

data class ImagePieceEntity(
    val documentId: String = "",
    val offsetX: Int = 0,
    val offsetY: Int = 0,
    val width: Int = 0,
    val height: Int = 0,
    val cornerRadius: Int = 0,
    val alpha: Float = 0f,
    val label: String? = null,
    val contentDescription: String? = null,
    val fontSize: Int = 0,
    val lineHeight: Int = 0,
    val letterSpacing: Int = 0,
    val imageBitmap: ImageBitmap = ImageBitmap(0, 0)
) : FirebaseEntity {
    override fun asDomain(): ImagePiece = ImagePiece(
        documentId = documentId,
        offset = IntOffset(offsetX, offsetY),
        size = IntSize(width, height),
        cornerRadius = cornerRadius,
        alpha = alpha,
        contentDescription = contentDescription,
        imageBitmap = imageBitmap,
        label = label,
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
        "alpha" to alpha,
        "label" to (label?: ""),
        "contentDescription" to (contentDescription?: ""),
        "fontSize" to fontSize,
        "lineHeight" to lineHeight,
        "letterSpacing" to letterSpacing,
        "imageBitmap" to imageBitmap
    )
}