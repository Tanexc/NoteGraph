package ru.tanexc.notegraph.presentation.ui.widgets.note

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ru.tanexc.notegraph.R
import ru.tanexc.notegraph.domain.model.ImagePiece

@Composable
fun ImagePieceComponent(
    modifier: Modifier = Modifier,
    onOffsetChange: ((IntOffset) -> Unit)?,
    focused: Boolean,
    piece: ImagePiece,
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
        }
    ) {

        Box(
            modifier.size(piece.size.width.dp, piece.size.height.dp)
        ) {
            Text(
                piece.label?: stringResource(R.string.untitled),
                style = piece.textStyle,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
            TODO("edit imagePiece component")
        }
    }
}