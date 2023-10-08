package ru.tanexc.notegraph.presentation.ui.widgets.note

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ru.tanexc.notegraph.domain.model.note.TextPiece

@Composable
fun TextPieceComponent(
    modifier: Modifier = Modifier,
    onOffsetChange: ((IntOffset) -> Unit)?,
    onRelease: () -> Unit,
    focused: Boolean,
    piece: TextPiece,
    indicationColor: Color
) {

    DraggableComponent(
        startOffset = piece.offset,
        enabled = focused,
        onOffsetChange = onOffsetChange,
        enabledIndication = {
            border(
                width = 3.dp,
                shape = RoundedCornerShape(piece.cornerRadius),
                color = indicationColor
            )
        },
        onRelease = onRelease
    ) {

        Box(
            modifier.size(piece.size.width.dp, piece.size.height.dp)
        ) {
            Text(
                piece.text,
                style = piece.textStyle,
                modifier = Modifier.fillMaxSize().padding(16.dp)
            )
        }
    }
}