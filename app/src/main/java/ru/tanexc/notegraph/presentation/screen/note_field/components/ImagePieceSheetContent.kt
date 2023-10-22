package ru.tanexc.notegraph.presentation.screen.note_field.components


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tanexc.notegraph.R
import ru.tanexc.notegraph.domain.model.note.ImagePiece


@Composable
fun ImagePieceSheetContent(
    piece: ImagePiece,
    onValueChanged: (ImagePiece) -> Unit
) {

    var labelText: String? by remember { mutableStateOf(piece.label) }
    var contentDescription: String? by remember { mutableStateOf(piece.contentDescription) }
    var fontSize: Float by remember { mutableFloatStateOf(piece.textStyle.fontSize.value) }
    var lineHeight: Float by remember { mutableFloatStateOf(piece.textStyle.lineHeight.value) }
    var letterSpacing: Float by remember { mutableFloatStateOf(piece.textStyle.letterSpacing.value) }

    var width: Int by remember { mutableIntStateOf(piece.size.width) }
    var height: Int by remember { mutableIntStateOf(piece.size.height) }
    var cornerRadius: Int by remember { mutableIntStateOf(piece.cornerRadius) }

    var alpha: Float by remember { mutableFloatStateOf(piece.alpha) }


    LazyColumn(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(stringResource(R.string.text), Modifier.fillMaxWidth(0.8f), textAlign = TextAlign.Start)
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                value = labelText ?: "",
                onValueChange = {
                    if (it.length <= 64) {
                        labelText = it
                        onValueChanged(piece.copy(label = labelText))
                    }
                },
                placeholder = {
                    Text(stringResource(R.string.piece_label))
                },
                supportingText = {
                    labelText?.let {
                        Text("${it.length} / 64")
                    }
                }
            )
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                value = contentDescription ?: "",
                onValueChange = {
                    if (it.length <= 512) {
                        contentDescription = it
                        onValueChanged(piece.copy(contentDescription = contentDescription))
                    }
                },
                placeholder = {
                    Text(stringResource(R.string.content_description))
                },
                supportingText = {
                    contentDescription?.let {
                        Text("${it.length} / 512")
                    }
                }
            )
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(0.3f),
                    value = fontSize.toString(),
                    onValueChange = {
                        it.toFloatOrNull()?.let { value ->
                            fontSize = value
                            onValueChanged(piece.copy(textStyle = piece.textStyle.copy(fontSize = fontSize.sp)))
                        }
                    },
                    label = {
                        Text(stringResource(R.string.font_size))
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    modifier = Modifier.weight(0.3f),
                    value = lineHeight.toString(),
                    onValueChange = {
                        it.toFloatOrNull()?.let { value ->
                            lineHeight = value
                            onValueChanged(piece.copy(textStyle = piece.textStyle.copy(lineHeight = lineHeight.sp)))
                        }
                    },
                    label = {
                        Text(stringResource(R.string.line_height))
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    modifier = Modifier.weight(0.3f),
                    value = letterSpacing.toString(),
                    onValueChange = {
                        it.toFloatOrNull()?.let { value ->
                            letterSpacing = value
                            onValueChanged(piece.copy(textStyle = piece.textStyle.copy(letterSpacing = letterSpacing.sp)))
                        }
                    },
                    label = {
                        Text(stringResource(R.string.letter_spacing))
                    }
                )
            }
        }

        item {
            Text(stringResource(R.string.size), Modifier.fillMaxWidth(0.8f), textAlign = TextAlign.Start)
            Row(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(0.3f),
                    value = width.toString(),
                    onValueChange = {
                        it.toIntOrNull()?.let { value ->
                            width = value
                            onValueChanged(piece.copy(size = IntSize(width, piece.size.height)))
                        }
                    },
                    label = {
                        Text(stringResource(R.string.width))
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    modifier = Modifier.weight(0.3f),
                    value = height.toString(),
                    onValueChange = {
                        it.toIntOrNull()?.let { value ->
                            height = value
                            onValueChanged(piece.copy(size = IntSize(piece.size.width, height)))
                        }
                    },
                    label = {
                        Text(stringResource(R.string.height))
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    modifier = Modifier.weight(0.3f),
                    value = cornerRadius.toString(),
                    onValueChange = {
                        it.toIntOrNull()?.let { value ->
                            cornerRadius = value
                            onValueChanged(piece.copy(cornerRadius = cornerRadius))
                        }
                    },
                    label = {
                        Text(stringResource(R.string.corner_radius))
                    }
                )
            }
        }

        item {
            Text(stringResource(R.string.image), Modifier.fillMaxWidth(0.8f), textAlign = TextAlign.Start)
            Row(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(0.3f),
                    value = alpha.toString(),
                    onValueChange = {
                        it.toFloatOrNull()?.let { value ->
                            alpha = value
                            onValueChanged(piece.copy(alpha = alpha))
                        }
                    },
                    label = {
                        Text(stringResource(R.string.alpha))
                    }
                )
            }
        }

    }

}