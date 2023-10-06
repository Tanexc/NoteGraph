package ru.tanexc.notegraph.presentation.screen.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.t8rin.dynamic.theme.rememberColorScheme
import com.t8rin.dynamic.theme.rememberDynamicThemeState
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.domain.model.note.Note
import ru.tanexc.notegraph.presentation.screen.notes.components.NoteSheetContent
import ru.tanexc.notegraph.presentation.screen.notes.view_model.NoteViewModel
import ru.tanexc.notegraph.presentation.ui.widgets.note.ImagePieceComponent
import ru.tanexc.notegraph.presentation.ui.widgets.note.NoteField
import ru.tanexc.notegraph.presentation.ui.widgets.note.TextPieceComponent
import ru.tanexc.notegraph.presentation.util.LocalSettingsProvider
import ru.tanexc.notegraph.presentation.util.rememberAppBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    note: Note
) {
    val viewModel: NoteViewModel = hiltViewModel()
    var showNoteSheet: Boolean by remember { mutableStateOf(false) }
    var showPieceSheet: Boolean by remember { mutableStateOf(false) }
    val topAppBarState = rememberAppBarState()

    val colorScheme = rememberColorScheme(
        isDarkTheme = LocalSettingsProvider.current.isDarkMode,
        amoledMode = LocalSettingsProvider.current.amoledMode,
        colorTuple = rememberDynamicThemeState().colorTuple.value
    )

    when(val currentNote = viewModel.note) {
        is Note -> {
            topAppBarState.current.updateTopAppBar(
                actions = {
                    when(viewModel.synchronizing) {
                        is Action.Loading -> CircularProgressIndicator(Modifier.size(24.dp))
                        is Action.Error -> Icon(
                            Icons.Filled.ReportProblem,
                            null
                        )
                        else -> {}
                    }
                    IconButton(
                        enabled = viewModel.synchronizing !is Action.Loading,
                        onClick = {
                            showPieceSheet = false
                            showNoteSheet = !showNoteSheet
                        }) {
                        Icon(
                            if (showNoteSheet) Icons.Filled.Build else Icons.Outlined.Build,
                            null
                        )
                    }
                }
            )

            NoteField(modifier = Modifier.fillMaxSize()) {
                currentNote.textPieces.forEach { piece ->
                    TextPieceComponent(
                        onOffsetChange = {
                            viewModel
                                .updateTextPieces(
                                    currentNote.textPieces
                                        .toMutableList()
                                        .apply { this[this.indexOf(piece)] = piece.copy(offset = it) })
                        },
                        focused = viewModel.focusedPiece == piece,
                        piece = piece,
                        indicationColor = colorScheme.tertiary.copy(0.7f)
                    )
                }
                currentNote.imagePieces.forEach { piece ->
                    ImagePieceComponent(
                        onOffsetChange = {
                            viewModel
                                .updateImagePieces(
                                    currentNote.imagePieces
                                        .toMutableList()
                                        .apply { this[this.indexOf(piece)] = piece.copy(offset = it) })
                        },
                        focused = viewModel.focusedPiece == piece,
                        piece = piece,
                        indicationColor = colorScheme.tertiary.copy(0.7f)

                    )
                }
            }

            AnimatedVisibility(
                visible = showPieceSheet || showNoteSheet,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
            ) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showNoteSheet = false
                        showPieceSheet = false
                    }
                ) {

                    if (showNoteSheet) {
                        NoteSheetContent(note = currentNote, onValueChanged = { viewModel.updateNote(it) })
                    }

                }
            }
        }
        else -> viewModel.setNote(note)
    }



}