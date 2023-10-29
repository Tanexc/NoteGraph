package ru.tanexc.notegraph.presentation.screen.note

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tanexc.notegraph.core.util.Action
import ru.tanexc.notegraph.core.util.SheetContent
import ru.tanexc.notegraph.presentation.screen.note.view_model.NoteViewModel
import ru.tanexc.notegraph.presentation.screen.note_field.NoteField
import ru.tanexc.notegraph.presentation.ui.widgets.note.NoteSheetContent
import ru.tanexc.notegraph.presentation.util.rememberAppBarState
import ru.tanexc.notegraph.presentation.util.rememberBottomSheetState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteScreen(
    modifier: Modifier,
    noteId: String
) {
    val viewModel: NoteViewModel = hiltViewModel()
    val topAppBarState = rememberAppBarState()
    val bottomSheetState = rememberBottomSheetState().current

    LaunchedEffect(noteId) {
        viewModel.initializeNote(noteId)
    }

    viewModel.note?.let { currentNote ->
        topAppBarState.current.updateTopAppBar(
            title = {
                Text(currentNote.label, modifier = Modifier.basicMarquee())
            },
            actions = {
                when (viewModel.synchronizing) {
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
                        bottomSheetState.setContent {
                            NoteSheetContent(
                                note = currentNote,
                                onValueChanged = { viewModel.saveNote(it) }
                            )
                        }
                    }
                ) {
                    Icon(
                        if (viewModel.sheetContent == SheetContent.Note) Icons.Filled.Build else Icons.Outlined.Build,
                        null
                    )
                }
            }
        )

        Box(modifier.fillMaxSize()) {
            NoteField(
                modifier = Modifier.fillMaxSize(),
                note = currentNote
            )
        }
    }
}