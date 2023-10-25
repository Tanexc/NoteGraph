package ru.tanexc.notegraph.presentation.ui.widgets.cards

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.tanexc.notegraph.R
import ru.tanexc.notegraph.domain.model.note.TextPiece
import ru.tanexc.notegraph.presentation.ui.theme.Typography

@Composable
fun TextPieceNoteCard(
    textPiece: TextPiece,
    colorScheme: ColorScheme
) {

    Column(
        Modifier
            .padding(16.dp)
    ) {
        Text(
            text = textPiece.label ?: stringResource(id = R.string.piece_label),
            overflow = TextOverflow.Ellipsis,
            style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = textPiece.label?.let {
                colorScheme.contentColorFor(colorScheme.secondaryContainer)
                    .copy(0.6f)
            } ?: colorScheme.contentColorFor(colorScheme.secondaryContainer)
                .copy(0.3f),
            maxLines = 1
        )

        Text(
            textPiece.text,
            overflow = TextOverflow.Ellipsis,
            style = Typography.bodySmall,
            maxLines = 5
        )
    }
}