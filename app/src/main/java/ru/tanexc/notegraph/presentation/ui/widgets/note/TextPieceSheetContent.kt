package ru.tanexc.notegraph.presentation.ui.widgets.note


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.smarttoolfactory.colorpicker.widget.PrettyColorPicker
import com.t8rin.dynamic.theme.rememberColorScheme
import com.t8rin.dynamic.theme.rememberDynamicThemeState
import ru.tanexc.notegraph.R
import ru.tanexc.notegraph.domain.model.note.TextPiece
import ru.tanexc.notegraph.presentation.ui.theme.Typography
import ru.tanexc.notegraph.presentation.util.LocalSettingsProvider


@Composable
fun TextPieceSheetContent(
    textPiece: TextPiece,
    onValueChanged: (TextPiece) -> Unit
) {

    var piece by remember {
        mutableStateOf(textPiece)
    }

    val colorScheme = rememberColorScheme(
        isDarkTheme = LocalSettingsProvider.current.isDarkMode,
        amoledMode = LocalSettingsProvider.current.amoledMode,
        colorTuple = rememberDynamicThemeState().colorTuple.value
    )

    var labelText: String? by remember { mutableStateOf(piece.label) }
    var text: String by remember { mutableStateOf(piece.text) }
    var fontSize: String by remember { mutableStateOf(piece.textStyle.fontSize.value.toString()) }
    var lineHeight: String by remember { mutableStateOf(piece.textStyle.lineHeight.value.toString()) }
    var letterSpacing: String by remember { mutableStateOf(piece.textStyle.letterSpacing.value.toString()) }

    var width: String by remember { mutableStateOf(piece.size.width.toString()) }
    var height: String by remember { mutableStateOf(piece.size.height.toString()) }
    var cornerRadius: String by remember { mutableStateOf(piece.cornerRadius.toString()) }

    var backgroundColor: Color by remember { mutableStateOf(piece.background) }

    LaunchedEffect(piece) {
        onValueChanged(piece)
    }

    LazyColumn(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                stringResource(R.string.text),
                Modifier.fillMaxWidth(0.8f),
                textAlign = TextAlign.Start,
                style = Typography.headlineSmall
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                value = labelText ?: "",
                onValueChange = {
                    if (it.length <= 64) {
                        labelText = it
                        piece = piece.copy(label = labelText)
                    }
                },
                label = {
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
                        piece = piece.copy(text = text)
                    }
                },
                label = {
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
                    value = fontSize,
                    onValueChange = {
                        fontSize = it
                        it.toFloatOrNull()?.let { value ->
                            piece =
                                piece.copy(textStyle = piece.textStyle.copy(fontSize = value.sp))
                        }
                    },
                    label = {
                        Text(stringResource(R.string.font_size))
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    modifier = Modifier.weight(0.3f),
                    value = lineHeight,
                    onValueChange = {
                        lineHeight = it
                        it.toFloatOrNull()?.let { value ->
                            piece =
                                piece.copy(textStyle = piece.textStyle.copy(lineHeight = value.sp))
                        }
                    },
                    label = {
                        Text(stringResource(R.string.line_height))
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    modifier = Modifier.weight(0.3f),
                    value = letterSpacing,
                    onValueChange = {
                        letterSpacing = it
                        it.toFloatOrNull()?.let { value ->
                            piece =
                                piece.copy(textStyle = piece.textStyle.copy(letterSpacing = value.sp))
                        }
                    },
                    label = {
                        Text(stringResource(R.string.letter_spacing))
                    }
                )
            }
        }

        item {
            Spacer(Modifier.size(16.dp))
            Text(
                stringResource(R.string.size),
                Modifier.fillMaxWidth(0.8f),
                textAlign = TextAlign.Start,
                style = Typography.headlineSmall
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(0.3f),
                    value = width,
                    onValueChange = {
                        width = it
                        it.toIntOrNull()?.let { value ->
                            piece = piece.copy(size = IntSize(value, piece.size.height))
                        }
                    },
                    label = {
                        Text(stringResource(R.string.width))
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    modifier = Modifier.weight(0.3f),
                    value = height,
                    onValueChange = {
                        height = it
                        it.toIntOrNull()?.let { value ->
                            piece = piece.copy(size = IntSize(piece.size.width, value))
                        }
                    },
                    label = {
                        Text(stringResource(R.string.height))
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    modifier = Modifier.weight(0.3f),
                    value = cornerRadius,
                    onValueChange = {
                        cornerRadius = it
                        it.toIntOrNull()?.let { value ->
                            piece = piece.copy(cornerRadius = value)
                            onValueChanged(piece)
                        } ?: run {
                            piece = piece.copy(cornerRadius = 0)
                        }
                    },
                    label = {
                        Text(stringResource(R.string.corner_radius))
                    },
                    placeholder = {
                        Text(text = "0")
                    }
                )
            }
        }
        item {

            Spacer(modifier = Modifier.size(16.dp))
            Text(
                stringResource(R.string.background),
                Modifier.fillMaxWidth(0.8f),
                textAlign = TextAlign.Start,
                style = Typography.headlineSmall
            )
            PrettyColorPicker(
                Modifier
                    .fillMaxWidth(0.8f)
                    .background(
                        colorScheme.secondaryContainer.copy(0.7f),
                        RoundedCornerShape(22.dp)
                    ),
                initialColor = backgroundColor,
                colorScheme = colorScheme,
                onValueChanged = { color ->
                    backgroundColor = color
                    piece = piece.copy(background = backgroundColor)
                    onValueChanged(piece)
                }
            )
        }
    }
}