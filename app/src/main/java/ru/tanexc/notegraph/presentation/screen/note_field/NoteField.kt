package ru.tanexc.notegraph.presentation.screen.note_field

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.packInts
import com.smarttoolfactory.zoom.rememberZoomState
import com.t8rin.dynamic.theme.rememberColorScheme
import com.t8rin.dynamic.theme.rememberDynamicThemeState
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.model.note.Note
import ru.tanexc.notegraph.presentation.screen.note_field.view_model.NoteFieldViewModel
import ru.tanexc.notegraph.presentation.ui.widgets.action_button.FabOption
import ru.tanexc.notegraph.presentation.ui.widgets.action_button.MultipleFloatingActionButton
import ru.tanexc.notegraph.presentation.ui.widgets.note.ExpandableField
import ru.tanexc.notegraph.presentation.ui.widgets.note.ImagePieceComponent
import ru.tanexc.notegraph.presentation.ui.widgets.note.ImagePieceSheetContent
import ru.tanexc.notegraph.presentation.ui.widgets.note.TextPieceComponent
import ru.tanexc.notegraph.presentation.ui.widgets.note.TextPieceSheetContent
import ru.tanexc.notegraph.presentation.util.LocalSettingsProvider
import ru.tanexc.notegraph.presentation.util.rememberBottomSheetState
import ru.tanexc.notegraph.presentation.util.rememberExpandableFieldState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteField(
    modifier: Modifier,
    note: Note
) {

    val viewModel: NoteFieldViewModel = hiltViewModel()
    val bottomSheetState = rememberBottomSheetState().current

    LaunchedEffect(note) {
        viewModel.initializeNote(note)
    }

    val colorScheme = rememberColorScheme(
        isDarkTheme = LocalSettingsProvider.current.isDarkMode,
        amoledMode = LocalSettingsProvider.current.amoledMode,
        colorTuple = rememberDynamicThemeState().colorTuple.value
    )

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let {
                viewModel.createImagePiece(it)
            }
        }
    )

    val expandableFieldState = rememberExpandableFieldState(
        cellSize = 40,
        onSizeChanged = {},
        cellColor = colorScheme.onSurface.copy(0.07f)
    )

    viewModel.note?.let { currentNote ->
        Box(modifier.fillMaxSize()) {
            ExpandableField(
                state = expandableFieldState
            ) { zoom ->
                currentNote.textPieces.forEach { item ->
                    TextPieceComponent(
                        modifier = Modifier
                            .scale(zoom)
                            .combinedClickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onLongClick = {
                                    viewModel focusOnPiece item.documentId
                                },
                                onDoubleClick = {
                                    viewModel focusOnPiece null
                                },
                                onClick = {}
                            ),
                        onOffsetChange = { offset ->
                            viewModel
                                .saveTextPiece(
                                    viewModel.note!!.textPieces.first { it.documentId == item.documentId }
                                        .copy(offset = offset)
                                )
                        },
                        focused = viewModel.focusedPieceId == item.documentId,
                        piece = item,
                        colorScheme = colorScheme,
                        actions = {
                            IconButton(
                                modifier = Modifier.size(24.dp),
                                onClick = {
                                    bottomSheetState.setContent {
                                        TextPieceSheetContent(
                                            textPiece = item,
                                            onValueChanged = { viewModel.saveTextPiece(it) }
                                        )
                                    }
                                }) {
                                Icon(
                                    imageVector = Icons.Outlined.Build,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                )
                            }

                            Spacer(Modifier.weight(1f))
                            when (viewModel.synchronizing) {
                                is Action.Loading -> {
                                    CircularProgressIndicator(
                                        Modifier.size(24.dp),
                                        strokeWidth = 4.dp
                                    )
                                }

                                else -> {}
                            }
                            IconButton(
                                modifier = Modifier.size(24.dp),
                                onClick = { viewModel.deleteTextPiece(item.documentId) }) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        },
                        defaultBackground = colorScheme.secondaryContainer.copy(0.5f),
                        initialOffset = item.offset * zoom
                    )
                }
                currentNote.imagePieces.forEach { item ->
                    ImagePieceComponent(
                        modifier = Modifier
                            .combinedClickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onLongClick = {
                                    viewModel focusOnPiece item.documentId
                                },
                                onDoubleClick = {
                                    viewModel focusOnPiece null
                                },
                                onClick = {}
                            ),
                        onOffsetChange = { offset ->
                            viewModel
                                .saveImagePiece(
                                    viewModel.note!!.imagePieces.first { it.documentId == item.documentId }
                                        .copy(offset = offset / zoom))
                        },
                        focused = viewModel.focusedPieceId == item.documentId,
                        piece = item,
                        colorScheme = colorScheme,
                        initialOffset = item.offset * zoom,
                        actions = {
                            IconButton(onClick = {
                                bottomSheetState.setContent {
                                    ImagePieceSheetContent(
                                        piece = item,
                                        onValueChanged = { viewModel.saveImagePiece(it) }
                                    )
                                }
                            }) {
                                Icon(
                                    Icons.Outlined.Build,
                                    null
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            when (viewModel.synchronizing) {
                                is Action.Loading -> {
                                    CircularProgressIndicator(Modifier.size(24.dp))
                                }

                                else -> {}
                            }
                            IconButton(onClick = { viewModel.deleteImagePiece(item.documentId) }) {
                                Icon(
                                    Icons.Outlined.Delete,
                                    null
                                )
                            }
                        }
                    )
                }
            }
            MultipleFloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                icon = {
                    Icon(
                        Icons.Outlined.Add,
                        null,
                        modifier = Modifier.size((it))
                    )

                },
                options = listOf(
                    FabOption(
                        icon = Icons.Outlined.Image,
                        index = 0
                    ),
                    FabOption(
                        icon = Icons.AutoMirrored.Outlined.Article,
                        index = 1
                    )
                ),
                onOptionSelected = {
                    when (it.index) {
                        0 -> {
                            imagePicker.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }

                        1 -> {
                            viewModel.createTextPiece()
                        }
                    }
                }
            )
        }
    }
}