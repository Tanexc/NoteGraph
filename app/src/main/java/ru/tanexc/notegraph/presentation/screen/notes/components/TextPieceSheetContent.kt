package ru.tanexc.notegraph.presentation.screen.notes.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.t8rin.dynamic.theme.rememberColorScheme
import com.t8rin.dynamic.theme.rememberDynamicThemeState
import ru.tanexc.notegraph.R
import ru.tanexc.notegraph.domain.model.note.TextPiece
import ru.tanexc.notegraph.presentation.util.LocalSettingsProvider


@OptIn(ExperimentalStdlibApi::class)
@Composable
fun TextPieceSheetContent(
    piece: TextPiece,
    onValueChanged: (TextPiece) -> Unit
) {

    val colorScheme = rememberColorScheme(
        isDarkTheme = LocalSettingsProvider.current.isDarkMode,
        amoledMode = LocalSettingsProvider.current.amoledMode,
        colorTuple = rememberDynamicThemeState().colorTuple.value
    )

    var labelText: String? by remember { mutableStateOf(piece.label) }
    var text: String by remember { mutableStateOf(piece.text) }
    var fontSize: Float by remember { mutableFloatStateOf(piece.textStyle.fontSize.value) }
    var lineHeight: Float by remember { mutableFloatStateOf(piece.textStyle.lineHeight.value) }
    var letterSpacing: Float by remember { mutableFloatStateOf(piece.textStyle.letterSpacing.value) }

    var width: Int by remember { mutableIntStateOf(piece.size.width) }
    var height: Int by remember { mutableIntStateOf(piece.size.height) }
    var cornerRadius: Int by remember { mutableIntStateOf(piece.cornerRadius) }

    var backgorundColor: Color by remember { mutableStateOf(piece.background) }
    val controller = rememberColorPickerController()


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
                value = text,
                onValueChange = {
                    if (it.length <= 1024) {
                        text = it
                        onValueChanged(piece.copy(text = text))
                    }
                },
                placeholder = {
                    Text(stringResource(R.string.content_description))
                },
                supportingText = {
                    Text("${text.length} / 1024")
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
            Text(stringResource(R.string.background), Modifier.fillMaxWidth(0.8f), textAlign = TextAlign.Start)
            Spacer(modifier = Modifier.size(8.dp))
            Box(Modifier.fillMaxWidth(0.8f).background(colorScheme.secondaryContainer)) {
                HsvColorPicker(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    controller = controller,
                    onColorChanged = { colorEnvelope ->
                        backgorundColor = colorEnvelope.color
                        onValueChanged(piece.copy(background = backgorundColor))
                    },
                    initialColor = backgorundColor
                )
            }
        }

    }

}