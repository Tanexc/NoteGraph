package ru.tanexc.notegraph.presentation.ui.widgets.note

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.google.firebase.annotations.concurrent.Background
import ru.tanexc.notegraph.domain.model.note.TextPiece
import ru.tanexc.notegraph.presentation.ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("SuspiciousIndentation")
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
                    .height(48.dp),
                content =
                if (focused && actions != null) {
                    actions
                } else {
                    {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(colorScheme.primary.copy(0.5f))
                        ) {
                            Text(
                                piece.label ?: "Untitled",
                                modifier = Modifier
                                    .padding(16.dp, 0.dp)
                                    .fillMaxWidth()
                                    .basicMarquee(2),
                                fontSize = Typography.headlineSmall.fontSize
                            )
                        }
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