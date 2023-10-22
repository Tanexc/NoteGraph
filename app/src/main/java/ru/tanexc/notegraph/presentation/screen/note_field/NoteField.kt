package ru.tanexc.notegraph.presentation.screen.note_field

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.t8rin.dynamic.theme.rememberColorScheme
import com.t8rin.dynamic.theme.rememberDynamicThemeState
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tanexc.notegraph.domain.model.note.Note
import ru.tanexc.notegraph.presentation.screen.note_field.components.ImagePieceSheetContent
import ru.tanexc.notegraph.presentation.screen.note_field.components.TextPieceSheetContent
import ru.tanexc.notegraph.presentation.screen.note_field.view_model.NoteFieldViewModel
import ru.tanexc.notegraph.presentation.ui.widgets.action_button.FabOption
import ru.tanexc.notegraph.presentation.ui.widgets.action_button.MultipleFloatingActionButton
import ru.tanexc.notegraph.presentation.ui.widgets.note.ExpandableField
import ru.tanexc.notegraph.presentation.ui.widgets.note.ImagePieceComponent
import ru.tanexc.notegraph.presentation.ui.widgets.note.TextPieceComponent
import ru.tanexc.notegraph.presentation.util.LocalSettingsProvider
import ru.tanexc.notegraph.presentation.util.rememberBottomSheetState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteField(
    modifier: Modifier,
    note: Note
) {
    val viewModel: NoteFieldViewModel = hiltViewModel()
    val bottomSheetState = rememberBottomSheetState().current

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

    LaunchedEffect(note) {
        viewModel.initializeNote(note)
    }

    viewModel.note?.let { currentNote ->
        Box(modifier.fillMaxSize()) {
            ExpandableField(
                initialSize = IntSize(564, 728),
                onSizeChanged = { },
            ) {
                currentNote.textPieces.forEach { item ->
                    TextPieceComponent(
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
                                .saveTextPiece(
                                    viewModel.note!!.textPieces.first { it.documentId == item.documentId }
                                        .copy(offset = offset))
                        },
                        focused = viewModel.focusedPieceId == item.documentId,
                        piece = item,
                        colorScheme = colorScheme,
                        actions = {
                            IconButton(onClick = {
                                bottomSheetState.setContent {
                                    TextPieceSheetContent(
                                        piece = item,
                                        onValueChanged = { viewModel.saveTextPiece(it) }
                                    )
                                }
                            }) {
                                Icon(
                                    Icons.Outlined.Build,
                                    null
                                )
                            }
                            Spacer(Modifier.weight(1f))
                            IconButton(onClick = { viewModel.deleteTextPiece(item.documentId) }) {
                                Icon(
                                    Icons.Outlined.Delete,
                                    null
                                )
                            }
                        },
                        defaultBackground = colorScheme.secondaryContainer.copy(0.5f)
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
                                        .copy(offset = offset))
                        },
                        focused = viewModel.focusedPieceId == item.documentId,
                        piece = item,
                        colorScheme = colorScheme,
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
                            IconButton(onClick = { viewModel.deleteImagePiece(item.documentId) }) {
                                Icon(
                                    Icons.Outlined.Delete,
                                    null
                                )
                            }
                        },

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