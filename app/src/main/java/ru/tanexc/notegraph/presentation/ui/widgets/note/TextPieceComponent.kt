package ru.tanexc.notegraph.presentation.ui.widgets.note

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ru.tanexc.notegraph.domain.model.note.TextPiece
import ru.tanexc.notegraph.presentation.ui.theme.Typography

@Composable
fun TextPieceComponent(
    modifier: Modifier = Modifier,
    onOffsetChange: ((IntOffset) -> Unit),
    focused: Boolean,
    piece: TextPiece,
    colorScheme: ColorScheme,
    defaultBackground: Color,
    actions: (@Composable (RowScope.() -> Unit))?
) {
    DraggableComponent(
        startOffset = piece.offset,
        enabled = focused,
        onRelease = onOffsetChange
    ) {
        Column(modifier.width(piece.size.width.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                content =
                if (focused && actions != null) {
                    actions
                } else {
                    {
                        piece.label?.let {
                            Row(
                                Modifier
                                    .fillMaxSize()
                                    .padding(0.dp, 4.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(colorScheme.primary.copy(0.5f))
                            ) {
                                Text(
                                    piece.label,
                                    modifier = Modifier
                                        .padding(16.dp, 0.dp)
                                        .fillMaxWidth(),
                                    fontSize = Typography.headlineSmall.fontSize,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }?: Row {}
                    }
                }
            )

            Box(
                modifier
                    .fillMaxWidth()
                    .height(piece.size.height.dp)
                    .run {
                        if (focused) this.border(
                            width = 3.dp,
                            shape = RoundedCornerShape(piece.cornerRadius.dp),
                            color = colorScheme.tertiary.copy(0.7f)
                        )
                        else this
                    }
                    .clip(RoundedCornerShape(piece.cornerRadius.dp))
                    .background(color = if (piece.background != Color.Transparent) piece.background else defaultBackground)
            ) {
                Text(
                    piece.text,
                    style = piece.textStyle,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }
    }
}