package ru.tanexc.notegraph.presentation.ui.widgets.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.tanexc.notegraph.R
import ru.tanexc.notegraph.domain.model.note.ImagePiece
import ru.tanexc.notegraph.presentation.ui.theme.Typography

@Composable
fun ImagePieceNoteCard(
    imagePiece: ImagePiece,
    colorScheme: ColorScheme
) {
    Column {
        Spacer(Modifier.size(16.dp))
        Text(
            text = imagePiece.label
                ?: stringResource(id = R.string.piece_label),
            overflow = TextOverflow.Ellipsis,
            style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = imagePiece.label?.let {
                colorScheme.contentColorFor(colorScheme.secondaryContainer)
                    .copy(0.6f)
            } ?: colorScheme.contentColorFor(colorScheme.secondaryContainer)
                .copy(0.3f),
            modifier = Modifier.padding(16.dp, 0.dp)
        )

        imagePiece.imageBitmap?.let {
            Image(
                it, imagePiece.contentDescription,
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .background(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.Transparent
                    ),
                contentScale = ContentScale.FillWidth
            )
        } ?: Text(
            modifier = Modifier.padding(16.dp, 32.dp),
            text = imagePiece.contentDescription
                ?: stringResource(id = R.string.content_description),
            overflow = TextOverflow.Ellipsis,
            style = Typography.bodySmall,
            color = imagePiece.contentDescription?.let {
                colorScheme.contentColorFor(colorScheme.secondaryContainer)
                    .copy(0.5f)
            } ?: colorScheme.contentColorFor(colorScheme.secondaryContainer)
        )
    }
}